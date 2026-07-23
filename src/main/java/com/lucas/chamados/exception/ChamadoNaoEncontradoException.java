package com.lucas.chamados.exception;

public class ChamadoNaoEncontradoException extends RuntimeException{
    public ChamadoNaoEncontradoException(Long id){
        super("Chamado não encontado para o id: " + id);
    }
}
