package com.algaworks.algafood.domain.exceptions;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PedidoNaoEncontradoException(Long pedidoId){
        this(String.format("Pedido com id: %d não encontrado", pedidoId));
    }
}
