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
    public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId){
        try {
            return ResponseEntity.ok().body(cidadeService.buscar(cidadeId));
        }catch (CidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cidade> salvar(@RequestBody Cidade cidade){
        return ResponseEntity.ok().body(cidadeService.salvar(cidade));
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,@RequestBody Cidade cidade){
        try{
            return ResponseEntity.ok().body(cidadeService.atualizar(cidadeId,cidade));
        }catch (CidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }catch (EstadoNaoEncontradoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Cidade> deletar(@PathVariable Long cidadeId){
        try {
            cidadeService.deletar(cidadeId);
            return ResponseEntity.noContent().build();
        }catch (CidadeNaoEncontradaException e){
           return ResponseEntity.notFound().build();
        }
    }
}
