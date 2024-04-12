package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.FormaPagamentoInvalidaException;
import com.algaworks.algafood.domain.exceptions.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private UsuarioService usuarioService;



    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPedidoExistente(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(()-> new PedidoNaoEncontradoException(pedidoId));
    }

    public Pedido emitirPedido(Pedido pedido) {
        validarPedido(pedido);
        validaItens(pedido);


        return pedidoRepository.save(pedido);
    }

    public void validarPedido(Pedido pedido){
        Restaurante restaurante = restauranteService.buscarRestauranteExistente(pedido.getRestaurante().getId());
        Usuario cliente = usuarioService.buscarUsuarioExistente(pedido.getCliente().getId());
        FormaPagamento formaPagamento = formaPagamentoService.buscar(pedido.getFormaPagamento().getId());
        Cidade cidade = cidadeService.buscar(pedido.getEnderecoEntrega().getCidade().getId());

        pedido.setRestaurante(restaurante);
        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setFormaPagamento(formaPagamento);
        pedido.setCliente(cliente);

        if (!restaurante.getFormaPagamentos().contains(formaPagamento)){
            throw new FormaPagamentoInvalidaException(formaPagamento.getDescricao());
        }
    }

    public void validaItens(Pedido pedido){
        pedido.getItens().stream().forEach(item ->{
            Produto produto = produtoService.buscarProdutoExistente(item.getId(), pedido.getRestaurante().getId());
            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

}
