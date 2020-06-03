package br.ufpe.cin.banco;


public class Conta extends ContaAbstrata {
	
	public Conta(String numero, double valor) throws OperacoesComValoresNegativosException{ //Construtor com dois parâmetros
		super(numero, valor);
	}
	
	public Conta(String numero) throws OperacoesComValoresNegativosException{ //Construtor com um parâmetro
		super(numero, 0.0);
	}

	public void debitar(double valor) throws SaldoInsuficienteException, OperacoesComValoresNegativosException { //Implementação do método abstrato
		if(valor < 0)
			throw new OperacoesComValoresNegativosException(valor);
		if (this.getSaldo() < valor) //Só entrará no if se o saldo for menor que o valor a ser debitado
			throw new SaldoInsuficienteException(this.getNumero(), this.getSaldo());
		this.setSaldo(this.getSaldo() - valor);
	}
		
}