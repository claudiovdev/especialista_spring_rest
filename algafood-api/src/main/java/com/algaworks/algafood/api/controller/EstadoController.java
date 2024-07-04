package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.EstadoModelAssembler;
import com.algaworks.algafood.api.assembler.modelDisassembler.EstadoModelDisassembler;
import com.algaworks.algafood.api.model.request.EstadoModelRequest;
import com.algaworks.algafood.api.model.response.EstadoModelResponse;
import com.algaworks.algafood.api.openapi.controller.EstadoOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoOpenApi {


    @Autowired
    EstadoService estadoService;

    @Autowired
    EstadoModelAssembler estadoModelAssembler;
    @Autowired
    EstadoModelDisassembler estadoModelDisassembler;

    @GetMapping
    public ResponseEntity<List<EstadoModelResponse>> listar(){
        return ResponseEntity.ok().body(estadoModelAssembler.toCollectionModelResponse(estadoService.findAll())) ;
    }

    @GetMapping("/{estadoId}")
    public EstadoModelResponse buscar(@PathVariable Long estadoId){
        return estadoModelAssembler.toModelResponse(estadoService.buscarEstadoExistente(estadoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EstadoModelResponse> cadastrar(@Valid @RequestBody EstadoModelRequest estadoModelRequest){
        Estado estado = estadoModelDisassembler.toDomain(estadoModelRequest);
        return ResponseEntity.ok().body(estadoModelAssembler.toModelResponse(estadoService.salvar(estado)));
    }

    @PutMapping("/{estadoId}")
    public EstadoModelResponse atualizar(@PathVariable @Valid Long estadoId, @RequestBody EstadoModelRequest estadoModelRequest){
        Estado estado = estadoModelDisassembler.toDomain(estadoModelRequest);
        return estadoModelAssembler.toModelResponse(estadoService.atualizar(estadoId, estado));
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long estadoId) {
        estadoService.deletar(estadoId);
    }

}
