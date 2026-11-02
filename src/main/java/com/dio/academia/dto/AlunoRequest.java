package com.dio.academia.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * Dados recebidos para criar ou atualizar um aluno.
 * As anotacoes de validacao sao verificadas no controller via @Valid.
 */
public record AlunoRequest(
        @NotBlank(message = "O nome e obrigatorio") String nome,
        @NotBlank(message = "O e-mail e obrigatorio")
        @Email(message = "E-mail invalido") String email,
        @NotBlank(message = "O CPF e obrigatorio") String cpf,
        @NotNull(message = "A data de nascimento e obrigatoria")
        @Past(message = "A data de nascimento deve ser no passado") LocalDate dataNascimento,
        @Valid EnderecoDTO endereco
) {
}
