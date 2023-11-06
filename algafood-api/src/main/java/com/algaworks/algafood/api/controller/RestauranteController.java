package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
