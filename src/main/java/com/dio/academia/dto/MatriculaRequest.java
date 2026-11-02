package com.dio.academia.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Dados para matricular um aluno em um plano.
 */
public record MatriculaRequest(
        @NotNull(message = "O id do aluno e obrigatorio") Long alunoId,
        @NotNull(message = "O id do plano e obrigatorio") Long planoId,
        @NotNull(message = "A data de inicio e obrigatoria") LocalDate dataInicio
) {
}
