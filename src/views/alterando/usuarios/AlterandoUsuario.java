/*
 * 
 * Falta fazer nesta tela:
 * 
 * 		- Carregar foto e salvar.
 * 		- Tirar foto da webcam e salvar.
 * 
 */

package views.alterando.usuarios;

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
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import fachada.FachadaUsuarios;

import modelo.Usuario;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;

public class AlterandoUsuario extends JInternalFrame implements KeyListener,
		MouseListener {

	private static final long serialVersionUID = 1L;

	private JLabel fundo, foto, fitaFoto, webcam, nomeCompletoTexto,
			apelidoTexto, senhaTexto, perguntaSecretaTexto,
			respostaSecretaTexto, sobreMimTexto, dataDeCadastroTexto,
			usuarioAlterado;

	private JButton alterarAgora, limparTudo, cancelar;

	private JTextArea campoSobreMim;

	private JTextField campoNomeCompleto, campoApelido, campoPergunta,
			campoDataDeCadastro;

	private JPasswordField campoResposta, campoSenha;
	private JPanel painelPrincipal;

	private String apelidoOriginal = "";

	// Cor (es) usadas ...

	private Color corDotexto = new Color(139, 139, 139);

	public AlterandoUsuario(String nomeCompleto, String dataDeCadastro,
			String apelido, String senha, String perguntaSecreta,
			String respostaSecreta, String sobreMim) {

		// Adicionando aparência NIMBUS ao jframe ...

		apelidoOriginal = apelido;

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		// --------------------------------------------------------------------------------------------------------------

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		usuarioAlterado = new JLabel();

		usuarioAlterado
				.setIcon(new ImageIcon(
						AlterandoUsuario.class
								.getResource("/views/alterando/usuarios/jpg/Usuario_Alterado.jpg")));

		usuarioAlterado.setBounds(0, 0, 927, 412);

		usuarioAlterado.addMouseListener(this);
		usuarioAlterado.addKeyListener(this);

		usuarioAlterado.setVisible(false);
		painelPrincipal.add(usuarioAlterado);

		campoDataDeCadastro = new JTextField();

		campoDataDeCadastro
				.setToolTipText("Este Campo Não é Editável, Ele Somente Captura a Data Do Sistema Para Maior Segurança");

		campoDataDeCadastro.setEditable(false);
		campoDataDeCadastro.setForeground(corDotexto);
		campoDataDeCadastro.setBounds(576, 126, 99, 33);
		campoDataDeCadastro.setText(dataDeCadastro);

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

		campoSobreMim = new JTextArea();

		campoSobreMim.setToolTipText("Fale Mais Sobre Você Aqui Neste Campo");

		campoSobreMim.setForeground(corDotexto);
		campoSobreMim.setLineWrap(true);
		campoSobreMim.setBounds(19, 328, 400, 67);
		campoSobreMim.setText(sobreMim);

		campoSobreMim.addKeyListener(this);
		painelPrincipal.add(campoSobreMim);

		// --------------------------------------------------------------------------------------------------------------

		sobreMimTexto = new JLabel("Sobre Mim");

		sobreMimTexto.setForeground(corDotexto);
		sobreMimTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		sobreMimTexto.setBounds(20, 300, 150, 25);

		painelPrincipal.add(sobreMimTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoResposta = new JPasswordField();

		campoResposta.setForeground(corDotexto);
		campoResposta.setBounds(445, 236, 230, 33);

		campoResposta
				.setToolTipText("Aqui Você Digitará Uma Resposta Secreta Caso Queira Alterar a Senha Futuramente");

		campoResposta.setText(respostaSecreta);

		campoResposta.addKeyListener(this);
		painelPrincipal.add(campoResposta);

		// --------------------------------------------------------------------------------------------------------------

		respostaSecretaTexto = new JLabel("Pergunta Secreta");

		respostaSecretaTexto.setForeground(corDotexto);
		respostaSecretaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		respostaSecretaTexto.setBounds(310, 200, 150, 25);

		painelPrincipal.add(respostaSecretaTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoPergunta = new JTextField();

		campoPergunta.setForeground(corDotexto);
		campoPergunta.setBounds(445, 196, 230, 33);
		campoPergunta.setText(perguntaSecreta);

		campoPergunta
				.setToolTipText("Aqui Você Digitará Uma Pergunta Secreta Caso Queira Alterar a Senha Futuramente");

		campoPergunta.addKeyListener(this);
		painelPrincipal.add(campoPergunta);

		// --------------------------------------------------------------------------------------------------------------

		perguntaSecretaTexto = new JLabel("Resposta Secreta");

		perguntaSecretaTexto.setForeground(corDotexto);
		perguntaSecretaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		perguntaSecretaTexto.setBounds(310, 240, 150, 25);

		painelPrincipal.add(perguntaSecretaTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoSenha = new JPasswordField();

		campoSenha.setForeground(corDotexto);
		campoSenha.setBounds(140, 236, 150, 33);
		campoSenha.setText(senha);

		campoSenha
				.setToolTipText("Digite Aqui Uma Senha Para o Desbloqueio Do Programa Futuramente");

		campoSenha.addKeyListener(this);
		painelPrincipal.add(campoSenha);

		// --------------------------------------------------------------------------------------------------------------

		senhaTexto = new JLabel("Senha");

		senhaTexto.setForeground(corDotexto);
		senhaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		senhaTexto.setBounds(20, 240, 150, 25);

		painelPrincipal.add(senhaTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoApelido = new JTextField();

		campoApelido.setForeground(corDotexto);

		campoApelido
				.setToolTipText("Digite Aqui Um Apelido Para Que Futuramente a Pessoa Alterada Possa Desbloquear o Software");

		campoApelido.setBounds(140, 196, 150, 33);
		campoApelido.setText(apelido);

		campoApelido.addKeyListener(this);
		painelPrincipal.add(campoApelido);

		// --------------------------------------------------------------------------------------------------------------

		apelidoTexto = new JLabel("Apelido");

		apelidoTexto.setForeground(corDotexto);
		apelidoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		apelidoTexto.setBounds(20, 200, 150, 25);

		painelPrincipal.add(apelidoTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoNomeCompleto = new JTextField();

		campoNomeCompleto.setForeground(corDotexto);

		campoNomeCompleto
				.setToolTipText("Digite Aqui o Nome Completo Do Usuário Que Irá Ser Alterado");

		campoNomeCompleto.setBounds(140, 126, 290, 33);
		campoNomeCompleto.setText(nomeCompleto);

		campoNomeCompleto.addKeyListener(this);
		campoNomeCompleto.addMouseListener(this);
		painelPrincipal.add(campoNomeCompleto);

		// --------------------------------------------------------------------------------------------------------------

		nomeCompletoTexto = new JLabel("Nome Completo");

		nomeCompletoTexto.setForeground(corDotexto);
		nomeCompletoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		nomeCompletoTexto.setBounds(20, 130, 150, 25);

		painelPrincipal.add(nomeCompletoTexto);

		// --------------------------------------------------------------------------------------------------------------

		webcam = new JLabel(

				new ImageIcon(
						AlterandoUsuario.class
								.getResource("/views/alterando/usuarios/png/webcam.png")));

		webcam.setBounds(760, 285, 50, 50);
		webcam.setToolTipText("Clique Aqui Para Capturar Uma Foto Do Usuário Pela Sua Webcam");

		webcam.addKeyListener(this);
		webcam.addMouseListener(this);
		painelPrincipal.add(webcam);

		// --------------------------------------------------------------------------------------------------------------

		fitaFoto = new JLabel(

				new ImageIcon(
						AlterandoUsuario.class
								.getResource("/views/alterando/usuarios/png/Fita_Foto.PNG")));

		fitaFoto.setBounds(743, 82, 80, 20);
		painelPrincipal.add(fitaFoto);

		foto = new JLabel(new ImageIcon(

				AlterandoUsuario.class
						.getResource("/views/alterando/usuarios/jpg/Foto.jpg")));

		foto.setBounds(703, 94, 157, 157);

		foto.setToolTipText("Clique Aqui Para Selecionar Uma Foto Do Seu Computador e Adicionar a Alteração Do Usuário");

		foto.addKeyListener(this);
		foto.addMouseListener(this);
		painelPrincipal.add(foto);

		alterarAgora = new JButton("Alterar Agora");
		alterarAgora.setBounds(531, 360, 175, 35);

		alterarAgora
				.setToolTipText("Clique Aqui Para Alterar Este Usuário, Ou Aperte As Teclas (CTRL + A)");

		alterarAgora.addKeyListener(this);
		alterarAgora.addMouseListener(this);
		painelPrincipal.add(alterarAgora);

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

		cancelar.setToolTipText("Clique Aqui Para Cancelar a Alteração, Ou Aperte a Tecla (ESC)");
		cancelar.setBounds(817, 360, 85, 35);

		cancelar.addKeyListener(this);
		cancelar.addMouseListener(this);
		painelPrincipal.add(cancelar);

		// --------------------------------------------------------------------------------------------------------------

		fundo = new JLabel(

				new ImageIcon(AlterandoUsuario.class
						.getResource("/views/alterando/usuarios/jpg/Fundo.jpg")));

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
				TelaPrincipal.chamandoNovamenteGerenciarUsuarios();

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
		this.setTitle("Thallyta Móveis - Alterando Usuário");

		this.setClosable(true);

		try {

			this.setSelected(true);

		}

		catch (PropertyVetoException e1) {

			new ErroEncontrado();

		}

		this.setContentPane(painelPrincipal);
		this.setVisible(true);

		// Difinindo o ponteiro no campo NOME COMPLETO ...

		campoNomeCompleto.requestFocus();

	}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == cancelar) {

			dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarUsuarios();

			}

			catch (Exception e1) {

				new ErroEncontrado();

			}

		}

		if (mouseClick.getSource() == usuarioAlterado
				& usuarioAlterado.isVisible() == true) {

			dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarUsuarios();

			}

			catch (Exception e1) {

				new ErroEncontrado();

			}

		}

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

		else if (mouseClick.getSource() == alterarAgora) {

			Usuario usu = new Usuario();

			usu.setApelido(campoApelido.getText());
			usu.setDataDeCadastro(campoDataDeCadastro.getText());
			usu.setNomeCompleto(campoNomeCompleto.getText());
			usu.setPerguntaSecreta(campoPergunta.getText());
			usu.setRespostaSecreta(campoResposta.getText());
			usu.setSenha(campoSenha.getText());
			usu.setSobreMim(campoSobreMim.getText());

			try {

				boolean verificando = VerificandoErrosAlteracao
						.verificandoErros(usu, apelidoOriginal);

				if (verificando == true) {

					FachadaUsuarios alterando = new FachadaUsuarios();
					alterando.alterandoUsuario(apelidoOriginal, usu);

					usuarioAlterado.setVisible(true);

				}

				else {

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == foto) {

			foto.setIcon(new ImageIcon(
					AlterandoUsuario.class
							.getResource("/views/alterando/usuarios/jpg/Foto_Mouse.jpg")));

		}

		else if (mouseEntry.getSource() == webcam) {

			webcam.setIcon(new ImageIcon(
					AlterandoUsuario.class
							.getResource("/views/alterando/usuarios/png/webcam_mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == foto) {

			foto.setIcon(new ImageIcon(AlterandoUsuario.class
					.getResource("/views/alterando/usuarios/jpg/Foto.jpg")));

		}

		else if (mouseExit.getSource() == webcam) {

			webcam.setIcon(new ImageIcon(AlterandoUsuario.class
					.getResource("/views/alterando/usuarios/png/webcam.png")));

		}

	}

	@Override
	public void mousePressed(MouseEvent mousePress) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseReal) {

	}

	@SuppressWarnings("deprecation")
	@Override
	public void keyPressed(KeyEvent keyPress) {

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarUsuarios();

			}

			catch (Exception e1) {

				new ErroEncontrado();

			}

		}

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

		if (keyPress.getKeyCode() == KeyEvent.VK_A && keyPress.isControlDown()) {

			Usuario usu = new Usuario();

			usu.setApelido(campoApelido.getText());
			usu.setDataDeCadastro(campoDataDeCadastro.getText());
			usu.setNomeCompleto(campoNomeCompleto.getText());
			usu.setPerguntaSecreta(campoPergunta.getText());
			usu.setRespostaSecreta(campoResposta.getText());
			usu.setSenha(campoSenha.getText());
			usu.setSobreMim(campoSobreMim.getText());

			try {

				boolean verificando = VerificandoErrosAlteracao
						.verificandoErros(usu, apelidoOriginal);

				if (verificando == true) {

					FachadaUsuarios alterando = new FachadaUsuarios();
					alterando.alterandoUsuario(apelidoOriginal, usu);

					usuarioAlterado.setVisible(true);

				}

				else {

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& usuarioAlterado.isVisible() == true) {

			this.dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarUsuarios();

			}

			catch (Exception e1) {

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

}