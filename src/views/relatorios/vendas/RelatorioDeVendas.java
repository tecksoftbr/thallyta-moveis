package views.relatorios.vendas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import modelo.ProdutoVenda;
import modelo.Vendas;
import views.principais.tela_de_erro.ErroEncontrado;
import fachada.FachadaVendas;

public class RelatorioDeVendas extends JInternalFrame implements MouseListener,
		KeyListener {

	private static final long serialVersionUID = 1L;

	private JPanel painelTabelaHoje;

	private JPanel painelPrincipal, painelHoje, painelOntem, painel3Dias,
			painel7Dias, painelPersonalizado;

	private JTable tabelaHoje, tabelaOntem, tabela3Dias, tabela7Dias,
			tabelaPersonalizado;

	private JTabbedPane abas;

	private String[] colunas = new String[] { "Código Da Venda",
			"Data Da Venda", "Desconto", "Valor Total", "Vezes",
			"Valor De Cada Parcela", "Permissão Para Entrega",
			"Preço De Entrega", "Data De Entraga",
			"Código Do Cliente - Comprador", "Nome - Cliente", "Rua - Cliente",
			"Número - Cliente", "Bairro - Cliente", "Complemento - Cliente",
			"Cidade - Cliente", "Estado - Cliente", "CEP - Cliente",
			"Telefone - 01", "Telefone - 02", "E-mail",
			"Quantidade De Produtos Comprados" };

	JScrollPane scrollTabela;

	ArrayList<Vendas> todasVendas = new ArrayList<>();

	// Tabela hoje ...

	ArrayList<Vendas> vendasHoje;
	String[][] dadosHoje;

	JPanel painelMostraProdutos;
	JTable tabelaProdutosHoje;
	String[][] dadosTabelaMostrarProdutos;
	ArrayList<ProdutoVenda> produtosCompra;

	private String[] modeloAmostraProdutos = new String[] { "Código - Produto",
			"Descrição", "Categoria", "Preço De Venda", "Quantidade", "Marca",
			"Modelo", "Número De Série", "Cor", "Código - Venda" };

	JScrollPane scrollTabelaProdutosNovos;
	DefaultTableModel modeloProdutosMostrar;

	// Tabela Ontem ...

	String[][] dadosOntem = new String[todasVendas.size()][];

	// Tabela 3 Dias Atrás ...

	String[][] dados3DiasAtras = new String[todasVendas.size()][];

	// Tabela 7 Dias Atrás ...

	String[][] dados7DiasAtras = new String[todasVendas.size()][];

	// Tabela Personalizado ...

	String[][] dadosPersonalizado = new String[todasVendas.size()][];
	private JTextField textField;
	private JTextField textField_1;

	private Color corDotexto = new Color(139, 139, 139);
	private JLabel label;

	public RelatorioDeVendas() throws SQLException {

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		// Resgatando todas as vendas do banco ...

		FachadaVendas capturando = new FachadaVendas();
		todasVendas = capturando.vendasCadastradas();

		vendasHoje = new ArrayList<>();

		// Verificando e adiconando na tabela de vendas de hoje ...

		for (int i = 0; i < todasVendas.size(); i++) {

			if (todasVendas.get(i).getDataVenda()
					.equals(pegandoDataDoSistema())) {

				vendasHoje.add(todasVendas.get(i));

			}

		}

		dadosHoje = new String[vendasHoje.size()][];

		int contadorHoje = 0;

		// Percorrendo ...

		for (Vendas venda : vendasHoje) {

			dadosHoje[contadorHoje] = new String[] {
					String.valueOf(venda.getCodigo()), venda.getDataVenda(),
					String.valueOf(venda.getDesconto()),
					String.valueOf(venda.getValorTotal()),
					String.valueOf(venda.getVezes()),
					String.valueOf(venda.getValorParcela()),
					venda.getPermissaoParaEntrega(),
					String.valueOf(venda.getPrecoEntrega()),
					venda.getDataEntrega(),
					String.valueOf(venda.getCodigoCliente()),
					venda.getNomeCliente(), venda.getRuaCliente(),
					venda.getNumeroCliente(), venda.getBairroCliente(),
					venda.getComplementoCliente(), venda.getCidadeCliente(),
					venda.getEstadoCliente(), venda.getCep(),
					venda.getTelefone01(), venda.getTelefone02(),
					venda.getEmail(),
					String.valueOf(venda.getProdutos().size()) };

			contadorHoje += 1;

		}

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

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(933, 566);
		this.setResizable(false);

		this.setContentPane(painelPrincipal);

		this.setClosable(true);

		try {

			this.setSelected(true);

		}

		catch (PropertyVetoException e1) {

			new ErroEncontrado();

		}

		this.setTitle("Thallyta Móveis - Relatório De Vendas");
		this.setVisible(true);

		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.gray);

		painelHoje = new JPanel();
		painelHoje.setBackground(SystemColor.control);

		JButton voltarHoje = new JButton("Voltar");

		voltarHoje.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}

		});

		JButton reletorioHoje = new JButton("Gerar Arquivo");
		reletorioHoje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		painelHoje.setLayout(null);

		reletorioHoje.setBounds(613, 340, 112, 35);
		painelHoje.add(reletorioHoje);

		JButton imprimirHoje = new JButton("Imprimir");

		imprimirHoje.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				try {

					tabelaHoje.print();

				}

				catch (PrinterException e) {

				}

			}

		});

		imprimirHoje.setBounds(730, 340, 88, 35);
		painelHoje.add(imprimirHoje);

		voltarHoje.setBounds(823, 340, 88, 35);
		painelHoje.add(voltarHoje);

		painelHoje.addMouseListener(this);
		painelHoje.addKeyListener(this);

		painelOntem = new JPanel();
		painelOntem.setBackground(SystemColor.control);

		painelOntem.addMouseListener(this);
		painelOntem.addKeyListener(this);

		painel3Dias = new JPanel();
		painel3Dias.setBackground(SystemColor.control);

		painel3Dias.addMouseListener(this);
		painel3Dias.addKeyListener(this);

		painel7Dias = new JPanel();
		painel7Dias.setBackground(SystemColor.control);

		painel7Dias.addMouseListener(this);
		painel7Dias.addKeyListener(this);

		painelPersonalizado = new JPanel();
		painelPersonalizado.setBackground(SystemColor.control);

		painelPersonalizado.addMouseListener(this);
		painelPersonalizado.addKeyListener(this);

		abas = new JTabbedPane();
		abas.setBounds(0, 120, 1000, 500);
		abas.setBackground(Color.black);

		abas.addTab("Hoje", painelHoje);
		painelHoje.setLayout(null);

		painelTabelaHoje = new JPanel();
		painelTabelaHoje.setBounds(10, 11, 901, 320);
		painelTabelaHoje.setLayout(new BorderLayout());
		painelHoje.add(painelTabelaHoje);

		DefaultTableModel modelo = new DefaultTableModel(dadosHoje, colunas);

		tabelaHoje = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		painelMostraProdutos = new JPanel();
		painelMostraProdutos.setBounds(10, 180, 901, 140);
		painelMostraProdutos.setLayout(new BorderLayout());
		painelMostraProdutos.setVisible(false);

		painelHoje.add(painelMostraProdutos);

		for (int i = 0; i < 22; i++) {

			if (i == 9) {

				tabelaHoje.getColumnModel().getColumn(i).setPreferredWidth(250);
				continue;

			}

			if (i == 21) {

				tabelaHoje.getColumnModel().getColumn(i).setPreferredWidth(250);
				continue;

			}

			tabelaHoje.getColumnModel().getColumn(i).setPreferredWidth(190);

		}

		tabelaHoje.getTableHeader().setReorderingAllowed(false);

		tabelaHoje.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabelaHoje.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabelaHoje.addMouseListener(this);
		tabelaHoje.addKeyListener(this);

		scrollTabela = new JScrollPane(tabelaHoje);
		painelTabelaHoje.add(scrollTabela);

		// ----------------------------------------------------------------------------------------------------------------------

		abas.addTab("Ontem", painelOntem);
		painelOntem.setLayout(null);

		JPanel painelTabelaOntem = new JPanel();
		painelTabelaOntem.setBounds(10, 11, 901, 320);
		painelTabelaOntem.setLayout(new BorderLayout());
		painelOntem.add(painelTabelaOntem);

		DefaultTableModel modeloTabelaOntem = new DefaultTableModel(dadosOntem,
				colunas);

		tabelaOntem = new JTable(modeloTabelaOntem) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		for (int i = 0; i < 22; i++) {

			if (i == 9) {

				tabelaOntem.getColumnModel().getColumn(i)
						.setPreferredWidth(250);
				continue;

			}

			if (i == 21) {

				tabelaOntem.getColumnModel().getColumn(i)
						.setPreferredWidth(250);
				continue;

			}

			tabelaOntem.getColumnModel().getColumn(i).setPreferredWidth(190);

		}

		tabelaOntem.getTableHeader().setReorderingAllowed(false);

		tabelaOntem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabelaOntem.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabelaOntem.addMouseListener(this);
		tabelaOntem.addKeyListener(this);

		JScrollPane scrollTabelaOntem;

		scrollTabelaOntem = new JScrollPane(tabelaOntem);
		painelTabelaOntem.add(scrollTabelaOntem);

		JButton relatorioOntem = new JButton("Gerar Arquivo");
		relatorioOntem.setBounds(613, 340, 112, 35);
		painelOntem.add(relatorioOntem);

		JButton imprimirOntem = new JButton("Imprimir");
		imprimirOntem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					tabelaHoje.print();

				}

				catch (PrinterException e) {

				}

			}
		});
		imprimirOntem.setBounds(730, 340, 88, 35);
		painelOntem.add(imprimirOntem);

		JButton voltarOntem = new JButton("Voltar");

		voltarOntem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}

		});

		voltarOntem.setBounds(823, 340, 88, 35);
		painelOntem.add(voltarOntem);

		// ----------------------------------------------------------------------------------------------------------------------

		abas.addTab("3 Dias Atrás", painel3Dias);
		painel3Dias.setLayout(null);

		JPanel painelTabela3DiasAtras = new JPanel();
		painelTabela3DiasAtras.setBounds(10, 11, 901, 320);
		painelTabela3DiasAtras.setLayout(new BorderLayout());
		painel3Dias.add(painelTabela3DiasAtras);

		DefaultTableModel modeloTabela3DiasAtras = new DefaultTableModel(
				dados3DiasAtras, colunas);

		tabela3Dias = new JTable(modeloTabela3DiasAtras) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		for (int i = 0; i < 22; i++) {

			if (i == 9) {

				tabela3Dias.getColumnModel().getColumn(i)
						.setPreferredWidth(250);
				continue;

			}

			if (i == 21) {

				tabela3Dias.getColumnModel().getColumn(i)
						.setPreferredWidth(250);
				continue;

			}

			tabela3Dias.getColumnModel().getColumn(i).setPreferredWidth(190);

		}

		tabela3Dias.getTableHeader().setReorderingAllowed(false);

		tabela3Dias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabela3Dias.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela3Dias.addMouseListener(this);
		tabela3Dias.addKeyListener(this);

		JScrollPane scrollTabela3Dias;

		scrollTabela3Dias = new JScrollPane(tabela3Dias);
		painelTabela3DiasAtras.add(scrollTabela3Dias);

		JButton relatorio3Dias = new JButton("Gerar Arquivo");
		relatorio3Dias.setBounds(613, 340, 112, 35);
		painel3Dias.add(relatorio3Dias);

		JButton imprimir3Dias = new JButton("Imprimir");
		imprimir3Dias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					tabelaHoje.print();

				}

				catch (PrinterException e) {

				}

			}
		});
		imprimir3Dias.setBounds(730, 340, 88, 35);
		painel3Dias.add(imprimir3Dias);

		JButton voltar3Dias = new JButton("Voltar");

		voltar3Dias.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}

		});
		voltar3Dias.setBounds(823, 340, 88, 35);
		painel3Dias.add(voltar3Dias);

		// ----------------------------------------------------------------------------------------------------------------------

		abas.addTab("7 Dias Atrás", painel7Dias);
		painel7Dias.setLayout(null);

		JPanel painelTabela7DiasAtras = new JPanel();
		painelTabela7DiasAtras.setBounds(10, 11, 901, 320);
		painelTabela7DiasAtras.setLayout(new BorderLayout());
		painel7Dias.add(painelTabela7DiasAtras);

		DefaultTableModel modeloTabela7DiasAtras = new DefaultTableModel(
				dados7DiasAtras, colunas);

		tabela7Dias = new JTable(modeloTabela7DiasAtras) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		for (int i = 0; i < 22; i++) {

			if (i == 9) {

				tabela7Dias.getColumnModel().getColumn(i)
						.setPreferredWidth(250);
				continue;

			}

			if (i == 21) {

				tabela7Dias.getColumnModel().getColumn(i)
						.setPreferredWidth(250);
				continue;

			}

			tabela7Dias.getColumnModel().getColumn(i).setPreferredWidth(190);

		}

		tabela7Dias.getTableHeader().setReorderingAllowed(false);

		tabela7Dias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabela7Dias.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabela7Dias.addMouseListener(this);
		tabela7Dias.addKeyListener(this);

		JScrollPane scrollTabela7Dias;

		scrollTabela7Dias = new JScrollPane(tabela7Dias);
		painelTabela7DiasAtras.add(scrollTabela7Dias);

		JButton relatorio7Dias = new JButton("Gerar Arquivo");
		relatorio7Dias.setBounds(613, 340, 112, 35);
		painel7Dias.add(relatorio7Dias);

		JButton imprimir7Dias = new JButton("Imprimir");
		imprimir7Dias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					tabelaHoje.print();

				}

				catch (PrinterException e) {

				}

			}
		});
		imprimir7Dias.setBounds(730, 340, 88, 35);
		painel7Dias.add(imprimir7Dias);

		JButton voltar7Dias = new JButton("Voltar");

		voltar7Dias.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}

		});

		voltar7Dias.setBounds(823, 340, 88, 35);
		painel7Dias.add(voltar7Dias);

		// ----------------------------------------------------------------------------------------------------------------------

		abas.addTab("Personalizado", painelPersonalizado);
		painelPersonalizado.setLayout(null);

		JPanel painelTabelaPersonalizado = new JPanel();
		painelTabelaPersonalizado.setBounds(10, 50, 901, 280);
		painelTabelaPersonalizado.setLayout(new BorderLayout());
		painelPersonalizado.add(painelTabelaPersonalizado);

		DefaultTableModel modeloTabelaPersonalizado = new DefaultTableModel(
				dadosPersonalizado, colunas);

		tabelaPersonalizado = new JTable(modeloTabelaPersonalizado) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		for (int i = 0; i < 22; i++) {

			if (i == 9) {

				tabelaPersonalizado.getColumnModel().getColumn(i)
						.setPreferredWidth(250);
				continue;

			}

			if (i == 21) {

				tabelaPersonalizado.getColumnModel().getColumn(i)
						.setPreferredWidth(250);
				continue;

			}

			tabelaPersonalizado.getColumnModel().getColumn(i)
					.setPreferredWidth(190);

		}

		tabelaPersonalizado.getTableHeader().setReorderingAllowed(false);

		tabelaPersonalizado
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabelaPersonalizado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabelaPersonalizado.addMouseListener(this);
		tabelaPersonalizado.addKeyListener(this);

		JScrollPane scrollTabelaPersonalizado;

		scrollTabelaPersonalizado = new JScrollPane(tabelaPersonalizado);
		painelTabelaPersonalizado.add(scrollTabelaPersonalizado);

		textField = new JFormattedTextField((setMascara("##/##/####")));

		textField.setBounds(200, 10, 112, 33);
		textField.setForeground(corDotexto);
		textField.setFont(new Font("Dialog", Font.PLAIN, 15));
		painelPersonalizado.add(textField);
		textField.setColumns(10);

		textField_1 = new JFormattedTextField((setMascara("##/##/####")));

		textField_1.setColumns(10);
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 15));
		textField_1.setBounds(357, 10, 112, 33);
		textField_1.setForeground(corDotexto);
		painelPersonalizado.add(textField_1);

		JLabel lblAt = new JLabel("Pesquisar Venda Por Data");
		lblAt.setBounds(10, 18, 178, 16);
		lblAt.setFont(new Font("Dialog", Font.PLAIN, 15));
		lblAt.setForeground(corDotexto);
		painelPersonalizado.add(lblAt);

		label = new JLabel("At\u00E9");
		label.setForeground(new Color(139, 139, 139));
		label.setFont(new Font("Dialog", Font.PLAIN, 15));
		label.setBounds(324, 18, 55, 16);
		painelPersonalizado.add(label);

		JButton botaoIr = new JButton("Ir");
		botaoIr.setBounds(480, 9, 55, 34);
		painelPersonalizado.add(botaoIr);

		JButton voltarPersonalizado = new JButton("Voltar");

		voltarPersonalizado.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				dispose();

			}

		});

		voltarPersonalizado.setBounds(823, 340, 88, 35);
		painelPersonalizado.add(voltarPersonalizado);

		JButton imprimirPersonalizado = new JButton("Imprimir");
		imprimirPersonalizado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					tabelaHoje.print();

				}

				catch (PrinterException e) {

				}

			}
		});
		imprimirPersonalizado.setBounds(730, 340, 88, 35);
		painelPersonalizado.add(imprimirPersonalizado);

		JButton reletorioPersonalizado = new JButton("Gerar Arquivo");
		reletorioPersonalizado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		reletorioPersonalizado.setBounds(613, 340, 112, 35);
		painelPersonalizado.add(reletorioPersonalizado);

		// ----------------------------------------------------------------------------------------------------------------------

		getContentPane().add(abas);

		JLabel Fundo = new JLabel(new ImageIcon(
				RelatorioDeVendas.class
						.getResource("/views/relatorios/vendas/jpg/Fundo.JPG")));

		Fundo.setBounds(0, 0, 927, 538);
		painelPrincipal.add(Fundo);

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		// Capturando linha e coluna selecionadas ...

		int coluna = tabelaHoje.getSelectedColumn();
		int linha = tabelaHoje.getSelectedRow();

		// Se o usuário clicar na tabela ...

		if (mouseClick.getSource() == tabelaHoje
				& tabelaHoje.isCellSelected(linha, coluna)) {

			// Abaixa o tamanho da tabela

			painelTabelaHoje.setBounds(10, 11, 901, 150);
			abas.repaint();

			// Mostra o painel de baixo ...

			painelMostraProdutos.setVisible(true);

			// Captura a linha selecionada ...

			int row = tabelaHoje.getSelectedRow();

			// Captura o código da linha ...

			int codigoCompra = Integer.parseInt((String) tabelaHoje.getValueAt(
					row, 0));

			// Criando array para adicionar os produtos da compra selecionada

			produtosCompra = new ArrayList<>();

			// Capturando os dados da compra e jogando no novo array ...

			for (int i = 0; i < vendasHoje.size(); i++) {

				if (vendasHoje.get(i).getCodigo() == codigoCompra) {

					produtosCompra = vendasHoje.get(i).getProdutos();

				}

			}

			// Criando os dados da tabela de produtos ...

			dadosTabelaMostrarProdutos = new String[produtosCompra.size()][];

			int contadorHoje = 0;

			// Percorrendo ...

			for (ProdutoVenda produtosVenda : produtosCompra) {

				dadosTabelaMostrarProdutos[contadorHoje] = new String[] {
						String.valueOf(produtosVenda.getCodigo()),
						produtosVenda.getDescricao(),
						produtosVenda.getCategoria(),
						String.valueOf(produtosVenda.getPrecoDeVenda()),
						String.valueOf(produtosVenda.getQuantidade()),
						produtosVenda.getMarca(), produtosVenda.getModelo(),
						produtosVenda.getNumeroSerie(), produtosVenda.getCor(),
						String.valueOf(produtosVenda.getCodigoVenda()) };

				contadorHoje += 1;

			}

			// Criando modelo para a nova tabela ...

			modeloProdutosMostrar = new DefaultTableModel(
					dadosTabelaMostrarProdutos, modeloAmostraProdutos);

			// Criando tabela ...

			tabelaProdutosHoje = new JTable(modeloProdutosMostrar) {

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {

					return false;

				}

			};

			tabelaProdutosHoje.getTableHeader().setReorderingAllowed(false);

			tabelaProdutosHoje
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			tabelaProdutosHoje.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			tabelaProdutosHoje.addMouseListener(this);
			tabelaProdutosHoje.addKeyListener(this);

			scrollTabelaProdutosNovos = new JScrollPane(tabelaProdutosHoje);
			painelMostraProdutos.add(scrollTabelaProdutosNovos);

			for (int i = 0; i < 10; i++) {

				tabelaProdutosHoje.getColumnModel().getColumn(i)
						.setPreferredWidth(190);

			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

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
}