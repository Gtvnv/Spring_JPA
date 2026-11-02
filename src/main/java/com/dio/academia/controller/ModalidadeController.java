package com.dio.academia.controller;

import com.dio.academia.dto.ModalidadeRequest;
import com.dio.academia.dto.ModalidadeResponse;
import com.dio.academia.service.ModalidadeService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Endpoints REST para o recurso Modalidade.
 */
@RestController
@RequestMapping("/api/modalidades")
@Tag(name = "Modalidades", description = "Gestao das modalidades oferecidas pela academia")
public class ModalidadeController {

    private final ModalidadeService modalidadeService;

    public ModalidadeController(ModalidadeService modalidadeService) {
        this.modalidadeService = modalidadeService;
    }

    @GetMapping
    @Operation(summary = "Lista todas as modalidades")
    public List<ModalidadeResponse> listar() {
        return modalidadeService.listarTodas().stream()
                .map(ModalidadeResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma modalidade por id")
    public ModalidadeResponse buscarPorId(@PathVariable Long id) {
        return ModalidadeResponse.fromEntity(modalidadeService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria uma nova modalidade")
    public ResponseEntity<ModalidadeResponse> criar(@Valid @RequestBody ModalidadeRequest request,
                                                    UriComponentsBuilder uriBuilder) {
        ModalidadeResponse response = ModalidadeResponse.fromEntity(modalidadeService.criar(request));
        URI uri = uriBuilder.path("/api/modalidades/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma modalidade existente")
    public ModalidadeResponse atualizar(@PathVariable Long id,
                                        @Valid @RequestBody ModalidadeRequest request) {
        return ModalidadeResponse.fromEntity(modalidadeService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma modalidade")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        modalidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
