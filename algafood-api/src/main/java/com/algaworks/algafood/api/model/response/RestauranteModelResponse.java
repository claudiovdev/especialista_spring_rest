package com.algaworks.algafood.api.model.response;

import com.algaworks.algafood.api.model.views.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModelResponse {

    @ApiModelProperty("1")
    @JsonView({RestauranteView.Resumido.class, RestauranteView.ApenasNome.class})
    private Long id;
    @ApiModelProperty("Mineiro")
    @JsonView({RestauranteView.Resumido.class, RestauranteView.ApenasNome.class})
    private String nome;
    @ApiModelProperty("30.05")
    @JsonView(RestauranteView.Resumido.class)
    private BigDecimal taxaFrete;
    @JsonView(RestauranteView.Resumido.class)
    private CozinhaModelResponse cozinha;
    private EnderecoModelResponse endereco;
    @ApiModelProperty(example = "true")
    private boolean ativo;
    @ApiModelProperty(example = "False")
    private boolean aberto;


}
