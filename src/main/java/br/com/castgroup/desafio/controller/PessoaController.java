package br.com.castgroup.desafio.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.castgroup.desafio.exception.PessoaNotFoundException;
import br.com.castgroup.desafio.model.Pessoa;
import br.com.castgroup.desafio.repository.PessoaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/rest")
@Api(value = "API REST Pessoa")
@CrossOrigin(origins = "*")
public class PessoaController {

	@Autowired
	PessoaRepository pessoaRepository;

	@GetMapping("/pessoas")
	@ApiOperation(value = "Retorna uma lista de Pessoas")
	public List<Pessoa> listarPessoas() {
		return pessoaRepository.findAll();
	}

	@GetMapping("/pessoa/{id}")
	@ApiOperation(value = "Retorna uma unica pessoa")
	@ExceptionHandler(PessoaNotFoundException.class)
	public Pessoa consultarPessoaPorID(@PathVariable(value = "id") long id) {
		Pessoa pessoa = null;

		pessoa = pessoaRepository.findById(id);
		if (pessoa == null) {
			throw new PessoaNotFoundException("Não consta nenhuma pessoa com esse id");
		}
		return pessoa;
	}

	@PostMapping("/pessoa/salvar")
	@ApiOperation(value = "salva uma Pessoa")
	public Pessoa salvarPessoa(@Valid @RequestBody Pessoa pessoa) {

		if (null == pessoa.getNome()) {
			throw new RuntimeException("O campo nome é obrigatório");
		}
		return pessoaRepository.save(pessoa);
	}

	@DeleteMapping("/pessoa/remover/{id}")
	@ApiOperation(value = "deleta uma Pessoa")
	public void removerPessoa(@PathVariable(value = "id") long id) {
		try {
			pessoaRepository.deleteById(id);
		}catch(EmptyResultDataAccessException ex) {
			throw new RuntimeException("Não consta nenhuma pessoa com esse id");
		}
		
	}
}
