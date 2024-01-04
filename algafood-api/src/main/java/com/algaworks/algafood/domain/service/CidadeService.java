package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    public static final String MSG_CIDADE_NAO_ENCONTRADA = "Não foi possivel localizar uma cidade com codigo %d";
    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não foi possivel localizar um estado com codigo %d";
    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Cidade buscar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId).orElseThrow(() -> new CozinhaNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
    }


    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }


    public Cidade atualizar(Long cidadeId, Cidade cidade) {
        Cidade cidadeExistente = cidadeRepository.findById(cidadeId).orElseThrow(()-> new CidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradoException(String.format(MSG_ESTADO_NAO_ENCONTRADO, estadoId)));
        BeanUtils.copyProperties(cidade, cidadeExistente, "id");
        cidadeExistente.setEstado(estado);
        return cidadeRepository.save(cidadeExistente);
    }

    public void deletar(Long cidadeId) {
        Cidade cidade = cidadeRepository.findById(cidadeId).orElseThrow(()-> new CidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId)));
        cidadeRepository.delete(cidade);
    }

    public Cidade buscarCidadeExistente(Long id){
       return  cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, id)));
    }
}
