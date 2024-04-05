package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.response.GrupoModelResponse;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoModelAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public GrupoModelResponse toModelResponse(Grupo grupo){
        return modelMapper.map(grupo, GrupoModelResponse.class);
    }
    public List<GrupoModelResponse> toCollectionModelResponse(List<Grupo> grupos) {
        return grupos.stream().map(grupo -> toModelResponse(grupo)).collect(Collectors.toList());
    }
}
