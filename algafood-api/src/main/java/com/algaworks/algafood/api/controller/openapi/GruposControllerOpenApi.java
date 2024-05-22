package com.algaworks.algafood.api.controller.openapi;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.request.GrupoModelRequest;
import com.algaworks.algafood.api.model.response.GrupoModelResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Grupo")
public interface GruposControllerOpenApi {
    @ApiOperation("Listar Grupos")
    public List<GrupoModelResponse> listar();

    @ApiOperation("Buscar Grupo Por ID")
    @ApiResponses({@ApiResponse(code = 404, message = "Grupo não encontrado", response = Process.class),
                    @ApiResponse(code = 400, message = "ID da cidade invalido", response = Problem.class)})
    public GrupoModelResponse buscar(Long grupoId);

    @ApiOperation("Criar Grupo")
    @ApiResponse(code = 204,message = "Grupo criado")
    public GrupoModelResponse cadastrar(GrupoModelRequest grupoModelRequest);

    @ApiOperation("Deletar Grupo Por ID")
    @ApiResponses({@ApiResponse(code = 404, message = "Grupo não encontrado", response = Process.class),
            @ApiResponse(code = 400, message = "ID da cidade invalido", response = Problem.class)})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(Long grupoId);

    @ApiOperation("Atualizar Grupo")
    @ApiResponses({@ApiResponse(code = 404, message = "Grupo não encontrado", response = Process.class),
            @ApiResponse(code = 400, message = "ID da cidade invalido", response = Problem.class)})
    public GrupoModelResponse atualizar(Long grupoId, GrupoModelRequest grupoModelRequest);
}
