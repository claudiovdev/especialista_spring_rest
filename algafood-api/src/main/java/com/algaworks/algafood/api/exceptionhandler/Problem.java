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
    @ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
            position = 25)
    private String userMessage;
    @ApiModelProperty(example = "2024,5,15,0,1,25")
    private LocalDateTime timestemp;
    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)",
            position = 30)
    private List<Field> fields;
    private List<Object> objects;

    @Getter
    @Builder
    public static class Field{
        @ApiModelProperty(example = "preco")
        private String nome;

        @ApiModelProperty(example = "O preço é obrigatório")
        private String userMessage;
    }

}
