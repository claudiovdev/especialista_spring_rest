package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.request.FotoProdutoModelRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable Long restauranteId,
                              @PathVariable Long produtoId,
                              @Valid FotoProdutoModelRequest fotoProdutoModelRequest){
        var nomeArquivo = UUID.randomUUID() + "_" + fotoProdutoModelRequest.getArquivo().getOriginalFilename();
        var arquivoFoto = Path.of("/Users/USER/Documents/catalogo", nomeArquivo);
        System.out.println(arquivoFoto);
        System.out.println(fotoProdutoModelRequest.getArquivo().getContentType());
        System.out.println(fotoProdutoModelRequest.getDescricao());

        try {
            fotoProdutoModelRequest.getArquivo().transferTo(arquivoFoto);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }

    }
}
