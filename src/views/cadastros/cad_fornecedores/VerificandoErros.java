package views.cadastros.cad_fornecedores;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import fachada.FachadaFornecedores;

import views.principais.tela_de_erro.ErroEncontrado;

public class VerificandoErros {

	public boolean verificandoAgora(String codigo, String empresa, String rua,
			String bairro, String cidade, String email, String site,
			String observacoes) throws SQLException {

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

		if (empresa.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Campo empresa está vazio, digite nele o nome do fornecedor que deseja cadastrar ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoEmpresa = empresa.toCharArray();

		if (separandoEmpresa.length > 100) {

			JOptionPane
					.showMessageDialog(
							null,
							"Você digitou no campo empresa um valor muito alto (Valor máximo permitido: 100 letras ou números) ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoRua = rua.toCharArray();

		if (separandoRua.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"Você digitou no campo rua um valor muito alto (Valor máximo permitido: 50 letras ou números) ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoBairro = bairro.toCharArray();

		if (separandoBairro.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"Você digitou no campo bairro um valor muito alto (Valor máximo permitido: 50 letras ou números) ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoCidade = cidade.toCharArray();

		if (separandoCidade.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"Você digitou no campo cidade um valor muito alto (Valor máximo permitido: 50 letras ou números) ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoEmail = email.toCharArray();

		if (separandoEmail.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"Você digitou no campo e-mail um valor muito alto (Valor máximo permitido: 50 letras ou números) ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoSite = site.toCharArray();

		if (separandoSite.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"Você digitou no campo site um valor muito alto (Valor máximo permitido: 50 letras ou números) ...",
							"Thallyta Móveis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoObservacoes = observacoes.toCharArray();

		if (separandoObservacoes.length > 9999) {

			JOptionPane
					.showMessageDialog(
							null,
							"Você digitou no campo observacoes um valor muito alto (Valor máximo permitido: 9999 letras ou números) ...",
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