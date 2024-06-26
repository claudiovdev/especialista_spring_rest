package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.assembler.modelDisassembler.CozinhaModelRequestDisassembler;
import com.algaworks.algafood.api.model.request.CozinhaModelRequest;
import com.algaworks.algafood.api.model.response.CozinhaModelResponse;
import com.algaworks.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenApi {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private CozinhaModelRequestDisassembler cozinhaModelRequestDisassembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CozinhaModelResponse> listar(@PageableDefault(size = 10) Pageable pageable){
        Page<Cozinha> cozinhasPage = cozinhaService.buscarTodas(pageable);
        List<CozinhaModelResponse> cozinhaModelResponseList = cozinhaModelAssembler.toCollectionModelResponse(cozinhasPage.getContent());
        Page<CozinhaModelResponse> cozinhaModelResponsePage = new PageImpl<>(cozinhaModelResponseList,pageable,cozinhasPage.getTotalElements());
        return cozinhaModelResponsePage;
    }

    @GetMapping(value = "/{cozinhaId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModelResponse buscar(@PathVariable(value = "cozinhaId") Long cozinhaId) {
        return cozinhaModelAssembler.toModelResponse(cozinhaService.buscarCozinhaExistente(cozinhaId));
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaModelResponse adicionar(@RequestBody @Valid CozinhaModelRequest cozinhaModelRequest){
        Cozinha cozinha = cozinhaModelRequestDisassembler.toDomain(cozinhaModelRequest);
        return cozinhaModelAssembler.toModelResponse(cozinhaService.salvar(cozinha));
    }

    @PutMapping(value = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CozinhaModelResponse atualizar(@PathVariable @Valid Long cozinhaId, @RequestBody CozinhaModelRequest cozinha){
        Cozinha cozinhaExistente = cozinhaService.buscarCozinhaExistente(cozinhaId);
        BeanUtils.copyProperties(cozinha, cozinhaExistente, "id");
        return cozinhaModelAssembler.toModelResponse(cozinhaService.salvar(cozinhaExistente));
    }

    @DeleteMapping(value = "/{cozinhaId}", produces = {})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long cozinhaId) {
        cozinhaService.excluir(cozinhaId);
    }

}
