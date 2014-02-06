package views.cadastros.cad_usuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.mysql.jdbc.PreparedStatement;

import views.principais.tela_de_erro.ErroEncontrado;
import modelo.Usuario;

public class VerificandoErros_Usuarios {

	// Classe de verificação de erros nas informações digitadas pelo usuário ...

	public static boolean verificandoErros(Usuario usu) throws SQLException {

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

			char[] separandoNomeCompleto = usu.getNomeCompleto().toCharArray();

			if (separandoNomeCompleto.length > 255) {

				JOptionPane
						.showMessageDialog(
								null,
								"O Nome Do Usuário Foi Digitado Muito Alto, Tente Um Com Menos De 256 Letras Ou Números",
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

			char[] separandoApelido = usu.getApelido().toCharArray();

			if (separandoApelido.length > 255) {

				JOptionPane
						.showMessageDialog(
								null,
								"O Apelido Do Usuário Foi Digitado Muito Alto, Tente Um Com Menos De 256 Letras Ou Números",
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

			char[] separandoSenha = usu.getSenha().toCharArray();

			if (separandoSenha.length > 255) {

				JOptionPane
						.showMessageDialog(
								null,
								"A Senha Do Usuário Foi Digitada Muito Alta, Tente Uma Com Menos De 256 Letras Ou Números",
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

			char[] separandoPergunta = usu.getPerguntaSecreta().toCharArray();

			if (separandoPergunta.length > 255) {

				JOptionPane
						.showMessageDialog(
								null,
								"A Pergunta Secreta Do Usuário Foi Digitada Muito Alta, Tente Uma Com Menos De 256 Letras Ou Números",
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

			char[] separandoResposta = usu.getRespostaSecreta().toCharArray();

			if (separandoResposta.length > 255) {

				JOptionPane
						.showMessageDialog(
								null,
								"A Resposta Secreta Do Usuário Foi Digitada Muito Alta, Tente Uma Com Menos De 256 Letras Ou Números",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			char[] separandoObservacoes = usu.getSobreMim().toCharArray();

			if (separandoObservacoes.length > 9999) {

				JOptionPane
						.showMessageDialog(
								null,
								"O Campo Sobre Mim Do Usuário Foi Digitado Muito Al"
										+ "to, Tente Uma Com Menos De 9999 Letras Ou Números",
								"Thallyta Móveis - Aviso Do Sistema",
								JOptionPane.ERROR_MESSAGE);

				return false;

			}

			// Verificando se já tem no banco de dados ...

			Connection conexao = DriverManager.getConnection(
					"jdbc:mysql://localhost/thallyta_moveis", "root", "");

			// Comando sql para procurar no banco de dados se já existe o
			// apelido digitado pelo usuário ...

			String sql = "select apelido from usuarios";

			try (PreparedStatement stm = (PreparedStatement) conexao
					.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

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

		// Caso algum erro ocorra ...

		catch (Exception e) {

			new ErroEncontrado();

		}

		// Por padarão o retorno deve ser true ...

		return true;

	}

}