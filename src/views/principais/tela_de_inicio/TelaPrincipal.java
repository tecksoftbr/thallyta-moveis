package views.principais.tela_de_inicio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import modelo.Cliente;
import modelo.ContasPagar;
import modelo.Lembrete;
import views.alterando.contas.AlterandoConta;
import views.alterando.fornecedores.AlterandoFornecedor;
import views.alterando.lembretes.AlterandoLembrete;
import views.alterando.produtos.AlterandoProduto;
import views.alterando.usuarios.AlterandoUsuario;
import views.cadastros.cad_clientes.CadastrarClientes;
import views.cadastros.cad_contas.CadastroContas;
import views.cadastros.cad_fornecedores.CadastroFornecedores;
import views.cadastros.cad_lembretes.CadastroLembretes;
import views.cadastros.cad_produtos.CadastroProdutos;
import views.cadastros.cad_usuarios.CadastroUsuarios;
import views.compras.GerenciandoCompras;
import views.gerenciamento.ger_clientes.GerenciandoClientes;
import views.gerenciamento.ger_contas_pagar.GerenciandoContasPagar;
import views.gerenciamento.ger_fornecedores.GerenciandoFornecedores;
import views.gerenciamento.ger_lembretes.GerenciandoLembretes;
import views.gerenciamento.ger_produtos.GerenciandoProdutos;
import views.gerenciamento.ger_usuarios.GerenciandoUsuarios;
import views.outras.desenvolvedores.Construindo;
import views.outras.sobre_software.Sobre;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_login.TelaDeLogin;
import views.relatorios.vendas.RelatorioDeVendas;
import views.utilitarios.BlocoDeNotas;
import views.utilitarios.Calculadora;
import views.utilitarios.TecladoVirtual;
import views.vendas.realizar_venda.RealizandoPrincipal;
import fachada.FachadaAvisos;

public class TelaPrincipal extends JFrame implements ActionListener,
		MouseListener {

	private static final long serialVersionUID = 1L;

	// Objetos e variáveis de apoio ...

	private JMenuBar barraDeMenu;

	private JMenu estoque, vendas, cadastros, relatorios, utilitarios,
			gerenciar, ajuda, sair;

	private JMenuItem compras, realizarVenda, cadProduto, cadFornecedor,
			cadCliente, cadContas, cadLembretes, vendasRealizadas, calculadora,
			bloquearSistema, gerProdutos, gerFornecedores, gerClientes,
			gerContas, gerLembretes, blocoDeNotas, SobreSoftware,
			desenvolvedores, cadUsuarios, gerUsuarios, tecladoVirtual;

	static JDesktopPane painelDeElementos;

	private JLabel fundo;

	static DateFormat df = new SimpleDateFormat("HH:mm");
	static String horaSistema = df.format(new Date());

	// Sistema de avisos ...

	JPanel painelTabelaContas;
	static JTable contasPagar_Tabela;

	private static String[] colunas_tabelaConta = new String[] { "Tipo",
			"Número Do Documento", "Data De Entrada", "Data De Vencimento",
			"Valor", "Origem" };

	static ArrayList<modelo.ContasPagar> contas = new ArrayList<>();
	static String[][] dadosTabelaContas = new String[contas.size()][];

	static DefaultTableModel modeloTabelaContas = new DefaultTableModel(
			dadosTabelaContas, colunas_tabelaConta);

	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------

	JLabel fundoTabela;

	JPanel painelTabelaLembretes;
	static JTable lembretes_Tabela;

	private static String[] colunas_tabelaLembretes = new String[] { "Código",
			"Título", "Descrição", "Data De Cadastro", "Data De Aviso" };

	static ArrayList<Lembrete> lembretes = new ArrayList<>();
	static String[][] dadosTabelaLembrete = new String[lembretes.size()][];

	static DefaultTableModel modeloTabelaLembretes = new DefaultTableModel(
			dadosTabelaLembrete, colunas_tabelaLembretes);

	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------

	JPanel painelTabelaAniversarios;
	static JTable aniversarios_Tabela;

	private static String[] colunas_tabelaAniversário = new String[] {
			"Código", "Nome Completo", "Telefone - 01", "Telefone - 02",
			"Email" };

	static ArrayList<Cliente> clientesAniversariantes = new ArrayList<>();
	static String[][] dadosTabelaCliente = new String[clientesAniversariantes
			.size()][];

	static DefaultTableModel modeloTabelaAniversario = new DefaultTableModel(
			dadosTabelaCliente, colunas_tabelaAniversário);

	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------------------------------------------------------------------------

	JLabel botaoEventos;
	int contadorAbertura = 1;

	// Inicializando construtor da classe ...

	public TelaPrincipal() {

		try {

			// Aparência do windows ...

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			// Inicializando e lançando uma barra de menu e seus atributos ...

			barraDeMenu = new JMenuBar();
			this.setJMenuBar(barraDeMenu);

			// --------------------------------------------------------------------------------------------------------------

			estoque = new JMenu("Estoque");
			barraDeMenu.add(estoque);

			compras = new JMenuItem("Gerenciar (Compras)");

			compras.addActionListener(this);
			estoque.add(compras);

			// --------------------------------------------------------------------------------------------------------------

			vendas = new JMenu("Vendas");
			barraDeMenu.add(vendas);

			realizarVenda = new JMenuItem("Realizar Venda");

			realizarVenda.addActionListener(this);

			vendas.add(realizarVenda);

			// --------------------------------------------------------------------------------------------------------------

			cadastros = new JMenu("Cadastros");
			barraDeMenu.add(cadastros);

			cadProduto = new JMenuItem("Produto");
			cadastros.add(cadProduto);

			cadProduto.addActionListener(this);

			cadFornecedor = new JMenuItem("Fornecedor");
			cadFornecedor.addActionListener(this);

			cadastros.add(cadFornecedor);

			cadCliente = new JMenuItem("Cliente");

			cadCliente.addActionListener(this);
			cadastros.add(cadCliente);

			cadContas = new JMenuItem("Contas a Pagar");
			cadastros.add(cadContas);

			cadContas.addActionListener(this);

			cadLembretes = new JMenuItem("Lembretes");

			cadastros.add(cadLembretes);

			cadLembretes.addActionListener(this);

			cadUsuarios = new JMenuItem("Usuários");

			cadUsuarios.addActionListener(this);
			cadastros.add(cadUsuarios);

			// --------------------------------------------------------------------------------------------------------------

			relatorios = new JMenu("Relatórios");
			barraDeMenu.add(relatorios);

			vendasRealizadas = new JMenuItem("Vendas Realizadas");
			vendasRealizadas.addActionListener(this);

			relatorios.add(vendasRealizadas);

			// --------------------------------------------------------------------------------------------------------------

			utilitarios = new JMenu("Utilitários");
			barraDeMenu.add(utilitarios);

			calculadora = new JMenuItem("Calculadora");
			utilitarios.add(calculadora);

			calculadora.addActionListener(this);

			blocoDeNotas = new JMenuItem("Bloco De Notas");
			blocoDeNotas.addActionListener(this);

			utilitarios.add(blocoDeNotas);

			tecladoVirtual = new JMenuItem("Teclado Virtual");
			utilitarios.add(tecladoVirtual);

			tecladoVirtual.addActionListener(this);

			bloquearSistema = new JMenuItem("Bloquear Sistema");

			bloquearSistema.addActionListener(this);
			utilitarios.add(bloquearSistema);

			// --------------------------------------------------------------------------------------------------------------

			gerenciar = new JMenu("Gerenciar");
			barraDeMenu.add(gerenciar);

			gerProdutos = new JMenuItem("Produtos");

			gerProdutos.addActionListener(this);
			gerenciar.add(gerProdutos);

			gerFornecedores = new JMenuItem("Fornecedores");
			gerFornecedores.addActionListener(this);

			gerenciar.add(gerFornecedores);

			gerClientes = new JMenuItem("Clientes");

			gerClientes.addActionListener(this);
			gerenciar.add(gerClientes);

			gerContas = new JMenuItem("Contas a Pagar");
			gerContas.addActionListener(this);

			gerenciar.add(gerContas);

			gerLembretes = new JMenuItem("Lembretes");
			gerLembretes.addActionListener(this);

			gerenciar.add(gerLembretes);

			gerUsuarios = new JMenuItem("Usuários");

			gerUsuarios.addActionListener(this);
			gerenciar.add(gerUsuarios);

			// --------------------------------------------------------------------------------------------------------------

			ajuda = new JMenu("Ajuda");
			barraDeMenu.add(ajuda);

			SobreSoftware = new JMenuItem("Sobre o Software");

			SobreSoftware.addActionListener(this);
			ajuda.add(SobreSoftware);

			desenvolvedores = new JMenuItem("Desenvolvedores");

			desenvolvedores.addActionListener(this);

			ajuda.add(desenvolvedores);

			sair = new JMenu("Sair");
			barraDeMenu.add(sair);

			painelDeElementos = new JDesktopPane();
			painelDeElementos.setBackground(UIManager
					.getColor("Button.background"));

			botaoEventos = new JLabel(
					new ImageIcon(
							TelaPrincipal.class
									.getResource("/views/principais/tela_de_inicio/png/Eventos.png")));

			botaoEventos.addMouseListener(this);

			botaoEventos.setBounds(540, 665, 107, 28);
			painelDeElementos.add(botaoEventos);

			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------

			painelTabelaContas = new JPanel();
			painelTabelaContas.setLayout(new BorderLayout());
			painelTabelaContas.setBounds(200, 770, 791, 115);

			painelDeElementos.add(painelTabelaContas);

			contasPagar_Tabela = new JTable(modeloTabelaContas) {

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {

					return false;

				}

			};

			contasPagar_Tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			for (int i = 0; i < 6; i++) {

				contasPagar_Tabela.getColumnModel().getColumn(i)
						.setPreferredWidth(250);

			}

			JScrollPane scrollTabelaContas = new JScrollPane(contasPagar_Tabela);
			painelTabelaContas.add(scrollTabelaContas);

			contasPagar_Tabela
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			contasPagar_Tabela.addMouseListener(this);

			contasPagar_Tabela.getTableHeader().setReorderingAllowed(false);

			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------

			painelTabelaLembretes = new JPanel();
			painelTabelaLembretes.setLayout(new BorderLayout());
			painelTabelaLembretes.setBounds(200, 1110, 791, 115);

			painelDeElementos.add(painelTabelaLembretes);

			lembretes_Tabela = new JTable(modeloTabelaLembretes) {

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {

					return false;

				}

			};

			lembretes_Tabela
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			lembretes_Tabela.addMouseListener(this);

			lembretes_Tabela.getTableHeader().setReorderingAllowed(false);

			lembretes_Tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			for (int i = 0; i < 5; i++) {

				lembretes_Tabela.getColumnModel().getColumn(i)
						.setPreferredWidth(250);

			}

			JScrollPane scrollTabelaLembretes = new JScrollPane(
					lembretes_Tabela);

			painelTabelaLembretes.add(scrollTabelaLembretes);

			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------

			painelTabelaAniversarios = new JPanel();
			painelTabelaAniversarios.setLayout(new BorderLayout());
			painelTabelaAniversarios.setBounds(200, 1110, 791, 115);

			painelDeElementos.add(painelTabelaAniversarios);

			aniversarios_Tabela = new JTable(modeloTabelaAniversario) {

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column) {

					return false;

				}

			};

			aniversarios_Tabela
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			aniversarios_Tabela.addMouseListener(this);

			aniversarios_Tabela.getTableHeader().setReorderingAllowed(false);

			aniversarios_Tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			for (int i = 0; i < 5; i++) {

				aniversarios_Tabela.getColumnModel().getColumn(i)
						.setPreferredWidth(250);

			}

			JScrollPane scrollTabelaAniversarios = new JScrollPane(
					aniversarios_Tabela);

			painelTabelaAniversarios.add(scrollTabelaAniversarios);

			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------
			// --------------------------------------------------------------------------------------------

			fundoTabela = new JLabel();

			fundoTabela
					.setIcon(new ImageIcon(
							TelaPrincipal.class
									.getResource("/views/principais/tela_de_inicio/png/Fundo_Tabelas.PNG")));

			fundoTabela.setBounds(138, 700, 900, 580);
			painelDeElementos.add(fundoTabela);

			fundo = new JLabel(
					new ImageIcon(
							TelaPrincipal.class
									.getResource("/views/principais/tela_de_inicio/jpg/Fundo.jpg")));

			fundo.setBounds(0, -5, 2125, 1245);
			painelDeElementos.add(fundo);

			// Propriedades da tela ...

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// this.setExtendedState(MAXIMIZED_BOTH);

			this.setSize(1200, 750);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setContentPane(painelDeElementos);
			this.setTitle("Thallyta Móveis - Bem Vindo (a)");
			this.setVisible(true);

			adicionandoValoresAsTabelas();

		}

		catch (Exception e) {

			e.getStackTrace();

		}

	}

	// Eventos para ir para outras telas ...

	@Override
	public void actionPerformed(ActionEvent eventAction) {

		if (eventAction.getSource() == cadLembretes) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new CadastroLembretes();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == cadContas) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new CadastroContas();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == cadFornecedor) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new CadastroFornecedores();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == cadUsuarios) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new CadastroUsuarios();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == gerUsuarios) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new GerenciandoUsuarios();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == gerLembretes) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new GerenciandoLembretes();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == gerContas) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new GerenciandoContasPagar();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == gerProdutos) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new GerenciandoProdutos();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == gerFornecedores) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new GerenciandoFornecedores();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == vendasRealizadas) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new RelatorioDeVendas();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						e.printStackTrace();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == gerClientes) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new GerenciandoClientes();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == realizarVenda) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new RealizandoPrincipal();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2 - 20);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == tecladoVirtual) {

			try {

				new TecladoVirtual();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == calculadora) {

			try {

				new Calculadora();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == desenvolvedores) {

			try {

				this.dispose();
				new Construindo();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == blocoDeNotas) {

			try {

				new BlocoDeNotas();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == cadProduto) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new CadastroProdutos();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == SobreSoftware) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new Sobre();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == bloquearSistema) {

			try {

				this.dispose();
				new TelaDeLogin();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == cadCliente) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new CadastrarClientes();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight();

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (Exception e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			catch (ExceptionInInitializerError e) {

				new ErroEncontrado();

			}

		}

		else if (eventAction.getSource() == compras) {

			try {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

					JInternalFrame jif;

					try {

						jif = new GerenciandoCompras();

						int lDesk = this.getWidth();
						int aDesk = this.getHeight() - 40;

						int lIFrame = jif.getWidth();
						int aIFrame = jif.getHeight();

						jif.setLocation(lDesk / 2 - lIFrame / 2, aDesk / 2
								- aIFrame / 2);

						painelDeElementos.add(jif);

						jif.setSelected(true);
						jif.moveToFront();

					}

					catch (ClassNotFoundException | InstantiationException
							| IllegalAccessException
							| UnsupportedLookAndFeelException e) {

						new ErroEncontrado();

					}

				}

				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e1) {

					new ErroEncontrado();

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			catch (PropertyVetoException e) {

				new ErroEncontrado();

			}

		}

	}

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (contadorAbertura == 1 & mouseClick.getSource() == botaoEventos) {

			botaoEventos.setBounds(540, 85, 107, 28);
			painelTabelaContas.setBounds(200, 190, 791, 115);
			painelTabelaLembretes.setBounds(200, 360, 791, 120);
			painelTabelaAniversarios.setBounds(200, 530, 791, 115);
			fundoTabela.setBounds(138, 120, 912, 580);

			contadorAbertura++;

		}

		else {

			botaoEventos.setBounds(540, 665, 107, 28);
			painelTabelaContas.setBounds(200, 770, 791, 115);
			painelTabelaLembretes.setBounds(200, 940, 791, 120);
			painelTabelaAniversarios.setBounds(200, 1110, 791, 115);
			fundoTabela.setBounds(138, 700, 912, 580);

			contadorAbertura--;

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == botaoEventos) {

			botaoEventos
					.setIcon(new ImageIcon(
							TelaPrincipal.class
									.getResource("/views/principais/tela_de_inicio/png/Eventos_Mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == botaoEventos) {

			botaoEventos
					.setIcon(new ImageIcon(
							TelaPrincipal.class
									.getResource("/views/principais/tela_de_inicio/png/Eventos.png")));

		}

	}

	@Override
	public void mousePressed(MouseEvent mousePress) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseReal) {

	}

	public static void adicionandoValoresAsTabelas() throws SQLException {

		FachadaAvisos avisos = new FachadaAvisos();

		contas = new ArrayList<>();
		contas = avisos.contasHoje();

		// Percorrendo ...

		if (contas.size() == 0) {

		}

		else {

			String[][] dados = new String[contas.size()][];
			int i = 0;

			// Percorrendo ...

			for (ContasPagar con : contas) {

				dados[i] = new String[] { con.getTipoConta(),
						con.getNumeroDocumento(), con.getDataEntrada(),
						con.getDataVencimento(),
						String.valueOf(con.getValor()), con.getOrigem() };

				i += 1;

			}

			DefaultTableModel modeloTC = new DefaultTableModel(dados,
					colunas_tabelaConta);

			contasPagar_Tabela.setModel(modeloTC);

			for (int iSize = 0; iSize < 6; iSize++) {

				contasPagar_Tabela.getColumnModel().getColumn(iSize)
						.setPreferredWidth(250);

			}

		}

		contasPagar_Tabela.setBackground(Color.white);

		// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		lembretes = new ArrayList<>();
		lembretes = avisos.lembretesHoje();

		if (lembretes.size() == 0) {

		}

		else {

			String[][] dados = new String[lembretes.size()][];
			int i = 0;

			// Percorrendo ...

			for (Lembrete lem : lembretes) {

				dados[i] = new String[] { String.valueOf(lem.getCodigo()),
						lem.getTitulo(), lem.getDescricao(),
						lem.getData_de_cadastro(), lem.getData_de_aviso() };

				i += 1;

			}

			DefaultTableModel modeloTL = new DefaultTableModel(dados,
					colunas_tabelaLembretes);

			lembretes_Tabela.setModel(modeloTL);

			for (int iSize = 0; iSize < 5; iSize++) {

				lembretes_Tabela.getColumnModel().getColumn(iSize)
						.setPreferredWidth(250);

			}
		}

		lembretes_Tabela.setBackground(Color.white);

		// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		clientesAniversariantes = new ArrayList<>();
		clientesAniversariantes = avisos.aniversariosHoje();

		if (clientesAniversariantes.size() == 0) {

		}

		else {
			

			for (modelo.Cliente cliente : clientesAniversariantes) {

				modeloTabelaAniversario.addRow(new Object[] {
						cliente.getCodigo(), cliente.getNomeCompleto(),
						cliente.getTelefone01(), cliente.getTelefone02(),
						cliente.getEmail() });

			}

		}
		
		

		aniversarios_Tabela.setBackground(Color.white);

	}

	// Abrindo telas de alteração ...

	public static void AbrindoAlterarProdutos(String codigo, String descricao,
			String categoria, String dataDeCadastro, String precoDeCompra,
			String precoDeVenda, String quantidade, String marca,
			String observacoes, String modelo, String numeroDeSerie,
			String cor, String urlImagem) {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				JInternalFrame jif;

				try {

					jif = new AlterandoProduto(codigo, descricao, categoria,
							dataDeCadastro, precoDeCompra, precoDeVenda,
							quantidade, marca, observacoes, modelo,
							numeroDeSerie, cor, urlImagem);

					int lIFrame = jif.getWidth();
					int aIFrame = jif.getHeight();

					jif.setLocation(1200 / 2 - lIFrame / 2,
							750 / 2 - aIFrame / 2);

					painelDeElementos.add(jif);

					jif.setSelected(true);
					jif.moveToFront();

				}

				catch (Exception e) {

					e.printStackTrace();

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		catch (ExceptionInInitializerError e) {

			new ErroEncontrado();

		}

	}

	public static void chamandoNovamenteGerenciarProdutos() {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				JInternalFrame jif;

				try {

					jif = new GerenciandoProdutos();

					int lIFrame = jif.getWidth();
					int aIFrame = jif.getHeight();

					jif.setLocation(1200 / 2 - lIFrame / 2,
							750 / 2 - aIFrame / 2);

					painelDeElementos.add(jif);

					jif.setSelected(true);
					jif.moveToFront();

				}

				catch (Exception e) {

					new ErroEncontrado();

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		catch (ExceptionInInitializerError e) {

			new ErroEncontrado();

		}

	}

	// --------------------------------------------------------------------------------------------------------------------------------------

	public static void AbrindoAlterarFornecedores(String codigo,
			String empresa, String cep, String rua, String bairro,
			String cidade, String estado, String telefone, String fax,
			String email, String site, String observacoes,
			String dataDeCadastro, String urlImagem) {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				JInternalFrame jif;

				try {

					jif = new AlterandoFornecedor(codigo, empresa, cep, rua,
							bairro, cidade, estado, telefone, fax, email, site,
							observacoes, dataDeCadastro, urlImagem);

					int lIFrame = jif.getWidth();
					int aIFrame = jif.getHeight();

					jif.setLocation(1200 / 2 - lIFrame / 2,
							750 / 2 - aIFrame / 2);

					painelDeElementos.add(jif);

					jif.setSelected(true);
					jif.moveToFront();

				}

				catch (Exception e) {

					new ErroEncontrado();

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		catch (ExceptionInInitializerError e) {

			new ErroEncontrado();

		}

	}

	public static void chamandoNovamenteGerenciarFornecedores() {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				JInternalFrame jif;

				try {

					jif = new GerenciandoFornecedores();

					int lIFrame = jif.getWidth();
					int aIFrame = jif.getHeight();

					jif.setLocation(1200 / 2 - lIFrame / 2,
							750 / 2 - aIFrame / 2);

					painelDeElementos.add(jif);

					jif.setSelected(true);
					jif.moveToFront();

				}

				catch (Exception e) {

					new ErroEncontrado();

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		catch (ExceptionInInitializerError e) {

			new ErroEncontrado();

		}

	}

	// --------------------------------------------------------------------------------------------------------------------------------------

	public static void AbrindoAlterarLembretes(String codigo, String titulo,
			String descricao, String dataDeCadastro, String dataDeAviso) {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				JInternalFrame jif;

				try {

					jif = new AlterandoLembrete(codigo, titulo, descricao,
							dataDeCadastro, dataDeAviso);

					int lIFrame = jif.getWidth();
					int aIFrame = jif.getHeight();

					jif.setLocation(1200 / 2 - lIFrame / 2,
							750 / 2 - aIFrame / 2);

					painelDeElementos.add(jif);

					jif.setSelected(true);
					jif.moveToFront();

				}

				catch (Exception e) {

					new ErroEncontrado();

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		catch (ExceptionInInitializerError e) {

			new ErroEncontrado();

		}

	}

	public static void chamandoNovamenteGerenciarLembretes() {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				JInternalFrame jif;

				try {

					jif = new GerenciandoLembretes();

					int lIFrame = jif.getWidth();
					int aIFrame = jif.getHeight();

					jif.setLocation(1200 / 2 - lIFrame / 2,
							750 / 2 - aIFrame / 2);

					painelDeElementos.add(jif);

					jif.setSelected(true);
					jif.moveToFront();

				}

				catch (Exception e) {

					new ErroEncontrado();

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		catch (ExceptionInInitializerError e) {

			new ErroEncontrado();

		}

	}

	// --------------------------------------------------------------------------------------------------------------------------------------

	public static void AbrindoAlterarUsuarios(String nomeCompleto,
			String dataDeCadastro, String apelido, String senha,
			String perguntaSecreta, String respostaSecreta, String sobreMim) {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				JInternalFrame jif;

				try {

					jif = new AlterandoUsuario(nomeCompleto, dataDeCadastro,
							apelido, senha, perguntaSecreta, respostaSecreta,
							sobreMim);

					int lIFrame = jif.getWidth();
					int aIFrame = jif.getHeight();

					jif.setLocation(1200 / 2 - lIFrame / 2,
							750 / 2 - aIFrame / 2);

					painelDeElementos.add(jif);

					jif.setSelected(true);
					jif.moveToFront();

				}

				catch (Exception e) {

					new ErroEncontrado();

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		catch (ExceptionInInitializerError e) {

			new ErroEncontrado();

		}

	}

	public static void chamandoNovamenteGerenciarUsuarios() {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				JInternalFrame jif;

				try {

					jif = new GerenciandoUsuarios();

					int lIFrame = jif.getWidth();
					int aIFrame = jif.getHeight();

					jif.setLocation(1200 / 2 - lIFrame / 2,
							750 / 2 - aIFrame / 2);

					painelDeElementos.add(jif);

					jif.setSelected(true);
					jif.moveToFront();

				}

				catch (Exception e) {

					new ErroEncontrado();

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		catch (ExceptionInInitializerError e) {

			new ErroEncontrado();

		}

	}

	// --------------------------------------------------------------------------------------------------------------------------------------

	public static void AbrindoAlterarContas(String tipo,
			String numeroDocumento, String dataDeEntrada,
			String dataDeVencimento, String Valor, String origem) {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				JInternalFrame jif;

				try {

					jif = new AlterandoConta(tipo, numeroDocumento,
							dataDeEntrada, dataDeVencimento, Valor, origem);

					int lIFrame = jif.getWidth();
					int aIFrame = jif.getHeight();

					jif.setLocation(1200 / 2 - lIFrame / 2,
							750 / 2 - aIFrame / 2);

					painelDeElementos.add(jif);

					jif.setSelected(true);
					jif.moveToFront();

				}

				catch (Exception e) {

					new ErroEncontrado();

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		catch (ExceptionInInitializerError e) {

			new ErroEncontrado();

		}

	}

	public static void chamandoNovamenteGerenciarContas() {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				JInternalFrame jif;

				try {

					jif = new GerenciandoContasPagar();

					int lIFrame = jif.getWidth();
					int aIFrame = jif.getHeight();

					jif.setLocation(1200 / 2 - lIFrame / 2,
							750 / 2 - aIFrame / 2);

					painelDeElementos.add(jif);

					jif.setSelected(true);
					jif.moveToFront();

				}

				catch (Exception e) {

					new ErroEncontrado();

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e1) {

				new ErroEncontrado();

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		catch (ExceptionInInitializerError e) {

			new ErroEncontrado();

		}

	}

}