package views.alterando.lembretes;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import views.principais.tela_de_erro.ErroEncontrado;
import fachada.FachadaLembretes;

public class VerificandoErrosAlteracao {

	public boolean verificandoErros(String codigo, String titulo,
			String dataDeAviso, String descricao, String codigoOriginal)
			throws SQLException {

		try {

			// Aparência do windows ...

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

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

			// Verificando código no banco de dados ...

			FachadaLembretes verificando = new FachadaLembretes();
			boolean verificandoBD = verificando.verificandoCodigo(codigo);

			if (codigo.equals(codigoOriginal)) {

			}

			else if (verificandoBD == false) {

				JOptionPane
						.showMessageDialog(
								null,
								"Descupe, mais este código já está cadastrado com outro lembrete ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se o campo título contém letras ou números ...

			if (titulo.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Não há nada no campo de título, por favor digitar alguma palavra ou frase ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se o campo descrição contém letras ou números ...

			if (descricao.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Não há nada no campo de descrição, por favor digitar alguma palavra ou frase ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se a data está incorreta ...

			Pattern p = Pattern
					.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");

			Matcher m = p.matcher(dataDeAviso);

			if (!m.find()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que a data digitada está incorreta, tente outra ...",
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

}