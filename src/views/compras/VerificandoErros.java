package views.compras;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class VerificandoErros {

	public boolean verificando(String quantidade)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {

		UIManager
				.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		// Verificando se não há nada no campo ...

		if (quantidade.trim().isEmpty() == true) {

			JOptionPane
					.showMessageDialog(
							null,
							"Não Há Nada No Campo De Quantidade, Favor Digite Algo ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// Verificando se há letras no campo quantidade ...

		boolean verificandoLetras = soContemNumeros(quantidade);

		if (verificandoLetras == false) {

			JOptionPane
					.showMessageDialog(
							null,
							"Há Letras No Campo De Quantidade, Favor Digitar Apenas Valores Numéricos ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] letras = quantidade.toCharArray();

		if (letras.length > 6) {

			JOptionPane
					.showMessageDialog(
							null,
							"Esta Quantidade é Muito Alta Para Adicionar Ao Produto (Permitido Apenas 6 Números) ...",
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