package br.com.fiap.mindlyapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(String subject, String role) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer("mindly-api")
                .withSubject(subject)
                .withClaim("role", role)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);
    }

    public String gerarToken(String subject) {
        return gerarToken(subject, "PACIENTE");
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decoded = JWT.require(algorithm)
                    .withIssuer("mindly-api")
                    .build()
                    .verify(token);

            return decoded.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String extrairRole(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decoded = JWT.require(algorithm)
                    .withIssuer("mindly-api")
                    .build()
                    .verify(token);

            return decoded.getClaim("role").asString();
        } catch (Exception e) {
            return null;
        }
    }
}
