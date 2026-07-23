package com.lucas.chamados.dto;

import com.lucas.chamados.model.enums.Fundacao;

public record UsuarioResumoDTO (Long id, String nome, Fundacao fundacao) {
}
