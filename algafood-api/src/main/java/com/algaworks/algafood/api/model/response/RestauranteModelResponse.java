package com.algaworks.algafood.api.model.response;

import com.algaworks.algafood.api.model.views.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModelResponse {

    @JsonView({RestauranteView.Resumido.class, RestauranteView.ApenasNome.class})
    private Long id;
    @JsonView({RestauranteView.Resumido.class, RestauranteView.ApenasNome.class})
    private String nome;
    @JsonView(RestauranteView.Resumido.class)
    private BigDecimal taxaFrete;
    @JsonView(RestauranteView.Resumido.class)
    private CozinhaModelResponse cozinha;
    private EnderecoModelResponse endereco;
    private boolean ativo;
    private boolean aberto;


}
