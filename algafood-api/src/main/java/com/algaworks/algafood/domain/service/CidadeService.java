package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exceptions.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CidadeService {


    public static final String MSG_ESTADO_NAO_ENCONTRADO = "Não foi possivel localizar um estado com codigo %d";
    public static final String MSG_CIDADE_EM_USO = "A cidade %d não pode ser deletada pois está em uso";
    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    private SmartValidator validator;

    @Autowired
    EstadoService estadoService;
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Cidade buscar(Long cidadeId) {
        return cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
    }


    @Transactional
    public Cidade salvar(Cidade cidade) {

        return cidadeRepository.save(cidade);
    }


    @Transactional
    public Cidade atualizar(Long cidadeId, Cidade cidade) {
        Cidade cidadeExistente = cidadeRepository.findById(cidadeId).orElseThrow(()-> new CidadeNaoEncontradaException(cidadeId));
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoService.buscarEstadoExistente(estadoId);
        BeanUtils.copyProperties(cidade, cidadeExistente, "id");
        validate(cidadeExistente, "cidade");
        cidadeExistente.setEstado(estado);
        return cidadeRepository.save(cidadeExistente);
    }

    public void deletar(Long cidadeId) {
        try{
            cidadeRepository.deleteById(cidadeId);
        }catch (EmptyResultDataAccessException e){
            throw  new CidadeNaoEncontradaException(cidadeId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }

    public Cidade buscarCidadeExistente(Long id){
       return  cidadeRepository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    private void validate(Cidade cidade, String objectName){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(cidade, objectName);
        validator.validate(cidade, bindingResult);

        if (bindingResult.hasErrors()){
            throw new ValidacaoException(bindingResult);
        }
    }
}
