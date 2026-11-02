package com.dio.academia.service.exception;

/**
 * Lancada quando um recurso solicitado nao existe no banco de dados.
 * Mapeada para HTTP 404 pelo tratador global de excecoes.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
