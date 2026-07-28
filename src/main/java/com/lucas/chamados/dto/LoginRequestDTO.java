package com.lucas.chamados.dto;

import jakarta.validation.constraints.NotBlank;

//Carrega email e senha em texto puro, define o que o cliente pode mandar
public record LoginRequestDTO(
        @NotBlank
        String email,

        @NotBlank
        String senha
) {
}
