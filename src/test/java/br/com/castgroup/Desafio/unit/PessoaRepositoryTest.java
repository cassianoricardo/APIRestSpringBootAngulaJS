package br.com.castgroup.Desafio.unit;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.castgroup.desafio.controller.PessoaController;
import br.com.castgroup.desafio.model.Pessoa;
import br.com.castgroup.desafio.repository.PessoaRepository;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
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
			Pessoa pessoa = pessoaController.consultarPessoaPorID(Mockito.anyLong());
			Assert.assertFalse("Ao tentar consulta alguma pessoa que não existe deveria lançar uma excesao",
					pessoa != null);
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().equals("Não consta nenhuma pessoa com esse id"));
		}
	}

	@Test
	public void quandoBuscarUmaPessoaQueExiste() {
		Pessoa mock = Mockito.mock(Pessoa.class);
		when(pessoaRepository.findById(Mockito.anyLong())).thenReturn(mock);
		try {
			Pessoa pessoa = pessoaController.consultarPessoaPorID(Mockito.anyLong());
			Assert.assertTrue("Pessoa consultada com sucesso", pessoa != null);
		} catch (RuntimeException e) {
			Assert.assertFalse("A consulta deveria retornar uma pessoa",
					e.getMessage().equals("Não consta nenhuma pessoa com esse id"));
		}
	}

	@Test
	public void quandoInserirUmaPessoaSemNome() {
		Pessoa mock = Mockito.mock(Pessoa.class);
		mock.setNome(null);
		when(pessoaRepository.save(mock)).thenReturn(mock);
		try {
			Pessoa pessoa = pessoaController.salvarPessoa(mock);
			Assert.assertFalse("Ao tentar cadastra uma pessoa sem nome deveria lançar uma excesão", pessoa != null);
		} catch (RuntimeException e) {
			Assert.assertTrue("Validação de nome realizada com sucesso",
					e.getMessage().equals("O campo Nome é obrigatório"));
		}

	}

	@Test
	public void quandoInserirUmaPessoaComNome() {
		Pessoa mock = Mockito.mock(Pessoa.class);
		when(mock.getNome()).thenReturn("cassiano");
		when(pessoaRepository.save(mock)).thenReturn(mock);

		try {
			Pessoa pessoa = pessoaController.salvarPessoa(mock);
			Assert.assertTrue("Pessoa cadastrada com sucesso", pessoa != null);
		} catch (RuntimeException e) {
			Assert.assertFalse("Não deveria lançar excesão, pois o nome está preenchido",
					e.getMessage().equals("O campo Nome é obrigatório"));
		}

	}

	@Test
	public void quandoDeletarUmaPessoaComIdExistente() {

		try {
			pessoaController.removerPessoa(Mockito.anyLong());
			Assert.assertTrue("Pessoa removida com sucesso", true);
		} catch (RuntimeException e) {
			Assert.assertFalse("Não deveria lançar excesão, pois o id é de uma pessoa existente",
					e.getMessage().equals("Não consta nenhuma pessoa com esse id"));
		}

	}

	@Test
	public void quandoDeletarUmaPessoaSemIdExistente() {
		
		doThrow(new EmptyResultDataAccessException(1)).when(pessoaRepository).deleteById(Mockito.anyLong());
		
		try {
			pessoaController.removerPessoa(Mockito.anyLong());
			Assert.assertFalse("deveria lançar uma excesão, pois o id da pessoa a remover não existe", true);
		} catch (RuntimeException e) {
			Assert.assertTrue("Validação de pessoa com id Inexistente realizada com sucesso",
					e.getMessage().equals("Não consta nenhuma pessoa com esse id"));
		}
	}

}
