package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.request.EstadoModelRequest;
import com.algaworks.algafood.api.model.response.EstadoModelResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Estados")
public interface EstadoOpenApi {

    @ApiOperation("Listar estados")
    public ResponseEntity<List<EstadoModelResponse>> listar();

    @ApiOperation("Buscar estado por Id")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class),
            @ApiResponse(code = 400, message = "Id do estado invalido", response = Problem.class)
    })
    public EstadoModelResponse buscar(@ApiParam(value = "Id do estado", example = "1", required = true) Long estadoId);

    @ApiOperation("Cadastrar estado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Estado criado")
    })
    public ResponseEntity<EstadoModelResponse> cadastrar(@ApiParam(name = "Corpo", value = "Representação de um estado", required = true)
                                                             EstadoModelRequest estadoModelRequest);

    @ApiOperation("Atualizar um estado")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    public EstadoModelResponse atualizar(@ApiParam(value = "Id de um estado", example = "1", required = true) Long estadoId,
                                         @ApiParam(name = "Corpo", value = "Representação de um estado para atualizar", required = true) EstadoModelRequest estadoModelRequest);
}
