package com.lucas.chamados.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    //Quando essa exceção específica acontecer rode esse método (catch tipado), pega uma família de exceção específica
    // Quando o @Valid falha, ele lança MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(MethodArgumentNotValidException ex){
        Map<String, String> erros = new HashMap<>();

        // pra cada campo que falhar, pega o nome do campo e a mensagem limpa
        ex.getBindingResult().getFieldErrors().forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

}
