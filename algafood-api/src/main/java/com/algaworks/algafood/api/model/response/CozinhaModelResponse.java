package com.algaworks.algafood.api.model.response;

import com.algaworks.algafood.api.model.views.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CozinhaModelResponse {
    @ApiModelProperty(example = "1")
    @JsonView(RestauranteView.Resumido.class)
    private Long id;
    @ApiModelProperty("Rio de Janeiro")
    @JsonView(RestauranteView.Resumido.class)
    private String nome;
}
