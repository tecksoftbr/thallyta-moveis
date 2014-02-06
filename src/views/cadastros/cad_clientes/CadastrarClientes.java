package views.cadastros.cad_clientes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import modelo.Cliente;
import views.outras.capturar_webcam.Capturando;
import views.principais.tela_de_erro.ErroEncontrado;
import fachada.FachadaClientes;

public class CadastrarClientes extends JInternalFrame implements MouseListener,
		KeyListener {

	private static final long serialVersionUID = 1L;

	private JPanel painelPrincipal, painelPessoal, painelComercial,
			painelDeObservacoes;

	private JTabbedPane abas;

	private JTextField campoCodigo;
	private JTextField campoNomeCompleto;
	private JLabel complemento, fundoFoto, foto, fitaFoto;
	private JTextField campoComplemento;

	private JLabel cpf;
	private JTextField campoCpf;
	private JLabel rg;

	private JTextField campoRg;
	private JLabel orgaoEmissor;

	private JTextField campoOrgaoEmissor;
	private JLabel dataDeEmissao;
	private JTextField campoDataDeEmissao;
	private JLabel nacionalidade;
	private JTextField campoNacionalidade;
	private JLabel rua;
	private JTextField campoRua;
	private JLabel numero;
	private JTextField campoNumero;
	private JLabel bairro;
	private JTextField campoBairro;
	private JLabel cidade;
	private JTextField campoCidade;
	private JComboBox<String> comboEstado, comboSexo, comboEstadoTrabalho;
	private JLabel estado;
	private JTextField campoCep;

	private JLabel cep;
	private JLabel telefone01;
	private JTextField campoTelefone01;
	private JLabel telefone02;
	private JTextField campoTelefone02;
	private JLabel email;
	private JTextField campoEmail;
	private JTextField campoDataDeNascimento;
	private JLabel estadoCivil;
	private JTextField campoEstadoCivil;
	private JLabel conjugue;
	private JTextField campoConjugue;
	private JLabel pai;

	private JTextField campoPai;
	private JLabel mae, emailTrabalho;
	private JTextField campoMae;
	private JTextField campoTrabalho;
	private JLabel trabalho;
	private JLabel cargo;
	private JLabel webcam;
	private JTextField campoCargo;
	private JLabel tempoDeServico;
	private JTextField campoTempoDeServico;
	private JTextField campoDataDeCadastro;

	JTextArea campoObservacoesTrabalho, campoObservacoesAdicionais;

	private JButton cancelar;
	private JButton limparTudo;
	private JButton salvarCadastrarOutro;
	private JButton salvar;

	private Color corDotexto = new Color(139, 139, 139);
	private JTextField campoEnderecoOndeTrabalha;
	private JTextField campoNumeroTrabalho;
	private JTextField campoComplementoTrabalho;
	private JTextField campoBairroTrabalho;
	private JTextField campoCidadeTrabalho;
	private JTextField campoCepTrabalho;
	private JTextField campoTelefoneTrabalho;
	private JTextField campoFaxTrabalho;
	private JTextField campoEmailTrabalho;

	private int ContadorMudarFoto = 1;

	public CadastrarClientes() {

		try {

			try {

				UIManager
						.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

			}

			catch (Exception e) {

				new ErroEncontrado();

			}

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

			this.setClosable(true);
			this.setSelected(true);

			this.setContentPane(painelPrincipal);
			this.setTitle("Thallyta Móveis - Cadastrando Novo Cliente");
			this.setVisible(true);

			getContentPane().setLayout(null);
			getContentPane().setBackground(Color.gray);

			painelPessoal = new JPanel();
			painelPessoal.setBackground(SystemColor.control);

			painelComercial = new JPanel();
			painelComercial.setBackground(SystemColor.control);

			painelDeObservacoes = new JPanel();
			painelDeObservacoes.setBackground(SystemColor.control);

			abas = new JTabbedPane();
			abas.setBounds(0, 80, 1000, 500);
			abas.setBackground(Color.black);

			abas.addTab("Pessoal", painelPessoal);
			painelPessoal.setLayout(null);
			painelPessoal.setLayout(null);

			JLabel codigo = new JLabel("C\u00F3digo *");
			codigo.setBounds(16, 17, 61, 20);
			codigo.setForeground(corDotexto);
			codigo.setFont(new Font("Dialog", Font.PLAIN, 15));
			painelPessoal.add(codigo);

			campoCodigo = new JTextField();
			campoCodigo.setForeground(new Color(139, 139, 139));
			campoCodigo.setBounds(78, 12, 55, 33);
			painelPessoal.add(campoCodigo);
			campoCodigo.setColumns(10);

			campoNomeCompleto = new JTextField();
			campoNomeCompleto.setColumns(10);
			campoNomeCompleto.setForeground(new Color(139, 139, 139));
			campoNomeCompleto.setBounds(266, 12, 237, 33);
			painelPessoal.add(campoNomeCompleto);

			JLabel nomeCompleto = new JLabel("Nome Completo *");
			nomeCompleto.setForeground(new Color(139, 139, 139));
			nomeCompleto.setFont(new Font("Dialog", Font.PLAIN, 15));
			nomeCompleto.setBounds(143, 17, 116, 20);
			painelPessoal.add(nomeCompleto);

			JLabel sexo = new JLabel("Sexo *");
			sexo.setForeground(new Color(139, 139, 139));
			sexo.setFont(new Font("Dialog", Font.PLAIN, 15));
			sexo.setBounds(514, 17, 43, 20);
			painelPessoal.add(sexo);

			comboSexo = new JComboBox<String>();
			comboSexo.setModel(new DefaultComboBoxModel<String>(new String[] {
					"Selecione ...", "Masculino", "Feminino" }));
			comboSexo.setBounds(562, 12, 128, 33);
			painelPessoal.add(comboSexo);

			complemento = new JLabel("Complemento");
			complemento.setForeground(new Color(139, 139, 139));
			complemento.setFont(new Font("Dialog", Font.PLAIN, 15));
			complemento.setBounds(642, 126, 137, 20);
			painelPessoal.add(complemento);

			campoComplemento = new JTextField();
			campoComplemento.setForeground(new Color(139, 139, 139));
			campoComplemento.setColumns(10);
			campoComplemento.setBounds(746, 121, 162, 33);
			painelPessoal.add(campoComplemento);

			cpf = new JLabel("CPF *");
			cpf.setForeground(new Color(139, 139, 139));
			cpf.setFont(new Font("Dialog", Font.PLAIN, 15));
			cpf.setBounds(705, 17, 55, 20);
			painelPessoal.add(cpf);

			campoCpf = new JFormattedTextField((setMascara("###.###.###-##")));

			campoCpf.setColumns(10);
			campoCpf.setForeground(new Color(139, 139, 139));
			campoCpf.setBounds(756, 12, 152, 33);
			painelPessoal.add(campoCpf);

			rg = new JLabel("RG *");
			rg.setForeground(new Color(139, 139, 139));
			rg.setFont(new Font("Dialog", Font.PLAIN, 15));
			rg.setBounds(16, 62, 55, 20);
			painelPessoal.add(rg);

			campoRg = new JTextField();
			campoRg.setColumns(10);
			campoRg.setForeground(new Color(139, 139, 139));
			campoRg.setBounds(78, 57, 90, 33);
			painelPessoal.add(campoRg);

			orgaoEmissor = new JLabel("Org\u00E3o Emissor *");
			orgaoEmissor.setForeground(new Color(139, 139, 139));
			orgaoEmissor.setFont(new Font("Dialog", Font.PLAIN, 15));
			orgaoEmissor.setBounds(177, 62, 116, 20);
			painelPessoal.add(orgaoEmissor);

			campoOrgaoEmissor = new JTextField();
			campoOrgaoEmissor.setColumns(10);
			campoOrgaoEmissor.setForeground(new Color(139, 139, 139));
			campoOrgaoEmissor.setBounds(294, 57, 61, 33);
			painelPessoal.add(campoOrgaoEmissor);

			dataDeEmissao = new JLabel("Data De Emiss\u00E3o *");
			dataDeEmissao.setForeground(new Color(139, 139, 139));
			dataDeEmissao.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataDeEmissao.setBounds(367, 62, 128, 20);
			painelPessoal.add(dataDeEmissao);

			campoDataDeEmissao = new JFormattedTextField(
					(setMascara("##/##/####")));
			campoDataDeEmissao.setColumns(10);
			campoDataDeEmissao.setForeground(new Color(139, 139, 139));
			campoDataDeEmissao.setBounds(502, 57, 128, 33);
			painelPessoal.add(campoDataDeEmissao);

			nacionalidade = new JLabel("Nacionalidade *");
			nacionalidade.setForeground(new Color(139, 139, 139));
			nacionalidade.setFont(new Font("Dialog", Font.PLAIN, 15));
			nacionalidade.setBounds(642, 62, 106, 20);
			painelPessoal.add(nacionalidade);

			campoNacionalidade = new JTextField();
			campoNacionalidade.setColumns(10);
			campoNacionalidade.setForeground(new Color(139, 139, 139));
			campoNacionalidade.setBounds(756, 57, 152, 33);
			painelPessoal.add(campoNacionalidade);

			rua = new JLabel("Rua");
			rua.setForeground(new Color(139, 139, 139));
			rua.setFont(new Font("Dialog", Font.PLAIN, 15));
			rua.setBounds(16, 126, 55, 20);
			painelPessoal.add(rua);

			campoRua = new JTextField();
			campoRua.setColumns(10);
			campoRua.setForeground(new Color(139, 139, 139));
			campoRua.setBounds(78, 121, 280, 33);
			painelPessoal.add(campoRua);

			numero = new JLabel("N\u00FAmero");
			numero.setForeground(new Color(139, 139, 139));
			numero.setFont(new Font("Dialog", Font.PLAIN, 15));
			numero.setBounds(368, 126, 55, 20);
			painelPessoal.add(numero);

			campoNumero = new JTextField();
			campoNumero.setColumns(10);
			campoNumero.setForeground(corDotexto);
			campoNumero.setBounds(435, 121, 100, 33);
			painelPessoal.add(campoNumero);

			bairro = new JLabel("Bairro");
			bairro.setForeground(new Color(139, 139, 139));
			bairro.setFont(new Font("Dialog", Font.PLAIN, 15));
			bairro.setBounds(16, 172, 55, 20);
			painelPessoal.add(bairro);

			campoBairro = new JTextField();
			campoBairro.setColumns(10);
			campoBairro.setForeground(corDotexto);
			campoBairro.setBounds(78, 167, 166, 33);
			painelPessoal.add(campoBairro);

			cidade = new JLabel("Cidade");
			cidade.setForeground(new Color(139, 139, 139));
			cidade.setFont(new Font("Dialog", Font.PLAIN, 15));
			cidade.setBounds(254, 172, 55, 20);
			painelPessoal.add(cidade);

			campoCidade = new JTextField();
			campoCidade.setColumns(10);
			campoCidade.setForeground(corDotexto);
			campoCidade.setBounds(310, 167, 159, 33);
			painelPessoal.add(campoCidade);

			comboEstado = new JComboBox<String>();

			comboEstado.setModel(new DefaultComboBoxModel<String>(
					new String[] { "Selecione ...", "Acre", "Alagoas",
							"Amap\u00E1", "Amazonas", "Bahia", "Cear\u00E1",
							"Distrito Federal", "Esp\u00EDrito Santo",
							"Goi\u00E1s", "Maranh\u00E3o", "Mato Grosso",
							"Mato Grosso do Sul", "Minas Gerais", "Par\u00E1",
							"Para\u00EDba", "Paran\u00E1", "Pernambuco",
							"Piau\u00ED", "Rio de Janeiro",
							"Rio Grande do Norte", "Rio Grande do Sul",
							"Rond\u00F4nia", "Roraima", "Santa Catarina",
							"S\u00E3o Paulo", "Sergipe", "Tocantins" }));

			comboEstado.setBounds(538, 167, 152, 33);
			painelPessoal.add(comboEstado);

			estado = new JLabel("Estado");
			estado.setForeground(new Color(139, 139, 139));
			estado.setFont(new Font("Dialog", Font.PLAIN, 15));
			estado.setBounds(481, 171, 61, 20);
			painelPessoal.add(estado);

			campoCep = new JFormattedTextField((setMascara("#####-###")));
			campoCep.setColumns(10);
			campoCep.setForeground(corDotexto);
			campoCep.setBounds(746, 167, 162, 33);
			painelPessoal.add(campoCep);

			cep = new JLabel("CEP");
			cep.setForeground(new Color(139, 139, 139));
			cep.setFont(new Font("Dialog", Font.PLAIN, 15));
			cep.setBounds(702, 172, 55, 20);
			painelPessoal.add(cep);

			telefone01 = new JLabel("Tel - 01");
			telefone01.setForeground(new Color(139, 139, 139));
			telefone01.setFont(new Font("Dialog", Font.PLAIN, 15));
			telefone01.setBounds(16, 240, 74, 20);
			painelPessoal.add(telefone01);

			campoTelefone01 = new JFormattedTextField(
					(setMascara("(##) ####-####")));

			campoTelefone01.setColumns(10);
			campoTelefone01.setForeground(corDotexto);
			campoTelefone01.setBounds(78, 235, 146, 33);
			painelPessoal.add(campoTelefone01);

			telefone02 = new JLabel("Telefone - 02");
			telefone02.setForeground(new Color(139, 139, 139));
			telefone02.setFont(new Font("Dialog", Font.PLAIN, 15));
			telefone02.setBounds(252, 240, 124, 20);
			painelPessoal.add(telefone02);

			campoTelefone02 = new JFormattedTextField(
					(setMascara("(##) ####-####")));

			campoTelefone02.setColumns(10);
			campoTelefone02.setForeground(corDotexto);
			campoTelefone02.setBounds(368, 235, 152, 33);
			painelPessoal.add(campoTelefone02);

			email = new JLabel("E-mail");
			email.setForeground(new Color(139, 139, 139));
			email.setFont(new Font("Dialog", Font.PLAIN, 15));
			email.setBounds(533, 240, 124, 20);
			painelPessoal.add(email);

			campoEmail = new JTextField();
			campoEmail.setColumns(10);
			campoEmail.setForeground(corDotexto);
			campoEmail.setBounds(586, 235, 322, 33);
			painelPessoal.add(campoEmail);

			salvar = new JButton("Salvar");
			salvar.setToolTipText("Clique Aqui Para Salvar Este Fornecedor, Ou Aperte As Teclas (CTRL + S)");
			salvar.setBounds(444, 381, 90, 35);

			salvar.addMouseListener(this);
			salvar.addKeyListener(this);

			painelPessoal.add(salvar);

			salvarCadastrarOutro = new JButton("Salvar e Cadastrar Outro");
			salvarCadastrarOutro
					.setToolTipText("Clique Aqui Para Salvar Este Fornecedor e Cadastrar Outro, Ou Aperte As Teclas (CTRL + F)");

			salvarCadastrarOutro.setBounds(537, 381, 175, 35);

			salvarCadastrarOutro.addMouseListener(this);
			salvarCadastrarOutro.addKeyListener(this);

			painelPessoal.add(salvarCadastrarOutro);

			limparTudo = new JButton("Limpar Tudo");
			limparTudo
					.setToolTipText("Clique Aqui Para Limpar Todos Os Campos, Ou Aperte As Teclas (CTRL + L)");
			limparTudo.setBounds(715, 381, 105, 35);

			limparTudo.addMouseListener(this);
			limparTudo.addKeyListener(this);

			painelPessoal.add(limparTudo);

			cancelar = new JButton("Cancelar");
			cancelar.setToolTipText("Clique Aqui Para Cancelar o Cadastro, Ou Aperte a Tecla (ESC)");
			cancelar.setBounds(823, 381, 85, 35);

			cancelar.addMouseListener(this);
			cancelar.addKeyListener(this);

			painelPessoal.add(cancelar);

			JLabel dataDeNascimento = new JLabel("Data De Nascimento");
			dataDeNascimento.setForeground(new Color(139, 139, 139));
			dataDeNascimento.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataDeNascimento.setBounds(16, 285, 136, 20);
			painelPessoal.add(dataDeNascimento);

			campoDataDeNascimento = new JFormattedTextField(
					(setMascara("##/##/####")));

			campoDataDeNascimento.setColumns(10);
			campoDataDeNascimento.setForeground(corDotexto);
			campoDataDeNascimento.setBounds(161, 280, 109, 33);
			painelPessoal.add(campoDataDeNascimento);

			estadoCivil = new JLabel("Estado Civil");
			estadoCivil.setForeground(new Color(139, 139, 139));
			estadoCivil.setFont(new Font("Dialog", Font.PLAIN, 15));
			estadoCivil.setBounds(280, 285, 106, 20);
			painelPessoal.add(estadoCivil);

			campoEstadoCivil = new JTextField();
			campoEstadoCivil.setColumns(10);
			campoEstadoCivil.setForeground(corDotexto);
			campoEstadoCivil.setBounds(368, 280, 152, 33);
			painelPessoal.add(campoEstadoCivil);

			conjugue = new JLabel("Conjugue");
			conjugue.setForeground(new Color(139, 139, 139));
			conjugue.setFont(new Font("Dialog", Font.PLAIN, 15));
			conjugue.setBounds(533, 285, 106, 20);
			painelPessoal.add(conjugue);

			campoConjugue = new JTextField();
			campoConjugue.setColumns(10);
			campoConjugue.setForeground(corDotexto);
			campoConjugue.setBounds(608, 280, 300, 33);
			painelPessoal.add(campoConjugue);

			pai = new JLabel("Pai");
			pai.setForeground(new Color(139, 139, 139));
			pai.setFont(new Font("Dialog", Font.PLAIN, 15));
			pai.setBounds(16, 341, 74, 20);
			painelPessoal.add(pai);

			campoPai = new JTextField();
			campoPai.setColumns(10);
			campoPai.setForeground(corDotexto);
			campoPai.setBounds(78, 336, 373, 33);
			painelPessoal.add(campoPai);

			mae = new JLabel("M\u00E3e");
			mae.setForeground(new Color(139, 139, 139));
			mae.setFont(new Font("Dialog", Font.PLAIN, 15));
			mae.setBounds(460, 340, 74, 20);
			painelPessoal.add(mae);

			campoMae = new JTextField();
			campoMae.setColumns(10);
			campoMae.setForeground(corDotexto);
			campoMae.setBounds(498, 335, 410, 33);
			painelPessoal.add(campoMae);

			abas.addTab("Comercial", painelComercial);
			painelComercial.setLayout(null);
			painelComercial.setLayout(null);

			campoTrabalho = new JTextField();
			campoTrabalho.setColumns(10);
			campoTrabalho.setForeground(corDotexto);
			campoTrabalho.setBounds(81, 12, 291, 33);
			painelComercial.add(campoTrabalho);

			trabalho = new JLabel("Trabalho");
			trabalho.setForeground(new Color(139, 139, 139));
			trabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			trabalho.setBounds(16, 17, 67, 20);
			painelComercial.add(trabalho);

			cargo = new JLabel("Cargo/Fun\u00E7\u00E3o");
			cargo.setForeground(new Color(139, 139, 139));
			cargo.setFont(new Font("Dialog", Font.PLAIN, 15));
			cargo.setBounds(384, 17, 98, 20);
			painelComercial.add(cargo);

			campoCargo = new JTextField();
			campoCargo.setColumns(10);
			campoCargo.setForeground(corDotexto);
			campoCargo.setBounds(486, 12, 161, 33);
			painelComercial.add(campoCargo);

			tempoDeServico = new JLabel("Tempo De Servi\u00E7o");
			tempoDeServico.setForeground(new Color(139, 139, 139));
			tempoDeServico.setFont(new Font("Dialog", Font.PLAIN, 15));
			tempoDeServico.setBounds(659, 17, 135, 20);
			painelComercial.add(tempoDeServico);

			campoTempoDeServico = new JTextField();
			campoTempoDeServico.setColumns(10);
			campoTempoDeServico.setForeground(corDotexto);
			campoTempoDeServico.setBounds(792, 12, 115, 33);
			painelComercial.add(campoTempoDeServico);

			campoObservacoesTrabalho = new JTextArea();
			campoObservacoesTrabalho.setForeground(corDotexto);
			campoObservacoesTrabalho.setBounds(16, 304, 891, 91);
			painelComercial.add(campoObservacoesTrabalho);

			JLabel observacoesTrabalho = new JLabel(
					"Observa\u00E7\u00F5es (Sobre o Trabalho)");
			observacoesTrabalho.setForeground(new Color(139, 139, 139));
			observacoesTrabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			observacoesTrabalho.setBounds(16, 276, 255, 20);
			painelComercial.add(observacoesTrabalho);

			campoEnderecoOndeTrabalha = new JTextField();
			campoEnderecoOndeTrabalha.setForeground(new Color(139, 139, 139));
			campoEnderecoOndeTrabalha.setColumns(10);
			campoEnderecoOndeTrabalha.setBounds(215, 89, 220, 33);
			painelComercial.add(campoEnderecoOndeTrabalha);

			JLabel enderecoOndeTrabalha = new JLabel(
					"Endere\u00E7o De Onde Trabalha");
			enderecoOndeTrabalha.setForeground(new Color(139, 139, 139));
			enderecoOndeTrabalha.setFont(new Font("Dialog", Font.PLAIN, 15));
			enderecoOndeTrabalha.setBounds(16, 94, 220, 20);
			painelComercial.add(enderecoOndeTrabalha);

			JLabel numeroTrabalho = new JLabel("N\u00FAmero");
			numeroTrabalho.setForeground(new Color(139, 139, 139));
			numeroTrabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			numeroTrabalho.setBounds(447, 94, 67, 20);
			painelComercial.add(numeroTrabalho);

			campoNumeroTrabalho = new JTextField();
			campoNumeroTrabalho.setForeground(new Color(139, 139, 139));
			campoNumeroTrabalho.setColumns(10);
			campoNumeroTrabalho.setBounds(513, 89, 67, 33);
			painelComercial.add(campoNumeroTrabalho);

			JLabel complementoTrabalho = new JLabel("Complemento");
			complementoTrabalho.setForeground(new Color(139, 139, 139));
			complementoTrabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			complementoTrabalho.setBounds(592, 94, 115, 20);
			painelComercial.add(complementoTrabalho);

			campoComplementoTrabalho = new JTextField();
			campoComplementoTrabalho.setForeground(new Color(139, 139, 139));
			campoComplementoTrabalho.setColumns(10);
			campoComplementoTrabalho.setBounds(695, 89, 212, 33);
			painelComercial.add(campoComplementoTrabalho);

			JLabel bairroTrabalho = new JLabel("Bairro");
			bairroTrabalho.setForeground(new Color(139, 139, 139));
			bairroTrabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			bairroTrabalho.setBounds(16, 139, 115, 20);
			painelComercial.add(bairroTrabalho);

			campoBairroTrabalho = new JTextField();
			campoBairroTrabalho.setForeground(new Color(139, 139, 139));
			campoBairroTrabalho.setColumns(10);
			campoBairroTrabalho.setBounds(66, 134, 212, 33);
			painelComercial.add(campoBairroTrabalho);

			JLabel cidadeTrabalho = new JLabel("Cidade");
			cidadeTrabalho.setForeground(new Color(139, 139, 139));
			cidadeTrabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			cidadeTrabalho.setBounds(290, 139, 115, 20);
			painelComercial.add(cidadeTrabalho);

			campoCidadeTrabalho = new JTextField();
			campoCidadeTrabalho.setForeground(new Color(139, 139, 139));
			campoCidadeTrabalho.setColumns(10);
			campoCidadeTrabalho.setBounds(348, 134, 166, 33);
			painelComercial.add(campoCidadeTrabalho);

			comboEstadoTrabalho = new JComboBox<String>();

			comboEstadoTrabalho.setModel(new DefaultComboBoxModel<String>(
					new String[] { "Selecione ...", "Acre", "Alagoas",
							"Amap\u00E1", "Amazonas", "Bahia", "Cear\u00E1",
							"Distrito Federal", "Esp\u00EDrito Santo",
							"Goi\u00E1s", "Maranh\u00E3o", "Mato Grosso",
							"Mato Grosso do Sul", "Minas Gerais", "Par\u00E1",
							"Para\u00EDba", "Paran\u00E1", "Pernambuco",
							"Piau\u00ED", "Rio de Janeiro",
							"Rio Grande do Norte", "Rio Grande do Sul",
							"Rond\u00F4nia", "Roraima", "Santa Catarina",
							"S\u00E3o Paulo", "Sergipe", "Tocantins" }));
			comboEstadoTrabalho.setBounds(580, 134, 138, 33);
			painelComercial.add(comboEstadoTrabalho);

			JLabel estadoTrabalho = new JLabel("Estado");
			estadoTrabalho.setForeground(new Color(139, 139, 139));
			estadoTrabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			estadoTrabalho.setBounds(525, 139, 61, 20);
			painelComercial.add(estadoTrabalho);

			campoCepTrabalho = new JFormattedTextField(
					(setMascara("#####-###")));

			campoCepTrabalho.setForeground(new Color(139, 139, 139));
			campoCepTrabalho.setColumns(10);
			campoCepTrabalho.setBounds(773, 134, 134, 33);
			painelComercial.add(campoCepTrabalho);

			JLabel cepTrabalho = new JLabel("CEP");
			cepTrabalho.setForeground(new Color(139, 139, 139));
			cepTrabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			cepTrabalho.setBounds(730, 139, 115, 20);
			painelComercial.add(cepTrabalho);

			JLabel telefoneTrabalho = new JLabel("Telefone");
			telefoneTrabalho.setForeground(new Color(139, 139, 139));
			telefoneTrabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			telefoneTrabalho.setBounds(16, 222, 115, 20);
			painelComercial.add(telefoneTrabalho);

			campoTelefoneTrabalho = new JFormattedTextField(
					(setMascara("(##) ####-####")));

			campoTelefoneTrabalho.setForeground(new Color(139, 139, 139));
			campoTelefoneTrabalho.setColumns(10);
			campoTelefoneTrabalho.setBounds(81, 217, 197, 33);
			painelComercial.add(campoTelefoneTrabalho);

			JLabel faxTrabalho = new JLabel("Fax");
			faxTrabalho.setForeground(new Color(139, 139, 139));
			faxTrabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			faxTrabalho.setBounds(290, 222, 34, 20);
			painelComercial.add(faxTrabalho);

			campoFaxTrabalho = new JFormattedTextField(
					(setMascara("(##) ####-####")));

			campoFaxTrabalho.setForeground(new Color(139, 139, 139));
			campoFaxTrabalho.setColumns(10);
			campoFaxTrabalho.setBounds(324, 217, 190, 33);
			painelComercial.add(campoFaxTrabalho);

			emailTrabalho = new JLabel("E-mail");
			emailTrabalho.setForeground(new Color(139, 139, 139));
			emailTrabalho.setFont(new Font("Dialog", Font.PLAIN, 15));
			emailTrabalho.setBounds(524, 222, 55, 20);
			painelComercial.add(emailTrabalho);

			campoEmailTrabalho = new JTextField();
			campoEmailTrabalho.setForeground(new Color(139, 139, 139));
			campoEmailTrabalho.setColumns(10);
			campoEmailTrabalho.setBounds(576, 217, 331, 33);
			painelComercial.add(campoEmailTrabalho);
			abas.addTab("Observações", painelDeObservacoes);
			painelDeObservacoes.setLayout(null);
			painelDeObservacoes.setLayout(null);

			campoObservacoesAdicionais = new JTextArea();
			campoObservacoesAdicionais.setForeground(corDotexto);
			campoObservacoesAdicionais.setBounds(16, 47, 600, 91);
			painelDeObservacoes.add(campoObservacoesAdicionais);

			JLabel observacoesAdicionais = new JLabel(
					"Outras Observa\u00E7\u00F5es Sobre o Cliente");
			observacoesAdicionais.setForeground(new Color(139, 139, 139));
			observacoesAdicionais.setFont(new Font("Dialog", Font.PLAIN, 15));
			observacoesAdicionais.setBounds(16, 19, 257, 20);
			painelDeObservacoes.add(observacoesAdicionais);

			JLabel dataDeCadastro = new JLabel("Data De Cadastro");
			dataDeCadastro.setForeground(new Color(139, 139, 139));
			dataDeCadastro.setFont(new Font("Dialog", Font.PLAIN, 15));
			dataDeCadastro.setBounds(16, 155, 129, 20);
			painelDeObservacoes.add(dataDeCadastro);

			campoDataDeCadastro = new JTextField(pegandoDataDoSistema());
			campoDataDeCadastro.setBounds(144, 150, 129, 33);
			campoDataDeCadastro.setForeground(corDotexto);
			campoDataDeCadastro.setFocusable(false);
			painelDeObservacoes.add(campoDataDeCadastro);
			campoDataDeCadastro.setColumns(10);

			getContentPane().add(abas);

			JLabel fundoPainelPessoal = new JLabel();
			fundoPainelPessoal.setBounds(0, 0, 1000, 470);
			fundoPainelPessoal.setLayout(new BorderLayout());

			fundoPainelPessoal
					.setIcon(new ImageIcon(
							CadastrarClientes.class
									.getResource("/views/cadastros/cad_clientes/jpg/Fundo_Abas.jpg")));

			painelPessoal.add(fundoPainelPessoal);

			JLabel fundoPainelComercial = new JLabel();
			fundoPainelComercial.setBounds(0, 0, 1000, 470);
			fundoPainelComercial.setLayout(new BorderLayout());

			fundoPainelComercial
					.setIcon(new ImageIcon(
							CadastrarClientes.class
									.getResource("/views/cadastros/cad_clientes/jpg/Fundo_Abas.jpg")));

			painelComercial.add(fundoPainelComercial);

			fitaFoto = new JLabel("New label");
			fitaFoto.setIcon(new ImageIcon(
					CadastrarClientes.class
							.getResource("/views/cadastros/cad_clientes/png/Fita_Foto.png")));
			fitaFoto.setBounds(732, 14, 82, 33);
			painelDeObservacoes.add(fitaFoto);

			foto = new JLabel("New label");
			foto.setIcon(new ImageIcon(CadastrarClientes.class
					.getResource("/views/cadastros/cad_clientes/jpg/Foto.jpg")));
			foto.setBounds(691, 25, 158, 157);

			foto.addMouseListener(this);
			foto.addKeyListener(this);
			painelDeObservacoes.add(foto);

			fundoFoto = new JLabel("New label");
			fundoFoto
					.setIcon(new ImageIcon(
							CadastrarClientes.class
									.getResource("/views/cadastros/cad_clientes/png/Foto_Fundo.png")));
			fundoFoto.setBounds(682, 19, 175, 202);
			painelDeObservacoes.add(fundoFoto);

			painelPrincipal.setLayout(new BorderLayout());

			webcam = new JLabel("New label");

			webcam.setIcon(new ImageIcon(
					CadastrarClientes.class
							.getResource("/views/cadastros/cad_clientes/png/webcam.png")));
			webcam.setBounds(756, 209, 36, 63);

			webcam.addMouseListener(this);
			webcam.addKeyListener(this);
			painelDeObservacoes.add(webcam);

			JLabel fundoPainelObservacoes = new JLabel();
			fundoPainelObservacoes.setBounds(0, 0, 1000, 470);
			fundoPainelObservacoes.setLayout(new BorderLayout());

			fundoPainelObservacoes
					.setIcon(new ImageIcon(
							CadastrarClientes.class
									.getResource("/views/cadastros/cad_clientes/jpg/Fundo_Abas.jpg")));

			painelDeObservacoes.add(fundoPainelObservacoes);

			// -----------------------------------------------------------------------------------

			painelPrincipal.setLayout(new BorderLayout());

			JLabel fundoPrincipal = new JLabel();

			fundoPrincipal
					.setIcon(new ImageIcon(
							CadastrarClientes.class
									.getResource("/views/cadastros/cad_clientes/jpg/Fundo.jpg")));

			painelPrincipal.add(fundoPrincipal);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public void mouseClicked(MouseEvent mouseClick) {

		if (mouseClick.getSource() == foto) {

			JFileChooser chooser = new JFileChooser();

			FileNameExtensionFilter filter = new FileNameExtensionFilter(
					"JPG & GIF Images", "jpg", "gif");

			chooser.setFileFilter(filter);
			chooser.setMultiSelectionEnabled(false);

			int returnVal = chooser.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {

				foto.setIcon((new ImageIcon(chooser.getSelectedFile().getPath())));
				ContadorMudarFoto = 2;

			}

		}

		if (mouseClick.getSource() == salvar) {

			try {

				VerificandoErros verificando = new VerificandoErros();

				boolean v = verificando.verificandoCliente(
						campoCodigo.getText(), campoNomeCompleto.getText(),
						(String) comboSexo.getSelectedItem(),
						campoCpf.getText(), campoRg.getText(),
						campoOrgaoEmissor.getText(),
						campoDataDeEmissao.getText(),
						campoNacionalidade.getText(), campoRua.getText(),
						campoNumero.getText(), campoComplemento.getText(),
						campoBairro.getText(), campoCidade.getText(),
						(String) comboEstado.getSelectedItem(),
						campoCep.getText(), campoEmail.getText(),
						campoEstadoCivil.getText(), campoConjugue.getText(),
						campoPai.getText(), campoMae.getText(),
						campoTrabalho.getText(), campoCargo.getText(),
						campoTempoDeServico.getText(),
						campoEnderecoOndeTrabalha.getText(),
						campoNumeroTrabalho.getText(),
						campoComplementoTrabalho.getText(),
						campoBairroTrabalho.getText(), campoCidade.getText(),
						(String) comboEstadoTrabalho.getSelectedItem(),
						campoCepTrabalho.getText(),
						campoEmailTrabalho.getText(),
						campoObservacoesTrabalho.getText(),
						campoObservacoesAdicionais.getText());

				if (v == true) {

					Cliente cliente = new Cliente();

					cliente.setCodigo(Integer.parseInt(campoCodigo.getText()));
					cliente.setNomeCompleto(campoNomeCompleto.getText());

					if (comboSexo.getSelectedItem().equals("Selecione ...")) {

						cliente.setSexo("");

					}

					else {

						cliente.setSexo((String) comboSexo.getSelectedItem());

					}

					cliente.setCpf(campoCpf.getText());
					cliente.setRg(Integer.parseInt(campoRg.getText()));
					cliente.setOrgaoEmissor(campoOrgaoEmissor.getText());
					cliente.setDataDeEmissao(campoDataDeEmissao.getText());
					cliente.setNacionalidade(campoNacionalidade.getText());

					cliente.setRua(campoRua.getText());
					cliente.setNumero(campoNumero.getText());
					cliente.setComplemento(campoComplemento.getText());
					cliente.setBairro(campoBairro.getText());
					cliente.setCidade(campoCidade.getText());

					if (comboEstado.getSelectedItem().equals("Selecione ...")) {

						cliente.setEstado("");

					}

					else {

						cliente.setEstado((String) comboEstado
								.getSelectedItem());

					}

					cliente.setCep(campoCep.getText());
					cliente.setTelefone01(campoTelefone01.getText());
					cliente.setTelefone02(campoTelefone02.getText());
					cliente.setEmail(campoEmail.getText());
					cliente.setDataDeNascimento(campoDataDeNascimento.getText());
					cliente.setEstadoCivil(campoEstadoCivil.getText());
					cliente.setConjugue(campoConjugue.getText());
					cliente.setPai(campoPai.getText());
					cliente.setMae(campoMae.getText());

					cliente.setTrabalho(campoTrabalho.getText());
					cliente.setCargo(campoCargo.getText());
					cliente.setTempoServico(campoTempoDeServico.getText());

					cliente.setEnderecoOndeTrabalha(campoEnderecoOndeTrabalha
							.getText());

					cliente.setNumeroTrabalho(campoNumeroTrabalho.getText());

					cliente.setComplementoTrabalho(campoComplementoTrabalho
							.getText());

					cliente.setBairroTrabalho(campoBairroTrabalho.getText());
					cliente.setCidadeTrabalho(campoCidadeTrabalho.getText());

					if (comboEstadoTrabalho.getSelectedItem().equals(
							"Selecione ...")) {

						cliente.setEstadoTrabalho("");

					}

					else {

						cliente.setEstadoTrabalho((String) comboEstadoTrabalho
								.getSelectedItem());

					}

					cliente.setCepTrabalho(campoCepTrabalho.getText());
					cliente.setTelefoneTrabalho(campoTelefoneTrabalho.getText());
					cliente.setFaxTrabalho(campoFaxTrabalho.getText());
					cliente.setEmailTrabalho(campoEmailTrabalho.getText());
					cliente.setSobreTrabalho(campoObservacoesTrabalho.getText());

					cliente.setObservacoesAdicionais(campoObservacoesAdicionais
							.getText());

					cliente.setDataDeCadastro(campoDataDeCadastro.getText());

					FachadaClientes enviandoValores = new FachadaClientes();
					enviandoValores.adicionandoCliente(cliente);

				}

				else {

				}

			}

			catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {

				new ErroEncontrado();

			}

			catch (SQLException e) {

				new ErroEncontrado();

			}

		}

		else if (mouseClick.getSource() == webcam) {

			new Capturando();

		}

		else if (mouseClick.getSource() == cancelar) {

			this.dispose();

		}

	}

	@Override
	public void mouseEntered(MouseEvent mouseEntry) {

		if (mouseEntry.getSource() == foto & ContadorMudarFoto == 1) {

			foto.setIcon(new ImageIcon(
					CadastrarClientes.class
							.getResource("/views/cadastros/cad_clientes/jpg/Foto_Mouse.jpg")));

		}

		if (mouseEntry.getSource() == webcam) {

			webcam.setIcon(new ImageIcon(
					CadastrarClientes.class
							.getResource("/views/cadastros/cad_clientes/png/webcam_mouse.png")));

		}

	}

	@Override
	public void mouseExited(MouseEvent mouseExit) {

		if (mouseExit.getSource() == foto & ContadorMudarFoto == 1) {

			foto.setIcon(new ImageIcon(CadastrarClientes.class
					.getResource("/views/cadastros/cad_clientes/jpg/Foto.jpg")));

		}

		if (mouseExit.getSource() == webcam) {

			webcam.setIcon(new ImageIcon(
					CadastrarClientes.class
							.getResource("/views/cadastros/cad_clientes/png/webcam.png")));

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

	@Override
	public void keyPressed(KeyEvent keyPress) {
		
		if (keyPress.getKeyCode() == KeyEvent.VK_ESCAPE){
			
			this.dispose();
			
		}

	}

	@Override
	public void keyReleased(KeyEvent keyReal) {

	}

	@Override
	public void keyTyped(KeyEvent keyType) {

	}

	private String pegandoDataDoSistema() {

		Date now = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String s = df.format(now);

		return s;

	}

}