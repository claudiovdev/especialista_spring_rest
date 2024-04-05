package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.assembler.GrupoModelDisassembler;
import com.algaworks.algafood.api.model.request.GrupoModelRequest;
import com.algaworks.algafood.api.model.response.GrupoModelResponse;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
import groovy.transform.AutoFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    public GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private GrupoModelDisassembler grupoModelDisassembler;

    @GetMapping
    public List<GrupoModelResponse> listar(){
        return grupoModelAssembler.toCollectionModelResponse(grupoService.listarTodos());
    }

    @GetMapping("/{grupoId}")
    public GrupoModelResponse buscar(@PathVariable Long grupoId){
        return grupoModelAssembler.toModelResponse(grupoService.buscarPorId(grupoId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoModelResponse cadastrar(@RequestBody @Valid GrupoModelRequest grupoModelRequest){
        Grupo grupo = grupoModelDisassembler.toModelRequest(grupoModelRequest);
        return grupoModelAssembler.toModelResponse(grupoService.cadastrar(grupo));
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long grupoId){
        grupoService.deletar(grupoId);
    }

    @PutMapping("/{grupoId}")
    public GrupoModelResponse atualizar(@PathVariable Long grupoId,
                                        @RequestBody @Valid GrupoModelRequest grupoModelRequest){
        var grupoExistente = grupoService.buscarPorId(grupoId);
        grupoModelDisassembler.copyToDomain(grupoModelRequest, grupoExistente);
        return grupoModelAssembler.toModelResponse(grupoService.cadastrar(grupoExistente));
    }
}
