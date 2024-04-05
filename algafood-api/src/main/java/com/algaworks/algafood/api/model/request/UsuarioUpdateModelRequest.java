package com.algaworks.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class UsuarioUpdateModelRequest {

    @NotBlank
    private String nome;

    @Email
    private String email;
}
