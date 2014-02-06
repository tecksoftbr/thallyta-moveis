// Tela concluída ...

package views.alterando.contas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.text.MaskFormatter;

import modelo.ContasPagar;

import fachada.FachadaContasPagar;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;

public class AlterandoConta extends JInternalFrame implements MouseListener,
		KeyListener {

	private static final long serialVersionUID = 1L;

	private JPanel painelPrincipal;

	private JLabel fundo, tipoConta, valor, numeroDocumento, origem,
			dataEntrada, dataVencimento, contaAlterada;

	private JTextField campoConta, campoValor, campoNumeroDocumento,
			campoOrigem;

	private JButton alterarAgora, limparTudo, cancelar;
	private Color corDotexto = new Color(139, 139, 139);

	private String numeroOriginal = "0";

	JFormattedTextField campoDataEntradaFormatado,
			campoDataVencimentoFormatado;

	public AlterandoConta(String tipo, String numeroDocumento,
			String dataDeEntrada, String dataDeVencimento, String Valor,
			String origem) {

		// Definindo layout da tela ...

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		try {

			painelPrincipal = new JPanel();
			painelPrincipal.setLayout(new BorderLayout());

			numeroOriginal = numeroDocumento;

			contaAlterada = new JLabel(

					new ImageIcon(
							AlterandoConta.class
									.getResource("/views/alterando/contas/jpg/Conta_Alterada.JPG")));

			contaAlterada.setBounds(0, 0, 672, 342);
			contaAlterada.setVisible(false);

			contaAlterada.addMouseListener(this);
			contaAlterada.addKeyListener(this);

			painelPrincipal.add(contaAlterada);

			campoDataEntradaFormatado = new JFormattedTextField(
					(setMascara("##/##/####")));

			campoDataEntradaFormatado.setForeground(corDotexto);
			campoDataEntradaFormatado.setBounds(547, 185, 101, 33);
			campoDataEntradaFormatado.setText(dataDeEntrada);

			campoDataEntradaFormatado
					.setToolTipText("Digite Aqui a Data De Entrada Que Esta Conta Foi Anúnciada");

			campoDataEntradaFormatado.addMouseListener(this);
			campoDataEntradaFormatado.addKeyListener(this);
			painelPrincipal.add(campoDataEntradaFormatado);

			// --------------------------------------------------------------------------------------------------------------

			campoDataVencimentoFormatado = new JFormattedTextField(
					(setMascara("##/##/####")));

			campoDataVencimentoFormatado.setText(dataDeVencimento);
			campoDataVencimentoFormatado.setForeground(corDotexto);
			campoDataVencimentoFormatado.setBounds(547, 226, 101, 33);

			campoDataVencimentoFormatado
					.setToolTipText("Digite Aqui a Data De Vencimento Desta Conta, No Dia Você Será Alertado");

			campoDataVencimentoFormatado.addMouseListener(this);
			campoDataVencimentoFormatado.addKeyListener(this);
			painelPrincipal.add(campoDataVencimentoFormatado);

			// --------------------------------------------------------------------------------------------------------------

			tipoConta = new JLabel("Tipo De Conta");

			tipoConta.setForeground(corDotexto);
			tipoConta.setFont(new Font("Dialog", Font.PLAIN, 15));
			tipoConta.setBounds(20, 190, 100, 25);

			tipoConta.addMouseListener(this);
			tipoConta.addKeyListener(this);
			painelPrincipal.add(tipoConta);

			// --------------------------------------------------------------------------------------------------------------

			campoConta = new JTextField();

			campoConta.setForeground(corDotexto);
			campoConta.setBounds(130, 186, 200, 33);
			campoConta.setText(tipo);

			campoConta
					.setToolTipText("Digite Aqui o Tipo Desta Conta, Se é Um Boleto, Cartão, Energia Ou Outros");

			campoConta.addMouseListener(this);
			campoConta.addKeyListener(this);
			painelPrincipal.add(campoConta);

			// --------------------------------------------------------------------------------------------------------------

			valor = new JLabel("Valor");

			valor.setForeground(corDotexto);
			valor.setFont(new Font("Dialog", Font.PLAIN, 15));
			valor.setBounds(500, 130, 40, 25);

			valor.addMouseListener(this);
			valor.addKeyListener(this);
			painelPrincipal.add(valor);

			// --------------------------------------------------------------------------------------------------------------

			campoValor = new JTextField();

			campoValor.addKeyListener(this);
			campoValor.setForeground(corDotexto);
			campoValor.setBounds(547, 126, 101, 33);
			campoValor.setText(Valor);

			campoValor
					.setToolTipText("Digite Aqui o Valor Desta Conta Seguindo o Exemplo: 1025.36");

			campoValor.addMouseListener(this);
			campoValor.addKeyListener(this);
			painelPrincipal.add(campoValor);

			// --------------------------------------------------------------------------------------------------------------

			this.numeroDocumento = new JLabel("Número Do Documento");

			this.numeroDocumento.setForeground(corDotexto);
			this.numeroDocumento.setFont(new Font("Dialog", Font.PLAIN, 15));
			this.numeroDocumento.setBounds(20, 130, 160, 25);

			this.numeroDocumento.addMouseListener(this);
			this.numeroDocumento.addKeyListener(this);
			this.painelPrincipal.add(this.numeroDocumento);

			// --------------------------------------------------------------------------------------------------------------

			campoNumeroDocumento = new JTextField();

			campoNumeroDocumento.setForeground(corDotexto);
			campoNumeroDocumento.setBounds(190, 126, 250, 33);
			campoNumeroDocumento.setText(numeroDocumento);

			campoNumeroDocumento
					.setToolTipText("Digite Aqui o Número Do Documento a Ser Alterado No Banco De Dados");

			campoNumeroDocumento.addMouseListener(this);
			campoNumeroDocumento.addKeyListener(this);
			painelPrincipal.add(campoNumeroDocumento);

			// --------------------------------------------------------------------------------------------------------------
			dataEntrada = new JLabel("Data Entrada");

			dataEntrada.setForeground(corDotexto);
			dataEntrada.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataEntrada.setBounds(384, 187, 100, 25);

			dataEntrada.addMouseListener(this);
			dataEntrada.addKeyListener(this);
			painelPrincipal.add(dataEntrada);

			// --------------------------------------------------------------------------------------------------------------

			this.origem = new JLabel("Origem");

			this.origem.setForeground(corDotexto);
			this.origem.setFont(new Font("Dialog", Font.PLAIN, 15));
			this.origem.setBounds(21, 230, 100, 25);

			this.origem.addMouseListener(this);
			this.origem.addKeyListener(this);
			this.painelPrincipal.add(this.origem);

			// --------------------------------------------------------------------------------------------------------------

			campoOrigem = new JTextField();

			campoOrigem.setForeground(corDotexto);
			campoOrigem.setBounds(130, 226, 200, 33);
			campoOrigem.setText(origem);

			campoOrigem
					.setToolTipText("Digite Aqui a Origem Desta Conta, Se é De Algum Fornecedor, Celpe Ou Outros");

			campoOrigem.addMouseListener(this);
			campoOrigem.addKeyListener(this);
			painelPrincipal.add(campoOrigem);

			// --------------------------------------------------------------------------------------------------------------

			dataVencimento = new JLabel("Data Vencimento");

			dataVencimento.setForeground(corDotexto);
			dataVencimento.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataVencimento.setBounds(384, 230, 120, 25);

			dataVencimento.addMouseListener(this);
			dataVencimento.addKeyListener(this);
			painelPrincipal.add(dataVencimento);

			// --------------------------------------------------------------------------------------------------------------

			alterarAgora = new JButton("Alterar Agora");
			alterarAgora.setBounds(277, 300, 175, 35);

			alterarAgora
					.setToolTipText("Clique Aqui Para Alterar, Ou Aperte As Teclas (CTRL + A)");

			alterarAgora.addMouseListener(this);
			alterarAgora.addKeyListener(this);
			painelPrincipal.add(alterarAgora);

			// --------------------------------------------------------------------------------------------------------------

			limparTudo = new JButton("Limpar Tudo");
			limparTudo.setBounds(455, 300, 105, 35);

			limparTudo
					.setToolTipText("Clique Aqui Para Limpar Todos Os Campos, Ou Aperte As Teclas (CTRL + L)");

			limparTudo.addMouseListener(this);
			limparTudo.addKeyListener(this);
			painelPrincipal.add(limparTudo);

			// --------------------------------------------------------------------------------------------------------------

			cancelar = new JButton("Cancelar");

			cancelar.setToolTipText("Clique Aqui Para Cancelar a Alteração, Ou Aperte a Tecla (ESC)");
			cancelar.setBounds(563, 300, 85, 35);

			cancelar.addMouseListener(this);
			cancelar.addKeyListener(this);
			painelPrincipal.add(cancelar);

			// Adicionando componentes a tela JFrame ...

			fundo = new JLabel(

					new ImageIcon(
							AlterandoConta.class
									.getResource("/views/alterando/contas/jpg/Fundo.jpg")));

			painelPrincipal.add(fundo);

			// --------------------------------------------------------------------------------------------------------------

			// Quando o usuário apertar "X" ...

			this.addInternalFrameListener(new InternalFrameListener()

			{
				public void internalFrameClosed(InternalFrameEvent e) {

				}

				@Override
				public void internalFrameActivated(InternalFrameEvent arg0) {

				}

				@Override
				public void internalFrameClosing(InternalFrameEvent arg0) {

					dispose();
					TelaPrincipal.chamandoNovamenteGerenciarContas();

				}

				@Override
				public void internalFrameDeactivated(InternalFrameEvent arg0) {

				}

				@Override
				public void internalFrameDeiconified(InternalFrameEvent arg0) {

				}

				@Override
				public void internalFrameIconified(InternalFrameEvent arg0) {

				}

				@Override
				public void internalFrameOpened(InternalFrameEvent arg0) {

				}

			});

			// Propriedades da tela ...

			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.setSize(678, 370);

			this.setResizable(false);

			this.setClosable(true);

			try {

				this.setSelected(true);

			}

			catch (PropertyVetoException e1) {

				new ErroEncontrado();

			}

			this.setContentPane(painelPrincipal);
			this.setTitle("Thallyta Móveis - Alterando Conta");
			this.setVisible(true);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	private MaskFormatter setMascara(String mascara) {

		MaskFormatter mask = null;

		try {

			mask = new MaskFormatter(mascara);

		}

		catch (java.text.ParseException ex) {

			new ErroEncontrado();

		}

		return mask;

	}

	@Override
	public void keyPressed(KeyEvent keyPress) {

		if (keyPress.getKeyCode() == KeyEvent.VK_A && keyPress.isControlDown()) {

			try {

				boolean v = VerificandoErros.verificando(
						campoNumeroDocumento.getText(), numeroOriginal,
						campoValor.getText(), campoConta.getText(),
						campoOrigem.getText(),
						campoDataEntradaFormatado.getText(),
						campoDataVencimentoFormatado.getText());

				if (v == true) {

					ContasPagar conta = new ContasPagar();

					conta.setDataEntrada(campoDataEntradaFormatado.getText());

					conta.setDataVencimento(campoDataVencimentoFormatado
							.getText());

					conta.setNumeroDocumento(campoNumeroDocumento.getText());
					conta.setOrigem(campoOrigem.getText());
					conta.setTipoConta(campoConta.getText());
					conta.setValor(Double.parseDouble(campoValor.getText()));

					FachadaContasPagar enviandoValores = new FachadaContasPagar();
					enviandoValores.alterandoContaPagar(numeroOriginal, conta);

					contaAlterada.setVisible(true);

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_L && keyPress.isControlDown()) {

			campoConta.setText("");
			campoDataEntradaFormatado.setText("");
			campoDataVencimentoFormatado.setText("");
			campoNumeroDocumento.setText("");
			campoOrigem.setText("");
			campoValor.setText("");

			campoNumeroDocumento.requestFocus();

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& contaAlterada.isVisible() == true) {

			this.dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarContas();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarContas();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == cancelar) {

			dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarContas();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		if (contaAlterada.isVisible() == true
				&& mouseClick.getSource() == contaAlterada) {

			this.dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarContas();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		else if (mouseClick.getSource() == alterarAgora) {

			try {

				boolean v = VerificandoErros.verificando(
						campoNumeroDocumento.getText(), numeroOriginal,
						campoValor.getText(), campoConta.getText(),
						campoOrigem.getText(),
						campoDataEntradaFormatado.getText(),
						campoDataVencimentoFormatado.getText());

				if (v == true) {

					ContasPagar conta = new ContasPagar();

					conta.setDataEntrada(campoDataEntradaFormatado.getText());

					conta.setDataVencimento(campoDataVencimentoFormatado
							.getText());

					conta.setNumeroDocumento(campoNumeroDocumento.getText());
					conta.setOrigem(campoOrigem.getText());
					conta.setTipoConta(campoConta.getText());
					conta.setValor(Double.parseDouble(campoValor.getText()));

					FachadaContasPagar enviandoValores = new FachadaContasPagar();
					enviandoValores.alterandoContaPagar(numeroOriginal, conta);

					contaAlterada.setVisible(true);

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		else if (mouseClick.getSource() == limparTudo) {

			campoConta.setText("");
			campoDataEntradaFormatado.setText("");
			campoDataVencimentoFormatado.setText("");
			campoNumeroDocumento.setText("");
			campoOrigem.setText("");
			campoValor.setText("");

			campoNumeroDocumento.requestFocus();

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

	}

	@Override
	public void mousePressed(MouseEvent mousePress) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseReal) {

	}

}