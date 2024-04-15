package com.algaworks.algafood.domain.exceptions;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public PedidoNaoEncontradoException(String codigo) {
        super(String.format("Pedido com id: %d não encontrado", codigo));
    }


}
