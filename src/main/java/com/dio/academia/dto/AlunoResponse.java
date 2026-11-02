package com.dio.academia.dto;

import com.dio.academia.domain.Aluno;

import java.time.LocalDate;

/**
 * Dados retornados ao cliente ao consultar um aluno.
 * Expoe apenas o que interessa, desacoplando a API da entidade JPA.
 */
public record AlunoResponse(
        Long id,
        String nome,
        String email,
        String cpf,
        LocalDate dataNascimento,
        EnderecoDTO endereco
) {

    public static AlunoResponse fromEntity(Aluno aluno) {
        EnderecoDTO enderecoDTO = null;
        if (aluno.getEndereco() != null) {
            enderecoDTO = new EnderecoDTO(
                    aluno.getEndereco().getCep(),
                    aluno.getEndereco().getLogradouro(),
                    aluno.getEndereco().getCidade(),
                    aluno.getEndereco().getUf()
            );
        }
        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCpf(),
                aluno.getDataNascimento(),
                enderecoDTO
        );
    }
}
