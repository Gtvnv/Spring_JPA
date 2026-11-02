package com.dio.academia.dto;

import com.dio.academia.domain.Plano;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Dados recebidos para criar ou atualizar um plano.
 */
public record PlanoRequest(
        @NotBlank(message = "O nome e obrigatorio") String nome,
        @NotNull(message = "O valor mensal e obrigatorio")
        @Positive(message = "O valor mensal deve ser maior que zero") BigDecimal valorMensal,
        @NotNull(message = "A periodicidade e obrigatoria") Plano.Periodicidade periodicidade
) {
}
