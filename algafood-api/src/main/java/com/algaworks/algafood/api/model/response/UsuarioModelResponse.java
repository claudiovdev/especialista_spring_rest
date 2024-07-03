package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class UsuarioModelResponse {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Claudio Vinicius")
    private String nome;
    @ApiModelProperty("Vinicius@gmail.com")
    private String email;
}
