package com.dio.academia.service;

import com.dio.academia.domain.Aluno;
import com.dio.academia.domain.Matricula;
import com.dio.academia.domain.Plano;
import com.dio.academia.dto.MatriculaRequest;
import com.dio.academia.repository.AlunoRepository;
import com.dio.academia.repository.MatriculaRepository;
import com.dio.academia.repository.PlanoRepository;
import com.dio.academia.service.exception.RecursoNaoEncontradoException;
import com.dio.academia.service.exception.RegraNegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Regras de negocio para matriculas.
 *
 * Mostra como usar os repositorios de entidades relacionadas (Aluno e Plano)
 * para montar uma nova Matricula.
 */
@Service
@Transactional(readOnly = true)
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final PlanoRepository planoRepository;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            AlunoRepository alunoRepository,
                            PlanoRepository planoRepository) {
        this.matriculaRepository = matriculaRepository;
        this.alunoRepository = alunoRepository;
        this.planoRepository = planoRepository;
    }

    public List<Matricula> listarTodas() {
        return matriculaRepository.findAll();
    }

    public List<Matricula> listarPorAluno(Long alunoId) {
        return matriculaRepository.findByAlunoId(alunoId);
    }

    public List<Object[]> contarPorPlano() {
        return matriculaRepository.contarMatriculasPorPlano();
    }

    @Transactional
    public Matricula matricular(MatriculaRequest request) {
        Aluno aluno = alunoRepository.findById(request.alunoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Aluno nao encontrado com id " + request.alunoId()));
        Plano plano = planoRepository.findById(request.planoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Plano nao encontrado com id " + request.planoId()));

        if (matriculaRepository.existsByAlunoIdAndStatus(aluno.getId(), Matricula.Status.ATIVA)) {
            throw new RegraNegocioException("O aluno ja possui uma matricula ativa");
        }

        Matricula matricula = new Matricula(plano, request.dataInicio(), Matricula.Status.ATIVA);
        aluno.adicionarMatricula(matricula);
        return matriculaRepository.save(matricula);
    }

    @Transactional
    public Matricula alterarStatus(Long id, Matricula.Status novoStatus) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Matricula nao encontrada com id " + id));
        matricula.setStatus(novoStatus);
        return matriculaRepository.save(matricula);
    }

    @Transactional
    public void cancelar(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Matricula nao encontrada com id " + id));
        matriculaRepository.delete(matricula);
    }
}
