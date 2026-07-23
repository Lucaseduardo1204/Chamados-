package com.lucas.chamados.controller;

import com.lucas.chamados.dto.ChamadoRequestDTO;
import com.lucas.chamados.dto.ChamadoResponseDTO;
import com.lucas.chamados.model.entity.Chamado;
import com.lucas.chamados.service.ChamadoService;
import jakarta.validation.Valid;
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

    @PostMapping
    public ChamadoResponseDTO criarChamado(@RequestBody @Valid ChamadoRequestDTO chamado){

        return chamadoService.novoChamado(chamado);
    }

//    O DTO nunca passa do service pra baixo. A Entity nunca sobe além do service.
}
