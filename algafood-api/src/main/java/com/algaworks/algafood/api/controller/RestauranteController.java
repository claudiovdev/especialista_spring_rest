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

import java.util.List;

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
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        try {
            Restaurante restauranteEncontrado = restauranteService.buscar(restauranteId);
            return ResponseEntity.ok().body(restauranteEncontrado);
        } catch (EntidadeNaoEncontradaException e) {
           return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante){

        try {
            Restaurante restauranteAtualizado = restauranteService.atualizar(restauranteId,restaurante);
            return ResponseEntity.ok().body(restauranteAtualizado);
        }catch (RestauranteNaoEncontradoException e){
            return ResponseEntity.notFound().build();
        }catch (CozinhaNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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

}
