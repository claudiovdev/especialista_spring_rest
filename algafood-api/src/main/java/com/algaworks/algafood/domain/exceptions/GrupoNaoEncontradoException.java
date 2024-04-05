package com.algaworks.algafood.domain.exceptions;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public GrupoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public GrupoNaoEncontradoException(Long id){
        this(String.format("Não foi possivel localicar grupo com id: %d", id));
    }
}
