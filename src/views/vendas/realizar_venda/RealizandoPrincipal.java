package views.vendas.realizar_venda;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import modelo.Cliente;
import modelo.Produto;
import views.principais.tela_de_erro.ErroEncontrado;
import fachada.FachadaClientes;
import fachada.FachadaProdutos;
import fachada.FachadaVendas;

public class RealizandoPrincipal extends JInternalFrame implements
		MouseListener, KeyListener, ItemListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	private JPanel painelPrincipal, painelProdutosCadastrados,
			painelTabelaSecundaria;

	private JLabel fundo, emEstoque, produtosEscolhidosTexto,
			textoPrecoEntrega, textoDataDeEntrega, textoDesconto,
			textoValorDaParcela, textoValorTotal, vendaEfetuada,
			gerarComprovanteFinal, concluirFinal, fundoBusca;

	private JTable tabelaProdutosCadastrados, tabelaSecundaria;
	private JScrollPane scrollTabela, scrollTabelaSecundaria;

	private JComboBox<String> comboClientes, comboPagamento;
	private JSpinner vezes;

	private JButton mais, cancelar, concluir, menos, removerTudo;
	private ButtonGroup grupoEntrega;

	private JRadioButton radioSim, radioNao;

	private ArrayList<Produto> produtos;

	private JTextField campoQuantidade, campoEntrega, campoDataDeEntrega,
			campoDesconto, campoValorDaParcela, campoValorTotal, invisivel;

	private JTextField campoBusca;

	private String[] colunas = new String[] { "Código", "Descrição",
			"Categoria", "Preço De Venda", "Quantidade", "Marca", "Modelo",
			"Número De Série", "Cor" };

	private ArrayList<Cliente> clientes;
	private ArrayList<Produto> produtosEscolhidos = new ArrayList<>();

	private String[][] dados_secundarios = new String[produtosEscolhidos.size()][];

	private DefaultTableModel modelo_secundario = new DefaultTableModel(
			dados_secundarios, colunas);

	private Produto produto;

	private DefaultTableModel modelo;

	// Cor (es) usadas ...

	private Color corDotexto = new Color(139, 139, 139);
	private Color corDasTabelas = new Color(242, 242, 242);

	// Informações da compra ...

	private Double valorTotal = 0.0;

	// --------------------------------- Informações requeridas
	// ---------------------------------

	int codigoCliente = 0;
	String nomeCliente = "";

	String ruaCliente = "";
	String numeroCliente = "";
	String bairro = "";
	String complemento = "";
	String cidade = "";
	String estado = "";
	String cep = "";

	String telefone01 = "";
	String telefone02 = "";

	String email = "";

	ArrayList<Produto> produtosCliente;

	String formaPagamento = "";
	String vezesPagamento = "";
	String permissaoEntrega = "";
	String precoEntrega = "";
	String dataEntrega = "";

	String descontoCompra = "";
	String valorParcela = "";
	String valorTotalCompra = "";

	String dataVenda = "";

	// ------------------------------------------------------------------------------------------

	public RealizandoPrincipal() throws SQLException, PropertyVetoException {

		// Adicionando aparência NIMBUS ao jframe ...

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		painelPrincipal = new JPanel();

		vendaEfetuada = new JLabel();

		vendaEfetuada
				.setIcon(new ImageIcon(
						RealizandoPrincipal.class
								.getResource("/views/vendas/realizar_venda/jpg/Venda_Realizada.jpg")));

		vendaEfetuada.addMouseListener(this);
		vendaEfetuada.addKeyListener(this);
		vendaEfetuada.setBounds(0, 0, 909, 602);
		vendaEfetuada.setVisible(false);

		concluirFinal = new JLabel("");

		concluirFinal
				.setIcon(new ImageIcon(
						RealizandoPrincipal.class
								.getResource("/views/vendas/realizar_venda/png/concluir_compra.png")));

		concluirFinal.setBounds(652, 306, 72, 22);
		concluirFinal.addMouseListener(this);
		concluirFinal.setVisible(false);
		concluirFinal.addKeyListener(this);

		painelPrincipal.add(concluirFinal);

		gerarComprovanteFinal = new JLabel("");

		gerarComprovanteFinal
				.setIcon(new ImageIcon(
						RealizandoPrincipal.class
								.getResource("/views/vendas/realizar_venda/png/gerar_comprovante.png")));

		gerarComprovanteFinal.setBounds(476, 306, 159, 25);
		gerarComprovanteFinal.setVisible(false);

		gerarComprovanteFinal.addMouseListener(this);
		gerarComprovanteFinal.addKeyListener(this);

		painelPrincipal.add(gerarComprovanteFinal);
		painelPrincipal.add(vendaEfetuada);

		FachadaProdutos resgatandoProdutos = new FachadaProdutos();
		produtos = resgatandoProdutos.listandoProdutos();

		String[][] dados = new String[produtos.size()][];
		int i = 0;

		// Percorrendo o ArrayList para adicionar dados a tabela ...

		for (Produto pro : produtos) {

			dados[i] = new String[] { String.valueOf(pro.getCodigo()),
					pro.getDescricao(), pro.getCategoria(),
					String.valueOf(pro.getPrecoDeVenda()),
					String.valueOf(pro.getQuantidade()), pro.getMarca(),
					pro.getModelo(), pro.getNumeroSerie(), pro.getCor() };

			i += 1;

		}

		painelProdutosCadastrados = new JPanel();
		painelProdutosCadastrados.setLayout(new BorderLayout());
		painelProdutosCadastrados.setBounds(15, 160, 880, 130);

		painelProdutosCadastrados.addMouseListener(this);
		painelProdutosCadastrados.addKeyListener(this);
		painelPrincipal.add(painelProdutosCadastrados);

		// Definindo um modelo padrão a esta janela ...

		modelo = new DefaultTableModel(dados, colunas);

		// Inserindo a tabela principal na janela e setando valores false para
		// que os campos não sejam editados ...

		tabelaProdutosCadastrados = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		tabelaProdutosCadastrados.setBackground(corDasTabelas);

		tabelaProdutosCadastrados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabelaProdutosCadastrados.getColumnModel().getColumn(0)
				.setPreferredWidth(190);
		tabelaProdutosCadastrados.getColumnModel().getColumn(1)
				.setPreferredWidth(190);
		tabelaProdutosCadastrados.getColumnModel().getColumn(2)
				.setPreferredWidth(190);
		tabelaProdutosCadastrados.getColumnModel().getColumn(3)
				.setPreferredWidth(190);
		tabelaProdutosCadastrados.getColumnModel().getColumn(4)
				.setPreferredWidth(190);
		tabelaProdutosCadastrados.getColumnModel().getColumn(5)
				.setPreferredWidth(190);

		tabelaProdutosCadastrados.getColumnModel().getColumn(6)
				.setPreferredWidth(190);
		tabelaProdutosCadastrados.getColumnModel().getColumn(7)
				.setPreferredWidth(190);
		tabelaProdutosCadastrados.getColumnModel().getColumn(8)
				.setPreferredWidth(190);

		tabelaProdutosCadastrados
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabelaProdutosCadastrados.addMouseListener(this);
		tabelaProdutosCadastrados.getTableHeader().setReorderingAllowed(false);

		// Inserindo scroll a tabela principal de produtos ...

		scrollTabela = new JScrollPane(tabelaProdutosCadastrados);
		painelProdutosCadastrados.add(scrollTabela);

		// Criando painel da tabela secundária

		painelTabelaSecundaria = new JPanel();
		painelTabelaSecundaria.setLayout(new BorderLayout());
		painelTabelaSecundaria.setBounds(15, 342, 880, 130);

		painelPrincipal.add(painelTabelaSecundaria);

		String[][] dadosSecundarios = new String[produtosEscolhidos.size()][];

		modelo_secundario = new DefaultTableModel(dadosSecundarios, colunas);

		// Criando uma nova tabela e inserindo valores de edição false ...

		tabelaSecundaria = new JTable(modelo_secundario) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		tabelaSecundaria.setBackground(corDasTabelas);

		tabelaSecundaria.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabelaSecundaria.getColumnModel().getColumn(0).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(2).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(3).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(4).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(5).setPreferredWidth(190);

		tabelaSecundaria.getColumnModel().getColumn(6).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(7).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(8).setPreferredWidth(190);

		tabelaSecundaria.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Adicionando eventos a tabela secundária, e para não permitir o
		// deslocamento das colunas ...

		tabelaSecundaria.addMouseListener(this);
		tabelaSecundaria.addKeyListener(this);

		tabelaSecundaria.getTableHeader().setReorderingAllowed(false);

		// Adicionando barra dce rolagem a tabela secundária ...

		scrollTabelaSecundaria = new JScrollPane(tabelaSecundaria);
		painelTabelaSecundaria.add(scrollTabelaSecundaria);

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

		painelPrincipal.setLayout(null);

		comboClientes = new JComboBox<String>();
		comboClientes.setBounds(721, 38, 176, 33);

		comboClientes.addItem("Selecione ...");

		FachadaClientes chamandoClientes = new FachadaClientes();

		clientes = new ArrayList<>();
		clientes = chamandoClientes.retornandoNomeClientes();

		for (int iC = 0; iC < clientes.size(); iC++) {

			comboClientes.addItem(clientes.get(iC).getNomeCompleto());

		}

		painelPrincipal.add(comboClientes);

		comboClientes.addMouseListener(this);
		comboClientes.addKeyListener(this);

		JLabel vendaParaCliente = new JLabel("Venda Para o Cliente");
		vendaParaCliente.setForeground(corDotexto);
		vendaParaCliente.setFont(new Font("Dialog", Font.PLAIN, 15));
		vendaParaCliente.setBounds(571, 46, 173, 16);
		painelPrincipal.add(vendaParaCliente);

		emEstoque = new JLabel("Em Estoque ...");
		emEstoque.setForeground(new Color(139, 139, 139));
		emEstoque.setFont(new Font("Dialog", Font.BOLD, 15));
		emEstoque.setBounds(15, 128, 173, 16);
		painelPrincipal.add(emEstoque);

		produtosEscolhidosTexto = new JLabel("Produtos Escolhidos ...");
		produtosEscolhidosTexto.setForeground(new Color(139, 139, 139));
		produtosEscolhidosTexto.setFont(new Font("Dialog", Font.BOLD, 15));
		produtosEscolhidosTexto.setBounds(15, 310, 173, 16);
		painelPrincipal.add(produtosEscolhidosTexto);

		campoQuantidade = new JTextField();
		campoQuantidade.setForeground(corDotexto);
		campoQuantidade.setBounds(781, 302, 72, 33);
		painelPrincipal.add(campoQuantidade);
		campoQuantidade.setColumns(10);

		campoQuantidade.addMouseListener(this);
		campoQuantidade.addKeyListener(this);

		mais = new JButton("+");
		mais.setBounds(859, 301, 36, 34);
		painelPrincipal.add(mais);

		mais.addMouseListener(this);
		mais.addKeyListener(this);

		JLabel textoQuantidade = new JLabel("Quantidade");
		textoQuantidade.setForeground(new Color(139, 139, 139));
		textoQuantidade.setFont(new Font("Dialog", Font.PLAIN, 15));
		textoQuantidade.setBounds(697, 310, 88, 16);
		painelPrincipal.add(textoQuantidade);

		JLabel textoFormaDePagamento = new JLabel("Forma De Pagamento");
		textoFormaDePagamento.setForeground(new Color(139, 139, 139));
		textoFormaDePagamento.setFont(new Font("Dialog", Font.PLAIN, 15));
		textoFormaDePagamento.setBounds(15, 487, 173, 25);
		painelPrincipal.add(textoFormaDePagamento);

		comboPagamento = new JComboBox<String>();

		comboPagamento.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Selecione ...", "Dinheiro (\u00E0 vista)",
				"Cart\u00E3o De Cr\u00E9dito", "Carn\u00EA" }));

		comboPagamento.setBounds(167, 484, 161, 33);
		painelPrincipal.add(comboPagamento);

		comboPagamento.addMouseListener(this);
		comboPagamento.addItemListener(this);
		comboPagamento.addKeyListener(this);

		JLabel textoVezes = new JLabel("Vezes");
		textoVezes.setForeground(new Color(139, 139, 139));
		textoVezes.setFont(new Font("Dialog", Font.PLAIN, 15));
		textoVezes.setBounds(340, 493, 54, 16);
		painelPrincipal.add(textoVezes);

		vezes = new JSpinner();
		vezes.setBounds(389, 485, 46, 33);
		vezes.setValue(1);
		vezes.setEnabled(false);
		painelPrincipal.add(vezes);

		vezes.addMouseListener(this);
		vezes.addChangeListener(this);
		vezes.addKeyListener(this);

		JLabel textoEntrega = new JLabel(
				"Deseja Que a Loja Entregue a Mercadoria ?");

		textoEntrega.setForeground(new Color(139, 139, 139));
		textoEntrega.setFont(new Font("Dialog", Font.PLAIN, 15));
		textoEntrega.setBounds(15, 529, 313, 20);
		painelPrincipal.add(textoEntrega);

		radioSim = new JRadioButton("Sim");
		radioSim.setBounds(330, 531, 54, 18);
		radioSim.setActionCommand("sim");
		painelPrincipal.add(radioSim);

		radioSim.addMouseListener(this);
		radioSim.addKeyListener(this);

		radioNao = new JRadioButton("N\u00E3o");
		radioNao.setBounds(389, 531, 54, 18);
		radioNao.setActionCommand("nao");
		painelPrincipal.add(radioNao);

		radioNao.addMouseListener(this);
		radioNao.addKeyListener(this);

		grupoEntrega = new ButtonGroup();
		grupoEntrega.add(radioSim);
		grupoEntrega.add(radioNao);

		textoPrecoEntrega = new JLabel("Pre\u00E7o Da Entrega");
		textoPrecoEntrega.setForeground(new Color(139, 139, 139));
		textoPrecoEntrega.setFont(new Font("Dialog", Font.PLAIN, 15));
		textoPrecoEntrega.setBounds(15, 564, 132, 24);
		painelPrincipal.add(textoPrecoEntrega);

		campoEntrega = new JTextField();
		campoEntrega.setForeground(new Color(139, 139, 139));
		campoEntrega.setColumns(10);
		campoEntrega.setEnabled(false);
		campoEntrega.setBounds(140, 561, 81, 33);
		campoEntrega.setText("0.0");
		painelPrincipal.add(campoEntrega);

		campoEntrega.addMouseListener(this);
		campoEntrega.addKeyListener(this);

		textoDataDeEntrega = new JLabel("Data De Entrega");
		textoDataDeEntrega.setForeground(new Color(139, 139, 139));
		textoDataDeEntrega.setFont(new Font("Dialog", Font.PLAIN, 15));
		textoDataDeEntrega.setBounds(231, 564, 132, 24);
		painelPrincipal.add(textoDataDeEntrega);

		campoDataDeEntrega = new JFormattedTextField(setMascara("##/##/####"));
		campoDataDeEntrega.setForeground(new Color(139, 139, 139));
		campoDataDeEntrega.setEnabled(false);
		campoDataDeEntrega.setColumns(10);
		campoDataDeEntrega.setBounds(351, 561, 84, 33);
		painelPrincipal.add(campoDataDeEntrega);

		campoDataDeEntrega.addMouseListener(this);
		campoDataDeEntrega.addKeyListener(this);

		textoDesconto = new JLabel("Desconto");
		textoDesconto.setForeground(new Color(139, 139, 139));
		textoDesconto.setFont(new Font("Dialog", Font.PLAIN, 15));
		textoDesconto.setBounds(462, 489, 72, 24);
		painelPrincipal.add(textoDesconto);

		campoDesconto = new JTextField();
		campoDesconto.setForeground(new Color(139, 139, 139));
		campoDesconto.setColumns(10);
		campoDesconto.setText("0.0");
		campoDesconto.setBounds(581, 484, 99, 33);
		painelPrincipal.add(campoDesconto);

		campoDesconto.addMouseListener(this);
		campoDesconto.addKeyListener(this);

		textoValorDaParcela = new JLabel("Valor Da Parcela");
		textoValorDaParcela.setForeground(new Color(139, 139, 139));
		textoValorDaParcela.setFont(new Font("Dialog", Font.PLAIN, 15));
		textoValorDaParcela.setBounds(462, 528, 132, 24);
		painelPrincipal.add(textoValorDaParcela);

		campoValorDaParcela = new JTextField();
		campoValorDaParcela.setForeground(new Color(139, 139, 139));
		campoValorDaParcela.setText("0.0");
		campoValorDaParcela.setFocusable(false);

		campoValorDaParcela.setColumns(10);
		campoValorDaParcela.setBounds(581, 524, 99, 33);
		painelPrincipal.add(campoValorDaParcela);

		campoValorDaParcela.addMouseListener(this);
		campoValorDaParcela.addKeyListener(this);

		textoValorTotal = new JLabel("Valor Total");
		textoValorTotal.setForeground(new Color(139, 139, 139));
		textoValorTotal.setFont(new Font("Dialog", Font.PLAIN, 15));
		textoValorTotal.setBounds(462, 566, 132, 24);
		painelPrincipal.add(textoValorTotal);

		campoValorTotal = new JTextField();
		campoValorTotal.setForeground(new Color(139, 139, 139));
		campoValorTotal.setText("" + valorTotal);
		campoValorTotal.setFocusable(false);

		campoValorTotal.setColumns(10);
		campoValorTotal.setBounds(581, 561, 99, 33);
		painelPrincipal.add(campoValorTotal);

		campoValorTotal.addMouseListener(this);
		campoValorTotal.addKeyListener(this);

		// Adicionando botão de menos a janela ...

		menos = new JButton("-");
		menos.setBounds(859, 484, 36, 34);

		menos.addMouseListener(this);
		menos.addKeyListener(this);
		painelPrincipal.add(menos);

		menos.addMouseListener(this);
		menos.addKeyListener(this);

		// Adicionando botão de remover tudo a janela ...

		removerTudo = new JButton("Remover Tudo");
		removerTudo.setBounds(743, 484, 110, 34);

		removerTudo.addMouseListener(this);
		removerTudo.addKeyListener(this);
		painelPrincipal.add(removerTudo);

		removerTudo.addMouseListener(this);
		removerTudo.addKeyListener(this);

		cancelar = new JButton("Cancelar");
		cancelar.setBounds(807, 560, 88, 34);
		painelPrincipal.add(cancelar);

		cancelar.addMouseListener(this);
		cancelar.addKeyListener(this);

		concluir = new JButton("Concluir");
		concluir.setBounds(717, 560, 88, 34);
		painelPrincipal.add(concluir);

		concluir.addMouseListener(this);
		concluir.addKeyListener(this);

		campoBusca = new JTextField("Buscar Produto ...");
		campoBusca.setBounds(721, 126, 152, 18);
		campoBusca.setBorder(null);
		campoBusca.setForeground(corDotexto);

		campoBusca
				.setToolTipText("Digite Aqui o Produto Desejado e Clique Em Ir, Ou Tecle Enter");

		campoBusca.addMouseListener(this);
		campoBusca.addKeyListener(this);

		painelPrincipal.add(campoBusca);
		campoBusca.setColumns(10);

		fundoBusca = new JLabel("New label");
		fundoBusca
				.setIcon(new ImageIcon(
						RealizandoPrincipal.class
								.getResource("/views/vendas/realizar_venda/png/campo_busca.png")));
		fundoBusca.setBounds(689, 123, 206, 25);
		painelPrincipal.add(fundoBusca);

		// Adicionando Fundo a Tela ...

		fundo = new JLabel(

				new ImageIcon(
						RealizandoPrincipal.class
								.getResource("/views/vendas/realizar_venda/jpg/Fundo_Principal.jpg")));

		fundo.setBounds(0, 0, 909, 602);
		painelPrincipal.add(fundo);

		// Propriedades da tela ...

		this.addMouseListener(this);
		this.addKeyListener(this);

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(915, 630);

		this.setClosable(true);
		this.setSelected(true);
		this.setResizable(false);
		this.setTitle("Thallyta Móveis - Realizando Venda");

		this.setContentPane(painelPrincipal);

		invisivel = new JTextField();
		invisivel.setBounds(15, 6, 110, 28);
		painelPrincipal.add(invisivel);
		invisivel.setColumns(10);
		this.setVisible(true);

	}

	@Override
	public void keyPressed(KeyEvent keyPress) {

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			this.dispose();

		}

		if (campoBusca.getText().equals("Buscar Produto ...")) {

			campoBusca.setText("");

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
						String.valueOf(pro.getPrecoDeVenda()),
						String.valueOf(pro.getQuantidade()), pro.getMarca(),
						pro.getModelo(), pro.getNumeroSerie(), pro.getCor() };

				i += 1;

			}

			DefaultTableModel modelo02 = new DefaultTableModel(dados, colunas);
			tabelaProdutosCadastrados.setModel(modelo02);

			tabelaProdutosCadastrados.getColumnModel().getColumn(0)
					.setPreferredWidth(190);
			tabelaProdutosCadastrados.getColumnModel().getColumn(1)
					.setPreferredWidth(190);
			tabelaProdutosCadastrados.getColumnModel().getColumn(2)
					.setPreferredWidth(190);
			tabelaProdutosCadastrados.getColumnModel().getColumn(3)
					.setPreferredWidth(190);
			tabelaProdutosCadastrados.getColumnModel().getColumn(4)
					.setPreferredWidth(190);
			tabelaProdutosCadastrados.getColumnModel().getColumn(5)
					.setPreferredWidth(190);

			tabelaProdutosCadastrados.getColumnModel().getColumn(6)
					.setPreferredWidth(190);
			tabelaProdutosCadastrados.getColumnModel().getColumn(7)
					.setPreferredWidth(190);
			tabelaProdutosCadastrados.getColumnModel().getColumn(8)
					.setPreferredWidth(250);

		}

		if (campoBusca.isFocusOwner() == true
				& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

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
							String.valueOf(pro.getPrecoDeVenda()),
							String.valueOf(pro.getQuantidade()),
							pro.getMarca(), pro.getModelo(),
							pro.getNumeroSerie(), pro.getCor() };

					i += 1;

				}

				DefaultTableModel modelo2 = new DefaultTableModel(dados,
						colunas);

				tabelaProdutosCadastrados.setModel(modelo2);

				tabelaProdutosCadastrados
						.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

				for (int j = 0; j < 9; j++) {

					tabelaProdutosCadastrados.getColumnModel().getColumn(j)
							.setPreferredWidth(190);

				}

			}

		}

		if (campoEntrega.isFocusOwner()
				& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			double valorDoCampoEntrega = Double.parseDouble((campoEntrega
					.getText()));

			double valorTotal = Double.parseDouble(campoValorTotal.getText());
			double valorCalculado = valorTotal + valorDoCampoEntrega;

			campoValorTotal.setText("" + valorCalculado);

			double ValorTotal = Double.parseDouble((campoValorTotal.getText()));

			int ve = (int) vezes.getValue();

			double totalParcelado = ValorTotal / ve;

			campoValorDaParcela.setText("" + totalParcelado);

		}

		if (campoDesconto.isFocusOwner()
				& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			double valorDoCampoDesconto = Double.parseDouble((campoDesconto
					.getText()));

			double valorTotal = Double.parseDouble(campoValorTotal.getText());
			double valorCalculado = valorTotal - valorDoCampoDesconto;

			campoValorTotal.setText("" + valorCalculado);

			if (comboPagamento.getSelectedIndex() == 1) {

				campoValorDaParcela.setText(campoValorTotal.getText());

			}

			if (comboPagamento.getSelectedIndex() == 2
					|| comboPagamento.getSelectedIndex() == 3) {

				int vezesEscolheu = Integer.parseInt(String.valueOf(vezes
						.getValue()));

				double valorTotalNovo = Double.parseDouble(campoValorTotal
						.getText());

				double valorParcelado = valorTotalNovo / vezesEscolheu;

				campoValorDaParcela.setText("" + valorParcelado);

			}

		}

		if (campoQuantidade.isFocusOwner() == true
				& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			VerificandoErros verificandoQuantidade = new VerificandoErros();
			boolean veri = false;

			try {

				veri = verificandoQuantidade.verificando(campoQuantidade
						.getText());

			}

			catch (ClassNotFoundException e) {

				new ErroEncontrado();

			}

			catch (InstantiationException e) {

				new ErroEncontrado();

			}

			catch (IllegalAccessException e) {

				new ErroEncontrado();

			}

			catch (UnsupportedLookAndFeelException e) {

				new ErroEncontrado();

			}

			if (veri == true) {

				produto = new Produto();

				int row = tabelaProdutosCadastrados.getSelectedRow();

				int quantidadeCampoPego = Integer
						.parseInt((String) campoQuantidade.getText());

				int quantidadeTabela = Integer
						.parseInt((String) tabelaProdutosCadastrados
								.getValueAt(row, 4));

				if (quantidadeCampoPego > quantidadeTabela) {

					try {

						UIManager
								.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

						JOptionPane
								.showMessageDialog(
										null,
										"No estoque não há esta quantidade digitada, por favor inserir um valor menor ...",
										"Thallyta Móveis - Aviso Do Sistema",
										JOptionPane.ERROR_MESSAGE);

					}

					catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException
							| UnsupportedLookAndFeelException e1) {

						new ErroEncontrado();

					}

				}

				else {

					produto.setCodigo(Integer
							.parseInt((String) tabelaProdutosCadastrados
									.getValueAt(row, 0)));

					produto.setDescricao((String) tabelaProdutosCadastrados
							.getValueAt(row, 1));

					produto.setCategoria((String) tabelaProdutosCadastrados
							.getValueAt(row, 2));

					produto.setPrecoDeVenda(Double
							.parseDouble((String) tabelaProdutosCadastrados
									.getValueAt(row, 3)));

					int quantidade = Integer.parseInt((String) campoQuantidade
							.getText());

					Double valorProduto = Double
							.parseDouble((String) tabelaProdutosCadastrados
									.getValueAt(row, 3));

					Double valor = valorProduto * quantidade;

					valorTotal = valorTotal + valor;

					campoValorTotal.setText("" + valorTotal);

					produto.setQuantidade(Integer
							.parseInt((String) campoQuantidade.getText()));

					produto.setMarca((String) tabelaProdutosCadastrados
							.getValueAt(row, 5));

					produto.setModelo((String) tabelaProdutosCadastrados
							.getValueAt(row, 6));

					produto.setNumeroSerie((String) tabelaProdutosCadastrados
							.getValueAt(row, 7));

					produto.setCor((String) tabelaProdutosCadastrados
							.getValueAt(row, 8));

					adicionandoElemento(produto);

					campoQuantidade.setText("");

				}

			}

			else {

				campoQuantidade.setText("");

			}

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == cancelar) {

			this.dispose();

		}

		int coluna = tabelaProdutosCadastrados.getSelectedColumn();
		int linha = tabelaProdutosCadastrados.getSelectedRow();

		if (mouseClick.getClickCount() == 2
				&& tabelaProdutosCadastrados.isCellSelected(linha, coluna)) {

			campoQuantidade.requestFocus();

		}

		int colunaSelecionados = tabelaSecundaria.getSelectedColumn();
		int linhaSelecionados = tabelaSecundaria.getSelectedRow();

		if (mouseClick.getSource() == menos
				&& tabelaSecundaria.isCellSelected(linhaSelecionados,
						colunaSelecionados)) {

			int rowSecudarias = tabelaSecundaria.getSelectedRow();
			modelo_secundario.removeRow(rowSecudarias);

		}

		if (mouseClick.getSource() == removerTudo) {

			while (tabelaSecundaria.getModel().getRowCount() > 0)
				modelo_secundario.removeRow(0);

		}

		if (mouseClick.getSource() == radioNao) {

			campoDataDeEntrega.setEnabled(false);

			campoEntrega.setText("0.0");
			campoEntrega.setEnabled(false);

		}

		if (mouseClick.getSource() == radioSim) {

			campoDataDeEntrega.setEnabled(true);

			campoEntrega.setText("0.0");
			campoEntrega.setEnabled(true);

		}

		if (mouseClick.getSource() == mais
				&& tabelaProdutosCadastrados.isCellSelected(linha, coluna)) {

			VerificandoErros verificandoQuantidade = new VerificandoErros();
			boolean veri = false;

			try {

				veri = verificandoQuantidade.verificando(campoQuantidade
						.getText());

			}

			catch (ClassNotFoundException e) {

				new ErroEncontrado();

			}

			catch (InstantiationException e) {

				new ErroEncontrado();

			}

			catch (IllegalAccessException e) {

				new ErroEncontrado();

			}

			catch (UnsupportedLookAndFeelException e) {

				new ErroEncontrado();

			}

			if (veri == true) {

				produto = new Produto();

				int row = tabelaProdutosCadastrados.getSelectedRow();

				int quantidadeCampoPego = Integer
						.parseInt((String) campoQuantidade.getText());

				int quantidadeTabela = Integer
						.parseInt((String) tabelaProdutosCadastrados
								.getValueAt(row, 4));

				if (quantidadeCampoPego > quantidadeTabela) {

					try {

						UIManager
								.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

						JOptionPane
								.showMessageDialog(
										null,
										"No estoque não há esta quantidade digitada, por favor inserir um valor menor ...",
										"Thallyta Móveis - Aviso Do Sistema",
										JOptionPane.ERROR_MESSAGE);

					}

					catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException
							| UnsupportedLookAndFeelException e1) {

						new ErroEncontrado();

					}

				}

				else {

					produto.setCodigo(Integer
							.parseInt((String) tabelaProdutosCadastrados
									.getValueAt(row, 0)));

					produto.setDescricao((String) tabelaProdutosCadastrados
							.getValueAt(row, 1));

					produto.setCategoria((String) tabelaProdutosCadastrados
							.getValueAt(row, 2));

					produto.setPrecoDeVenda(Double
							.parseDouble((String) tabelaProdutosCadastrados
									.getValueAt(row, 3)));

					int quantidade = Integer.parseInt((String) campoQuantidade
							.getText());

					Double valorProduto = Double
							.parseDouble((String) tabelaProdutosCadastrados
									.getValueAt(row, 3));

					Double valor = valorProduto * quantidade;

					valorTotal = valorTotal + valor;

					campoValorTotal.setText("" + valorTotal);

					produto.setQuantidade(Integer
							.parseInt((String) campoQuantidade.getText()));

					produto.setMarca((String) tabelaProdutosCadastrados
							.getValueAt(row, 5));

					produto.setModelo((String) tabelaProdutosCadastrados
							.getValueAt(row, 6));

					produto.setNumeroSerie((String) tabelaProdutosCadastrados
							.getValueAt(row, 7));

					produto.setCor((String) tabelaProdutosCadastrados
							.getValueAt(row, 8));

					adicionandoElemento(produto);

					campoQuantidade.setText("");

				}

			}

			else {

				campoQuantidade.setText("");

			}

		}

		if (mouseClick.getSource() == concluir) {

			boolean v = verificandoDataEntrega();

			String valorDoSpinner = String.valueOf(vezes.getValue());

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

			// Se a 2º tabela não tiver nada ...

			if (comboClientes.getSelectedItem().equals("Selecione ...")) {

				JOptionPane
						.showMessageDialog(
								null,
								"Você Ainda não escolheu nenhum cliente para efetuar esta compra, este procedimento é obrigatório ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

			}

			else if (tabelaSecundaria.getModel().getRowCount() == 0) {

				JOptionPane
						.showMessageDialog(
								null,
								"A segunda tabela está vazia, tente colocar produtos dentro dela para efetuar este procedimento ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

			}

			else if (comboPagamento.getSelectedItem().equals("Selecione ...")) {

				JOptionPane
						.showMessageDialog(
								null,
								"Você não escolheu o tipo de pagamento que este cliente irá efetuar, selecione algum ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

			}

			else if (radioSim.isSelected() == true & v == false) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que a data de entrega digitada está incorreta, tente outra ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

			}

			else if (radioSim.isSelected()
					& campoEntrega.getText().trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que o campo de preço de entrega está vazio, adicione algum valor (Ex: 0.0) ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

			}

			else if (radioSim.isSelected() == false
					& radioNao.isSelected() == false) {

				JOptionPane
						.showMessageDialog(
								null,
								"Você não escolheu o tipo de entrega, por favor selecione a opção desejada ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

			}

			else if (comboPagamento.getSelectedIndex() == 2
					& valorDoSpinner.equals("0")) {

				JOptionPane
						.showMessageDialog(
								null,
								"Você escolheu a opção de cartão de crédito e a quantidade de vezes está com o valor 0, no mínimo é permitido 1 ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

			}

			else if (comboPagamento.getSelectedIndex() == 3
					& valorDoSpinner.equals("0")) {

				JOptionPane
						.showMessageDialog(
								null,
								"Você escolheu a opção de carnê e a quantidade de vezes está com o valor 0, no mínimo é permitido 1 ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

			}

			else {

				String nomeSelecionadoCliente = String.valueOf(comboClientes
						.getSelectedItem());

				for (int i = 0; i < clientes.size(); i++) {

					if (clientes.get(i).getNomeCompleto()
							.equals(nomeSelecionadoCliente)) {

						codigoCliente = clientes.get(i).getCodigo();
						nomeCliente = clientes.get(i).getNomeCompleto();

						ruaCliente = clientes.get(i).getRua();
						numeroCliente = clientes.get(i).getNumero();
						bairro = clientes.get(i).getBairro();
						complemento = clientes.get(i).getComplemento();
						cidade = clientes.get(i).getCidade();
						estado = clientes.get(i).getEstado();
						cep = clientes.get(i).getCep();

						telefone01 = clientes.get(i).getTelefone01();
						telefone02 = clientes.get(i).getTelefone02();

						email = clientes.get(i).getEmail();

						produtosCliente = new ArrayList<>();

						Produto produto;

						for (int j = 0; j < tabelaSecundaria.getRowCount(); j++) {

							produto = new Produto();

							int codigoProduto = (int) tabelaSecundaria
									.getValueAt(j, 0);

							produto.setCodigo(codigoProduto);

							produto.setDescricao((String) tabelaSecundaria
									.getValueAt(j, 1));

							produto.setCategoria((String) tabelaSecundaria
									.getValueAt(j, 2));

							produto.setPrecoDeVenda((double) tabelaSecundaria
									.getValueAt(j, 3));

							produto.setQuantidade((int) tabelaSecundaria
									.getValueAt(j, 4));

							produto.setMarca((String) tabelaSecundaria
									.getValueAt(j, 5));

							produto.setModelo((String) tabelaSecundaria
									.getValueAt(j, 6));

							produto.setNumeroSerie((String) tabelaSecundaria
									.getValueAt(j, 7));

							produto.setCor((String) tabelaSecundaria
									.getValueAt(j, 8));

							produtosCliente.add(produto);

						}
						;

						formaPagamento = String.valueOf(comboPagamento
								.getSelectedItem());

						vezesPagamento = String.valueOf(vezes.getValue());

						if (radioSim.isSelected() == true) {

							permissaoEntrega = "Sim";

						}

						if (radioNao.isSelected() == true) {

							permissaoEntrega = "Não";

						}

						descontoCompra = campoDesconto.getText();
						valorParcela = campoValorDaParcela.getText();
						valorTotalCompra = campoValorTotal.getText();

						dataVenda = pegandoDataDoSistema();
						precoEntrega = campoEntrega.getText();
						dataEntrega = campoDataDeEntrega.getText();

						FachadaVendas gravando = new FachadaVendas();

						try {

							gravando.adicionandoVendas(codigoCliente,
									nomeCliente, ruaCliente, numeroCliente,
									bairro, complemento, cidade, estado, cep,
									telefone01, telefone02, email,
									produtosCliente, formaPagamento,
									vezesPagamento, permissaoEntrega,
									precoEntrega, dataEntrega, descontoCompra,
									valorParcela, valorTotalCompra, dataVenda);

							vendaEfetuada.setVisible(true);
							concluirFinal.setVisible(true);
							gerarComprovanteFinal.setVisible(true);

							ArrayList<String> quantidades = new ArrayList<>();
							ArrayList<String> codigos = new ArrayList<>();

							for (int k = 0; k < produtosCliente.size(); k++) {

								codigos.add(String.valueOf(produtosCliente.get(
										k).getCodigo()));
								quantidades.add(String.valueOf(produtosCliente
										.get(k).getQuantidade()));

							}

							FachadaProdutos subtraindoDoEstoque = new FachadaProdutos();
							subtraindoDoEstoque.subtraindoProdutos(codigos,
									quantidades);

						}

						catch (SQLException e) {

							e.printStackTrace();

						}

					}

				}

			}

		}

		if (mouseClick.getSource() == gerarComprovanteFinal) {

			System.out.println("2222");

			JFileChooser chooser;
			chooser = new JFileChooser();

			String caminho = "";

			int retorno = chooser.showSaveDialog(null);

			if (retorno == JFileChooser.APPROVE_OPTION) {

				caminho = chooser.getSelectedFile().getAbsolutePath();

				try {

					new GerarComprovante(codigoCliente, nomeCliente,
							ruaCliente, numeroCliente, bairro, complemento,
							cidade, estado, cep, telefone01, telefone02, email,
							produtosCliente, formaPagamento, vezesPagamento,
							permissaoEntrega, precoEntrega, dataEntrega,
							descontoCompra, valorParcela, valorTotal,
							dataVenda, caminho);

				}

				catch (Exception e) {
					e.printStackTrace();

				}

			}

		}

		if (mouseClick.getSource() == concluirFinal) {

			this.dispose();

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == concluirFinal) {

			concluirFinal
					.setIcon(new ImageIcon(
							RealizandoPrincipal.class
									.getResource("/views/vendas/realizar_venda/png/concluir_compra_mouse.png")));

		}

		if (mouseEntry.getSource() == gerarComprovanteFinal) {

			gerarComprovanteFinal
					.setIcon(new ImageIcon(
							RealizandoPrincipal.class
									.getResource("/views/vendas/realizar_venda/png/gerar_comprovante_mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == concluirFinal) {

			concluirFinal
					.setIcon(new ImageIcon(
							RealizandoPrincipal.class
									.getResource("/views/vendas/realizar_venda/png/concluir_compra.png")));

		}

		if (mouseExit.getSource() == gerarComprovanteFinal) {

			gerarComprovanteFinal
					.setIcon(new ImageIcon(
							RealizandoPrincipal.class
									.getResource("/views/vendas/realizar_venda/png/gerar_comprovante.png")));

		}

	}

	@Override
	public void mousePressed(MouseEvent mousePress) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseReal) {

	}

	public void adicionandoElemento(Produto produto) {

		modelo_secundario.addRow(new Object[] { produto.getCodigo(),
				produto.getDescricao(), produto.getCategoria(),
				produto.getPrecoDeVenda(), produto.getQuantidade(),
				produto.getMarca(), produto.getModelo(),
				produto.getNumeroSerie(), produto.getCor() });

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
	public void itemStateChanged(ItemEvent arg0) {

		if (comboPagamento.getSelectedIndex() == 1) {

			vezes.setValue(1);
			vezes.setEnabled(false);

			campoValorDaParcela.setText(campoValorTotal.getText());

		}

		else if (comboPagamento.getSelectedIndex() == 2) {

			vezes.setValue(1);
			vezes.setEnabled(true);

		}

		else if (comboPagamento.getSelectedIndex() == 3) {

			vezes.setValue(1);
			vezes.setEnabled(true);

		}

	}

	@Override
	public void stateChanged(ChangeEvent v) {

		if (v.getSource() == vezes) {

			Double valorpego = Double.parseDouble(campoValorTotal.getText());
			Double valorParcelado = valorpego
					/ Integer.parseInt(String.valueOf(vezes.getValue()));

			campoValorDaParcela.setText(String.valueOf(valorParcelado));

		}

	}

	private String pegandoDataDoSistema() {

		Date now = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String s = df.format(now);

		return s;

	}

	public boolean verificandoDataEntrega() {

		Pattern p1 = Pattern
				.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");

		Matcher m1 = p1.matcher(campoDataDeEntrega.getText());

		if (!m1.find()) {

			return false;

		}

		return true;

	}

}