package fachada;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import repositorio.RepositorioAvisos;

import modelo.Cliente;
import modelo.ContasPagar;
import modelo.Lembrete;

public class FachadaAvisos {

	private static final String url = "jdbc:mysql://localhost/thallyta_moveis";
	private static final String usuario = "root";
	private static final String senha = "";

	public ArrayList<ContasPagar> contasHoje() throws SQLException {

		ArrayList<modelo.ContasPagar> contas = new ArrayList<>();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioAvisos retornando = new RepositorioAvisos();
			contas = retornando.retornandoContas(conexao);

		}

		return contas;

	}

	public ArrayList<Lembrete> lembretesHoje() throws SQLException {

		ArrayList<modelo.Lembrete> lembretes = new ArrayList<>();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioAvisos retornando = new RepositorioAvisos();
			lembretes = retornando.retornandoLembretes(conexao);

		}

		return lembretes;

	}

	public ArrayList<Cliente> aniversariosHoje() throws SQLException {

		ArrayList<modelo.Cliente> clientes = new ArrayList<>();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioAvisos retornando = new RepositorioAvisos();
			clientes = retornando.retornandoClientes(conexao);

		}

		return clientes;

	}

}