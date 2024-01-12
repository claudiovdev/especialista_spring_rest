package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CadastroCozinhaIntegrationIT  {

	@LocalServerPort
	private int port;

	@Autowired
	Flyway flyway;


	@Autowired
	EstadoRepository estadoRepository;

	@BeforeEach
	void migrateDatabase() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}

	@Test
	public void deveretornarStatus200_QuandoConsultarCozinha(){
		RestAssured.given()
				.accept(ContentType.JSON)
				.when().get()
				.then().statusCode(HttpStatus.OK.value());
		flyway.migrate();
	}

	@Test
	public void deveConter3Cozinhas_QuandoConsultarCozinhas(){
		RestAssured.given()
				.accept(ContentType.JSON)
				.when().get()
				.then().statusCode(HttpStatus.OK.value())
				.body("", hasSize(4));
		flyway.migrate();
	}
}
