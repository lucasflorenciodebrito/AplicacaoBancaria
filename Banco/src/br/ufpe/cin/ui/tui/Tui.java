package br.ufpe.cin.ui.tui;

import java.util.InputMismatchException;
import java.util.Scanner;
import br.ufpe.cin.banco.Banco;
import br.ufpe.cin.banco.Conta;
import br.ufpe.cin.banco.ContaAbstrata;
import br.ufpe.cin.banco.ContaEspecial;
import br.ufpe.cin.banco.ContaImposto;
import br.ufpe.cin.banco.ContaJaCadastradaException;
import br.ufpe.cin.banco.OperacoesComValoresNegativosException;
import br.ufpe.cin.banco.Poupanca;
import br.ufpe.cin.banco.PoupancaEsp;
import br.ufpe.cin.banco.RenderBonusContaEspecialException;
import br.ufpe.cin.banco.RenderJurosPoupancaException;
import br.ufpe.cin.banco.SaldoInsuficienteException;
import br.ufpe.cin.dados.ContaNaoEncontradaException;
import br.ufpe.cin.dados.RepositorioArrayList;

public class Tui {
	
	Scanner input = new Scanner(System.in);
	ContaAbstrata conta = null;
	String numero, v;
	int tipo, tipo1;
	double valor;
	Banco banco = new Banco(new RepositorioArrayList(100));
	
	public void menu() throws OperacoesComValoresNegativosException, ContaJaCadastradaException, SaldoInsuficienteException {
			
		System.out.println("Digite: 1 - Cadastrar Conta, 2 - Creditar, 3- Debitar, 4 - Saldo, 5 - Render juros, 6 - Render Bônus, 7 - Transferência, 8 - Sair");
		try{
			tipo1 = input.nextInt();
		}catch(InputMismatchException e) {
			System.out.println("Você não digitou um inteiro");
		}	
		if (tipo1 < 0 || tipo1 > 8) {					
			System.out.println("Digite um número entre 1 e 8");
		}	
		
		switch(tipo1) {
			case 1: 
				cadastrar();
				break;
			case 2:
				creditar();
				break;
			case 3:
				debitar();
				break;
			case 4: 
				saldo();
				break;
			case 5:
				renderJuros();
				break;
			case 6:
				renderBonus();
				break;
			case 7:
				transferir();
				break;
			case 8:
				System.exit(0);
				break;
			default: 
				input.nextLine();
				menu();
				break;
		}
	}
	
	private void cadastrar() throws ContaJaCadastradaException, OperacoesComValoresNegativosException, SaldoInsuficienteException {
		
		try {
			System.out.println("Digite 1- Conta, 2- Poupança, 3- Conta especial, 4- Conta com imposto, 5- Poupança Especial, 6 - Voltar para o menu inicial");
			tipo = input.nextInt();
			while(tipo < 0 || tipo > 6) {
				System.out.println("Inserir apenas números inteiros de 1 a 6");
				System.out.println("Digite 1- Conta, 2- Poupança, 3- Conta especial, 4- Conta com imposto, 5- Poupança Especial, 6 - Voltar para o menu inicial");
				tipo = input.nextInt();
			}
		}catch(InputMismatchException e ) {
			System.out.println("Você não digitou um inteiro");
			input.nextLine();
			cadastrar();
		}

		if(tipo == 6) {
			menu();
		}
		
		System.out.println("Digite o número da conta:");
		numero = input.next();
		System.out.println("Digite o valor:");
		valor = input.nextDouble();
		
		try {
			switch(tipo) {
				case 1: 
					conta = new Conta(numero, valor);
					break;
				case 2: 
					conta = new Poupanca(numero, valor);
					break;
				case 3: 
					conta = new ContaEspecial(numero, valor);
					break;
				case 4: 
					conta = new ContaImposto(numero, valor);
					break;
				case 5:
					conta = new PoupancaEsp(numero,valor);
					break;
				default: 
					break;
			}
			banco.cadastrar(conta);
			System.out.println("Conta cadastrada com sucesso");
		}catch(ContaJaCadastradaException e) {
			System.out.println(e.getMessage());
		} catch(OperacoesComValoresNegativosException e) {
			System.out.println(e.getMessage());
		}
		menu();
	}
	
	private void creditar() throws OperacoesComValoresNegativosException, ContaJaCadastradaException, SaldoInsuficienteException{
		System.out.println("Digite o número da conta:");
		numero = input.next();
		System.out.println("Digite o valor:");
		v = input.next();
		try{
			valor = Double.parseDouble(v);
			banco.creditar(numero, valor);
			System.out.println("Crédito executado com sucesso");
			menu();
		}catch (ContaNaoEncontradaException e) {
			System.out.println(e.getMessage());
		} catch(NumberFormatException e) {
			System.out.println("O valor deve ser numérico");			
		}
	}
	
	private void debitar() throws SaldoInsuficienteException, OperacoesComValoresNegativosException, ContaJaCadastradaException{
		System.out.println("Digite o número da conta:");
		numero = input.next();
		System.out.println("Digite o valor:");
		v = input.next();
		try {
			valor = Double.parseDouble(v);
			banco.debitar(numero, valor);
			System.out.println("Débito realizado com sucesso");;
		} catch (ContaNaoEncontradaException e) {
			System.out.println(e.getMessage());
		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		}catch(NumberFormatException e) {
			System.out.println("O valor deve ser numérico");
		}
		menu();
	}

	private void saldo() throws OperacoesComValoresNegativosException, ContaJaCadastradaException, SaldoInsuficienteException {
		System.out.println("Digite o número da conta:");
		numero = input.next();
		try {
			double saldo = banco.getSaldo(numero);
			System.out.println("O saldo da conta " + numero +" é " + saldo);
			menu();
		} catch (ContaNaoEncontradaException e) {
			System.out.println(e.getMessage());
		}catch (OperacoesComValoresNegativosException e) {
			System.out.println(e.getMessage());
		}		
	}
	
	private void renderJuros() throws ContaJaCadastradaException, SaldoInsuficienteException, OperacoesComValoresNegativosException {
		System.out.println("Digite o número da conta:");
		numero = input.next();
		try {
			banco.renderJuros(numero);
			System.out.println("Juros creditado com sucesso");		
		} catch (ContaNaoEncontradaException e) {
			System.out.println(e.getMessage());
		} catch (RenderJurosPoupancaException e) {
			System.out.println(e.getMessage());
		}catch (OperacoesComValoresNegativosException e) {
			System.out.println(e.getMessage());
		}
		menu();
	}
	
	private void renderBonus() throws ContaJaCadastradaException, SaldoInsuficienteException, OperacoesComValoresNegativosException {
		System.out.println("Digite o número da conta:");
		numero = input.next();
		try {
			banco.renderBonus(numero);
			System.out.println("Bônus creditado com sucesso");
		} catch (ContaNaoEncontradaException e) {
			System.out.println("Conta não encontrada");
		} catch (RenderBonusContaEspecialException e) {
			System.out.println(e.getMessage());
		} catch (OperacoesComValoresNegativosException e) {
			System.out.println(e.getMessage());
		}
		menu();
	}
	
	private void transferir() throws ContaJaCadastradaException, OperacoesComValoresNegativosException, SaldoInsuficienteException {
		String de;
		String para;
		System.out.println("De:");
		de = input.next();
		System.out.println("Digite o valor:");
		v = input.next();
		
		try {
			double valor = Double.parseDouble(v);
			System.out.println("Informe o número da conta de destino:");
			para = input.next();
			banco.transferir(de, para, valor);
			System.out.println("Transferência executada com sucesso");		
		} catch (NumberFormatException e) {
			System.out.println("Apenas valores numéricos");
		} catch (ContaNaoEncontradaException e) {
			System.out.println(e.getMessage());
		} catch (SaldoInsuficienteException e) {
			System.out.println(e.getMessage());
		}catch (OperacoesComValoresNegativosException e) {
			System.out.println(e.getMessage());
		}
		menu();
	}

	public static void main(String[] args) throws OperacoesComValoresNegativosException, ContaJaCadastradaException, SaldoInsuficienteException {
		
		Tui m = new Tui();
		m.menu();		
	}
}