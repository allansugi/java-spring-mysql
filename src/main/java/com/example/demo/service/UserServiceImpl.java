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
import com.example.demo.verifier.RegisterVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDaoImpl dao;

    @Autowired
    public UserServiceImpl(UserDaoImpl dao) {
        this.dao = dao;
    }

    private User convertToData(UserRegisterForm form) {
        return new User(form);
    }

    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * @param form register form
     * @return response stating registration successful
     * @throws DatabaseErrorException    if database connection has an error
     * @throws RegisterArgumentException if email exists in the database
     * @throws BadRequestException       if parameter not met the requirement
     */
    @Override
    public Response<String> addUser(UserRegisterForm form) throws DatabaseErrorException, RegisterArgumentException, BadRequestException {
        try {
            if (!RegisterVerifier.validPasswordStrength(form.getPassword())) {
                throw new BadRequestException("Password not met the requirement");
            }

            List<User> users = this.dao.findAll();
            for (User user: users) {
                if (user.getEmail().equals(form.getEmail())) {
                    throw new RegisterArgumentException("same email exist");
                }
            }

            User user = convertToData(form);
            user.setPassword(encryptPassword(user.getPassword()));

            this.dao.store(user);

            Response<String> response = new Response<>();
            response.setSuccess(true);
            response.setResponse("registration successful");
            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException(e);
        }
    }

    /**
     * @param form login form
     * @return JWT token
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
            if (!validUser.isEmpty() && BCrypt.checkpw(form.getPassword(), validUser.get(0).getPassword())) {
                Response<String> response = new Response<>();
                response.setSuccess(true);
                response.setResponse(JWTUtil.createToken(validUser.get(0).getId()));
                return response;
            } else {
                throw new AuthenticationException("Incorrect email or password");
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Response<String> updatePassword(String token, String password) throws DatabaseErrorException {
        try {
            String id = JWTUtil.verifyToken(token);
            this.dao.updatePassword(encryptPassword(password), id);
            Response<String> response = new Response<>();
            response.setSuccess(false);
            response.setResponse("password updated");
            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Response<String> updateUsername(String token, String username) throws DatabaseErrorException {
        try {
            String id = JWTUtil.verifyToken(token);
            this.dao.updateUsername(username, id);
            Response<String> response = new Response<>();
            response.setSuccess(false);
            response.setResponse("username updated");
            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Response<String> updateEmail(String token, String email) throws DatabaseErrorException {
        try {
            String id = JWTUtil.verifyToken(token);
            this.dao.updateEmail(email, id);
            Response<String> response = new Response<>();
            response.setSuccess(false);
            response.setResponse("email updated");
            return response;
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

}
