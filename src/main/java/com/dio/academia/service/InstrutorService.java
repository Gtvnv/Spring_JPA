package com.dio.academia.service;

import com.dio.academia.domain.Instrutor;
import com.dio.academia.domain.Modalidade;
import com.dio.academia.dto.InstrutorRequest;
import com.dio.academia.repository.InstrutorRepository;
import com.dio.academia.repository.ModalidadeRepository;
import com.dio.academia.service.exception.RecursoNaoEncontradoException;
import com.dio.academia.service.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Regras de negocio para instrutores, incluindo a associacao com modalidades
 * (relacionamento ManyToMany).
 */
@Service
@Transactional(readOnly = true)
public class InstrutorService {

    private final InstrutorRepository instrutorRepository;
    private final ModalidadeRepository modalidadeRepository;

    public InstrutorService(InstrutorRepository instrutorRepository,
                            ModalidadeRepository modalidadeRepository) {
        this.instrutorRepository = instrutorRepository;
        this.modalidadeRepository = modalidadeRepository;
    }

    public List<Instrutor> listarTodos() {
        return instrutorRepository.buscarTodosComModalidades();
    }

    public Instrutor buscarPorId(Long id) {
        return instrutorRepository.buscarComModalidades(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Instrutor nao encontrado com id " + id));
    }

    public List<Instrutor> buscarPorModalidade(String nomeModalidade) {
        return instrutorRepository.buscarPorModalidade(nomeModalidade);
    }

    @Transactional
    public Instrutor criar(InstrutorRequest request) {
        if (instrutorRepository.existsByCref(request.cref())) {
            throw new RegraNegocioException("Ja existe um instrutor com o CREF " + request.cref());
        }
        Instrutor instrutor = new Instrutor(request.nome(), request.cref());
        instrutor.setModalidades(resolverModalidades(request.modalidadeIds()));
        return instrutorRepository.save(instrutor);
    }

    @Transactional
    public Instrutor atualizar(Long id, InstrutorRequest request) {
        Instrutor instrutor = buscarPorId(id);
        instrutorRepository.findByCref(request.cref()).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new RegraNegocioException("Ja existe um instrutor com o CREF " + request.cref());
            }
        });
        instrutor.setNome(request.nome());
        instrutor.setCref(request.cref());
        instrutor.setModalidades(resolverModalidades(request.modalidadeIds()));
        return instrutorRepository.save(instrutor);
    }

    @Transactional
    public void excluir(Long id) {
        Instrutor instrutor = buscarPorId(id);
        instrutorRepository.delete(instrutor);
    }

    /**
     * Converte uma lista de ids de modalidade nas entidades correspondentes,
     * falhando se algum id nao existir.
     */
    private Set<Modalidade> resolverModalidades(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }
        List<Modalidade> encontradas = modalidadeRepository.findAllById(ids);
        if (encontradas.size() != ids.size()) {
            throw new RegraNegocioException("Uma ou mais modalidades informadas nao existem");
        }
        return new HashSet<>(encontradas);
    }
}
