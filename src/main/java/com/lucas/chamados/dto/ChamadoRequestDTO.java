package com.lucas.chamados.dto;

import com.lucas.chamados.model.enums.PrioridadeEnum;
import com.lucas.chamados.model.enums.TipoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ChamadoRequestDTO(

        @NotNull(message = "Selecione a prioridade!")
        PrioridadeEnum prioridade,

        @NotNull(message = "Selecione o tipo!")
        TipoEnum tipo,

        @NotBlank(message = "Sistema não pode ser nulo!")
        String sistema,

        @NotBlank(message = "Resumo não pode ser nulo!")
        String resumo,

        @NotBlank(message = "Descrição não pode ser nula!")
        String descricao,

        @NotNull
        Long solicitanteId

) {
}
