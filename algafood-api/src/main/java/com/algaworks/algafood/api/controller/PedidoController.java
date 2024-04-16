package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.modelAssembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.assembler.modelDisassembler.PedidoModelDisassembler;
import com.algaworks.algafood.api.model.request.PedidoModelRequest;
import com.algaworks.algafood.api.model.response.PedidoModelResponse;
import com.algaworks.algafood.api.model.response.PedidoResumidoModelResponse;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.PedidoService;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

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
    public List<PedidoResumidoModelResponse> listarPedidos(){
        return pedidoResumoModelAssembler.toCollectionModelResponse(pedidoService.listarPedidos());
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
}
