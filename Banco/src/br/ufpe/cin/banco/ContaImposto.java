package br.ufpe.cin.banco;

public class ContaImposto extends ContaAbstrata {
	public static final double CPMF = 0.0038;
	
	public ContaImposto(String numero, double valor) {
		super(numero, valor);
	}
	
	public ContaImposto(String numero) {
		this(numero, 0.0);
	}
	
	public void debitar(double valor) throws SaldoInsuficienteException, OperacoesComValoresNegativosException { //Implementa o método de modo diferente das demais contas
		if(valor < 0)
			throw new OperacoesComValoresNegativosException(valor);
		if (this.getSaldo() < valor) //Só entrará no if se o saldo for menor que o valor a ser debitado
			throw new SaldoInsuficienteException(this.getNumero(), this.getSaldo());
		double imposto = valor * CPMF;
	    double total = valor + imposto;
	    this.setSaldo(this.getSaldo() - total);
	}

}