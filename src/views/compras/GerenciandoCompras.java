package views.compras;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;

import modelo.Produto;
import views.gerenciamento.ger_produtos.GerenciandoProdutos;
import views.principais.tela_de_erro.ErroEncontrado;
import fachada.FachadaProdutos;

public class GerenciandoCompras extends JInternalFrame implements
		MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	// Objetos e varíáveis a serem incrementadas ...

	JPanel painelPrincipal, painelTabela, painelTabelaSecundaria;
	JTable tabelaDeResultados, tabelaSecundaria;
	JScrollPane scrollTabela, scrollTabelaSecundaria;

	JLabel fundo, quantidadeTexto, quantidadeDeProdutos,
			quantidadeDeProdutosEscolhidos, compraAdicionada, botaoIr;

	JTextField quantidadeCampo, campoBusca;
	JButton mais, menos, removerTudo, concluir, cancelar;

	// Variáveis de apoio ...

	private ArrayList<String> codigos, quantidades;

	// ----------------------------------------------------------------------------------------------------------------------------------------------

	private ArrayList<Produto> produtos;
	private ArrayList<Produto> produtosEscolhidos = new ArrayList<>();

	// Rodapé das colunas que vão ser incrementadas na tabela ...

	private String[] colunas = new String[] { "Código", "Descrição",
			"Categoria", "Data De Cadasto", "Preço De Compra",
			"Preço De Venda", "Quantidade", "Marca", "Observações", "Modelo",
			"Número De Série", "Cor" };

	private String[][] dados_secundarios = new String[produtosEscolhidos.size()][];

	DefaultTableModel modelo_secundario = new DefaultTableModel(
			dados_secundarios, colunas);

	Produto produto;

	// Cor (es) usadas ...

	private Color corDotexto = new Color(139, 139, 139);
	private Color corFundoTabela = new Color(242, 242, 242);

	public GerenciandoCompras() throws SQLException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException, PropertyVetoException {

		// Adicionando o painel principal a tela ...

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		// Confirmação de compra adicionada ...

		compraAdicionada = new JLabel();

		compraAdicionada.setIcon(new ImageIcon(GerenciandoCompras.class
				.getResource("/views/compras/png/Adicionados_Sucesso.png")));

		compraAdicionada.addMouseListener(this);
		compraAdicionada.addKeyListener(this);

		compraAdicionada.setBounds(0, 0, 927, 631);
		compraAdicionada.setVisible(false);

		painelPrincipal.add(compraAdicionada);

		// Adicionando o JLabel quantidade de produtos a tela ...

		quantidadeDeProdutos = new JLabel("Quantidade De Produtos:");
		quantidadeDeProdutos.setFont(new Font("Dialog", Font.PLAIN, 15));
		quantidadeDeProdutos.setForeground(corDotexto);
		quantidadeDeProdutos.setBounds(20, 320, 200, 34);

		quantidadeDeProdutos.addMouseListener(this);
		quantidadeDeProdutos.addKeyListener(this);

		painelPrincipal.add(quantidadeDeProdutos);

		// Adicionando o botão + a tela ...

		mais = new JButton("+");
		mais.setBounds(876, 319, 36, 34);
		mais.setToolTipText("Depois que escolher o produto dese"
				+ "jado e digitar a quantidade clique aqui");

		mais.addMouseListener(this);
		mais.addKeyListener(this);

		painelPrincipal.add(mais);

		// Adicionando o JLabel de quantidade a tela ...

		quantidadeTexto = new JLabel("Quantidade");
		quantidadeTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
		quantidadeTexto.setForeground(corDotexto);
		quantidadeTexto.setBounds(705, 320, 90, 34);

		campoBusca = new JTextField("Buscar Produto ...");
		campoBusca.setBounds(695, 41, 150, 22);
		campoBusca.setForeground(corDotexto);
		campoBusca.setBorder(null);

		campoBusca
				.setToolTipText("Digite Aqui o Produto Desejado e Clique Em Ir, Ou Tecle Enter");

		campoBusca.addMouseListener(this);
		campoBusca.addKeyListener(this);

		painelPrincipal.add(campoBusca);

		botaoIr = new JLabel(

				new ImageIcon(
						GerenciandoProdutos.class
								.getResource("/views/gerenciamento/ger_produtos/png/Ir.png")));

		botaoIr.setToolTipText("Clique Aqui Para Pesquisar o Produto Digitado, Ou Tecle ENTER");
		botaoIr.setBounds(849, 37, 62, 32);

		botaoIr.addMouseListener(this);
		botaoIr.addKeyListener(this);

		painelPrincipal.add(botaoIr);

		quantidadeTexto.addMouseListener(this);
		quantidadeTexto.addKeyListener(this);

		painelPrincipal.add(quantidadeTexto);

		// Adicionando o campo de quantidade a tela ...

		quantidadeCampo = new JTextField();
		quantidadeCampo.setForeground(corDotexto);
		quantidadeCampo.setBounds(788, 320, 80, 33);

		quantidadeCampo
				.setToolTipText("Digite aqui a quantidade do produto que deseja adicionar, e clique em + ou tecle (ENTER)");

		quantidadeCampo.addMouseListener(this);
		quantidadeCampo.addKeyListener(this);

		painelPrincipal.add(quantidadeCampo);

		// Chamando a fachada e listando todos os produtos do banco de dados ...

		FachadaProdutos resgatandoProdutos = new FachadaProdutos();
		produtos = resgatandoProdutos.listandoProdutos();

		// Criando uma matriz de dados que serão inseridos na tabela ...

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
					pro.getNumeroSerie(), pro.getCor() };

			i += 1;

		}

		// JLabel informando a quantidade dos produtos cadastrados ...

		quantidadeDeProdutos
				.setText("Produtos Cadastrados: " + produtos.size());

		// Inserindo painel da tabela principal ...

		painelTabela = new JPanel();
		painelTabela.setLayout(new BorderLayout());
		painelTabela.setBounds(20, 130, 890, 180);

		painelTabela.addMouseListener(this);
		painelTabela.addKeyListener(this);
		painelPrincipal.add(painelTabela);

		// Definindo um modelo padrão a esta janela ...

		DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

		// Inserindo a tabela principal na janela e setando valores false para
		// que os campos não sejam editados ...

		tabelaDeResultados = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		tabelaDeResultados
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Inserindo evento de mouse a janela e false para que as colunas não

		tabelaDeResultados.addMouseListener(this);
		tabelaDeResultados.getTableHeader().setReorderingAllowed(false);
		tabelaDeResultados.setBackground(corFundoTabela);

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

		// Inserindo scroll a tabela principal de produtos ...

		scrollTabela = new JScrollPane(tabelaDeResultados);
		painelTabela.add(scrollTabela);

		// Criando painel da tabela secundária

		painelTabelaSecundaria = new JPanel();
		painelTabelaSecundaria.setLayout(new BorderLayout());
		painelTabelaSecundaria.setBounds(20, 362, 890, 150);

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

		tabelaSecundaria.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabelaSecundaria.getColumnModel().getColumn(0).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(2).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(3).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(4).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(5).setPreferredWidth(190);

		tabelaSecundaria.getColumnModel().getColumn(6).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(7).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(8).setPreferredWidth(250);
		tabelaSecundaria.getColumnModel().getColumn(9).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(10).setPreferredWidth(190);
		tabelaSecundaria.getColumnModel().getColumn(11).setPreferredWidth(190);

		tabelaSecundaria.setBackground(corFundoTabela);

		tabelaSecundaria.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Adicionando eventos a tabela secundária, e para não permitir o
		// deslocamento das colunas ...

		tabelaSecundaria.addMouseListener(this);
		tabelaSecundaria.addKeyListener(this);

		tabelaSecundaria.getTableHeader().setReorderingAllowed(false);

		// Adicionando barra dce rolagem a tabela secundária ...

		scrollTabelaSecundaria = new JScrollPane(tabelaSecundaria);
		painelTabelaSecundaria.add(scrollTabelaSecundaria);

		// Adicionando botão cancelar a tela ...

		cancelar = new JButton("Cancelar");
		cancelar.setBounds(822, 575, 90, 34);
		cancelar.setToolTipText("Clique aqui para cancelar esta operação, ou tecle (ESC)");

		cancelar.addMouseListener(this);
		cancelar.addKeyListener(this);

		painelPrincipal.add(cancelar);

		// Adicionando botão concluir a tela ...

		concluir = new JButton("Concluir");
		concluir.setBounds(726, 575, 90, 34);

		concluir.setToolTipText("Clique aqui para concluir esta operação ou tecle (CTRL + C)");

		concluir.addMouseListener(this);
		concluir.addKeyListener(this);

		painelPrincipal.add(concluir);

		// Adicionando botão de menos a janela ...

		menos = new JButton("-");
		menos.setBounds(876, 520, 36, 34);
		menos.setToolTipText("Se deseja retirar algum produto desta tabela, clique no mesmo e após isto aqui, ou selecione e tecle (-)");

		menos.addMouseListener(this);
		menos.addKeyListener(this);
		painelPrincipal.add(menos);

		// Adicionando botão de remover tudo a janela ...

		removerTudo = new JButton("Remover Tudo");
		removerTudo.setBounds(760, 520, 110, 34);

		removerTudo
				.setToolTipText("Se deseja remover todos os dados desta tabela, clique aqui ou simplemente tecle (CTRL + R)");

		removerTudo.addMouseListener(this);
		removerTudo.addKeyListener(this);
		painelPrincipal.add(removerTudo);

		// Adicionando o JLabel quantidade de produtos escolhidos a tela ...

		quantidadeDeProdutosEscolhidos = new JLabel(

		"Quantidade De Produtos Escolhidos: "
				+ tabelaSecundaria.getModel().getRowCount());

		quantidadeDeProdutosEscolhidos.setFont(new Font("Dialog", Font.PLAIN,
				15));

		quantidadeDeProdutosEscolhidos.setForeground(corDotexto);
		quantidadeDeProdutosEscolhidos.setBounds(20, 520, 300, 34);

		quantidadeDeProdutosEscolhidos.addMouseListener(this);
		quantidadeDeProdutosEscolhidos.addKeyListener(this);

		painelPrincipal.add(quantidadeDeProdutosEscolhidos);

		// Adicionando o fundo principal a tela ...

		fundo = new JLabel();

		fundo.setIcon(new ImageIcon(GerenciandoCompras.class
				.getResource("/views/compras/jpg/Fundo.jpg")));

		painelPrincipal.add(fundo);

		// Propriedades da tela ...

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

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(939, 659);

		this.setContentPane(painelPrincipal);
		this.addKeyListener(this);

		this.setResizable(false);
		this.setTitle("Thallyta Móveis - Gerenciando Compras");

		this.setClosable(true);
		this.setSelected(true);
		this.setVisible(true);

	}

	@Override
	public void keyPressed(KeyEvent keyPress) {

		if (keyPress.getKeyCode() == KeyEvent.VK_R && keyPress.isControlDown()) {

			while (tabelaSecundaria.getModel().getRowCount() > 0)
				modelo_secundario.removeRow(0);

			quantidadeDeProdutosEscolhidos
					.setText("Quantidade De Produtos Escolhidos: "
							+ tabelaSecundaria.getModel().getRowCount());

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_C && keyPress.isControlDown()) {

			if (tabelaSecundaria.getModel().getRowCount() == 0) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"Não Há Nada Para Incluir, a 2º Tabela Está Vazia ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

			}

			else {

				int quantidadeDeLinhas = tabelaSecundaria.getModel()
						.getRowCount();

				codigos = new ArrayList<>();
				quantidades = new ArrayList<>();

				for (int i = 0; i < quantidadeDeLinhas; i++) {

					codigos.add(String.valueOf(tabelaSecundaria
							.getValueAt(i, 0)));

					quantidades.add(String.valueOf(tabelaSecundaria.getValueAt(
							i, 6)));

				}

				FachadaProdutos gravando = new FachadaProdutos();

				try {

					gravando.adicionandoCompras(codigos, quantidades);
					compraAdicionada.setVisible(true);

				}

				catch (SQLException e) {

					new ErroEncontrado();

				}

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			dispose();

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
						pro.getDataDeCadastro(),
						String.valueOf(pro.getPrecoDeCompra()),
						String.valueOf(pro.getPrecoDeVenda()),
						String.valueOf(pro.getQuantidade()), pro.getMarca(),
						pro.getObservacoes(), pro.getModelo(),
						pro.getNumeroSerie(), pro.getCor() };

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
			tabelaDeResultados.getColumnModel().getColumn(11)
					.setPreferredWidth(190);

		}

		int coluna = tabelaDeResultados.getSelectedColumn();
		int linha = tabelaDeResultados.getSelectedRow();

		if (quantidadeCampo.isFocusOwner() == true
				& keyPress.getKeyCode() == KeyEvent.VK_ENTER
				& tabelaDeResultados.isCellSelected(linha, coluna)) {

			VerificandoErros verificandoQuantidade = new VerificandoErros();
			boolean veri = false;

			try {

				veri = verificandoQuantidade.verificando(quantidadeCampo
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

				int row = tabelaDeResultados.getSelectedRow();

				produto.setCodigo(Integer.parseInt((String) tabelaDeResultados
						.getValueAt(row, 0)));

				produto.setDescricao((String) tabelaDeResultados.getValueAt(
						row, 1));

				produto.setCategoria((String) tabelaDeResultados.getValueAt(
						row, 2));

				produto.setDataDeCadastro((String) tabelaDeResultados
						.getValueAt(row, 3));

				produto.setPrecoDeCompra(Double
						.parseDouble((String) tabelaDeResultados.getValueAt(
								row, 4)));

				produto.setPrecoDeVenda(Double
						.parseDouble((String) tabelaDeResultados.getValueAt(
								row, 5)));

				produto.setQuantidade(Integer.parseInt((String) quantidadeCampo
						.getText()));

				produto.setMarca((String) tabelaDeResultados.getValueAt(row, 7));

				produto.setObservacoes((String) tabelaDeResultados.getValueAt(
						row, 8));

				produto.setModelo((String) tabelaDeResultados
						.getValueAt(row, 9));

				produto.setNumeroSerie((String) tabelaDeResultados.getValueAt(
						row, 10));

				produto.setCor((String) tabelaDeResultados.getValueAt(row, 11));

				adicionandoElemento(produto);

				quantidadeCampo.setText("");

				quantidadeDeProdutosEscolhidos
						.setText("Quantidade De Produtos Escolhidos: "
								+ tabelaSecundaria.getModel().getRowCount());

			}

			else {

				quantidadeCampo.setText("");

			}

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

				for (int j = 0; j < 12; j++) {

					tabelaDeResultados.getColumnModel().getColumn(j)
							.setPreferredWidth(190);

				}

			}

		}

		if (compraAdicionada.isVisible() == true
				& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			this.dispose();

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

	@Override
	public void mouseClicked(MouseEvent mouseCLick) {

		if (mouseCLick.getSource() == campoBusca
				& campoBusca.getText().equals("Buscar Produto ...")) {

			campoBusca.setText("");

		}

		if (compraAdicionada.isVisible() == true
				&& mouseCLick.getSource() == compraAdicionada) {

			this.dispose();

		}

		if (mouseCLick.getSource() == botaoIr) {

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

				for (int j = 0; j < 12; j++) {

					tabelaDeResultados.getColumnModel().getColumn(j)
							.setPreferredWidth(190);

				}

			}

		}

		if (mouseCLick.getSource() == cancelar) {

			this.dispose();

		}

		if (mouseCLick.getSource() == concluir) {

			if (tabelaSecundaria.getModel().getRowCount() == 0) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"Não Há Nada Para Incluir, a 2º Tabela Está Vazia ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {

					new ErroEncontrado();

				}

			}

			else {

				int quantidadeDeLinhas = tabelaSecundaria.getModel()
						.getRowCount();

				codigos = new ArrayList<>();
				quantidades = new ArrayList<>();

				for (int i = 0; i < quantidadeDeLinhas; i++) {

					codigos.add(String.valueOf(tabelaSecundaria
							.getValueAt(i, 0)));

					quantidades.add(String.valueOf(tabelaSecundaria.getValueAt(
							i, 6)));

				}

				FachadaProdutos gravando = new FachadaProdutos();

				try {

					gravando.adicionandoCompras(codigos, quantidades);
					compraAdicionada.setVisible(true);

				}

				catch (SQLException e) {

					new ErroEncontrado();

				}

			}

		}

		int coluna = tabelaDeResultados.getSelectedColumn();
		int linha = tabelaDeResultados.getSelectedRow();

		if (mouseCLick.getClickCount() == 2
				& tabelaDeResultados.isCellSelected(linha, coluna)) {

			quantidadeCampo.requestFocus();

		}

		int colunaSelecionados = tabelaSecundaria.getSelectedColumn();
		int linhaSelecionados = tabelaSecundaria.getSelectedRow();

		if (mouseCLick.getSource() == menos
				&& tabelaSecundaria.isCellSelected(linhaSelecionados,
						colunaSelecionados)) {

			int rowSecudarias = tabelaSecundaria.getSelectedRow();
			modelo_secundario.removeRow(rowSecudarias);

			quantidadeDeProdutosEscolhidos
					.setText("Quantidade De Produtos Escolhidos: "
							+ tabelaSecundaria.getModel().getRowCount());

		}

		if (mouseCLick.getSource() == removerTudo) {

			while (tabelaSecundaria.getModel().getRowCount() > 0)
				modelo_secundario.removeRow(0);

			quantidadeDeProdutosEscolhidos
					.setText("Quantidade De Produtos Escolhidos: "
							+ tabelaSecundaria.getModel().getRowCount());

		}

		if (mouseCLick.getSource() == mais
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			VerificandoErros verificandoQuantidade = new VerificandoErros();
			boolean veri = false;

			try {

				veri = verificandoQuantidade.verificando(quantidadeCampo
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

				int row = tabelaDeResultados.getSelectedRow();

				produto.setCodigo(Integer.parseInt((String) tabelaDeResultados
						.getValueAt(row, 0)));

				produto.setDescricao((String) tabelaDeResultados.getValueAt(
						row, 1));

				produto.setCategoria((String) tabelaDeResultados.getValueAt(
						row, 2));

				produto.setDataDeCadastro((String) tabelaDeResultados
						.getValueAt(row, 3));

				produto.setPrecoDeCompra(Double
						.parseDouble((String) tabelaDeResultados.getValueAt(
								row, 4)));

				produto.setPrecoDeVenda(Double
						.parseDouble((String) tabelaDeResultados.getValueAt(
								row, 5)));

				produto.setQuantidade(Integer.parseInt((String) quantidadeCampo
						.getText()));

				produto.setMarca((String) tabelaDeResultados.getValueAt(row, 7));

				produto.setObservacoes((String) tabelaDeResultados.getValueAt(
						row, 8));

				produto.setModelo((String) tabelaDeResultados
						.getValueAt(row, 9));

				produto.setNumeroSerie((String) tabelaDeResultados.getValueAt(
						row, 10));

				produto.setCor((String) tabelaDeResultados.getValueAt(row, 11));

				adicionandoElemento(produto);

				quantidadeCampo.setText("");

				quantidadeDeProdutosEscolhidos
						.setText("Quantidade De Produtos Escolhidos: "
								+ tabelaSecundaria.getModel().getRowCount());

			}

			else {

				quantidadeCampo.setText("");

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

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoProdutos.class
							.getResource("/views/gerenciamento/ger_produtos/png/Ir.png")));

		}

	}

	@Override
	public void mousePressed(MouseEvent mousePress) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseReal) {

	}

	public void adicionandoElemento(Produto produto) {

		modelo_secundario
				.addRow(new Object[] { produto.getCodigo(),
						produto.getDescricao(), produto.getCategoria(),
						produto.getDataDeCadastro(),
						produto.getPrecoDeCompra(), produto.getPrecoDeVenda(),
						produto.getQuantidade(), produto.getMarca(),
						produto.getObservacoes(), produto.getModelo(),
						produto.getNumeroSerie(), produto.getCor() });

	}

}