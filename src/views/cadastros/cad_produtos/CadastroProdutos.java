package views.cadastros.cad_produtos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import modelo.Produto;
import views.outras.capturar_webcam.Capturando;
import views.outras.selecionar_foto.Foto;
import views.principais.tela_de_erro.ErroEncontrado;
import fachada.FachadaProdutos;

public class CadastroProdutos extends JInternalFrame implements MouseListener,
		KeyListener {

	private static final long serialVersionUID = 1L;

	private static Foto foto;
	private JLabel fundo, fitaFoto, codigoTexto, descricaoTexto,
			categoriaTexto, precoDeCompraTexto, precoDeVendaTexto,
			quantidadeTexto, marcaTexto, observacoesTexto, modeloTexto,
			numeroDeSerieTexto, corTexto, dataDeCadastroTexto, webcam,
			produtoSalvo;

	private static JLabel closeFoto;

	private JTextField campoCodigo, campoDescricao, campoPrecoDeCompra,
			campoPrecoDeVenda, campoQuantidade, campoMarca, campoModelo,
			campoNumeroDeSerie, campoCor, campoDataDeCadastro, campoCategoria;

	private JComboBox<String> categorias;

	private JButton salvar, cancelar, limparTudo, salvarCadastrarOutro, mais,
			menos, okCategoria, cancelarCategoria;

	private JTextArea observacoes;
	private JPanel painelPrincipal, painelDescricao;
	private JScrollPane scrollDescricao;

	private Color corDotexto = new Color(139, 139, 139);
	private int verificarDirecao = 0;

	private static int ContadorMudarFoto = 1;
	private static String caminhoDaFoto = "Nenhum Caminho";

	public CadastroProdutos() {

		try {

			// Definindo layout da tela ...

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			// Adicionando componentes a tela JFrame ...

			painelPrincipal = new JPanel();
			painelPrincipal.setLayout(new BorderLayout());

			produtoSalvo = new JLabel(

					new ImageIcon(
							CadastroProdutos.class
									.getResource("/views/cadastros/cad_produtos/jpg/Produto_Salvo.JPG")));

			produtoSalvo.setBounds(0, 0, 927, 538);
			produtoSalvo.setVisible(false);

			produtoSalvo.addMouseListener(this);
			produtoSalvo.addKeyListener(this);

			painelPrincipal.add(produtoSalvo);

			closeFoto = new JLabel("Excluir Foto");

			closeFoto.setBounds(750, 250, 80, 31);
			closeFoto.setVisible(false);

			closeFoto.addMouseListener(this);
			closeFoto.addKeyListener(this);

			painelPrincipal.add(closeFoto);

			cancelarCategoria = new JButton("Cancelar");
			cancelarCategoria.setBounds(329, 206, 80, 33);
			cancelarCategoria
					.setToolTipText("Se Quiser Cancelar o Cadastro Desta Categoria, Clique Aqui");
			cancelarCategoria.setVisible(false);

			cancelarCategoria.addKeyListener(this);
			cancelarCategoria.addMouseListener(this);
			painelPrincipal.add(cancelarCategoria);

			// --------------------------------------------------------------------------------------------------------------

			okCategoria = new JButton("Ok");
			okCategoria.setBounds(275, 206, 50, 33);
			okCategoria.setVisible(false);
			okCategoria
					.setToolTipText("Depois De Ter Digitado a Categoria, Clique Aqui");

			okCategoria.addKeyListener(this);
			okCategoria.addMouseListener(this);
			painelPrincipal.add(okCategoria);

			// --------------------------------------------------------------------------------------------------------------

			campoCategoria = new JTextField();

			campoCategoria.setForeground(corDotexto);
			campoCategoria.setBounds(110, 206, 150, 33);
			campoCategoria.setToolTipText("Digite Agora a Categoria Desejada");
			campoCategoria.setVisible(false);

			campoCategoria.addKeyListener(this);
			campoCategoria.addMouseListener(this);
			painelPrincipal.add(campoCategoria);

			// --------------------------------------------------------------------------------------------------------------

			menos = new JButton("-");
			menos.setBounds(314, 206, 35, 33);
			menos.setToolTipText("Selecione a Categoria Que Deseja Remover e Clique Aqui");

			menos.addKeyListener(this);
			menos.addMouseListener(this);
			painelPrincipal.add(menos);

			// --------------------------------------------------------------------------------------------------------------

			mais = new JButton("+");
			mais.setBounds(275, 206, 35, 33);
			mais.setToolTipText("Adicione Uma Categoria Clicando Aqui");

			mais.addKeyListener(this);
			mais.addMouseListener(this);
			painelPrincipal.add(mais);

			// --------------------------------------------------------------------------------------------------------------

			webcam = new JLabel(

					new ImageIcon(
							CadastroProdutos.class
									.getResource("/views/cadastros/cad_produtos/png/webcam.png")));

			webcam.setBounds(760, 285, 50, 50);
			webcam.setToolTipText("Clique Aqui, Caso Deseje Capturar Uma Foto Pela Sua Webcam");

			webcam.addKeyListener(this);
			webcam.addMouseListener(this);
			painelPrincipal.add(webcam);

			// --------------------------------------------------------------------------------------------------------------

			campoDataDeCadastro = new JTextField();

			campoDataDeCadastro.setEditable(false);
			campoDataDeCadastro.setText(pegandoDataDoSistema());
			campoDataDeCadastro.setForeground(corDotexto);
			campoDataDeCadastro.setBounds(480, 165, 160, 33);
			campoDataDeCadastro.setFocusable(false);

			campoDataDeCadastro
					.setToolTipText("Este Campo Não é Editável, Ele Somente Captura a Data Do Sistema Para Maior Segurança");

			campoDataDeCadastro.addKeyListener(this);
			campoDataDeCadastro.addMouseListener(this);
			painelPrincipal.add(campoDataDeCadastro);

			// --------------------------------------------------------------------------------------------------------------

			dataDeCadastroTexto = new JLabel("Data De Cadastro");

			dataDeCadastroTexto.setForeground(corDotexto);
			dataDeCadastroTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataDeCadastroTexto.setBounds(481, 140, 150, 25);

			dataDeCadastroTexto.addKeyListener(this);
			dataDeCadastroTexto.addMouseListener(this);
			painelPrincipal.add(dataDeCadastroTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoCor = new JTextField();

			campoCor.setForeground(corDotexto);
			campoCor.setBounds(612, 426, 150, 33);

			campoCor.setToolTipText("Digite Aqui a Cor Deste Produto");

			campoCor.addKeyListener(this);
			campoCor.addMouseListener(this);
			painelPrincipal.add(campoCor);

			// --------------------------------------------------------------------------------------------------------------

			corTexto = new JLabel("Cor");

			corTexto.setForeground(corDotexto);
			corTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			corTexto.setBounds(550, 430, 50, 25);

			corTexto.addKeyListener(this);
			corTexto.addMouseListener(this);
			painelPrincipal.add(corTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoNumeroDeSerie = new JTextField();

			campoNumeroDeSerie.setForeground(corDotexto);
			campoNumeroDeSerie.setBounds(679, 386, 223, 33);

			campoNumeroDeSerie
					.setToolTipText("Digite Aqui o Número De Série Deste Produto");

			campoNumeroDeSerie.addKeyListener(this);
			campoNumeroDeSerie.addMouseListener(this);
			painelPrincipal.add(campoNumeroDeSerie);

			// --------------------------------------------------------------------------------------------------------------

			numeroDeSerieTexto = new JLabel("Número De Série");

			numeroDeSerieTexto.setForeground(corDotexto);
			numeroDeSerieTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			numeroDeSerieTexto.setBounds(550, 390, 120, 25);

			numeroDeSerieTexto.addKeyListener(this);
			numeroDeSerieTexto.addMouseListener(this);
			painelPrincipal.add(numeroDeSerieTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoModelo = new JTextField();

			campoModelo.setForeground(corDotexto);
			campoModelo.setBounds(612, 346, 150, 33);

			campoModelo.setToolTipText("Digite Aqui o Modelo Deste Produto");

			campoModelo.addKeyListener(this);
			campoModelo.addMouseListener(this);
			painelPrincipal.add(campoModelo);

			// --------------------------------------------------------------------------------------------------------------

			modeloTexto = new JLabel("Modelo");

			modeloTexto.setForeground(corDotexto);
			modeloTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			modeloTexto.setBounds(550, 350, 100, 25);

			modeloTexto.addKeyListener(this);
			modeloTexto.addMouseListener(this);
			painelPrincipal.add(modeloTexto);

			// --------------------------------------------------------------------------------------------------------------

			observacoesTexto = new JLabel("Observações");

			observacoesTexto.setForeground(corDotexto);
			observacoesTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			observacoesTexto.setBounds(20, 410, 120, 25);

			observacoesTexto.addKeyListener(this);
			observacoesTexto.addMouseListener(this);
			painelPrincipal.add(observacoesTexto);

			// --------------------------------------------------------------------------------------------------------------

			painelDescricao = new JPanel();
			painelDescricao.setLayout(new BorderLayout());
			painelDescricao.setBounds(20, 435, 390, 83);

			observacoes = new JTextArea();

			observacoes.setForeground(corDotexto);
			observacoes.setLineWrap(true);
			observacoes.setBounds(20, 435, 390, 85);

			observacoes.setToolTipText("Digite Aqui Observações Deste Produto");

			scrollDescricao = new JScrollPane(observacoes);
			painelDescricao.add(scrollDescricao);
			painelPrincipal.add(painelDescricao);

			observacoes.addKeyListener(this);
			observacoes.addMouseListener(this);

			// --------------------------------------------------------------------------------------------------------------

			campoMarca = new JTextField();

			campoMarca.setForeground(corDotexto);
			campoMarca.setBounds(338, 336, 167, 33);

			campoMarca.setToolTipText("Digite Aqui a Marca Deste Produto");

			campoMarca.addKeyListener(this);
			campoMarca.addMouseListener(this);
			painelPrincipal.add(campoMarca);

			// --------------------------------------------------------------------------------------------------------------

			marcaTexto = new JLabel("Marca");

			marcaTexto.setForeground(corDotexto);
			marcaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			marcaTexto.setBounds(280, 340, 100, 25);

			marcaTexto.addKeyListener(this);
			marcaTexto.addMouseListener(this);
			painelPrincipal.add(marcaTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoQuantidade = new JTextField();

			campoQuantidade.setForeground(corDotexto);
			campoQuantidade.setBounds(160, 336, 100, 33);

			campoQuantidade
					.setToolTipText("Digite Aqui a Quantidade Do Produto");

			campoQuantidade.addKeyListener(this);
			campoQuantidade.addMouseListener(this);
			painelPrincipal.add(campoQuantidade);

			// --------------------------------------------------------------------------------------------------------------

			quantidadeTexto = new JLabel("Quantidade *");

			quantidadeTexto.setForeground(corDotexto);
			quantidadeTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			quantidadeTexto.setBounds(20, 340, 120, 25);

			quantidadeTexto.addKeyListener(this);
			quantidadeTexto.addMouseListener(this);
			painelPrincipal.add(quantidadeTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoPrecoDeVenda = new JTextField();

			campoPrecoDeVenda.setForeground(corDotexto);
			campoPrecoDeVenda.setBounds(405, 296, 100, 33);

			campoPrecoDeVenda
					.setToolTipText("Digite Aqui o Preço De Venda Do Produto");

			campoPrecoDeVenda.addKeyListener(this);
			campoPrecoDeVenda.addMouseListener(this);
			painelPrincipal.add(campoPrecoDeVenda);

			// --------------------------------------------------------------------------------------------------------------

			precoDeVendaTexto = new JLabel("Preço De Venda *");

			precoDeVendaTexto.setForeground(corDotexto);
			precoDeVendaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			precoDeVendaTexto.setBounds(280, 300, 120, 25);

			precoDeVendaTexto.addKeyListener(this);
			precoDeVendaTexto.addMouseListener(this);
			painelPrincipal.add(precoDeVendaTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoPrecoDeCompra = new JTextField();

			campoPrecoDeCompra.setForeground(corDotexto);
			campoPrecoDeCompra.setBounds(160, 296, 100, 33);

			campoPrecoDeCompra
					.setToolTipText("Digite Aqui o Preço De Compra Do Produto");

			campoPrecoDeCompra.addKeyListener(this);
			campoPrecoDeCompra.addMouseListener(this);
			painelPrincipal.add(campoPrecoDeCompra);

			// --------------------------------------------------------------------------------------------------------------

			precoDeCompraTexto = new JLabel("Preço De Compra *");

			precoDeCompraTexto.setForeground(corDotexto);
			precoDeCompraTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			precoDeCompraTexto.setBounds(20, 300, 130, 25);

			precoDeCompraTexto.addKeyListener(this);
			precoDeCompraTexto.addMouseListener(this);
			painelPrincipal.add(precoDeCompraTexto);

			// --------------------------------------------------------------------------------------------------------------

			categorias = new JComboBox<String>();
			categorias.setBounds(110, 206, 150, 33);

			categorias.addItem("Selecionar ...");
			categorias.setToolTipText("Escolha Aqui a Categoria Desejada");

			ArrayList<String> categoriasBD = new ArrayList<>();
			categoriasBD = FachadaProdutos.listandoCategorias();

			for (int i = 0; i < categoriasBD.size(); i++) {

				categorias.addItem(categoriasBD.get(i));

			}

			categorias.addKeyListener(this);
			categorias.addMouseListener(this);
			painelPrincipal.add(categorias);

			// --------------------------------------------------------------------------------------------------------------

			categoriaTexto = new JLabel("Categoria *");

			categoriaTexto.setForeground(corDotexto);
			categoriaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			categoriaTexto.setBounds(20, 210, 75, 25);

			categoriaTexto.addKeyListener(this);
			categoriaTexto.addMouseListener(this);
			painelPrincipal.add(categoriaTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoDescricao = new JTextField();

			campoDescricao.setForeground(corDotexto);
			campoDescricao.setBounds(110, 165, 300, 33);

			campoDescricao
					.setToolTipText("Digite Aqui a Descrição Deste Produto");

			campoDescricao.addKeyListener(this);
			campoDescricao.addMouseListener(this);
			painelPrincipal.add(campoDescricao);

			// --------------------------------------------------------------------------------------------------------------

			descricaoTexto = new JLabel("Descrição *");

			descricaoTexto.setForeground(corDotexto);
			descricaoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			descricaoTexto.setBounds(20, 170, 85, 25);

			descricaoTexto.addKeyListener(this);
			descricaoTexto.addMouseListener(this);
			painelPrincipal.add(descricaoTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoCodigo = new JTextField();

			campoCodigo.setForeground(corDotexto);
			campoCodigo.setBounds(110, 125, 100, 33);

			campoCodigo.setToolTipText("Digite Aqui o Código Deste Produto");

			campoCodigo.addKeyListener(this);
			campoCodigo.addMouseListener(this);
			painelPrincipal.add(campoCodigo);

			// --------------------------------------------------------------------------------------------------------------

			codigoTexto = new JLabel("Código *");

			codigoTexto.setForeground(corDotexto);
			codigoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			codigoTexto.setBounds(20, 130, 75, 25);

			codigoTexto.addKeyListener(this);
			codigoTexto.addMouseListener(this);
			painelPrincipal.add(codigoTexto);

			// --------------------------------------------------------------------------------------------------------------

			salvar = new JButton("Salvar");
			salvar.setBounds(438, 485, 90, 35);

			salvar.setToolTipText("Clique Aqui Para Salvar Este Produto, Ou Aperte As Teclas (CTRL + S)");

			salvar.addKeyListener(this);
			salvar.addMouseListener(this);
			painelPrincipal.add(salvar);

			// --------------------------------------------------------------------------------------------------------------

			salvarCadastrarOutro = new JButton("Salvar e Cadastrar Outro");
			salvarCadastrarOutro.setBounds(531, 485, 175, 35);

			salvarCadastrarOutro
					.setToolTipText("Clique Aqui Para Salvar Este Produto e Cadastrar Outro, Ou Aperte As Teclas (CTRL + F)");

			salvarCadastrarOutro.addKeyListener(this);
			salvarCadastrarOutro.addMouseListener(this);
			painelPrincipal.add(salvarCadastrarOutro);

			// --------------------------------------------------------------------------------------------------------------

			limparTudo = new JButton("Limpar Tudo");
			limparTudo.setBounds(709, 485, 105, 35);

			limparTudo
					.setToolTipText("Clique Aqui Para Limpar Todos Os Campos, Ou Aperte As Teclas (CTRL + L)");

			limparTudo.addKeyListener(this);
			limparTudo.addMouseListener(this);
			painelPrincipal.add(limparTudo);

			// --------------------------------------------------------------------------------------------------------------

			cancelar = new JButton("Cancelar");

			cancelar.setToolTipText("Clique Aqui Para Cancelar o Cadastro, Ou Aperte a Tecla (ESC)");
			cancelar.setBounds(817, 485, 85, 35);

			cancelar.addKeyListener(this);
			cancelar.addMouseListener(this);
			painelPrincipal.add(cancelar);

			// --------------------------------------------------------------------------------------------------------------

			fitaFoto = new JLabel(

					new ImageIcon(
							CadastroProdutos.class
									.getResource("/views/cadastros/cad_produtos/png/Fita_Foto.PNG")));

			fitaFoto.setBounds(743, 82, 80, 20);
			painelPrincipal.add(fitaFoto);

			foto = new Foto(
					new ImageIcon(
							CadastroProdutos.class
									.getResource("/views/cadastros/cad_produtos/jpg/Foto.jpg")));

			foto.setBounds(702, 94, 157, 157);
			foto.setToolTipText("Selecione Uma Foto Do Seu Computador Clicando Aqui");

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

			foto.addKeyListener(this);
			foto.addMouseListener(this);
			painelPrincipal.add(foto);

			// Adicionando Fundo a Tela ...

			fundo = new JLabel(

					new ImageIcon(
							CadastroProdutos.class
									.getResource("/views/cadastros/cad_produtos/jpg/Fundo.jpg")));

			painelPrincipal.add(fundo);
			campoCodigo.requestFocus();

			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			this.setSize(933, 566);

			this.setContentPane(painelPrincipal);
			this.addKeyListener(this);

			this.setResizable(false);
			this.setTitle("Thallyta Móveis - Cadastrando Novo Produto");

			this.setClosable(true);
			this.setSelected(true);
			this.setVisible(true);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == cancelar) {

			this.dispose();

		}

		if (mouseClick.getSource() == foto) {

			JFileChooser chooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images", "jpg", "gif");

			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);

			int returnVal = chooser.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				caminhoDaFoto = chooser.getSelectedFile().getPath();

				foto.setIcon((new ImageIcon(chooser.getSelectedFile().getPath())));
				closeFoto.setVisible(true);
				foto.repaint();

				ContadorMudarFoto = 2;

			}

		}

		if (produtoSalvo.isVisible() & mouseClick.getSource() == produtoSalvo
				& verificarDirecao == 1) {

			this.dispose();

		}

		if (produtoSalvo.isVisible() & mouseClick.getSource() == produtoSalvo
				& verificarDirecao == 2) {

			produtoSalvo.setVisible(false);

			campoCodigo.setText("");
			campoDescricao.setText("");
			campoPrecoDeCompra.setText("");
			campoPrecoDeVenda.setText("");
			campoQuantidade.setText("");
			campoMarca.setText("");
			campoModelo.setText("");
			campoNumeroDeSerie.setText("");
			campoCor.setText("");
			observacoes.setText("");

			categorias.setSelectedIndex(0);
			campoCodigo.requestFocus();

		}

		else if (mouseClick.getSource() == mais) {

			categorias.setVisible(false);
			mais.setVisible(false);
			menos.setVisible(false);

			campoCategoria.setVisible(true);
			okCategoria.setVisible(true);
			cancelarCategoria.setVisible(true);

			campoCodigo.setFocusable(false);
			campoDescricao.setFocusable(false);
			campoPrecoDeCompra.setFocusable(false);
			campoPrecoDeVenda.setFocusable(false);
			campoQuantidade.setFocusable(false);

			campoMarca.setFocusable(false);
			observacoes.setFocusable(false);
			campoModelo.setFocusable(false);

			campoNumeroDeSerie.setFocusable(false);
			campoCor.setFocusable(false);

			salvar.setEnabled(false);
			salvarCadastrarOutro.setEnabled(false);
			limparTudo.setEnabled(false);
			cancelar.setEnabled(false);

			campoCategoria.requestFocus();

		}

		else if (mouseClick.getSource() == okCategoria) {

			boolean veri = false;

			veri = VerificandoErros_Produtos
					.verificarErros_Categoria(campoCategoria.getText());

			if (veri == true) {

				FachadaProdutos.adicionandoCategoria(campoCategoria.getText());

				categorias.addItem(campoCategoria.getText());
				String itemSelecionado = campoCategoria.getText();
				categorias.setSelectedItem(itemSelecionado);

				categorias.setVisible(true);
				mais.setVisible(true);
				menos.setVisible(true);

				campoCategoria.setVisible(false);
				okCategoria.setVisible(false);
				cancelarCategoria.setVisible(false);

				campoCodigo.setFocusable(true);
				campoDescricao.setFocusable(true);
				campoPrecoDeCompra.setFocusable(true);
				campoPrecoDeVenda.setFocusable(true);
				campoQuantidade.setFocusable(true);

				campoMarca.setFocusable(true);
				observacoes.setFocusable(true);
				campoModelo.setFocusable(true);

				campoNumeroDeSerie.setFocusable(true);
				campoCor.setFocusable(true);

				salvar.setEnabled(true);
				salvarCadastrarOutro.setEnabled(true);
				limparTudo.setEnabled(true);
				cancelar.setEnabled(true);

				campoCategoria.setText("");
				campoPrecoDeCompra.requestFocus();

			}

			else {

				campoCategoria.setText("");
				campoCategoria.requestFocus();

			}

		}

		else if (mouseClick.getSource() == cancelarCategoria) {

			categorias.setVisible(true);
			mais.setVisible(true);
			menos.setVisible(true);

			campoCategoria.setVisible(false);
			okCategoria.setVisible(false);
			cancelarCategoria.setVisible(false);

			campoCodigo.setFocusable(true);
			campoDescricao.setFocusable(true);
			campoPrecoDeCompra.setFocusable(true);
			campoPrecoDeVenda.setFocusable(true);
			campoQuantidade.setFocusable(true);

			campoMarca.setFocusable(true);
			observacoes.setFocusable(true);
			campoModelo.setFocusable(true);

			campoNumeroDeSerie.setFocusable(true);
			campoCor.setFocusable(true);

			salvar.setEnabled(true);
			salvarCadastrarOutro.setEnabled(true);
			limparTudo.setEnabled(true);
			cancelar.setEnabled(true);

			campoCategoria.setText("");
			campoPrecoDeCompra.requestFocus();

		}

		else if (mouseClick.getSource() == menos) {

			String categoriaRemover = (String) categorias.getSelectedItem();

			if (categoriaRemover.equals("Selecionar ...")) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"Não poderá remover esta categoria, ela é padrão do sistema ...",
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

				FachadaProdutos.removendoCategoria(categoriaRemover);

				int indexRemocao = categorias.getSelectedIndex();
				categorias.removeItemAt(indexRemocao);

				categorias.setSelectedIndex(0);

			}

		}

		else if (mouseClick.getSource() == salvar) {

			try {

				VerificandoErros_Produtos verificando = new VerificandoErros_Produtos();

				boolean verif = verificando.verrificandoErros_Produto(
						campoCodigo.getText(), campoDescricao.getText(),
						(String) categorias.getSelectedItem(),
						campoPrecoDeCompra.getText(),
						campoPrecoDeVenda.getText(), campoQuantidade.getText(),
						campoMarca.getText(), campoModelo.getText(),
						campoNumeroDeSerie.getText(), campoCor.getText(),
						observacoes.getText());

				if (verif == true) {

					Produto produto = new Produto();

					produto.setCodigo(Integer.parseInt(campoCodigo.getText()));
					produto.setDescricao(campoDescricao.getText());
					produto.setCategoria((String) categorias.getSelectedItem());
					produto.setDataDeCadastro(campoDataDeCadastro.getText());

					produto.setPrecoDeCompra(Double
							.parseDouble(campoPrecoDeCompra.getText()));

					produto.setPrecoDeVenda(Double
							.parseDouble(campoPrecoDeVenda.getText()));

					produto.setQuantidade(Integer.parseInt(campoQuantidade
							.getText()));

					produto.setMarca(campoMarca.getText());
					produto.setModelo(campoModelo.getText());
					produto.setNumeroSerie(campoNumeroDeSerie.getText());
					produto.setCor(campoCor.getText());
					produto.setObservacoes(observacoes.getText());
					produto.setUrlFoto(caminhoDaFoto);

					FachadaProdutos adc = new FachadaProdutos();
					adc.adicionandoProduto(produto);

					verificarDirecao = 1;
					produtoSalvo.setVisible(true);
					painelPrincipal.setFocusable(false);

				}

			}

			catch (NumberFormatException e) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"Código Do Produto Inválido, Digite Apenas Valores Numéricos ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

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

		}

		else if (mouseClick.getSource() == salvarCadastrarOutro) {

			try {

				VerificandoErros_Produtos verificando = new VerificandoErros_Produtos();

				boolean verif = verificando.verrificandoErros_Produto(
						campoCodigo.getText(), campoDescricao.getText(),
						(String) categorias.getSelectedItem(),
						campoPrecoDeCompra.getText(),
						campoPrecoDeVenda.getText(), campoQuantidade.getText(),
						campoMarca.getText(), campoModelo.getText(),
						campoNumeroDeSerie.getText(), campoCor.getText(),
						observacoes.getText());

				if (verif == true) {

					Produto produto = new Produto();

					produto.setCodigo(Integer.parseInt(campoCodigo.getText()));
					produto.setDescricao(campoDescricao.getText());
					produto.setCategoria((String) categorias.getSelectedItem());
					produto.setDataDeCadastro(campoDataDeCadastro.getText());

					produto.setPrecoDeCompra(Double
							.parseDouble(campoPrecoDeCompra.getText()));

					produto.setPrecoDeVenda(Double
							.parseDouble(campoPrecoDeVenda.getText()));

					produto.setQuantidade(Integer.parseInt(campoQuantidade
							.getText()));

					produto.setMarca(campoMarca.getText());
					produto.setModelo(campoModelo.getText());
					produto.setNumeroSerie(campoNumeroDeSerie.getText());
					produto.setCor(campoCor.getText());
					produto.setObservacoes(observacoes.getText());
					produto.setUrlFoto(caminhoDaFoto);

					FachadaProdutos adc = new FachadaProdutos();
					adc.adicionandoProduto(produto);

					verificarDirecao = 2;
					produtoSalvo.setVisible(true);
					painelPrincipal.setFocusable(false);

				}

			}

			catch (NumberFormatException e) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"Código Do Produto Inválido, Digite Apenas Valores Numéricos ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

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

		}

		else if (mouseClick.getSource() == limparTudo) {

			campoCodigo.setText("");
			campoDescricao.setText("");
			campoPrecoDeCompra.setText("");
			campoPrecoDeVenda.setText("");
			campoQuantidade.setText("");
			campoMarca.setText("");
			campoModelo.setText("");
			campoNumeroDeSerie.setText("");
			campoCor.setText("");
			observacoes.setText("");

			categorias.setSelectedIndex(0);
			campoCodigo.requestFocus();

		}

		else if (mouseClick.getSource() == webcam) {

			new Capturando();

		}

		if (mouseClick.getSource() == closeFoto) {

			foto.setIcon(new ImageIcon(
					CadastroProdutos.class
							.getResource("/views/cadastros/cad_produtos/jpg/Foto_Mouse.jpg")));

			ContadorMudarFoto = 1;
			caminhoDaFoto = "Nenhum Caminho";
			closeFoto.setVisible(false);
			foto.repaint();

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == webcam) {

			webcam.setIcon(new ImageIcon(
					CadastroProdutos.class
							.getResource("/views/cadastros/cad_produtos/png/webcam_mouse.png")));

		}

		else if (mouseEntry.getSource() == foto & ContadorMudarFoto == 1) {

			foto.setIcon(new ImageIcon(
					CadastroProdutos.class
							.getResource("/views/cadastros/cad_produtos/jpg/Foto_Mouse.jpg")));

			this.repaint();

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == webcam) {

			webcam.setIcon(new ImageIcon(
					CadastroProdutos.class
							.getResource("/views/cadastros/cad_produtos/png/webcam.png")));

		}

		else if (mouseExit.getSource() == foto & ContadorMudarFoto == 1) {

			foto.setIcon(new ImageIcon(CadastroProdutos.class
					.getResource("/views/cadastros/cad_produtos/jpg/Foto.jpg")));

			this.repaint();

		}

	}

	@Override
	public void mousePressed(MouseEvent mousePress) {

	}

	@Override
	public void mouseReleased(MouseEvent mouseReal) {

	}

	private String pegandoDataDoSistema() {

		Date now = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String s = df.format(now);

		return s;

	}

	@Override
	public void keyPressed(KeyEvent keyPress) {

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			this.dispose();

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_L && keyPress.isControlDown()) {

			campoCodigo.setText("");
			campoDescricao.setText("");
			campoPrecoDeCompra.setText("");
			campoPrecoDeVenda.setText("");
			campoQuantidade.setText("");
			campoMarca.setText("");
			campoModelo.setText("");
			campoNumeroDeSerie.setText("");
			campoCor.setText("");
			observacoes.setText("");

			categorias.setSelectedIndex(0);
			campoCodigo.requestFocus();

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_S && keyPress.isControlDown()) {

			try {

				VerificandoErros_Produtos verificando = new VerificandoErros_Produtos();

				boolean verif = verificando.verrificandoErros_Produto(
						campoCodigo.getText(), campoDescricao.getText(),
						(String) categorias.getSelectedItem(),
						campoPrecoDeCompra.getText(),
						campoPrecoDeVenda.getText(), campoQuantidade.getText(),
						campoMarca.getText(), campoModelo.getText(),
						campoNumeroDeSerie.getText(), campoCor.getText(),
						observacoes.getText());

				if (verif == true) {

					Produto produto = new Produto();

					produto.setCodigo(Integer.parseInt(campoCodigo.getText()));
					produto.setDescricao(campoDescricao.getText());
					produto.setCategoria((String) categorias.getSelectedItem());
					produto.setDataDeCadastro(campoDataDeCadastro.getText());

					produto.setPrecoDeCompra(Double
							.parseDouble(campoPrecoDeCompra.getText()));

					produto.setPrecoDeVenda(Double
							.parseDouble(campoPrecoDeVenda.getText()));

					produto.setQuantidade(Integer.parseInt(campoQuantidade
							.getText()));

					produto.setMarca(campoMarca.getText());
					produto.setModelo(campoModelo.getText());
					produto.setNumeroSerie(campoNumeroDeSerie.getText());
					produto.setCor(campoCor.getText());
					produto.setObservacoes(observacoes.getText());
					produto.setUrlFoto(caminhoDaFoto);

					FachadaProdutos adc = new FachadaProdutos();
					adc.adicionandoProduto(produto);

					verificarDirecao = 1;
					produtoSalvo.setVisible(true);
					painelPrincipal.setFocusable(false);

				}

			}

			catch (NumberFormatException e) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"Código Do Produto Inválido, Digite Apenas Valores Numéricos ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

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

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_F && keyPress.isControlDown()) {

			try {

				VerificandoErros_Produtos verificando = new VerificandoErros_Produtos();

				boolean verif = verificando.verrificandoErros_Produto(
						campoCodigo.getText(), campoDescricao.getText(),
						(String) categorias.getSelectedItem(),
						campoPrecoDeCompra.getText(),
						campoPrecoDeVenda.getText(), campoQuantidade.getText(),
						campoMarca.getText(), campoModelo.getText(),
						campoNumeroDeSerie.getText(), campoCor.getText(),
						observacoes.getText());

				if (verif == true) {

					Produto produto = new Produto();

					produto.setCodigo(Integer.parseInt(campoCodigo.getText()));
					produto.setDescricao(campoDescricao.getText());
					produto.setCategoria((String) categorias.getSelectedItem());
					produto.setDataDeCadastro(campoDataDeCadastro.getText());

					produto.setPrecoDeCompra(Double
							.parseDouble(campoPrecoDeCompra.getText()));

					produto.setPrecoDeVenda(Double
							.parseDouble(campoPrecoDeVenda.getText()));

					produto.setQuantidade(Integer.parseInt(campoQuantidade
							.getText()));

					produto.setMarca(campoMarca.getText());
					produto.setModelo(campoModelo.getText());
					produto.setNumeroSerie(campoNumeroDeSerie.getText());
					produto.setCor(campoCor.getText());
					produto.setObservacoes(observacoes.getText());
					produto.setUrlFoto(caminhoDaFoto);

					FachadaProdutos adc = new FachadaProdutos();
					adc.adicionandoProduto(produto);

					verificarDirecao = 2;
					produtoSalvo.setVisible(true);
					painelPrincipal.setFocusable(false);

				}

			}

			catch (NumberFormatException e) {

				try {

					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

					JOptionPane
							.showMessageDialog(
									null,
									"Código Do Produto Inválido, Digite Apenas Valores Numéricos ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

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

		}

		if (campoCategoria.isFocusable() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			boolean veri = false;

			veri = VerificandoErros_Produtos
					.verificarErros_Categoria(campoCategoria.getText());

			if (veri == true) {

				FachadaProdutos.adicionandoCategoria(campoCategoria.getText());

				categorias.addItem(campoCategoria.getText());
				String itemSelecionado = campoCategoria.getText();
				categorias.setSelectedItem(itemSelecionado);

				categorias.setVisible(true);
				mais.setVisible(true);
				menos.setVisible(true);

				campoCategoria.setVisible(false);
				okCategoria.setVisible(false);
				cancelarCategoria.setVisible(false);

				campoCodigo.setFocusable(true);
				campoDescricao.setFocusable(true);
				campoPrecoDeCompra.setFocusable(true);
				campoPrecoDeVenda.setFocusable(true);
				campoQuantidade.setFocusable(true);

				campoMarca.setFocusable(true);
				observacoes.setFocusable(true);
				campoModelo.setFocusable(true);

				campoNumeroDeSerie.setFocusable(true);
				campoCor.setFocusable(true);

				salvar.setEnabled(true);
				salvarCadastrarOutro.setEnabled(true);
				limparTudo.setEnabled(true);
				cancelar.setEnabled(true);

				campoCategoria.setText("");
				campoPrecoDeCompra.requestFocus();

			}

			else {

				campoCategoria.setText("");

			}

		}

		if (produtoSalvo.isVisible() == true
				&& keyPress.getKeyCode() == KeyEvent.VK_ENTER) {

			if (verificarDirecao == 1) {

				this.dispose();

			}

			else {

				produtoSalvo.setVisible(false);

				campoCodigo.setText("");
				campoDescricao.setText("");
				campoPrecoDeCompra.setText("");
				campoPrecoDeVenda.setText("");
				campoQuantidade.setText("");
				campoMarca.setText("");
				campoModelo.setText("");
				campoNumeroDeSerie.setText("");
				campoCor.setText("");
				observacoes.setText("");

				categorias.setSelectedIndex(0);
				campoCodigo.requestFocus();

			}

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}
	
	public static void retornandoEnderecoDaWebcam(String caminho){
		
		caminhoDaFoto = caminho;
		foto.setIcon((new ImageIcon(caminhoDaFoto)));
		foto.repaint();
		
		closeFoto.setVisible(true);
		foto.repaint();

		ContadorMudarFoto = 2;
		
	}

}