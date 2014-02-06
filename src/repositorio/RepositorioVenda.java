package repositorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Produto;
import modelo.ProdutoVenda;
import modelo.Vendas;

import com.mysql.jdbc.Connection;

public class RepositorioVenda implements IRepositorioVenda {

	static String sqlAdicionando = "insert into vendas values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	@Override
	public void adicionandoVendas(int codigoCliente, String nomeCliente,
			String ruaCliente, String numeroCliente, String bairro,
			String complemento, String cidade, String estado, String cep,
			String telefone01, String telefone02, String email,
			ArrayList<Produto> produtosCliente, String formaPagamento,
			String vezesPagamento, String permissaoEntrega,
			String precoEntrega, String dataEntrega, String descontoCompra,
			String valorParcela, String valorTotalCompra, String dataVenda,
			Connection conexao) throws SQLException {

		try (PreparedStatement stm = conexao.prepareStatement(sqlAdicionando)) {

			stm.setInt(1, 0);

			stm.setString(2, dataVenda);
			stm.setString(3, descontoCompra);
			stm.setString(4, valorParcela);
			stm.setString(5, valorTotalCompra);
			stm.setString(6, formaPagamento);
			stm.setString(7, vezesPagamento);
			stm.setString(8, permissaoEntrega);
			stm.setString(9, precoEntrega);
			stm.setString(10, dataEntrega);
			stm.setString(11, String.valueOf(codigoCliente));
			stm.setString(12, nomeCliente);
			stm.setString(13, ruaCliente);
			stm.setString(14, numeroCliente);
			stm.setString(15, bairro);
			stm.setString(16, complemento);
			stm.setString(17, cidade);
			stm.setString(18, estado);
			stm.setString(19, cep);
			stm.setString(20, telefone01);
			stm.setString(21, telefone02);
			stm.setString(22, email);

			stm.executeUpdate();

			adicionandoProdutosVendas(conexao, produtosCliente);

		}

	}

	public void adicionandoProdutosVendas(Connection conexao,
			ArrayList<Produto> produtos) throws SQLException {

		int codigoCompra = listandoCodigoCompraAdicionada(conexao);

		String sqlAdicionando = "insert into produtos_venda values (?,?,?,?,?,?,?,?,?,?)";

		for (int i = 0; i < produtos.size(); i++) {

			try (PreparedStatement stm = conexao
					.prepareStatement(sqlAdicionando)) {

				stm.setInt(1, produtos.get(i).getCodigo());
				stm.setString(2, produtos.get(i).getDescricao());
				stm.setString(3, produtos.get(i).getCategoria());

				stm.setDouble(4, produtos.get(i).getPrecoDeVenda());
				stm.setDouble(5, produtos.get(i).getQuantidade());
				stm.setString(6, produtos.get(i).getMarca());
				stm.setString(7, produtos.get(i).getModelo());

				stm.setString(8, produtos.get(i).getNumeroSerie());
				stm.setString(9, produtos.get(i).getCor());
				stm.setInt(10, codigoCompra);

				stm.executeUpdate();

			}

		}

	}

	public int listandoCodigoCompraAdicionada(Connection conexao)
			throws SQLException {

		ArrayList<Integer> codigosComprasGravadas = new ArrayList<>();
		String sql = "SELECT codigo FROM vendas";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				codigosComprasGravadas.add(rs.getInt(1));

			}

		}

		int tamanhoArray = codigosComprasGravadas.size();
		return codigosComprasGravadas.get(tamanhoArray - 1);

	}

	public ArrayList<Vendas> listandoVendas(Connection conexao)
			throws SQLException {

		ArrayList<Vendas> vendas = new ArrayList<>();
		String sql = "SELECT * FROM vendas";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			Vendas venda;

			while (rs.next()) {
				
				venda = new Vendas();

				venda.setCodigo(rs.getInt(1));
				venda.setDataVenda(rs.getString(2));

				venda.setDesconto(rs.getDouble(3));
				venda.setValorParcela(rs.getDouble(4));
				venda.setValorTotal(rs.getDouble(5));

				venda.setFormaDePagamento(rs.getString(6));
				venda.setVezes(rs.getInt(7));
				venda.setPermissaoParaEntrega(rs.getString(8));
				venda.setPrecoEntrega(rs.getDouble(9));
				venda.setDataEntrega(rs.getString(10));

				venda.setCodigoCliente(rs.getInt(11));
				venda.setNomeCliente(rs.getString(12));
				venda.setRuaCliente(rs.getString(13));
				venda.setNumeroCliente(rs.getString(14));
				venda.setBairroCliente(rs.getString(15));
				venda.setComplementoCliente(rs.getString(16));

				venda.setCidadeCliente(rs.getString(17));
				venda.setEstadoCliente(rs.getString(18));
				venda.setCep(rs.getString(19));
				venda.setTelefone01(rs.getString(20));
				venda.setTelefone02(rs.getString(21));
				venda.setEmail(rs.getString(22));

				venda.setProdutos(verificandoProdutosDaCompra(conexao,
						venda.getCodigo()));

				vendas.add(venda);

			}

		}

		return vendas;

	}

	public ArrayList<ProdutoVenda> verificandoProdutosDaCompra(
			Connection conexao, int codigoVenda) throws SQLException {

		ArrayList<ProdutoVenda> produtosEncontrados = new ArrayList<>();
		String comandoSql = "SELECT * FROM produtos_venda";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(comandoSql);
				ResultSet rs = stm.executeQuery()) {

			ProdutoVenda produto;

			while (rs.next()) {

				produto = new ProdutoVenda();

				produto.setCodigo(rs.getInt(1));
				produto.setDescricao(rs.getString(2));
				produto.setCategoria(rs.getString(3));
				produto.setPrecoDeVenda(rs.getDouble(4));
				produto.setQuantidade(rs.getInt(5));
				produto.setMarca(rs.getString(6));
				produto.setModelo(rs.getString(7));
				produto.setNumeroSerie(rs.getString(8));
				produto.setCor(rs.getString(9));
				produto.setCodigoVenda(rs.getInt(10));

				if (produto.getCodigoVenda() == codigoVenda) {

					produtosEncontrados.add(produto);

				}

			}

		}

		return produtosEncontrados;

	}

}