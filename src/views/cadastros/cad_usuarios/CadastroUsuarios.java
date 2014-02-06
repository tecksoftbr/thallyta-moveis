package views.cadastros.cad_usuarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import fachada.FachadaUsuarios;
import modelo.Usuario;
import views.outras.capturar_webcam.Capturando;
import views.principais.tela_de_erro.ErroEncontrado;

public class CadastroUsuarios extends JInternalFrame implements MouseListener,
		KeyListener {

	private static final long serialVersionUID = 1L;

	// Objetos a serem incrementados e variáveis de apoio ...

	private JLabel fundo, foto, fitaFoto, webcam, nomeCompletoTexto,
			apelidoTexto, senhaTexto, perguntaSecretaTexto,
			respostaSecretaTexto, sobreMimTexto, dataDeCadastroTexto,
			usuarioSalvo;

	private JButton salvar, salvarCadastrarOutro, limparTudo, cancelar;

	private JTextArea campoSobreMim;

	private JTextField campoNomeCompleto, campoApelido, campoPergunta,
			campoDataDeCadastro;

	private JPasswordField campoResposta, campoSenha;
	private JPanel painelPrincipal, painelSobreMim;

	private JScrollPane scrollSobreMim;

	private int contadorSalvar = 0;
	private int ContadorMudarFoto = 1;
	
	FachadaUsuarios passandoUsuario;

	// Cor (es) usadas ...

	private Color corDotexto = new Color(139, 139, 139);

	// Construtor da classe ...

	public CadastroUsuarios() throws PropertyVetoException {

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		// Adicionando aparência NIMBUS ao jframe ...

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		// Adicionando componentes a tela ...

		usuarioSalvo = new JLabel(

				new ImageIcon(
						CadastroUsuarios.class
								.getResource("/views/cadastros/cad_usuarios/jpg/Usuario_Salvo.JPG")));

		usuarioSalvo.setBounds(0, 0, 927, 412);
		usuarioSalvo.setVisible(false);

		usuarioSalvo.addMouseListener(this);
		usuarioSalvo.addKeyListener(this);

		painelPrincipal.add(usuarioSalvo);

		campoDataDeCadastro = new JTextField(pegandoDataDoSistema());

		campoDataDeCadastro
				.setToolTipText("Este Campo Não é Editável, Ele Somente Captura a Data Do Sistema Para Maior Segurança");

		campoDataDeCadastro.setEditable(false);
		campoDataDeCadastro.setForeground(corDotexto);
		campoDataDeCadastro.setBounds(576, 126, 99, 33);

		campoDataDeCadastro.setFocusable(false);

		campoDataDeCadastro.addKeyListener(this);
		painelPrincipal.add(campoDataDeCadastro);

		// --------------------------------------------------------------------------------------------------------------

		dataDeCadastroTexto = new JLabel("Data De Cadastro");

		dataDeCadastroTexto.setForeground(corDotexto);
		dataDeCadastroTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		dataDeCadastroTexto.setBounds(445, 130, 150, 25);

		painelPrincipal.add(dataDeCadastroTexto);

		// --------------------------------------------------------------------------------------------------------------

		painelSobreMim = new JPanel();
		painelSobreMim.setLayout(new BorderLayout());
		painelSobreMim.setBounds(19, 328, 400, 67);

		campoSobreMim = new JTextArea();

		campoSobreMim.setToolTipText("Fale Mais Sobre Você Aqui Neste Campo");

		campoSobreMim.setForeground(corDotexto);
		campoSobreMim.setLineWrap(true);
		campoSobreMim.setBounds(19, 320, 400, 61);

		scrollSobreMim = new JScrollPane(campoSobreMim);

		painelSobreMim.add(scrollSobreMim);
		painelPrincipal.add(painelSobreMim);

		campoSobreMim.addKeyListener(this);

		// --------------------------------------------------------------------------------------------------------------

		sobreMimTexto = new JLabel("Sobre Mim");

		sobreMimTexto.setForeground(corDotexto);
		sobreMimTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		sobreMimTexto.setBounds(20, 299, 150, 25);

		painelPrincipal.add(sobreMimTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoResposta = new JPasswordField();

		campoResposta.setForeground(corDotexto);
		campoResposta.setBounds(445, 236, 230, 33);

		campoResposta
				.setToolTipText("Aqui Você Digitará Uma Resposta Secreta Caso Queira Alterar a Senha Futuramente");

		campoResposta.addKeyListener(this);
		painelPrincipal.add(campoResposta);

		// --------------------------------------------------------------------------------------------------------------

		respostaSecretaTexto = new JLabel("Pergunta Secreta *");

		respostaSecretaTexto.setForeground(corDotexto);
		respostaSecretaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		respostaSecretaTexto.setBounds(310, 200, 150, 25);

		painelPrincipal.add(respostaSecretaTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoPergunta = new JTextField();

		campoPergunta.setForeground(corDotexto);
		campoPergunta.setBounds(445, 196, 230, 33);

		campoPergunta
				.setToolTipText("Aqui Você Digitará Uma Pergunta Secreta Caso Queira Alterar a Senha Futuramente");

		campoPergunta.addKeyListener(this);
		painelPrincipal.add(campoPergunta);

		// --------------------------------------------------------------------------------------------------------------

		perguntaSecretaTexto = new JLabel("Resposta Secreta *");

		perguntaSecretaTexto.setForeground(corDotexto);
		perguntaSecretaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		perguntaSecretaTexto.setBounds(310, 240, 150, 25);

		painelPrincipal.add(perguntaSecretaTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoSenha = new JPasswordField();

		campoSenha.setForeground(corDotexto);
		campoSenha.setBounds(140, 236, 150, 33);

		campoSenha
				.setToolTipText("Digite Aqui Uma Senha Para o Desbloqueio Do Programa Futuramente");

		campoSenha.addKeyListener(this);
		painelPrincipal.add(campoSenha);

		// --------------------------------------------------------------------------------------------------------------

		senhaTexto = new JLabel("Senha *");

		senhaTexto.setForeground(corDotexto);
		senhaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		senhaTexto.setBounds(20, 240, 150, 25);

		painelPrincipal.add(senhaTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoApelido = new JTextField();

		campoApelido.setForeground(corDotexto);

		campoApelido
				.setToolTipText("Digite Aqui Um Apelido Para Que Futuramente a Pessoa Cadastrada Possa Desbloquear o Software");

		campoApelido.setBounds(140, 196, 150, 33);

		campoApelido.addKeyListener(this);
		painelPrincipal.add(campoApelido);

		// --------------------------------------------------------------------------------------------------------------

		apelidoTexto = new JLabel("Apelido *");

		apelidoTexto.setForeground(corDotexto);
		apelidoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		apelidoTexto.setBounds(20, 200, 150, 25);

		painelPrincipal.add(apelidoTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoNomeCompleto = new JTextField();

		campoNomeCompleto.setForeground(corDotexto);

		campoNomeCompleto
				.setToolTipText("Digite Aqui o Nome Completo Do Usuário Que Irá Ser Cadastrado");

		campoNomeCompleto.setBounds(140, 126, 290, 33);

		campoNomeCompleto.addKeyListener(this);
		campoNomeCompleto.addMouseListener(this);
		painelPrincipal.add(campoNomeCompleto);

		// --------------------------------------------------------------------------------------------------------------

		nomeCompletoTexto = new JLabel("Nome Completo *");

		nomeCompletoTexto.setForeground(corDotexto);
		nomeCompletoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		nomeCompletoTexto.setBounds(20, 130, 150, 25);

		painelPrincipal.add(nomeCompletoTexto);

		// --------------------------------------------------------------------------------------------------------------

		webcam = new JLabel(

				new ImageIcon(
						CadastroUsuarios.class
								.getResource("/views/cadastros/cad_usuarios/png/webcam.png")));

		webcam.setBounds(760, 285, 50, 50);
		webcam.setToolTipText("Clique Aqui Para Capturar Uma Foto Do Usuário Pela Sua Webcam");

		webcam.addKeyListener(this);
		webcam.addMouseListener(this);
		painelPrincipal.add(webcam);

		// --------------------------------------------------------------------------------------------------------------

		fitaFoto = new JLabel(

				new ImageIcon(
						CadastroUsuarios.class
								.getResource("/views/cadastros/cad_produtos/png/Fita_Foto.PNG")));

		fitaFoto.setBounds(741, 82, 80, 20);
		painelPrincipal.add(fitaFoto);

		foto = new JLabel(
				new ImageIcon(
						CadastroUsuarios.class
								.getResource("/views/cadastros/cad_usuarios/jpg/Foto.jpg")));
		foto.setBounds(703, 94, 157, 157);

		foto.setToolTipText("Clique Aqui Para Selecionar Uma Foto Do Seu Computador e Adicionar Ao Cadastro Do Usuário");

		foto.addKeyListener(this);
		foto.addMouseListener(this);
		painelPrincipal.add(foto);

		// --------------------------------------------------------------------------------------------------------------

		salvar = new JButton("Salvar");
		salvar.setBounds(438, 360, 90, 35);
		salvar.setToolTipText("Clique Aqui Para Salvar Este Usuário, Ou Aperte As Teclas (CTRL + S)");

		salvar.addKeyListener(this);
		salvar.addMouseListener(this);
		painelPrincipal.add(salvar);

		// --------------------------------------------------------------------------------------------------------------

		salvarCadastrarOutro = new JButton("Salvar e Cadastrar Outro");
		salvarCadastrarOutro.setBounds(531, 360, 175, 35);

		salvarCadastrarOutro
				.setToolTipText("Clique Aqui Para Salvar Este Usuário e Cadastrar Outro, Ou Aperte As Teclas (CTRL + F)");

		salvarCadastrarOutro.addKeyListener(this);
		salvarCadastrarOutro.addMouseListener(this);
		painelPrincipal.add(salvarCadastrarOutro);

		// --------------------------------------------------------------------------------------------------------------

		limparTudo = new JButton("Limpar Tudo");
		limparTudo.setBounds(709, 360, 105, 35);

		limparTudo
				.setToolTipText("Clique Aqui Para Limpar Todos Os Campos, Ou Aperte As Teclas (CTRL + L)");

		limparTudo.addKeyListener(this);
		limparTudo.addMouseListener(this);
		painelPrincipal.add(limparTudo);

		// --------------------------------------------------------------------------------------------------------------

		cancelar = new JButton("Cancelar");

		cancelar.setToolTipText("Clique Aqui Para Cancelar o Cadastro, Ou Aperte a Tecla (ESC)");
		cancelar.setBounds(817, 360, 85, 35);

		cancelar.addKeyListener(this);
		cancelar.addMouseListener(this);
		painelPrincipal.add(cancelar);

		// --------------------------------------------------------------------------------------------------------------

		fundo = new JLabel(

				new ImageIcon(
						CadastroUsuarios.class
								.getResource("/views/cadastros/cad_usuarios/jpg/Fundo.jpg")));

		painelPrincipal.add(fundo);

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

		this.addMouseListener(this);
		this.addKeyListener(this);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(933, 440);
		this.setResizable(false);
		this.setTitle("Thallyta Móveis - Cadastrando Novo Usuário");

		this.setContentPane(painelPrincipal);

		this.setClosable(true);
		this.setSelected(true);
		this.setVisible(true);

		// Difinindo o ponteiro no campo NOME COMPLETO ...

		campoNomeCompleto.requestFocus();

	}

	// Método de mouse clicado ...

	@Override
	@SuppressWarnings("deprecation")
	public void mouseClicked(MouseEvent mouseClick) {

		// Quando clicar no botão cancelar ...

		if (mouseClick.getSource() == cancelar) {

			dispose();

		}

		if (usuarioSalvo.isVisible() & mouseClick.getSource() == usuarioSalvo
				& contadorSalvar == 1) {

			this.dispose();

		}

		if (usuarioSalvo.isVisible() & mouseClick.getSource() == usuarioSalvo
				& contadorSalvar == 2) {

			usuarioSalvo.setVisible(false);

			campoNomeCompleto.setText("");
			campoApelido.setText("");
			campoSenha.setText("");
			campoPergunta.setText("");
			campoResposta.setText("");
			campoSobreMim.setText("");

			// Difinindo o ponteiro no campo NOME COMPLETO ...

			campoNomeCompleto.requestFocus();

		}

		// Quando clicar no botão limpar tudo ...

		else if (mouseClick.getSource() == limparTudo) {

			campoNomeCompleto.setText("");
			campoApelido.setText("");
			campoSenha.setText("");
			campoPergunta.setText("");
			campoResposta.setText("");
			campoSobreMim.setText("");

			// Difinindo o ponteiro no campo NOME COMPLETO ...

			campoNomeCompleto.requestFocus();

		}

		// Quando clicar no botão salvar ...

		else if (mouseClick.getSource() == salvar) {

			// Criando um objeto e atribuindo os valores dos campos a ele ...

			Usuario usu = new Usuario();

			usu.setNomeCompleto(campoNomeCompleto.getText());
			usu.setDataDeCadastro(campoDataDeCadastro.getText());
			usu.setApelido(campoApelido.getText());
			usu.setSenha(campoSenha.getText());

			usu.setPerguntaSecreta(campoPergunta.getText());
			usu.setRespostaSecreta(campoResposta.getText());

			usu.setSobreMim(campoSobreMim.getText());

			try {

				boolean informacao = VerificandoErros_Usuarios
						.verificandoErros(usu);

				// Se o resultado bolleano dos métodos for == true ...

				if (informacao == true) {

					passandoUsuario = new FachadaUsuarios();
					passandoUsuario.adicionarUsuario(usu);

					painelPrincipal.setEnabled(false);
					usuarioSalvo.setVisible(true);
					contadorSalvar = 1;

				}

				// Senão ...

				else {

				}

			}

			// Caso algo dê errado, a classe de erro e chamada ...

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		// Caso o usuário clicar em salvar e cadastrar outro

		else if (mouseClick.getSource() == salvarCadastrarOutro) {

			// Criando novo usuário ...

			Usuario usu = new Usuario();

			usu.setNomeCompleto(campoNomeCompleto.getText());
			usu.setDataDeCadastro(campoDataDeCadastro.getText());
			usu.setApelido(campoApelido.getText());
			usu.setSenha(campoSenha.getText());

			usu.setPerguntaSecreta(campoPergunta.getText());
			usu.setRespostaSecreta(campoResposta.getText());

			usu.setSobreMim(campoSobreMim.getText());

			try {

				// Verificando erros ...

				boolean informacao = VerificandoErros_Usuarios
						.verificandoErros(usu);

				// Se o resultado retornado pelo método for == true, armazene no
				// banco de dados ...

				if (informacao == true) {

					FachadaUsuarios passandoUsuario = new FachadaUsuarios();
					passandoUsuario.adicionarUsuario(usu);

					painelPrincipal.setEnabled(false);
					usuarioSalvo.setVisible(true);
					contadorSalvar = 2;

				}

				// Senão ...

				else {

				}

			}

			// Chamando a classe de erro, caso algo dê errado ...

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		// Se o usuário clicar em cima da foto ...

		else if (mouseClick.getSource() == foto) {

			JFileChooser chooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images", "jpg", "gif");

			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);

			int returnVal = chooser.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				foto.setIcon((new ImageIcon(chooser.getSelectedFile().getPath())));
				ContadorMudarFoto = 2;

			}

		}
		
		else if (mouseClick.getSource() == webcam) {
			
			new Capturando();
			
		}

	}

	// Caso o usuário passe o mouse por cima de componentes da tela ...

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == foto & ContadorMudarFoto == 1) {

			foto.setIcon(new ImageIcon(
					CadastroUsuarios.class
							.getResource("/views/cadastros/cad_usuarios/jpg/Foto_Mouse.jpg")));

		}

		else if (mouseEntry.getSource() == webcam) {

			webcam.setIcon(new ImageIcon(
					CadastroUsuarios.class
							.getResource("/views/cadastros/cad_usuarios/png/webcam_mouse.png")));

		}

	}

	// Caso o usuário retire o mouse de cima de componentes da tela ...

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == foto & ContadorMudarFoto == 1) {

			foto.setIcon(new ImageIcon(CadastroUsuarios.class
					.getResource("/views/cadastros/cad_usuarios/jpg/Foto.jpg")));

		}

		else if (mouseExit.getSource() == webcam) {

			webcam.setIcon(new ImageIcon(
					CadastroUsuarios.class
							.getResource("/views/cadastros/cad_usuarios/png/webcam.png")));

		}

	}

	// Mouse pressionado ...

	@Override
	public void mousePressed(MouseEvent mousePress) {

	}

	// Mouse solto, após o pressionado ...

	@Override
	public void mouseReleased(MouseEvent mouseReal) {

	}

	// Métodos de entrada de teclado ...

	@Override
	@SuppressWarnings("deprecation")
	public void keyPressed(KeyEvent keyPress) {

		// Caso o usuário tecle (CTRL + L), limpará todos os campos ...

		if (keyPress.getKeyCode() == KeyEvent.VK_L && keyPress.isControlDown()) {

			campoNomeCompleto.setText("");
			campoApelido.setText("");
			campoSenha.setText("");
			campoPergunta.setText("");
			campoResposta.setText("");
			campoSobreMim.setText("");

			// Difinindo o ponteiro no campo NOME COMPLETO ...

			campoNomeCompleto.requestFocus();

		}

		// Caso o usuário tecle (CTRL + S), iniciará o processo de verificação e
		// gravação no banco de dados ...

		else if (keyPress.getKeyCode() == KeyEvent.VK_S
				&& keyPress.isControlDown()) {

			Usuario usu = new Usuario();

			usu.setNomeCompleto(campoNomeCompleto.getText());
			usu.setDataDeCadastro(campoDataDeCadastro.getText());
			usu.setApelido(campoApelido.getText());
			usu.setSenha(campoSenha.getText());

			usu.setPerguntaSecreta(campoPergunta.getText());
			usu.setRespostaSecreta(campoResposta.getText());

			usu.setSobreMim(campoSobreMim.getText());

			// Verificando erros ...

			try {

				boolean informacao = VerificandoErros_Usuarios
						.verificandoErros(usu);

				// Se o método voltar um valor true ...

				if (informacao == true) {

					FachadaUsuarios passandoUsuario = new FachadaUsuarios();
					passandoUsuario.adicionarUsuario(usu);

					painelPrincipal.setEnabled(false);
					usuarioSalvo.setVisible(true);
					contadorSalvar = 1;

				}

				// Caso retorne falso ...

				else {

				}

			}

			// Caso dê erro ...

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		// Caso o usuário tecle (CTRL + F), iniciará o processo de verificação e
		// ...

		// gravação no banco de dados, e continuará na mesma tela, esperando ...
		// outro usuário ser cadastrado ...

		else if (keyPress.getKeyCode() == KeyEvent.VK_F
				&& keyPress.isControlDown()) {

			// Criando um objeto usuário ...

			Usuario usu = new Usuario();

			usu.setNomeCompleto(campoNomeCompleto.getText());
			usu.setDataDeCadastro(campoDataDeCadastro.getText());
			usu.setApelido(campoApelido.getText());
			usu.setSenha(campoSenha.getText());

			usu.setPerguntaSecreta(campoPergunta.getText());
			usu.setRespostaSecreta(campoResposta.getText());

			usu.setSobreMim(campoSobreMim.getText());

			// Criando um try/catch para verificação de erros no que foi
			// esquecido ou digitado ...

			try {

				boolean informacao = VerificandoErros_Usuarios
						.verificandoErros(usu);

				if (informacao == true) {

					FachadaUsuarios passandoUsuario = new FachadaUsuarios();
					passandoUsuario.adicionarUsuario(usu);

					painelPrincipal.setEnabled(false);
					usuarioSalvo.setVisible(true);
					contadorSalvar = 2;

				}

				else {

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		// Caso o usuário tecle (ESC), O fechamento da janela será iniciado e
		// ...
		// assim chamará a tela principal ...

		else if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			dispose();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& contadorSalvar == 1 && usuarioSalvo.isVisible() == true) {

			this.dispose();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& contadorSalvar == 2 && usuarioSalvo.isVisible() == true) {

			usuarioSalvo.setVisible(false);

			campoNomeCompleto.setText("");
			campoApelido.setText("");
			campoSenha.setText("");
			campoPergunta.setText("");
			campoResposta.setText("");
			campoSobreMim.setText("");

			// Difinindo o ponteiro no campo NOME COMPLETO ...

			campoNomeCompleto.requestFocus();

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

	// Método que retornará a data do sistema ...

	private String pegandoDataDoSistema() {

		Date now = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String s = df.format(now);

		return s;

	}

}