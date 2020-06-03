package br.ufpe.cin.banco;

import br.ufpe.cin.dados.ContaNaoEncontradaException;

public class PoupancaEsp extends Poupanca {
	
	public PoupancaEsp(String numero, double saldoInicial) throws OperacoesComValoresNegativosException{
		super(numero, saldoInicial);
	}
	
	public PoupancaEsp(String numero) throws OperacoesComValoresNegativosException{
		super(numero);
	}

	@Override
	public void renderJuros(double taxa) throws OperacoesComValoresNegativosException, ContaNaoEncontradaException {
		double juros = (this.getSaldo() * (taxa + 0.10));
		this.creditar(juros);
	}
	
	
	
	
}
