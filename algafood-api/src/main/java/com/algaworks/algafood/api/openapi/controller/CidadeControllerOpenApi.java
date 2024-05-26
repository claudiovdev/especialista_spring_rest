package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.request.CidadeModelRequest;
import com.algaworks.algafood.api.model.response.CidadeModelResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

    @ApiOperation("Lista as cidades")
    public ResponseEntity<List<CidadeModelResponse>> listar();

    @ApiOperation("Busca cidade por identificador")
    @ApiResponses({ @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
            @ApiResponse(code = 400, message = "ID da cidade invalido", response = Problem.class)})
    public CidadeModelResponse buscar(@ApiParam(value = "Id de uma cidade", example = "1") Long cidadeId);

    @ApiOperation("Cadastrar uma cidade")
    @ApiResponses({ @ApiResponse(code = 204, message = "Cidade cadastrada"),})
    public ResponseEntity<CidadeModelResponse> salvar(@ApiParam(name = "Corpo", value = "Representação de uma cidade")CidadeModelRequest cidadeModelRequest);

    @ApiResponses({ @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
            @ApiResponse(code = 200, message = "Cidade Atualizada", response = Problem.class)})
    public CidadeModelResponse atualizar(@PathVariable @Valid Long cidadeId, CidadeModelRequest cidadeModelRequest);

    @ApiResponses({ @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class),
            @ApiResponse(code = 204, message = "Cidade excluida")})
    public void deletar( Long cidadeId);
}

