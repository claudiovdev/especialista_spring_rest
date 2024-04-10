package com.algaworks.algafood.api.assembler.modelAssembler;

import com.algaworks.algafood.api.model.response.ProdutoModelResponse;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoModelResponse toModelResponse(Produto produto){
        return modelMapper.map(produto, ProdutoModelResponse.class);
    }

    public List<ProdutoModelResponse> toCollectionModelResponse(Collection<Produto> produtos){
        return produtos.stream().map(produto -> toModelResponse(produto)).collect(Collectors.toList());
    }

}
