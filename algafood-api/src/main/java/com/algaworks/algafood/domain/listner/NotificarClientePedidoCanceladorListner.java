package com.algaworks.algafood.domain.listner;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Collections;

@Component
public class NotificarClientePedidoCanceladorListner {

    @Autowired
    private EnvioEmailService envioEmailService;
    @TransactionalEventListener
    public void quandoPedidoForCancelado(PedidoCanceladoEvent event){
        Pedido pedido = event.getPedido();
        var mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " + Pedido cancelado")
                .corpo("pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatarios(Collections.singleton(pedido.getCliente().getEmail()))
                .build();

        envioEmailService.enviar(mensagem);
    }

}
