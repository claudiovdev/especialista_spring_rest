package com.algaworks.algafood.api.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoModelResponse {
    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
