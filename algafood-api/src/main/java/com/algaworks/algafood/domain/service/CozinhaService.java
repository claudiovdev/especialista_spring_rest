package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CozinhaService {

    public static final String MSG_COZINHA_EM_USO = "Cozinha de codigo %d não pode ser removida pois está em uso";
    @Autowired
    CozinhaRepository cozinhaRepository;

    public Page<Cozinha> buscarTodas(Pageable pageable){
        return cozinhaRepository.findAll(pageable);
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long id){
        try {
            cozinhaRepository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
        }catch (EmptyResultDataAccessException e){
            throw new CozinhaNaoEncontradaException(id);
        }
    }

    public Cozinha buscarCozinhaExistente(Long id){
        return cozinhaRepository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }
}
