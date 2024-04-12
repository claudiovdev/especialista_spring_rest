package com.algaworks.algafood.api.model.response;

import com.algaworks.algafood.domain.model.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PedidoModelResponse {
    private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private EnderecoModelResponse endereco;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataCancelamento;
    private OffsetDateTime dataEntrega;
    private FormaPagamentoModelResponse formaPagamento;
    private RestauranteModelResponse restaurante;
    private UsuarioModelResponse cliente;
    private List<ItemPedidoModelResponse> itens = new ArrayList<>();
}
