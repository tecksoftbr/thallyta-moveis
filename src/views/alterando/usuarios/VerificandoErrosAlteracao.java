package views.alterando.usuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import modelo.Usuario;
import views.principais.tela_de_erro.ErroEncontrado;

import com.mysql.jdbc.PreparedStatement;

public class VerificandoErrosAlteracao {

	// Classe de verificação de erros nas informações digitadas pelo usuário ...

	public static boolean verificandoErros(Usuario usu, String apelidoOriginal)
			throws SQLException {

		try {

			// Adicionando visual do windows ao jframe ...

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			// Se o campo de nome completo estiver apenas com espaços, ou seja
			// vazio ...

			if (usu.getNomeCompleto().trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Nome Completo, Este Campo é Obrigatório",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se o campo de apelido estiver apenas com espaços, ou seja vazio
			// ...

			else if (usu.getApelido().trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Apelido, Este Campo é Obrigatório",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se o campo de senha estiver apenas com espaços, ou seja vazio ...

			else if (usu.getSenha().trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Senha, Este Campo é Obrigatório",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se o campo de pergunta secreta estiver apenas com espaços, ou
			// seja vazio ...

			else if (usu.getPerguntaSecreta().trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Pergunta Secreta, Este Campo é Obrigatório",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Se o campo de resposta secreta estiver apenas com espaços, ou
			// seja vazio ...

			else if (usu.getRespostaSecreta().trim().isEmpty()) {

				JOptionPane
						.showMessageDialog(
								null,
								"Não Há Letras Ou Números No Campo De Resposta Secreta, Este Campo é Obrigatório",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se já tem no banco de dados ...

			if (apelidoOriginal.equals(usu.getApelido())) {

			}

			else {

				Connection conexao = DriverManager.getConnection(
						"jdbc:mysql://localhost/thallyta_moveis", "root", "");

				// Comando sql para procurar no banco de dados se já existe o
				// apelido digitado pelo usuário ...

				String sql = "select apelido from usuarios";

				try (PreparedStatement stm = (PreparedStatement) conexao
						.prepareStatement(sql);
						ResultSet rs = stm.executeQuery()) {

					while (rs.next()) {

						// Se ouver este apelido já cadastrado, uma mensagem de
						// aviso é apresentada e também false é voltado ...

						if (usu.getApelido().equals(rs.getString(1))) {

							JOptionPane
									.showMessageDialog(
											null,
											"Este Apelido Já Está Sendo Usado Por Outra Pessoa, Tente Outro",
											"Thallyta Móveis - Aviso Do Sistema",
											JOptionPane.ERROR_MESSAGE);

							return false;

						}

					}

				}

			}

		}

		// Caso algum erro ocorra ...

		catch (Exception e) {

			new ErroEncontrado();

		}

		// Por padarão o retorno deve ser true ...

		return true;

	}

}