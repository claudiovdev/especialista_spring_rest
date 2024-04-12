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
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import com.algaworks.algafood.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class FluxoPedidoController {

    @Autowired
    private FluxoPedidoService pedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void confirmar(@PathVariable Long pedidoId){
        pedidoService.confirmar(pedidoId);
    }

}
