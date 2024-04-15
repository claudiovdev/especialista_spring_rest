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
import org.springframework.beans.factory.annotation.Autowired;
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
