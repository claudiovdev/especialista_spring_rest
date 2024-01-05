package com.algaworks.algafood.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public RestauranteNaoEncontradoException(String mensagem){
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long id){
        this(String.format("Não foi possivel localizar restaurante com id %d", id));
    }
}
