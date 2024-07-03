package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FormaPagamentoIdModelRequest {
    @ApiModelProperty(example = "1", required = true)
    @NotNull
    Long id;
}
