package br.ufpe.cin.banco;

public class ContaJaCadastradaException extends Exception {
	
	public ContaJaCadastradaException() {
		super("J� existe uma conta cadastrada com este n�mero");
	}
}
