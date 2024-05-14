package com.algaworks.algafood.core.openapi;

import net.sf.jasperreports.data.http.RequestMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.operation.ResponseMessagesReader;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
                .paths(PathSelectors.any())
                //.paths(PathSelectors.ant("/restaurantes/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, globalGetResponse())
                .globalResponses(HttpMethod.POST, globalPostPutResponse())
                .globalResponses(HttpMethod.PUT, globalPostPutResponse())
                .globalResponses(HttpMethod.DELETE, globalPostPutResponse())
                .apiInfo(apiInfo())
                .tags(new Tag("Cidades", "Gerencia as cidades"));
    }

    private List<Response> globalGetResponse(){
        return Arrays.asList(
               new ResponseBuilder()
                       .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                       .description("Erro ao realizar requisição fale com administrador").build(),
                new ResponseBuilder()
                        .code(HttpStatus.BAD_REQUEST.toString())
                        .description("Requisição inválida (erro do cliente)")
                        .build());
    }

    private List<Response> globalPostPutResponse(){
        return Arrays.asList(
                new ResponseBuilder()
                        .code(HttpStatus.BAD_REQUEST.toString())
                        .description("Requisição inválida (erro do cliente)")
                        .build(),
                new ResponseBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .description("Erro interno no servidor")
                        .build(),
                new ResponseBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.toString())
                        .description("Recurso não possui representação que poderia ser aceita pelo consumidor")
                        .build(),
                new ResponseBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString())
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .build()
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Algafood API")
                .description("Api aberta para clientes e restaurantes")
                .version("1.0")
                .contact(new Contact("Vinicius", "https://meusite.com.br", "contato@gmail.com"))
                .build();
    }
}
