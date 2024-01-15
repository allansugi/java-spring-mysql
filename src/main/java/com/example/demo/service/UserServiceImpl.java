package com.example.demo.service;

import com.example.demo.dao.UserDaoImpl;
import com.example.demo.exception.AuthenticationException;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.model.User;
import com.example.demo.util.JWTUtil;
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

    @Override
    public void addUser(UserRegisterForm form) throws DatabaseErrorException {
        try {
            User user = convertToData(form);
            user.setPassword(encryptPassword(user.getPassword()));
            this.dao.store(user);
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    /**
     * @param form
     * @return JWT token
     * @throws DatabaseErrorException if there's an error in the database
     * @throws AuthenticationException if no account found
     */
    @Override
    public String authenticate(UserLoginForm form) throws DatabaseErrorException, AuthenticationException {
        try {
            List<User> users = this.dao.findAll();
            String valId = form.getEmail();
            List<User> validUser = users.stream()
                    .filter(u -> u.getEmail().equals(valId))
                    .toList();
            if (!validUser.isEmpty() && BCrypt.checkpw(form.getPassword(), validUser.get(0).getPassword())) {
                return JWTUtil.createToken(validUser.get(0).getId());
            } else {
                throw new AuthenticationException("no such account exist");
            }
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public void updatePassword(String id, String password) throws DatabaseErrorException {
        try {
            this.dao.updatePassword(encryptPassword(password), id);
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public void updateUsername(String id, String username) throws DatabaseErrorException {
        try {
            this.dao.updateUsername(username, id);
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public void updateEmail(String id, String email) throws DatabaseErrorException {
        try {
            this.dao.updateEmail(email, id);
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

}
