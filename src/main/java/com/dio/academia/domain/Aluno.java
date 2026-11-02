package com.dio.academia.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um aluno matriculado na academia.
 *
 * Demonstra:
 * <ul>
 *   <li>{@link Embedded}: o endereco e incorporado na tabela do aluno;</li>
 *   <li>{@link OneToMany}: um aluno possui varias matriculas, com cascade e
 *       orphanRemoval para gerenciar o ciclo de vida das matriculas.</li>
 * </ul>
 */
@Entity
@Table(name = "aluno")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do aluno e obrigatorio")
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank(message = "O e-mail e obrigatorio")
    @Email(message = "E-mail invalido")
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NotBlank(message = "O CPF e obrigatorio")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @NotNull(message = "A data de nascimento e obrigatoria")
    @Past(message = "A data de nascimento deve ser no passado")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Matricula> matriculas = new ArrayList<>();

    public Aluno() {
    }

    public Aluno(String nome, String email, String cpf, LocalDate dataNascimento) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
    }

    /**
     * Adiciona uma matricula mantendo a consistencia dos dois lados do
     * relacionamento bidirecional.
     */
    public void adicionarMatricula(Matricula matricula) {
        matriculas.add(matricula);
        matricula.setAluno(this);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
}
