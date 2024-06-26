package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Collections;

@Service
public class FluxoPedidoService {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;


    @Transactional
    public void confirmar(String codigo){
        Pedido pedido = pedidoService.buscarPedidoExistente(codigo);
        pedido.confirmar();
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = pedidoService.buscarPedidoExistente(codigo);
        pedido.cancelar();
        pedidoRepository.save(pedido);
    }

    public void entregar(String codigo) {
        Pedido pedido = pedidoService.buscarPedidoExistente(codigo);
        pedido.entregue();
    }
}
