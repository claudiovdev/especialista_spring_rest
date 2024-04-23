package com.algaworks.algafood.domain.exceptions;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public FotoProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId){
        this(String.format("Não foi possivel localizar foto para o restauranteId: %s e produtoId: %s", restauranteId, produtoId));
    }
}
