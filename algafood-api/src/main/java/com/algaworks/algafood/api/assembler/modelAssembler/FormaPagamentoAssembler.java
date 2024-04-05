package com.algaworks.algafood.api.assembler.modelAssembler;

import com.algaworks.algafood.api.model.response.FormaPagamentoModelResponse;
import com.algaworks.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoModelResponse toModelResponse(FormaPagamento formaPagamento){
      return  modelMapper.map(formaPagamento, FormaPagamentoModelResponse.class);
    }

    public List<FormaPagamentoModelResponse> toCollectionModelResponse(List<FormaPagamento> formaPagamentoList){
        return formaPagamentoList.stream().map(formaPagamento -> toModelResponse(formaPagamento)).collect(Collectors.toList());
    }
}
