package br.com.castgroup.Desafio.integration;

import org.junit.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.castgroup.desafio.model.Pessoa;
import br.com.castgroup.desafio.repository.PessoaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PessoaControllerTest {

	final String BASE_URI = "http://localhost:8080/rest";

	@Autowired
	private PessoaRepository pessoaRepository;

	private RestTemplate restTemplate = new RestTemplate();

	public static long idPessoa;
	
	@Test
	public void testListarPessoas() {
		pessoaRepository.save(new Pessoa("fernanda"));
		pessoaRepository.save(new Pessoa("cassiano"));
		
		ResponseEntity<Pessoa[]> responseEntity = restTemplate.getForEntity(BASE_URI + "/pessoas", Pessoa[].class);
		
		Assert.assertEquals(2, responseEntity.getBody().length);
	}

	@Test
	public void testConsultarPessoaPorID() {
		ResponseEntity<Pessoa> responseEntity = restTemplate.getForEntity(BASE_URI + "/pessoa/" + idPessoa,
				Pessoa.class);
		
		Assert.assertEquals("teste", responseEntity.getBody().getNome());
		
	}

	@Test
	public void testSalvarPessoa() {
		pessoaRepository.deleteAll();
	
		
		ResponseEntity<Pessoa> responseEntity = restTemplate
				.postForEntity(BASE_URI + "/pessoa/salvar/", new Pessoa("teste"), Pessoa.class);
		Assert.assertEquals("teste",  responseEntity.getBody().getNome());
		idPessoa = responseEntity.getBody().getId();
	}
	
	@Test
	public void testRemoverPessoa() {
		
		restTemplate.delete(BASE_URI + "/pessoa/remover/"+ idPessoa, Pessoa.class );
	}

}
