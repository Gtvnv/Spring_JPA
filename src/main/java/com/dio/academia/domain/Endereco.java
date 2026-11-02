package com.dio.academia.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

/**
 * Objeto de valor que representa o endereco de um aluno.
 *
 * A anotacao {@link Embeddable} indica que esta classe nao possui tabela
 * propria: suas colunas sao incorporadas na tabela da entidade que a utiliza
 * (ver {@link Aluno}).
 */
@Embeddable
public class Endereco {

    @Size(max = 10, message = "O CEP deve ter no maximo 10 caracteres")
    @Column(length = 10)
    private String cep;

    @Size(max = 150)
    @Column(length = 150)
    private String logradouro;

    @Size(max = 60)
    @Column(length = 60)
    private String cidade;

    @Size(max = 2, message = "A UF deve ter 2 caracteres")
    @Column(length = 2)
    private String uf;

    public Endereco() {
    }

    public Endereco(String cep, String logradouro, String cidade, String uf) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
