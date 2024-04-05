package com.algaworks.algafood.domain.exceptions;

public class SenhaIncompativelException extends NegocioException{
    public SenhaIncompativelException(String mensagem) {
        super(mensagem);
    }
}
