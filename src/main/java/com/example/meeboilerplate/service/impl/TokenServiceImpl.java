package com.example.meeboilerplate.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.meeboilerplate.service.TokenService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${app.token.secret}")
    private String secretKey;
    @Value("${app.token.issuer}")
    private String issuer;

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }

    public DecodedJWT validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(getAlgorithm()).withIssuer(issuer).build();
            return verifier.verify(token);
        } catch (Exception e) {
            return null;
        }
    }
}
