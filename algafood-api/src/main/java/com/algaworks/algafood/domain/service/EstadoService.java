package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    public Estado buscarPorId(Long estadoId) {
        return estadoRepository.findById(estadoId).orElseThrow(EntidadeNaoEncontradaException::new);
    }

    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado atualizar(Long estadoId, Estado estado) {
        Estado estadoExistente = estadoRepository.findById(estadoId).orElseThrow(EstadoNaoEncontradoException:: new);
        BeanUtils.copyProperties(estado,estadoExistente, "id");
        return estadoRepository.save(estadoExistente);
    }

    public void deletar(Long estadoId) {
        Estado estadoExistente = estadoRepository.findById(estadoId).orElseThrow(EstadoNaoEncontradoException:: new);
        estadoRepository.delete(estadoExistente);
    }
}
