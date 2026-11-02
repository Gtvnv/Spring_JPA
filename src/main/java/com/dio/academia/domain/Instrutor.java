package com.dio.academia.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa um instrutor da academia.
 *
 * Demonstra um relacionamento {@link ManyToMany}: um instrutor pode atuar em
 * varias modalidades e uma modalidade pode ter varios instrutores.
 */
@Entity
@Table(name = "instrutor")
public class Instrutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do instrutor e obrigatorio")
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank(message = "O CREF e obrigatorio")
    @Size(max = 20, message = "O CREF deve ter no maximo 20 caracteres")
    @Column(nullable = false, unique = true, length = 20)
    private String cref;

    @ManyToMany
    @JoinTable(
            name = "instrutor_modalidade",
            joinColumns = @JoinColumn(name = "instrutor_id"),
            inverseJoinColumns = @JoinColumn(name = "modalidade_id")
    )
    private Set<Modalidade> modalidades = new HashSet<>();

    public Instrutor() {
    }

    public Instrutor(String nome, String cref) {
        this.nome = nome;
        this.cref = cref;
    }

    public void adicionarModalidade(Modalidade modalidade) {
        this.modalidades.add(modalidade);
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

    public String getCref() {
        return cref;
    }

    public void setCref(String cref) {
        this.cref = cref;
    }

    public Set<Modalidade> getModalidades() {
        return modalidades;
    }

    public void setModalidades(Set<Modalidade> modalidades) {
        this.modalidades = modalidades;
    }
}
