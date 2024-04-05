package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class UsuarioModelResponse {

    private Long id;
    private String nome;
    private String email;
}
