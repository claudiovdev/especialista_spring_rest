package com.algaworks.algafood.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CozinhaNaoEncontradaException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public CozinhaNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(){

    }
}
