package com.algaworks.algafood.domain.exceptions;

public class CidadeNaoEncontradaException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public CidadeNaoEncontradaException(){

    }
}
