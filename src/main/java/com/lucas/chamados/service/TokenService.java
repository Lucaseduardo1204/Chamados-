package com.lucas.chamados.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lucas.chamados.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    //puxa o secret do application.properties que irá "assinar" os tokens, atualmente hardcoded no arquivo porem o valor
    //também pode ser configurado a referenciar a uma variável de ambiente
    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("chamados-api")
                .withSubject(usuario.getEmail())
                .withExpiresAt(Instant.now().plusSeconds(7200))
                .sign(algorithm);
    }


    public String validartoken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("chamados-api")
                .build()
                .verify(token)  //confere a assinatura, se for forjado ou ja expirado, lança exceção
                .getSubject();  //devolve o email
    }

}
