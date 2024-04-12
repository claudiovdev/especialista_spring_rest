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
        if (!pedido.getStatus().equals(StatusPedido.CRIADO)){
            throw new NegocioException(String.format("Status do pedido: %d não pode ser alterado de %s para %s",
                    pedido.getItens(), pedido.getStatus().getDescricao(),StatusPedido.CONFIRMADO.getDescricao()));
        }
        pedido.setStatus(StatusPedido.CONFIRMADO);
        pedido.setDataConfirmacao(OffsetDateTime.now());
    }
}
