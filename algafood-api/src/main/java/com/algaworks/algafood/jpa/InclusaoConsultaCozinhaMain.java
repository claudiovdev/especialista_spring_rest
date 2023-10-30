package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class InclusaoConsultaCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CadastroCozinha cadastroCozinha = applicationContext.getBean(CadastroCozinha.class);

        Cozinha cozinha1 = new Cozinha();
        cozinha1.setNome("Brasileira");
        cozinha1 =  cadastroCozinha.salvar(cozinha1);

        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Japoneza");
        cozinha2 = cadastroCozinha.salvar(cozinha2);

        System.out.printf("%d - %s", cozinha1.getId(), cozinha1.getNome());
        System.out.println(" ");
        System.out.printf("%d - %s", cozinha2.getId(), cozinha2.getNome());
        System.out.println(" ");
    }
}
