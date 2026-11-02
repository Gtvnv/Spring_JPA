package com.dio.academia.repository;

import com.dio.academia.domain.Modalidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio da entidade {@link Modalidade}.
 *
 * Ao estender {@link JpaRepository} ganhamos gratuitamente as operacoes de
 * CRUD (save, findById, findAll, deleteById, etc.).
 */
public interface ModalidadeRepository extends JpaRepository<Modalidade, Long> {

    Optional<Modalidade> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
