package com.dio.academia.controller.exception;

import com.dio.academia.service.exception.RecursoNaoEncontradoException;
import com.dio.academia.service.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Tratador global de excecoes: converte as excecoes da aplicacao em respostas
 * HTTP padronizadas, evitando vazar stack traces para o cliente.
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> tratarNaoEncontrado(
            RecursoNaoEncontradoException ex, WebRequest request) {
        return montarResposta(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, Object>> tratarRegraNegocio(
            RegraNegocioException ex, WebRequest request) {
        return montarResposta(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    /**
     * Trata as falhas de Bean Validation (@Valid), devolvendo o detalhe de
     * cada campo invalido.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> tratarValidacao(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> campos = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(erro ->
                campos.put(erro.getField(), erro.getDefaultMessage()));

        Map<String, Object> corpo = corpoBase(HttpStatus.BAD_REQUEST,
                "Erro de validacao nos campos enviados", request);
        corpo.put("campos", campos);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    private ResponseEntity<Map<String, Object>> montarResposta(
            HttpStatus status, String mensagem, WebRequest request) {
        return ResponseEntity.status(status).body(corpoBase(status, mensagem, request));
    }

    private Map<String, Object> corpoBase(HttpStatus status, String mensagem, WebRequest request) {
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("timestamp", Instant.now().toString());
        corpo.put("status", status.value());
        corpo.put("error", status.getReasonPhrase());
        corpo.put("message", mensagem);
        corpo.put("path", request.getDescription(false).replace("uri=", ""));
        return corpo;
    }
}
