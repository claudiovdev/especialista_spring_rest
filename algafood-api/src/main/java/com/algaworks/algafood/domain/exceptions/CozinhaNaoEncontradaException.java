package com.algaworks.algafood.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException{
    private static final long serialVersionUID = 1L;
    public CozinhaNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public CozinhaNaoEncontradaException(Long id){
        this(String.format("Cozinha com codigo %d não foi encontrada", id));
    }

}
