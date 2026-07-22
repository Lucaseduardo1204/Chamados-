package com.lucas.chamados.controller;

import com.lucas.chamados.model.entity.Chamado;
import com.lucas.chamados.repository.ChamadoRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {
    private final ChamadoRepository repository;

    public ChamadoController(ChamadoRepository repository){
        this.repository = repository;
    }

    @GetMapping()
    public List<Chamado> listarTodos(){
        return repository.findAll();

    }

    @PostMapping
    public Chamado criarChamado(@RequestBody @Valid Chamado chamado){
        return repository.save(chamado);
    }

}
