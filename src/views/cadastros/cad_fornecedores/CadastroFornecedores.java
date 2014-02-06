package views.cadastros.cad_fornecedores;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import modelo.Fornecedor;
import fachada.FachadaFornecedores;
import views.cadastros.cad_produtos.CadastroProdutos;
import views.outras.capturar_webcam.Capturando;
import views.outras.selecionar_foto.Foto;
import views.principais.tela_de_erro.ErroEncontrado;

public class CadastroFornecedores extends JInternalFrame implements
		MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	// Objetos e vari�veis de apoio ...

	private Foto foto;
	private JPanel painelPrincipal, painelDescricao;

	private JLabel fundo, fitaFoto, webcam, codigoTexto, empresaTexto,
			cepTexto, ruaTexto, bairroTexto, cidadeTexto, estadoTexto,
			textoTelefone, textoFax, textoEmail, textoSite,
			dataDeCadastroTexto, observacoesTexto, fornecedorSalvo, closeFoto;

	private JTextField campoCodigo, campoEmpresa, campoCep, campoRua,
			campoBairro, campoCidade, campoTelefone, campoFax, campoEmail,
			campoSite, campoDataDeCadastro;

	private JComboBox<String> estados;
	private JTextArea campoObservacoes;
	private JScrollPane scrollDescricao;

	private JButton salvar, cancelar, salvarCadastrarOutro, limparTudo;

	private Color corDotexto = new Color(139, 139, 139);
	private int verificandoDirecao = 0;

	private int ContadorMudarFoto = 1;
	private String caminhoDaFoto = "Nenhum Caminho";

	public CadastroFornecedores() {

		try {

			// Definindo layout da tela ...

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

			painelPrincipal = new JPanel();
			painelPrincipal.setLayout(new BorderLayout());
			painelPrincipal.addKeyListener(this);

			// Adicionando componentes a tela ...

			fornecedorSalvo = new JLabel(

					new ImageIcon(
							CadastroFornecedores.class
									.getResource("/views/cadastros/cad_fornecedores/jpg/Fornecedor_Salvo.JPG")));

			fornecedorSalvo.setBounds(0, 0, 927, 538);
			fornecedorSalvo.setVisible(false);

			fornecedorSalvo.addMouseListener(this);
			fornecedorSalvo.addKeyListener(this);

			painelPrincipal.add(fornecedorSalvo);

			closeFoto = new JLabel("Excluir Foto");

			closeFoto.setBounds(750, 250, 80, 31);
			closeFoto.setVisible(false);

			closeFoto.addMouseListener(this);
			closeFoto.addKeyListener(this);

			painelPrincipal.add(closeFoto);

			painelDescricao = new JPanel();
			painelDescricao.setLayout(new BorderLayout());
			painelDescricao.setBounds(529, 395, 371, 73);

			campoObservacoes = new JTextArea();

			campoObservacoes.setForeground(corDotexto);
			campoObservacoes.setBounds(529, 395, 373, 73);

			campoObservacoes
					.setToolTipText("Clique Aqui e Adicione Uma Observa��o a Este Fornecedor");

			scrollDescricao = new JScrollPane(campoObservacoes);

			campoObservacoes.addKeyListener(this);

			painelDescricao.add(scrollDescricao);
			painelPrincipal.add(painelDescricao);

			observacoesTexto = new JLabel("Observa��es");

			observacoesTexto.setForeground(corDotexto);
			observacoesTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			observacoesTexto.setBounds(530, 370, 100, 25);

			observacoesTexto.addKeyListener(this);
			painelPrincipal.add(observacoesTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoDataDeCadastro = new JTextField();

			campoDataDeCadastro.setForeground(corDotexto);
			campoDataDeCadastro.setText(pegandoDataDoSistema());
			campoDataDeCadastro.setFocusable(false);

			campoDataDeCadastro.setEditable(false);
			campoDataDeCadastro.setBounds(548, 235, 120, 33);

			campoDataDeCadastro
					.setToolTipText("Este Campo N�o � Edit�vel, Ele Somente Captura a Data Do Sistema Para Maior Seguran�a");

			campoDataDeCadastro.addKeyListener(this);
			painelPrincipal.add(campoDataDeCadastro);

			// --------------------------------------------------------------------------------------------------------------

			dataDeCadastroTexto = new JLabel("Data De Cadastro");

			dataDeCadastroTexto.setForeground(corDotexto);
			dataDeCadastroTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataDeCadastroTexto.setBounds(520, 210, 120, 25);

			dataDeCadastroTexto.addKeyListener(this);
			painelPrincipal.add(dataDeCadastroTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoSite = new JTextField();

			campoSite.setForeground(corDotexto);
			campoSite.setBounds(110, 435, 391, 33);

			campoSite
					.setToolTipText("Clique Aqui e Adicione Um Site a Este Fornecedor");

			campoSite.addKeyListener(this);
			painelPrincipal.add(campoSite);

			// --------------------------------------------------------------------------------------------------------------

			campoEmail = new JTextField();

			campoEmail.setForeground(corDotexto);
			campoEmail.setBounds(110, 395, 391, 33);

			campoEmail
					.setToolTipText("Clique Aqui e Adicione Um E-mail a Este Fornecedor");

			campoEmail.addKeyListener(this);
			painelPrincipal.add(campoEmail);

			// --------------------------------------------------------------------------------------------------------------

			textoSite = new JLabel("Site");

			textoSite.setForeground(corDotexto);
			textoSite.setFont(new Font("Dialog", Font.PLAIN, 15));
			textoSite.setBounds(20, 440, 75, 25);

			textoSite.addKeyListener(this);
			painelPrincipal.add(textoSite);

			// --------------------------------------------------------------------------------------------------------------

			textoEmail = new JLabel("E-mail");

			textoEmail.setForeground(corDotexto);
			textoEmail.setFont(new Font("Dialog", Font.PLAIN, 15));
			textoEmail.setBounds(20, 400, 75, 25);

			textoEmail.addKeyListener(this);
			painelPrincipal.add(textoEmail);

			// --------------------------------------------------------------------------------------------------------------

			campoFax = new JFormattedTextField(setMascara("(##) ####-####"));

			campoFax.setForeground(corDotexto);
			campoFax.setBounds(326, 355, 175, 33);

			campoFax.setToolTipText("Clique Aqui e Adicione Um Fax a Este Fornecedor");

			campoFax.addKeyListener(this);
			painelPrincipal.add(campoFax);

			// --------------------------------------------------------------------------------------------------------------

			textoFax = new JLabel("Fax");

			textoFax.setForeground(corDotexto);
			textoFax.setFont(new Font("Dialog", Font.PLAIN, 15));
			textoFax.setBounds(293, 360, 50, 25);

			textoFax.addKeyListener(this);
			painelPrincipal.add(textoFax);

			// --------------------------------------------------------------------------------------------------------------

			campoTelefone = new JFormattedTextField(
					setMascara("(##) ####-####"));

			campoTelefone.setForeground(corDotexto);
			campoTelefone.setBounds(110, 355, 175, 33);

			campoTelefone
					.setToolTipText("Clique Aqui e Adicione Um Telefone a Este Fornecedor");

			campoTelefone.addKeyListener(this);
			painelPrincipal.add(campoTelefone);

			// --------------------------------------------------------------------------------------------------------------

			textoTelefone = new JLabel("Telefone");

			textoTelefone.setForeground(corDotexto);
			textoTelefone.setFont(new Font("Dialog", Font.PLAIN, 15));
			textoTelefone.setBounds(20, 360, 75, 25);

			textoTelefone.addKeyListener(this);
			painelPrincipal.add(textoTelefone);

			// --------------------------------------------------------------------------------------------------------------

			salvar = new JButton("Salvar");
			salvar.setBounds(438, 485, 90, 35);

			salvar.setToolTipText("Clique Aqui Para Salvar Este Fornecedor, Ou Aperte As Teclas (CTRL + S)");

			salvar.addKeyListener(this);
			salvar.addMouseListener(this);
			painelPrincipal.add(salvar);

			// --------------------------------------------------------------------------------------------------------------

			salvarCadastrarOutro = new JButton("Salvar e Cadastrar Outro");
			salvarCadastrarOutro.setBounds(531, 485, 175, 35);

			salvarCadastrarOutro
					.setToolTipText("Clique Aqui Para Salvar Este Fornecedor e Cadastrar Outro, Ou Aperte As Teclas (CTRL + F)");

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

			estadoTexto = new JLabel("Estado");

			estadoTexto.setForeground(corDotexto);
			estadoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			estadoTexto.setBounds(293, 300, 75, 25);

			estadoTexto.addKeyListener(this);
			painelPrincipal.add(estadoTexto);

			// --------------------------------------------------------------------------------------------------------------

			estados = new JComboBox<String>();
			estados.setBounds(347, 295, 154, 33);

			estados.addItem("Selecione ...");
			estados.addItem("Acre");
			estados.addItem("Alagoas");
			estados.addItem("Amap�");
			estados.addItem("Amazonas");
			estados.addItem("Bahia");
			estados.addItem("Cear�");
			estados.addItem("Distrito Federal");
			estados.addItem("Goi�s");
			estados.addItem("Esp�rito Santo");
			estados.addItem("Maranh�o");
			estados.addItem("Mato Grosso");
			estados.addItem("Mato Grosso Do Sul");
			estados.addItem("Minas Gerais");
			estados.addItem("Par�");
			estados.addItem("Paraiba");
			estados.addItem("Paran�");
			estados.addItem("Pernambuco");
			estados.addItem("Piau�");
			estados.addItem("Rio De Janeiro");
			estados.addItem("Rio Grande Do Norte");
			estados.addItem("Rio Grande Do Sul");
			estados.addItem("Rond�nia");
			estados.addItem("Ror�ima");
			estados.addItem("S�o Paulo");
			estados.addItem("Santa Catarina");
			estados.addItem("Sergipe");
			estados.addItem("Tocantins");

			estados.setToolTipText("Clique Aqui e Adicione Um Estado a Este Fornecedor");

			estados.addKeyListener(this);
			painelPrincipal.add(estados);

			// --------------------------------------------------------------------------------------------------------------

			campoCidade = new JTextField();

			campoCidade.setForeground(corDotexto);
			campoCidade.setBounds(110, 295, 175, 33);

			campoCidade
					.setToolTipText("Caso Queira Digitar Uma Informa��o De Cidade Para o Fornecedor, Clique Aqui");

			campoCidade.addKeyListener(this);
			painelPrincipal.add(campoCidade);

			// --------------------------------------------------------------------------------------------------------------

			cidadeTexto = new JLabel("Cidade");

			cidadeTexto.setForeground(corDotexto);
			cidadeTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			cidadeTexto.setBounds(20, 300, 75, 25);

			cidadeTexto.addKeyListener(this);
			painelPrincipal.add(cidadeTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoBairro = new JTextField();

			campoBairro.setForeground(corDotexto);
			campoBairro.setBounds(371, 255, 130, 33);

			campoBairro
					.setToolTipText("Continue Adicionando Informa��es Sobre o Bairro Do Fornecedor, Clicando Aqui");

			campoBairro.addKeyListener(this);
			painelPrincipal.add(campoBairro);

			// --------------------------------------------------------------------------------------------------------------

			bairroTexto = new JLabel("Bairro");

			bairroTexto.setForeground(corDotexto);
			bairroTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			bairroTexto.setBounds(322, 260, 75, 25);

			bairroTexto.addKeyListener(this);
			painelPrincipal.add(bairroTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoRua = new JTextField();

			campoRua.setForeground(corDotexto);
			campoRua.setBounds(110, 255, 200, 33);

			campoRua.setToolTipText("Aqui Voc� Adicionar� Um Endere�o, Come�ando Pela Rua");

			campoRua.addKeyListener(this);
			painelPrincipal.add(campoRua);

			// --------------------------------------------------------------------------------------------------------------

			ruaTexto = new JLabel("Rua");

			ruaTexto.setForeground(corDotexto);
			ruaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			ruaTexto.setBounds(20, 260, 75, 25);

			ruaTexto.addKeyListener(this);
			painelPrincipal.add(ruaTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoCep = new JFormattedTextField(setMascara("#####-###"));

			campoCep.setForeground(corDotexto);
			campoCep.setBounds(110, 215, 120, 33);

			campoCep.setToolTipText("Digite Aqui o CEP Do Fornecedor Que Est� Cadastrando e Tecle Enter Ou Clique Em Verificar");

			campoCep.addKeyListener(this);
			painelPrincipal.add(campoCep);

			// --------------------------------------------------------------------------------------------------------------

			cepTexto = new JLabel("CEP");

			cepTexto.setForeground(corDotexto);
			cepTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			cepTexto.setBounds(20, 220, 75, 25);

			cepTexto.addKeyListener(this);
			painelPrincipal.add(cepTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoEmpresa = new JTextField();

			campoEmpresa.setForeground(corDotexto);
			campoEmpresa.setBounds(110, 165, 391, 33);

			campoEmpresa
					.setToolTipText("Digite Aqui a Descri��o Da Empresa Do Fornecedor Que Est� Cadastrando");

			campoEmpresa.addKeyListener(this);
			painelPrincipal.add(campoEmpresa);

			// --------------------------------------------------------------------------------------------------------------

			empresaTexto = new JLabel("Empresa *");

			empresaTexto.setForeground(corDotexto);
			empresaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			empresaTexto.setBounds(20, 170, 75, 25);

			empresaTexto.addKeyListener(this);
			painelPrincipal.add(empresaTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoCodigo = new JTextField();

			campoCodigo.setForeground(corDotexto);
			campoCodigo.setBounds(110, 125, 100, 33);

			campoCodigo
					.setToolTipText("Aqui Voc� Adicionar� Um C�digo Para Este Fornecedor");

			campoCodigo.addKeyListener(this);
			painelPrincipal.add(campoCodigo);

			// --------------------------------------------------------------------------------------------------------------

			codigoTexto = new JLabel("C�digo *");

			codigoTexto.setForeground(corDotexto);
			codigoTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			codigoTexto.setBounds(20, 130, 75, 25);

			codigoTexto.addKeyListener(this);
			painelPrincipal.add(codigoTexto);

			// --------------------------------------------------------------------------------------------------------------

			webcam = new JLabel(

					new ImageIcon(
							CadastroFornecedores.class
									.getResource("/views/cadastros/cad_fornecedores/png/webcam.png")));

			webcam.setBounds(760, 285, 50, 50);

			webcam.setToolTipText("Clique Aqui, Caso Deseje Capturar Uma Foto Pela Sua Webcam");

			webcam.addKeyListener(this);
			webcam.addMouseListener(this);
			painelPrincipal.add(webcam);

			// --------------------------------------------------------------------------------------------------------------

			fitaFoto = new JLabel(

					new ImageIcon(
							CadastroFornecedores.class
									.getResource("/views/cadastros/cad_fornecedores/png/Fita_Foto.PNG")));

			fitaFoto.setBounds(741, 82, 80, 20);
			fitaFoto.addKeyListener(this);
			painelPrincipal.add(fitaFoto);

			foto = new Foto(
					new ImageIcon(
							CadastroProdutos.class
									.getResource("/views/cadastros/cad_produtos/jpg/Foto.jpg")));

			foto.setBounds(703, 94, 157, 157);
			foto.setToolTipText("Selecione Uma Logomarca Ou Foto Do Seu Computador Clicando Aqui");

			foto.addKeyListener(this);
			foto.addMouseListener(this);
			painelPrincipal.add(foto);

			// Adicionando Fundo a Tela ...

			fundo = new JLabel(

					new ImageIcon(
							CadastroFornecedores.class
									.getResource("/views/cadastros/cad_fornecedores/jpg/Fundo.jpg")));

			fundo.addKeyListener(this);
			painelPrincipal.add(fundo);

			// Quando o usu�rio apertar "X" ...

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
			this.setSize(933, 566);

			this.setContentPane(painelPrincipal);
			this.addKeyListener(this);

			this.setResizable(false);
			this.setTitle("Thallyta M�veis - Cadastrando Novo Fornecedor");

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

			dispose();

		}

		if (fornecedorSalvo.isVisible()
				& mouseClick.getSource() == fornecedorSalvo
				& verificandoDirecao == 1) {

			this.dispose();

		}

		if (fornecedorSalvo.isVisible()
				& mouseClick.getSource() == fornecedorSalvo
				& verificandoDirecao == 2) {

			fornecedorSalvo.setVisible(false);

			campoCodigo.setText("");
			campoEmpresa.setText("");
			campoCep.setText("");
			campoRua.setText("");
			campoBairro.setText("");
			campoCidade.setText("");

			estados.setSelectedIndex(0);

			campoTelefone.setText("");
			campoFax.setText("");
			campoEmail.setText("");
			campoSite.setText("");

			campoObservacoes.setText("");

			campoCodigo.requestFocus();

		}

		else if (mouseClick.getSource() == salvar) {

			VerificandoErros verificando = new VerificandoErros();

			boolean verificado = true;

			try {

				verificado = verificando.verificandoAgora(
						campoCodigo.getText(), campoEmpresa.getText(),
						campoRua.getText(), campoBairro.getText(),
						campoCidade.getText(), campoEmail.getText(),
						campoSite.getText(), campoObservacoes.getText());

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			if (verificado == true) {

				Fornecedor fornecedor = new Fornecedor();

				fornecedor.setCodigo(Integer.parseInt(campoCodigo.getText()));
				fornecedor.setEmpresa(campoEmpresa.getText());
				fornecedor.setCep(campoCep.getText());
				fornecedor.setRua(campoRua.getText());
				fornecedor.setBairro(campoBairro.getText());
				fornecedor.setCidade(campoCidade.getText());

				if (estados.getSelectedItem().equals("Selecione ...")) {

					fornecedor.setEstado("");

				}

				else {

					fornecedor.setEstado((String) estados.getSelectedItem());

				}

				fornecedor.setTelefone(campoTelefone.getText());
				fornecedor.setFax(campoFax.getText());
				fornecedor.setEmail(campoEmail.getText());
				fornecedor.setPaginaWeb(campoSite.getText());

				fornecedor.setDataDeCadastro(campoDataDeCadastro.getText());

				fornecedor.setUrlFoto(caminhoDaFoto);
				fornecedor.setObservacoes(campoObservacoes.getText());

				FachadaFornecedores gravando = new FachadaFornecedores();

				try {

					gravando.adicionandoFornecedor(fornecedor);
					fornecedorSalvo.setVisible(true);

					painelPrincipal.setEnabled(false);
					verificandoDirecao = 1;

				}

				catch (SQLException e) {

					new ErroEncontrado();

				}

			}

			else {

			}

		}

		else if (mouseClick.getSource() == salvarCadastrarOutro) {

			VerificandoErros verificando = new VerificandoErros();

			boolean verificado = true;

			try {

				verificado = verificando.verificandoAgora(
						campoCodigo.getText(), campoEmpresa.getText(),
						campoRua.getText(), campoBairro.getText(),
						campoCidade.getText(), campoEmail.getText(),
						campoSite.getText(), campoObservacoes.getText());

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			if (verificado == true) {

				Fornecedor fornecedor = new Fornecedor();

				fornecedor.setCodigo(Integer.parseInt(campoCodigo.getText()));
				fornecedor.setEmpresa(campoEmpresa.getText());
				fornecedor.setCep(campoCep.getText());
				fornecedor.setRua(campoRua.getText());
				fornecedor.setBairro(campoBairro.getText());
				fornecedor.setCidade(campoCidade.getText());

				if (estados.getSelectedItem().equals("Selecione ...")) {

					fornecedor.setEstado("");

				}

				else {

					fornecedor.setEstado((String) estados.getSelectedItem());

				}

				fornecedor.setTelefone(campoTelefone.getText());
				fornecedor.setFax(campoFax.getText());
				fornecedor.setEmail(campoEmail.getText());
				fornecedor.setPaginaWeb(campoSite.getText());

				fornecedor.setUrlFoto(caminhoDaFoto);
				fornecedor.setDataDeCadastro(campoDataDeCadastro.getText());
				fornecedor.setObservacoes(campoObservacoes.getText());

				FachadaFornecedores gravando = new FachadaFornecedores();

				try {

					gravando.adicionandoFornecedor(fornecedor);
					fornecedorSalvo.setVisible(true);

					painelPrincipal.setEnabled(false);
					verificandoDirecao = 2;

				}

				catch (SQLException e) {

					new ErroEncontrado();

				}

			}

			else {

			}

		}

		else if (mouseClick.getSource() == limparTudo) {

			campoCodigo.setText("");
			campoEmpresa.setText("");
			campoCep.setText("");
			campoRua.setText("");
			campoBairro.setText("");
			campoCidade.setText("");

			estados.setSelectedIndex(0);

			campoTelefone.setText("");
			campoFax.setText("");
			campoEmail.setText("");
			campoSite.setText("");

			campoObservacoes.setText("");

			campoCodigo.requestFocus();

		}

		else if (mouseClick.getSource() == foto) {

			JFileChooser chooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images", "jpg", "gif");

			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);

			int returnVal = chooser.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				caminhoDaFoto = chooser.getSelectedFile().getPath();
				closeFoto.setVisible(true);

				foto.setIcon((new ImageIcon(chooser.getSelectedFile().getPath())));
				ContadorMudarFoto = 2;

				foto.repaint();

			}

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
					CadastroFornecedores.class
							.getResource("/views/cadastros/cad_fornecedores/png/webcam_mouse.png")));

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
					CadastroFornecedores.class
							.getResource("/views/cadastros/cad_fornecedores/png/webcam.png")));

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

	private String pegandoDataDoSistema() {

		Date now = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String s = df.format(now);

		return s;

	}

	@Override
	public void keyPressed(KeyEvent keyPress) {

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			dispose();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_L
				&& keyPress.isControlDown()) {

			campoCodigo.setText("");
			campoEmpresa.setText("");
			campoCep.setText("");
			campoRua.setText("");
			campoBairro.setText("");
			campoCidade.setText("");

			estados.setSelectedIndex(0);

			campoTelefone.setText("");
			campoFax.setText("");
			campoEmail.setText("");
			campoSite.setText("");

			campoObservacoes.setText("");

			campoCodigo.requestFocus();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_S
				&& keyPress.isControlDown()) {

			VerificandoErros verificando = new VerificandoErros();

			boolean verificado = true;

			try {

				verificado = verificando.verificandoAgora(
						campoCodigo.getText(), campoEmpresa.getText(),
						campoRua.getText(), campoBairro.getText(),
						campoCidade.getText(), campoEmail.getText(),
						campoSite.getText(), campoObservacoes.getText());

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			if (verificado == true) {

				Fornecedor fornecedor = new Fornecedor();

				fornecedor.setCodigo(Integer.parseInt(campoCodigo.getText()));
				fornecedor.setEmpresa(campoEmpresa.getText());
				fornecedor.setCep(campoCep.getText());
				fornecedor.setRua(campoRua.getText());
				fornecedor.setBairro(campoBairro.getText());
				fornecedor.setCidade(campoCidade.getText());

				if (estados.getSelectedItem().equals("Selecione ...")) {

					fornecedor.setEstado("");

				}

				else {

					fornecedor.setEstado((String) estados.getSelectedItem());

				}

				fornecedor.setTelefone(campoTelefone.getText());
				fornecedor.setFax(campoFax.getText());
				fornecedor.setEmail(campoEmail.getText());
				fornecedor.setPaginaWeb(campoSite.getText());

				fornecedor.setUrlFoto(caminhoDaFoto);
				fornecedor.setDataDeCadastro(campoDataDeCadastro.getText());
				fornecedor.setObservacoes(campoObservacoes.getText());

				FachadaFornecedores gravando = new FachadaFornecedores();

				try {

					gravando.adicionandoFornecedor(fornecedor);
					fornecedorSalvo.setVisible(true);

					painelPrincipal.setEnabled(false);
					verificandoDirecao = 1;

				}

				catch (SQLException e) {

					new ErroEncontrado();

				}

			}

			else {

			}

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_F
				&& keyPress.isControlDown()) {

			VerificandoErros verificando = new VerificandoErros();

			boolean verificado = true;

			try {

				verificado = verificando.verificandoAgora(
						campoCodigo.getText(), campoEmpresa.getText(),
						campoRua.getText(), campoBairro.getText(),
						campoCidade.getText(), campoEmail.getText(),
						campoSite.getText(), campoObservacoes.getText());

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

			if (verificado == true) {

				Fornecedor fornecedor = new Fornecedor();

				fornecedor.setCodigo(Integer.parseInt(campoCodigo.getText()));
				fornecedor.setEmpresa(campoEmpresa.getText());
				fornecedor.setCep(campoCep.getText());
				fornecedor.setRua(campoRua.getText());
				fornecedor.setBairro(campoBairro.getText());
				fornecedor.setCidade(campoCidade.getText());

				if (estados.getSelectedItem().equals("Selecione ...")) {

					fornecedor.setEstado("");

				}

				else {

					fornecedor.setEstado((String) estados.getSelectedItem());

				}

				fornecedor.setTelefone(campoTelefone.getText());
				fornecedor.setFax(campoFax.getText());
				fornecedor.setEmail(campoEmail.getText());
				fornecedor.setPaginaWeb(campoSite.getText());

				fornecedor.setUrlFoto(caminhoDaFoto);
				fornecedor.setDataDeCadastro(campoDataDeCadastro.getText());
				fornecedor.setObservacoes(campoObservacoes.getText());

				FachadaFornecedores gravando = new FachadaFornecedores();

				try {

					gravando.adicionandoFornecedor(fornecedor);
					fornecedorSalvo.setVisible(true);

					painelPrincipal.setEnabled(false);
					verificandoDirecao = 2;

				}

				catch (SQLException e) {

					new ErroEncontrado();

				}

			}

			else {

			}

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& fornecedorSalvo.isVisible() == true
				&& verificandoDirecao == 1) {

			this.dispose();

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& fornecedorSalvo.isVisible() == true
				&& verificandoDirecao == 2) {

			fornecedorSalvo.setVisible(false);

			campoCodigo.setText("");
			campoEmpresa.setText("");
			campoCep.setText("");
			campoRua.setText("");
			campoBairro.setText("");
			campoCidade.setText("");

			estados.setSelectedIndex(0);

			campoTelefone.setText("");
			campoFax.setText("");
			campoEmail.setText("");
			campoSite.setText("");

			campoObservacoes.setText("");

			campoCodigo.requestFocus();

		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

}