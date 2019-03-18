package br.com.castgroup.desafio.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus (value = HttpStatus.NOT_FOUND, reason="Não consta nenhuma pessoa com esse id")  // 404
public class PessoaNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public PessoaNotFoundException( String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	public PessoaNotFoundException(String mensagem) {
		super(mensagem);
	}
}
