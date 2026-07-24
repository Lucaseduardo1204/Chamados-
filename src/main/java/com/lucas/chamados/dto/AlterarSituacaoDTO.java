package com.lucas.chamados.dto;

import com.lucas.chamados.model.enums.SituacaoEnum;
import jakarta.validation.constraints.NotNull;

public record AlterarSituacaoDTO(
        @NotNull
        SituacaoEnum situacao
) {

}
