package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {
    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    CozinhaRepository cozinhaRepository;

    public List<Restaurante> listar(){
       return restauranteRepository.findAll();
    }

    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId).orElseThrow(()-> new EntidadeNaoEncontradaException(String.format("Não encontramos o restaurante com id: %d", restauranteId)));
    }

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.find(cozinhaId);

        if (cozinha == null){
            throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com codigo %d", cozinhaId));
        }

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }
}
