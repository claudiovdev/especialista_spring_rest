package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoModelRequest {
    @ApiModelProperty(example = "Cartão de crédito", required = true)
    @NotNull
    private String descricao;
}
