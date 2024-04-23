package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.modelAssembler.FotoProdutoAssembler;
import com.algaworks.algafood.api.model.request.FotoProdutoModelRequest;
import com.algaworks.algafood.api.model.response.FotoProdutoModelResponse;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoservice;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private CatalogoFotoProdutoservice catalogoFotoProdutoservice;

    @Autowired
    private FotoStorageService fotoStorageService;

    @Autowired
    private FotoProdutoAssembler fotoProdutoAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModelResponse atualizarFoto(@PathVariable Long restauranteId,
                                                  @PathVariable Long produtoId,
                                                  @Valid FotoProdutoModelRequest fotoProdutoModelRequest) throws IOException {
        Produto produto = produtoService.buscarProdutoExistente(produtoId,restauranteId);
        MultipartFile arquivo = fotoProdutoModelRequest.getArquivo();

        FotoProduto foto = new FotoProduto();

        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoModelRequest.getDescricao());
        foto.setContentType(arquivo.getContentType());
        foto.setTamanho(arquivo.getSize());
        foto.setNomeArquivo(arquivo.getOriginalFilename());
       return fotoProdutoAssembler.toModelResponse(catalogoFotoProdutoservice.salvar(foto, arquivo.getInputStream()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public  FotoProdutoModelResponse buscar(@PathVariable Long restauranteId,
                               @PathVariable Long produtoId){
        FotoProduto fotoProduto = catalogoFotoProdutoservice.buscarFotoProduto(restauranteId, produtoId);
        return fotoProdutoAssembler.toModelResponse(fotoProduto);
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> entregarFoto(@PathVariable Long restauranteId,
                                                            @PathVariable Long produtoId) {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoservice.buscarFotoProduto(restauranteId,produtoId);
            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(inputStream));
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }

    }
}
