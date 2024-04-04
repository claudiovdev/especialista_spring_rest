package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteModelResponse {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaModelResponse cozinha;



}