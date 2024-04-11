package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermisaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    @Autowired
    PermisaoRepository permisaoRepository;

    public Permissao buscarPermissaoExistente(Long permissaoId){
        return permisaoRepository.findById(permissaoId).orElseThrow(
                () -> new PermissaoNaoEncontradaException(permissaoId));
    }
}
