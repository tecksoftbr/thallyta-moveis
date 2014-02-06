// Tela concluída (Revisada Por Thiago e Kleysson) ...

package views.cadastros.cad_contas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.text.MaskFormatter;

import modelo.ContasPagar;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;
import fachada.FachadaContasPagar;

public class CadastroContas extends JInternalFrame implements MouseListener,
		KeyListener {

	private static final long serialVersionUID = 1L;

	private JPanel painelPrincipal;

	private JLabel fundo, tipoConta, valor, numeroDocumento, origem,
			dataEntrada, dataVencimento, contaSalva;

	private JTextField campoConta, campoValor, campoNumeroDocumento,
			campoOrigem;

	private JButton salvar, salvarCadastrarOutro, limparTudo, cancelar;
	private Color corDotexto = new Color(139, 139, 139);

	JFormattedTextField campoDataEntradaFormatado,
			campoDataVencimentoFormatado;

	private int verificarDirecao = 0;
	private ContasPagar conta;

	public CadastroContas() {

		// Definindo layout da tela ...

		try {

			painelPrincipal = new JPanel();
			painelPrincipal.setLayout(new BorderLayout());

			contaSalva = new JLabel(

					new ImageIcon(
							CadastroContas.class
									.getResource("/views/cadastros/cad_contas/jpg/Conta_Salva.jpg")));

			contaSalva.setBounds(0, 0, 672, 342);
			contaSalva.setVisible(false);

			contaSalva.addMouseListener(this);
			contaSalva.addKeyListener(this);
			painelPrincipal.add(contaSalva);

			campoDataEntradaFormatado = new JFormattedTextField(
					(setMascara("##/##/####")));

			campoDataEntradaFormatado.setForeground(corDotexto);
			campoDataEntradaFormatado.setBounds(547, 185, 101, 33);

			campoDataEntradaFormatado
					.setToolTipText("Digite Aqui a Data De Entrada Que Esta Conta Foi Anúnciada");

			campoDataEntradaFormatado.addMouseListener(this);
			campoDataEntradaFormatado.addKeyListener(this);
			painelPrincipal.add(campoDataEntradaFormatado);

			// --------------------------------------------------------------------------------------------------------------

			campoDataVencimentoFormatado = new JFormattedTextField(
					(setMascara("##/##/####")));

			campoDataVencimentoFormatado.setForeground(corDotexto);
			campoDataVencimentoFormatado.setBounds(547, 226, 101, 33);

			campoDataVencimentoFormatado
					.setToolTipText("Digite Aqui a Data De Vencimento Desta Conta, No Dia Você Será Alertado");

			campoDataVencimentoFormatado.addMouseListener(this);
			campoDataVencimentoFormatado.addKeyListener(this);
			painelPrincipal.add(campoDataVencimentoFormatado);

			// --------------------------------------------------------------------------------------------------------------

			tipoConta = new JLabel("Tipo De Conta *");

			tipoConta.setForeground(corDotexto);
			tipoConta.setFont(new Font("Dialog", Font.PLAIN, 15));
			tipoConta.setBounds(20, 190, 110, 25);

			tipoConta.addMouseListener(this);
			tipoConta.addKeyListener(this);
			painelPrincipal.add(tipoConta);

			// --------------------------------------------------------------------------------------------------------------

			campoConta = new JTextField();

			campoConta.setForeground(corDotexto);
			campoConta.setBounds(140, 186, 200, 33);

			campoConta
					.setToolTipText("Digite Aqui o Tipo Desta Conta, Se é Um Boleto, Cartão, Energia Ou Outros");

			campoConta.addMouseListener(this);
			campoConta.addKeyListener(this);
			painelPrincipal.add(campoConta);

			// --------------------------------------------------------------------------------------------------------------

			valor = new JLabel("Valor *");

			valor.setForeground(corDotexto);
			valor.setFont(new Font("Dialog", Font.PLAIN, 15));
			valor.setBounds(490, 130, 50, 25);

			valor.addMouseListener(this);
			valor.addKeyListener(this);
			painelPrincipal.add(valor);

			// --------------------------------------------------------------------------------------------------------------

			campoValor = new JTextField();

			campoValor.addKeyListener(this);
			campoValor.setForeground(corDotexto);
			campoValor.setBounds(547, 126, 101, 33);

			campoValor
					.setToolTipText("Digite Aqui o Valor Desta Conta Seguindo o Exemplo: 1025.36");

			campoValor.addMouseListener(this);
			campoValor.addKeyListener(this);
			painelPrincipal.add(campoValor);

			// --------------------------------------------------------------------------------------------------------------

			numeroDocumento = new JLabel("Número Do Documento *");

			numeroDocumento.setForeground(corDotexto);
			numeroDocumento.setFont(new Font("Dialog", Font.PLAIN, 15));
			numeroDocumento.setBounds(20, 130, 170, 25);

			numeroDocumento.addMouseListener(this);
			numeroDocumento.addKeyListener(this);
			painelPrincipal.add(numeroDocumento);

			// --------------------------------------------------------------------------------------------------------------

			campoNumeroDocumento = new JTextField();

			campoNumeroDocumento.setForeground(corDotexto);
			campoNumeroDocumento.setBounds(200, 126, 250, 33);

			campoNumeroDocumento
					.setToolTipText("Digite Aqui o Número Do Documento a Ser Gravado No Banco De Dados");

			campoNumeroDocumento.addMouseListener(this);
			campoNumeroDocumento.addKeyListener(this);
			painelPrincipal.add(campoNumeroDocumento);

			// --------------------------------------------------------------------------------------------------------------
			dataEntrada = new JLabel("Data Entrada *");

			dataEntrada.setForeground(corDotexto);
			dataEntrada.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataEntrada.setBounds(384, 187, 100, 25);

			dataEntrada.addMouseListener(this);
			dataEntrada.addKeyListener(this);
			painelPrincipal.add(dataEntrada);

			// --------------------------------------------------------------------------------------------------------------

			origem = new JLabel("Origem *");

			origem.setForeground(corDotexto);
			origem.setFont(new Font("Dialog", Font.PLAIN, 15));
			origem.setBounds(21, 230, 100, 25);

			origem.addMouseListener(this);
			origem.addKeyListener(this);
			painelPrincipal.add(origem);

			// --------------------------------------------------------------------------------------------------------------

			campoOrigem = new JTextField();

			campoOrigem.setForeground(corDotexto);
			campoOrigem.setBounds(140, 226, 200, 33);

			campoOrigem
					.setToolTipText("Digite Aqui a Origem Desta Conta, Se é De Algum Fornecedor, Celpe Ou Outros");

			campoOrigem.addMouseListener(this);
			campoOrigem.addKeyListener(this);
			painelPrincipal.add(campoOrigem);

			// --------------------------------------------------------------------------------------------------------------

			dataVencimento = new JLabel("Data Vencimento *");

			dataVencimento.setForeground(corDotexto);
			dataVencimento.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataVencimento.setBounds(384, 230, 130, 25);

			dataVencimento.addMouseListener(this);
			dataVencimento.addKeyListener(this);
			painelPrincipal.add(dataVencimento);

			// --------------------------------------------------------------------------------------------------------------

			salvar = new JButton("Salvar");
			salvar.setBounds(184, 285, 90, 35);

			salvar.setToolTipText("Clique Aqui Para Salvar Esta Conta, Ou Aperte As Teclas (CTRL + S)");

			salvar.addMouseListener(this);
			salvar.addKeyListener(this);
			painelPrincipal.add(salvar);

			// --------------------------------------------------------------------------------------------------------------

			salvarCadastrarOutro = new JButton("Salvar e Cadastrar Outra");
			salvarCadastrarOutro.setBounds(277, 285, 175, 35);

			salvarCadastrarOutro
					.setToolTipText("Clique Aqui Para Salvar Esta Conta e Cadastrar Outra, Ou Aperte As Teclas (CTRL + F)");

			salvarCadastrarOutro.addMouseListener(this);
			salvarCadastrarOutro.addKeyListener(this);
			painelPrincipal.add(salvarCadastrarOutro);

			// --------------------------------------------------------------------------------------------------------------

			limparTudo = new JButton("Limpar Tudo");
			limparTudo.setBounds(455, 285, 105, 35);

			limparTudo
					.setToolTipText("Clique Aqui Para Limpar Todos Os Campos, Ou Aperte As Teclas (CTRL + L)");

			limparTudo.addMouseListener(this);
			limparTudo.addKeyListener(this);
			painelPrincipal.add(limparTudo);

			// --------------------------------------------------------------------------------------------------------------

			cancelar = new JButton("Cancelar");

			cancelar.setToolTipText("Clique Aqui Para Cancelar o Cadastro, Ou Aperte a Tecla (ESC)");
			cancelar.setBounds(563, 285, 85, 35);

			cancelar.addMouseListener(this);
			cancelar.addKeyListener(this);
			painelPrincipal.add(cancelar);

			// Adicionando componentes a tela JFrame ...

			fundo = new JLabel(

					new ImageIcon(
							CadastroContas.class
									.getResource("/views/cadastros/cad_contas/jpg/Fundo.jpg")));

			painelPrincipal.add(fundo);

			// --------------------------------------------------------------------------------------------------------------

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
			this.setContentPane(painelPrincipal);

			this.setTitle("Thallyta Móveis - Cadastrando Nova Conta");

			this.setClosable(true);
			this.setSelected(true);
			this.setVisible(true);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == cancelar) {

			dispose();

		}

		if (contaSalva.isVisible() & mouseClick.getSource() == contaSalva
				& verificarDirecao == 1) {

			this.dispose();

		}

		if (contaSalva.isVisible() & mouseClick.getSource() == contaSalva
				& verificarDirecao == 2) {

			contaSalva.setVisible(false);

			campoConta.setText("");
			campoDataEntradaFormatado.setText("");
			campoDataVencimentoFormatado.setText("");
			campoNumeroDocumento.setText("");
			campoOrigem.setText("");
			campoValor.setText("");

			campoNumeroDocumento.requestFocus();

		}

		if (mouseClick.getSource() == limparTudo) {

			campoConta.setText("");
			campoDataEntradaFormatado.setText("");
			campoDataVencimentoFormatado.setText("");
			campoNumeroDocumento.setText("");
			campoOrigem.setText("");
			campoValor.setText("");

			campoNumeroDocumento.requestFocus();

		}

		if (mouseClick.getSource() == salvar) {

			try {

				boolean verif = VerificandoErros_Contas.verificando(
						campoNumeroDocumento.getText(), campoValor.getText(),
						campoConta.getText(), campoOrigem.getText(),
						campoDataEntradaFormatado.getText(),
						campoDataVencimentoFormatado.getText());

				if (verif == true) {

					conta = new ContasPagar();
					conta.setDataEntrada(campoDataEntradaFormatado.getText());

					conta.setDataVencimento(campoDataVencimentoFormatado
							.getText());

					conta.setNumeroDocumento(campoNumeroDocumento.getText());
					conta.setOrigem(campoOrigem.getText());
					conta.setTipoConta(campoConta.getText());
					conta.setValor(Double.parseDouble(campoValor.getText()));

					FachadaContasPagar contaBD = new FachadaContasPagar();
					contaBD.adicionarContasPagar(conta);

					contaSalva.setVisible(true);
					verificarDirecao = 1;

					TelaPrincipal.adicionandoValoresAsTabelas();

				}

				else {

				}

			}

			catch (Exception e) {

			}

		}

		if (mouseClick.getSource() == salvarCadastrarOutro) {

			try {

				boolean verif = VerificandoErros_Contas.verificando(
						campoNumeroDocumento.getText(), campoValor.getText(),
						campoConta.getText(), campoOrigem.getText(),
						campoDataEntradaFormatado.getText(),
						campoDataVencimentoFormatado.getText());

				if (verif == true) {

					conta = new ContasPagar();
					conta.setDataEntrada(campoDataEntradaFormatado.getText());

					conta.setDataVencimento(campoDataVencimentoFormatado
							.getText());

					conta.setNumeroDocumento(campoNumeroDocumento.getText());
					conta.setOrigem(campoOrigem.getText());
					conta.setTipoConta(campoConta.getText());
					conta.setValor(Double.parseDouble(campoValor.getText()));

					FachadaContasPagar contaBD = new FachadaContasPagar();
					contaBD.adicionarContasPagar(conta);

					contaSalva.setVisible(true);
					verificarDirecao = 2;

					TelaPrincipal.adicionandoValoresAsTabelas();

				}

				else {

				}

			}

			catch (Exception e) {

			}

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

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& contaSalva.isVisible() == true && verificarDirecao == 1) {

			dispose();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& contaSalva.isVisible() == true && verificarDirecao == 2) {

			contaSalva.setVisible(false);

			campoConta.setText("");
			campoDataEntradaFormatado.setText("");
			campoDataVencimentoFormatado.setText("");
			campoNumeroDocumento.setText("");
			campoOrigem.setText("");
			campoValor.setText("");

			campoNumeroDocumento.requestFocus();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_S
				&& keyPress.isControlDown()) {

			try {

				boolean verif = VerificandoErros_Contas.verificando(
						campoNumeroDocumento.getText(), campoValor.getText(),
						campoConta.getText(), campoOrigem.getText(),
						campoDataEntradaFormatado.getText(),
						campoDataVencimentoFormatado.getText());

				if (verif == true) {

					conta = new ContasPagar();
					conta.setDataEntrada(campoDataEntradaFormatado.getText());

					conta.setDataVencimento(campoDataVencimentoFormatado
							.getText());

					conta.setNumeroDocumento(campoNumeroDocumento.getText());
					conta.setOrigem(campoOrigem.getText());
					conta.setTipoConta(campoConta.getText());
					conta.setValor(Double.parseDouble(campoValor.getText()));

					FachadaContasPagar contaBD = new FachadaContasPagar();
					contaBD.adicionarContasPagar(conta);

					contaSalva.setVisible(true);
					verificarDirecao = 1;

					TelaPrincipal.adicionandoValoresAsTabelas();

				}

				else {

				}

			}

			catch (Exception e) {

			}

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_F
				&& keyPress.isControlDown()) {

			try {

				boolean verif = VerificandoErros_Contas.verificando(
						campoNumeroDocumento.getText(), campoValor.getText(),
						campoConta.getText(), campoOrigem.getText(),
						campoDataEntradaFormatado.getText(),
						campoDataVencimentoFormatado.getText());

				if (verif == true) {

					conta = new ContasPagar();
					conta.setDataEntrada(campoDataEntradaFormatado.getText());

					conta.setDataVencimento(campoDataVencimentoFormatado
							.getText());

					conta.setNumeroDocumento(campoNumeroDocumento.getText());
					conta.setOrigem(campoOrigem.getText());
					conta.setTipoConta(campoConta.getText());
					conta.setValor(Double.parseDouble(campoValor.getText()));

					FachadaContasPagar contaBD = new FachadaContasPagar();
					contaBD.adicionarContasPagar(conta);

					contaSalva.setVisible(true);
					verificarDirecao = 2;

					TelaPrincipal.adicionandoValoresAsTabelas();

				}

				else {

				}

			}

			catch (Exception e) {

			}

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_L
				&& keyPress.isControlDown()) {

			campoConta.setText("");
			campoDataEntradaFormatado.setText("");
			campoDataVencimentoFormatado.setText("");
			campoNumeroDocumento.setText("");
			campoOrigem.setText("");
			campoValor.setText("");

			campoNumeroDocumento.requestFocus();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			this.dispose();

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

}