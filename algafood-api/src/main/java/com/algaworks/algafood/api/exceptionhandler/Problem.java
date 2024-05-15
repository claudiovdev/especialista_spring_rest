package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400")
    private Integer status;
    @ApiModelProperty(example = "https://algafood.com.br/recurso-nao-encontrado")
    private String type;
    @ApiModelProperty(example = "Recurso não encontrado")
    private String title;
    @ApiModelProperty(example = "O recurso /cidades que você tentou acessar, é inexistente")
    private String detail;
    private String userMessage;
    @ApiModelProperty(example = "2024,5,15,0,1,25")
    private LocalDateTime timestemp;
    private List<Field> fields;
    private List<Object> objects;

    @Getter
    @Builder
    public static class Field{
        private String nome;
        private String userMessage;
    }

}
