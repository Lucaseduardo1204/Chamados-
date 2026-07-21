package com.lucas.chamados.controller;

import com.lucas.chamados.model.Usuario;
import com.lucas.chamados.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @RestController - Indica que a classe responde requisições HTTP e devolve JSON
@RestController

//@RequestMapping("/usuarios") - o endereço de base, tudo sobre /usuarios
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repository;

    // Injeção de dependência por construtor
    public UsuarioController(UsuarioRepository repository){
        this.repository = repository;
    }

    //@GetMapping - quando chegar um get em /usuarios, roda esse método
    @GetMapping
    public List<Usuario> listarTodos(){
        // .findAll() - método padrão do JpaRepository
        return repository.findAll();
    }
}
