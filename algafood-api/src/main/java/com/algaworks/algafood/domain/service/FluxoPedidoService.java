package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;

@Service
public class FluxoPedidoService {

    @Autowired
    PedidoService pedidoService;
    @Transactional
    public void confirmar(Long pedidoId){
        Pedido pedido = pedidoService.buscarPedidoExistente(pedidoId);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(Long pedidoId) {
        Pedido pedido = pedidoService.buscarPedidoExistente(pedidoId);
        pedido.cancelar();
    }

    public void entregar(Long pedidoId) {
        Pedido pedido = pedidoService.buscarPedidoExistente(pedidoId);
        pedido.entregue();
    }
}
