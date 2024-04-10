package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public Produto buscarProdutoExistente(Long produtoId, Long restauranteId){
        return produtoRepository.findByProdutoPorRestaurante(produtoId, restauranteId).orElseThrow(()-> new ProdutoNaoEncontradoException(produtoId));
    }

    public List<Produto> buscarTodosProdutos(Restaurante restaurante) {
        return produtoRepository.findByRestaurante(restaurante);
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }
}
