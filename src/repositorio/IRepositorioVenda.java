package repositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import modelo.Produto;
import modelo.ProdutoVenda;
import modelo.Vendas;

public interface IRepositorioVenda {

	public void adicionandoVendas(int codigoCliente, String nomeCliente,
			String ruaCliente, String numeroCliente, String bairro,
			String complemento, String cidade, String estado, String cep,
			String telefone01, String telefone02, String email,
			ArrayList<Produto> produtosCliente, String formaPagamento,
			String vezesPagamento, String permissaoEntrega,
			String precoEntrega, String dataEntrega, String descontoCompra,
			String valorParcela, String valorTotalCompra, String dataVenda,
			Connection conexao) throws SQLException;

	public int listandoCodigoCompraAdicionada(Connection conexao)
			throws SQLException;

	public ArrayList<Vendas> listandoVendas(Connection conexao)
			throws SQLException;

	public ArrayList<ProdutoVenda> verificandoProdutosDaCompra(
			Connection conexao, int codigoVenda) throws SQLException;

}