package br.com.castgroup.desafio.exception;

public class PessoaNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public PessoaNotFoundException( String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
	
	public PessoaNotFoundException(String mensagem) {
		super(mensagem);
	}
}
