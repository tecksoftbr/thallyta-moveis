package fachada;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Usuario;
import repositorio.RepositorioUsuario;
import views.principais.tela_de_erro.ErroEncontrado;

public class FachadaUsuarios {

	private final String url = "jdbc:mysql://localhost/thallyta_moveis";
	private final String usuario = "root";
	private final String senha = "";

	// Adiciona um usuário ao banco de dados ...

	public void adicionarUsuario(Usuario usu) throws SQLException {

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioUsuario cadastrandoBD = new RepositorioUsuario();

			cadastrandoBD.cadastrandoUsuario(
					(com.mysql.jdbc.Connection) conexao, usu);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	// Buscando usuário com um retorno boolean ...

	public boolean buscandoUsuario(Usuario usuario) {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioUsuario buscandoUsuario = new RepositorioUsuario();

			boolean veirificandoNoRepositorio = buscandoUsuario
					.buscandoUsuario(conexao, usuario);

			if (veirificandoNoRepositorio == true) {

				return true;

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		return false;

	}

	// Método usado para realizar uma busca no banco de dados para alteração de
	// senha ...

	public Usuario verificandoUsuario(String nomeOuApelido) {

		Usuario usu = new Usuario();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioUsuario verificandoUsuario = new RepositorioUsuario();
			usu = verificandoUsuario.verificandoUsuario(conexao, nomeOuApelido);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		return usu;

	}

	// Método de alteração de senha ...

	public void alterandoSenha(Usuario usuario) {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioUsuario chamandoMetodo = new RepositorioUsuario();
			chamandoMetodo.alterandoSenha(conexao, usuario);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	// Listando usuários ...

	public ArrayList<Usuario> listandoUsuarios() throws SQLException {

		ArrayList<Usuario> usuarios = new ArrayList<>();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioUsuario retornandoUsuarios = new RepositorioUsuario();
			usuarios = retornandoUsuarios.listandoUsuarios(conexao);

		}

		return usuarios;

	}

	// Removendo Usuários ...

	public void removendoUsuarios(Usuario usuario) {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioUsuario chamandoMetodo = new RepositorioUsuario();
			chamandoMetodo.removerUsuario(usuario, conexao);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	public void alterandoUsuario(String apelidoOriginal, Usuario usu)
			throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioUsuario gravando = new RepositorioUsuario();
			gravando.alterandoUsuario(conexao, apelidoOriginal, usu);

		}

	}

}