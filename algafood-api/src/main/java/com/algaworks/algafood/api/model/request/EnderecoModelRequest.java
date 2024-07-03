package com.algaworks.algafood.api.model.request;

import com.algaworks.algafood.api.model.response.CidadeResumoModelResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EnderecoModelRequest {

    @ApiModelProperty(example = "38400-000", required = true)
    @NotBlank
    private String cep;
    @ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
    @NotBlank
    private String logradouro;
    @ApiModelProperty(example = "1500", required = true)
    @NotBlank
    private String numero;
    @ApiModelProperty(example = "Apto 901")
    @NotBlank
    private String complemento;
    @ApiModelProperty(example = "Centro", required = true)
    @NotBlank
    private String bairro;
    @Valid
    private CidadeIdModelRequest cidade;
}
