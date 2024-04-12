package com.algaworks.algafood.api.assembler.modelAssembler;

import com.algaworks.algafood.api.model.response.UsuarioModelResponse;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioModelAssember {
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModelResponse toModelResponse(Usuario usuario){
        return modelMapper.map(usuario, UsuarioModelResponse.class);
    }

    public List<UsuarioModelResponse> toCollectionModelResponse(Collection<Usuario> usuarios){
        return usuarios.stream().map(usuario -> toModelResponse(usuario)).collect(Collectors.toList());
    }

}
