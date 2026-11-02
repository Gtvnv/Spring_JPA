package com.dio.academia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Dados recebidos para criar ou atualizar uma modalidade.
 */
public record ModalidadeRequest(
        @NotBlank(message = "O nome e obrigatorio")
        @Size(max = 60, message = "O nome deve ter no maximo 60 caracteres") String nome,
        @Size(max = 255, message = "A descricao deve ter no maximo 255 caracteres") String descricao
) {
}
