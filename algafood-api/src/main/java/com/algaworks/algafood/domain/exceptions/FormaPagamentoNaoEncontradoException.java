package com.algaworks.algafood.domain.exceptions;

public class FormaPagamentoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public FormaPagamentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public FormaPagamentoNaoEncontradoException(Long id){
        this(String.format("Não foi possivel localizar formaPagamento com codigo: %d", id));
    }
}
