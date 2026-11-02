package com.dio.academia.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Representa uma modalidade oferecida pela academia (ex.: Musculacao, Spinning,
 * Crossfit, Pilates).
 *
 * Demonstra o mapeamento mais simples de uma entidade JPA: uma tabela com
 * colunas basicas.
 */
@Entity
@Table(name = "modalidade")
public class Modalidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da modalidade e obrigatorio")
    @Size(max = 60, message = "O nome deve ter no maximo 60 caracteres")
    @Column(nullable = false, unique = true, length = 60)
    private String nome;

    @Size(max = 255, message = "A descricao deve ter no maximo 255 caracteres")
    @Column(length = 255)
    private String descricao;

    public Modalidade() {
    }

    public Modalidade(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
