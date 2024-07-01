package com.algaworks.algafood.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoFilter {
    @ApiModelProperty(example = "1", value = "ID do cliente para filtro da pesquisa")
    private Long clienteId;
    @ApiModelProperty(example = "1", value = "ID do restaurante para filtro da pesquisa")
    private Long restauranteId;
    @ApiModelProperty(example = "2019-11-01T10:00:00Z", value = "Data/hora de criação inicial do filtro de pesquisa")
    private OffsetDateTime dataCriacaoInicio;
    @ApiModelProperty(example = "2019-11-01T10:00:00Z", value = "Data/hora de criação final do filtro de pesquisa")
    private OffsetDateTime dataCriacaoFim;
}
