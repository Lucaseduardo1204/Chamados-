package com.lucas.chamados.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// - RestControllerAdvice - intercepta exceções de TODOS os controllers em um único lugar - tratador global, toda exceção que
// sair de um controller, passa por ele. (Um catch que cobre a aplicação toda e não só um método)
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    //Quando essa exceção específica acontecer rode esse método (catch tipado), pega uma família de exceção específica
    // Quando o @Valid falha, ele lança MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(MethodArgumentNotValidException ex){
        Map<String, String> erros = new HashMap<>();

        // pra cada campo que falhar, pega o nome do campo e a mensagem limpa
        ex.getBindingResult().getFieldErrors().forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> tratarMensagemDeFundacao(HttpMessageNotReadableException ex){
        Map<String, String> erros = new HashMap<>();

        erros.put("Erro", "Valor inválido para um dos campos. Verifique os valores permitidos.");
        log.error(String.valueOf(ex.getCause()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }



}
