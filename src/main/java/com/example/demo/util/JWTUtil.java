package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {
    private static final String secret = "secret";
    private static final String accessClaim = "id";
    public static String createToken(String id) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JWT.create().withIssuer("auth0").withClaim(accessClaim, id).sign(algorithm);
    }

    /**
     * expiry for valid token is for 1h
     * @param token from header
     * @return decoded token which is id
     */
    public static String verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                                    .acceptLeeway(3600)
                                    .withIssuer("auth0")
                                    .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim(accessClaim).asString();
    }
}
