package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.response.CozinhaModelResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageModelOpenApi <T>{
    private List<T> content;
    @ApiModelProperty(example = "10",value = "Quantidade de itens por pagina")
    private Long size;
    @ApiModelProperty(example = "50", value = "Total de registros")
    private Long totalElements;
    @ApiModelProperty(example = "3", value = "Total de paginas")
    private Long totalPages;
    @ApiModelProperty(example = "0",value = "Numero da pagina")
    private Long number;

}
