package com.algaworks.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PedidoModelRequest {
    @Valid
    @NotNull
    private RestauranteIdModelRequest restaurante;
    @Valid
    @NotNull
    private FormaPagamentoIdModelRequest formaPagamento;
    @Valid
    @NotNull
    private EnderecoModelRequest endereco;
    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoModelRequest> itens;
}
