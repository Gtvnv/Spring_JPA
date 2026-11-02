package com.dio.academia.repository;

import com.dio.academia.domain.Plano;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositorio da entidade {@link Plano}.
 *
 * Demonstra consultas derivadas do nome do metodo (query methods).
 */
public interface PlanoRepository extends JpaRepository<Plano, Long> {

    List<Plano> findByPeriodicidade(Plano.Periodicidade periodicidade);

    List<Plano> findByValorMensalLessThanEqualOrderByValorMensalAsc(BigDecimal valor);
}
