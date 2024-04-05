package com.algaworks.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenhaUsuarioModelRequest {
    private String senhaAntiga;
    private String novaSenha;
}
