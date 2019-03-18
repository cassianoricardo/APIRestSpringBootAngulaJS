package br.com.castgroup.Desafio.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.castgroup.desafio.controller.PessoaController;
import br.com.castgroup.desafio.exception.PessoaNotFoundException;
import br.com.castgroup.desafio.model.Pessoa;
import br.com.castgroup.desafio.repository.PessoaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PessoaControllerTest {

	final String BASE_URI = "http://localhost:8080/rest";

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaController pessoaController;

	private RestTemplate restTemplate = new RestTemplate();

	public static long sequence = 0;
	
	@Before
	public void limpaBase() {
		pessoaRepository.deleteAll();
	}
	
	@Test
	public void testListarPessoas() {
		geraMassaDeTest();
		geraMassaDeTest();
		
		ResponseEntity<Pessoa[]> responseEntity = restTemplate.getForEntity(BASE_URI + "/pessoas", Pessoa[].class);
		
		Assert.assertEquals(2, responseEntity.getBody().length);
	}

	@Test
	public void testConsultarPessoaPorID() {
		geraMassaDeTest();
		try {
		ResponseEntity<Pessoa> responseEntity = restTemplate.getForEntity(BASE_URI + "/pessoa/" + sequence,
				Pessoa.class);
			Assert.assertEquals("teste", responseEntity.getBody().getNome());
		}catch(HttpClientErrorException ex) {
			Assert.assertFalse(ex.getStatusText().equals(HttpStatus.NOT_FOUND.toString()));
		}
		
	}

	@Test
	public void testSalvarPessoa() {
		
		ResponseEntity<Pessoa> responseEntity = restTemplate
				.postForEntity(BASE_URI + "/pessoa/salvar/", new Pessoa("teste"), Pessoa.class);
		Assert.assertEquals("teste",  responseEntity.getBody().getNome());
	}
	
	@Test
	public void testRemoverPessoa() {
		geraMassaDeTest();
		restTemplate.delete(BASE_URI + "/pessoa/remover/"+ sequence, Pessoa.class );
		try {
			pessoaController.consultarPessoaPorID(sequence);
			Assert.assertFalse("Deveria lançar uma excecao pois o id não deveria constar na base",true);
		} catch (PessoaNotFoundException e) {
			Assert.assertTrue(e.getMessage().equals("Não consta nenhuma pessoa com esse id"));
		}
	}
	
	public void geraMassaDeTest() {
		pessoaRepository.save(new Pessoa("teste"+sequence+1));
		sequence +=1; 
	}

}
