package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {
    public List<Cozinha> listar();
    public Cozinha salvar(Cozinha cozinha);
    public Cozinha find(Long id);
    public void remover(Long id);
}
