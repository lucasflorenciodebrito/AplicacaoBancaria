package br.ufpe.cin.banco;

public class OperacoesComValoresNegativosException extends Exception{
	
	public OperacoesComValoresNegativosException (double valor) {
		super("O valor n�o pode ser negativo "+valor);
	}
}
