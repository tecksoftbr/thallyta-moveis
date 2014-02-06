package views.cadastros.cad_clientes;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

import fachada.FachadaClientes;

public class VerificandoErros {

	public boolean verificandoCliente(String codigo, String nomeCompleto,
			String sexo, String cpf, String rg, String orgaoEmissor,
			String dataDeEmissao, String nacionalidade, String rua,
			String numero, String complemento, String bairro, String cidade,
			String estado, String cep, String email, String estadoCivil,
			String conjugue, String pai, String mae, String trabalho,
			String cargo, String tempoServico, String enderecoOndeTrabalha,
			String numeroTrabalho, String complementoTrabalho,
			String bairroTrabalho, String cidadeTrabalho,
			String estadoTrabalho, String cepTrabalho, String emailTrabalho,
			String observacoesTrabalho, String observacoesAdicionais)

	throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException,
			SQLException {

		char[] codigoLetra = codigo.toCharArray();

		// Se n�o h� nada neste campo ...

		if (codigo.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que n�o h� n�meros no campo de c�digo, por favor digite algum ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// Se os d�gitos forem > 6 ...

		if (codigoLetra.length >= 7) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que este c�digo tem mais de 6 d�gitos, digite um com menor valor ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// Verificando se h� letras no campo de c�digo ...

		boolean verificandoLetras = soContemNumeros(codigo);

		if (verificandoLetras == false) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que h� letras no campo de c�digo, n�o � permitido ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		FachadaClientes verificandoCodigo = new FachadaClientes();
		boolean vC = verificandoCodigo.verificandoCodigo(codigo);

		if (vC == false) {

			JOptionPane
					.showMessageDialog(
							null,
							"Este c�digo j� est� cadastrado no banco de dados, por favor selecione outro ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		if (nomeCompleto.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que n�o h� nada no campo de nome completo, por favor digite algum ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoNomeCompleto = nomeCompleto.toCharArray();

		if (separandoNomeCompleto.length > 255) {

			JOptionPane
					.showMessageDialog(
							null,
							"Descupe, mais o nome completo do cliente foi digitado muito grande (Permitido at� 255 letras ou n�meros) ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		if (sexo.equals("Selecione ...")) {

			JOptionPane
					.showMessageDialog(
							null,
							"Voc� ainda n�o selecionou um sexo, esta informa��o � obrigat�ria para o cadastro ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		boolean v = validacpf(cpf);

		if (v == false) {

			JOptionPane
					.showMessageDialog(
							null,
							"Descupe, mais o cpf digitado est� errado, por favor verifique oque foi digitado ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		if (rg.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que n�o h� nada no campo de rg, por favor digite algum ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoRG = rg.toCharArray();

		if (separandoRG.length > 20) {

			JOptionPane
					.showMessageDialog(
							null,
							"O valor digitado no campo RG est� muito alto, favor digitar o RG correto ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		for (int i = 0; i < separandoRG.length; i++) {

			if (!Character.isDigit(separandoRG[i])) {

				JOptionPane
						.showMessageDialog(
								null,
								"H� letra (s) no campo de RG, por favor digite apenas n�meros ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoOrgaoEmissor = orgaoEmissor.toCharArray();

		if (separandoOrgaoEmissor.length > 10) {

			JOptionPane.showMessageDialog(null,
					"Org�o emissor muito algo, por favor digite um v�lido ...",
					"Thallyta M�veis - Aviso Do Sistema",
					JOptionPane.ERROR_MESSAGE);

			return false;

		}

		if (orgaoEmissor.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que n�o h� nada no campo de org�o emissor, por favor digite algum ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoDataDeEmissao = dataDeEmissao.toCharArray();

		for (int i = 0; i < separandoDataDeEmissao.length; i++) {

			if (separandoDataDeEmissao[i] == ' ') {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que a data de emiss�o do RG digitada est� incorreta, favor tente novamente ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

		}

		Pattern p2 = Pattern
				.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");

		Matcher m2 = p2.matcher(dataDeEmissao);

		if (!m2.find()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que a data de emiss�o digitada est� incorreta, tente outra ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		if (nacionalidade.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que n�o h� nada no campo de nacionalidade, esta informa��o � obrigat�ria ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoNacionalidade = nacionalidade.toCharArray();

		if (separandoNacionalidade.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"A nacionalidade foi digitada muito alta, favor corrigir este erro ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoRua = rua.toCharArray();

		if (separandoCampoRua.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"O nome da rua foi digitado grande demais, valor m�ximo permitido � de 50 letras ou n�meros ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoNumero = numero.toCharArray();

		if (separandoCampoNumero.length > 10) {

			JOptionPane
					.showMessageDialog(
							null,
							"O n�mero foi digitado grande demais, valor m�ximo permitido � de 10 ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoComplemento = complemento.toCharArray();

		if (separandoCampoComplemento.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"O complemento foi digitado grande demais, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoBairro = bairro.toCharArray();

		if (separandoCampoBairro.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"O bairro foi digitado grande demais, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoCidade = cidade.toCharArray();

		if (separandoCampoCidade.length > 20) {

			JOptionPane
					.showMessageDialog(
							null,
							"O nome da cidade foi digitado grande demais, valor m�ximo permitido � de 20 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoEmail = email.toCharArray();

		if (separandoCampoEmail.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"O e-mail deste cliente foi digitado grande demais, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoEstadoCivil = estadoCivil.toCharArray();

		if (separandoCampoEstadoCivil.length > 20) {

			JOptionPane
					.showMessageDialog(
							null,
							"O estado civil deste cliente foi digitado grande demais, valor m�ximo permitido � de 20 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoConjugue = conjugue.toCharArray();

		if (separandoCampoConjugue.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo conjugue deste cliente est�o incorretas, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoPai = pai.toCharArray();

		if (separandoCampoPai.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo pai deste cliente est�o incorretas, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoMae = mae.toCharArray();

		if (separandoCampoMae.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo m�e deste cliente est�o incorretas, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoTrabalho = trabalho.toCharArray();

		if (separandoCampoTrabalho.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo trabalho deste cliente est�o incorretas, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoCargo = cargo.toCharArray();

		if (separandoCampoCargo.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo cargo/fun��o deste cliente est�o incorretas, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoTempoDeServico = tempoServico.toCharArray();

		if (separandoCampoTempoDeServico.length > 20) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo tempo de servi�o deste cliente est�o incorretas, valor m�ximo permitido � de 20 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoEnderecoTrabalho = enderecoOndeTrabalha
				.toCharArray();

		if (separandoCampoEnderecoTrabalho.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo endere�o de trabalho deste cliente est�o incorretas, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoNumeroTrabalho = numeroTrabalho.toCharArray();

		if (separandoCampoNumeroTrabalho.length > 20) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo n�mero de trabalho deste cliente est�o incorretas, valor m�ximo permitido � de 20 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoComplementoTrabalho = complementoTrabalho
				.toCharArray();

		if (separandoCampoComplementoTrabalho.length > 200) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo complemento de trabalho deste cliente est�o incorretas, valor m�ximo permitido � de 200 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoBairroTrabalho = bairroTrabalho.toCharArray();

		if (separandoCampoBairroTrabalho.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo bairro de trabalho deste cliente est�o incorretas, valor m�ximo permitido � de 200 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoCidadeTrabalho = cidadeTrabalho.toCharArray();

		if (separandoCampoCidadeTrabalho.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo cidade de trabalho deste cliente est�o incorretas, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoEmailTrabalho = emailTrabalho.toCharArray();

		if (separandoCampoEmailTrabalho.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo e-mail de trabalho deste cliente est�o incorretas, valor m�ximo permitido � de 50 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoObservacoesTrabalho = observacoesTrabalho
				.toCharArray();

		if (separandoCampoObservacoesTrabalho.length > 255) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo observa��es sobre trabalho deste cliente est�o incorretas, valor m�ximo permitido � de 255 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoObservacoeAdicionais = observacoesAdicionais
				.toCharArray();

		if (separandoCampoObservacoeAdicionais.length > 255) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informa��es do campo observa��es adicionais deste cliente est�o incorretas, valor m�ximo permitido � de 255 n�meros ou letras ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		return true;

	}

	// Verificando se h� letras ...

	public static boolean soContemNumeros(String texto) {

		char[] separando = texto.toCharArray();
		boolean verif = true;

		for (int i = 0; i < separando.length; i++) {

			if (!Character.isDigit(separando[i])) {

				verif = false;
				break;

			}

		}

		return verif;

	}

	public boolean validacpf(String cpf) {

		char[] separando = cpf.toCharArray();

		for (int i = 0; i < separando.length; i++) {

			if (separando[i] == ' ') {

				return false;

			}

		}

		return true;

	}

}