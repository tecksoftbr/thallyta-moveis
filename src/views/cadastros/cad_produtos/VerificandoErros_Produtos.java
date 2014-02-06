package views.cadastros.cad_produtos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import views.principais.tela_de_erro.ErroEncontrado;

import com.mysql.jdbc.PreparedStatement;

import fachada.FachadaProdutos;

public class VerificandoErros_Produtos {

	// Verificando Erros De Produto ...

	public boolean verrificandoErros_Produto(String codigo, String descricao,
			String categoria, String precoDeCompra, String precoDeVenda,
			String quantidade, String marca, String modelo,
			String numeroDeSerie, String cor, String observacoes)
			throws SQLException {

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			char desmachandoMarca[] = marca.toCharArray();

			if (desmachandoMarca.length > 100) {

				JOptionPane
						.showMessageDialog(
								null,
								"Campo Marca Com Quantidade Muito Alta Para Armazenamento, (Valor Máximo: 100) ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			char desmachandoModelo[] = modelo.toCharArray();

			if (desmachandoModelo.length > 100) {

				JOptionPane
						.showMessageDialog(
								null,
								"Campo Modelo Com Quantidade Muito Alta Para Armazenamento, (Valor Máximo: 100) ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			char desmachandoNumeroDeSerie[] = numeroDeSerie.toCharArray();

			if (desmachandoNumeroDeSerie.length > 100) {

				JOptionPane
						.showMessageDialog(
								null,
								"Campo Número De Série Com Quantidade Muito Alta Para Armazenamento, (Valor Máximo: 100) ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			char desmachandoCor[] = cor.toCharArray();

			if (desmachandoCor.length > 20) {

				JOptionPane
						.showMessageDialog(
								null,
								"Campo Cor Com Quantidade Muito Alta Para Armazenamento, (Valor Máximo: 20) ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			char desmachandoObservacoes[] = observacoes.toCharArray();

			if (desmachandoObservacoes.length > 1000) {

				JOptionPane
						.showMessageDialog(
								null,
								"Campo Observações Com Quantidade Muito Alta Para Armazenamento, (Valor Máximo: 1000) ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se e quantidade estiver com + de 6 dígitos ...

			char[] quantidadeLetras = quantidade.toCharArray();

			if (quantidadeLetras.length >= 7) {

				JOptionPane
						.showMessageDialog(
								null,
								"Quantidade errada, tente colocar algum valor abaixo de 6 dígitos ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se campo quantidade está vazio ...

			if (quantidade.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"O campo de quantidade está vazio, insira algum valor menor ou igual que 6 dígitos ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se há letras no campo de quantidade ...

			boolean verificando = soContemNumeros(quantidade);

			if (verificando == false) {

				JOptionPane
						.showMessageDialog(
								null,
								"No campo quantidade há valor (es) incorreto (s), como alguma letra digitada ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando preço de compra ...

			boolean verificandoValor = verificandoValor(precoDeCompra);

			if (verificandoValor == true) {

			}

			else {

				JOptionPane
						.showMessageDialog(
								null,
								"O preço de compra digitado está errado, tente colocar um valor com um decimal correto (Ex: 2.30) ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando preço de venda ...

			boolean verificandoValor_Venda = verificandoValor(precoDeVenda);

			if (verificandoValor_Venda == true) {

			}

			else {

				JOptionPane
						.showMessageDialog(
								null,
								"O preço de venda digitado está errado, tente colocar um valor com um decimal correto (Ex: 2.30) ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando a existencia de muitos dígitos em preco de compra ...

			char[] valorSeparado = precoDeCompra.toCharArray();

			if (valorSeparado.length > 8) {

				JOptionPane
						.showMessageDialog(
								null,
								"O preço de compra digitado está alto para ser armazenado, tente colocar um valor menor ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando a existencia de muitos dígitos em preco de compra ...

			char[] valorSeparado_Venda = precoDeVenda.toCharArray();

			if (valorSeparado_Venda.length > 8) {

				JOptionPane
						.showMessageDialog(
								null,
								"O preço de venda digitado está alto para ser armazenado, tente colocar um valor menor ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando a existência de vírgula no preço de compra ...

			char[] valorDigitado = precoDeCompra.toCharArray();

			for (int i = 0; i < valorDigitado.length; i++) {

				if (valorDigitado[i] == ',') {

					JOptionPane
							.showMessageDialog(
									null,
									"Você digitou no campo preço de compra, uma vírgula (por favor adicione um ponto no lugar dela) ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

					return false;

				}

			}

			// Verificando a existência de vírgula no preço de compra ...

			char[] valorDigitado_Venda = precoDeVenda.toCharArray();

			for (int i = 0; i < valorDigitado_Venda.length; i++) {

				if (valorDigitado_Venda[i] == ',') {

					JOptionPane
							.showMessageDialog(
									null,
									"Você digitou no campo preço de venda, uma vírgula (por favor adicione um ponto no lugar dela) ...",
									"Thallyta Móveis - Aviso Do Sistema",
									JOptionPane.ERROR_MESSAGE);

					return false;

				}

			}

			// Se a categoria for igual a selecionar ...

			if (categoria.equals("Selecionar ...")) {

				JOptionPane
						.showMessageDialog(
								null,
								"Você esqueceu de selecionar alguma categoria. Esta categoria não pode ser cadastrada ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se o campo descrição não conter letras ou números ...

			if (descricao.trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Campo descrição está vazio, digite nele o nome do produto que deseja cadastrar ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			char letrasDescricao[] = descricao.toCharArray();

			if (letrasDescricao.length > 255) {

				JOptionPane
						.showMessageDialog(
								null,
								"Descrição do produto muito grande. Digite uma menor (Até 255 letras ou números) ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

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

			FachadaProdutos verif = new FachadaProdutos();
			boolean verificado = verif.verificandoCodigo(codigo);

			if (verificado == true) {

				return true;

			}

			else {

				JOptionPane
						.showMessageDialog(
								null,
								"Descupe, mais este código já está cadastrado com outro produto ...",
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

	// Verificando Erros De Categoria ...

	public static boolean verificarErros_Categoria(String categoria) {

		try {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			Connection conexao = DriverManager.getConnection(
					"jdbc:mysql://localhost/thallyta_moveis", "root", "");

			String sql = "select nome from categorias";

			try (PreparedStatement stm = (PreparedStatement) conexao
					.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

				while (rs.next()) {

					if (categoria.equals(rs.getString(1))) {

						JOptionPane
								.showMessageDialog(
										null,
										"Esta categoria já está cadastrada, tente outra ...",
										"Thallyta Móveis - Aviso Do Sistema",
										JOptionPane.ERROR_MESSAGE);

						return false;

					}

				}

			}

			if (categoria.equals("Selecionar ...")) {

				JOptionPane
						.showMessageDialog(
								null,
								"Esta Categoria Não Pode Ser Armazenada, Tente Outra ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			char[] letras = categoria.toCharArray();

			if (letras.length > 100) {

				JOptionPane
						.showMessageDialog(
								null,
								"Você digitou um valor muito alto no campo categoria, tente novamente ...",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			for (int i = 0; i < letras.length; i++) {

				if (letras[i] != ' ') {

					return true;

				}

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		return false;

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

	// Verificando valor digitado ...

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