package com.dio.academia.dto;

import com.dio.academia.domain.Plano;

import java.math.BigDecimal;

/**
 * Dados retornados ao consultar um plano.
 */
public record PlanoResponse(
        Long id,
        String nome,
        BigDecimal valorMensal,
        String periodicidade
) {

    public static PlanoResponse fromEntity(Plano plano) {
        return new PlanoResponse(
                plano.getId(),
                plano.getNome(),
                plano.getValorMensal(),
                plano.getPeriodicidade().name()
        );
    }
}
