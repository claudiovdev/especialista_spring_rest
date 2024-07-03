package com.algaworks.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModelRequest {
    @ApiModelProperty(example = "Sushi da praça", required = true)
    @NotBlank
    private String nome;
    @ApiModelProperty(example = "12.00", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;
    @Valid
    @NotNull
    private CozinhaIdModelRequest cozinha;
    @Valid
    @NotNull
    private EnderecoModelRequest endereco;
}
