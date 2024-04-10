package com.algaworks.algafood.api.assembler.modelDisassembler;

import com.algaworks.algafood.api.model.request.ProdutoModelRequest;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProdutoModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Produto toDomain(ProdutoModelRequest produtoModelRequest){
        return modelMapper.map(produtoModelRequest, Produto.class);
    }

    public void toObjectProduto(ProdutoModelRequest produtoModelRequest, Produto produto){
        modelMapper.map(produtoModelRequest, produto);
    }
}
