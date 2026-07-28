package com.lucas.chamados.controller;

import com.lucas.chamados.dto.LoginRequestDTO;
import com.lucas.chamados.dto.LoginResponseDTO;
import com.lucas.chamados.model.entity.Usuario;
import com.lucas.chamados.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Recebe o DTO e organiza o login
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService){
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    // Define a rota de login
    @PostMapping("/login")
    // Login response recebe como parâmetro Login request, dentro dela
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO dados){


        // define a variavel authToken que recebe um novo Objeto UsernamePasswordAuthenticationToken que recebe email e
        // senha originados do LoginRequestDTO. Serve como um envelope para carregar o email e a senha, nessa flag o
        // isAuthenticated retorna false
        var authToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());


        // Após, define a variável auth que recebe authenticationManager que usa o metodo authenticate para conferir o
        // authToken. É a chamada para o motor de autenticação do SpringSecurity(AuthenticationManager)
        // Se der certo, retorna um novo objeto Authentication totalmente preenchido e validado, se der errado lança
        // exceção como BadCredentialsException
        var auth = authenticationManager.authenticate(authToken);


        // Define usuario que recebe o casting (Garante que ele será um usuário) do auth.getPrincipal. É um método do
        //  objeto Authentication (auth) responsável por retornar quem é o sujeito/usuário daquela autenticação.
        //  Antes de autenticar, geralmente retorna apenas uma String com o login/e-mail digitado no formulário, após
        //  autenticar, retorna o objeto do usuário completo.
        var usuario = (Usuario) auth.getPrincipal();


        // Define token que recebe o tokenService que utiliza o método de gerarToken recebendo o usuario como parametro
        String token = tokenService.gerarToken(usuario);


        // Retorna um novo LoginResponseDTO passando o token gerado como parâmetro
        return new LoginResponseDTO(token);

        /* Resumindo:
        * UsernamePasswordAuthenticationToken = é um envelope contendo login e senha (pode estar validado ou não)
        * authenticationManager.authenticate() = O método que valida se o login e a senha batem. Lança exceção se falhar
        * atualmente, falta um tratamento na GlobalExceptionHandler devolvento um status correto (401) com mensagem clara
        * auth.getPrincipal() = Retorna os dados completos do usuário logado (geralmente um UserDetails)
        */
    }

}
