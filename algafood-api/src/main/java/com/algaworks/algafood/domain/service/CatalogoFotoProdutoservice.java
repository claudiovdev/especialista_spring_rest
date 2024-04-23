package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.Optional;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoservice {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream dadosArquivo){
        Long restauranteId = fotoProduto.getRestauranteId();
        Long produtoId = fotoProduto.getProduto().getId();
        String nomeNovoArquivo = fotoStorage.gerarNomeOriginal(fotoProduto.getNomeArquivo());
        String nomeArquivoAntigo = null;

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId,produtoId);
        if (fotoExistente.isPresent()){
            nomeArquivoAntigo = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }
        fotoProduto.setNomeArquivo(nomeNovoArquivo);
        fotoProduto = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        fotoStorage.substituirFoto(nomeArquivoAntigo,novaFoto);

        return  fotoProduto;
    }
}
