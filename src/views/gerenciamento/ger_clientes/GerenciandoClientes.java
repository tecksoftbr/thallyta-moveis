package views.gerenciamento.ger_clientes;

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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import fachada.FachadaClientes;
import modelo.Cliente;
import views.principais.tela_de_erro.ErroEncontrado;

public class GerenciandoClientes extends JInternalFrame implements
		MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JPanel painelPrincipal, painelTabela;
	private JLabel fundo, botaoIr, pdfGerado, clienteRemovido;
	private JButton imprimir, gerarPDF, remover, voltar, alterar;
	private JTextField campoBusca;
	private JTable tabelaDeResultados;
	private JScrollPane scrollTabela;

	private Color corDotexto = new Color(139, 139, 139);

	private String[] colunas = new String[] { "Código", "Nome Completo",
			"Sexo", "CPF", "RG", "Orgão Emissor", "Data De Emissão",
			"Nacionalidade", "Rua", "Número", "Complemento", "Bairro",
			"Cidade", "Estado", "CEP", "Telefone - 01", "Telefone - 02",
			"E-mail", "Data De Nascimento", "Estado Civil", "Conjugue", "Pai",
			"Mãe", "Trabalho", "Cargo", "Tempo De Serviço", "Sobre o Trabalho",
			"Observações Adicionais", "Data De Cadastro",
			"Endereço Do Trabalho", "Número - Trabalho",
			"Complemento - Trabalho", "Bairro - Trabalho", "Cidade - Trabalho",
			"Estado - Trabalho", "CEP - Trabalho", "Telefone - Trabalho",
			"Fax - Trabalho", "Email - Trabalho" };

	private ArrayList<Cliente> clientesCadastrados;

	public GerenciandoClientes() throws SQLException {

		// Adicionando um visual nimbus a tela ...

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {

			new ErroEncontrado();

		}

		clientesCadastrados = new ArrayList<>();

		FachadaClientes listando = new FachadaClientes();
		clientesCadastrados = listando.listandoClientes();

		String[][] dados = new String[clientesCadastrados.size()][];
		int i = 0;

		// Percorrendo ...

		for (Cliente cli : clientesCadastrados) {

			dados[i] = new String[] { String.valueOf(cli.getCodigo()),
					cli.getNomeCompleto(), cli.getSexo(), cli.getCpf(),
					String.valueOf(cli.getRg()), cli.getOrgaoEmissor(),
					cli.getDataDeEmissao(), cli.getNacionalidade(),
					cli.getRua(), cli.getNumero(), cli.getComplemento(),
					cli.getBairro(), cli.getCidade(), cli.getEstado(),
					cli.getCep(), cli.getTelefone01(), cli.getTelefone02(),
					cli.getEmail(), cli.getDataDeNascimento(),
					cli.getEstadoCivil(), cli.getConjugue(), cli.getPai(),
					cli.getMae(), cli.getTrabalho(), cli.getCargo(),
					cli.getTempoServico(), cli.getSobreTrabalho(),
					cli.getObservacoesAdicionais(), cli.getDataDeCadastro(),
					cli.getEnderecoOndeTrabalha(), cli.getNumeroTrabalho(),
					cli.getComplementoTrabalho(), cli.getBairroTrabalho(),
					cli.getCidadeTrabalho(), cli.getEstadoTrabalho(),
					cli.getCepTrabalho(), cli.getTelefoneTrabalho(),
					cli.getFaxTrabalho(), cli.getEmailTrabalho() };

			i += 1;

		}

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		// -----------------------------------------------------------------------------------------------------------------------

		painelTabela = new JPanel();
		painelTabela.setLayout(new BorderLayout());
		painelTabela.setBounds(20, 140, 910, 300);

		clienteRemovido = new JLabel();

		clienteRemovido
				.setIcon(new ImageIcon(
						GerenciandoClientes.class
								.getResource("/views/gerenciamento/ger_clientes/jpg/Cliente_Removido.jpg")));

		clienteRemovido.setVisible(false);
		clienteRemovido.setBounds(0, 0, 944, 497);

		clienteRemovido.addMouseListener(this);
		clienteRemovido.addKeyListener(this);

		painelPrincipal.add(clienteRemovido);

		pdfGerado = new JLabel();

		pdfGerado
				.setIcon(new ImageIcon(
						GerenciandoClientes.class
								.getResource("/views/gerenciamento/ger_clientes/jpg/Pdf_Gerado.jpg")));

		pdfGerado.setVisible(false);
		pdfGerado.setBounds(0, 0, 950, 500);

		pdfGerado.addMouseListener(this);
		pdfGerado.addKeyListener(this);

		painelPrincipal.add(painelTabela);

		DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

		tabelaDeResultados = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {

				return false;

			}

		};

		for (int j = 0; j < 39; j++) {

			tabelaDeResultados.getColumnModel().getColumn(j)
					.setPreferredWidth(190);

		}

		TableRowSorter<TableModel> sorter;
		sorter = new TableRowSorter<TableModel>(modelo);
		tabelaDeResultados.setRowSorter(sorter);

		tabelaDeResultados.getTableHeader().setReorderingAllowed(false);

		tabelaDeResultados.addMouseListener(this);
		tabelaDeResultados.addKeyListener(this);

		tabelaDeResultados
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollTabela = new JScrollPane(tabelaDeResultados);
		painelTabela.add(scrollTabela);

		tabelaDeResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// -----------------------------------------------------------------------------------------------------------------------

		campoBusca = new JTextField("Buscar Cliente ...");
		campoBusca.setBounds(710, 49, 150, 22);
		campoBusca.setForeground(corDotexto);
		campoBusca.setBorder(null);

		painelPrincipal.add(campoBusca);

		campoBusca
				.setToolTipText("Digite Aqui o Cliente Desejado e Clique Em Ir, Ou Tecle Enter");

		campoBusca.addMouseListener(this);
		campoBusca.addKeyListener(this);

		voltar = new JButton("Voltar");
		voltar.setToolTipText("Clique Aqui Para Voltar a Tela Principal");
		voltar.setBounds(842, 451, 90, 35);

		voltar.addMouseListener(this);
		voltar.addKeyListener(this);
		painelPrincipal.add(voltar);

		alterar = new JButton("Alterar");
		alterar.setBounds(748, 451, 90, 35);
		alterar.setToolTipText("Clique Aqui Para Alterar o Cliente Selecionado, Ou Tecle (CTRL + A)");

		alterar.addMouseListener(this);
		alterar.addKeyListener(this);
		painelPrincipal.add(alterar);

		imprimir = new JButton("Imprimir");
		imprimir.setToolTipText("Clique Aqui Para Imprimir Uma Lista Dos Clientes Cadastrados");
		imprimir.setBounds(560, 451, 90, 35);

		imprimir.addMouseListener(this);
		imprimir.addKeyListener(this);

		painelPrincipal.add(imprimir);

		// -----------------------------------------------------------------------------------------------------------------------

		gerarPDF = new JButton("Relatório");
		gerarPDF.setToolTipText("Clique Aqui Para Gerar Um PDF De Todos Os Clientes Cadastrados");
		gerarPDF.setBounds(466, 451, 90, 35);

		gerarPDF.addMouseListener(this);
		gerarPDF.addKeyListener(this);

		painelPrincipal.add(gerarPDF);

		// -----------------------------------------------------------------------------------------------------------------------

		remover = new JButton("Remover");

		remover.setToolTipText("Clique Aqui Para Remover o Cliente Selecionado, Ou Tecle (CTRL + R)");
		remover.setBounds(654, 451, 90, 35);

		remover.addMouseListener(this);
		remover.addKeyListener(this);

		painelPrincipal.add(remover);

		botaoIr = new JLabel(

				new ImageIcon(
						GerenciandoClientes.class
								.getResource("/views/gerenciamento/ger_clientes/png/Ir.png")));

		botaoIr.setToolTipText("Clique Aqui Para Pesquisar o Cliente Digitado");
		botaoIr.setBounds(866, 45, 62, 32);

		botaoIr.addMouseListener(this);
		botaoIr.addKeyListener(this);

		painelPrincipal.add(botaoIr);

		fundo = new JLabel(

				new ImageIcon(
						GerenciandoClientes.class
								.getResource("/views/gerenciamento/ger_clientes/jpg/Fundo_Principal.JPG")));

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

		this.setClosable(true);

		try {

			this.setSelected(true);

		}

		catch (PropertyVetoException e1) {

			new ErroEncontrado();

		}

		this.setResizable(false);
		this.setTitle("Thallyta Móveis - Gerenciando Cliente (s)");

		this.setContentPane(painelPrincipal);
		this.setVisible(true);

	}

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == clienteRemovido
				& clienteRemovido.isVisible() == true) {

			try {

				clienteRemovido.setVisible(false);

				FachadaClientes listando = new FachadaClientes();

				clientesCadastrados = listando.listandoClientes();

				// Criando os dados que vão ser incrementados a tabela ...

				String[][] dados = new String[clientesCadastrados.size()][];

				int i = 0;

				// Percorrendo ...

				for (Cliente cli : clientesCadastrados) {

					dados[i] = new String[] { String.valueOf(cli.getCodigo()),
							cli.getNomeCompleto(), cli.getSexo(), cli.getCpf(),
							String.valueOf(cli.getRg()), cli.getOrgaoEmissor(),
							cli.getDataDeEmissao(), cli.getNacionalidade(),
							cli.getRua(), cli.getNumero(),
							cli.getComplemento(), cli.getBairro(),
							cli.getCidade(), cli.getEstado(), cli.getCep(),
							cli.getTelefone01(), cli.getTelefone02(),
							cli.getEmail(), cli.getDataDeNascimento(),
							cli.getEstadoCivil(), cli.getConjugue(),
							cli.getPai(), cli.getMae(), cli.getTrabalho(),
							cli.getCargo(), cli.getTempoServico(),
							cli.getSobreTrabalho(),
							cli.getObservacoesAdicionais(),
							cli.getDataDeCadastro(),
							cli.getEnderecoOndeTrabalha(),
							cli.getNumeroTrabalho(),
							cli.getComplementoTrabalho(),
							cli.getBairroTrabalho(), cli.getCidadeTrabalho(),
							cli.getEstadoTrabalho(), cli.getCepTrabalho(),
							cli.getTelefoneTrabalho(), cli.getFaxTrabalho(),
							cli.getEmailTrabalho() };

					i += 1;

				}

				DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

				tabelaDeResultados.setModel(modelo);

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		if (mouseClick.getSource() == voltar) {

			dispose();

		}

		int coluna = tabelaDeResultados.getSelectedColumn();
		int linha = tabelaDeResultados.getSelectedRow();

		if (mouseClick.getSource() == remover
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			FachadaClientes deletando = new FachadaClientes();

			try {

				deletando.deletandoCliente((String) tabelaDeResultados
						.getValueAt(row, 0));

				clienteRemovido.setVisible(true);

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

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

		if (mouseClick.getSource() == gerarPDF) {

			JFileChooser chooser;
			chooser = new JFileChooser();

			String caminho = "";

			int retorno = chooser.showSaveDialog(null);

			if (retorno == JFileChooser.APPROVE_OPTION) {

				caminho = chooser.getSelectedFile().getAbsolutePath();
				new GerarPDF(clientesCadastrados, caminho);

				pdfGerado.setVisible(true);

			}

		}

		if (mouseClick.getSource() == pdfGerado & pdfGerado.isVisible() == true) {

			pdfGerado.setVisible(false);

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoClientes.class
							.getResource("/views/gerenciamento/ger_clientes/png/Ir_Mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoClientes.class
							.getResource("/views/gerenciamento/ger_clientes/png/Ir.png")));

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

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			this.dispose();

		}

		if (pdfGerado.isVisible() == true
				& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			pdfGerado.setVisible(false);

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

}