package fachada;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Cliente;

import repositorio.RepositorioCliente;

public class FachadaClientes {

	private final String url = "jdbc:mysql://localhost/thallyta_moveis";
	private final String usuario = "root";
	private final String senha = "";

	public boolean verificandoCodigo(String codigo) throws SQLException {

		boolean verificando = true;

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioCliente verificar = new RepositorioCliente();
			verificando = verificar.verificandoCodigo(conexao, codigo);

		}

		return verificando;

	}

	public void adicionandoCliente(Cliente cliente) throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioCliente gravando = new RepositorioCliente();
			gravando.adicionandoCliente(conexao, cliente);

		}

	}

	public ArrayList<Cliente> retornandoNomeClientes() throws SQLException {

		ArrayList<Cliente> nomes = new ArrayList<>();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioCliente r = new RepositorioCliente();
			nomes = r.retornandoNomeClientes(conexao);

		}

		return nomes;

	}

	public ArrayList<Cliente> listandoClientes() throws SQLException {

		ArrayList<Cliente> clientes = new ArrayList<>();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioCliente r = new RepositorioCliente();
			clientes = r.listandoClientes(conexao);

		}

		return clientes;

	}

	public void deletandoCliente(String codigoCliente) throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioCliente removendo = new RepositorioCliente();
			removendo.deletandoCliente(conexao, codigoCliente);

		}

	}

}