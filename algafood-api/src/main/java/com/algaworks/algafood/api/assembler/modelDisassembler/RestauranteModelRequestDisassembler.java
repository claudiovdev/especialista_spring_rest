package com.algaworks.algafood.api.assembler.modelDisassembler;

import com.algaworks.algafood.api.model.request.RestauranteModelRequest;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelRequestDisassembler {

    @Autowired
    ModelMapper modelMapper;
    public Restaurante toDomain(RestauranteModelRequest restauranteModelRequest){
        return modelMapper.map(restauranteModelRequest,Restaurante.class);
    }

    public void copyToDomain(RestauranteModelRequest modelRequest, Restaurante restaurante){
        modelMapper.map(modelRequest, restaurante);
    }
}
