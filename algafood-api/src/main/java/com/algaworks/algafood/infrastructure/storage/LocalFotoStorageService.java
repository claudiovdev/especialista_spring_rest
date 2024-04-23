package com.algaworks.algafood.infrastructure.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;



//@Service
public class LocalFotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeArquivo);
            FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(arquivoPath))
                    .build();
            return fotoRecuperada;
        }catch (Exception e){
            throw new StorageException("Não foi possivel recuperar o arquivo",e);
        }

    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try{
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        }catch (Exception e){
            throw new StorageException("Não foi possivel armazenar o arquivo",e);
        }

    }

    @Override
    public void removerArquivo(String nomeDoArquivo) {
        try {
            Path arquivoPath = getArquivoPath(nomeDoArquivo);
            Files.deleteIfExists(arquivoPath);
        }catch (IOException e){
            throw new StorageException("Não foi possivel excluir o arquivo",e);
        }

    }

    private Path getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }
}
