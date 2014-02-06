package fachada;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Produto;
import repositorio.RepositorioProduto;
import views.principais.tela_de_erro.ErroEncontrado;

public class FachadaProdutos {

	// Métodos de produto ...

	private static final String url = "jdbc:mysql://localhost/thallyta_moveis";
	private static final String usuario = "root";
	private static final String senha = "";

	// Alterando produto ...

	public void alterandoLembrete(String codigoOriginal, Produto produto)
			throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioProduto passandoValores = new RepositorioProduto();
			passandoValores.alterandoProduto(conexao, codigoOriginal, produto);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	// Removendo produto ...

	public void removendoProduto(String codigo) throws SQLException {

		try (com.mysql.jdbc.Connection conexao = ((com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha))) {

			RepositorioProduto passandoDados = new RepositorioProduto();
			passandoDados.removendoProduto(conexao, codigo);

		}

	}

	// Verificando se o código digitado já está contido no banco de dados ...

	public boolean verificandoCodigo(String codigo) throws SQLException {

		boolean verif = true;

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioProduto verificar = new RepositorioProduto();
			verif = verificar.verificandoCodigo_BD(conexao, codigo);

			if (verif == true) {

				return true;

			}

			else {

				return false;

			}

		}

	}

	public void adicionandoProduto(Produto produto) {

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioProduto adicionandoProduto_BD = new RepositorioProduto();

			adicionandoProduto_BD.adicionarProduto(
					(com.mysql.jdbc.Connection) conexao, produto);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	// Métodos de categoria ...

	public static void adicionandoCategoria(String categoria) {

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioProduto adicionandoCategoria_BD = new RepositorioProduto();
			adicionandoCategoria_BD.adicionarCategoria(
					(com.mysql.jdbc.Connection) conexao, categoria);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	public static ArrayList<String> listandoCategorias() {

		ArrayList<String> categ = null;

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioProduto resgatandoCategorias = new RepositorioProduto();

			categ = resgatandoCategorias
					.listandoCategorias((com.mysql.jdbc.Connection) conexao);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		return categ;

	}

	public static void removendoCategoria(String categoria) {

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioProduto adicionandoCategoria_BD = new RepositorioProduto();

			adicionandoCategoria_BD.removerCategoria(
					(com.mysql.jdbc.Connection) conexao, categoria);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	// Listando produtos ...

	public ArrayList<Produto> listandoProdutos() throws SQLException {

		ArrayList<Produto> produtos = new ArrayList<>();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioProduto retornandoUsuarios = new RepositorioProduto();
			produtos = retornandoUsuarios.listandoProdutos(conexao);

		}

		return produtos;

	}

	// Adicionando compras da loja ao estoque ...

	public void adicionandoCompras(ArrayList<String> codigos,
			ArrayList<String> quantidades) throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioProduto gravando = new RepositorioProduto();
			gravando.adicionandoCompras(conexao, codigos, quantidades);

		}

	}
	
	public void subtraindoProdutos(ArrayList<String> codigos,
			ArrayList<String> quantidades) throws SQLException {

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioProduto gravando = new RepositorioProduto();
			gravando.decrementandoProdutos(conexao, codigos, quantidades);

		}

	}

	public ArrayList<Produto> buscandoProdutos(String nomeBusca)
			throws SQLException {

		ArrayList<Produto> produtosAchados = new ArrayList<>();

		try (com.mysql.jdbc.Connection conexao = (com.mysql.jdbc.Connection) DriverManager
				.getConnection(url, usuario, senha)) {

			RepositorioProduto acharProdutos = new RepositorioProduto();

			produtosAchados = acharProdutos
					.buscandoProdutos(conexao, nomeBusca);

		}

		return produtosAchados;

	}

}