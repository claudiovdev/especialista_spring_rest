package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaModelRequest {

    @ApiModelProperty(example = "Brasileira", required = true)
    @NotBlank
    private String nome;
}
