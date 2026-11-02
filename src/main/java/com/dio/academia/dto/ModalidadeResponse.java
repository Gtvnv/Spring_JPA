package com.dio.academia.dto;

import com.dio.academia.domain.Modalidade;

/**
 * Dados retornados ao consultar uma modalidade.
 */
public record ModalidadeResponse(
        Long id,
        String nome,
        String descricao
) {

    public static ModalidadeResponse fromEntity(Modalidade modalidade) {
        return new ModalidadeResponse(
                modalidade.getId(),
                modalidade.getNome(),
                modalidade.getDescricao()
        );
    }
}
