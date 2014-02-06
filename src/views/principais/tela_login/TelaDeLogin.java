package views.principais.tela_login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import modelo.Usuario;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;
import views.utilitarios.TecladoVirtual;
import fachada.FachadaUsuarios;

public class TelaDeLogin extends JFrame implements MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;

	// Objetos e variáveis de apoio ...

	private JLabel fundo, campo1, campo2, botaoLogin, esqueciSenha,
			senhaErrada, closeSenhaErrada, tecladoVirtual, fundoEscuro;

	private JTextField campoLogin;
	private JPasswordField campoSenha;
	private Color corDotexto = new Color(139, 139, 139);
	private static int contadorDeErros = 0;

	JPanel painelDeElementos;

	// --------------------------------------------------------------------------------------------------------------

	// Criação de um novo objeto usuário ...

	Usuario usu = new Usuario();

	// Objetos (elementos) de mudança de senha ...

	private JTextField campoLogin_Es, campoResposta_Es;
	private JPasswordField campoSenha_Es;
	private JButton verificar_Es, confirmar_Es, alterar_Es;

	private int contadorResposta = 1;

	// Construtor da classe ...

	public TelaDeLogin() {

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		painelDeElementos = new JPanel();
		painelDeElementos.setLayout(new BorderLayout());

		// --------------------------------------------------------------------------------------------------------------

		alterar_Es = new JButton("Alterar");
		alterar_Es.setBounds(553, 454, 90, 35);
		alterar_Es.addKeyListener(this);
		alterar_Es.addMouseListener(this);

		alterar_Es.setVisible(false);
		painelDeElementos.add(alterar_Es);

		// --------------------------------------------------------------------------------------------------------------

		campoSenha_Es = new JPasswordField();

		campoSenha_Es.setVisible(false);
		campoSenha_Es.setForeground(corDotexto);
		campoSenha_Es.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		campoSenha_Es.setBounds(265, 456, 280, 33);

		campoSenha_Es.addKeyListener(this);
		campoSenha_Es.addMouseListener(this);
		painelDeElementos.add(campoSenha_Es);

		// --------------------------------------------------------------------------------------------------------------

		confirmar_Es = new JButton("Confirmar");
		confirmar_Es.setBounds(553, 294, 90, 35);

		confirmar_Es.addKeyListener(this);
		confirmar_Es.addMouseListener(this);

		confirmar_Es.setVisible(false);
		painelDeElementos.add(confirmar_Es);

		// --------------------------------------------------------------------------------------------------------------

		campoResposta_Es = new JTextField();

		campoResposta_Es.setVisible(false);
		campoResposta_Es.setForeground(corDotexto);

		campoResposta_Es.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		campoResposta_Es.setBounds(265, 295, 280, 33);

		campoResposta_Es.addKeyListener(this);
		campoResposta_Es.addMouseListener(this);
		painelDeElementos.add(campoResposta_Es);

		// --------------------------------------------------------------------------------------------------------------

		verificar_Es = new JButton("Verificar");
		verificar_Es.setBounds(553, 134, 90, 35);

		verificar_Es.addKeyListener(this);
		verificar_Es.addMouseListener(this);

		verificar_Es.setVisible(false);
		painelDeElementos.add(verificar_Es);

		// --------------------------------------------------------------------------------------------------------------

		campoLogin_Es = new JTextField();

		campoLogin_Es.setVisible(false);
		campoLogin_Es.setForeground(corDotexto);

		campoLogin_Es.setHorizontalAlignment(javax.swing.JTextField.CENTER);
		campoLogin_Es.setBounds(265, 135, 280, 33);

		campoLogin_Es.addKeyListener(this);
		campoLogin_Es.addMouseListener(this);
		painelDeElementos.add(campoLogin_Es);

		// --------------------------------------------------------------------------------------------------------------

		fundoEscuro = new JLabel(

				new ImageIcon(
						TelaDeLogin.class
								.getResource("/views/principais/tela_login/png/Fundo_Escuro_01.png")));

		fundoEscuro.setBounds(0, 0, 850, 550);

		fundoEscuro.setVisible(false);
		painelDeElementos.add(fundoEscuro);

		// --------------------------------------------------------------------------------------------------------------

		tecladoVirtual = new JLabel(

				new ImageIcon(
						TelaDeLogin.class
								.getResource("/views/principais/tela_login/png/Teclado_Virtual.png")));

		tecladoVirtual.setToolTipText("Clique Aqui e Abra o Teclado Virtual");
		tecladoVirtual.setBounds(540, 231, 28, 22);

		tecladoVirtual.addMouseListener(this);
		painelDeElementos.add(tecladoVirtual);

		// --------------------------------------------------------------------------------------------------------------

		closeSenhaErrada = new JLabel(

				new ImageIcon(
						TelaDeLogin.class
								.getResource("/views/principais/tela_login/png/Close_Senha_Errada.png")));

		closeSenhaErrada.setBounds(820, 19, 9, 9);
		closeSenhaErrada.setVisible(false);

		closeSenhaErrada
				.setToolTipText("Clicando Aqui Você Poderá Fechar Esta Caixa De Aviso");

		closeSenhaErrada.addMouseListener(this);
		painelDeElementos.add(closeSenhaErrada);

		// --------------------------------------------------------------------------------------------------------------

		senhaErrada = new JLabel(

				new ImageIcon(
						TelaDeLogin.class
								.getResource("/views/principais/tela_login/png/Senha_Errada_01.png")));

		senhaErrada.setBounds(0, 0, 850, 55);
		senhaErrada.setVisible(false);

		painelDeElementos.add(senhaErrada);

		// --------------------------------------------------------------------------------------------------------------

		esqueciSenha = new JLabel(

				new ImageIcon(
						TelaDeLogin.class
								.getResource("/views/principais/tela_login/png/Esqueci_Senha.png")));

		esqueciSenha
				.setToolTipText("Consiga Entrar No Sistema, Altere Sua Senha Clicando Aqui");

		esqueciSenha.setBounds(331, 410, 195, 14);
		esqueciSenha.addMouseListener(this);
		painelDeElementos.add(esqueciSenha);

		// --------------------------------------------------------------------------------------------------------------

		botaoLogin = new JLabel(

				new ImageIcon(
						TelaDeLogin.class
								.getResource("/views/principais/tela_login/png/Botao.png")));

		botaoLogin
				.setToolTipText("Após Ter Digitado As Informações, Clique Aqui Ou Tecle (Enter) Para Continuar");

		botaoLogin.setBounds(507, 335, 78, 38);

		botaoLogin.addMouseListener(this);
		painelDeElementos.add(botaoLogin);

		// --------------------------------------------------------------------------------------------------------------

		campoSenha = new JPasswordField();
		campoSenha.setText("Senha");
		campoSenha.setForeground(corDotexto);
		campoSenha.setBorder(null);
		campoSenha.setBounds(280, 290, 250, 25);

		campoSenha
				.setToolTipText("Digite Aqui Sua Senha Corretamente e Tecle Enter ...");

		campoSenha.addKeyListener(this);
		campoSenha.addMouseListener(this);
		painelDeElementos.add(campoSenha);

		// --------------------------------------------------------------------------------------------------------------

		campoLogin = new JTextField();
		campoLogin.setText("Usuário Cadastrado");
		campoLogin.setForeground(corDotexto);
		campoLogin.setBorder(null);

		campoLogin
				.setToolTipText("Digite Aqui o Seu Login e Após Isso Digite a Senha Abaixo ...");

		campoLogin.setBounds(280, 230, 250, 25);

		campoLogin.addKeyListener(this);
		campoLogin.addMouseListener(this);
		painelDeElementos.add(campoLogin);

		// --------------------------------------------------------------------------------------------------------------

		campo2 = new JLabel(

				new ImageIcon(
						TelaDeLogin.class
								.getResource("/views/principais/tela_login/png/Campo.png")));

		campo2.setBounds(265, 280, 320, 43);

		painelDeElementos.add(campo2);

		// --------------------------------------------------------------------------------------------------------------

		campo1 = new JLabel(

				new ImageIcon(
						TelaDeLogin.class
								.getResource("/views/principais/tela_login/png/Campo.png")));

		campo1.setBounds(265, 220, 320, 43);
		painelDeElementos.add(campo1);

		// --------------------------------------------------------------------------------------------------------------

		fundo = new JLabel(

				new ImageIcon(
						TelaDeLogin.class
								.getResource("/views/principais/tela_login/jpg/Fundo.jpg")));

		painelDeElementos.add(fundo);

		// Propriedades da tela ...

		// Quando o usuário apertar "X" ...

		this.addWindowListener(new java.awt.event.WindowAdapter() {

			public void windowClosing(java.awt.event.WindowEvent e) {

				if (fundoEscuro.isVisible() == true) {

					dispose();
					new TelaDeLogin();

				}

				else {

					System.exit(0);

				}

			}

		});

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(856, 578);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		this.setContentPane(painelDeElementos);
		this.setTitle("Thallyta Móveis - Logando No Sistema");
		this.setVisible(true);

	}

	@Override
	@SuppressWarnings("deprecation")
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == closeSenhaErrada) {

			closeSenhaErrada.setVisible(false);
			senhaErrada.setVisible(false);

		}

		else if (mouseClick.getSource() == botaoLogin) {

			verificandoSenha();

		}

		else if (mouseClick.getSource() == esqueciSenha) {

			fundoEscuro.setVisible(true);
			verificar_Es.setVisible(true);
			campoLogin_Es.setVisible(true);

			campoLogin_Es.requestFocus();

			campoLogin.setVisible(false);
			campoSenha.setVisible(false);

		}

		else if (mouseClick.getSource() == tecladoVirtual) {

			new TecladoVirtual();

		}

		else if (mouseClick.getSource() == verificar_Es) {

			FachadaUsuarios chamandoMetodo = new FachadaUsuarios();

			usu = chamandoMetodo.verificandoUsuario(campoLogin_Es.getText());

			if (usu.getApelido().equals(campoLogin_Es.getText())
					|| usu.getNomeCompleto().equals(campoLogin_Es.getText())) {

				fundoEscuro
						.setIcon(

						new ImageIcon(
								TelaDeLogin.class
										.getResource("/views/principais/tela_login/png/Fundo_Escuro_02.png")));

				verificar_Es.setEnabled(false);
				campoLogin_Es.setFocusable(false);

				confirmar_Es.setVisible(true);
				campoResposta_Es.setVisible(true);

				campoResposta_Es.setText(usu.getPerguntaSecreta());
				campoResposta_Es.requestFocus();

				if (campoResposta_Es.getText().equals(usu.getRespostaSecreta())) {

				}

				else {
				}

			}

			else if (campoLogin_Es.getText().trim().isEmpty()) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Nome Ou Apelido, Tente Digitar Novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				campoLogin_Es.setText("");
				campoLogin_Es.requestFocus();

			}

			else {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				JOptionPane
						.showMessageDialog(
								null,
								"Usuário Não Encontrado, Verifique Oque Foi Digitado e Tente Novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				campoLogin_Es.setText("");
				campoLogin_Es.requestFocus();

			}

		}

		else if (mouseClick.getSource() == confirmar_Es) {

			FachadaUsuarios chamandoMetodo = new FachadaUsuarios();

			usu = chamandoMetodo.verificandoUsuario(campoResposta_Es.getText());

			if (usu.getRespostaSecreta().equals(campoResposta_Es.getText())) {

				campoResposta_Es.setFocusable(false);
				confirmar_Es.setEnabled(false);

				fundoEscuro
						.setIcon(new ImageIcon(
								TelaDeLogin.class
										.getResource("/views/principais/tela_login/png/Fundo_Escuro_03.png")));

				campoSenha_Es.setVisible(true);
				alterar_Es.setVisible(true);

				campoSenha_Es.requestFocus();

			}

			else if (campoResposta_Es.getText().trim().isEmpty()) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Resposta Secreta, Tente Digitar Novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				campoResposta_Es.setText("");
				campoResposta_Es.requestFocus();

			}

			else {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				JOptionPane
						.showMessageDialog(
								null,
								"Resposta Errada, Verifique o Que Foi Digitado e Tente Novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				campoResposta_Es.setText("");
				campoResposta_Es.requestFocus();

			}

		}

		else if (mouseClick.getSource() == alterar_Es) {

			if (campoSenha_Es.getText().trim().isEmpty()) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Senha, Digite Novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				campoSenha_Es.setText("");
				campoSenha_Es.requestFocus();

			}

			else {

				alterar_Es.setEnabled(false);
				campoSenha_Es.setFocusable(false);

				usu.setSenha(campoSenha_Es.getText());

				FachadaUsuarios chamandoMetodos = new FachadaUsuarios();
				chamandoMetodos.alterandoSenha(usu);

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"Parabéns, Você Consegiu Alterar Sua Senha, Agora Levaremos Você a Tela De Login. Tecle Enter ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.WARNING_MESSAGE);

					setVisible(false);
					new TelaDeLogin();

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == botaoLogin) {

			botaoLogin
					.setIcon(new ImageIcon(
							TelaDeLogin.class
									.getResource("/views/principais/tela_login/png/Botao_Mouse.png")));

		}

		else if (mouseEntry.getSource() == closeSenhaErrada) {

			closeSenhaErrada
					.setIcon(new ImageIcon(
							TelaDeLogin.class
									.getResource("/views/principais/tela_login/png/Close_Senha_Errada_Mouse.png")));

		}

		else if (mouseEntry.getSource() == tecladoVirtual) {

			tecladoVirtual
					.setIcon(new ImageIcon(
							TelaDeLogin.class
									.getResource("/views/principais/tela_login/png/Teclado_Virtual_Mouse.png")));

		}

		else if (mouseEntry.getSource() == esqueciSenha) {

			esqueciSenha
					.setIcon(new ImageIcon(
							TelaDeLogin.class
									.getResource("/views/principais/tela_login/png/Esqueci_Senha_Mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == botaoLogin) {

			botaoLogin
					.setIcon(new ImageIcon(
							TelaDeLogin.class
									.getResource("/views/principais/tela_login/png/Botao.png")));

		}

		else if (mouseExit.getSource() == closeSenhaErrada) {

			closeSenhaErrada
					.setIcon(new ImageIcon(
							TelaDeLogin.class
									.getResource("/views/principais/tela_login/png/Close_Senha_Errada.png")));

		}

		else if (mouseExit.getSource() == tecladoVirtual) {

			tecladoVirtual
					.setIcon(new ImageIcon(
							TelaDeLogin.class
									.getResource("/views/principais/tela_login/png/Teclado_Virtual.png")));

		}

		else if (mouseExit.getSource() == esqueciSenha) {

			esqueciSenha
					.setIcon(new ImageIcon(
							TelaDeLogin.class
									.getResource("/views/principais/tela_login/png/Esqueci_Senha.png")));

		}

	}

	@Override
	public void mousePressed(MouseEvent mousePress) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseReal) {

	}

	@Override
	@SuppressWarnings("deprecation")
	public void keyPressed(KeyEvent keyPress) {

		if (campoSenha.isFocusOwner() == true) {

			tecladoVirtual.setBounds(540, 291, 28, 22);

		}

		if (campoLogin.isFocusOwner() == true) {

			tecladoVirtual.setBounds(540, 231, 28, 22);

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& campoLogin.isVisible() == true
				&& campoSenha.isVisible() == true) {

			verificandoSenha();

		}

		else if (campoLogin.getText().equals("Usuário Cadastrado")
				&& campoSenha.getText().equals("Senha")) {

			campoLogin.setText("");
			campoSenha.setText("");

		}

		else if (campoLogin_Es.isFocusable() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			FachadaUsuarios chamandoMetodo = new FachadaUsuarios();

			usu = chamandoMetodo.verificandoUsuario(campoLogin_Es.getText());

			if (usu.getApelido().equals(campoLogin_Es.getText())
					|| usu.getNomeCompleto().equals(campoLogin_Es.getText())) {

				fundoEscuro
						.setIcon(new ImageIcon(
								TelaDeLogin.class
										.getResource("/views/principais/tela_login/png/Fundo_Escuro_02.png")));

				verificar_Es.setEnabled(false);
				campoLogin_Es.setFocusable(false);

				confirmar_Es.setVisible(true);
				campoResposta_Es.setVisible(true);

				campoResposta_Es.setText(usu.getPerguntaSecreta());
				campoResposta_Es.requestFocus();

				if (campoResposta_Es.getText().equals(usu.getRespostaSecreta())) {

				}

				else {

				}

			}

			else if (campoLogin_Es.getText().trim().isEmpty()) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Nome Ou Apelido, Tente Digitar Novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				campoLogin_Es.setText("");
				campoLogin_Es.requestFocus();

			}

			else {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				JOptionPane
						.showMessageDialog(
								null,
								"Usuário Não Encontrado, Verifique Oque Foi Digitado e Tente Novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				campoLogin_Es.setText("");
				campoLogin_Es.requestFocus();

			}

		}

		else if (campoResposta_Es.isFocusable() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			FachadaUsuarios chamandoMetodo = new FachadaUsuarios();

			usu = chamandoMetodo.verificandoUsuario(campoResposta_Es.getText());

			if (usu.getRespostaSecreta().equals(campoResposta_Es.getText())) {

				campoResposta_Es.setFocusable(false);
				confirmar_Es.setEnabled(false);

				fundoEscuro
						.setIcon(new ImageIcon(
								TelaDeLogin.class
										.getResource("/views/principais/tela_login/png/Fundo_Escuro_03.png")));

				campoSenha_Es.setVisible(true);
				alterar_Es.setVisible(true);

				campoSenha_Es.requestFocus();

			}

			else if (campoResposta_Es.getText().trim().isEmpty()) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Resposta Secreta, Tente Digitar Novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				campoResposta_Es.setText("");
				campoResposta_Es.requestFocus();

			}

			else {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				JOptionPane
						.showMessageDialog(
								null,
								"Resposta Errada, Verifique o Que Foi Digitado e Tente Novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				campoResposta_Es.setText("");
				campoResposta_Es.requestFocus();

			}

		}

		else if (campoSenha_Es.isFocusable() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

		}

		if (campoResposta_Es.isFocusable()
				&& campoLogin_Es.isFocusable() == false) {

			if (contadorResposta == 1) {

				System.out.println(contadorResposta);
				contadorResposta++;

			}

			else if (contadorResposta == 2) {

				campoResposta_Es.setText("");
				contadorResposta++;

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			System.exit(0);

		}

		else if (campoSenha_Es.isFocusable() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			if (campoSenha_Es.getText().equals("")) {

			}

			else if (campoSenha_Es.getText().trim().isEmpty()) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Senha, Digite Novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				campoSenha_Es.setText("");
				campoSenha_Es.requestFocus();

			}

			else {

				alterar_Es.setEnabled(false);
				campoSenha_Es.setFocusable(false);

				usu.setSenha(campoSenha_Es.getText());

				FachadaUsuarios chamandoMetodos = new FachadaUsuarios();
				chamandoMetodos.alterandoSenha(usu);

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"Parabéns, Você Consegiu Alterar Sua Senha, Agora Levaremos Você a Tela De Login. Tecle Enter ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.WARNING_MESSAGE);

					dispose();
					new TelaDeLogin();

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

			}

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent KeyType) {

	}

	@SuppressWarnings("deprecation")
	public void verificandoSenha() {

		campoLogin.requestFocus();

		Usuario usuarioComum = new Usuario();

		usuarioComum.setApelido(campoLogin.getText());
		usuarioComum.setSenha(campoSenha.getText());

		FachadaUsuarios passandoUsuario = new FachadaUsuarios();
		boolean verificando = passandoUsuario.buscandoUsuario(usuarioComum);

		if (verificando == true) {

			contadorDeErros = 0;

			this.dispose();
			new TelaPrincipal();

		}

		else {

			campoLogin.setText("");
			campoSenha.setText("");

			senhaErrada.setVisible(true);
			closeSenhaErrada.setVisible(true);

			contadorDeErros++;

			if (contadorDeErros == 1) {

				senhaErrada
						.setIcon(new ImageIcon(
								TelaDeLogin.class
										.getResource("/views/principais/tela_login/png/Senha_Errada_01.png")));

			}

			if (contadorDeErros == 2) {

				senhaErrada
						.setIcon(new ImageIcon(
								TelaDeLogin.class
										.getResource("/views/principais/tela_login/png/Senha_Errada_02.png")));

			}

			if (contadorDeErros == 3) {

				System.exit(0);

			}

		}

	}

}