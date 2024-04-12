package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.GrupoModelAssembler;
import com.algaworks.algafood.api.assembler.modelAssembler.UsuarioModelAssember;
import com.algaworks.algafood.api.model.response.GrupoModelResponse;
import com.algaworks.algafood.domain.service.UsuarioService;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioModelAssember usuarioModelAssember;
    @Autowired
    private GrupoModelAssembler grupoModelAssembler;


    @GetMapping
    List<GrupoModelResponse> listarGrupoPorUsuario(@PathVariable Long usuarioId){
        var usuario = usuarioService.buscarUsuarioExistente(usuarioId);
        return grupoModelAssembler.toCollectionModelResponse(usuario.getGrupos());
    }

    @DeleteMapping("/{grupoId}/desassociar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarGrupoDeUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.desassociarGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{grupoId}/associar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarGrupoDeUsuario(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.associarGrupo(usuarioId, grupoId);
    }

}
