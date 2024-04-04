package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoModelResponse {
    private Long id;

    @NotBlank()
    private String nome;
}
