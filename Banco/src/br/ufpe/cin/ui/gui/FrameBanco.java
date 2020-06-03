package br.ufpe.cin.ui.gui;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
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
import br.ufpe.cin.dados.RepositorioContasArray;

public class FrameBanco extends JFrame {

	private Banco fachada;
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton bt_cadastrar = null;
	private JButton bt_creditar = null;
	private JButton bt_debitar = null;
	private JButton bt_transferir = null;
	private JButton bt_saldo = null;
	private JButton bt_renderJuros = null;
	private JButton bt_renderBonus = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel = null;
	private JLabel jLabel2 = null;
	private JTextField tf_numero = null;
	private JTextField tf_valor = null;

	private JRadioButton rb_conta = null;

	private JRadioButton rb_poupanca = null;
	
	private JRadioButton rb_poupancaEsp = null;

	private JRadioButton rb_contaEspecial = null;

	private JRadioButton rb_contaImposto = null;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;
	/**
	 * This is the default constructor
	 */
	public FrameBanco() {
		super();
		initialize();
		
		fachada = new Banco(new RepositorioArrayList(100));
		
		//Veja como usar RadioButton em 
		//http://java.sun.com/j2se/1.5.0/docs/api/javax/swing/JRadioButton.html
		ButtonGroup bg = new ButtonGroup();
		bg.add(rb_conta);
		bg.add(rb_contaEspecial);
		bg.add(rb_contaImposto);
		bg.add(rb_poupanca);
		bg.add(rb_poupancaEsp);
	}

	private void erroConversao() {
		JOptionPane.showMessageDialog(this, "O valor deve ser numérico");
		tf_valor.setText("");
		tf_valor.requestFocus();
	}

	private void erroNumero() {
		erroNumero("Informe o número da conta desejada");
	}
	
	private void erroNumero(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem);
		tf_numero.selectAll();
		tf_numero.requestFocus();
	}
	
	private void erroSaldo(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem);
		tf_valor.selectAll();
		tf_valor.requestFocus();
	}
	
	private void sucesso(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem);
		tf_numero.setText("");
		tf_valor.setText("");
		tf_numero.requestFocus();
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(450, 250);
		this.setContentPane(getJContentPane());
		this.setTitle("Aplicação Bancária");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel2 =  new JLabel();
			jLabel2.setBounds(new Rectangle(120, 180, 200, 28));
			jLabel2.setText("Discentes: Jéssica e Lucas");
			jLabel.setBounds(new Rectangle(106, 110, 66, 28));
			jLabel.setText("Valor");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(106, 76, 66, 28));
			jLabel1.setText("Número");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getBt_creditar(), null);
			jContentPane.add(getBt_debitar(), null);
			jContentPane.add(getBt_transferir(), null);
			jContentPane.add(getBt_saldo(), null);
			jContentPane.add(getBt_renderJuros(), null);
			jContentPane.add(getBt_renderBonus(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanel1(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes bt_cadastrar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_cadastrar() {
		if (bt_cadastrar == null) {
			bt_cadastrar = new JButton();
			bt_cadastrar.setText("Cadastrar");
			bt_cadastrar.setBounds(new Rectangle(2, 14, 113, 29));
			bt_cadastrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						cadastrar();
					} catch (OperacoesComValoresNegativosException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return bt_cadastrar;
	}

	private void cadastrar() throws OperacoesComValoresNegativosException {
		ContaAbstrata conta = null;
		String numero = tf_numero.getText();
		String tipoConta = "";
		String v = tf_valor.getText();
		if (numero.equals("")) {
			erroNumero();
		} else {
			try {
				double valor = Double.parseDouble(v);
				if(rb_conta.isSelected()) {
					conta = new Conta(numero, valor);
					tipoConta = "Conta";
				} else if(rb_poupanca.isSelected()) {
					conta = new Poupanca(numero, valor);
					tipoConta = "Poupança";
				} else if(rb_contaEspecial.isSelected()) {
					conta = new ContaEspecial(numero, valor);
					tipoConta = "Conta Especial";
				} else if(rb_contaImposto.isSelected()) {
					conta = new ContaImposto(numero, valor);
					tipoConta = "Conta Imposto";
				} else if (rb_poupancaEsp.isSelected()){
					conta = new PoupancaEsp(numero, valor);
					tipoConta = "Poupança Especial";
				}
				fachada.cadastrar(conta);
				sucesso(tipoConta +" cadastrada com sucesso");
			} catch (NumberFormatException e) {
				erroConversao();
			} catch (ContaJaCadastradaException e) {
				erroNumero(e.getMessage());
			}catch(OperacoesComValoresNegativosException e) {
				erroNumero(e.getMessage());
			}
		}
	}


	/**
	 * This method initializes bt_creditar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_creditar() {
		if (bt_creditar == null) {
			bt_creditar = new JButton();
			bt_creditar.setBounds(new Rectangle(5, 75, 88, 29));
			bt_creditar.setText("Creditar");
			bt_creditar.setBackground(new Color (11,229,69));
			bt_creditar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						creditar();
					} catch (SaldoInsuficienteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return bt_creditar;
	}

	private void creditar() throws  SaldoInsuficienteException {
		String numero = tf_numero.getText();
		String v = tf_valor.getText();
		if (numero.equals("")) {
			erroNumero();
		} else {
			try {
				double valor = Double.parseDouble(v);	
				fachada.creditar(numero, valor);
				sucesso("Crédito executado com sucesso");
			} catch (NumberFormatException e) {
				erroConversao();
			} catch (ContaNaoEncontradaException e) {
				erroNumero(e.getMessage());
			}catch (OperacoesComValoresNegativosException e) {
				erroNumero(e.getMessage());
			}
		}
	}

	/**
	 * This method initializes bt_debitar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_debitar() {
		if (bt_debitar == null) {
			bt_debitar = new JButton();
			bt_debitar.setBounds(new Rectangle(5, 109, 88, 29));
			bt_debitar.setText("Debitar");
			bt_debitar.setBackground(new Color (229,11,11));
			bt_debitar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					debitar();
				}
			});
		}
		return bt_debitar;
	}

	private void debitar() {
		String numero = tf_numero.getText();
		String v = tf_valor.getText();
		if (numero.equals("")) {
			erroNumero();
		} else {
			try {
				double valor = Double.parseDouble(v);	
				fachada.debitar(numero, valor);
				sucesso("Débito executado com sucesso");
			} catch (NumberFormatException e) {
				erroConversao();
			} catch (ContaNaoEncontradaException e) {
				erroNumero(e.getMessage());
			} catch (SaldoInsuficienteException e) {
				erroSaldo(e.getMessage());
			}catch (OperacoesComValoresNegativosException e) {
				erroSaldo(e.getMessage());
			}
		}
	}

	/**
	 * This method initializes bt_transferir	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_transferir() {
		if (bt_transferir == null) {
			bt_transferir = new JButton();
			bt_transferir.setBounds(new Rectangle(287, 109, 88, 29));
			bt_transferir.setText("Transferir");
			bt_transferir.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					transferir();
				}
			});
		}
		return bt_transferir;
	}

	private void transferir() {
		String de = tf_numero.getText();
		String v = tf_valor.getText();
		String para = null;
		if (de.equals("")) {
			erroNumero();
		} else {
			try {
				double valor = Double.parseDouble(v);
				do {
					para = JOptionPane.showInputDialog(this, "Informe o número da conta de destino");
				} while (para.equals(""));
				fachada.transferir(de, para, valor);
				sucesso("Transferência executada com sucesso");
			} catch (NumberFormatException e) {
				erroConversao();
			} catch (ContaNaoEncontradaException e) {
				erroNumero(e.getMessage());
			} catch (SaldoInsuficienteException e) {
				erroSaldo(e.getMessage());
			}
			catch (OperacoesComValoresNegativosException e) {
				erroSaldo(e.getMessage());
			}
		}
	}

	/**
	 * This method initializes bt_saldo	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_saldo() {
		if (bt_saldo == null) {
			bt_saldo = new JButton();
			bt_saldo.setBounds(new Rectangle(287, 75, 88, 29));
			bt_saldo.setText("Saldo");
			bt_saldo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						saldo();
					} catch (SaldoInsuficienteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (OperacoesComValoresNegativosException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return bt_saldo;
	}

	private void saldo() throws  SaldoInsuficienteException, OperacoesComValoresNegativosException {
		String numero = tf_numero.getText();
		if (numero.equals("")) {
			erroNumero();
		} else {
			try {
				double saldo = fachada.getSaldo(numero);
				sucesso("O saldo da conta "+ numero+" é "+saldo);
			} catch (ContaNaoEncontradaException e) {
				erroNumero(e.getMessage());
			}
		}
	}

	/**
	 * This method initializes bt_renderJuros	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_renderJuros() {
		if (bt_renderJuros == null) {
			bt_renderJuros = new JButton();
			bt_renderJuros.setBounds(new Rectangle(217, 148, 113, 29));
			bt_renderJuros.setText("Render juros");
			bt_renderJuros.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						renderJuros();
					} catch (SaldoInsuficienteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return bt_renderJuros;
	}

	private void renderJuros() throws  SaldoInsuficienteException {
		String numero = tf_numero.getText();
		if (numero.equals("")) {
			erroNumero();
		} else {
			try {
				fachada.renderJuros(numero);
				sucesso("Juros creditado com sucesso");
			} catch (ContaNaoEncontradaException e) {
				erroNumero(e.getMessage());
			} catch (RenderJurosPoupancaException e) {
				erroNumero(e.getMessage());
			}catch (OperacoesComValoresNegativosException e) {
				erroNumero(e.getMessage());
			}
		}
	}

	/**
	 * This method initializes bt_renderBonus	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBt_renderBonus() {
		if (bt_renderBonus == null) {
			bt_renderBonus = new JButton();
			bt_renderBonus.setBounds(new Rectangle(52, 148, 113, 29));
			bt_renderBonus.setText("Render bônus");
			bt_renderBonus.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						renderBonus();
					} catch (SaldoInsuficienteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return bt_renderBonus;
	}

	private void renderBonus() throws  SaldoInsuficienteException {
		String numero = tf_numero.getText();
		if (numero.equals("")) {
			erroNumero();
		} else {
			try {
				fachada.renderBonus(numero);
				sucesso("Bônus creditado com sucesso");
			} catch (ContaNaoEncontradaException e) {
				erroNumero(e.getMessage());
			} catch (RenderBonusContaEspecialException e) {
				erroNumero(e.getMessage());
			}catch (OperacoesComValoresNegativosException e) {
				erroNumero(e.getMessage());
			}
		}
	}

	/**
	 * This method initializes tf_numero	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTf_numero() {
		if (tf_numero == null) {
			tf_numero = new JTextField();
			tf_numero.setToolTipText("Número da conta a ser operada (se transferência, conta de origem)");
			tf_numero.setBounds(new Rectangle(0
					, 7, 103, 28));
		}
		return tf_numero;
	}

	/**
	 * This method initializes tf_valor	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getTf_valor() {
		if (tf_valor == null) {
			tf_valor = new JTextField();
			tf_valor.setToolTipText("Valor a ser utilizado pelas operações");
			tf_valor.setBounds(new Rectangle(0, 41, 103, 28));
		}
		return tf_valor;
	}

	/**
	 * This method initializes rb_conta	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRb_conta() {
		if (rb_conta == null) {
			rb_conta = new JRadioButton();
			rb_conta.setText("Conta");
			rb_conta.setSelected(true);
			rb_conta.setBounds(new Rectangle(125, 5, 95, 23));
		}
		return rb_conta;
	}
	
	private JRadioButton getRb_poupancaEsp() {
		if(rb_poupancaEsp == null) {
			rb_poupancaEsp = new JRadioButton();
			rb_poupancaEsp.setText("Poupança Especial");
			rb_poupancaEsp.setSelected(true);
			rb_poupancaEsp.setBounds(new Rectangle(125, 5, 95, 23));
		}
		
		return rb_poupancaEsp;
	}

	/**
	 * This method initializes rb_Poupanca	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRb_Poupanca() {
		if (rb_poupanca == null) {
			rb_poupanca = new JRadioButton();
			rb_poupanca.setText("Poupança");
			rb_poupanca.setBounds(new Rectangle(125, 32, 95, 23));
		}
		return rb_poupanca;
	}

	/**
	 * This method initializes rb_contaEspecial	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRb_contaEspecial() {
		if (rb_contaEspecial == null) {
			rb_contaEspecial = new JRadioButton();
			rb_contaEspecial.setText("Conta Especial");
			rb_contaEspecial.setBounds(new Rectangle(223, 5, 131, 23));
		}
		return rb_contaEspecial;
	}

	/**
	 * This method initializes rb_contaImposto	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getRb_contaImposto() {
		if (rb_contaImposto == null) {
			rb_contaImposto = new JRadioButton();
			rb_contaImposto.setText("Conta Imposto");
			rb_contaImposto.setBounds(new Rectangle(223, 32, 131, 23));
		}
		return rb_contaImposto;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setBounds(new Rectangle(16, 9, 400, 65));
			jPanel.setBackground(new Color(224,224,224));
			jPanel.add(getBt_cadastrar(), null);
			jPanel.add(getRb_conta(), null);
			jPanel.add(getRb_Poupanca(), null);
			jPanel.add(getRb_contaEspecial(), null);
			jPanel.add(getRb_contaImposto(), null);
			jPanel.add(getRb_poupancaEsp());
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(175, 71, 103, 76));
			jPanel1.add(getTf_valor(), null);
			jPanel1.add(getTf_numero(), null);
		}
		return jPanel1;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"