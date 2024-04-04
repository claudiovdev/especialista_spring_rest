package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.response.RestauranteModelResponse;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssember {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteModelResponse toModel(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteModelResponse.class);
    }

    public List<RestauranteModelResponse> toCollectionToModel(List<Restaurante> restaurantes){
        return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
    }
}
