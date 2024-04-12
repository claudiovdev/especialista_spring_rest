package com.algaworks.algafood.api.assembler.modelAssembler;

import com.algaworks.algafood.api.model.response.PedidoModelResponse;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoModelResponse toModelResponse(Pedido pedido){
        return modelMapper.map(pedido, PedidoModelResponse.class);
    }

    public List<PedidoModelResponse> toCollectionModelResponse(List<Pedido> pedidos){
        return pedidos.stream().map(this::toModelResponse).collect(Collectors.toList());
    }
}
