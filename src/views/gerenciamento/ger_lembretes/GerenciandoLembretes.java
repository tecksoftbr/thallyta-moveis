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

package views.gerenciamento.ger_lembretes;

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

import modelo.Lembrete;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;
import fachada.FachadaLembretes;

// Classe de gerenciamento dos dados da empresa ...

public class GerenciandoLembretes extends JInternalFrame implements
		MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	// Objetos e variáveis de apoio ...

	private JLabel fundo, botaoIr, lembreteRemovido, pdfGerado;
	private JPanel painelPrincipal, painelTabela;
	private JTable tabelaDeResultados;

	JTextField campoBusca;

	private JButton alterar, remover, voltar, imprimir, gerarPDF;
	private JScrollPane scrollTabela;

	// Rodapé das colunas que vão ser incrementadas na tabela ...

	private String[] colunas = new String[] { "Código", "Título", "Descrição",
			"Data De Cadastro", "Data De Aviso" };

	// Alguma (s) cor (es) ...

	private Color corDotexto = new Color(139, 139, 139);

	// Criando um array da classe collection para a mais a frente fazer a busca
	// e adicionar elementos do banco de dados ...

	private ArrayList<Lembrete> lembretes = new ArrayList<>();

	// Contrutor da classe ...

	public GerenciandoLembretes() throws SQLException {

		// Adicionando um visual nimbus a tela ...

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		}

		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {

			new ErroEncontrado();

		}

		// Chamando a fachada, para a mesma se comunicar com o banco de dados e
		// assim resgatar todos os valores de lembretes ...

		FachadaLembretes pegarLembretes = new FachadaLembretes();
		lembretes = pegarLembretes.listandoLembretes();

		// Criando os dados que vão ser incrementados a tabela ...

		String[][] dados = new String[lembretes.size()][];
		int i = 0;

		// Percorrendo ...

		for (Lembrete lem : lembretes) {

			dados[i] = new String[] { String.valueOf(lem.getCodigo()),
					lem.getTitulo(), lem.getDescricao(),
					lem.getData_de_cadastro(), lem.getData_de_aviso() };

			i += 1;

		}

		// Adicionando componentes a tela e paineis ...

		painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());

		lembreteRemovido = new JLabel(

				new ImageIcon(
						GerenciandoLembretes.class
								.getResource("/views/gerenciamento/ger_lembretes/jpg/Lembrete_Removido.JPG")));

		lembreteRemovido.setBounds(0, 0, 950, 500);
		lembreteRemovido.setVisible(false);

		lembreteRemovido.addMouseListener(this);
		lembreteRemovido.addKeyListener(this);

		painelPrincipal.add(lembreteRemovido);

		pdfGerado = new JLabel();
		pdfGerado
				.setIcon(new ImageIcon(
						GerenciandoLembretes.class
								.getResource("/views/gerenciamento/ger_lembretes/jpg/PDF_Gerado.jpg")));

		pdfGerado.setVisible(false);
		pdfGerado.setBounds(0, 0, 950, 500);

		pdfGerado.addMouseListener(this);
		pdfGerado.addKeyListener(this);

		painelPrincipal.add(pdfGerado);

		campoBusca = new JTextField("Buscar Lembrete ...");
		campoBusca.setBounds(710, 49, 150, 22);
		campoBusca.setForeground(corDotexto);
		campoBusca.setBorder(null);

		campoBusca
				.setToolTipText("Digite Aqui o Lembrete Desejado e Clique Em Ir, Ou Tecle Enter");

		campoBusca.addMouseListener(this);
		campoBusca.addKeyListener(this);
		painelPrincipal.add(campoBusca);

		// Ainda adicionando ...

		voltar = new JButton("Voltar");
		voltar.setToolTipText("Clique Aqui Para Voltar a Tela Principal, Ou Tecle (ESC)");
		voltar.setBounds(842, 451, 90, 35);

		voltar.addMouseListener(this);
		voltar.addKeyListener(this);
		painelPrincipal.add(voltar);

		imprimir = new JButton("Imprimir");
		imprimir.setToolTipText("Clique Aqui Para Imprimir Uma Lista Dos Lembretes Cadastrados");
		imprimir.setBounds(560, 451, 90, 35);

		imprimir.addMouseListener(this);
		imprimir.addKeyListener(this);

		painelPrincipal.add(imprimir);

		// -----------------------------------------------------------------------------------------------------------------------

		gerarPDF = new JButton("Relatório");
		gerarPDF.setToolTipText("Clique Aqui Para Gerar Um PDF De Todos Os Lembretes Cadastrados");
		gerarPDF.setBounds(466, 451, 90, 35);

		gerarPDF.addMouseListener(this);
		gerarPDF.addKeyListener(this);

		painelPrincipal.add(gerarPDF);

		alterar = new JButton("Alterar");
		alterar.setBounds(748, 451, 90, 35);
		alterar.setToolTipText("Clique Aqui Para Alterar o Lembrete Selecionado, Ou Tecle (CTRL + A)");

		alterar.addMouseListener(this);
		alterar.addKeyListener(this);
		painelPrincipal.add(alterar);

		// Ainda adicionando ...

		remover = new JButton("Remover");

		remover.setToolTipText("Clique Aqui Para Remover o Lembrete Selecionado, Ou Tecle (CTRL + R)");
		remover.setBounds(654, 451, 90, 35);

		remover.addMouseListener(this);
		remover.addKeyListener(this);
		painelPrincipal.add(remover);

		botaoIr = new JLabel(

				new ImageIcon(
						GerenciandoLembretes.class
								.getResource("/views/gerenciamento/ger_fornecedores/png/Ir.png")));

		botaoIr.setToolTipText("Clique Aqui Para Pesquisar o Lembrete Digitado");
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

		tabelaDeResultados.getTableHeader().setReorderingAllowed(false);

		tabelaDeResultados
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabelaDeResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabelaDeResultados.getColumnModel().getColumn(0).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(2).setPreferredWidth(250);
		tabelaDeResultados.getColumnModel().getColumn(3).setPreferredWidth(190);
		tabelaDeResultados.getColumnModel().getColumn(4).setPreferredWidth(190);

		tabelaDeResultados.addMouseListener(this);
		tabelaDeResultados.addKeyListener(this);

		scrollTabela = new JScrollPane(tabelaDeResultados);
		painelTabela.add(scrollTabela);

		fundo = new JLabel(

				new ImageIcon(
						GerenciandoLembretes.class
								.getResource("/views/gerenciamento/ger_lembretes/jpg/Fundo.jpg")));

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
		this.setTitle("Thallyta Móveis - Gerenciando Lembrete (s)");

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
				new GerarPDF(lembretes, caminho);

				pdfGerado.setVisible(true);

			}

			else {

			}

		}

		if (pdfGerado.isVisible() == true & mouseClick.getSource() == pdfGerado) {

			pdfGerado.setVisible(false);

		}

		if (mouseClick.getSource() == lembreteRemovido
				& lembreteRemovido.isVisible() == true) {

			FachadaLembretes pegarLembretes = new FachadaLembretes();
			lembreteRemovido.setVisible(false);

			try {

				lembretes = pegarLembretes.listandoLembretes();

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			// Criando os dados que vão ser incrementados a tabela ...

			String[][] dados = new String[lembretes.size()][];
			int i = 0;

			// Percorrendo ...

			for (Lembrete lem : lembretes) {

				dados[i] = new String[] { String.valueOf(lem.getCodigo()),
						lem.getTitulo(), lem.getDescricao(),
						lem.getData_de_cadastro(), lem.getData_de_aviso() };

				i += 1;

			}

			DefaultTableModel modelo = new DefaultTableModel(dados, colunas);
			tabelaDeResultados.setModel(modelo);

			tabelaDeResultados.getColumnModel().getColumn(0)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(1)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(2)
					.setPreferredWidth(250);
			tabelaDeResultados.getColumnModel().getColumn(3)
					.setPreferredWidth(190);
			tabelaDeResultados.getColumnModel().getColumn(4)
					.setPreferredWidth(190);

		}

		if (mouseClick.getSource() == remover
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			FachadaLembretes removendo = new FachadaLembretes();

			removendo.removendoLembrete((String) tabelaDeResultados.getValueAt(
					row, 0));

			lembreteRemovido.setVisible(true);

		}

		if (mouseClick.getSource() == alterar
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			this.dispose();
			int row = tabelaDeResultados.getSelectedRow();

			String codigo = (String) tabelaDeResultados.getValueAt(row, 0);

			String Titulo = (String) tabelaDeResultados.getValueAt(row, 1);

			String Descricao = (String) tabelaDeResultados.getValueAt(row, 2);

			String dataDeCadastro = (String) tabelaDeResultados.getValueAt(row,
					3);

			String dataDeAviso = (String) tabelaDeResultados.getValueAt(row, 4);

			TelaPrincipal.AbrindoAlterarLembretes(codigo, Titulo, Descricao,
					dataDeCadastro, dataDeAviso);

		}

		if (mouseClick.getClickCount() == 2
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			this.dispose();
			int row = tabelaDeResultados.getSelectedRow();

			String codigo = (String) tabelaDeResultados.getValueAt(row, 0);

			String Titulo = (String) tabelaDeResultados.getValueAt(row, 1);

			String Descricao = (String) tabelaDeResultados.getValueAt(row, 2);

			String dataDeCadastro = (String) tabelaDeResultados.getValueAt(row,
					3);

			String dataDeAviso = (String) tabelaDeResultados.getValueAt(row, 4);

			TelaPrincipal.AbrindoAlterarLembretes(codigo, Titulo, Descricao,
					dataDeCadastro, dataDeAviso);

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoLembretes.class
							.getResource("/views/gerenciamento/ger_lembretes/png/Ir_Mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == botaoIr) {

			botaoIr.setIcon(new ImageIcon(
					GerenciandoLembretes.class
							.getResource("/views/gerenciamento/ger_lembretes/png/Ir.png")));

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

		if (pdfGerado.isFocusable() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			pdfGerado.setVisible(false);

		}

		if (lembreteRemovido.isFocusable() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			lembreteRemovido.setVisible(false);

		}

		int coluna = tabelaDeResultados.getSelectedColumn();
		int linha = tabelaDeResultados.getSelectedRow();

		if (keyPress.getKeyCode() == KeyEvent.VK_R && keyPress.isControlDown()
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			int row = tabelaDeResultados.getSelectedRow();

			FachadaLembretes removendo = new FachadaLembretes();
			removendo.removendoLembrete((String) tabelaDeResultados.getValueAt(
					row, 0));

			lembreteRemovido.setVisible(true);

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_A && keyPress.isControlDown()
				&& tabelaDeResultados.isCellSelected(linha, coluna)) {

			this.dispose();
			int row = tabelaDeResultados.getSelectedRow();

			String codigo = (String) tabelaDeResultados.getValueAt(row, 0);

			String Titulo = (String) tabelaDeResultados.getValueAt(row, 1);

			String Descricao = (String) tabelaDeResultados.getValueAt(row, 2);

			String dataDeCadastro = (String) tabelaDeResultados.getValueAt(row,
					3);

			String dataDeAviso = (String) tabelaDeResultados.getValueAt(row, 4);

			TelaPrincipal.AbrindoAlterarLembretes(codigo, Titulo, Descricao,
					dataDeCadastro, dataDeAviso);

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

}