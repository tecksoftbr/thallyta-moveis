package fachada;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import repositorio.RepositorioFornecedor;
import views.principais.tela_de_erro.ErroEncontrado;
import modelo.Fornecedor;

public class FachadaFornecedores {

	private final String url = "jdbc:mysql://localhost/thallyta_moveis";
	private final String usuario = "root";
	private final String senha = "";

	// Removendo fornecedores ...

	public void removendoFornecedor(String codigo) throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioFornecedor passandoFornecedor = new RepositorioFornecedor();
			passandoFornecedor.removendoFornecedor(conexao, codigo);

		}

	}

	// Listando fornecedores e retornando um array do tipo collection ...

	public ArrayList<Fornecedor> listandoFornecedores() throws SQLException {

		ArrayList<Fornecedor> fornecedores = new ArrayList<>();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(this.url, this.usuario, this.senha)) {

			RepositorioFornecedor retornandoFornecedores = new RepositorioFornecedor();
			fornecedores = retornandoFornecedores.listandoFornecedores(conexao);

		}

		// Caso algo saia errado ...

		catch (Exception e) {

			new ErroEncontrado();

		}

		return fornecedores;

	}

	// Verificando se o código digitado já está contido no banco de dados ...

	public boolean verificandoCodigo(String codigo) throws SQLException {

		boolean verif = true;

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioFornecedor verificandoNoBanco = new RepositorioFornecedor();
			verif = verificandoNoBanco.verificandoCodigo_BD(conexao, codigo);

		}

		return verif;

	}

	// Alterando fornecedor ...

	public void alterandoFornecedor(String codigoOriginal, Fornecedor fornecedor)
			throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioFornecedor alterando = new RepositorioFornecedor();
			
			alterando
					.atualizandoFornecedor(conexao, codigoOriginal, fornecedor);

		}

	}

	// Adicionando fornecedor ao banco de dados ...

	public void adicionandoFornecedor(Fornecedor fornecedor)
			throws SQLException {

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioFornecedor gravando = new RepositorioFornecedor();
			gravando.cadastrandoFornecedor(conexao, fornecedor);

		}

	}

}