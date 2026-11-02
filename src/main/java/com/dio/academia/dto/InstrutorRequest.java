package com.dio.academia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

/**
 * Dados recebidos para criar ou atualizar um instrutor.
 * O campo modalidadeIds associa o instrutor a modalidades existentes
 * (relacionamento ManyToMany).
 */
public record InstrutorRequest(
        @NotBlank(message = "O nome e obrigatorio") String nome,
        @NotBlank(message = "O CREF e obrigatorio")
        @Size(max = 20, message = "O CREF deve ter no maximo 20 caracteres") String cref,
        Set<Long> modalidadeIds
) {
}
