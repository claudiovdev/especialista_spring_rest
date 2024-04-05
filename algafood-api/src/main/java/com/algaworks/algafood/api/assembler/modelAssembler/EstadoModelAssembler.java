package com.algaworks.algafood.api.assembler.modelAssembler;

import com.algaworks.algafood.api.model.response.EstadoModelResponse;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoModelResponse toModelResponse(Estado estado){
        return modelMapper.map(estado, EstadoModelResponse.class);
    }

    public List<EstadoModelResponse> toCollectionModelResponse(List<Estado> estados){
        return estados.stream().map(estado -> toModelResponse(estado)).collect(Collectors.toList());
    }
}
