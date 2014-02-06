package repositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import modelo.Cliente;
import modelo.ContasPagar;
import modelo.Lembrete;

public interface IRepositorioAvisos {

	public ArrayList<ContasPagar> retornandoContas(Connection conexao)
			throws SQLException;

	public ArrayList<Lembrete> retornandoLembretes(Connection conexao)
			throws SQLException;

	public ArrayList<Cliente> retornandoClientes(Connection conexao)
			throws SQLException;

}