package com.algaworks.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CidadeModelRequest {
    private Long id;
    @NotBlank()
    private String nome;
    @Valid
    @NotNull
    private EstadoIdModelRequest estadoModelRequest;
}
