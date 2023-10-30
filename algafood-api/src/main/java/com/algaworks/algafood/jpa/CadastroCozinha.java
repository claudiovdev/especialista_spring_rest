package com.algaworks.algafood.jpa;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CadastroCozinha {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> listar(){
       return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha){
        return manager.merge(cozinha);
    }

    public Cozinha find(Long id){
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    public void remover(Long id){
        Cozinha cozinha = find(id);
        manager.remove(cozinha);
    }
}
