package com.lucas.chamados.exception;

public class UsuarioNaoEncontradoException extends RuntimeException{
    public UsuarioNaoEncontradoException(Long id){
        super("Usuario não encontado para o id: " + id);
    }
}
