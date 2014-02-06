// Tela Concluída ...

package views.alterando.lembretes;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.text.MaskFormatter;

import modelo.Lembrete;
import views.cadastros.cad_lembretes.CadastroLembretes;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;
import fachada.FachadaLembretes;

public class AlterandoLembrete extends JInternalFrame implements KeyListener,
		MouseListener {

	private static final long serialVersionUID = 1L;

	// Variáveis e objetos de apoio ...

	private JLabel fundo, codigoTexto, tituloTexto, descricaoTexto,
			dataDeCadastroTexto, dataDeAvisoTexto, lembreteAlterado;

	private JPanel painelPrincipal, painelDescricao;
	private JTextField campoTitulo, campoDataDeCadastro, campoCodigo;
	private JFormattedTextField campoDataDeAviso;
	private JScrollPane scrollDescricao;

	private JTextArea campoDescricao;
	private JButton alterarAgora, limparTudo, cancelar;

	private Color corDotexto = new Color(139, 139, 139);
	private static String CodigoOriginal;

	public AlterandoLembrete(String codigo, String titulo, String descricao,
			String dataDeCadastro, String dataDeAviso) {

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		CodigoOriginal = codigo;

		// Adicionando componentes a tela JFrame ...

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		lembreteAlterado = new JLabel();

		lembreteAlterado
				.setIcon(new ImageIcon(
						AlterandoLembrete.class
								.getResource("/views/alterando/lembretes/jpg/Lembrete_Alterado.jpg")));

		lembreteAlterado.setBounds(0, 0, 672, 342);

		lembreteAlterado.addKeyListener(this);
		lembreteAlterado.addMouseListener(this);

		lembreteAlterado.setVisible(false);

		painelPrincipal.add(lembreteAlterado);

		campoDataDeAviso = new JFormattedTextField((setMascara("##/##/####")));

		campoDataDeAviso.setForeground(corDotexto);
		campoDataDeAviso.setBounds(558, 165, 90, 33);
		campoDataDeAviso.setText(dataDeAviso);

		campoDataDeAviso
				.setToolTipText("Digite Aqui a Data Que Você Quer Ser Informado Sobre Este Aviso");

		campoDataDeAviso.addKeyListener(this);
		painelPrincipal.add(campoDataDeAviso);

		// --------------------------------------------------------------------------------------------------------------

		dataDeAvisoTexto = new JLabel("Data De Aviso");

		dataDeAvisoTexto.setForeground(corDotexto);
		dataDeAvisoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		dataDeAvisoTexto.setBounds(410, 170, 110, 25);

		painelPrincipal.add(dataDeAvisoTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoDataDeCadastro = new JTextField();

		campoDataDeCadastro
				.setToolTipText("Este Campo Não é Editável, Ele Somente Captura a Data Do Sistema Para Maior Segurança");

		campoDataDeCadastro.setForeground(corDotexto);
		campoDataDeCadastro.setEditable(false);
		campoDataDeCadastro.setBounds(558, 125, 90, 33);
		campoDataDeCadastro.setFocusable(false);

		campoDataDeCadastro.setText(dataDeCadastro);

		campoDataDeCadastro.addKeyListener(this);
		painelPrincipal.add(campoDataDeCadastro);

		// --------------------------------------------------------------------------------------------------------------

		dataDeCadastroTexto = new JLabel("Data De Cadastro");

		dataDeCadastroTexto.setForeground(corDotexto);
		dataDeCadastroTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		dataDeCadastroTexto.setBounds(410, 130, 120, 25);

		painelPrincipal.add(dataDeCadastroTexto);

		// --------------------------------------------------------------------------------------------------------------

		alterarAgora = new JButton("Alterar Agora");
		alterarAgora.setBounds(302, 300, 150, 35);

		alterarAgora
				.setToolTipText("Clique Aqui Para Alterar Este Lembrete, Ou Aperte As Teclas (CTRL + A)");

		alterarAgora.addKeyListener(this);
		alterarAgora.addMouseListener(this);
		painelPrincipal.add(alterarAgora);

		// --------------------------------------------------------------------------------------------------------------

		limparTudo = new JButton("Limpar Tudo");
		limparTudo.setBounds(455, 300, 105, 35);

		limparTudo
				.setToolTipText("Clique Aqui Para Limpar Todos Os Campos, Ou Aperte As Teclas (CTRL + L)");

		limparTudo.addKeyListener(this);
		limparTudo.addMouseListener(this);
		painelPrincipal.add(limparTudo);

		// --------------------------------------------------------------------------------------------------------------

		cancelar = new JButton("Cancelar");

		cancelar.setToolTipText("Clique Aqui Para Cancelar a Alteração, Ou Aperte a Tecla (ESC)");
		cancelar.setBounds(563, 300, 85, 35);

		cancelar.addKeyListener(this);
		cancelar.addMouseListener(this);
		painelPrincipal.add(cancelar);

		// --------------------------------------------------------------------------------------------------------------

		painelDescricao = new JPanel();
		painelDescricao.setLayout(new BorderLayout());
		painelDescricao.setBounds(102, 220, 544, 70);

		campoDescricao = new JTextArea();
		campoDescricao.setBounds(102, 220, 544, 70);
		campoDescricao.setLineWrap(true);
		campoDescricao.setForeground(corDotexto);
		campoDescricao.setText(descricao);

		campoDescricao
				.setToolTipText("Digite Aqui a Descrição Completa Do Lembrete");

		scrollDescricao = new JScrollPane(campoDescricao);
		painelDescricao.add(scrollDescricao);
		painelPrincipal.add(painelDescricao);

		// --------------------------------------------------------------------------------------------------------------

		descricaoTexto = new JLabel("Descrição");

		descricaoTexto.setForeground(corDotexto);
		descricaoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		descricaoTexto.setBounds(20, 222, 75, 25);

		painelPrincipal.add(descricaoTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoTitulo = new JTextField();

		campoTitulo.setToolTipText("Digite Neste Campo o Título Do Lembrete ");

		campoTitulo.setForeground(corDotexto);
		campoTitulo.setBounds(100, 165, 250, 33);
		campoTitulo.setText(titulo);

		campoTitulo.addKeyListener(this);
		painelPrincipal.add(campoTitulo);

		// --------------------------------------------------------------------------------------------------------------

		tituloTexto = new JLabel("Título");

		tituloTexto.setForeground(corDotexto);
		tituloTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		tituloTexto.setBounds(20, 170, 75, 25);

		painelPrincipal.add(tituloTexto);

		// --------------------------------------------------------------------------------------------------------------

		campoCodigo = new JTextField();

		campoCodigo
				.setToolTipText("Digite Aqui Algum Código Para Possível Busca Futuramente");

		campoCodigo.setForeground(corDotexto);
		campoCodigo.setBounds(100, 125, 100, 33);
		campoCodigo.setText(codigo);

		campoCodigo.addKeyListener(this);
		painelPrincipal.add(campoCodigo);

		// --------------------------------------------------------------------------------------------------------------

		codigoTexto = new JLabel("Código");

		codigoTexto.setForeground(corDotexto);
		codigoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		codigoTexto.setBounds(20, 130, 75, 25);

		painelPrincipal.add(codigoTexto);

		// Adicionando Fundo a Tela ...

		fundo = new JLabel(

				new ImageIcon(
						CadastroLembretes.class
								.getResource("/views/alterando/lembretes/jpg/Fundo.jpg")));

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
				TelaPrincipal.chamandoNovamenteGerenciarLembretes();

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
		this.setTitle("Thallyta Móveis - Alterando Lembrete");
		this.setVisible(true);

		// Difinindo o ponteiro no campo CÓDIGO ...

		campoCodigo.requestFocus();

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
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == cancelar) {

			this.dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarLembretes();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		if (lembreteAlterado.isVisible() == true
				& mouseClick.getSource() == lembreteAlterado) {

			this.dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarLembretes();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		else if (mouseClick.getSource() == limparTudo) {

			campoCodigo.setText("");
			campoDataDeAviso.setText("");
			campoDescricao.setText("");
			campoTitulo.setText("");

			campoCodigo.requestFocus();

		}

		else if (mouseClick.getSource() == alterarAgora) {

			VerificandoErrosAlteracao verificandoErros = new VerificandoErrosAlteracao();

			try {

				boolean verificando = verificandoErros.verificandoErros(
						campoCodigo.getText(), campoTitulo.getText(),
						campoDataDeAviso.getText(), campoDescricao.getText(),
						CodigoOriginal);

				if (verificando == true) {

					Lembrete lembrete = new Lembrete();

					lembrete.setCodigo(Integer.parseInt(campoCodigo.getText()));
					lembrete.setData_de_aviso(campoDataDeAviso.getText());
					lembrete.setData_de_cadastro(campoDataDeCadastro.getText());
					lembrete.setDescricao(campoDescricao.getText());
					lembrete.setTitulo(campoTitulo.getText());

					FachadaLembretes chamandoMetodo = new FachadaLembretes();
					chamandoMetodo.alterandoLembrete(CodigoOriginal, lembrete);

					painelPrincipal.setEnabled(false);
					lembreteAlterado.setVisible(true);

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

	@Override
	public void keyPressed(KeyEvent keyPress) {

		if (keyPress.getKeyCode() == KeyEvent.VK_A && keyPress.isControlDown()) {

			VerificandoErrosAlteracao verificandoErros = new VerificandoErrosAlteracao();

			try {

				boolean verificando = verificandoErros.verificandoErros(
						campoCodigo.getText(), campoTitulo.getText(),
						campoDataDeAviso.getText(), campoDescricao.getText(),
						CodigoOriginal);

				if (verificando == true) {

					Lembrete lembrete = new Lembrete();

					lembrete.setCodigo(Integer.parseInt(campoCodigo.getText()));
					lembrete.setData_de_aviso(campoDataDeAviso.getText());
					lembrete.setData_de_cadastro(campoDataDeCadastro.getText());
					lembrete.setDescricao(campoDescricao.getText());
					lembrete.setTitulo(campoTitulo.getText());

					FachadaLembretes chamandoMetodo = new FachadaLembretes();
					chamandoMetodo.alterandoLembrete(CodigoOriginal, lembrete);

					painelPrincipal.setEnabled(false);
					lembreteAlterado.setVisible(true);

				}

				else {

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		if (lembreteAlterado.isVisible() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			this.dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarLembretes();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			this.dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarLembretes();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_L) {

			campoCodigo.setText("");
			campoDataDeAviso.setText("");
			campoDescricao.setText("");
			campoTitulo.setText("");

			campoCodigo.requestFocus();

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

}