package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CozinhaService {

    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha com codigo %d não foi encontrada";
    public static final String MSG_COZINHA_EM_USO = "Cozinha de codigo %d não pode ser removida pois está em uso";
    @Autowired
    CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long id){
        try {
            Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
            cozinhaRepository.delete(cozinha.get());

        }catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));

        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
        }
    }

    public Cozinha buscarCozinhaExistente(Long id){
        return cozinhaRepository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id)));
    }
}
