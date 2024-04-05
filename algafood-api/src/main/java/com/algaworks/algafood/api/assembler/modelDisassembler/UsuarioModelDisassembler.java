package com.algaworks.algafood.api.assembler.modelDisassembler;

import com.algaworks.algafood.api.model.request.UsuarioModelRequest;
import com.algaworks.algafood.api.model.request.UsuarioUpdateModelRequest;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomain(UsuarioModelRequest usuarioModelRequest){
        return modelMapper.map(usuarioModelRequest, Usuario.class);
    }


    public void atualizar(UsuarioUpdateModelRequest novoUsuario, Usuario usuarioExistente){
         modelMapper.map(novoUsuario, usuarioExistente);
    }
}
