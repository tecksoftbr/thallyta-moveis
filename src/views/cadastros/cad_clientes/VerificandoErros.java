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

		// Se não há nada neste campo ...

		if (codigo.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que não há números no campo de código, por favor digite algum ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// Se os dígitos forem > 6 ...

		if (codigoLetra.length >= 7) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que este código tem mais de 6 dígitos, digite um com menor valor ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// Verificando se há letras no campo de código ...

		boolean verificandoLetras = soContemNumeros(codigo);

		if (verificandoLetras == false) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que há letras no campo de código, não é permitido ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		FachadaClientes verificandoCodigo = new FachadaClientes();
		boolean vC = verificandoCodigo.verificandoCodigo(codigo);

		if (vC == false) {

			JOptionPane
					.showMessageDialog(
							null,
							"Este código já está cadastrado no banco de dados, por favor selecione outro ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		if (nomeCompleto.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que não há nada no campo de nome completo, por favor digite algum ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoNomeCompleto = nomeCompleto.toCharArray();

		if (separandoNomeCompleto.length > 255) {

			JOptionPane
					.showMessageDialog(
							null,
							"Descupe, mais o nome completo do cliente foi digitado muito grande (Permitido até 255 letras ou números) ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		if (sexo.equals("Selecione ...")) {

			JOptionPane
					.showMessageDialog(
							null,
							"Você ainda não selecionou um sexo, esta informação é obrigatória para o cadastro ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		boolean v = validacpf(cpf);

		if (v == false) {

			JOptionPane
					.showMessageDialog(
							null,
							"Descupe, mais o cpf digitado está errado, por favor verifique oque foi digitado ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		if (rg.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que não há nada no campo de rg, por favor digite algum ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoRG = rg.toCharArray();

		if (separandoRG.length > 20) {

			JOptionPane
					.showMessageDialog(
							null,
							"O valor digitado no campo RG está muito alto, favor digitar o RG correto ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		for (int i = 0; i < separandoRG.length; i++) {

			if (!Character.isDigit(separandoRG[i])) {

				JOptionPane
						.showMessageDialog(
								null,
								"Há letra (s) no campo de RG, por favor digite apenas números ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoOrgaoEmissor = orgaoEmissor.toCharArray();

		if (separandoOrgaoEmissor.length > 10) {

			JOptionPane.showMessageDialog(null,
					"Orgão emissor muito algo, por favor digite um válido ...",
					"Thallyta Móveis - Aviso Do Sistema",
					JOptionPane.ERROR_MESSAGE);

			return false;

		}

		if (orgaoEmissor.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que não há nada no campo de orgão emissor, por favor digite algum ...",
							"Thallyta Móveis - Aviso Do Sistema",
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
								"Verificamos que a data de emissão do RG digitada está incorreta, favor tente novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
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
							"Verificamos que a data de emissão digitada está incorreta, tente outra ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		if (nacionalidade.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Verificamos que não há nada no campo de nacionalidade, esta informação é obrigatória ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoNacionalidade = nacionalidade.toCharArray();

		if (separandoNacionalidade.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"A nacionalidade foi digitada muito alta, favor corrigir este erro ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoRua = rua.toCharArray();

		if (separandoCampoRua.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"O nome da rua foi digitado grande demais, valor máximo permitido é de 50 letras ou números ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoNumero = numero.toCharArray();

		if (separandoCampoNumero.length > 10) {

			JOptionPane
					.showMessageDialog(
							null,
							"O número foi digitado grande demais, valor máximo permitido é de 10 ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoComplemento = complemento.toCharArray();

		if (separandoCampoComplemento.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"O complemento foi digitado grande demais, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoBairro = bairro.toCharArray();

		if (separandoCampoBairro.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"O bairro foi digitado grande demais, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoCidade = cidade.toCharArray();

		if (separandoCampoCidade.length > 20) {

			JOptionPane
					.showMessageDialog(
							null,
							"O nome da cidade foi digitado grande demais, valor máximo permitido é de 20 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoEmail = email.toCharArray();

		if (separandoCampoEmail.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"O e-mail deste cliente foi digitado grande demais, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoEstadoCivil = estadoCivil.toCharArray();

		if (separandoCampoEstadoCivil.length > 20) {

			JOptionPane
					.showMessageDialog(
							null,
							"O estado civil deste cliente foi digitado grande demais, valor máximo permitido é de 20 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoConjugue = conjugue.toCharArray();

		if (separandoCampoConjugue.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informações do campo conjugue deste cliente estão incorretas, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoPai = pai.toCharArray();

		if (separandoCampoPai.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informações do campo pai deste cliente estão incorretas, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoMae = mae.toCharArray();

		if (separandoCampoMae.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informações do campo mãe deste cliente estão incorretas, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoTrabalho = trabalho.toCharArray();

		if (separandoCampoTrabalho.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informações do campo trabalho deste cliente estão incorretas, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoCargo = cargo.toCharArray();

		if (separandoCampoCargo.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informações do campo cargo/função deste cliente estão incorretas, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoTempoDeServico = tempoServico.toCharArray();

		if (separandoCampoTempoDeServico.length > 20) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informações do campo tempo de serviço deste cliente estão incorretas, valor máximo permitido é de 20 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
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
							"As informações do campo endereço de trabalho deste cliente estão incorretas, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoNumeroTrabalho = numeroTrabalho.toCharArray();

		if (separandoCampoNumeroTrabalho.length > 20) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informações do campo número de trabalho deste cliente estão incorretas, valor máximo permitido é de 20 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
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
							"As informações do campo complemento de trabalho deste cliente estão incorretas, valor máximo permitido é de 200 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoBairroTrabalho = bairroTrabalho.toCharArray();

		if (separandoCampoBairroTrabalho.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informações do campo bairro de trabalho deste cliente estão incorretas, valor máximo permitido é de 200 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoCidadeTrabalho = cidadeTrabalho.toCharArray();

		if (separandoCampoCidadeTrabalho.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informações do campo cidade de trabalho deste cliente estão incorretas, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// -----------------------------------------------------------------------------------------------------------------------------------------------------------

		char[] separandoCampoEmailTrabalho = emailTrabalho.toCharArray();

		if (separandoCampoEmailTrabalho.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"As informações do campo e-mail de trabalho deste cliente estão incorretas, valor máximo permitido é de 50 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
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
							"As informações do campo observações sobre trabalho deste cliente estão incorretas, valor máximo permitido é de 255 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
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
							"As informações do campo observações adicionais deste cliente estão incorretas, valor máximo permitido é de 255 números ou letras ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		return true;

	}

	// Verificando se há letras ...

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