package com.algaworks.algafood.infrastructure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfVendasReportService implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;
    @Override
    public byte[] emitirVendaDiaras(VendaDiariaFilter vendaDiariaFilter, String timeOffset) {
        try{
            //Aqui eu utilizo para buscar o meu arquivo pelo caminho no resource
            var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            //Aqui vou definir os parametros do relatório, como o locale
            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var vendasDiarias = vendaQueryService.consultarVendasDiarias(vendaDiariaFilter, timeOffset);

            //Aqui eu vou montar meu dataSource
            var dataSource = new JRBeanCollectionDataSource(vendasDiarias);

            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }catch (Exception e){
            throw new ReportException("Não foi possivel emitir relátorios de venda diaria", e.getCause());
        }
    }
}
