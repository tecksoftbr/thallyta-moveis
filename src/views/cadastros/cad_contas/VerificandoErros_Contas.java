package views.cadastros.cad_contas;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fachada.FachadaContasPagar;
import views.principais.tela_de_erro.ErroEncontrado;

public class VerificandoErros_Contas {

	public static boolean verificando(String numeroDoDocumento, String valor,
			String tipoDaConta, String origem, String dataDeEntrada,
			String dataDeVencimento) throws SQLException {

		try {

			// Aparência do windows ...

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			// Se não há nada no campo de número de documento ...

			if (numeroDoDocumento.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que não há números no campo de número de documento, por favor digite algum ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			char[] codigoLetra = numeroDoDocumento.toCharArray();

			// Se o número do documento tem mais de 255 caractéres ...

			if (codigoLetra.length >= 256) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que este código tem mais de 255 dígitos, digite um com menor valor ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se há letras no campo de número de documento ...

			boolean verificandoLetras = soContemNumeros(numeroDoDocumento);

			if (verificandoLetras == false) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que há letras no campo de número de documento, não é permitido ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se o número existe no banco de dados ...

			FachadaContasPagar verif = new FachadaContasPagar();

			boolean verificando = verif
					.verificandoNumeroDocumento(numeroDoDocumento);

			if (verificando == true) {
			}

			else {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que este número do documento está cadastrado com outra conta, tente outro ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se não há nada no campo de tipo da conta ...

			if (tipoDaConta.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que não há nada no campo de tipo da conta, por favor digite alguma palavra ou frase ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se não há nada no campo de origem ...

			if (origem.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que não há nada no campo de origem, por favor digite alguma palavra ou frase ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando valor digitado - R$ ...

			boolean verificandoValor = verificandoValor(valor);

			if (verificandoValor == true) {

			}

			else {

				JOptionPane
						.showMessageDialog(
								null,
								"O valor digitado está errado, tente colocar um valor com um decimal correto (Ex: 2.30) ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando a existencia de muitos dígitos em valor ...

			char[] valorSeparado = valor.toCharArray();

			if (valorSeparado.length > 8) {

				JOptionPane
						.showMessageDialog(
								null,
								"O valor digitado está alto para ser armazenado, tente colocar um valor menor ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando a existência de vírgula no valor ...

			char[] valorDigitado = valor.toCharArray();

			for (int i = 0; i < valorDigitado.length; i++) {

				if (valorDigitado[i] == ',') {

					JOptionPane
							.showMessageDialog(
									null,
									"Você digitou no campo valor, uma vírgula (por favor adicione um ponto no lugar dela) ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

					return false;

				}

			}

			// Verificando data de entrada ...

			Pattern p1 = Pattern
					.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");

			Matcher m1 = p1.matcher(dataDeEntrada);

			if (!m1.find()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que a data de entrada digitada está incorreta, tente outra ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// verificando data de vencimento ...

			Pattern p2 = Pattern
					.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");

			Matcher m2 = p2.matcher(dataDeVencimento);

			if (!m2.find()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que a data de vencimento digitada está incorreta, tente outra ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

		}

		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {

			new ErroEncontrado();

		}

		return true;

	}

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

	public static boolean verificandoValor(String valor) {

		if (Pattern.matches("(R\\$\\s)?\\d+(\\.?\\d*){0,8}((,\\d\\d)|(,\\d))?",
				valor)) {

			return true;

		}

		else {

			return false;

		}

	}

}