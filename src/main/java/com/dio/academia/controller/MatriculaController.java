package com.dio.academia.controller;

import com.dio.academia.domain.Matricula;
import com.dio.academia.dto.MatriculaRequest;
import com.dio.academia.dto.MatriculaResponse;
import com.dio.academia.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Endpoints REST para o recurso Matricula.
 */
@RestController
@RequestMapping("/api/matriculas")
@Tag(name = "Matriculas", description = "Matriculas de alunos em planos")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    @GetMapping
    @Operation(summary = "Lista matriculas (todas ou de um aluno especifico)")
    public List<MatriculaResponse> listar(@RequestParam(required = false) Long alunoId) {
        List<Matricula> matriculas = (alunoId != null)
                ? matriculaService.listarPorAluno(alunoId)
                : matriculaService.listarTodas();
        return matriculas.stream().map(MatriculaResponse::fromEntity).toList();
    }

    /**
     * Relatorio: quantidade de matriculas por plano.
     */
    @GetMapping("/relatorio/por-plano")
    public List<Map<String, Object>> relatorioPorPlano() {
        return matriculaService.contarPorPlano().stream()
                .map(linha -> {
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("plano", linha[0]);
                    item.put("quantidade", linha[1]);
                    return item;
                })
                .toList();
    }

    @PostMapping
    public ResponseEntity<MatriculaResponse> matricular(@Valid @RequestBody MatriculaRequest request,
                                                        UriComponentsBuilder uriBuilder) {
        MatriculaResponse response = MatriculaResponse.fromEntity(matriculaService.matricular(request));
        URI uri = uriBuilder.path("/api/matriculas/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{id}/status")
    public MatriculaResponse alterarStatus(@PathVariable Long id,
                                           @RequestParam Matricula.Status status) {
        return MatriculaResponse.fromEntity(matriculaService.alterarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        matriculaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }
}
