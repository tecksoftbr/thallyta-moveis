/*
 * 
 * 
 * Falta fazer nesta tela:
 * 
 * 		- Campo de busca completo.
 * 		- Fazer com que a tela de alteração de produtos fique interna.
 * 
 * 
 */

package views.gerenciamento.ger_contas_pagar;

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

import modelo.ContasPagar;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;
import fachada.FachadaContasPagar;

public class GerenciandoContasPagar extends JInternalFrame implements
		MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	private JPanel painelPrincipal, painelTabela;
	private JLabel fundo, botaoIr, contaRemovida, pdfGerado, totalDeContas,
			valorTotal;

	private JButton voltar, remover, alterar, gerarPDF, imprimir;

	private JTextField campoBusca;
	private JTable tabelaDeResultados;
	private JScrollPane scrollTabela;

	// Rodapé das colunas que vão ser incrementadas na tabela ...

	private String[] colunas = new String[] { "Tipo", "Número Do Documento",
			"Data De Entrada", "Data De Vencimento", "Valor", "Origem" };

	// Alguma (s) cor (es) ...

	private Color corDotexto = new Color(139, 139, 139);

	// Criando um array da classe collection para a mais a frente fazer a busca
	// e adicionar elementos do banco de dados ...

	private ArrayList<ContasPagar> contas = new ArrayList<>();

	// Contrutor da classe ...

	public GerenciandoContasPagar() throws SQLException {

		// Adicionando um visual nimbus a tela ...

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {

			new ErroEncontrado();

		}

		FachadaContasPagar chamandoMetodo_Listagem = new FachadaContasPagar();
		contas = chamandoMetodo_Listagem.listandoContas();

		String[][] dados = new String[contas.size()][];
		int i = 0;

		// Percorrendo ...

		for (ContasPagar con : contas) {

			dados[i] = new String[] { con.getTipoConta(),
					con.getNumeroDocumento(), con.getDataEntrada(),
					con.getDataVencimento(), String.valueOf((con.getValor())),
					con.getOrigem() };

			i += 1;

		}

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		pdfGerado = new JLabel();

		pdfGerado
				.setIcon(new ImageIcon(
						GerenciandoContasPagar.class
								.getResource("/views/gerenciamento/ger_contas_pagar/jpg/PDF_Gerado.jpg")));

		pdfGerado.setVisible(false);
		pdfGerado.setBounds(0, 0, 950, 500);

		pdfGerado.addMouseListener(this);
		pdfGerado.addKeyListener(this);

		painelPrincipal.add(pdfGerado);

		// -----------------------------------------------------------------------------------------------------------------------

		contaRemovida = new JLabel(

				new ImageIcon(
						GerenciandoContasPagar.class
								.getResource("/views/gerenciamento/ger_contas_pagar/jpg/Conta_Removida.jpg")));

		contaRemovida.setBounds(0, 0, 950, 500);

		contaRemovida.addMouseListener(this);
		contaRemovida.addKeyListener(this);

		contaRemovida.setVisible(false);
		painelPrincipal.add(contaRemovida);

		campoBusca = new JTextField("Buscar Conta ...");
		campoBusca.setBounds(710, 49, 150, 22);
		campoBusca.setForeground(corDotexto);
		campoBusca.setBorder(null);

		campoBusca
				.setToolTipText("Digite Aqui a Conta Desejada e Clique Em Ir, Ou Tecle Enter");

		campoBusca.addMouseListener(this);
		campoBusca.addKeyListener(this);

		painelPrincipal.add(campoBusca);

		// Ainda adicionando ...

		voltar = new JButton("Voltar");
		voltar.setToolTipText("Clique Aqui Para Voltar a Tela Principal");
		voltar.setBounds(842, 451, 90, 35);

		voltar.addMouseListener(this);
		voltar.addKeyListener(this);
		painelPrincipal.add(voltar);

		alterar = new JButton("Alterar");
		alterar.setBounds(748, 451, 90, 35);
		alterar.setToolTipText("Clique Aqui Para Alterar a Conta Selecionada, Ou Tecle (CTRL + A)");

		alterar.addMouseListener(this);
		alterar.addKeyListener(this);
		painelPrincipal.add(alterar);

		// -----------------------------------------------------------------------------------------------------------------------

		totalDeContas = new JLabel("Total De Contas Cadastradas: "
				+ contas.size());

		totalDeContas.setForeground(corDotexto);

		totalDeContas.setBounds(20, 451, 300, 35);
		totalDeContas.setFont(new Font("Dialog", Font.PLAIN, 15));

		painelPrincipal.add(totalDeContas);

		// -----------------------------------------------------------------------------------------------------------------------

		double valor = 0.0;

		for (int j = 0; j < contas.size(); j++) {

			valor = valor + contas.get(j).getValor();

		}

		valorTotal = new JLabel("Valor Total: " + valor);

		valorTotal.setForeground(corDotexto);

		valorTotal.setBounds(290, 451, 300, 35);
		valorTotal.setFont(new Font("Dialog", Font.PLAIN, 15));

		painelPrincipal.add(valorTotal);

		// -----------------------------------------------------------------------------------------------------------------------

		imprimir = new JButton("Imprimir");
		imprimir.setToolTipText("Clique Aqui Para Imprimir Uma Lista Das Contas Cadastrados");
		imprimir.setBounds(560, 451, 90, 35);

		imprimir.addMouseListener(this);
		imprimir.addKeyListener(this);

		painelPrincipal.add(imprimir);

		// -----------------------------------------------------------------------------------------------------------------------

		gerarPDF = new JButton("Relatório");
		gerarPDF.setToolTipText("Clique Aqui Para Gerar Um PDF De Todas As Contas Cadastrados");
		gerarPDF.setBounds(466, 451, 90, 35);

		gerarPDF.addMouseListener(this);
		gerarPDF.addKeyListener(this);

		painelPrincipal.add(gerarPDF);

		// -----------------------------------------------------------------------------------------------------------------------

		remover = new JButton("Remover");

		remover.setToolTipText("Clique Aqui Para Remover a Conta Selecionada, Ou Tecle (CTRL + R)");
		remover.setBounds(654, 451, 90, 35);

		remover.addMouseListener(this);
		remover.addKeyListener(this);

		painelPrincipal.add(remover);

		botaoIr = new JLabel(

				new ImageIcon(
						GerenciandoContasPagar.class
								.getResource("/views/gerenciamento/ger_contas_pagar/png/Ir.png")));

		botaoIr.setToolTipText("Clique Aqui Para Pesquisar a Conta Digitada");
		botaoIr.setBounds(866, 45, 62, 32);

		// Ainda adicionando ...

		botaoIr.addMouseListener(this);
		botaoIr.addKeyListener(this);
		painelPrincipal.add(botaoIr);

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

		tabelaDeResultados.getColumnModel().getColumn(0).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(2).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(3).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(4).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(5).setPreferredWidth(190);

		tabelaDeResultados
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabelaDeResultados.addMouseListener(this);
		tabelaDeResultados.addKeyListener(this);

		tabelaDeResultados.getTableHeader().setReorderingAllowed(false);

		scrollTabela = new JScrollPane(tabelaDeResultados);
		painelTabela.add(scrollTabela);

		fundo = new JLabel(

				new ImageIcon(
						GerenciandoContasPagar.class
								.getResource("/views/gerenciamento/ger_contas_pagar/jpg/Fundo.jpg")));

		tabelaDeResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

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
		this.setTitle("Thallyta Móveis - Gerenciando Conta (s)");

		this.setContentPane(painelPrincipal);
		this.setClosable(true);

		try {

			this.setSelected(true);

		}

		catch (PropertyVetoException e1) {

			new ErroEncontrado();

		}

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

		if (contaRemovida.isVisible() == true
				& mouseClick.getSource() == contaRemovida) {

			FachadaContasPagar chamandoMetodo_Listagem = new FachadaContasPagar();

			try {

				contas = chamandoMetodo_Listagem.listandoContas();

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			String[][] dados = new String[contas.size()][];
			int i = 0;

			// Percorrendo ...

			for (ContasPagar con : contas) {

				dados[i] = new String[] { con.getTipoConta(),
						con.getNumeroDocumento(), con.getDataEntrada(),
						con.getDataVencimento(),
						String.valueOf((con.getValor())), con.getOrigem() };

				i += 1;

			}

			DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

			tabelaDeResultados.setModel(modelo);
			contaRemovida.setVisible(false);

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

		}

		if (mouseClick.getClickCount() == 2
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();
			this.dispose();

			TelaPrincipal.AbrindoAlterarContas(
					(String) tabelaDeResultados.getValueAt(row, 0),
					(String) tabelaDeResultados.getValueAt(row, 1),
					(String) tabelaDeResultados.getValueAt(row, 2),
					(String) tabelaDeResultados.getValueAt(row, 3),
					(String) tabelaDeResultados.getValueAt(row, 4),
					(String) tabelaDeResultados.getValueAt(row, 5));

		}

		if (mouseClick.getSource() == alterar
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();
			this.dispose();

			TelaPrincipal.AbrindoAlterarContas(
					(String) tabelaDeResultados.getValueAt(row, 0),
					(String) tabelaDeResultados.getValueAt(row, 1),
					(String) tabelaDeResultados.getValueAt(row, 2),
					(String) tabelaDeResultados.getValueAt(row, 3),
					(String) tabelaDeResultados.getValueAt(row, 4),
					(String) tabelaDeResultados.getValueAt(row, 5));

		}

		if (mouseClick.getSource() == gerarPDF) {

			JFileChooser chooser;
			chooser = new JFileChooser();

			String caminho = "";

			int retorno = chooser.showSaveDialog(null);

			if (retorno == JFileChooser.APPROVE_OPTION) {

				caminho = chooser.getSelectedFile().getAbsolutePath();

				new GerarPDF(contas, caminho);
				pdfGerado.setVisible(true);

			}

			else {

			}

		}

		if (pdfGerado.isVisible() == true & mouseClick.getSource() == pdfGerado) {

			pdfGerado.setVisible(false);

		}

		if (mouseClick.getSource() == remover
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();
			FachadaContasPagar passandoNumeroDaConta = new FachadaContasPagar();

			try {

				passandoNumeroDaConta
						.removerContaPagar((String) tabelaDeResultados
								.getValueAt(row, 1));

				this.setFocusable(false);
				contaRemovida.setVisible(true);

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoContasPagar.class
							.getResource("/views/gerenciamento/ger_contas_pagar/png/Ir_Mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoContasPagar.class
							.getResource("/views/gerenciamento/ger_contas_pagar/png/Ir.png")));

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

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& contaRemovida.isVisible() == true) {

			FachadaContasPagar chamandoMetodo_Listagem = new FachadaContasPagar();

			try {

				contas = chamandoMetodo_Listagem.listandoContas();

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			String[][] dados = new String[contas.size()][];
			int i = 0;

			// Percorrendo ...

			for (ContasPagar con : contas) {

				dados[i] = new String[] { con.getTipoConta(),
						con.getNumeroDocumento(), con.getDataEntrada(),
						con.getDataVencimento(),
						String.valueOf((con.getValor())), con.getOrigem() };

				i += 1;

			}

			DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

			tabelaDeResultados.setModel(modelo);
			contaRemovida.setVisible(false);

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& pdfGerado.isVisible() == true) {

			pdfGerado.setVisible(false);

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			this.dispose();

		}

		int coluna = tabelaDeResultados.getSelectedColumn();
		int linha = tabelaDeResultados.getSelectedRow();

		if (keyPress.getKeyCode() == KeyEvent.VK_A && keyPress.isControlDown()
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();
			this.dispose();

			TelaPrincipal.AbrindoAlterarContas(
					(String) tabelaDeResultados.getValueAt(row, 0),
					(String) tabelaDeResultados.getValueAt(row, 1),
					(String) tabelaDeResultados.getValueAt(row, 2),
					(String) tabelaDeResultados.getValueAt(row, 3),
					(String) tabelaDeResultados.getValueAt(row, 4),
					(String) tabelaDeResultados.getValueAt(row, 5));

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_R && keyPress.isControlDown()
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();
			FachadaContasPagar passandoNumeroDaConta = new FachadaContasPagar();

			try {

				passandoNumeroDaConta
						.removerContaPagar((String) tabelaDeResultados
								.getValueAt(row, 1));

				this.setFocusable(false);
				contaRemovida.setVisible(true);

			}

			catch (SQLException e) {

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