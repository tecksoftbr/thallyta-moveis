package repositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Cliente;

import com.mysql.jdbc.Connection;

public interface IRepositorioCliente {

	public boolean verificandoCodigo(Connection conexao, String codigo)
			throws SQLException;

	public void adicionandoCliente(Connection conexao, Cliente cliente)
			throws SQLException;

	public ArrayList<Cliente> retornandoNomeClientes(Connection conexao)
			throws SQLException;

	public ArrayList<Cliente> listandoClientes(Connection conexao)
			throws SQLException;

	public void deletandoCliente(Connection conexao, String codigo)
			throws SQLException;

}