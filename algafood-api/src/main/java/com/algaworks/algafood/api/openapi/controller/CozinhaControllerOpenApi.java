package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.request.CozinhaModelRequest;
import com.algaworks.algafood.api.model.response.CozinhaModelResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

    @ApiOperation("Lista Cozinha com paginação")
    public Page<CozinhaModelResponse> listar(Pageable pageable);

    @ApiOperation("Busca Cozinha por Id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cozinha invalido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaModelResponse buscar(@ApiParam(value = "Id da cozinha") Long cozinhaId) ;

    @ApiOperation("Cadastrar Cozinha")
    @ApiResponse(code = 201,message = "Cozinha cadastrada")
    public CozinhaModelResponse adicionar(@ApiParam(name = "Corpo",value = "Representação da cozinha")
                                              CozinhaModelRequest cozinhaModelRequest);

    @ApiOperation("Atualiza uma cozinha por Id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da cozinha invalido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    public CozinhaModelResponse atualizar(@ApiParam(value = "Id da cozinha", example = "1") Long cozinhaId,
                                          @ApiParam(name = "Corpo", value = "Representação da cozinha") CozinhaModelRequest cozinha);

    public void deletar( Long cozinhaId);

}
