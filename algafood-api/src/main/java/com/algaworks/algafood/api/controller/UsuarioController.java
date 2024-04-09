package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.UsuarioModelAssember;
import com.algaworks.algafood.api.assembler.modelDisassembler.UsuarioModelDisassembler;
import com.algaworks.algafood.api.model.request.SenhaUsuarioModelRequest;
import com.algaworks.algafood.api.model.request.UsuarioModelRequest;
import com.algaworks.algafood.api.model.request.UsuarioUpdateModelRequest;
import com.algaworks.algafood.api.model.response.UsuarioModelResponse;
import com.algaworks.algafood.domain.exceptions.SenhaIncompativelException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioModelAssember usuarioModelAssember;

    @Autowired
    private UsuarioModelDisassembler usuarioModelDisassembler;

    @GetMapping
    public List<UsuarioModelResponse> listar(){
        return usuarioModelAssember.toCollectionModelResponse(usuarioService.listar());
    }

    @GetMapping("/{usuarioId}")
    public UsuarioModelResponse buscar(@PathVariable Long usuarioId){
        return usuarioModelAssember.toModelResponse(usuarioService.buscarUsuarioExistente(usuarioId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioModelResponse cadastrar(@RequestBody @Valid UsuarioModelRequest usuarioModelRequest){
        Usuario usuario = usuarioModelDisassembler.toDomain(usuarioModelRequest);
        return usuarioModelAssember.toModelResponse(usuarioService.cadastrar(usuario));
    }

    @PutMapping
    public UsuarioModelResponse atualizar(@PathVariable Long usuarioId,
                                          @RequestBody @Valid UsuarioUpdateModelRequest usuarioModelRequest){
        var usuarioExistente = usuarioService.buscarUsuarioExistente(usuarioId);
        usuarioModelDisassembler.atualizar(usuarioModelRequest, usuarioExistente);
        return usuarioModelAssember.toModelResponse(usuarioService.cadastrar(usuarioExistente));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaUsuarioModelRequest usuarioModelRequest) {
        usuarioService.atualizarSenha(usuarioId, usuarioModelRequest.getSenhaAntiga(), usuarioModelRequest.getNovaSenha());
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long usuarioId){
        usuarioService.deletarUsuario(usuarioId);
    }
}
