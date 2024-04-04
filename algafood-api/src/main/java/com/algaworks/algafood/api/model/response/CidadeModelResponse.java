package com.algaworks.algafood.api.model.response;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CidadeModelResponse {

    private Long id;


    private String nome;


    private EstadoModelResponse estado;
}
