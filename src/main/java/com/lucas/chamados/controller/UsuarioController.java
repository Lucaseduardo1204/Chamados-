package com.lucas.chamados.controller;

import com.lucas.chamados.model.Usuario;
import com.lucas.chamados.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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

    //PostMapping - responde ao verbo POST em /usuarios, o que criará um novo recurso
    @PostMapping
    // @RequestBody - pega o JSON que chega no corpo da requisição e converte para objeto Usuario
    // @Valid = dispara as validações (Ex: @Email no campo de email da entity)
    public Usuario criar(@RequestBody @Valid Usuario usuario){
        //Faz o insert
        return repository.save(usuario);
    }
}
