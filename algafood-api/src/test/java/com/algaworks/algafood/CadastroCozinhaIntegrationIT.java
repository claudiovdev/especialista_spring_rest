package com.algaworks.algafood;

import com.algaworks.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIntegrationIT {

	@Autowired
	CozinhaService cozinhaService;

	@Test
	public void deveAtribuirId_quandoCadastrarComDadosCorretos(){
		//cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");
		// ação
		novaCozinha = cozinhaService.salvar(novaCozinha);

		// Validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();

	}

	@Test(expected = ConstraintViolationException.class)
	public void deveFalhar_quandoCadastrarCozinhaSemNome(){
		//cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		// ação
		novaCozinha = cozinhaService.salvar(novaCozinha);
	}

	@Test(expected = EntidadeEmUsoException.class)
	public void deveFalhar_quandoExcluirCozinhaEmUso(){
		Long cozinhaId = 1L;

		cozinhaService.excluir(cozinhaId);
	}

	@Test(expected = CozinhaNaoEncontradaException.class)
	public void deveFalhar_quandoExcluirCozinhaInexistente(){
		Long cozinhaId = 10L;

		cozinhaService.excluir(cozinhaId);
	}

}
