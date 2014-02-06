package repositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Produto;

import com.mysql.jdbc.Connection;

public interface IRepositorioProduto {

	public void adicionarCategoria(Connection conexao, String categoria);

	public ArrayList<String> listandoCategorias(Connection conexao)
			throws SQLException;

	public void removerCategoria(Connection conexao, String categoria)
			throws SQLException;

	public ArrayList<Produto> listandoProdutos(Connection conexao)
			throws SQLException;

	public boolean verificandoCodigo_BD(java.sql.Connection conexao,
			String codigo) throws SQLException;

	public void removendoProduto(Connection conexao, String codigoProduto)
			throws SQLException;

	public void alterandoProduto(Connection conexao, String codigoOriginal,
			Produto produto) throws SQLException;

	public void adicionandoCompras(Connection conexao,
			ArrayList<String> codigos, ArrayList<String> quantidades)
			throws SQLException;

	public ArrayList<Produto> buscandoProdutos(Connection conexao,
			String nomeBusca) throws SQLException;
	

	public void decrementandoProdutos(Connection conexao,
			ArrayList<String> codigos, ArrayList<String> quantidades)
			throws SQLException;

}