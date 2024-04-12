package com.algaworks.algafood.core.config;

import com.algaworks.algafood.api.model.request.ItemPedidoModelRequest;
import com.algaworks.algafood.api.model.response.EnderecoModelResponse;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConifg {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModelResponse.class);
        enderecoToEnderecoModelTypeMap.<String>addMapping(
                endereco -> endereco.getCidade().getEstado().getNome(),
                (destino, value) -> destino.getCidade().setEstado(value));
        modelMapper.createTypeMap(ItemPedidoModelRequest.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));


        return modelMapper;
    }
}
