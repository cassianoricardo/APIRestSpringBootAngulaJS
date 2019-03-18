package br.com.castgroup.desafio;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.castgroup.desafio.model.Pessoa;
import br.com.castgroup.desafio.repository.PessoaRepository;

@SpringBootApplication
public class DesafioApplication implements CommandLineRunner  {
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		Pessoa p1 = new Pessoa("Teste A","R. da Aurora",1259,"Santo Amaro","Recife","Pernambuco",8121254100l,81999999999l);
		Pessoa p2 = new Pessoa("Teste B","R. da Aurora",1259,"Santo Amaro","Recife","Pernambuco",8121254100l,81999999999l);
		Pessoa p3 = new Pessoa("Teste C","R. da Aurora",1259,"Santo Amaro","Recife","Pernambuco",8121254100l,81999999999l);
		
		pessoaRepository.saveAll(Arrays.asList(p1,p2,p3));
	}

}
