package com.algaworks.algafood.domain.exceptions;

public class FormaPagamentoInvalidaException extends NegocioException{
    public FormaPagamentoInvalidaException(String mensagem) {
        super(String.format("Forma de pagamento %s não é aceita para este restaurante"));
    }
}
