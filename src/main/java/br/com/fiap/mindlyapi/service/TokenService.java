package br.com.fiap.mindlyapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(String subject) {
        return JWT.create()
                .withIssuer("mindly-api")
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(Algorithm.HMAC256(secret));
    }

    public String validarToken(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("mindly-api")
                .build()
                .verify(token)
                .getSubject();
    }
}
