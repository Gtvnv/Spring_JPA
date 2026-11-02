package com.dio.academia.controller;

import com.dio.academia.domain.Plano;
import com.dio.academia.dto.PlanoRequest;
import com.dio.academia.dto.PlanoResponse;
import com.dio.academia.service.PlanoService;
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
 * Endpoints REST para o recurso Plano.
 */
@RestController
@RequestMapping("/api/planos")
@Tag(name = "Planos", description = "Gestao dos planos de assinatura da academia")
public class PlanoController {

    private final PlanoService planoService;

    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }

    @GetMapping
    @Operation(summary = "Lista os planos, opcionalmente filtrando por periodicidade")
    public List<PlanoResponse> listar(@RequestParam(required = false) Plano.Periodicidade periodicidade) {
        List<Plano> planos = (periodicidade != null)
                ? planoService.listarPorPeriodicidade(periodicidade)
                : planoService.listarTodos();
        return planos.stream().map(PlanoResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um plano por id")
    public PlanoResponse buscarPorId(@PathVariable Long id) {
        return PlanoResponse.fromEntity(planoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo plano")
    public ResponseEntity<PlanoResponse> criar(@Valid @RequestBody PlanoRequest request,
                                               UriComponentsBuilder uriBuilder) {
        PlanoResponse response = PlanoResponse.fromEntity(planoService.criar(request));
        URI uri = uriBuilder.path("/api/planos/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um plano existente")
    public PlanoResponse atualizar(@PathVariable Long id,
                                   @Valid @RequestBody PlanoRequest request) {
        return PlanoResponse.fromEntity(planoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um plano")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        planoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
