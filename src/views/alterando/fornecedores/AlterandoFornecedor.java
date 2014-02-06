/*
 * 
 * 
 * Falta fazer nesta tela:
 * 
 * 		- Carregar e salvar foto.
 * 		- Tirar foto da webcam e selecionar.
 * 
 * 
 */

package views.alterando.fornecedores;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.text.MaskFormatter;

import fachada.FachadaFornecedores;

import modelo.Fornecedor;

import views.cadastros.cad_fornecedores.CadastroFornecedores;
import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;

public class AlterandoFornecedor extends JInternalFrame implements
		MouseListener, KeyListener {

	private static final long serialVersionUID = 1L;

	// Objetos e variáveis de apoio ...

	private JPanel painelPrincipal, painelDescricao;

	private JLabel fundo, fitaFoto, foto, webcam, codigoTexto, empresaTexto,
			cepTexto, ruaTexto, bairroTexto, cidadeTexto, estadoTexto,
			textoTelefone, textoFax, textoEmail, textoSite,
			dataDeCadastroTexto, observacoesTexto, fornecedorAlterado;

	private JTextField campoCodigo, campoEmpresa, campoCep, campoRua,
			campoBairro, campoCidade, campoTelefone, campoFax, campoEmail,
			campoSite, campoDataDeCadastro;

	private JComboBox<String> estados;
	private JTextArea campoObservacoes;
	private JScrollPane scrollDescricao;

	private JButton cancelar, alterarAgora, limparTudo;

	private Color corDotexto = new Color(139, 139, 139);
	private String codigoOriginal = "0";

	private int verificaçãoDeFoto = 1;

	public AlterandoFornecedor(String codigo, String empresa, String cep,
			String rua, String bairro, String cidade, String estado,
			String telefone, String fax, String email, String site,
			String observacoes, String dataDeCadastro, String urlImagem) {

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

			codigoOriginal = codigo;

			// Adicionando componentes a tela ...

			fornecedorAlterado = new JLabel(

					new ImageIcon(
							AlterandoFornecedor.class
									.getResource("/views/alterando/fornecedores/jpg/Fornecedor_Alterado.JPG")));

			fornecedorAlterado.setBounds(0, 0, 927, 538);
			fornecedorAlterado.setVisible(false);

			fornecedorAlterado.addMouseListener(this);
			fornecedorAlterado.addKeyListener(this);

			painelPrincipal.add(fornecedorAlterado);

			painelDescricao = new JPanel();
			painelDescricao.setLayout(new BorderLayout());
			painelDescricao.setBounds(529, 395, 371, 73);

			campoObservacoes = new JTextArea();

			campoObservacoes.setForeground(corDotexto);
			campoObservacoes.setBounds(529, 395, 373, 73);
			campoObservacoes.setText(observacoes);

			campoObservacoes
					.setToolTipText("Clique Aqui e Adicione Uma Observação a Este Fornecedor");

			scrollDescricao = new JScrollPane(campoObservacoes);

			campoObservacoes.addKeyListener(this);

			painelDescricao.add(scrollDescricao);
			painelPrincipal.add(painelDescricao);

			observacoesTexto = new JLabel("Observações");

			observacoesTexto.setForeground(corDotexto);
			observacoesTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			observacoesTexto.setBounds(530, 370, 100, 25);

			observacoesTexto.addKeyListener(this);
			painelPrincipal.add(observacoesTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoDataDeCadastro = new JTextField();

			campoDataDeCadastro.setForeground(corDotexto);
			campoDataDeCadastro.setText(dataDeCadastro);
			campoDataDeCadastro.setFocusable(false);

			campoDataDeCadastro.setEditable(false);
			campoDataDeCadastro.setBounds(548, 235, 120, 33);

			campoDataDeCadastro
					.setToolTipText("Este Campo Não é Editável, Ele Somente Captura a Data Do Sistema Para Maior Segurança");

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
			campoSite.setText(site);

			campoSite
					.setToolTipText("Clique Aqui e Adicione Um Site a Este Fornecedor");

			campoSite.addKeyListener(this);
			painelPrincipal.add(campoSite);

			// --------------------------------------------------------------------------------------------------------------

			campoEmail = new JTextField();

			campoEmail.setForeground(corDotexto);
			campoEmail.setBounds(110, 395, 391, 33);
			campoEmail.setText(email);

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
			campoFax.setText(fax);

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
			campoTelefone.setText(telefone);
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

			alterarAgora = new JButton("Alterar Agora");
			alterarAgora.setBounds(531, 485, 175, 35);

			alterarAgora
					.setToolTipText("Clique Aqui Para Alterar Este Fornecedor, Ou Aperte As Teclas (CTRL + A)");

			alterarAgora.addKeyListener(this);
			alterarAgora.addMouseListener(this);
			painelPrincipal.add(alterarAgora);

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

			cancelar.setToolTipText("Clique Aqui Para Cancelar a Alteração, Ou Aperte a Tecla (ESC)");
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
			estados.addItem("Amapá");
			estados.addItem("Amazonas");
			estados.addItem("Bahia");
			estados.addItem("Ceará");
			estados.addItem("Distrito Federal");
			estados.addItem("Goiás");
			estados.addItem("Espírito Santo");
			estados.addItem("Maranhão");
			estados.addItem("Mato Grosso");
			estados.addItem("Mato Grosso Do Sul");
			estados.addItem("Minas Gerais");
			estados.addItem("Pará");
			estados.addItem("Paraiba");
			estados.addItem("Paraná");
			estados.addItem("Pernambuco");
			estados.addItem("Piauí");
			estados.addItem("Rio De Janeiro");
			estados.addItem("Rio Grande Do Norte");
			estados.addItem("Rio Grande Do Sul");
			estados.addItem("Rondônia");
			estados.addItem("Rorâima");
			estados.addItem("São Paulo");
			estados.addItem("Santa Catarina");
			estados.addItem("Sergipe");
			estados.addItem("Tocantins");

			estados.setToolTipText("Clique Aqui e Adicione Um Estado a Este Fornecedor");
			estados.setSelectedItem(estado);

			estados.addKeyListener(this);
			painelPrincipal.add(estados);

			// --------------------------------------------------------------------------------------------------------------

			campoCidade = new JTextField();

			campoCidade.setForeground(corDotexto);
			campoCidade.setBounds(110, 295, 175, 33);
			campoCidade.setText(cidade);

			campoCidade
					.setToolTipText("Caso Queira Digitar Uma Informação De Cidade Para o Fornecedor, Clique Aqui");

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

			campoBairro.setText(bairro);
			campoBairro.setBounds(371, 255, 130, 33);

			campoBairro
					.setToolTipText("Continue Adicionando Informações Sobre o Bairro Do Fornecedor, Clicando Aqui");

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

			campoRua.setText(rua);
			campoRua.setBounds(110, 255, 200, 33);

			campoRua.setToolTipText("Aqui Você Adicionará Um Endereço, Começando Pela Rua");

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

			campoCep.setText(cep);
			campoCep.setBounds(110, 215, 120, 33);

			campoCep.setToolTipText("Digite Aqui o CEP Do Fornecedor Que Está Cadastrando e Tecle Enter Ou Clique Em Verificar");

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
			campoEmpresa.setText(empresa);

			campoEmpresa
					.setToolTipText("Digite Aqui a Descrição Da Empresa Do Fornecedor Que Está Cadastrando");

			campoEmpresa.addKeyListener(this);
			painelPrincipal.add(campoEmpresa);

			// --------------------------------------------------------------------------------------------------------------

			empresaTexto = new JLabel("Empresa");

			empresaTexto.setForeground(corDotexto);
			empresaTexto.setFont(new Font("Dialog", Font.PLAIN, 15));
			empresaTexto.setBounds(20, 170, 75, 25);

			empresaTexto.addKeyListener(this);
			painelPrincipal.add(empresaTexto);

			// --------------------------------------------------------------------------------------------------------------

			campoCodigo = new JTextField();

			campoCodigo.setForeground(corDotexto);

			campoCodigo.setText(codigo);
			campoCodigo.setBounds(110, 125, 100, 33);

			campoCodigo
					.setToolTipText("Aqui Você Adicionará Um Código Para Este Fornecedor");

			campoCodigo.addKeyListener(this);
			painelPrincipal.add(campoCodigo);

			// --------------------------------------------------------------------------------------------------------------

			codigoTexto = new JLabel("Código");

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

			fitaFoto.setBounds(743, 82, 80, 20);
			fitaFoto.addKeyListener(this);
			painelPrincipal.add(fitaFoto);

			foto = new JLabel(

					new ImageIcon(
							CadastroFornecedores.class
									.getResource("/views/cadastros/cad_fornecedores/jpg/Foto.jpg")));

			foto.setBounds(703, 94, 157, 157);
			foto.setToolTipText("Selecione Uma Logomarca Ou Foto Do Seu Computador Clicando Aqui");

			if (urlImagem.equals("Nenhum Caminho")) {

			}

			else {

				foto.setIcon(new ImageIcon(urlImagem));
				verificaçãoDeFoto = 2;

			}

			foto.addKeyListener(this);
			foto.addMouseListener(this);
			painelPrincipal.add(foto);

			// Adicionando Fundo a Tela ...

			fundo = new JLabel(

					new ImageIcon(
							AlterandoFornecedor.class
									.getResource("/views/alterando/fornecedores/jpg/Fundo.JPG")));

			fundo.addKeyListener(this);
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
					TelaPrincipal.chamandoNovamenteGerenciarFornecedores();

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

			this.setClosable(true);

			try {

				this.setSelected(true);

			}

			catch (PropertyVetoException e1) {

				new ErroEncontrado();

			}

			this.setResizable(false);
			this.setTitle("Thallyta Móveis - Alterando Fornecedor");
			this.setVisible(true);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public void keyPressed(KeyEvent keyPress) {

		if (keyPress.getKeyCode() == KeyEvent.VK_A && keyPress.isControlDown()) {

			VerificandoErros verificando = new VerificandoErros();

			try {

				boolean v = verificando.verificandoAgora(campoCodigo.getText(),
						campoEmpresa.getText(), codigoOriginal);

				if (v == true) {

					Fornecedor fornecedor = new Fornecedor();

					fornecedor.setBairro(campoBairro.getText());
					fornecedor.setCep(campoCep.getText());
					fornecedor.setCidade(campoCidade.getText());

					fornecedor
							.setCodigo(Integer.parseInt(campoCodigo.getText()));

					fornecedor.setDataDeCadastro(campoDataDeCadastro.getText());
					fornecedor.setEmail(campoEmail.getText());
					fornecedor.setEmpresa(campoEmpresa.getText());
					fornecedor.setEstado((String) estados.getSelectedItem());
					fornecedor.setFax(campoFax.getText());
					fornecedor.setObservacoes(campoObservacoes.getText());
					fornecedor.setPaginaWeb(campoSite.getText());
					fornecedor.setRua(campoRua.getText());
					fornecedor.setTelefone(campoTelefone.getText());

					FachadaFornecedores alterando = new FachadaFornecedores();
					alterando.alterandoFornecedor(codigoOriginal, fornecedor);

					fornecedorAlterado.setVisible(true);
					campoObservacoes.setFocusable(false);

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		if (keyPress.getKeyCode() == KeyEvent.VK_L && keyPress.isControlDown()) {

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

		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE) {

			this.dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarFornecedores();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		else if (keyPress.getKeyCode() == KeyEvent.VK_ENTER
				&& fornecedorAlterado.isVisible() == true) {

			this.dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarFornecedores();

			}

			catch (Exception e) {

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

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (fornecedorAlterado.isVisible() == true
				& mouseClick.getSource() == fornecedorAlterado) {

			this.dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarFornecedores();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		if (mouseClick.getSource() == alterarAgora) {

			VerificandoErros verificando = new VerificandoErros();

			try {

				boolean v = verificando.verificandoAgora(campoCodigo.getText(),
						campoEmpresa.getText(), codigoOriginal);

				if (v == true) {

					Fornecedor fornecedor = new Fornecedor();

					fornecedor.setBairro(campoBairro.getText());
					fornecedor.setCep(campoCep.getText());
					fornecedor.setCidade(campoCidade.getText());

					fornecedor
							.setCodigo(Integer.parseInt(campoCodigo.getText()));

					fornecedor.setDataDeCadastro(campoDataDeCadastro.getText());
					fornecedor.setEmail(campoEmail.getText());
					fornecedor.setEmpresa(campoEmpresa.getText());
					fornecedor.setEstado((String) estados.getSelectedItem());
					fornecedor.setFax(campoFax.getText());
					fornecedor.setObservacoes(campoObservacoes.getText());
					fornecedor.setPaginaWeb(campoSite.getText());
					fornecedor.setRua(campoRua.getText());
					fornecedor.setTelefone(campoTelefone.getText());

					FachadaFornecedores alterando = new FachadaFornecedores();
					alterando.alterandoFornecedor(codigoOriginal, fornecedor);

					fornecedorAlterado.setVisible(true);
					campoObservacoes.setFocusable(false);

				}

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		if (mouseClick.getSource() == cancelar) {

			dispose();

			try {

				TelaPrincipal.chamandoNovamenteGerenciarFornecedores();

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

		}

		if (mouseClick.getSource() == limparTudo) {

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
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == webcam) {

			webcam.setIcon(new ImageIcon(
					CadastroFornecedores.class
							.getResource("/views/cadastros/cad_fornecedores/png/webcam_mouse.png")));

		}

		else if (mouseEntry.getSource() == foto & verificaçãoDeFoto == 1) {

			foto.setIcon(new ImageIcon(
					CadastroFornecedores.class
							.getResource("/views/cadastros/cad_fornecedores/jpg/Foto_Mouse.jpg")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == webcam) {

			webcam.setIcon(new ImageIcon(
					CadastroFornecedores.class
							.getResource("/views/cadastros/cad_fornecedores/png/webcam.png")));

		}

		else if (mouseExit.getSource() == foto & verificaçãoDeFoto == 1) {

			foto.setIcon(new ImageIcon(
					CadastroFornecedores.class
							.getResource("/views/cadastros/cad_fornecedores/jpg/Foto.jpg")));

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

}