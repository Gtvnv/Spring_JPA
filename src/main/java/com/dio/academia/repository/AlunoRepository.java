package com.dio.academia.repository;

import com.dio.academia.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio da entidade {@link Aluno}.
 *
 * Mistura query methods derivados com consultas JPQL customizadas.
 */
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Optional<Aluno> findByEmailIgnoreCase(String email);

    Optional<Aluno> findByCpf(String cpf);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByCpf(String cpf);

    List<Aluno> findByNomeContainingIgnoreCase(String trecho);

    /**
     * Lista alunos que possuem ao menos uma matricula com o status informado.
     * O status e passado como parametro para manter o JPQL simples e portavel.
     */
    @Query("""
            SELECT DISTINCT a FROM Aluno a
            JOIN a.matriculas m
            WHERE m.status = :status
            """)
    List<Aluno> buscarPorStatusMatricula(@Param("status") com.dio.academia.domain.Matricula.Status status);

    /**
     * Busca o aluno pelo id ja carregando suas matriculas (fetch join) para
     * evitar o problema do N+1 e o LazyInitializationException.
     */
    @Query("""
            SELECT a FROM Aluno a
            LEFT JOIN FETCH a.matriculas
            WHERE a.id = :id
            """)
    Optional<Aluno> buscarComMatriculas(@Param("id") Long id);
}
