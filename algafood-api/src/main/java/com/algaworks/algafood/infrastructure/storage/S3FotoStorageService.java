package com.algaworks.algafood.infrastructure.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        String caminhoS3 = getCaminhoS3(nomeArquivo);

        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(),caminhoS3);
        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try{
            String caminhoS3 = getCaminhoS3(novaFoto.getNomeArquivo());

            var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(novaFoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoS3,
                    novaFoto.getInputStream(),
                    objectMetaData)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        }catch (Exception e){
            throw new StorageException("Não foi possivel enviar arquivo para AmazonS3", e);
        }


    }

    @Override
    public void removerArquivo(String nomeDoArquivo) {
        try {
            String caminhoArquivo = getCaminhoS3(nomeDoArquivo);

            var deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(), caminhoArquivo);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
        }

    }

    private String getCaminhoS3(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }
}
