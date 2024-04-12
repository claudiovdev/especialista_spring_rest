package com.algaworks.algafood.api.assembler.modelDisassembler;

import com.algaworks.algafood.api.model.request.PedidoModelRequest;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomain(PedidoModelRequest pedidoModelRequest){
        return modelMapper.map(pedidoModelRequest, Pedido.class);
    }
}
