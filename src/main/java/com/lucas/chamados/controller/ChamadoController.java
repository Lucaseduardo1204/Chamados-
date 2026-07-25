package com.lucas.chamados.controller;

import com.lucas.chamados.dto.*;
import com.lucas.chamados.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {
    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService){
        this.chamadoService = chamadoService;
    }

    @GetMapping()
    public List<ChamadoResponseDTO> listarTodos(){
       return  chamadoService.listarTodos();

    }

    @GetMapping("/{id}")
    public ChamadoResponseDTO listarPorid(@PathVariable Long id){
        return chamadoService.listarPorId(id);

    }

    @PostMapping
    public ChamadoResponseDTO criarChamado(@RequestBody @Valid ChamadoRequestDTO chamado){

        // se der certo retorna o DTO no body da resposta
        return chamadoService.novoChamado(chamado);
    }


    @PatchMapping("/{id}/responsavel")
    // alterarResponsavel devolve um ChamadoResponseDTO por parametro ele recebe o id vindo da url e o id do novo
    // responsavel pelo requestbody
    public  ChamadoResponseDTO alterarResponsavel(@PathVariable("id") Long idChamado,
                                                  @RequestBody @Valid AlterarResponsavelDTO novoResponsavel){

        return chamadoService.alterarResponsavel(idChamado, novoResponsavel);
    }

    @PatchMapping("/{id}/situacao")
    public ChamadoResponseDTO alterarSituacao(@PathVariable("id") Long idChamado,
                                              @RequestBody @Valid AlterarSituacaoDTO novaSituacao){

        return chamadoService.alterarSituacao(idChamado, novaSituacao);
    }

    @PostMapping("/{id}/interacoes")
    public InteracaoResponseDTO adicionarInteracao(@PathVariable("id") Long chamadoId,
                                                   @RequestBody @Valid InteracaoRequestDTO interacao){

        return chamadoService.adicionarInteracao(chamadoId, interacao);
    }

    @GetMapping("/{id}/interacoes")
    public List<InteracaoResponseDTO> listarInteracoes(@PathVariable Long id){
        return chamadoService.listarInteracoes(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarChamado(@PathVariable Long id){

        chamadoService.inativarChamado(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



//    O DTO nunca passa do service pra baixo. A Entity nunca sobe além do service.
}
