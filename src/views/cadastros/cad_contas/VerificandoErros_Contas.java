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

			// Apar�ncia do windows ...

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			// Se n�o h� nada no campo de n�mero de documento ...

			if (numeroDoDocumento.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que n�o h� n�meros no campo de n�mero de documento, por favor digite algum ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			char[] codigoLetra = numeroDoDocumento.toCharArray();

			// Se o n�mero do documento tem mais de 255 caract�res ...

			if (codigoLetra.length >= 256) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que este c�digo tem mais de 255 d�gitos, digite um com menor valor ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se h� letras no campo de n�mero de documento ...

			boolean verificandoLetras = soContemNumeros(numeroDoDocumento);

			if (verificandoLetras == false) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que h� letras no campo de n�mero de documento, n�o � permitido ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se o n�mero existe no banco de dados ...

			FachadaContasPagar verif = new FachadaContasPagar();

			boolean verificando = verif
					.verificandoNumeroDocumento(numeroDoDocumento);

			if (verificando == true) {
			}

			else {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que este n�mero do documento est� cadastrado com outra conta, tente outro ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se n�o h� nada no campo de tipo da conta ...

			if (tipoDaConta.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que n�o h� nada no campo de tipo da conta, por favor digite alguma palavra ou frase ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se n�o h� nada no campo de origem ...

			if (origem.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que n�o h� nada no campo de origem, por favor digite alguma palavra ou frase ...",
								"Thallyta M�veis - Aviso Do Sistema",
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
								"O valor digitado est� errado, tente colocar um valor com um decimal correto (Ex: 2.30) ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando a existencia de muitos d�gitos em valor ...

			char[] valorSeparado = valor.toCharArray();

			if (valorSeparado.length > 8) {

				JOptionPane
						.showMessageDialog(
								null,
								"O valor digitado est� alto para ser armazenado, tente colocar um valor menor ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando a exist�ncia de v�rgula no valor ...

			char[] valorDigitado = valor.toCharArray();

			for (int i = 0; i < valorDigitado.length; i++) {

				if (valorDigitado[i] == ',') {

					JOptionPane
							.showMessageDialog(
									null,
									"Voc� digitou no campo valor, uma v�rgula (por favor adicione um ponto no lugar dela) ...",
									"Thallyta M�veis - Aviso Do Sistema",
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
								"Verificamos que a data de entrada digitada est� incorreta, tente outra ...",
								"Thallyta M�veis - Aviso Do Sistema",
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
								"Verificamos que a data de vencimento digitada est� incorreta, tente outra ...",
								"Thallyta M�veis - Aviso Do Sistema",
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