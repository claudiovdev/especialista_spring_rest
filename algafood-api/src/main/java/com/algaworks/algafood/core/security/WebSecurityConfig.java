package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .cors().and()
                    .oauth2ResourceServer().jwt();

    }



//    @Bean
//    public JwtDecoder jwtDecoder(){
//        var secretKey = new SecretKeySpec("sfa987sfa98fsa4saf9saf84fsa98sfa79fas8sfa498sfa97".getBytes(), "HmacSHA256");
//        return NimbusJwtDecoder.withSecretKey(secretKey).build();
//    }
}
