package com.algaworks.algafood.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @ManyToOne()
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    @Embedded
    private Endereco endereco;

    private Boolean ativo = Boolean.TRUE;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
                    joinColumns = @JoinColumn(name = "restaurante_id"),
                    inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private Set<FormaPagamento> formaPagamentos = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    public boolean removerFormaPagamento(FormaPagamento formaPagamento){
        return getFormaPagamentos().remove(formaPagamento);
    }

    public boolean adicionarFormaPagamento(FormaPagamento formaPagamento){
        return getFormaPagamentos().add(formaPagamento);
    }
    public void ativar(){
        setAtivo(true);
    }

    public void inativar(){
        setAtivo(false);
    }

}
