package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.request.RestauranteModelRequest;
import com.algaworks.algafood.api.model.response.RestauranteModelResponse;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {
    @ApiOperation("Busca restaurante por codigo")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
            @ApiResponse(code = 400, message = "Id do restaurante invalido", response = Problem.class)
    })
    RestauranteModelResponse buscar(@ApiParam(value = "Id do restaurante", example = "1") Long restauranteId);

    @ApiOperation("Adiciona um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurante criado")
    })
    RestauranteModelResponse adicionar(@ApiParam(name = "Corpo", value = "Representação de um restaurante novo", required = true)
                                       RestauranteModelRequest restauranteModelRequest);
    @ApiOperation("Atualizar um restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class),
            @ApiResponse(code = 200, message = "Restaurante Atualizado")
    })
    RestauranteModelResponse atualizar(@ApiParam(value = "Id do restaurante",example = "1", required = true) Long restauranteId,
                                       @ApiParam(name = "Corpo", value = "Representação do restaurante com dados novos", required = true)
                                       RestauranteModelRequest restauranteModelRequest);



}
