package com.dio.academia.service.exception;

/**
 * Lancada quando uma regra de negocio e violada (ex.: e-mail ja cadastrado).
 * Mapeada para HTTP 409 (Conflict) pelo tratador global de excecoes.
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
