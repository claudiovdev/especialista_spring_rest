package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.FormaPagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.exceptions.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormaPagamentoService {

    private static final String MSG_FORMAPAGAMENTO_EM_USO = "A formaPagamento com id %d não pode ser deletada pois está em uso";


    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    public List<FormaPagamento> listar(){
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento buscar(Long formaPagamentoId) {
        return formaPagamentoRepository.findById(formaPagamentoId).orElseThrow(() ->
                new FormaPagamentoNaoEncontradoException(formaPagamentoId));
    }

    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    public void deletar(Long formaPagamentoId) {
        try {
            formaPagamentoRepository.deleteById(formaPagamentoId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_FORMAPAGAMENTO_EM_USO, formaPagamentoId));
        }catch (EmptyResultDataAccessException e){
            throw  new FormaPagamentoNaoEncontradoException( formaPagamentoId);
        }

    }
}
