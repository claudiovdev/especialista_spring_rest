package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.dto.VendaDiaria;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.infrastructure.service.report.PdfVendasReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    PdfVendasReportService pdfVendasReportService;

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(@RequestParam(required = false, defaultValue = "+00:00") String timeoffSet, VendaDiariaFilter vendaDiariaFilter){
        return vendaQueryService.consultarVendasDiarias(vendaDiariaFilter,timeoffSet);
    }
    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(@RequestParam(required = false, defaultValue = "+00:00") String timeoffSet, VendaDiariaFilter vendaDiariaFilter){
        byte[] bytes = pdfVendasReportService.emitirVendaDiaras(vendaDiariaFilter, timeoffSet);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytes);
    }
}
