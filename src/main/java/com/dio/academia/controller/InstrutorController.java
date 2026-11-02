package com.dio.academia.controller;

import com.dio.academia.dto.InstrutorRequest;
import com.dio.academia.dto.InstrutorResponse;
import com.dio.academia.service.InstrutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
 * Endpoints REST para o recurso Instrutor.
 */
@RestController
@RequestMapping("/api/instrutores")
@Tag(name = "Instrutores", description = "Gestao dos instrutores e suas modalidades")
public class InstrutorController {

    private final InstrutorService instrutorService;

    public InstrutorController(InstrutorService instrutorService) {
        this.instrutorService = instrutorService;
    }

    @GetMapping
    @Operation(summary = "Lista instrutores, opcionalmente filtrando por modalidade")
    public List<InstrutorResponse> listar(@RequestParam(required = false) String modalidade) {
        if (modalidade != null && !modalidade.isBlank()) {
            return instrutorService.buscarPorModalidade(modalidade).stream()
                    .map(InstrutorResponse::fromEntity)
                    .toList();
        }
        return instrutorService.listarTodos().stream()
                .map(InstrutorResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um instrutor por id")
    public InstrutorResponse buscarPorId(@PathVariable Long id) {
        return InstrutorResponse.fromEntity(instrutorService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo instrutor, associando-o a modalidades existentes")
    public ResponseEntity<InstrutorResponse> criar(@Valid @RequestBody InstrutorRequest request,
                                                   UriComponentsBuilder uriBuilder) {
        InstrutorResponse response = InstrutorResponse.fromEntity(instrutorService.criar(request));
        URI uri = uriBuilder.path("/api/instrutores/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um instrutor e suas modalidades")
    public InstrutorResponse atualizar(@PathVariable Long id,
                                       @Valid @RequestBody InstrutorRequest request) {
        return InstrutorResponse.fromEntity(instrutorService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um instrutor")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        instrutorService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
