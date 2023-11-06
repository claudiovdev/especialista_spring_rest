package com.algaworks.algafood.domain.exceptions;

public class EstadoNaoEncontradoException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EstadoNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public EstadoNaoEncontradoException(){

    }
}
