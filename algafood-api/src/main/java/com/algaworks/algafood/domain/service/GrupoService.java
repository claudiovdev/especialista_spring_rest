package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exceptions.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GrupoService {

    private static final String MSG_GRUPO_EM_USO = "O grupo com id %d não pode ser deletada pois está em uso";

    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> listarTodos() {
        return grupoRepository.findAll();
    }

    public Grupo buscarPorId(Long grupoId) {
        return grupoRepository.findById(grupoId).orElseThrow(()-> new GrupoNaoEncontradoException(grupoId));
    }
    @Transactional
    public Grupo cadastrar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void deletar(Long grupoId) {
        try {
            grupoRepository.deleteById(grupoId);
            grupoRepository.flush();
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, grupoId));
        }catch (EmptyResultDataAccessException e){
                throw  new GrupoNaoEncontradoException(grupoId);
        }

    }
}
