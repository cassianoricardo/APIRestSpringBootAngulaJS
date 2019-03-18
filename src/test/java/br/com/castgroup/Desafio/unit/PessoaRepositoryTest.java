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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.castgroup.desafio.controller.PessoaController;
import br.com.castgroup.desafio.exception.PessoaNotFoundException;
import br.com.castgroup.desafio.model.Pessoa;
import br.com.castgroup.desafio.repository.PessoaRepository;

@RunWith(SpringRunner.class)
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

		when(pessoaRepository.findById(Mockito.anyInt())).thenReturn(null);
		try {
			ResponseEntity<Pessoa> response = pessoaController.consultarPessoaPorID(Mockito.anyInt());
			Assert.assertFalse("Ao tentar consulta alguma pessoa que não existe deveria lançar uma excesao",
					null != response.getBody() );
		} catch (PessoaNotFoundException e) {
			Assert.assertEquals("Não consta nenhuma pessoa com esse id",e.getMessage());
		}
	}

	@Test
	public void quandoBuscarUmaPessoaQueExiste() {
		Pessoa mock = Mockito.mock(Pessoa.class);
		when(pessoaRepository.findById(Mockito.anyInt())).thenReturn(mock);
		try {
			ResponseEntity<Pessoa> response = pessoaController.consultarPessoaPorID(Mockito.anyInt());
			Assert.assertTrue( null != response.getBody());
		} catch (PessoaNotFoundException e) {
			Assert.assertEquals("A consulta deveria retornar uma pessoa",
					"Não consta nenhuma pessoa com esse id",e.getMessage());
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
			Assert.assertEquals("O campo nome é obrigatório",e.getMessage());
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
			Assert.assertNotEquals("Não deveria lançar excesão, pois o nome está preenchido",
					"O campo Nome é obrigatório",e.getMessage());
		}
	}

	@Test
	public void quandoDeletarUmaPessoaComIdExistente() {
		Pessoa mock = Mockito.mock(Pessoa.class);
		when(pessoaRepository.findById(Mockito.anyInt())).thenReturn(mock);
		try {
			pessoaController.removerPessoa(Mockito.anyInt());
			Assert.assertTrue(true);
		} catch (PessoaNotFoundException e) {
			Assert.assertNotEquals("Não deveria lançar excesão, pois o id é de uma pessoa existente",
					"Não consta nenhuma pessoa com esse id",e.getMessage());
		}
	}

	@Test
	public void quandoDeletarUmaPessoaSemIdExistente() {
		when(pessoaRepository.findById(Mockito.anyInt())).thenReturn(null);
		try {
			pessoaController.removerPessoa(Mockito.anyInt());
			Assert.assertFalse("deveria lançar uma excesão, pois o id da pessoa a remover não existe", true);
		} catch (PessoaNotFoundException e) {
			Assert.assertEquals("Não consta nenhuma pessoa com esse id",e.getMessage());
		}
	}
}
