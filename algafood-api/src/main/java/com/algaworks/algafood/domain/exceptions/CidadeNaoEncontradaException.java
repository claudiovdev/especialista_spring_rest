package com.algaworks.algafood.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{
    private static final long serialVersionUID = 1L;

    public CidadeNaoEncontradaException(String mensagem){
        super(mensagem);
    }

    public CidadeNaoEncontradaException(Long id){
        this(String.format("Não foi possivel localizar uma cidade com codigo %d", id));
    }


}
