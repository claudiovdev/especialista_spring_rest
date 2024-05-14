package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoModelResponse {
    @ApiModelProperty(example = "1")
    private Long id;
    @NotBlank()
    @ApiModelProperty(example = "RJ")
    private String nome;
}
