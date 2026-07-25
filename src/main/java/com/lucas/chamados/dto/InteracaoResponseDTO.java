package com.lucas.chamados.dto;

import java.time.LocalDateTime;

public record InteracaoResponseDTO(
        Long id,
        UsuarioResumoDTO autor,
        String texto,
        LocalDateTime dataHora

) {
}
