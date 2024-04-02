package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteModelAssember;
import com.algaworks.algafood.api.assembler.RestauranteModelRequestDisassembler;
import com.algaworks.algafood.api.model.request.RestauranteModelRequest;
import com.algaworks.algafood.api.model.response.CozinhaModel;
import com.algaworks.algafood.api.model.response.RestauranteModel;
import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
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
import java.util.stream.Collectors;

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
    public ResponseEntity<List<RestauranteModel>> listarCozinhas(){
        List<RestauranteModel> cozinhas = restauranteModelAssember.toCollectionToModel(restauranteService.listar());
        return ResponseEntity.ok().body(cozinhas);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante =   restauranteService.buscarRestauranteExistente(restauranteId);
        return restauranteModelAssember.toModel(restaurante);

    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid() RestauranteModelRequest restauranteModelRequest){
        Restaurante restaurante = restauranteModelRequestDisassembler.toDomain(restauranteModelRequest);
        return restauranteModelAssember.toModel(restauranteService.salvar(restaurante));
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable @Valid Long restauranteId, @RequestBody RestauranteModelRequest restauranteModelRequest){
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
    public List<RestauranteModel> buscarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteModelAssember.toCollectionToModel(restauranteService.buscarPorTaxaFrete(taxaInicial,taxaFinal));
    }

    @GetMapping("/nome")
    public List<RestauranteModel> buscarPorTaxaFrete(String  nome, Long cozinhaId){
        return restauranteModelAssember.toCollectionToModel(restauranteService.nomeCozinha(nome,cozinhaId));
    }

    @GetMapping("/nome-por-taxa-frete")
    public List<RestauranteModel> buscarPorNomeTaxaFrete(String nome,BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteModelAssember.toCollectionToModel(restauranteService.find(nome,taxaInicial,taxaFinal));
    }

    @GetMapping("/frete-gratis")
    public List<RestauranteModel> buscarRestauranteComFreteGratis(String nome){
        return restauranteModelAssember.toCollectionToModel(restauranteService.buscarComFreteGratis(nome));
    }

}
