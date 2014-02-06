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

			// Apar�ncia do windows ...

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

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

			// Verificando c�digo no banco de dados ...

			FachadaLembretes verificando = new FachadaLembretes();
			boolean verificandoBD = verificando.verificandoCodigo(codigo);

			if (codigo.equals(codigoOriginal)) {

			}

			else if (verificandoBD == false) {

				JOptionPane
						.showMessageDialog(
								null,
								"Descupe, mais este c�digo j� est� cadastrado com outro lembrete ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se o campo t�tulo cont�m letras ou n�meros ...

			if (titulo.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"N�o h� nada no campo de t�tulo, por favor digitar alguma palavra ou frase ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se o campo descri��o cont�m letras ou n�meros ...

			if (descricao.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"N�o h� nada no campo de descri��o, por favor digitar alguma palavra ou frase ...",
								"Thallyta M�veis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se a data est� incorreta ...

			Pattern p = Pattern
					.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");

			Matcher m = p.matcher(dataDeAviso);

			if (!m.find()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Verificamos que a data digitada est� incorreta, tente outra ...",
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

}