package repositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import modelo.Fornecedor;

public interface IRepositorioFornecedor {

	public ArrayList<Fornecedor> listandoFornecedores(Connection conexao)
			throws SQLException;

	public boolean verificandoCodigo_BD(java.sql.Connection conexao,
			String codigo) throws SQLException;

	public void cadastrandoFornecedor(java.sql.Connection conexao,
			Fornecedor fornecedor);

	public void removendoFornecedor(Connection conexao, String codigoFornecedor)
			throws SQLException;

	public void atualizandoFornecedor(Connection conexao,
			String codigoOriginal, Fornecedor fornecedor) throws SQLException;

}