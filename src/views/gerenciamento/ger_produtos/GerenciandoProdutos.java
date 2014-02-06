package views.gerenciamento.ger_produtos;

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

import javax.swing.JTable.PrintMode;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;

import modelo.Produto;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;
import fachada.FachadaProdutos;

public class GerenciandoProdutos extends JInternalFrame implements
		MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JPanel painelPrincipal, painelTabela;
	private JTextField campoBusca;

	private JLabel fundo, botaoIr, produtoRemovido, totalDeProdutosCadastrados,
			pdfGerado, clearCampoBusca;

	private JButton voltar, remover, alterar, gerarPDF, imprimir;
	private JTable tabelaDeResultados;
	private JScrollPane scrollTabela;

	// Rodapé das colunas que vão ser incrementadas na tabela ...

	private String[] colunas = new String[] { "Código", "Descrição",
			"Categoria", "Data De Cadasto", "Preço De Compra",
			"Preço De Venda", "Quantidade", "Marca", "Observações", "Modelo",
			"Número De Série", "Cor", "Url Da Foto" };

	// Cor (es) usadas ...

	private Color corDotexto = new Color(139, 139, 139);

	private ArrayList<Produto> produtos;

	public GerenciandoProdutos() throws SQLException {

		// Adicionando um visual nimbus a tela ...

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {

			new ErroEncontrado();

		}

		produtos = new ArrayList<>();

		FachadaProdutos resgatandoProdutos = new FachadaProdutos();
		produtos = resgatandoProdutos.listandoProdutos();

		String[][] dados = new String[produtos.size()][];
		int i = 0;

		// Percorrendo ...

		for (Produto pro : produtos) {

			dados[i] = new String[] { String.valueOf(pro.getCodigo()),
					pro.getDescricao(), pro.getCategoria(),
					pro.getDataDeCadastro(),
					String.valueOf(pro.getPrecoDeCompra()),
					String.valueOf(pro.getPrecoDeVenda()),
					String.valueOf(pro.getQuantidade()), pro.getMarca(),
					pro.getObservacoes(), pro.getModelo(),
					pro.getNumeroSerie(), pro.getCor(), pro.getUrlFoto() };

			i += 1;

		}

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		produtoRemovido = new JLabel();
		produtoRemovido
				.setIcon(new ImageIcon(
						GerenciandoProdutos.class
								.getResource("/views/gerenciamento/ger_produtos/jpg/Produto_Removido.jpg")));

		produtoRemovido.setVisible(false);
		produtoRemovido.setBounds(0, 0, 950, 500);

		produtoRemovido.addMouseListener(this);
		produtoRemovido.addKeyListener(this);

		painelPrincipal.add(produtoRemovido);

		// -----------------------------------------------------------------------------------------------------------------------

		pdfGerado = new JLabel();

		pdfGerado
				.setIcon(new ImageIcon(
						GerenciandoProdutos.class
								.getResource("/views/gerenciamento/ger_produtos/jpg/PDF_Gerado.jpg")));

		pdfGerado.setVisible(false);
		pdfGerado.setBounds(0, 0, 950, 500);

		pdfGerado.addMouseListener(this);
		pdfGerado.addKeyListener(this);

		painelPrincipal.add(pdfGerado);

		// -----------------------------------------------------------------------------------------------------------------------

		clearCampoBusca = new JLabel(
				new ImageIcon(
						GerenciandoProdutos.class
								.getResource("/views/gerenciamento/ger_produtos/png/Clear.png")));

		clearCampoBusca.setVisible(false);
		clearCampoBusca.setBounds(830, 49, 28, 22);

		clearCampoBusca.addMouseListener(this);
		clearCampoBusca.addKeyListener(this);

		painelPrincipal.add(clearCampoBusca);

		botaoIr = new JLabel(

				new ImageIcon(
						GerenciandoProdutos.class
								.getResource("/views/gerenciamento/ger_produtos/png/Ir.png")));

		botaoIr.setToolTipText("Clique Aqui Para Pesquisar o Produto Digitado");
		botaoIr.setBounds(866, 45, 62, 32);

		botaoIr.addMouseListener(this);
		botaoIr.addKeyListener(this);

		// -----------------------------------------------------------------------------------------------------------------------

		campoBusca = new JTextField("Buscar Produto ...");
		campoBusca.setBounds(710, 49, 150, 22);
		campoBusca.setForeground(corDotexto);
		campoBusca.setBorder(null);

		campoBusca
				.setToolTipText("Digite Aqui o Produto Desejado e Clique Em Ir, Ou Tecle Enter");

		campoBusca.addMouseListener(this);
		campoBusca.addKeyListener(this);

		painelPrincipal.add(campoBusca);

		// -----------------------------------------------------------------------------------------------------------------------

		voltar = new JButton("Voltar");
		voltar.setToolTipText("Clique Aqui Para Voltar a Tela Principal, Ou Tecle (ESC)");
		voltar.setBounds(842, 451, 90, 35);

		voltar.addMouseListener(this);
		voltar.addKeyListener(this);

		painelPrincipal.add(voltar);

		// -----------------------------------------------------------------------------------------------------------------------

		alterar = new JButton("Alterar");
		alterar.setBounds(748, 451, 90, 35);
		alterar.setToolTipText("Clique Aqui Para Alterar o Produto Selecionado, Ou Tecle (CTRL + A)");

		alterar.addMouseListener(this);
		alterar.addKeyListener(this);

		painelPrincipal.add(alterar);

		// -----------------------------------------------------------------------------------------------------------------------

		totalDeProdutosCadastrados = new JLabel(
				"Total De Produtos Cadastrados: " + produtos.size());

		totalDeProdutosCadastrados.setForeground(corDotexto);

		totalDeProdutosCadastrados.setBounds(20, 451, 300, 35);
		totalDeProdutosCadastrados.setFont(new Font("Dialog", Font.PLAIN, 15));

		painelPrincipal.add(totalDeProdutosCadastrados);

		// -----------------------------------------------------------------------------------------------------------------------

		imprimir = new JButton("Imprimir");
		imprimir.setToolTipText("Clique Aqui Para Imprimir Uma Lista Dos Produtos Cadastrados, Ou Tecle (CTRL + I)");
		imprimir.setBounds(560, 451, 90, 35);

		imprimir.addMouseListener(this);
		imprimir.addKeyListener(this);

		painelPrincipal.add(imprimir);

		// -----------------------------------------------------------------------------------------------------------------------

		gerarPDF = new JButton("Relatório");
		gerarPDF.setToolTipText("Clique Aqui Para Gerar Um PDF De Todos Os Produtos Cadastrados, Ou Tecle (CTRL + G)");
		gerarPDF.setBounds(466, 451, 90, 35);

		gerarPDF.addMouseListener(this);
		gerarPDF.addKeyListener(this);

		painelPrincipal.add(gerarPDF);

		// -----------------------------------------------------------------------------------------------------------------------

		remover = new JButton("Remover");

		remover.setToolTipText("Clique Aqui Para Remover o Produto Selecionado, Ou Tecle (CTRL + R)");
		remover.setBounds(654, 451, 90, 35);

		remover.addMouseListener(this);
		remover.addKeyListener(this);

		painelPrincipal.add(remover);

		painelTabela = new JPanel();
		painelTabela.setLayout(new BorderLayout());
		painelTabela.setBounds(20, 140, 910, 300);

		painelPrincipal.add(painelTabela);

		DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

		tabelaDeResultados = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		tabelaDeResultados.setBackground(Color.white);

		tabelaDeResultados.getTableHeader().setReorderingAllowed(false);

		tabelaDeResultados.addMouseListener(this);
		tabelaDeResultados.addKeyListener(this);

		tabelaDeResultados
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

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
		tabelaDeResultados.getColumnModel().getColumn(8).setPreferredWidth(250);
		tabelaDeResultados.getColumnModel().getColumn(9).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(10)
				.setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(11)
				.setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(12)
				.setPreferredWidth(190);

		botaoIr.addMouseListener(this);
		painelPrincipal.add(botaoIr);

		fundo = new JLabel(

				new ImageIcon(
						GerenciandoProdutos.class
								.getResource("/views/gerenciamento/ger_produtos/jpg/Fundo.jpg")));

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
		this.setTitle("Thallyta Móveis - Gerenciando Produto (s)");

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

		if (mouseClick.getSource() == voltar) {

			dispose();

		}

		if (mouseClick.getSource() == campoBusca
				& campoBusca.getText().equals("Buscar Produto ...")) {

			campoBusca.setText("");

		}

		if (mouseClick.getSource() == botaoIr) {

			FachadaProdutos buscando = new FachadaProdutos();
			ArrayList<Produto> produtosAchados = null;

			try {

				produtosAchados = new ArrayList<>();
				produtosAchados = buscando.buscandoProdutos(campoBusca
						.getText());

			}

			catch (SQLException e1) {

				new ErroEncontrado();

			}

			if (produtosAchados.isEmpty() == true) {

				campoBusca.setText("Nenhum Encontrado ...");
				campoBusca.setForeground(Color.red);

			}

			else {

				String[][] dados = new String[produtosAchados.size()][];
				int i = 0;

				// Percorrendo ...

				for (Produto pro : produtosAchados) {

					dados[i] = new String[] { String.valueOf(pro.getCodigo()),
							pro.getDescricao(), pro.getCategoria(),
							pro.getDataDeCadastro(),
							String.valueOf(pro.getPrecoDeCompra()),
							String.valueOf(pro.getPrecoDeVenda()),
							String.valueOf(pro.getQuantidade()),
							pro.getMarca(), pro.getObservacoes(),
							pro.getModelo(), pro.getNumeroSerie(),
							pro.getCor(), pro.getUrlFoto() };

					i += 1;

				}

				DefaultTableModel modelo2 = new DefaultTableModel(dados,
						colunas);

				tabelaDeResultados.setModel(modelo2);

				tabelaDeResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				for (int j = 0; j < 13; j++) {

					tabelaDeResultados.getColumnModel().getColumn(j)
							.setPreferredWidth(190);

				}

			}

		}

		if (mouseClick.getSource() == clearCampoBusca) {

			clearCampoBusca.setVisible(false);

			produtos = new ArrayList<>();

			FachadaProdutos resgatandoProdutos = new FachadaProdutos();

			try {

				produtos = resgatandoProdutos.listandoProdutos();

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			String[][] dados = new String[produtos.size()][];
			int i = 0;

			// Percorrendo ...

			for (Produto pro : produtos) {

				dados[i] = new String[] { String.valueOf(pro.getCodigo()),
						pro.getDescricao(), pro.getCategoria(),
						pro.getDataDeCadastro(),
						String.valueOf(pro.getPrecoDeCompra()),
						String.valueOf(pro.getPrecoDeVenda()),
						String.valueOf(pro.getQuantidade()), pro.getMarca(),
						pro.getObservacoes(), pro.getModelo(),
						pro.getNumeroSerie(), pro.getCor(), pro.getUrlFoto() };

				i += 1;

			}

			DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

			tabelaDeResultados.setModel(modelo);

			tabelaDeResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			for (int j = 0; j < 13; j++) {

				tabelaDeResultados.getColumnModel().getColumn(j)
						.setPreferredWidth(190);

			}

			campoBusca.setText("Buscar Produto ...");

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

		if (mouseClick.getSource() == alterar
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			this.dispose();

			TelaPrincipal.AbrindoAlterarProdutos(
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
					(String) tabelaDeResultados.getValueAt(row, 12));

		}

		if (mouseClick.getSource() == remover
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			FachadaProdutos removendo = new FachadaProdutos();

			try {

				removendo.removendoProduto((String) tabelaDeResultados
						.getValueAt(row, 0));

				produtoRemovido.setVisible(true);

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		if (mouseClick.getSource() == produtoRemovido
				& produtoRemovido.isVisible() == true) {

			produtoRemovido.setVisible(false);

			FachadaProdutos resgatandoProdutos = new FachadaProdutos();

			try {

				produtos = new ArrayList<>();
				produtos = resgatandoProdutos.listandoProdutos();

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			String[][] dados = new String[produtos.size()][];
			int i = 0;

			// Percorrendo ...

			for (Produto pro : produtos) {

				dados[i] = new String[] { String.valueOf(pro.getCodigo()),
						pro.getDescricao(), pro.getCategoria(),
						pro.getDataDeCadastro(),
						String.valueOf(pro.getPrecoDeCompra()),
						String.valueOf(pro.getPrecoDeVenda()),
						String.valueOf(pro.getQuantidade()), pro.getMarca(),
						pro.getObservacoes(), pro.getModelo(),
						pro.getNumeroSerie(), pro.getCor(), pro.getUrlFoto() };

				i += 1;

			}

			DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

			tabelaDeResultados.setModel(modelo);

			for (int j = 0; j < 13; j++) {

				tabelaDeResultados.getColumnModel().getColumn(j)
						.setPreferredWidth(190);

			}

			campoBusca.setText("Buscar Produto ...");

		}

		if (mouseClick.getClickCount() == 2
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			this.dispose();

			TelaPrincipal.AbrindoAlterarProdutos(
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
					(String) tabelaDeResultados.getValueAt(row, 12));

		}

		if (mouseClick.getSource() == gerarPDF) {

			JFileChooser chooser;
			chooser = new JFileChooser();

			String caminho = "";

			int retorno = chooser.showSaveDialog(null);

			if (retorno == JFileChooser.APPROVE_OPTION) {

				caminho = chooser.getSelectedFile().getAbsolutePath();

				try {

					new GerarPDF(produtos, caminho);

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				pdfGerado.setVisible(true);

			}

			else {

			}

		}

		if (pdfGerado.isVisible() == true
				&& mouseClick.getSource() == pdfGerado) {

			try {

				pdfGerado.setVisible(false);
				for (int j = 0; j < 13; j++) {

					tabelaDeResultados.getColumnModel().getColumn(j)
							.setPreferredWidth(190);

				}

				campoBusca.setText("Buscar Produto ...");

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoProdutos.class
							.getResource("/views/gerenciamento/ger_produtos/png/Ir_Mouse.png")));

		}

		if (mouseEntry.getSource() == clearCampoBusca) {

			clearCampoBusca
					.setIcon(new ImageIcon(
							GerenciandoProdutos.class
									.getResource("/views/gerenciamento/ger_produtos/png/Clear_Mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoProdutos.class
							.getResource("/views/gerenciamento/ger_produtos/png/Ir.png")));

		}

		if (mouseExit.getSource() == clearCampoBusca) {

			clearCampoBusca
					.setIcon(new ImageIcon(
							GerenciandoProdutos.class
									.getResource("/views/gerenciamento/ger_produtos/png/Clear.png")));

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

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			this.dispose();

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				& pdfGerado.isVisible() == true) {

			pdfGerado.setVisible(false);

			for (int j = 0; j < 13; j++) {

				tabelaDeResultados.getColumnModel().getColumn(j)
						.setPreferredWidth(190);

			}

			campoBusca.setText("Buscar Produto ...");

		}

		if (campoBusca.getText().equals("Nenhum Encontrado ...")
				& campoBusca.isFocusOwner()) {

			campoBusca.setForeground(corDotexto);
			campoBusca.setText("");

		}

		char[] letrasDoCampo = campoBusca.getText().toCharArray();

		if (letrasDoCampo.length == 1 & campoBusca.isFocusOwner()
				& keyPress.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

			String[][] dados = new String[produtos.size()][];
			int i = 0;

			// Percorrendo o ArrayList para adicionar dados a tabela ...

			for (Produto pro : produtos) {

				dados[i] = new String[] { String.valueOf(pro.getCodigo()),
						pro.getDescricao(), pro.getCategoria(),
						pro.getDataDeCadastro(),
						String.valueOf(pro.getPrecoDeCompra()),
						String.valueOf(pro.getPrecoDeVenda()),
						String.valueOf(pro.getQuantidade()), pro.getMarca(),
						pro.getObservacoes(), pro.getModelo(),
						pro.getNumeroSerie(), pro.getCor(), pro.getUrlFoto() };

				i += 1;

			}

			DefaultTableModel modelo02 = new DefaultTableModel(dados, colunas);
			tabelaDeResultados.setModel(modelo02);

			tabelaDeResultados.getColumnModel().getColumn(0)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(1)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(2)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(3)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(4)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(5)
					.setPreferredWidth(190);

			tabelaDeResultados.getColumnModel().getColumn(6)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(7)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(8)
					.setPreferredWidth(250);
			tabelaDeResultados.getColumnModel().getColumn(9)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(10)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(12)
					.setPreferredWidth(190);

		}

		if (produtoRemovido.isVisible() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			produtoRemovido.setVisible(false);

			FachadaProdutos resgatandoProdutos = new FachadaProdutos();

			try {

				produtos = resgatandoProdutos.listandoProdutos();

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			String[][] dados = new String[produtos.size()][];
			int i = 0;

			// Percorrendo ...

			for (Produto pro : produtos) {

				dados[i] = new String[] { String.valueOf(pro.getCodigo()),
						pro.getDescricao(), pro.getCategoria(),
						pro.getDataDeCadastro(),
						String.valueOf(pro.getPrecoDeCompra()),
						String.valueOf(pro.getPrecoDeVenda()),
						String.valueOf(pro.getQuantidade()), pro.getMarca(),
						pro.getObservacoes(), pro.getModelo(),
						pro.getNumeroSerie(), pro.getCor(), pro.getUrlFoto() };

				i += 1;

			}

			DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

			tabelaDeResultados.setModel(modelo);

			for (int j = 0; j < 13; j++) {

				tabelaDeResultados.getColumnModel().getColumn(j)
						.setPreferredWidth(190);

			}

			campoBusca.setText("Buscar Produto ...");

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_I && keyPress.isControlDown()) {

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

		if (keyPress.getKeyCode() == KeyEvent.VK_R && keyPress.isControlDown()
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			FachadaProdutos removendo = new FachadaProdutos();

			try {

				removendo.removendoProduto((String) tabelaDeResultados
						.getValueAt(row, 0));

				produtoRemovido.setVisible(true);

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_A && keyPress.isControlDown()
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			this.dispose();

			TelaPrincipal.AbrindoAlterarProdutos(
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
					(String) tabelaDeResultados.getValueAt(row, 12));

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				& campoBusca.isFocusOwner() == true) {

			FachadaProdutos buscando = new FachadaProdutos();
			ArrayList<Produto> produtosAchados = null;

			try {

				produtosAchados = new ArrayList<>();
				produtosAchados = buscando.buscandoProdutos(campoBusca
						.getText());

			}

			catch (SQLException e1) {

				new ErroEncontrado();

			}

			if (produtosAchados.isEmpty() == true) {

				campoBusca.setText("Nenhum Encontrado ...");
				campoBusca.setForeground(Color.red);

			}

			else {

				String[][] dados = new String[produtosAchados.size()][];
				int i = 0;

				// Percorrendo ...

				for (Produto pro : produtosAchados) {

					dados[i] = new String[] { String.valueOf(pro.getCodigo()),
							pro.getDescricao(), pro.getCategoria(),
							pro.getDataDeCadastro(),
							String.valueOf(pro.getPrecoDeCompra()),
							String.valueOf(pro.getPrecoDeVenda()),
							String.valueOf(pro.getQuantidade()),
							pro.getMarca(), pro.getObservacoes(),
							pro.getModelo(), pro.getNumeroSerie(),
							pro.getCor(), pro.getUrlFoto() };

					i += 1;

				}

				DefaultTableModel modelo2 = new DefaultTableModel(dados,
						colunas);

				tabelaDeResultados.setModel(modelo2);

				tabelaDeResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				for (int j = 0; j < 13; j++) {

					tabelaDeResultados.getColumnModel().getColumn(j)
							.setPreferredWidth(190);

				}

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_G && keyPress.isControlDown()) {

			JFileChooser chooser;
			chooser = new JFileChooser();

			String caminho = "";

			int retorno = chooser.showSaveDialog(null);

			if (retorno == JFileChooser.APPROVE_OPTION) {

				caminho = chooser.getSelectedFile().getAbsolutePath();

				try {

					new GerarPDF(produtos, caminho);

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

				pdfGerado.setVisible(true);

			}

			else {

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