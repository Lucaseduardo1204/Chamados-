package com.lucas.chamados.exception;

public class SituacaoNaoPermitida extends RuntimeException{
    public SituacaoNaoPermitida(){
        super("não é possível alterar de FECHADA para ABERTA");
    }
}
