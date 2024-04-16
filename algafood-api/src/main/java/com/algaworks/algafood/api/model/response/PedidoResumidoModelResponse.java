package com.algaworks.algafood.api.model.response;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

//@JsonFilter("pedidoFilter")
@Getter
@Setter
public class PedidoResumidoModelResponse {
    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteModelResponse restaurante;
    private UsuarioModelResponse cliente;
}
