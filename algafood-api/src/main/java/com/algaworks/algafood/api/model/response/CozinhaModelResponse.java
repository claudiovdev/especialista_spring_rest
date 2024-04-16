package com.algaworks.algafood.api.model.response;

import com.algaworks.algafood.api.model.views.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CozinhaModelResponse {
    @JsonView(RestauranteView.Resumido.class)
    private Long id;
    @JsonView(RestauranteView.Resumido.class)
    private String nome;
}
