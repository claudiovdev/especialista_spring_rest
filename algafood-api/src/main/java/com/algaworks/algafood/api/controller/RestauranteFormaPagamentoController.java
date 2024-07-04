package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.model.response.FormaPagamentoModelResponse;
import com.algaworks.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurantes/{restauranteId}/forma-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    @Autowired
    RestauranteService restauranteService;

    @Autowired
    FormaPagamentoAssembler formaPagamentoAssembler;




    @GetMapping
    public List<FormaPagamentoModelResponse> listarCozinhas(@PathVariable Long restauranteId){
        Restaurante restaurante = restauranteService.buscarRestauranteExistente(restauranteId);
        return formaPagamentoAssembler.toCollectionModelResponse(restaurante.getFormaPagamentos());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PostMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }

}
