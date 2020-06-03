package br.ufpe.cin.banco;

import br.ufpe.cin.dados.ContaNaoEncontradaException;

public class ContaEspecial extends Conta {
	
	private double bonus;

	public ContaEspecial(String numero, double valor) throws OperacoesComValoresNegativosException{ //Construtor com dois parâmetros
		super(numero, valor);
		bonus = 0.0;
	}
	
	public ContaEspecial(String numero) throws OperacoesComValoresNegativosException{ //Construtor com um parâmetro
		this(numero, 0.0);
	}
    
	@Override
	public void creditar(double valor) throws OperacoesComValoresNegativosException, ContaNaoEncontradaException {
		super.creditar(valor);
		bonus = bonus + (valor * 0.01);
	}

	public void renderBonus() throws OperacoesComValoresNegativosException, ContaNaoEncontradaException {
		super.creditar(this.bonus);
		bonus = 0;
	}

	public double getBonus() {
		return this.bonus;
	}
}