package com.dio.academia.repository;

import com.dio.academia.domain.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio da entidade {@link Instrutor}.
 *
 * Demonstra consultas avancadas com JPQL (@Query) navegando pelo
 * relacionamento ManyToMany com Modalidade.
 */
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {

    Optional<Instrutor> findByCref(String cref);

    boolean existsByCref(String cref);

    /**
     * Lista todos os instrutores ja carregando suas modalidades (fetch join),
     * evitando o LazyInitializationException ao montar o response.
     */
    @Query("SELECT DISTINCT i FROM Instrutor i LEFT JOIN FETCH i.modalidades")
    List<Instrutor> buscarTodosComModalidades();

    /**
     * Busca um instrutor por id ja carregando suas modalidades (fetch join).
     */
    @Query("SELECT i FROM Instrutor i LEFT JOIN FETCH i.modalidades WHERE i.id = :id")
    Optional<Instrutor> buscarComModalidades(@Param("id") Long id);

    /**
     * Busca instrutores que atuam em uma determinada modalidade (pelo nome),
     * navegando pelo relacionamento ManyToMany.
     */
    @Query("""
            SELECT DISTINCT i FROM Instrutor i
            JOIN i.modalidades m
            WHERE LOWER(m.nome) = LOWER(:nomeModalidade)
            """)
    List<Instrutor> buscarPorModalidade(@Param("nomeModalidade") String nomeModalidade);
}
