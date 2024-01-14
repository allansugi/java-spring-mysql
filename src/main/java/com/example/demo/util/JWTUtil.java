package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTUtil {
    private static final String secret = "secret";

    public static String createToken(String id) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JWT.create().withIssuer("auth0").withClaim("id", id).sign(algorithm);
    }
}
