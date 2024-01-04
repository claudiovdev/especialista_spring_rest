package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exceptions.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<Cidade>> listar(){
        return ResponseEntity.ok().body(cidadeService.listar());
    }

    @GetMapping("/{cidadeId}")
    public Cidade buscar(@PathVariable Long cidadeId){
        return cidadeService.buscarCidadeExistente(cidadeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cidade> salvar(@RequestBody Cidade cidade){
        return ResponseEntity.ok().body(cidadeService.salvar(cidade));
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId,@RequestBody Cidade cidade){
       return cidadeService.atualizar(cidadeId, cidade);
    }

    @DeleteMapping("/{cidadeId}")
    public void deletar(@PathVariable Long cidadeId){
        cidadeService.deletar(cidadeId);
    }
}
