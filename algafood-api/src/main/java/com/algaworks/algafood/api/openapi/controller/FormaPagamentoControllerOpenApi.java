package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.request.FormaPagamentoModelRequest;
import com.algaworks.algafood.api.model.response.FormaPagamentoModelResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenApi {

    @ApiOperation("Buscar formas de pagamento")
    public ResponseEntity<List<FormaPagamentoModelResponse>> listar();


    @ApiOperation("Busca forma de pagamento por Id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Id da forma de pagamento invalida", response = Problem.class ),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    public ResponseEntity<FormaPagamentoModelResponse> buscar(
            @ApiParam(value = "Id de uma forma de pagamento", example = "1") Long formaPagamentoId);

    @ApiOperation("Cadastrar forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento cadastrada")
    })
    public FormaPagamentoModelResponse adicionar(
            @ApiParam(name = "Corpo", value = "Representação de uma forma de pagamento") FormaPagamentoModelRequest formaPagamentoModelRequest);


    @ApiOperation("Deletar uma forma de pagamento por Id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class),
            @ApiResponse(code = 400, message = "Id da forma de pagamento invalido", response = Problem.class)
    })
    public void deletar( @ApiParam(value = "Id da forma de pagamento", example = "1") Long formaPagamentoId);

}
