package com.dio.academia.service;

import com.dio.academia.domain.Aluno;
import com.dio.academia.domain.Endereco;
import com.dio.academia.domain.Matricula;
import com.dio.academia.dto.AlunoRequest;
import com.dio.academia.dto.EnderecoDTO;
import com.dio.academia.repository.AlunoRepository;
import com.dio.academia.service.exception.RecursoNaoEncontradoException;
import com.dio.academia.service.exception.RegraNegocioException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Regras de negocio para o cadastro de alunos.
 *
 * O {@link Transactional} garante que cada operacao de escrita rode dentro de
 * uma transacao; as leituras usam transacoes somente-leitura.
 */
@Service
@Transactional(readOnly = true)
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    /**
     * Lista alunos de forma paginada e ordenada. O {@link Pageable} e resolvido
     * automaticamente a partir dos parametros page, size e sort da requisicao.
     */
    public Page<Aluno> listarPaginado(Pageable pageable) {
        return alunoRepository.findAll(pageable);
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Aluno nao encontrado com id " + id));
    }

    public List<Aluno> buscarPorNome(String trecho) {
        return alunoRepository.findByNomeContainingIgnoreCase(trecho);
    }

    public List<Aluno> listarComMatriculaAtiva() {
        return alunoRepository.buscarPorStatusMatricula(Matricula.Status.ATIVA);
    }

    @Transactional
    public Aluno criar(AlunoRequest request) {
        validarUnicidade(request.email(), request.cpf(), null);

        Aluno aluno = new Aluno(
                request.nome(),
                request.email(),
                request.cpf(),
                request.dataNascimento()
        );
        aplicarEndereco(aluno, request.endereco());
        return alunoRepository.save(aluno);
    }

    @Transactional
    public Aluno atualizar(Long id, AlunoRequest request) {
        Aluno aluno = buscarPorId(id);
        validarUnicidade(request.email(), request.cpf(), id);

        aluno.setNome(request.nome());
        aluno.setEmail(request.email());
        aluno.setCpf(request.cpf());
        aluno.setDataNascimento(request.dataNascimento());
        aplicarEndereco(aluno, request.endereco());
        return alunoRepository.save(aluno);
    }

    @Transactional
    public void excluir(Long id) {
        Aluno aluno = buscarPorId(id);
        alunoRepository.delete(aluno);
    }

    private void aplicarEndereco(Aluno aluno, EnderecoDTO dto) {
        if (dto == null) {
            aluno.setEndereco(null);
            return;
        }
        aluno.setEndereco(new Endereco(dto.cep(), dto.logradouro(), dto.cidade(), dto.uf()));
    }

    /**
     * Garante que e-mail e CPF sejam unicos. Ao atualizar, ignora o proprio
     * registro (idAtual).
     */
    private void validarUnicidade(String email, String cpf, Long idAtual) {
        alunoRepository.findByEmailIgnoreCase(email).ifPresent(existente -> {
            if (!existente.getId().equals(idAtual)) {
                throw new RegraNegocioException("Ja existe um aluno com o e-mail " + email);
            }
        });
        alunoRepository.findByCpf(cpf).ifPresent(existente -> {
            if (!existente.getId().equals(idAtual)) {
                throw new RegraNegocioException("Ja existe um aluno com o CPF " + cpf);
            }
        });
    }
}
