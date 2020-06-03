package br.ufpe.cin.banco;

public class ContaJaCadastradaException extends Exception {
	
	public ContaJaCadastradaException() {
		super("Já existe uma conta cadastrada com este número");
	}
}
