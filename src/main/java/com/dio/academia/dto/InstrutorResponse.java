package com.dio.academia.dto;

import com.dio.academia.domain.Instrutor;

import java.util.List;

/**
 * Dados retornados ao consultar um instrutor, incluindo as modalidades em que
 * ele atua (relacionamento ManyToMany).
 */
public record InstrutorResponse(
        Long id,
        String nome,
        String cref,
        List<ModalidadeResponse> modalidades
) {

    public static InstrutorResponse fromEntity(Instrutor instrutor) {
        List<ModalidadeResponse> modalidades = instrutor.getModalidades().stream()
                .map(ModalidadeResponse::fromEntity)
                .sorted((a, b) -> a.nome().compareToIgnoreCase(b.nome()))
                .toList();
        return new InstrutorResponse(
                instrutor.getId(),
                instrutor.getNome(),
                instrutor.getCref(),
                modalidades
        );
    }
}
