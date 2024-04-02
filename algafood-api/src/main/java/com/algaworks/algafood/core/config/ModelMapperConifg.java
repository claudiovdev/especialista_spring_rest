package com.algaworks.algafood.core.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConifg {

    @Bean
    public ModelMapper modelMapper(){
        return  new ModelMapper();
    }
}
