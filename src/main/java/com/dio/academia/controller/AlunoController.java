package com.dio.academia.controller;

import com.dio.academia.dto.AlunoRequest;
import com.dio.academia.dto.AlunoResponse;
import com.dio.academia.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Endpoints REST para o recurso Aluno.
 *
 * Demonstra o CRUD completo: criar (POST), listar/buscar (GET),
 * atualizar (PUT) e excluir (DELETE).
 */
@RestController
@RequestMapping("/api/alunos")
@Tag(name = "Alunos", description = "Gestao dos alunos da academia")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    @Operation(summary = "Lista alunos (todos, por nome, ou apenas com matricula ativa)")
    public List<AlunoResponse> listar(@RequestParam(required = false) String nome,
                                      @RequestParam(required = false, defaultValue = "false") boolean ativos) {
        if (ativos) {
            return alunoService.listarComMatriculaAtiva().stream()
                    .map(AlunoResponse::fromEntity)
                    .toList();
        }
        if (nome != null && !nome.isBlank()) {
            return alunoService.buscarPorNome(nome).stream()
                    .map(AlunoResponse::fromEntity)
                    .toList();
        }
        return alunoService.listarTodos().stream()
                .map(AlunoResponse::fromEntity)
                .toList();
    }

    @GetMapping("/paginado")
    @Operation(summary = "Lista alunos de forma paginada e ordenada "
            + "(parametros: page, size, sort). Ex.: ?page=0&size=10&sort=nome,asc")
    public Page<AlunoResponse> listarPaginado(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return alunoService.listarPaginado(pageable).map(AlunoResponse::fromEntity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um aluno por id")
    public AlunoResponse buscarPorId(@PathVariable Long id) {
        return AlunoResponse.fromEntity(alunoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo aluno")
    public ResponseEntity<AlunoResponse> criar(@Valid @RequestBody AlunoRequest request,
                                               UriComponentsBuilder uriBuilder) {
        AlunoResponse response = AlunoResponse.fromEntity(alunoService.criar(request));
        URI uri = uriBuilder.path("/api/alunos/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um aluno existente")
    public AlunoResponse atualizar(@PathVariable Long id,
                                   @Valid @RequestBody AlunoRequest request) {
        return AlunoResponse.fromEntity(alunoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um aluno")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        alunoService.excluir(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
