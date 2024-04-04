package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoDisassembler;
import com.algaworks.algafood.api.model.request.FormaPagamentoModelRequest;
import com.algaworks.algafood.api.model.response.FormaPagamentoModelResponse;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("forma-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoAssembler formaPagamentoAssembler;

    @Autowired
    private FormaPagamentoDisassembler formaPagamentoDisassembler;

    @GetMapping
    public List<FormaPagamentoModelResponse> listar(){
        return formaPagamentoAssembler.toCollectionModelResponse(formaPagamentoService.listar());
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoModelResponse buscar(@PathVariable Long formaPagamentoId){
        return formaPagamentoAssembler.toModelResponse(formaPagamentoService.buscar(formaPagamentoId));
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModelResponse adicionar(@RequestBody @Valid FormaPagamentoModelRequest formaPagamentoModelRequest){
        FormaPagamento formaPagamento = formaPagamentoDisassembler.toDomain(formaPagamentoModelRequest);
        return formaPagamentoAssembler.toModelResponse(formaPagamentoService.salvar(formaPagamento));
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long formaPagamentoId){
        formaPagamentoService.deletar(formaPagamentoId);
    }

}
