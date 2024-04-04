package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.response.CozinhaModelResponse;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaModelResponse toModelResponse(Cozinha cozinha){
        return modelMapper.map(cozinha, CozinhaModelResponse.class);
    }

    public List<CozinhaModelResponse> toCollectionModelResponse(List<Cozinha> cozinhas){
        return cozinhas.stream().map(cozinha -> toModelResponse(cozinha)).collect(Collectors.toList());
    }

}
