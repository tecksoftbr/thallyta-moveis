package views.alterando.fornecedores;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import views.principais.tela_de_erro.ErroEncontrado;
import fachada.FachadaFornecedores;

public class VerificandoErros {

	public boolean verificandoAgora(String codigo, String empresa,
			String codigoOriginal) throws SQLException {

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		}

		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {

			new ErroEncontrado();

		}

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

		// Verificando se há no banco dados ...

		if (codigo.equals(codigoOriginal)) {

		}

		else {

			FachadaFornecedores verificandoCodigo = new FachadaFornecedores();
			boolean verificandoBD = verificandoCodigo.verificandoCodigo(codigo);

			if (verificandoBD == false) {

				JOptionPane
						.showMessageDialog(
								null,
								"Descupe, mais este código já está cadastrado com outro fornecedor ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

		}

		if (empresa.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Campo empresa está vazio, digite nele o nome do fornecedor que deseja cadastrar ...",
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

}