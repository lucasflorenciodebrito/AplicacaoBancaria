package br.ufpe.cin.dados;

import java.util.ArrayList;

import br.ufpe.cin.banco.ContaAbstrata;
import br.ufpe.cin.banco.OperacoesComValoresNegativosException;
import br.ufpe.cin.banco.SaldoInsuficienteException;

public class RepositorioArrayList implements RepositorioContas {

	private ArrayList<ContaAbstrata> contas; //ArrayList de ContaAbstrata 
	private int indice;
	
	public RepositorioArrayList (int tamanho) { //Contrutor com um parâmetro
		contas = new ArrayList<ContaAbstrata>();
		indice = 0;
	}
	
	public void inserir(ContaAbstrata conta) {
		contas.add(conta);
		indice = indice + 1;
	}

	public ContaAbstrata procurar(String numero) throws ContaNaoEncontradaException, SaldoInsuficienteException, OperacoesComValoresNegativosException {
		ContaAbstrata resposta = null;
		int i = this.getIndice(numero);
		if (i == this.indice) {
			throw new ContaNaoEncontradaException();
		} else {
			resposta = contas.get(i);
		}
		return resposta;
	}
	public void remover(String numero) throws ContaNaoEncontradaException {
		int i = this.getIndice(numero);
		if (i == this.indice) {
			throw new ContaNaoEncontradaException();
		} else {
			this.indice = this.indice - 1;
			contas.remove(i);
		}
	}

	public void atualizar(ContaAbstrata conta) throws ContaNaoEncontradaException {
		int i = this.getIndice(conta.getNumero());
		if (i == this.indice) {
			throw new ContaNaoEncontradaException();
		} else {
			contas.set(i,conta);
		}
	}

	public boolean existe(String numero) {
		int i = this.getIndice(numero);
		return (i != this.indice);
	}

	private int getIndice(String numero) {
		String n;
		boolean achou = false;
		int i = 0;
		while ((!achou) && (i < this.indice)) {
			n = contas.get(i).getNumero();
			if (n.equals(numero)) {
				achou = true;
			} else {
				i = i + 1;
			}
		}
		return i;
	}
}
