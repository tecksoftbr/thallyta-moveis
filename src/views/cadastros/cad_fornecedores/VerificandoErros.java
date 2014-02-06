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

		// Verificando se h� no banco dados ...

		FachadaFornecedores verificandoCodigo = new FachadaFornecedores();
		boolean verificandoBD = verificandoCodigo.verificandoCodigo(codigo);

		if (verificandoBD == false) {

			JOptionPane
					.showMessageDialog(
							null,
							"Descupe, mais este c�digo j� est� cadastrado com outro fornecedor ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		if (empresa.trim().isEmpty()) {

			JOptionPane
					.showMessageDialog(
							null,
							"Campo empresa est� vazio, digite nele o nome do fornecedor que deseja cadastrar ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoEmpresa = empresa.toCharArray();

		if (separandoEmpresa.length > 100) {

			JOptionPane
					.showMessageDialog(
							null,
							"Voc� digitou no campo empresa um valor muito alto (Valor m�ximo permitido: 100 letras ou n�meros) ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoRua = rua.toCharArray();

		if (separandoRua.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"Voc� digitou no campo rua um valor muito alto (Valor m�ximo permitido: 50 letras ou n�meros) ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoBairro = bairro.toCharArray();

		if (separandoBairro.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"Voc� digitou no campo bairro um valor muito alto (Valor m�ximo permitido: 50 letras ou n�meros) ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoCidade = cidade.toCharArray();

		if (separandoCidade.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"Voc� digitou no campo cidade um valor muito alto (Valor m�ximo permitido: 50 letras ou n�meros) ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoEmail = email.toCharArray();

		if (separandoEmail.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"Voc� digitou no campo e-mail um valor muito alto (Valor m�ximo permitido: 50 letras ou n�meros) ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoSite = site.toCharArray();

		if (separandoSite.length > 50) {

			JOptionPane
					.showMessageDialog(
							null,
							"Voc� digitou no campo site um valor muito alto (Valor m�ximo permitido: 50 letras ou n�meros) ...",
							"Thallyta M�veis - Aviso Do Sistema",
							JOptionPane.ERROR_MESSAGE);

			return false;

		}

		char[] separandoObservacoes = observacoes.toCharArray();

		if (separandoObservacoes.length > 9999) {

			JOptionPane
					.showMessageDialog(
							null,
							"Voc� digitou no campo observacoes um valor muito alto (Valor m�ximo permitido: 9999 letras ou n�meros) ...",
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