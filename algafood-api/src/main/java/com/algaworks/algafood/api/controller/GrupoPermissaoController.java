package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.response.PermissaoModelResponse;
import com.algaworks.algafood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @GetMapping
    List<PermissaoModelResponse> listarPermissoesPorGrupo(@PathVariable Long grupoId){
        return permissaoModelAssembler.toCollectionModelResponse(grupoService.buscarPermissoesPorGrupo(grupoId));
    }

    @DeleteMapping("/{permissaoId}/desassociar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermisaoParaGrupo(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.desassociarPermissao(grupoId,permissaoId);
    }

    @PutMapping("/{permissaoId}/associar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarPermisaoParaGrupo(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.associarPermissao(grupoId,permissaoId);
    }

}
