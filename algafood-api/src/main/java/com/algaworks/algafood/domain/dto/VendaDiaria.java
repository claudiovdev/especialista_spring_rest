package com.algaworks.algafood.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class VendaDiaria {
    private Date data;
    private Long totalVendas;
    private BigDecimal totalFaturado;
}
