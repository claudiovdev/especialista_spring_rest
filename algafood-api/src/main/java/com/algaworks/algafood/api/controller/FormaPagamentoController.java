package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.modelDisassembler.FormaPagamentoDisassembler;
import com.algaworks.algafood.api.model.request.FormaPagamentoModelRequest;
import com.algaworks.algafood.api.model.response.FormaPagamentoModelResponse;
import com.algaworks.algafood.api.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("forma-pagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @Autowired
    private FormaPagamentoDisassembler formaPagamentoDisassembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FormaPagamentoModelResponse>> listar(){
        List<FormaPagamentoModelResponse> collectionModelResponse = formaPagamentoAssembler.toCollectionModelResponse(formaPagamentoService.listar());
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(collectionModelResponse);
    }

    @GetMapping(value = "/{formaPagamentoId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormaPagamentoModelResponse> buscar(@PathVariable Long formaPagamentoId){
        FormaPagamentoModelResponse modelResponse = formaPagamentoAssembler.toModelResponse(formaPagamentoService.buscar(formaPagamentoId));
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .body(modelResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModelResponse adicionar(@RequestBody @Valid FormaPagamentoModelRequest formaPagamentoModelRequest){
        FormaPagamento formaPagamento = formaPagamentoDisassembler.toDomain(formaPagamentoModelRequest);
        return formaPagamentoAssembler.toModelResponse(formaPagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping(value = "/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long formaPagamentoId){
        formaPagamentoService.deletar(formaPagamentoId);
    }

}
