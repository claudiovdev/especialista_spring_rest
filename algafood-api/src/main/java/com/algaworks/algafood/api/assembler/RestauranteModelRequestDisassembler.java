package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.request.RestauranteModelRequest;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

@Component
public class RestauranteModelRequestDisassembler {
    public Restaurante toDomain(RestauranteModelRequest restauranteModelRequest){
        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteModelRequest.getCozinha().getId());

        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteModelRequest.getNome());
        restaurante.setTaxaFrete(restauranteModelRequest.getTaxaFrete());

        restaurante.setCozinha(cozinha);
        return restaurante;
    }
}
