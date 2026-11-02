package com.dio.academia.service;

import com.dio.academia.domain.Modalidade;
import com.dio.academia.dto.ModalidadeRequest;
import com.dio.academia.repository.ModalidadeRepository;
import com.dio.academia.service.exception.RecursoNaoEncontradoException;
import com.dio.academia.service.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Regras de negocio para modalidades.
 */
@Service
@Transactional(readOnly = true)
public class ModalidadeService {

    private final ModalidadeRepository modalidadeRepository;

    public ModalidadeService(ModalidadeRepository modalidadeRepository) {
        this.modalidadeRepository = modalidadeRepository;
    }

    public List<Modalidade> listarTodas() {
        return modalidadeRepository.findAll();
    }

    public Modalidade buscarPorId(Long id) {
        return modalidadeRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Modalidade nao encontrada com id " + id));
    }

    @Transactional
    public Modalidade criar(ModalidadeRequest request) {
        if (modalidadeRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new RegraNegocioException("Ja existe uma modalidade com o nome " + request.nome());
        }
        Modalidade modalidade = new Modalidade(request.nome(), request.descricao());
        return modalidadeRepository.save(modalidade);
    }

    @Transactional
    public Modalidade atualizar(Long id, ModalidadeRequest request) {
        Modalidade modalidade = buscarPorId(id);
        modalidadeRepository.findByNomeIgnoreCase(request.nome()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new RegraNegocioException("Ja existe uma modalidade com o nome " + request.nome());
            }
        });
        modalidade.setNome(request.nome());
        modalidade.setDescricao(request.descricao());
        return modalidadeRepository.save(modalidade);
    }

    @Transactional
    public void excluir(Long id) {
        Modalidade modalidade = buscarPorId(id);
        modalidadeRepository.delete(modalidade);
    }
}
