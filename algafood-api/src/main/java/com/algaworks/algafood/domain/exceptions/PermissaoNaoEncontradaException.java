package com.algaworks.algafood.domain.exceptions;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException{
    public PermissaoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    public PermissaoNaoEncontradaException(Long permissaoId){
        this(String.format("Permissao com id: %d não foi encontrada"));
    }
}
