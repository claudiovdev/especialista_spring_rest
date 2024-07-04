package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoModelRequest {
    @ApiModelProperty(example = "1", required = true)
    private Long id;
    @ApiModelProperty(example = "Rio de Janeiro", required = true)
    private String nome;
}
