package br.ufpe.cin.banco;

import br.ufpe.cin.dados.ContaNaoEncontradaException;

public class Poupanca extends Conta {
	
	public Poupanca(String numero, double valor) throws OperacoesComValoresNegativosException{ //Construtor com dois parâmetros
		super(numero, valor);
	}
	
	public Poupanca(String numero) throws OperacoesComValoresNegativosException{ //Construtor com um parâmetro
		this(numero, 0.0);
	}
	
	public void renderJuros(double taxa) throws OperacoesComValoresNegativosException, ContaNaoEncontradaException {
		double juros = this.getSaldo() * taxa;
		this.creditar(juros);
	}
}
