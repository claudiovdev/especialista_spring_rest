package com.algaworks.algafood.domain.exceptions;

public class CozinhaNaoEncontradaException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public CozinhaNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(){

    }
}
