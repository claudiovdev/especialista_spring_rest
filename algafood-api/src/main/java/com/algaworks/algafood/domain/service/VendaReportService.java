package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import net.sf.jasperreports.engine.JRException;

public interface VendaReportService {

    byte[] emitirVendaDiaras(VendaDiariaFilter vendaDiariaFilter, String timeOffset) throws JRException;
}
