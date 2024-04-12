package com.algaworks.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class ItemPedidoModelRequest {
    @NotNull
    private Long produtoId;
    @NotNull
    @PositiveOrZero
    private Long quantidade;
    private String observacao;
}
