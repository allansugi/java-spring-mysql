package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.dao.UserDaoImpl;
import com.example.demo.exception.DatabaseErrorException;
import com.example.demo.form.UserLoginForm;
import com.example.demo.form.UserRegisterForm;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDaoImpl dao;
    private final String secret = "secret";

    @Autowired
    public UserServiceImpl(UserDaoImpl dao) {
        this.dao = dao;
    }

    private User convertToData(UserRegisterForm form) {
        return new User(form);
    }
    private String createToken() {
        Algorithm algorithm = Algorithm.HMAC512(this.secret);
        return JWT.create().withIssuer("auth0").sign(algorithm);
    }
    private User encryptPassword(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        return user;
    }

    @Override
    public void addUser(UserRegisterForm form) throws DatabaseErrorException {
        try {
            User user = convertToData(form);
            User encrypted = encryptPassword(user);
            this.dao.store(encrypted);
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public Boolean authenticate(UserLoginForm form) throws DatabaseErrorException {
        try {
            List<User> users = this.dao.findAll();
            String valId = form.getEmail();
            List<User> validUser = users.stream()
                    .filter(u -> u.getEmail().equals(valId))
                    .toList();
            return !validUser.isEmpty() && BCrypt.checkpw(form.getPassword(), validUser.get(0).getPassword());
        } catch (SQLException e) {
            throw new DatabaseErrorException();
        }
    }

    @Override
    public void updatePassword() throws DatabaseErrorException {

    }

    @Override
    public void updateUsername() throws DatabaseErrorException {

    }

    @Override
    public void updateEmail() throws DatabaseErrorException {

    }


}
