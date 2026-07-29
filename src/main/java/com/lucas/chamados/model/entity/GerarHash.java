package com.lucas.chamados.model.entity;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarHash {
    public static void main(String[] args) {
        String hash = new BCryptPasswordEncoder().encode("senhasolicitante");
        System.out.println(hash);
    }
}
