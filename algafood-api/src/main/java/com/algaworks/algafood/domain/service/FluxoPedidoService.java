package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Collections;

@Service
public class FluxoPedidoService {

    @Autowired
    private EnvioEmailService envioEmailService;

    @Autowired
    private PedidoService pedidoService;


    @Transactional
    public void confirmar(String codigo){
        Pedido pedido = pedidoService.buscarPedidoExistente(codigo);
        pedido.confirmar();

        var mensagem = Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " + Pedido confimado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatarios(Collections.singleton(pedido.getCliente().getEmail()))
                .build();

        envioEmailService.enviar(mensagem);

    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = pedidoService.buscarPedidoExistente(codigo);
        pedido.cancelar();
    }

    public void entregar(String codigo) {
        Pedido pedido = pedidoService.buscarPedidoExistente(codigo);
        pedido.entregue();
    }
}
