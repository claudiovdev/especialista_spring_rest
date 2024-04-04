package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.request.FormaPagamentoModelRequest;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toDomain(FormaPagamentoModelRequest formaPagamentoModelRequest){
        return modelMapper.map(formaPagamentoModelRequest, FormaPagamento.class);
    }
}
