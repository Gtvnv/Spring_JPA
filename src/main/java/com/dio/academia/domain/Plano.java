package com.dio.academia.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Representa um plano de assinatura da academia (ex.: Mensal, Trimestral, Anual).
 *
 * Demonstra o uso de {@link Enumerated} para persistir um enum e o mapeamento
 * de valores monetarios com {@link BigDecimal}.
 */
@Entity
@Table(name = "plano")
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do plano e obrigatorio")
    @Column(nullable = false, length = 60)
    private String nome;

    @NotNull(message = "O valor mensal e obrigatorio")
    @Positive(message = "O valor mensal deve ser maior que zero")
    @Column(name = "valor_mensal", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMensal;

    @NotNull(message = "A periodicidade e obrigatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Periodicidade periodicidade;

    public enum Periodicidade {
        MENSAL, TRIMESTRAL, SEMESTRAL, ANUAL
    }

    public Plano() {
    }

    public Plano(String nome, BigDecimal valorMensal, Periodicidade periodicidade) {
        this.nome = nome;
        this.valorMensal = valorMensal;
        this.periodicidade = periodicidade;
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

    public BigDecimal getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(BigDecimal valorMensal) {
        this.valorMensal = valorMensal;
    }

    public Periodicidade getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(Periodicidade periodicidade) {
        this.periodicidade = periodicidade;
    }
}
