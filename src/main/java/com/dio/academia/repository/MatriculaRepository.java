package com.dio.academia.repository;

import com.dio.academia.domain.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio da entidade {@link Matricula}.
 *
 * Demonstra consultas por propriedades de entidades relacionadas e uma
 * agregacao com JPQL.
 */
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findByAlunoId(Long alunoId);

    List<Matricula> findByStatus(Matricula.Status status);

    /**
     * Conta quantas matriculas existem para cada plano.
     * Retorna uma lista de arrays [nomeDoPlano, quantidade].
     */
    @Query("""
            SELECT m.plano.nome, COUNT(m)
            FROM Matricula m
            GROUP BY m.plano.nome
            ORDER BY COUNT(m) DESC
            """)
    List<Object[]> contarMatriculasPorPlano();

    boolean existsByAlunoIdAndStatus(Long alunoId, Matricula.Status status);

    @Query("SELECT COUNT(m) FROM Matricula m WHERE m.status = :status")
    long contarPorStatus(@Param("status") Matricula.Status status);
}
