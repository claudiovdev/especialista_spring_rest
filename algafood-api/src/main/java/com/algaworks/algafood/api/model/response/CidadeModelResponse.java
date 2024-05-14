package com.algaworks.algafood.api.model.response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeModelResponse {

    @ApiModelProperty(value = "Id da cidade", example = "1")
    private Long id;
    @ApiModelProperty(example = "Rio de Janeiro")
    private String nome;
    private EstadoModelResponse estado;
}
