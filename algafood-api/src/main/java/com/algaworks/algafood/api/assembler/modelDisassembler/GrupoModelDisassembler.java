package com.algaworks.algafood.api.assembler.modelDisassembler;

import com.algaworks.algafood.api.model.request.GrupoModelRequest;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;
    public Grupo toModelRequest(GrupoModelRequest grupoModelRequest) {
        return modelMapper.map(grupoModelRequest, Grupo.class);
    }

    public void copyToDomain(GrupoModelRequest grupoModelRequest, Grupo grupo){
        modelMapper.map(grupoModelRequest, grupo);
    }
}
