package fachada;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import repositorio.RepositorioAjuda;

import modelo.Ajuda;

public class FachadaAjuda {

	private final String url = "jdbc:mysql://localhost/thallyta_moveis";
	private final String usuario = "root";
	private final String senha = "";

	private ArrayList<Ajuda> ajudas = new ArrayList<>();

	public ArrayList<Ajuda> listandoAjuda() throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioAjuda resgatandoAjudas = new RepositorioAjuda();
			ajudas = resgatandoAjudas.listandoAjudas(conexao);

		}

		return ajudas;

	}

}