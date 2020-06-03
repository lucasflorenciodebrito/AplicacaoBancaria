package br.ufpe.cin.dados;

/**
 * Defina a excecao ContaNaoEncontradaException no 
 * pacote aula16.br.ufpe.cin.dados
 *
 */
public class ContaNaoEncontradaException extends Exception {

	public ContaNaoEncontradaException() {
		super("Conta não encontrada");
	}
}
