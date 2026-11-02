package com.dio.academia.dto;

import jakarta.validation.constraints.Size;

/**
 * DTO para o endereco embutido no aluno.
 */
public record EnderecoDTO(
        @Size(max = 10) String cep,
        @Size(max = 150) String logradouro,
        @Size(max = 60) String cidade,
        @Size(max = 2, message = "A UF deve ter 2 caracteres") String uf
) {
}
