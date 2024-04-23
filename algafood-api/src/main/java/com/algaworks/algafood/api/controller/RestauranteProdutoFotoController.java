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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
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

    @GetMapping
    public ResponseEntity<InputStreamResource> entregarFoto(@PathVariable Long restauranteId,
                                                            @PathVariable Long produtoId,
                                                            @RequestHeader(name = "accept") String acceptHeader) {
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoservice.buscarFotoProduto(restauranteId,produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypeList = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypeList);

            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(inputStream));
        }catch (EntidadeNaoEncontradaException | HttpMediaTypeNotAcceptableException e){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirFoto(@PathVariable Long restauranteId,
                            @PathVariable Long produtoId){
        catalogoFotoProdutoservice.excluirFoto(restauranteId, produtoId);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeList) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypeList.stream().anyMatch( mediaType -> mediaType.isCompatibleWith(mediaTypeFoto));
        if (!compativel){
            throw new HttpMediaTypeNotAcceptableException(mediaTypeList);
        }
    }
}
