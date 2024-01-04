package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RestauranteService {
    public static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "Não foi possivel localizar restaurante com id %d";
    public static final String MSG_COZINHA_NAO_ENCONTRADA = "Não foi possivel localizar cozinha com id %d";
    public static final String MSG_RESTAURANTE_EM_USO = "O restaurante com id %d não pode ser deletada pois está em uso";
    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    CozinhaRepository cozinhaRepository;


    public List<Restaurante> listar(){
       return restauranteRepository.findAll2();
    }

    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
    }

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId)));

        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }


    public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {

        Restaurante restauranteExistente = restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, cozinhaId)));
        BeanUtils.copyProperties(restauranteExistente, restaurante, "id", "formaPagamentos",
                "endereco","dataCadastro");
        restaurante.setId(restauranteExistente.getId());

        return restauranteRepository.save(restaurante);
    }

    public void deletar(Long restauranteId) {
        try {
            restauranteRepository.deleteById(restauranteId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
        }catch (EmptyResultDataAccessException e){
            throw new RestauranteNaoEncontradoException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId));
        }
    }

    public Restaurante buscarRestauranteExistente(Long restauranteId){
        return restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
    }

    public Restaurante atualizarParcialmente(Long restauranteId, Map<String, Object> campos) {
        Restaurante restauranteExistente = restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, restauranteId)));
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

    public List<Restaurante> buscarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    public List<Restaurante> nomeCozinha(String nome, Long cozinhaId) {
        return restauranteRepository.findByNomeContainingAndCozinhaId(nome, cozinhaId);
    }

    public List<Restaurante> find(String nome,BigDecimal taxaInicial, BigDecimal taxaFinal ){
       return restauranteRepository.find(nome,taxaInicial,taxaFinal);
    }

    public List<Restaurante> buscarComFreteGratis(String nome) {
        return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(RestauranteSpecs.comNomeSemelhante(nome)));
    }
}
