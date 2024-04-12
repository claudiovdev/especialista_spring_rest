package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.PedidoModelAssembler;
import com.algaworks.algafood.api.model.response.PedidoModelResponse;
import com.algaworks.algafood.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

//    @Autowired
//    private PedidoModelDisassembler pedidoModelDisassembler;

    @GetMapping
    public List<PedidoModelResponse> listarPedidos(){
        return pedidoModelAssembler.toCollectionModelResponse(pedidoService.listarPedidos());
    }

    @GetMapping("/{pedidoId}")
    public PedidoModelResponse buscarPedido(@PathVariable Long pedidoId){
        return pedidoModelAssembler.toModelResponse(pedidoService.buscarPedidoExistente(pedidoId));
    }
}
