package br.ufpe.cin.banco;

import br.ufpe.cin.dados.ContaNaoEncontradaException;

/**
 * Modifique a classe Conta para lancar a excecao SaldoInsuficienteException 
 * e ajuste das demais classes da aplicacao
 *
 */
public abstract class ContaAbstrata {
	private String numero;
	private double saldo;
	
	public ContaAbstrata(String numero, double valor) { //Construtor de dois parâmetros
		this.numero = numero;
		this.saldo = valor;
	}
	
	public ContaAbstrata(String numero) { //Construtor de um parâmetro
		this(numero, 0.0);
	}
	
	public String getNumero() {
		return this.numero;
	}
	
	public double getSaldo() {
		return this.saldo;
	}
	
	public void creditar(double valor) throws ContaNaoEncontradaException, OperacoesComValoresNegativosException{
		if(valor < 0) 
			throw new OperacoesComValoresNegativosException(valor);
		this.saldo = this.saldo + valor;
	}
	
	public abstract void debitar(double valor) throws ContaNaoEncontradaException, SaldoInsuficienteException, OperacoesComValoresNegativosException;

	protected void setSaldo(double saldo) {	
		this.saldo = saldo;
	}
		
}
