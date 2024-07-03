package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.modelAssembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.assembler.modelDisassembler.PedidoModelDisassembler;
import com.algaworks.algafood.api.model.request.PedidoModelRequest;
import com.algaworks.algafood.api.model.response.PedidoModelResponse;
import com.algaworks.algafood.api.model.response.PedidoResumidoModelResponse;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.PedidoService;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenApi {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoModelDisassembler pedidoModelDisassembler;

//    @GetMapping
//    public MappingJacksonValue listarPedidos(@RequestParam(required = false) String campos){
//        List<Pedido> pedidos = pedidoService.listarPedidos();
//        List<PedidoResumidoModelResponse> pedidosModel = pedidoResumoModelAssembler.toCollectionModelResponse(pedidos);
//        MappingJacksonValue pedidoWrapper = new MappingJacksonValue(pedidosModel);
//        SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();
//        simpleFilterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if (StringUtils.isNotBlank(campos)){
//            simpleFilterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidoWrapper.setFilters(simpleFilterProvider);
//        return pedidoWrapper;
//    }

    @GetMapping
    public Page<PedidoResumidoModelResponse> pesquisar(PedidoFilter filtro, Pageable pageable){
        Pageable pageableTraduzida = traduzirPageable(pageable);
        Page<Pedido> pedidos = pedidoService.listarPedidos(filtro, pageable);
        List<PedidoResumidoModelResponse> listaPedidosModelResponse = pedidoResumoModelAssembler.toCollectionModelResponse(pedidos.getContent());
        PageImpl page = new PageImpl<>(listaPedidosModelResponse, pageable,pedidos.getTotalElements());
        return page;
    }




    @GetMapping("/{codigo}")
    public PedidoModelResponse buscarPedido(@PathVariable String codigo){
        return pedidoModelAssembler.toModelResponse(pedidoService.buscarPedidoExistente(codigo));
    }

    @PostMapping
    public PedidoModelResponse cadastrarPedido(@RequestBody PedidoModelRequest pedidoModelRequest){
        try{
            Pedido pedido =  pedidoModelDisassembler.toDomain(pedidoModelRequest);
            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);
            pedido = pedidoService.emitirPedido(pedido);
            return pedidoModelAssembler.toModelResponse(pedido);
        }catch (EntidadeNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }

    }

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = ImmutableMap.of(
                "nomeCliente", "cliente.nome",
                "codigo", "codigo",
                "valorTotal", "valorTotal",
                "restaurante.nome", "restaurante.nome"

        );

        return PageableTranslator.translate(pageable,mapeamento);

    }
}
