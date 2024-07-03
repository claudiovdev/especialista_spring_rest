package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.request.PedidoModelRequest;
import com.algaworks.algafood.api.model.response.PedidoModelResponse;
import com.algaworks.algafood.api.model.response.PedidoResumidoModelResponse;
import com.algaworks.algafood.api.openapi.model.PageModelOpenApi;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi{
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da propriedade para filtrar na resposta, separados por virgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation("Pesquisa os pedidos")
    Page<PedidoResumidoModelResponse> pesquisar(PedidoFilter filtro, Pageable pageable);

    @ApiOperation("Registra pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido registrado")
    })
    PedidoModelResponse cadastrarPedido(@ApiParam(name = "corpo", value = "Representação de um pedido")
                                                   PedidoModelRequest pedidoModelRequest);

    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nome da propriedade para filtrar na resposta, separados por virgula",
                    name = "campos", paramType = "query", type = "string")
    })
    @ApiOperation("Busca pedido por codigo")
    @ApiResponses({
            @ApiResponse(code = 404,message = "Pedido não encontrado", response = Problem.class)
    })
    PedidoModelResponse buscarPedido(@ApiParam(value = "Codigo de um pedido",
            example = "f9981ca4-5a5e-4da3-af04-933861df3e55") String codigo);

}
