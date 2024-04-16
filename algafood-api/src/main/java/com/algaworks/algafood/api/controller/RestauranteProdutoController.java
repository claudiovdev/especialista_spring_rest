package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.ProdutoModelAssembler;
import com.algaworks.algafood.api.assembler.modelAssembler.RestauranteModelAssember;
import com.algaworks.algafood.api.assembler.modelDisassembler.ProdutoModelDisassembler;
import com.algaworks.algafood.api.assembler.modelDisassembler.RestauranteModelRequestDisassembler;
import com.algaworks.algafood.api.model.request.ProdutoModelRequest;
import com.algaworks.algafood.api.model.request.RestauranteModelRequest;
import com.algaworks.algafood.api.model.response.ProdutoModelResponse;
import com.algaworks.algafood.api.model.response.RestauranteModelResponse;
import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    RestauranteService restauranteService;

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    ProdutoModelDisassembler produtoModelDisassembler;

    @GetMapping()
    List<ProdutoModelResponse> listar(@PathVariable Long restauranteId,
                                      @RequestParam(required = false) boolean incluirAtivos){
        List<Produto> produtos = new ArrayList<>();
        if (incluirAtivos){
            produtos = restauranteService.listarProdutos(restauranteId);
        }else {
            produtos = restauranteService.listarProdutosAtivos(restauranteId);
        }
        return produtoModelAssembler.toCollectionModelResponse(produtos);
    }

    @GetMapping("/{produtoId}")
     public ProdutoModelResponse buscarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        return produtoModelAssembler.toModelResponse(restauranteService.buscarProdutoPorRestaurante(restauranteId, produtoId));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoModelResponse cadastrar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoModelRequest produtoModelRequest){
        Restaurante restaurante = restauranteService.buscarRestauranteExistente(restauranteId);
        Produto produto = produtoModelDisassembler.toDomain(produtoModelRequest);
        produto.setRestaurante(restaurante);
        return  produtoModelAssembler.toModelResponse(produtoService.salvarProduto(produto));
    }

    @PutMapping("/{produtoId}")
    public ProdutoModelResponse atualizarProduto(@PathVariable Long restauranteId,@PathVariable Long produtoId,
                                                 @RequestBody @Valid ProdutoModelRequest produtoModelRequest){
        Produto produtoAtual = restauranteService.buscarProdutoPorRestaurante(restauranteId, produtoId);
        produtoModelDisassembler.toObjectProduto(produtoModelRequest, produtoAtual);
        return produtoModelAssembler.toModelResponse(produtoService.salvarProduto(produtoAtual));
    }
}
