package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("from Pedido p join fetch p.cliente c join fetch p.restaurante r join fetch r.cozinha co join fetch r.endereco e join fetch e.cidade")
    List<Pedido> findAll();
}
