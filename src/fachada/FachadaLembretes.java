package fachada;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import repositorio.RepositorioLembrete;
import views.principais.tela_de_erro.ErroEncontrado;
import modelo.Lembrete;

public class FachadaLembretes {

	private static final String url = "jdbc:mysql://localhost/thallyta_moveis";
	private static final String usuario = "root";
	private static final String senha = "";

	public void adicionandoLembreteVenda(Lembrete lembrete) throws SQLException {

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioLembrete adicionando = new RepositorioLembrete();
			adicionando.gravandoLembreteEntrega(lembrete, conexao);

		}

	}

	public void adicionarLembrete(Lembrete lem) throws SQLException {

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioLembrete cadastrandoBD = new RepositorioLembrete();
			cadastrandoBD.adicionarLembrete(conexao, lem);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	public ArrayList<Lembrete> listandoLembretes() throws SQLException {

		ArrayList<Lembrete> lembretes = new ArrayList<>();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioLembrete resgatandoLembretes = new RepositorioLembrete();
			lembretes = resgatandoLembretes.listandoLembretes(conexao);

		}

		return lembretes;

	}

	// Verificando se o código digitado já está contido no banco de dados ...

	public boolean verificandoCodigo(String codigo) throws SQLException {

		boolean verif = true;

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioLembrete verificando_BD = new RepositorioLembrete();
			verif = verificando_BD.verificandoCodigo_BD(conexao, codigo);

		}

		return verif;

	}

	// Removendo Lembretes ...

	public void removendoLembrete(String codigo) {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioLembrete removendo = new RepositorioLembrete();
			removendo.removendoLembrete(conexao, codigo);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	public void alterandoLembrete(String codigoOriginal, Lembrete lembrete)
			throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioLembrete passandoLembrete = new RepositorioLembrete();

			passandoLembrete.alterandoLembrete(conexao, codigoOriginal,
					lembrete);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

}