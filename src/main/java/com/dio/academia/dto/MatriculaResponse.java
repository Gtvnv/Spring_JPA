package com.dio.academia.dto;

import com.dio.academia.domain.Matricula;

import java.time.LocalDate;

/**
 * Dados retornados ao consultar uma matricula.
 */
public record MatriculaResponse(
        Long id,
        Long alunoId,
        String alunoNome,
        Long planoId,
        String planoNome,
        LocalDate dataInicio,
        LocalDate dataFim,
        String status
) {

    public static MatriculaResponse fromEntity(Matricula m) {
        return new MatriculaResponse(
                m.getId(),
                m.getAluno().getId(),
                m.getAluno().getNome(),
                m.getPlano().getId(),
                m.getPlano().getNome(),
                m.getDataInicio(),
                m.getDataFim(),
                m.getStatus().name()
        );
    }
}
