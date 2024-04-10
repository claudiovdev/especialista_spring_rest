package com.algaworks.algafood.domain.exceptions;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long produtoId){
        this(String.format("Produto com id: %d não foi encontrado", produtoId));
    }
}
