package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.Groups;
import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.exceptions.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteService restauranteService;


    @GetMapping
    public ResponseEntity<List<RestauranteModel>> listarCozinhas(){
        List<RestauranteModel> cozinhas = toCollectionToModel(restauranteService.listar());
        return ResponseEntity.ok().body(cozinhas);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante =   restauranteService.buscarRestauranteExistente(restauranteId);
        return toModel(restaurante);

    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid() Restaurante restaurante){
        return toModel(restauranteService.salvar(restaurante));
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable @Valid Long restauranteId, @RequestBody Restaurante restaurante){
        Restaurante atualizarRestaurante = new Restaurante();
        try {
            atualizarRestaurante = restauranteService.atualizar(restauranteId, restaurante);
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }
        return toModel(atualizarRestaurante);
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
    public List<RestauranteModel> buscarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
        return toCollectionToModel(restauranteService.buscarPorTaxaFrete(taxaInicial,taxaFinal));
    }

    @GetMapping("/nome")
    public List<RestauranteModel> buscarPorTaxaFrete(String  nome, Long cozinhaId){
        return toCollectionToModel(restauranteService.nomeCozinha(nome,cozinhaId));
    }

    @GetMapping("/nome-por-taxa-frete")
    public List<RestauranteModel> buscarPorNomeTaxaFrete(String nome,BigDecimal taxaInicial, BigDecimal taxaFinal){
        return toCollectionToModel(restauranteService.find(nome,taxaInicial,taxaFinal));
    }

    @GetMapping("/frete-gratis")
    public List<RestauranteModel> buscarRestauranteComFreteGratis(String nome){
        return toCollectionToModel(restauranteService.buscarComFreteGratis(nome));
    }

    private static RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteModel.setCozinha(cozinhaModel);
        return restauranteModel;
    }

    private List<RestauranteModel> toCollectionToModel(List<Restaurante> restaurantes){
        return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
    }

}
