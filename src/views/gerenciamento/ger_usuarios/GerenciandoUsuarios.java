/*
 * 
 * Falta fazer ...
 * 
 * 		- Campo de busca completo.
 * 		- Não aparecer os dados se senha e resposta secreta na tela.
 * 		- Fazer com que a tela de alteração de produtos fique interna.
 * 
 */

package views.gerenciamento.ger_usuarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTable.PrintMode;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;

import modelo.Usuario;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;
import fachada.FachadaUsuarios;

//Classe de gerenciamento dos dados da empresa ...

public class GerenciandoUsuarios extends JInternalFrame implements
		MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	// Objetos e variáveis de apoio

	JPanel painelPrincipal, painelTabela;
	JLabel fundo, botaoIr, usuarioRemovido, pdfGerado;
	JTable tabelaDeResultados;

	JTextField campoBusca;
	JButton alterar, remover, voltar, imprimir, gerarPDF;

	// Rodapé das colunas que vão ser incrementadas na tabela ...

	private String[] colunas = new String[] { "Nome Completo",
			"Data De Cadastro", "Apelido", "Senha", "Pergunta Secreta",
			"Resposta Secreta", "Sobre Mim" };

	JScrollPane scrollTabela;
	ArrayList<Usuario> usuariosCadastrados;

	// Cor (es) usadas ...

	private Color corDotexto = new Color(139, 139, 139);

	// Contrutor da classe ...

	public GerenciandoUsuarios() throws SQLException {

		// Adicionando um visual nimbus a tela ...

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {

			new ErroEncontrado();

		}

		usuariosCadastrados = new ArrayList<>();

		// Chamando a fachada, para a mesma se comunicar com o banco de dados e
		// assim resgatar todos os valores de lembretes ...

		FachadaUsuarios pegandoValores = new FachadaUsuarios();
		usuariosCadastrados = pegandoValores.listandoUsuarios();

		// Criando os dados que vão ser incrementados a tabela ...

		String[][] dados = new String[usuariosCadastrados.size()][];
		int i = 0;

		// Percorrendo ...

		for (Usuario usu : usuariosCadastrados) {

			dados[i] = new String[] { usu.getNomeCompleto(),
					usu.getDataDeCadastro(), usu.getApelido(), "***************",
					usu.getPerguntaSecreta(), "***************",
					usu.getSobreMim() };

			i += 1;

		}

		// Adicionando componentes a tela e paineis ...

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		usuarioRemovido = new JLabel(

				new ImageIcon(
						GerenciandoUsuarios.class
								.getResource("/views/gerenciamento/ger_usuarios/jpg/Usuario_Removido.JPG")));

		usuarioRemovido.setBounds(0, 0, 950, 500);

		usuarioRemovido.setVisible(false);

		usuarioRemovido.addMouseListener(this);
		usuarioRemovido.addKeyListener(this);
		painelPrincipal.add(usuarioRemovido);

		campoBusca = new JTextField("Buscar Usuário ...");
		campoBusca.setBounds(710, 49, 150, 22);
		campoBusca.setForeground(corDotexto);
		campoBusca.setBorder(null);

		campoBusca
				.setToolTipText("Digite Aqui o Usuário Desejado e Clique Em Ir, Ou Tecle Enter");

		campoBusca.addMouseListener(this);
		campoBusca.addKeyListener(this);
		painelPrincipal.add(campoBusca);

		pdfGerado = new JLabel();

		pdfGerado
				.setIcon(new ImageIcon(
						GerenciandoUsuarios.class
								.getResource("/views/gerenciamento/ger_usuarios/jpg/PDF_Gerado.jpg")));

		pdfGerado.setVisible(false);
		pdfGerado.setBounds(0, 0, 950, 500);

		pdfGerado.addMouseListener(this);
		pdfGerado.addKeyListener(this);

		painelPrincipal.add(pdfGerado);

		voltar = new JButton("Voltar");
		voltar.setToolTipText("Clique Aqui Para Voltar a Tela Principal, Ou Aperte (ESC)");
		voltar.setBounds(842, 451, 90, 35);

		imprimir = new JButton("Imprimir");
		imprimir.setToolTipText("Clique Aqui Para Imprimir Uma Lista Dos Usuários Cadastrados");
		imprimir.setBounds(560, 451, 90, 35);

		imprimir.addMouseListener(this);
		imprimir.addKeyListener(this);

		painelPrincipal.add(imprimir);

		// -----------------------------------------------------------------------------------------------------------------------

		gerarPDF = new JButton("Relatório");
		gerarPDF.setToolTipText("Clique Aqui Para Gerar Um PDF De Todos Os Usuários Cadastrados");
		gerarPDF.setBounds(466, 451, 90, 35);

		gerarPDF.addMouseListener(this);
		gerarPDF.addKeyListener(this);

		painelPrincipal.add(gerarPDF);

		// Ainda adicionando ...

		voltar.addMouseListener(this);
		voltar.addKeyListener(this);
		painelPrincipal.add(voltar);

		alterar = new JButton("Alterar");
		alterar.setBounds(748, 451, 90, 35);
		alterar.setToolTipText("Clique Aqui Para Alterar o Usuário Selecionado, Ou Tecle (CTRL + A)");

		alterar.addMouseListener(this);
		alterar.addKeyListener(this);
		painelPrincipal.add(alterar);

		remover = new JButton("Remover");

		// Ainda adicionando ...

		remover.setToolTipText("Clique Aqui Para Remover o Usuário Selecionado, Ou Tecle (CTRL + R)");
		remover.setBounds(654, 451, 90, 35);

		remover.addMouseListener(this);
		remover.addKeyListener(this);
		painelPrincipal.add(remover);

		painelTabela = new JPanel();
		painelTabela.setLayout(new BorderLayout());
		painelTabela.setBounds(20, 140, 910, 300);
		painelPrincipal.add(painelTabela);

		DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

		// Criando uma tabela não editável ...

		tabelaDeResultados = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		tabelaDeResultados.addMouseListener(this);
		tabelaDeResultados.addKeyListener(this);

		tabelaDeResultados.getTableHeader().setReorderingAllowed(false);

		tabelaDeResultados
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollTabela = new JScrollPane(tabelaDeResultados);
		painelTabela.add(scrollTabela);

		botaoIr = new JLabel(

				new ImageIcon(
						GerenciandoUsuarios.class
								.getResource("/views/gerenciamento/ger_usuarios/png/Ir.png")));

		botaoIr.setToolTipText("Clique Aqui Para Pesquisar o Usuário Digitado");
		botaoIr.setBounds(866, 45, 62, 32);

		// Ainda adicionando ...

		botaoIr.addMouseListener(this);
		botaoIr.addKeyListener(this);
		painelPrincipal.add(botaoIr);

		fundo = new JLabel(

				new ImageIcon(
						GerenciandoUsuarios.class
								.getResource("/views/gerenciamento/ger_usuarios/jpg/Fundo.jpg")));

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

		tabelaDeResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabelaDeResultados.getColumnModel().getColumn(0).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(2).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(3).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(4).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(5).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(6).setPreferredWidth(190);

		// Propriedades da tela ...

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(956, 528);
		this.setResizable(false);
		this.setTitle("Thallyta Móveis - Gerenciando Usuário (s)");

		this.setClosable(true);

		try {

			this.setSelected(true);

		}

		catch (PropertyVetoException e1) {

			new ErroEncontrado();

		}

		this.setContentPane(painelPrincipal);
		this.setVisible(true);

	}

	// Eventos de mouse para operações clicáveis e outros ...

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		int coluna = tabelaDeResultados.getSelectedColumn();
		int linha = tabelaDeResultados.getSelectedRow();

		if (mouseClick.getSource() == imprimir) {

			try {

				PrinterJob job = PrinterJob.getPrinterJob();

				job.setPrintable(tabelaDeResultados.getPrintable(
						PrintMode.FIT_WIDTH, null, null));

				job.setJobName("Nome do Trabalho de impressão");

				if (job.printDialog()) {

					job.print();

				}

			}

			catch (PrinterException e) {

				new ErroEncontrado();

			}

		}

		if (mouseClick.getSource() == gerarPDF) {

			JFileChooser chooser;
			chooser = new JFileChooser();

			String caminho = "";

			int retorno = chooser.showSaveDialog(null);

			if (retorno == JFileChooser.APPROVE_OPTION) {

				caminho = chooser.getSelectedFile().getAbsolutePath();
				new GerarPDF(usuariosCadastrados, caminho);

				campoBusca.setVisible(false);
				pdfGerado.setVisible(true);

			}

		}

		if (pdfGerado.isVisible() == true & mouseClick.getSource() == pdfGerado) {

			campoBusca.setVisible(true);
			pdfGerado.setVisible(false);

		}

		if (mouseClick.getSource() == remover
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			painelPrincipal.setFocusable(false);
			int row = tabelaDeResultados.getSelectedRow();

			Usuario usuarioPego = new Usuario();

			usuarioPego.setNomeCompleto((String) tabelaDeResultados.getValueAt(
					row, 0));

			usuarioPego.setDataDeCadastro((String) tabelaDeResultados
					.getValueAt(row, 1));

			usuarioPego.setApelido((String) tabelaDeResultados.getValueAt(row,
					2));

			usuarioPego
					.setSenha((String) tabelaDeResultados.getValueAt(row, 3));

			usuarioPego.setPerguntaSecreta((String) tabelaDeResultados
					.getValueAt(row, 4));

			usuarioPego.setRespostaSecreta((String) tabelaDeResultados
					.getValueAt(row, 5));

			usuarioPego.setSobreMim((String) tabelaDeResultados.getValueAt(row,
					6));

			FachadaUsuarios removendo = new FachadaUsuarios();
			removendo.removendoUsuarios(usuarioPego);

			usuarioRemovido.setVisible(true);

		}

		if (mouseClick.getSource() == alterar
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			this.dispose();
			int row = tabelaDeResultados.getSelectedRow();

			TelaPrincipal.AbrindoAlterarUsuarios(
					(String) tabelaDeResultados.getValueAt(row, 0),
					(String) tabelaDeResultados.getValueAt(row, 1),
					(String) tabelaDeResultados.getValueAt(row, 2),
					(String) tabelaDeResultados.getValueAt(row, 3),
					(String) tabelaDeResultados.getValueAt(row, 4),
					(String) tabelaDeResultados.getValueAt(row, 5),
					(String) tabelaDeResultados.getValueAt(row, 6));

		}

		if (usuarioRemovido.isVisible() == true
				& mouseClick.getSource() == usuarioRemovido) {

			FachadaUsuarios pegandoValores = new FachadaUsuarios();

			try {

				usuariosCadastrados = pegandoValores.listandoUsuarios();

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			// Criando os dados que vão ser incrementados a tabela ...

			String[][] dados = new String[usuariosCadastrados.size()][];
			int i = 0;

			// Percorrendo ...

			for (Usuario usu : usuariosCadastrados) {

				dados[i] = new String[] { usu.getNomeCompleto(),
						usu.getDataDeCadastro(), usu.getApelido(),
						usu.getSenha(), usu.getPerguntaSecreta(),
						usu.getRespostaSecreta(), usu.getSobreMim() };

				i += 1;

			}

			DefaultTableModel modelo = new DefaultTableModel(dados, colunas);
			tabelaDeResultados.setModel(modelo);

		}

		if (mouseClick.getClickCount() == 2
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			this.dispose();
			int row = tabelaDeResultados.getSelectedRow();

			TelaPrincipal.AbrindoAlterarUsuarios(
					(String) tabelaDeResultados.getValueAt(row, 0),
					(String) tabelaDeResultados.getValueAt(row, 1),
					(String) tabelaDeResultados.getValueAt(row, 2),
					(String) tabelaDeResultados.getValueAt(row, 3),
					(String) tabelaDeResultados.getValueAt(row, 4),
					(String) tabelaDeResultados.getValueAt(row, 5),
					(String) tabelaDeResultados.getValueAt(row, 6));

		}

		if (mouseClick.getSource() == voltar) {

			dispose();

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoUsuarios.class
							.getResource("/views/gerenciamento/ger_usuarios/png/Ir_Mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoUsuarios.class
							.getResource("/views/gerenciamento/ger_usuarios/png/Ir.png")));

		}

	}

	@Override
	public void mousePressed(MouseEvent mousePress) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseReal) {

	}

	@Override
	public void keyPressed(KeyEvent keyPress) {

		if (pdfGerado.isFocusable() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			pdfGerado.setVisible(false);

		}

		if (usuarioRemovido.isFocusable() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			usuarioRemovido.setVisible(false);

		}

		int coluna = tabelaDeResultados.getSelectedColumn();
		int linha = tabelaDeResultados.getSelectedRow();

		if (keyPress.getKeyCode() == KeyEvent.VK_R && keyPress.isControlDown()
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			painelPrincipal.setFocusable(false);
			int row = tabelaDeResultados.getSelectedRow();

			Usuario usuarioPego = new Usuario();

			usuarioPego.setNomeCompleto((String) tabelaDeResultados.getValueAt(
					row, 0));

			usuarioPego.setDataDeCadastro((String) tabelaDeResultados
					.getValueAt(row, 1));

			usuarioPego.setApelido((String) tabelaDeResultados.getValueAt(row,
					2));

			usuarioPego
					.setSenha((String) tabelaDeResultados.getValueAt(row, 3));

			usuarioPego.setPerguntaSecreta((String) tabelaDeResultados
					.getValueAt(row, 4));

			usuarioPego.setRespostaSecreta((String) tabelaDeResultados
					.getValueAt(row, 5));

			usuarioPego.setSobreMim((String) tabelaDeResultados.getValueAt(row,
					6));

			FachadaUsuarios removendo = new FachadaUsuarios();
			removendo.removendoUsuarios(usuarioPego);

			usuarioRemovido.setVisible(true);

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_A
				&& keyPress.isControlDown()
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			this.dispose();
			int row = tabelaDeResultados.getSelectedRow();

			TelaPrincipal.AbrindoAlterarUsuarios(
					(String) tabelaDeResultados.getValueAt(row, 0),
					(String) tabelaDeResultados.getValueAt(row, 1),
					(String) tabelaDeResultados.getValueAt(row, 2),
					(String) tabelaDeResultados.getValueAt(row, 3),
					(String) tabelaDeResultados.getValueAt(row, 4),
					(String) tabelaDeResultados.getValueAt(row, 5),
					(String) tabelaDeResultados.getValueAt(row, 6));

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

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