package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    InputStream recuperar(String nomeArquivo);
    void armazenar(NovaFoto novaFoto);
    void removerArquivo(String nomeDoArquivo);

    default String gerarNomeOriginal(String nomeArquivo){
        return UUID.randomUUID().toString() + "_" + nomeArquivo;
    }

    default void substituirFoto(String nomeArquivoAntigo, NovaFoto novaFoto){
        this.armazenar(novaFoto);
        if (nomeArquivoAntigo != null){
            this.removerArquivo(nomeArquivoAntigo);
        }
    }

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
    }
}
