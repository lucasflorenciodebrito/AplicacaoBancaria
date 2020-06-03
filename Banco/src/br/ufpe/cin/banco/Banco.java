package br.ufpe.cin.banco;

import br.ufpe.cin.dados.ContaNaoEncontradaException;
import br.ufpe.cin.dados.RepositorioContas;

/**
 * O metodo cadastrar da classe Banco deve lancar a excecao 
 * ContaJaCadastradaException quando ja existir uma conta com 
 * o mesmo numero da conta a ser cadastrada
 * 
 * Crie e utilize excecoes especificas em outros casos onde esta
 * sendo utilizada RuntimeException
 */
public class Banco {

	private RepositorioContas contas;
	
	private final double TAXA = 0.008;

	public Banco(RepositorioContas rep) { //Construtor da classe
		this.contas = rep;
	}
	//Por que aqui é preciso usar condicional e nos abaixo não
	
	public void cadastrar(ContaAbstrata conta) throws ContaJaCadastradaException, OperacoesComValoresNegativosException { 
		String numero = conta.getNumero();
		
		if (contas.existe(numero)) {
			throw new ContaJaCadastradaException();
		} else {
			if(conta.getSaldo() < 0) {
				throw new OperacoesComValoresNegativosException(conta.getSaldo());
			}else {
			contas.inserir(conta);
		    }
		}
	}

	public void creditar(String numero, double valor) throws ContaNaoEncontradaException, OperacoesComValoresNegativosException, SaldoInsuficienteException{
		ContaAbstrata c = contas.procurar(numero);
		c.creditar(valor);
	}

	public void debitar(String numero, double valor) throws ContaNaoEncontradaException, SaldoInsuficienteException, OperacoesComValoresNegativosException {
		ContaAbstrata c = contas.procurar(numero);
		c.debitar(valor);
	}

	public double getSaldo(String numero) throws ContaNaoEncontradaException, SaldoInsuficienteException, OperacoesComValoresNegativosException{
		ContaAbstrata c = contas.procurar(numero);
		return c.getSaldo();
	}

	public void transferir(String de, String para, double valor) throws ContaNaoEncontradaException, SaldoInsuficienteException, OperacoesComValoresNegativosException {
		ContaAbstrata origem = contas.procurar(de);
		ContaAbstrata destino = contas.procurar(para);
		origem.debitar(valor);
		destino.creditar(valor);
	}
	
	public void renderJuros(String numero) throws ContaNaoEncontradaException, RenderJurosPoupancaException, OperacoesComValoresNegativosException, SaldoInsuficienteException{
		ContaAbstrata c = contas.procurar(numero);
		if (c instanceof Poupanca) {
			((Poupanca) c).renderJuros(TAXA);
		} 
		else {
			throw new RenderJurosPoupancaException();
		}
	}

	public void renderBonus(String numero) throws ContaNaoEncontradaException, RenderBonusContaEspecialException, OperacoesComValoresNegativosException, SaldoInsuficienteException{
		ContaAbstrata c = contas.procurar(numero);
		if (c instanceof ContaEspecial) {
			((ContaEspecial) c).renderBonus();
		} 
		else {
			throw new RenderBonusContaEspecialException();
		}
	}

}