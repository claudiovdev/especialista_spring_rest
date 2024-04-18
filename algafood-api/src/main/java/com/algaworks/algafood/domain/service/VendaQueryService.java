package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import org.springframework.context.annotation.Configuration;
import java.util.List;
public interface VendaQueryService {
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeoffSet);
}
