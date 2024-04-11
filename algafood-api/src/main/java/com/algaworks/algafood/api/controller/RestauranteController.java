package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.RestauranteModelAssember;
import com.algaworks.algafood.api.assembler.modelDisassembler.RestauranteModelRequestDisassembler;
import com.algaworks.algafood.api.model.request.RestauranteModelRequest;
import com.algaworks.algafood.api.model.response.RestauranteModelResponse;
import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteService restauranteService;

    @Autowired
    RestauranteModelAssember restauranteModelAssember;

    @Autowired
    RestauranteModelRequestDisassembler restauranteModelRequestDisassembler;


    @GetMapping
    public ResponseEntity<List<RestauranteModelResponse>> listarCozinhas(){
        List<RestauranteModelResponse> cozinhas = restauranteModelAssember.toCollectionToModel(restauranteService.listar());
        return ResponseEntity.ok().body(cozinhas);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModelResponse buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante =   restauranteService.buscarRestauranteExistente(restauranteId);
        return restauranteModelAssember.toModel(restaurante);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModelResponse adicionar(@RequestBody @Valid() RestauranteModelRequest restauranteModelRequest){
        Restaurante restaurante = restauranteModelRequestDisassembler.toDomain(restauranteModelRequest);
        return restauranteModelAssember.toModel(restauranteService.salvar(restaurante));
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModelResponse atualizar(@PathVariable @Valid Long restauranteId, @RequestBody RestauranteModelRequest restauranteModelRequest){
        Restaurante restaurante = restauranteModelRequestDisassembler.toDomain(restauranteModelRequest);
        Restaurante atualizarRestaurante = new Restaurante();
        try {
            atualizarRestaurante = restauranteService.atualizar(restauranteId, restaurante);
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
        return restauranteModelAssember.toModel(atualizarRestaurante);
    }


    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long restauranteId){
        restauranteService.deletar(restauranteId);
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos){
        Restaurante restaurante = restauranteService.atualizarParcialmente(restauranteId, campos);
        return ResponseEntity.ok().body(restaurante);
    }

    @GetMapping("/por-taxa-frete")
    public List<RestauranteModelResponse> buscarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteModelAssember.toCollectionToModel(restauranteService.buscarPorTaxaFrete(taxaInicial,taxaFinal));
    }

    @GetMapping("/nome")
    public List<RestauranteModelResponse> buscarPorTaxaFrete(String  nome, Long cozinhaId){
        return restauranteModelAssember.toCollectionToModel(restauranteService.nomeCozinha(nome,cozinhaId));
    }

    @GetMapping("/nome-por-taxa-frete")
    public List<RestauranteModelResponse> buscarPorNomeTaxaFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteModelAssember.toCollectionToModel(restauranteService.find(nome,taxaInicial,taxaFinal));
    }

    @GetMapping("/frete-gratis")
    public List<RestauranteModelResponse> buscarRestauranteComFreteGratis(String nome){
        return restauranteModelAssember.toCollectionToModel(restauranteService.buscarComFreteGratis(nome));
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId){
        restauranteService.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId){
        restauranteService.inativar(restauranteId);
    }


    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarFechamento(@PathVariable Long restauranteId){
        restauranteService.atualizarFechamento(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarAbertura(@PathVariable Long restauranteId){
        restauranteService.atualizarAbertura(restauranteId);
    }
}
