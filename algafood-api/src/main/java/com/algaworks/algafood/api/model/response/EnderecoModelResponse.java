package com.algaworks.algafood.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModelResponse {

    @ApiModelProperty(example = "38400-000")
    private String cep;
    @ApiModelProperty(example = "Rua Floriano Peixoto")
    private String logradouro;
    @ApiModelProperty(example = "15")
    private String numero;
    @ApiModelProperty(example = "Marques")
    private String complemento;
    @ApiModelProperty("Moderno")
    private String bairro;
    private CidadeResumoModelResponse cidade;
}
