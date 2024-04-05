package com.algaworks.algafood.domain.exceptions;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{
    public UsuarioNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public UsuarioNaoEncontradoException(Long usuarioId){
        this(String.format("Não foi possivel localizar usuario com id: %d", usuarioId));
    }
}
