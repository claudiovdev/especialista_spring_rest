package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.RestauranteModelAssember;
import com.algaworks.algafood.api.assembler.modelAssembler.UsuarioModelAssember;
import com.algaworks.algafood.api.model.response.UsuarioModelResponse;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioController {

    @Autowired
    RestauranteService restauranteService;

    @Autowired
    RestauranteModelAssember restauranteModelAssember;

    @Autowired
    UsuarioModelAssember usuarioModelAssember;

    @GetMapping
    public List<UsuarioModelResponse> buscaResponsaveis(@PathVariable Long restauranteId){
        Restaurante restaurante = restauranteService.buscarRestauranteExistente(restauranteId);
        return usuarioModelAssember.toCollectionModelResponse(restaurante.getUsuarios());
    }

    @PutMapping("/{usuarioId}/associar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        restauranteService.associarResponsavel(restauranteId, usuarioId);
    }

    @DeleteMapping("/{usuarioId}/desassociar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }
}
