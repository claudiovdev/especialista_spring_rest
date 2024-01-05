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
import com.fasterxml.jackson.databind.DeserializationFeature;
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

    public static final String MSG_RESTAURANTE_EM_USO = "O restaurante com id %d não pode ser deletada pois está em uso";
    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    CozinhaService cozinhaService;


    public List<Restaurante> listar(){
       return restauranteRepository.findAll2();
    }

    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException( restauranteId));
    }

    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaService.buscarCozinhaExistente(cozinhaId);
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }


    public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {

        Restaurante restauranteExistente = restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException( restauranteId));
        BeanUtils.copyProperties(restaurante, restauranteExistente, "id", "formaPagamentos",
                "endereco","dataCadastro");

        return restauranteRepository.save(restaurante);
    }

    public void deletar(Long restauranteId) {
        try {
            restauranteRepository.deleteById(restauranteId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
        }catch (EmptyResultDataAccessException e){
            throw  new RestauranteNaoEncontradoException( restauranteId);
        }
    }

    public Restaurante buscarRestauranteExistente(Long restauranteId){
        return restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException( restauranteId));
    }

    public Restaurante atualizarParcialmente(Long restauranteId, Map<String, Object> campos) {
        Restaurante restauranteExistente = restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException( restauranteId));
        merge(campos, restauranteExistente);
        return atualizar(restauranteId, restauranteExistente);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
        dadosOrigem.forEach((nomePropriedade, valorPropriedade) ->{
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
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
