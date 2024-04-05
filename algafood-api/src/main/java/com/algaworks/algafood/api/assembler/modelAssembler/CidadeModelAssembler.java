package com.algaworks.algafood.api.assembler.modelAssembler;

import com.algaworks.algafood.api.model.response.CidadeModelResponse;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeModelResponse toModelResponse(Cidade cidade){
        return modelMapper.map(cidade, CidadeModelResponse.class);
    }

    public List<CidadeModelResponse> toCollectionModelResponse(List<Cidade> cidades){
        return cidades.stream().map(cidade -> toModelResponse(cidade)).collect(Collectors.toList());
    }
}
