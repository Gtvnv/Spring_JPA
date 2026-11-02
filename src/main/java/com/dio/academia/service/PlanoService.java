package com.dio.academia.service;

import com.dio.academia.domain.Plano;
import com.dio.academia.dto.PlanoRequest;
import com.dio.academia.repository.PlanoRepository;
import com.dio.academia.service.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Regras de negocio para planos.
 */
@Service
@Transactional(readOnly = true)
public class PlanoService {

    private final PlanoRepository planoRepository;

    public PlanoService(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    public List<Plano> listarTodos() {
        return planoRepository.findAll();
    }

    public Plano buscarPorId(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Plano nao encontrado com id " + id));
    }

    public List<Plano> listarPorPeriodicidade(Plano.Periodicidade periodicidade) {
        return planoRepository.findByPeriodicidade(periodicidade);
    }

    @Transactional
    public Plano criar(PlanoRequest request) {
        Plano plano = new Plano(request.nome(), request.valorMensal(), request.periodicidade());
        return planoRepository.save(plano);
    }

    @Transactional
    public Plano atualizar(Long id, PlanoRequest request) {
        Plano plano = buscarPorId(id);
        plano.setNome(request.nome());
        plano.setValorMensal(request.valorMensal());
        plano.setPeriodicidade(request.periodicidade());
        return planoRepository.save(plano);
    }

    @Transactional
    public void excluir(Long id) {
        Plano plano = buscarPorId(id);
        planoRepository.delete(plano);
    }
}
