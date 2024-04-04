package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.assembler.CidadeModelDisassembler;
import com.algaworks.algafood.api.model.request.CidadeModelRequest;
import com.algaworks.algafood.api.model.response.CidadeModelResponse;
import com.algaworks.algafood.domain.exceptions.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exceptions.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    CidadeService cidadeService;
    @Autowired
    CidadeModelAssembler cidadeModelAssembler;
    @Autowired
    CidadeModelDisassembler cidadeModelDisassembler;

    @GetMapping
    public ResponseEntity<List<CidadeModelResponse>> listar(){
        return ResponseEntity.ok().body(cidadeModelAssembler.toCollectionModelResponse(cidadeService.listar()));
    }

    @GetMapping("/{cidadeId}")
    public CidadeModelResponse buscar(@PathVariable Long cidadeId){
        return cidadeModelAssembler.toModelResponse(cidadeService.buscarCidadeExistente(cidadeId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CidadeModelResponse> salvar( @Valid @RequestBody CidadeModelRequest cidadeModelRequest){
        Cidade cidade = cidadeModelDisassembler.toDomain(cidadeModelRequest);
        return ResponseEntity.ok().body(cidadeModelAssembler.toModelResponse(cidadeService.salvar(cidade)));
    }

    @PutMapping("/{cidadeId}")
    public CidadeModelResponse atualizar(@PathVariable @Valid Long cidadeId, @RequestBody CidadeModelRequest cidadeModelRequest){
        CidadeModelResponse cidadeAtualizada = new CidadeModelResponse();
        Cidade cidade = cidadeModelDisassembler.toDomain(cidadeModelRequest);
        try {
            cidadeAtualizada = cidadeModelAssembler.toModelResponse(cidadeService.atualizar(cidadeId, cidade));
        }catch (EstadoNaoEncontradoException e){
            throw new NegocioException(e.getMessage());
        }
        return cidadeAtualizada;
    }

    @DeleteMapping("/{cidadeId}")
    public void deletar(@PathVariable Long cidadeId){
        cidadeService.deletar(cidadeId);
    }
}
