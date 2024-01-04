package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("restaurantes")
public class RestauranteController {

    @Autowired
    RestauranteService restauranteService;


    @GetMapping
    public ResponseEntity<List<Restaurante>> listarCozinhas(){
        List<Restaurante> cozinhas = restauranteService.listar();
        return ResponseEntity.ok().body(cozinhas);
    }

    @GetMapping("/{restauranteId}")
    public Restaurante buscar(@PathVariable Long restauranteId) {
        return restauranteService.buscarRestauranteExistente(restauranteId);
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
        try {
            Restaurante restauranteSalvo = restauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteSalvo);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){
        return restauranteService.atualizar(restauranteId, restaurante);
    }


    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> deletar(Long restauranteId){
        try{
            restauranteService.deletar(restauranteId);
           return ResponseEntity.noContent().build();
        }catch (RestauranteNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos){
        Restaurante restaurante = restauranteService.atualizarParcialmente(restauranteId, campos);
        return ResponseEntity.ok().body(restaurante);
    }

    @GetMapping("/por-taxa-frete")
    public List<Restaurante> buscarPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteService.buscarPorTaxaFrete(taxaInicial,taxaFinal);
    }

    @GetMapping("/nome")
    public List<Restaurante> buscarPorTaxaFrete(String  nome, Long cozinhaId){
        return restauranteService.nomeCozinha(nome,cozinhaId);
    }

    @GetMapping("/nome-por-taxa-frete")
    public List<Restaurante> buscarPorNomeTaxaFrete(String nome,BigDecimal taxaInicial, BigDecimal taxaFinal){
        return restauranteService.find(nome,taxaInicial,taxaFinal);
    }

    @GetMapping("/frete-gratis")
    public List<Restaurante> buscarRestauranteComFreteGratis(String nome){
        return restauranteService.buscarComFreteGratis(nome);
    }

}
