package views.gerenciamento.ger_fornecedores;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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

import modelo.Fornecedor;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;
import fachada.FachadaFornecedores;

public class GerenciandoFornecedores extends JInternalFrame implements
		MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	// Objetos e variáveis de apoio ...

	private JPanel painelPrincipal, painelTabela;
	private JButton voltar, remover, alterar, imprimir, gerarPDF;
	private JTextField campoBusca;

	private JLabel botaoIr, fundo, fornecedorRemovido, pdfGerado,
			fornecedoresCadastrados;
	private JTable tabelaDeResultados;
	private JScrollPane scrollTabela;

	// Rodapé das colunas que vão ser incrementadas na tabela ...

	private String[] colunas = new String[] { "Código", "Empresa", "CEP",
			"Rua", "Bairro", "Cidade", "Estado", "Telefone", "Fax", "Email",
			"Site", "Observações", "Data De Cadastro", "Url Da Foto" };

	private ArrayList<Fornecedor> fornecedores = new ArrayList<>();

	// Alguma (s) cor (es) ...

	private Color corDotexto = new Color(139, 139, 139);

	public GerenciandoFornecedores() throws SQLException {

		// Adicionando um visual nimbus a tela ...

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {

			new ErroEncontrado();

		}

		FachadaFornecedores resgatandoNoBanco = new FachadaFornecedores();
		fornecedores = resgatandoNoBanco.listandoFornecedores();

		// Criando os dados que vão ser incrementados a tabela ...

		String[][] dados = new String[fornecedores.size()][];

		int i = 0;

		// Percorrendo ...

		for (Fornecedor forn : fornecedores) {

			dados[i] = new String[] { String.valueOf(forn.getCodigo()),
					forn.getEmpresa(), forn.getCep(), forn.getRua(),
					forn.getBairro(), forn.getCidade(), forn.getEstado(),
					forn.getTelefone(), forn.getFax(), forn.getEmail(),
					forn.getPaginaWeb(), forn.getObservacoes(),
					forn.getDataDeCadastro(), forn.getUrlFoto() };

			i += 1;

		}

		// Adicionando componentes a tela e paineis ...

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		// -----------------------------------------------------------------------------------------------------------------------

		pdfGerado = new JLabel();

		pdfGerado
				.setIcon(new ImageIcon(
						GerenciandoFornecedores.class
								.getResource("/views/gerenciamento/ger_fornecedores/jpg/PDF_Gerado.JPG")));

		pdfGerado.setVisible(false);
		pdfGerado.setBounds(0, 0, 950, 500);

		pdfGerado.addMouseListener(this);
		pdfGerado.addKeyListener(this);

		painelPrincipal.add(pdfGerado);

		// -----------------------------------------------------------------------------------------------------------------------

		fornecedoresCadastrados = new JLabel(
				"Total De Fornecedores Cadastrados: " + fornecedores.size());

		fornecedoresCadastrados.setForeground(corDotexto);
		fornecedoresCadastrados.setBounds(20, 451, 300, 35);

		fornecedoresCadastrados.setFont(new Font("Dialog", Font.PLAIN, 15));

		painelPrincipal.add(fornecedoresCadastrados);

		// -----------------------------------------------------------------------------------------------------------------------

		fornecedorRemovido = new JLabel();

		fornecedorRemovido
				.setIcon(new ImageIcon(
						GerenciandoFornecedores.class
								.getResource("/views/gerenciamento/ger_fornecedores/jpg/Fornecedor_Removido.JPG")));

		fornecedorRemovido.setVisible(false);
		fornecedorRemovido.setBounds(0, 0, 950, 500);

		fornecedorRemovido.addMouseListener(this);
		fornecedorRemovido.addKeyListener(this);

		painelPrincipal.add(fornecedorRemovido);

		campoBusca = new JTextField("Buscar Fornecedor ...");
		campoBusca.setBounds(710, 49, 150, 22);
		campoBusca.setForeground(corDotexto);
		campoBusca.setBorder(null);

		campoBusca.addMouseListener(this);
		campoBusca.addKeyListener(this);

		campoBusca
				.setToolTipText("Digite Aqui o Fornecedor Desejado e Clique Em Ir, Ou Tecle Enter");

		painelPrincipal.add(campoBusca);

		// -----------------------------------------------------------------------------------------------------------------------

		imprimir = new JButton("Imprimir");
		imprimir.setToolTipText("Clique Aqui Para Imprimir Uma Lista Dos Fornecedores Cadastrados");
		imprimir.setBounds(560, 451, 90, 35);

		imprimir.addMouseListener(this);
		imprimir.addKeyListener(this);

		painelPrincipal.add(imprimir);

		// -----------------------------------------------------------------------------------------------------------------------

		gerarPDF = new JButton("Relatório");
		gerarPDF.setToolTipText("Clique Aqui Para Gerar Um PDF De Todos Os Fornecedores Cadastrados");
		gerarPDF.setBounds(466, 451, 90, 35);

		gerarPDF.addMouseListener(this);
		gerarPDF.addKeyListener(this);

		painelPrincipal.add(gerarPDF);

		voltar = new JButton("Voltar");
		voltar.setToolTipText("Clique Aqui Para Voltar a Tela Principal, Ou Tecle (ESC)");
		voltar.setBounds(842, 451, 90, 35);

		voltar.addMouseListener(this);
		voltar.addKeyListener(this);

		painelPrincipal.add(voltar);

		alterar = new JButton("Alterar");
		alterar.setBounds(748, 451, 90, 35);
		alterar.setToolTipText("Clique Aqui Para Alterar o Fornecedor Selecionado, Ou Tecle (CTRL + A)");

		alterar.addMouseListener(this);
		alterar.addKeyListener(this);

		painelPrincipal.add(alterar);

		// Ainda adicionando ...

		remover = new JButton("Remover");

		remover.setToolTipText("Clique Aqui Para Remover o Fornecedor Selecionado, Ou Tecle (CTRL + R)");
		remover.setBounds(654, 451, 90, 35);

		remover.addMouseListener(this);
		remover.addKeyListener(this);

		painelPrincipal.add(remover);

		botaoIr = new JLabel(

				new ImageIcon(
						GerenciandoFornecedores.class
								.getResource("/views/gerenciamento/ger_fornecedores/png/Ir.png")));

		botaoIr.setToolTipText("Clique Aqui Para Pesquisar o Fornecedor Digitado");
		botaoIr.setBounds(866, 45, 62, 32);

		painelTabela = new JPanel();
		painelTabela.setLayout(new BorderLayout());
		painelTabela.setBounds(20, 140, 910, 300);

		painelPrincipal.add(painelTabela);

		// Ainda adicionando ...

		DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

		tabelaDeResultados = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		tabelaDeResultados
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabelaDeResultados.getTableHeader().setReorderingAllowed(false);

		tabelaDeResultados.addMouseListener(this);
		tabelaDeResultados.addKeyListener(this);

		scrollTabela = new JScrollPane(tabelaDeResultados);
		painelTabela.add(scrollTabela);

		tabelaDeResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabelaDeResultados.getColumnModel().getColumn(0).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(2).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(3).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(4).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(5).setPreferredWidth(190);

		tabelaDeResultados.getColumnModel().getColumn(6).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(7).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(8).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(9).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(10)
				.setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(11)
				.setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(12)
				.setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(13)
				.setPreferredWidth(190);

		// Ainda adicionando ...

		botaoIr.addMouseListener(this);
		botaoIr.addKeyListener(this);

		painelPrincipal.add(botaoIr);

		fundo = new JLabel(

				new ImageIcon(
						GerenciandoFornecedores.class
								.getResource("/views/gerenciamento/ger_fornecedores/jpg/Fundo.JPG")));

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
		this.setSize(956, 528);
		this.setResizable(false);
		this.setTitle("Thallyta Móveis - Gerenciando Fornecedor (es)");

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

		if (mouseClick.getSource() == voltar) {

			dispose();

		}

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

		if (fornecedorRemovido.isVisible() == true
				&& mouseClick.getSource() == fornecedorRemovido) {

			try {

				fornecedorRemovido.setVisible(false);

				FachadaFornecedores resgatandoNoBanco = new FachadaFornecedores();
				fornecedores = resgatandoNoBanco.listandoFornecedores();

				// Criando os dados que vão ser incrementados a tabela ...

				String[][] dados = new String[fornecedores.size()][];

				int i = 0;

				// Percorrendo ...

				for (Fornecedor forn : fornecedores) {

					dados[i] = new String[] { String.valueOf(forn.getCodigo()),
							forn.getEmpresa(), forn.getCep(), forn.getRua(),
							forn.getBairro(), forn.getCidade(),
							forn.getEstado(), forn.getTelefone(),
							forn.getFax(), forn.getEmail(),
							forn.getPaginaWeb(), forn.getObservacoes(),
							forn.getDataDeCadastro() };

					i += 1;

				}

				DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

				tabelaDeResultados.setModel(modelo);

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		int coluna = tabelaDeResultados.getSelectedColumn();
		int linha = tabelaDeResultados.getSelectedRow();

		if (mouseClick.getSource() == remover
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			FachadaFornecedores passandoCodigo = new FachadaFornecedores();

			int row = tabelaDeResultados.getSelectedRow();

			try {

				passandoCodigo.removendoFornecedor((String) tabelaDeResultados
						.getValueAt(row, 0));

				fornecedorRemovido.setVisible(true);

			}

			catch (SQLException e) {

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
				new GerarPDF(fornecedores, caminho);

				pdfGerado.setVisible(true);

			}

			else {

			}

		}

		if (pdfGerado.isVisible() == true & mouseClick.getSource() == pdfGerado) {

			try {

				pdfGerado.setVisible(false);

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		if (mouseClick.getSource() == alterar
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			this.dispose();

			TelaPrincipal.AbrindoAlterarFornecedores(
					(String) tabelaDeResultados.getValueAt(row, 0),
					(String) tabelaDeResultados.getValueAt(row, 1),
					(String) tabelaDeResultados.getValueAt(row, 2),
					(String) tabelaDeResultados.getValueAt(row, 3),
					(String) tabelaDeResultados.getValueAt(row, 4),
					(String) tabelaDeResultados.getValueAt(row, 5),
					(String) tabelaDeResultados.getValueAt(row, 6),
					(String) tabelaDeResultados.getValueAt(row, 7),
					(String) tabelaDeResultados.getValueAt(row, 8),
					(String) tabelaDeResultados.getValueAt(row, 9),
					(String) tabelaDeResultados.getValueAt(row, 10),
					(String) tabelaDeResultados.getValueAt(row, 11),
					(String) tabelaDeResultados.getValueAt(row, 12),
					(String) tabelaDeResultados.getValueAt(row, 13));

		}

		if (mouseClick.getClickCount() == 2
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			this.dispose();

			TelaPrincipal.AbrindoAlterarFornecedores(
					(String) tabelaDeResultados.getValueAt(row, 0),
					(String) tabelaDeResultados.getValueAt(row, 1),
					(String) tabelaDeResultados.getValueAt(row, 2),
					(String) tabelaDeResultados.getValueAt(row, 3),
					(String) tabelaDeResultados.getValueAt(row, 4),
					(String) tabelaDeResultados.getValueAt(row, 5),
					(String) tabelaDeResultados.getValueAt(row, 6),
					(String) tabelaDeResultados.getValueAt(row, 7),
					(String) tabelaDeResultados.getValueAt(row, 8),
					(String) tabelaDeResultados.getValueAt(row, 9),
					(String) tabelaDeResultados.getValueAt(row, 10),
					(String) tabelaDeResultados.getValueAt(row, 11),
					(String) tabelaDeResultados.getValueAt(row, 12),
					(String) tabelaDeResultados.getValueAt(row, 13));

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoFornecedores.class
							.getResource("/views/gerenciamento/ger_fornecedores/png/Ir_Mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoFornecedores.class
							.getResource("/views/gerenciamento/ger_fornecedores/png/Ir.png")));

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

		int coluna = tabelaDeResultados.getSelectedColumn();
		int linha = tabelaDeResultados.getSelectedRow();

		if (keyPress.getKeyCode() == KeyEvent.VK_R && keyPress.isControlDown()
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			FachadaFornecedores passandoCodigo = new FachadaFornecedores();

			int row = tabelaDeResultados.getSelectedRow();

			try {

				passandoCodigo.removendoFornecedor((String) tabelaDeResultados
						.getValueAt(row, 0));

				fornecedorRemovido.setVisible(true);

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& pdfGerado.isVisible() == true) {

			try {

				pdfGerado.setVisible(false);

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& fornecedorRemovido.isVisible() == true) {

			try {

				fornecedorRemovido.setVisible(false);

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			this.dispose();

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_A && keyPress.isControlDown()
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			this.dispose();

			TelaPrincipal.AbrindoAlterarFornecedores(
					(String) tabelaDeResultados.getValueAt(row, 0),
					(String) tabelaDeResultados.getValueAt(row, 1),
					(String) tabelaDeResultados.getValueAt(row, 2),
					(String) tabelaDeResultados.getValueAt(row, 3),
					(String) tabelaDeResultados.getValueAt(row, 4),
					(String) tabelaDeResultados.getValueAt(row, 5),
					(String) tabelaDeResultados.getValueAt(row, 6),
					(String) tabelaDeResultados.getValueAt(row, 7),
					(String) tabelaDeResultados.getValueAt(row, 8),
					(String) tabelaDeResultados.getValueAt(row, 9),
					(String) tabelaDeResultados.getValueAt(row, 10),
					(String) tabelaDeResultados.getValueAt(row, 11),
					(String) tabelaDeResultados.getValueAt(row, 12),
					(String) tabelaDeResultados.getValueAt(row, 13));

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

}