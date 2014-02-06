// Tela concluída ...

package views.cadastros.cad_lembretes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.text.MaskFormatter;

import modelo.Lembrete;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;
import fachada.FachadaLembretes;

public class CadastroLembretes extends JInternalFrame implements MouseListener,
		KeyListener {

	private static final long serialVersionUID = 1L;

	// Variáveis e objetos de apoio ...

	private JLabel fundo, codigoTexto, tituloTexto, descricaoTexto,
			dataDeCadastroTexto, dataDeAvisoTexto, lembreteSalvo;

	private JPanel painelPrincipal, painelDescricao;
	private JTextField campoTitulo, campoDataDeCadastro, campoCodigo;
	private JFormattedTextField campoDataDeAviso;

	private JScrollPane srollDescricao;

	private JTextArea campoDescricao;
	private JButton salvar, salvarCadastrarOutro, limparTudo, cancelar;

	private Color corDotexto = new Color(139, 139, 139);
	private int verificadorDirecao = 0;

	public CadastroLembretes() {

		// Definindo layout da tela ...

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			// Adicionando componentes a tela JFrame ...

			painelPrincipal = new JPanel();
			painelPrincipal.setLayout(new BorderLayout());

			lembreteSalvo = new JLabel(

					new ImageIcon(
							CadastroLembretes.class
									.getResource("/views/cadastros/cad_lembretes/jpg/Lembrete_Salvo.JPG")));

			lembreteSalvo.setBounds(0, 0, 672, 342);
			lembreteSalvo.setVisible(false);

			lembreteSalvo.addMouseListener(this);
			lembreteSalvo.addKeyListener(this);

			painelPrincipal.add(lembreteSalvo);

			campoDataDeAviso = new JFormattedTextField(
					(setMascara("##/##/####")));

			campoDataDeAviso.setForeground(corDotexto);
			campoDataDeAviso.setBounds(558, 165, 90, 33);

			campoDataDeAviso
					.setToolTipText("Digite Aqui a Data Que Você Quer Ser Informado Sobre Este Aviso");

			campoDataDeAviso.addKeyListener(this);
			painelPrincipal.add(campoDataDeAviso);

			// --------------------------------------------------------------------------------------------------------------

			dataDeAvisoTexto = new JLabel("Data De Aviso *");

			dataDeAvisoTexto.setForeground(corDotexto);
			dataDeAvisoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataDeAvisoTexto.setBounds(410, 170, 110, 25);

			painelPrincipal.add(dataDeAvisoTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoDataDeCadastro = new JTextField();

			campoDataDeCadastro
					.setToolTipText("Este Campo Não é Editável, Ele Somente Captura a Data Do Sistema Para Maior Segurança");

			campoDataDeCadastro.setForeground(corDotexto);
			campoDataDeCadastro.setText(pegandoDataDoSistema());
			campoDataDeCadastro.setEditable(false);
			campoDataDeCadastro.setBounds(558, 125, 90, 33);
			campoDataDeCadastro.setFocusable(false);

			campoDataDeCadastro.addKeyListener(this);
			painelPrincipal.add(campoDataDeCadastro);

			// --------------------------------------------------------------------------------------------------------------

			dataDeCadastroTexto = new JLabel("Data De Cadastro *");

			dataDeCadastroTexto.setForeground(corDotexto);
			dataDeCadastroTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataDeCadastroTexto.setBounds(410, 130, 130, 25);

			painelPrincipal.add(dataDeCadastroTexto);

			// --------------------------------------------------------------------------------------------------------------

			salvar = new JButton("Salvar");
			salvar.setBounds(184, 296, 90, 35);
			salvar.setToolTipText("Clique Aqui Para Salvar Este Lembrete, Ou Aperte As Teclas (CTRL + S)");

			salvar.addKeyListener(this);
			salvar.addMouseListener(this);
			painelPrincipal.add(salvar);

			// --------------------------------------------------------------------------------------------------------------

			salvarCadastrarOutro = new JButton("Salvar e Cadastrar Outro");
			salvarCadastrarOutro.setBounds(277, 296, 175, 35);

			salvarCadastrarOutro
					.setToolTipText("Clique Aqui Para Salvar Este Lembrete e Cadastrar Outro, Ou Aperte As Teclas (CTRL + F)");

			salvarCadastrarOutro.addKeyListener(this);
			salvarCadastrarOutro.addMouseListener(this);
			painelPrincipal.add(salvarCadastrarOutro);

			// --------------------------------------------------------------------------------------------------------------

			limparTudo = new JButton("Limpar Tudo");
			limparTudo.setBounds(455, 296, 105, 35);

			limparTudo
					.setToolTipText("Clique Aqui Para Limpar Todos Os Campos, Ou Aperte As Teclas (CTRL + L)");

			limparTudo.addKeyListener(this);
			limparTudo.addMouseListener(this);
			painelPrincipal.add(limparTudo);

			// --------------------------------------------------------------------------------------------------------------

			cancelar = new JButton("Cancelar");

			cancelar.setToolTipText("Clique Aqui Para Cancelar o Cadastro, Ou Aperte a Tecla (ESC)");
			cancelar.setBounds(563, 296, 85, 35);

			cancelar.addKeyListener(this);
			cancelar.addMouseListener(this);
			painelPrincipal.add(cancelar);

			// --------------------------------------------------------------------------------------------------------------

			painelDescricao = new JPanel();
			painelDescricao.setLayout(new BorderLayout());
			painelDescricao.setBounds(102, 220, 544, 70);

			campoDescricao = new JTextArea();
			campoDescricao.setBounds(100, 225, 548, 70);
			campoDescricao.setLineWrap(true);
			campoDescricao.setForeground(corDotexto);

			campoDescricao
					.setToolTipText("Digite Aqui a Descrição Completa Do Lembrete");

			srollDescricao = new JScrollPane(campoDescricao);
			painelDescricao.add(srollDescricao);
			painelPrincipal.add(painelDescricao);

			campoDescricao.addKeyListener(this);

			// --------------------------------------------------------------------------------------------------------------

			descricaoTexto = new JLabel("Descrição *");

			descricaoTexto.setForeground(corDotexto);
			descricaoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			descricaoTexto.setBounds(20, 222, 85, 25);

			painelPrincipal.add(descricaoTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoTitulo = new JTextField();

			campoTitulo
					.setToolTipText("Digite Neste Campo o Título Do Lembrete ");

			campoTitulo.setForeground(corDotexto);
			campoTitulo.setBounds(100, 165, 250, 33);

			campoTitulo.addKeyListener(this);
			painelPrincipal.add(campoTitulo);

			// --------------------------------------------------------------------------------------------------------------

			tituloTexto = new JLabel("Título *");

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

			campoCodigo.addKeyListener(this);
			painelPrincipal.add(campoCodigo);

			// --------------------------------------------------------------------------------------------------------------

			codigoTexto = new JLabel("Código *");

			codigoTexto.setForeground(corDotexto);
			codigoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			codigoTexto.setBounds(20, 130, 75, 25);

			painelPrincipal.add(codigoTexto);

			// Adicionando Fundo a Tela ...

			fundo = new JLabel(

					new ImageIcon(
							CadastroLembretes.class
									.getResource("/views/cadastros/cad_lembretes/jpg/Fundo.jpg")));

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

			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.setSize(678, 370);
			this.setResizable(false);

			this.setContentPane(painelPrincipal);
			this.setTitle("Thallyta Móveis - Cadastrando Novo Lembrete");

			this.setClosable(true);
			this.setSelected(true);
			this.setVisible(true);

			// Difinindo o ponteiro no campo CÓDIGO ...

			campoCodigo.requestFocus();

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

		if (lembreteSalvo.isVisible() & mouseClick.getSource() == lembreteSalvo
				& verificadorDirecao == 1) {

			this.dispose();

		}

		if (lembreteSalvo.isVisible() & mouseClick.getSource() == lembreteSalvo
				& verificadorDirecao == 2) {

			lembreteSalvo.setVisible(false);

			campoCodigo.setText("");
			campoDataDeAviso.setText("");
			campoDescricao.setText("");
			campoTitulo.setText("");

			campoCodigo.requestFocus();

		}

		else if (mouseClick.getSource() == limparTudo) {

			campoCodigo.setText("");
			campoDataDeAviso.setText("");
			campoDescricao.setText("");
			campoTitulo.setText("");

			campoCodigo.requestFocus();

		}

		else if (mouseClick.getSource() == salvar) {

			try {

				VerificandoErros_Lembretes verif = new VerificandoErros_Lembretes();

				boolean verificado = verif.verificandoErros(
						campoCodigo.getText(), campoTitulo.getText(),
						campoDataDeAviso.getText(), campoDescricao.getText());

				if (verificado == true) {

					Lembrete lem = new Lembrete();

					lem.setCodigo(Integer.parseInt(campoCodigo.getText()));
					lem.setTitulo(campoTitulo.getText());
					lem.setDescricao(campoDescricao.getText());

					lem.setData_de_cadastro(campoDataDeCadastro.getText());
					lem.setData_de_aviso(campoDataDeAviso.getText());

					FachadaLembretes chamandoMetodo_Adicionar = new FachadaLembretes();
					chamandoMetodo_Adicionar.adicionarLembrete(lem);

					verificadorDirecao = 1;
					lembreteSalvo.setVisible(true);

					TelaPrincipal.adicionandoValoresAsTabelas();

				}

				else {

				}

			}

			catch (Exception e) {

			}

		}

		else if (mouseClick.getSource() == salvarCadastrarOutro) {

			try {

				VerificandoErros_Lembretes verif = new VerificandoErros_Lembretes();

				boolean verificado = verif.verificandoErros(
						campoCodigo.getText(), campoTitulo.getText(),
						campoDataDeAviso.getText(), campoDescricao.getText());

				if (verificado == true) {

					Lembrete lem = new Lembrete();

					lem.setCodigo(Integer.parseInt(campoCodigo.getText()));
					lem.setTitulo(campoTitulo.getText());
					lem.setDescricao(campoDescricao.getText());

					lem.setData_de_cadastro(campoDataDeCadastro.getText());
					lem.setData_de_aviso(campoDataDeAviso.getText());

					FachadaLembretes chamandoMetodo_Adicionar = new FachadaLembretes();
					chamandoMetodo_Adicionar.adicionarLembrete(lem);

					verificadorDirecao = 2;
					lembreteSalvo.setVisible(true);

					TelaPrincipal.adicionandoValoresAsTabelas();

				}

				else {

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			// Quando o número do campo estiver errado (null, espaços, letras ou
			// valores maiores) ...

			catch (NumberFormatException e) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"O código digitado está incorreto e não pode ser armazenado, tente outro ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

					campoCodigo.setText("");
					campoCodigo.requestFocus();

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

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

	// --------------------------------------------------------------------------------------------------------------

	private String pegandoDataDoSistema() {

		Date now = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String s = df.format(now);

		return s;

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

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			dispose();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_S
				&& keyPress.isControlDown()) {

			try {

				VerificandoErros_Lembretes verif = new VerificandoErros_Lembretes();

				boolean verificado = verif.verificandoErros(
						campoCodigo.getText(), campoTitulo.getText(),
						campoDataDeAviso.getText(), campoDescricao.getText());

				if (verificado == true) {

					Lembrete lem = new Lembrete();

					lem.setCodigo(Integer.parseInt(campoCodigo.getText()));
					lem.setTitulo(campoTitulo.getText());
					lem.setDescricao(campoDescricao.getText());

					lem.setData_de_cadastro(campoDataDeCadastro.getText());
					lem.setData_de_aviso(campoDataDeAviso.getText());

					FachadaLembretes chamandoMetodo_Adicionar = new FachadaLembretes();
					chamandoMetodo_Adicionar.adicionarLembrete(lem);

					verificadorDirecao = 1;
					lembreteSalvo.setVisible(true);

					TelaPrincipal.adicionandoValoresAsTabelas();

				}

				else {

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			// Quando o número do campo estiver errado (null, espaços, letras ou
			// valores maiores) ...

			catch (NumberFormatException e) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"O código digitado está incorreto e não pode ser armazenado, tente outro ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

					campoCodigo.setText("");
					campoCodigo.requestFocus();

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_F
				&& keyPress.isControlDown()) {

			try {

				VerificandoErros_Lembretes verif = new VerificandoErros_Lembretes();

				boolean verificado = verif.verificandoErros(
						campoCodigo.getText(), campoTitulo.getText(),
						campoDataDeAviso.getText(), campoDescricao.getText());

				if (verificado == true) {

					Lembrete lem = new Lembrete();

					lem.setCodigo(Integer.parseInt(campoCodigo.getText()));
					lem.setTitulo(campoTitulo.getText());
					lem.setDescricao(campoDescricao.getText());

					lem.setData_de_cadastro(campoDataDeCadastro.getText());
					lem.setData_de_aviso(campoDataDeAviso.getText());

					FachadaLembretes chamandoMetodo_Adicionar = new FachadaLembretes();
					chamandoMetodo_Adicionar.adicionarLembrete(lem);

					verificadorDirecao = 2;
					lembreteSalvo.setVisible(true);

					TelaPrincipal.adicionandoValoresAsTabelas();

				}

				else {

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			// Quando o número do campo estiver errado (null, espaços, letras ou
			// valores maiores) ...

			catch (NumberFormatException e) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"O código digitado está incorreto e não pode ser armazenado, tente outro ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

					campoCodigo.setText("");
					campoCodigo.requestFocus();

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_L
				&& keyPress.isControlDown()) {

			campoCodigo.setText("");
			campoDataDeAviso.setText("");
			campoDescricao.setText("");
			campoTitulo.setText("");

			campoCodigo.requestFocus();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& lembreteSalvo.isVisible() == true && verificadorDirecao == 1) {

			this.dispose();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& lembreteSalvo.isVisible() == true && verificadorDirecao == 2) {

			lembreteSalvo.setVisible(false);

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
	public void keyTyped(KeyEvent KeyType) {

	}

}