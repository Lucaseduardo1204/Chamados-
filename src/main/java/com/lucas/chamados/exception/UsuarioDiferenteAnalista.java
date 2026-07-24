package com.lucas.chamados.exception;

public class UsuarioDiferenteAnalista extends RuntimeException {
    public UsuarioDiferenteAnalista(Long id){
        super("O usuário com id: " + id + " deve ser analista!");
    }
}

