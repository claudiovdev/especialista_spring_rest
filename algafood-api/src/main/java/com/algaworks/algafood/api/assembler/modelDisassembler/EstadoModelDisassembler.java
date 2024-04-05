package com.algaworks.algafood.api.assembler.modelDisassembler;

import com.algaworks.algafood.api.model.request.EstadoModelRequest;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomain(EstadoModelRequest estadoModelRequest){
        return modelMapper.map(estadoModelRequest, Estado.class);
    }
}
