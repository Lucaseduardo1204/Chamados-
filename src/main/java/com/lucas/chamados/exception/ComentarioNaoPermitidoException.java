package com.lucas.chamados.exception;

public class ComentarioNaoPermitidoException extends RuntimeException {
    public ComentarioNaoPermitidoException() {
        super("esse usuário não pode comentar nesse chamado!");
    }
}
