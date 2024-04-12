package com.algaworks.algafood.api.model.response;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
@Getter
@Setter
public class ItemPedidoModelResponse {
    private Long id;
    private Long produtoId;
    private String produtoNome;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private Integer quantidade;
    private String observacao;
}
