package com.algaworks.algafood.api.model.request;

import com.algaworks.algafood.api.model.response.CidadeResumoModelResponse;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EnderecoModelRequest {

    @NotBlank
    private String cep;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;
    @NotBlank
    private String complemento;
    @NotBlank
    private String bairro;
    @Valid
    private CidadeIdModelRequest cidade;
}
