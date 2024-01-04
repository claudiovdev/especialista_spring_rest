package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {


    @Autowired
    EstadoService estadoService;

    @GetMapping
    public ResponseEntity<List<Estado>> listar(){
        return ResponseEntity.ok().body(estadoService.findAll()) ;
    }

    @GetMapping("/{estadoId}")
    public Estado buscar(@PathVariable Long estadoId){
        return estadoService.buscarEstadoExistente(estadoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Estado> cadastrar(@RequestBody Estado estado){
        return ResponseEntity.ok().body(estadoService.salvar(estado));
    }

    @PutMapping("/{estadoId}")
    public Estado atualizar(@PathVariable Long estadoId, @RequestBody Estado estado){
        return estadoService.atualizar(estadoId, estado);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long estadoId) {
        estadoService.deletar(estadoId);
    }

}
