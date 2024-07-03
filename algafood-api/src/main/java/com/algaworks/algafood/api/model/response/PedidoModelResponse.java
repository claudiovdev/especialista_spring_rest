package com.algaworks.algafood.api.model.response;

import com.algaworks.algafood.domain.model.*;
import io.swagger.annotations.ApiModelProperty;
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
public class    PedidoModelResponse {
    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;
    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;
    @ApiModelProperty(example = "298.90")
    private BigDecimal taxaFrete;
    @ApiModelProperty(example = "298.90")
    private BigDecimal valorTotal;
    private EnderecoModelResponse endereco;
    @ApiModelProperty(example = "CRIADO")
    private String status;
    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCriacao;
    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataConfirmacao;
    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataCancelamento;
    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime dataEntrega;
    private FormaPagamentoModelResponse formaPagamento;
    private RestauranteModelResponse restaurante;
    private UsuarioModelResponse cliente;
    private List<ItemPedidoModelResponse> itens = new ArrayList<>();
}
