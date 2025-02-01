package com.example.meeboilerplate.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface TokenService {
    public Algorithm getAlgorithm();

    public DecodedJWT validateToken(String token);
}
