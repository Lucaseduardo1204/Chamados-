package com.lucas.chamados.dto;

import com.lucas.chamados.model.enums.PrioridadeEnum;
import com.lucas.chamados.model.enums.SituacaoEnum;
import com.lucas.chamados.model.enums.TipoEnum;

import java.time.LocalDateTime;

public record ChamadoResponseDTO(
        Long id,
        LocalDateTime dataHoraCriacao,
        SituacaoEnum situacao,
        PrioridadeEnum prioridade,
        TipoEnum tipo,
        String sistema,
        String resumo,
        String descricao,
        UsuarioResumoDTO solicitante,
        UsuarioResumoDTO responsavel
) {
}
