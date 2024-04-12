package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPedidoExistente(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(()-> new PedidoNaoEncontradoException(pedidoId));
    }
}
