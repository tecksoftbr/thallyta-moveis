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

		// Verificando se n�o h� nada no campo ...

		if (quantidade.trim().isEmpty() == true) {

			JOptionPane
					.showMessageDialog(
							null,
							"N�o H� Nada No Campo De Quantidade, Favor Digite Algo ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		// Verificando se h� letras no campo quantidade ...

		boolean verificandoLetras = soContemNumeros(quantidade);

		if (verificandoLetras == false) {

			JOptionPane
					.showMessageDialog(
							null,
							"H� Letras No Campo De Quantidade, Favor Digitar Apenas Valores Num�ricos ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] letras = quantidade.toCharArray();

		if (letras.length > 6) {

			JOptionPane
					.showMessageDialog(
							null,
							"Esta Quantidade � Muito Alta Para Adicionar Ao Produto (Permitido Apenas 6 N�meros) ...",
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

}