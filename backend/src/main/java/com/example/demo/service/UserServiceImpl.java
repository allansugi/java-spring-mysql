package com.example.demo.service;

import com.example.demo.dao.UserDaoImpl;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.exception.RegisterArgumentException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.model.User;
import com.example.demo.response.Response;
import com.example.demo.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDaoImpl dao;
    private final JWTUtil util;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserDaoImpl dao, JWTUtil util) {
        this.dao = dao;
        this.util = util;
        this.encoder = new BCryptPasswordEncoder();
    }

    private User convertToData(UserRegisterForm form) {
        return new User(form);
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, encoder.encode(password));
    }

    private Boolean passwordMatches(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }

    private Boolean validEmail(String email) throws SQLException {
        List<User> users = this.dao.findAll();
        for (User user: users) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    private Boolean validUsername(String username) throws SQLException {
        List<User> users = this.dao.findAll();
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * requirement:
     * minimum password length is 8
     * have at least 1 uppercase and 1 lowercase letter
     * have at least 1 number
     * have at least 1 special character
     * @param password password
     * @return true if password meets the requirement, otherwise false
     */
    private Boolean validPassword(String password) {
        boolean containSpecialCharacter = false;
        boolean containNumber = false;
        boolean containUpper = false;
        boolean containLower = false;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                containNumber = true;
            } else if (Character.isUpperCase(c)) {
                containUpper = true;
            } else if (Character.isLowerCase(c)) {
                containLower = true;
            } else {
                containSpecialCharacter = true;
            }
        }

        // Ensure that at least one character from each category is present
        return containLower
                && containUpper
                && containNumber
                && containSpecialCharacter
                && password.length() >= 8;
    }

    /**
     * @param form register form
     * @return response stating registration successful
     * @throws DatabaseErrorException    if database connection has an error
     * @throws RegisterArgumentException if account with same email exists in the database
     * @throws BadRequestException       if parameter not meet the requirement
     */
    @Override
    public Response<String> addUser(UserRegisterForm form) throws DatabaseErrorException, RegisterArgumentException, BadRequestException {
        try {
            if (!validPassword(form.getPassword())) {
                throw new BadRequestException("Password not meet the requirement");
            } else if (!validEmail(form.getEmail())) {
                throw new RegisterArgumentException("same email exist");
            } else {
                User user = convertToData(form);
                user.setPassword(encryptPassword(user.getPassword()));

                this.dao.store(user);

                Response<String> response = new Response<>();
                response.setSuccess(true);
                response.setResponse("registration successful");
                return response;
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException(e);
        }
    }

    /**
     * @param form login form
     * @return success response with JWT token
     * @throws DatabaseErrorException  if there's an error in the database
     * @throws AuthenticationException if no account found
     */
    @Override
    public Response<String> authenticate(UserLoginForm form) throws DatabaseErrorException, AuthenticationException {
        try {
            List<User> users = this.dao.findAll();
            String valId = form.getEmail();
            List<User> validUser = users.stream()
                    .filter(u -> u.getEmail().equals(valId))
                    .toList();
            if (!validUser.isEmpty() && passwordMatches(form.getPassword(), validUser.get(0).getPassword())) {
                Response<String> response = new Response<>();
                response.setSuccess(true);
                response.setResponse(this.util.createToken(validUser.get(0).getId()));
                return response;
            } else {
                throw new AuthenticationException("Incorrect email or password");
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    /**
     * @param token token from cookie
     * @param password new password
     * @return successful response password updated
     * @throws DatabaseErrorException database error 505
     * @throws BadRequestException inadequate password requirement error 400
     */
    @Override
    public Response<String> updatePassword(String token, String password) throws DatabaseErrorException, BadRequestException {
        try {
            if (!validPassword(password)) {
                throw new BadRequestException("Password not met the requirement");
            } else {
                String id = this.util.verifyToken(token);
                this.dao.updatePassword(encryptPassword(password), id);
                Response<String> response = new Response<>();
                response.setSuccess(true);
                response.setResponse("password updated");
                return response;
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    /**
     * @param token token from cookie
     * @param username new username
     * @return successful response username updated
     * @throws DatabaseErrorException database error 500
     */
    @Override
    public Response<String> updateUsername(String token, String username) throws DatabaseErrorException {
        try {
            if (!validUsername(username)) {
                throw new RegisterArgumentException("same username exist");
            }
            String id = this.util.verifyToken(token);
            this.dao.updateUsername(username, id);
            Response<String> response = new Response<>();
            response.setSuccess(true);
            response.setResponse("username updated");
            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    /**
     * @param token token from cookie
     * @param email new email
     * @return success response email updated
     * @throws DatabaseErrorException database error 500
     * @throws RegisterArgumentException same email error 409
     */
    @Override
    public Response<String> updateEmail(String token, String email) throws DatabaseErrorException, RegisterArgumentException {
        try {
            if (!validEmail(email)) {
                throw new RegisterArgumentException("same email exist");
            }
            String id = this.util.verifyToken(token);
            this.dao.updateEmail(email, id);
            Response<String> response = new Response<>();
            response.setSuccess(true);
            response.setResponse("email updated");
            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

}
