package com.algaworks.algafood.api.assembler.modelAssembler;

import com.algaworks.algafood.api.model.response.FotoProdutoModelResponse;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoModelResponse toModelResponse(FotoProduto fotoProduto){
        return modelMapper.map(fotoProduto, FotoProdutoModelResponse.class);
    }
}
