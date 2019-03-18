package br.com.castgroup.Desafio.unit;


import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.castgroup.desafio.controller.PessoaController;
import br.com.castgroup.desafio.exception.PessoaNotFoundException;
import br.com.castgroup.desafio.model.Pessoa;
import br.com.castgroup.desafio.repository.PessoaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PessoaRepositoryTest {

	@InjectMocks
	PessoaController pessoaController;

	@Mock
	PessoaRepository pessoaRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void quandoBuscarUmaPessoaQueNaoExiste() {

		when(pessoaRepository.findById(Mockito.anyLong())).thenReturn(null);
		try {
			ResponseEntity<Pessoa> response = pessoaController.consultarPessoaPorID(Mockito.anyLong());
			Assert.assertFalse("Ao tentar consulta alguma pessoa que não existe deveria lançar uma excesao",
					null != response.getBody() );
		} catch (PessoaNotFoundException e) {
			Assert.assertTrue(e.getMessage().equals("Não consta nenhuma pessoa com esse id"));
		}
	}

	@Test
	public void quandoBuscarUmaPessoaQueExiste() {
		Pessoa mock = Mockito.mock(Pessoa.class);
		when(pessoaRepository.findById(Mockito.anyLong())).thenReturn(mock);
		try {
			ResponseEntity<Pessoa> response = pessoaController.consultarPessoaPorID(Mockito.anyLong());
			Assert.assertTrue( null != response.getBody());
		} catch (PessoaNotFoundException e) {
			Assert.assertFalse("A consulta deveria retornar uma pessoa",
					e.getMessage().equals("Não consta nenhuma pessoa com esse id"));
		}
	}

	@Test
	public void quandoInserirUmaPessoaSemNome() {
		Pessoa mock = Mockito.mock(Pessoa.class);
		when(mock.getNome()).thenReturn(null);
		when(pessoaRepository.save(mock)).thenReturn(mock);
		try {
			Pessoa pessoa = pessoaController.salvarPessoa(mock);
			Assert.assertFalse("Ao tentar cadastra uma pessoa sem nome deveria lançar uma excesão", null  != pessoa );
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().equals("O campo nome é obrigatório"));
		}

	}

	@Test
	public void quandoInserirUmaPessoaComNome() {
		Pessoa mock = Mockito.mock(Pessoa.class);
		when(mock.getNome()).thenReturn("cassiano");
		when(pessoaRepository.save(mock)).thenReturn(mock);

		try {
			Pessoa pessoa = pessoaController.salvarPessoa(mock);
			Assert.assertTrue( pessoa != null);
		} catch (RuntimeException e) {
			Assert.assertFalse("Não deveria lançar excesão, pois o nome está preenchido",
					e.getMessage().equals("O campo Nome é obrigatório"));
		}

	}

	@Test
	public void quandoDeletarUmaPessoaComIdExistente() {
		Pessoa mock = Mockito.mock(Pessoa.class);
		when(pessoaRepository.findById(Mockito.anyLong())).thenReturn(mock);
		try {
			pessoaController.removerPessoa(Mockito.anyLong());
			Assert.assertTrue(true);
		} catch (PessoaNotFoundException e) {
			Assert.assertFalse("Não deveria lançar excesão, pois o id é de uma pessoa existente",
					e.getMessage().equals("Não consta nenhuma pessoa com esse id"));
		}

	}

	@Test
	public void quandoDeletarUmaPessoaSemIdExistente() {
		when(pessoaRepository.findById(Mockito.anyLong())).thenReturn(null);
		try {
			pessoaController.removerPessoa(Mockito.anyLong());
			Assert.assertFalse("deveria lançar uma excesão, pois o id da pessoa a remover não existe", true);
		} catch (PessoaNotFoundException e) {
			Assert.assertTrue(e.getMessage().equals("Não consta nenhuma pessoa com esse id"));
		}
	}
	
	

}
