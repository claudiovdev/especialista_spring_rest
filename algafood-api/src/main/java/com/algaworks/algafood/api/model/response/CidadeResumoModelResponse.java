package com.algaworks.algafood.api.model.response;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModelResponse {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty("Campo grande")
    private String nome;
    @ApiModelProperty(example = "Rio de Janeiro")
    private String estado;
}
