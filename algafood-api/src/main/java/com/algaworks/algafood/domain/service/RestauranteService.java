package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.exceptions.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class RestauranteService {

    public static final String MSG_RESTAURANTE_EM_USO = "O restaurante com id %d não pode ser deletada pois está em uso";
    @Autowired
    RestauranteRepository restauranteRepository;
    @Autowired
    CozinhaService cozinhaService;

    @Autowired
    CidadeService cidadeService;

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    private SmartValidator validator;
    @Autowired
    private UsuarioService usuarioService;

    public List<Restaurante> listar(){
       return restauranteRepository.findAll2();
    }

    public Restaurante buscar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException( restauranteId));
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante){
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaService.buscarCozinhaExistente(cozinhaId);
        Cidade cidade = cidadeService.buscar(restaurante.getEndereco().getCidade().getId());
        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);
        return restauranteRepository.save(restaurante);
    }


    @Transactional
    public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {
        Restaurante restauranteExistente = restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException( restauranteId));
        cozinhaService.buscarCozinhaExistente(restaurante.getCozinha().getId());
       cidadeService.buscar(restaurante.getEndereco().getCidade().getId());
        BeanUtils.copyProperties(restaurante, restauranteExistente, "id", "formaPagamentos","dataCadastro");
        validate(restauranteExistente, "restaurante");

        return restauranteRepository.save(restaurante);
    }
    @Transactional
    public void ativar(Long restauranteId){
        Restaurante restaurante = buscarRestauranteExistente(restauranteId);
        restaurante.ativar();
    }
    @Transactional
    public void inativar(Long restauranteId){
        Restaurante restaurante = buscarRestauranteExistente(restauranteId);
        restaurante.inativar();
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

    @Transactional
    public Restaurante atualizarParcialmente(Long restauranteId, Map<String, Object> campos) {
        Restaurante restauranteExistente = restauranteRepository.findById(restauranteId).orElseThrow(() -> new RestauranteNaoEncontradoException( restauranteId));
        merge(campos, restauranteExistente);
        validate(restauranteExistente, "restaurante");
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

    @Transactional
    public void desassociarFormaPagamento(Long restauranteId, Long formapagamentoId){
        Restaurante restaurante = buscarRestauranteExistente(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscar(formapagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }
    @Transactional
    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarRestauranteExistente(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscar(formaPagamentoId);

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    private void validate(Restaurante restaurante, String objectName){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
        validator.validate(restaurante, bindingResult);

        if (bindingResult.hasErrors()){
            throw new ValidacaoException(bindingResult);
        }
    }


    public List<Produto> listarProdutos(Long restauranteId) {
        Restaurante restaurante = buscarRestauranteExistente(restauranteId);
        return produtoService.buscarTodosProdutos(restaurante);
    }
    public List<Produto> listarProdutosAtivos(Long restauranteId) {
        Restaurante restaurante = buscarRestauranteExistente(restauranteId);
        return produtoService.busarProdutosAtivos(restaurante);
    }

    public Produto buscarProdutoPorRestaurante(Long restauranteId, Long produtoId) {
        Restaurante restaurante = buscarRestauranteExistente(restauranteId);
        return produtoService.buscarProdutoExistente(produtoId, restauranteId);
    }

    @Transactional
    public void atualizarFechamento(Long restauranteId) {
      var restaurante =  buscarRestauranteExistente(restauranteId);
      restaurante.setAberto(false);
    }

    @Transactional
    public void atualizarAbertura(Long restauranteId) {
        var restaurante = buscarRestauranteExistente(restauranteId);
        restaurante.setAberto(true);
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarRestauranteExistente(restauranteId);
        Usuario usuario = usuarioService.buscarUsuarioExistente(usuarioId);

        restaurante.getUsuarios().add(usuario);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarRestauranteExistente(restauranteId);
        Usuario usuario = usuarioService.buscarUsuarioExistente(usuarioId);

        restaurante.getUsuarios().remove(usuario);
    }

    @Transactional
    public void ativarRestaurantes(List<Long> restauranteIds) {
        try {
            restauranteIds.forEach(this::ativar);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }
    @Transactional
    public void inativarRestaurantes(List<Long> restauranteIds) {
        try {
            restauranteIds.forEach(this::inativar);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
    }
}
