package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageableModelOpenApi {
    @ApiModelProperty(value = "Quantidade de paginas ",example = "10")
    private int page;
    @ApiModelProperty(value = "Quantidade de itens por pagina", example = "5")
    private int size;
    @ApiModelProperty(value = "Nome da propriedade para ordenação")
    private List<String> sort;

}
