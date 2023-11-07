package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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


    public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {

        Restaurante restauranteExistente = restauranteRepository.findById(restauranteId).orElseThrow(()-> new RestauranteNaoEncontradoException(String.format("Não existe restaurante com id %d", restauranteId)));
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.find(cozinhaId);
        if (cozinha == null){
            throw new CozinhaNaoEncontradaException(String.format("Não existe cozinha com id %d", cozinhaId));
        }
        BeanUtils.copyProperties(restauranteExistente, restaurante, "id");
        restaurante.setId(restauranteExistente.getId());

        return restauranteRepository.save(restaurante);
    }

    public void deletar(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow(RestauranteNaoEncontradoException:: new);
        restauranteRepository.delete(restaurante);
    }

    public Restaurante atualizarParcialmente(Long restauranteId, Map<String, Object> campos) {
        Restaurante restauranteExistente = restauranteRepository.findById(restauranteId).orElseThrow(RestauranteNaoEncontradoException::new);
        Restaurante restauranteAtualizado = merge(campos, restauranteExistente);
        return atualizar(restauranteId, restauranteAtualizado);
    }

    private Restaurante merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
        dadosOrigem.forEach((nomePropriedade, valorPropriedade) ->{
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
        return restauranteDestino;
    }

}
