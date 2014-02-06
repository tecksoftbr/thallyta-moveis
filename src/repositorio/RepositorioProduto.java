package repositorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Produto;
import views.principais.tela_de_erro.ErroEncontrado;

import com.mysql.jdbc.Connection;

public class RepositorioProduto implements IRepositorioProduto {

	public void adicionarProduto(Connection conexao, Produto produto) {

		String sql_AdicionarProduto = "insert into produtos values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement stm = conexao
				.prepareStatement(sql_AdicionarProduto)) {

			stm.setInt(1, produto.getCodigo());

			stm.setString(2, produto.getDescricao());
			stm.setString(3, produto.getCategoria());
			stm.setString(4, produto.getDataDeCadastro());

			stm.setDouble(5, produto.getPrecoDeCompra());
			stm.setDouble(6, produto.getPrecoDeVenda());

			stm.setInt(7, produto.getQuantidade());

			stm.setString(8, produto.getMarca());
			stm.setString(9, produto.getObservacoes());
			stm.setString(10, produto.getModelo());
			stm.setString(11, produto.getNumeroSerie());
			stm.setString(12, produto.getCor());
			stm.setString(13, produto.getUrlFoto());

			stm.executeUpdate();

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public void adicionarCategoria(Connection conexao, String categoria) {

		String sqlCategoria = "insert into categorias values (?,?)";

		try (PreparedStatement stm = conexao.prepareStatement(sqlCategoria)) {

			stm.setInt(1, 0);
			stm.setString(2, categoria);

			stm.executeUpdate();

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public ArrayList<String> listandoCategorias(Connection conexao)
			throws SQLException {

		ArrayList<String> categorias = new ArrayList<>();

		String sqlListando = "select nome from categorias";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sqlListando);
				ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				categorias.add(rs.getString(1));

			}

		}

		return categorias;

	}

	@Override
	public void removerCategoria(Connection conexao, String categoria)
			throws SQLException {

		String sqlCategoria = "delete from categorias where nome = (?)";

		try (PreparedStatement stm = conexao.prepareStatement(sqlCategoria)) {

			stm.setString(1, categoria);
			stm.executeUpdate();

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public ArrayList<Produto> listandoProdutos(Connection conexao)
			throws SQLException {

		String sql = "SELECT codigo, descricao, categoria, data_de_cadastro, preco_de_compra, p"
				+ "reco_de_venda, quantidade, marca, observacoes, modelo, numero_de_serie, cor, urlFoto FROM produtos";

		ArrayList<Produto> produtosBD = new ArrayList<>();

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				int codigo = Integer.parseInt(rs.getString(1));

				String descricao = rs.getString(2);
				String categoria = rs.getString(3);
				String dataDeCadastro = rs.getString(4);

				double precoDeCompra = rs.getDouble(5);
				double precoDeVenda = rs.getDouble(6);

				int quantidade = rs.getInt(7);

				String marca = rs.getString(8);
				String observacoes = rs.getString(9);
				String modelo = rs.getString(10);
				String numeroDeSerie = rs.getString(11);
				String cor = rs.getString(12);
				String urlFoto = rs.getString(13);

				Produto produto = new Produto();

				produto.setCategoria(categoria);
				produto.setCodigo(codigo);
				produto.setCor(cor);
				produto.setDataDeCadastro(dataDeCadastro);
				produto.setDescricao(descricao);
				produto.setMarca(marca);
				produto.setModelo(modelo);
				produto.setNumeroSerie(numeroDeSerie);
				produto.setObservacoes(observacoes);
				produto.setPrecoDeCompra(precoDeCompra);
				produto.setPrecoDeVenda(precoDeVenda);
				produto.setQuantidade(quantidade);
				produto.setUrlFoto(urlFoto);

				produtosBD.add(produto);

			}

		}

		return produtosBD;

	}

	@Override
	public boolean verificandoCodigo_BD(java.sql.Connection conexao,
			String codigo) throws SQLException {

		String sql = "SELECT codigo FROM produtos";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				if (codigo.equals(String.valueOf(rs.getInt(1)))) {

					return false;

				}

			}

		}

		return true;

	}

	@Override
	public void removendoProduto(Connection conexao, String codigoProduto)
			throws SQLException {

		String sql = "DELETE FROM produtos WHERE codigo=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setString(1, codigoProduto);
			stm.executeUpdate();

		}

	}

	@Override
	public void alterandoProduto(Connection conexao, String codigoOriginal,
			Produto produto) throws SQLException {

		String sql = "UPDATE produtos SET codigo=?, descricao=?, categoria=?, data_de_cadastro=?, preco_de_compra=?, "
				+ "preco_de_venda=?, quantidade=?, marca=?, observacoes=?, modelo=?, numero_de_serie=?, cor=?, urlFoto=? WHERE codigo=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setInt(1, produto.getCodigo());
			stm.setString(2, produto.getDescricao());
			stm.setString(3, produto.getCategoria());
			stm.setString(4, produto.getDataDeCadastro());
			stm.setDouble(5, produto.getPrecoDeCompra());
			stm.setDouble(6, produto.getPrecoDeVenda());
			stm.setInt(7, produto.getQuantidade());
			stm.setString(8, produto.getMarca());
			stm.setString(9, produto.getObservacoes());
			stm.setString(10, produto.getModelo());
			stm.setString(11, produto.getNumeroSerie());
			stm.setString(12, produto.getCor());
			stm.setString(13, produto.getUrlFoto());

			stm.setString(14, codigoOriginal);
			stm.executeUpdate();

		}

	}

	@Override
	public void adicionandoCompras(Connection conexao,
			ArrayList<String> codigos, ArrayList<String> quantidades)
			throws SQLException {

		String sql = "SELECT quantidade FROM produtos WHERE codigo=?";
		String sqlUpdate = "UPDATE produtos SET quantidade=? WHERE codigo =?";

		int quantidadeBD = 0;

		for (int i = 0; i < codigos.size(); i++) {

			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setString(1, codigos.get(i));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				int quantidadeSoftware = Integer.parseInt(quantidades.get(i));
				quantidadeBD = rs.getInt(1) + quantidadeSoftware;

			}

			try (PreparedStatement stm = (PreparedStatement) conexao
					.prepareStatement(sqlUpdate)) {

				stm.setInt(1, quantidadeBD);
				stm.setString(2, codigos.get(i));

				stm.executeUpdate();

			}

		}

	}

	@Override
	public ArrayList<Produto> buscandoProdutos(Connection conexao,
			String nomeBusca) throws SQLException {

		ArrayList<Produto> produtosAchados = new ArrayList<>();
		Produto produto;

		String sql = "SELECT * FROM produtos WHERE descricao LIKE '%"
				+ nomeBusca + "%'";

		PreparedStatement stmt = conexao.prepareStatement(sql);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {

			produto = new Produto();

			produto.setCodigo(rs.getInt(1));
			produto.setDescricao(rs.getString(2));
			produto.setCategoria(rs.getString(3));
			produto.setDataDeCadastro(rs.getString(4));
			produto.setPrecoDeCompra(rs.getDouble(5));
			produto.setPrecoDeVenda(rs.getDouble(6));
			produto.setQuantidade(rs.getInt(7));
			produto.setMarca(rs.getString(8));

			produto.setObservacoes(rs.getString(9));
			produto.setModelo(rs.getString(10));
			produto.setNumeroSerie(rs.getString(11));
			produto.setCor(rs.getString(12));
			produto.setUrlFoto(rs.getString(13));

			produtosAchados.add(produto);

		}

		rs.close();
		stmt.close();

		return produtosAchados;

	}

	@Override
	public void decrementandoProdutos(Connection conexao,
			ArrayList<String> codigos, ArrayList<String> quantidades)
			throws SQLException {

		String sql = "SELECT quantidade FROM produtos WHERE codigo=?";
		String sqlUpdate = "UPDATE produtos SET quantidade=? WHERE codigo =?";

		int quantidadeBD = 0;

		for (int i = 0; i < codigos.size(); i++) {

			PreparedStatement stmt = conexao.prepareStatement(sql);

			stmt.setString(1, codigos.get(i));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				int quantidadeSoftware = Integer.parseInt(quantidades.get(i));
				quantidadeBD = rs.getInt(1) - quantidadeSoftware;

			}

			try (PreparedStatement stm = (PreparedStatement) conexao
					.prepareStatement(sqlUpdate)) {

				stm.setInt(1, quantidadeBD);
				stm.setString(2, codigos.get(i));

				stm.executeUpdate();

			}

		}

	}

}