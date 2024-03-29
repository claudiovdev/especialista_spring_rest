package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
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
public class EstadoService {

    public static final String MSG_ESTADO_EM_USO_EXCEPTION = "O estado com id %d não pode ser deletada pois está em uso";
    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private SmartValidator validator;

    public List<Estado> findAll() {
        return estadoRepository.findAll();
    }

    public Estado buscarPorId(Long estadoId) {
        return estadoRepository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }

    @Transactional
    public Estado salvar(Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional
    public Estado atualizar(Long estadoId, Estado estado) {
        Estado estadoExistente = estadoRepository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));;
        BeanUtils.copyProperties(estado,estadoExistente, "id");
        validate(estadoExistente, "estado");
        return estadoRepository.save(estadoExistente);
    }

    public void deletar(Long estadoId) {
        try{
            estadoRepository.deleteById(estadoId);
        }catch (EmptyResultDataAccessException e){
            throw new EstadoNaoEncontradoException(estadoId);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO_EXCEPTION, estadoId));
        }
    }

    public Estado buscarEstadoExistente(Long estadoId){
       return estadoRepository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }

    private void validate(Estado estado, String objectName){
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(estado, objectName);
        validator.validate(estado, bindingResult);

        if (bindingResult.hasErrors()){
            throw new ValidacaoException(bindingResult);
        }
    }
}
