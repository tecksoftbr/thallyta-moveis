package repositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.ContasPagar;

public interface IRepositorioContasPagar {

	public void adicionarContasPagar(Connection con, ContasPagar conta)
			throws SQLException;

	public boolean verificandoNumeroDocumento(Connection con,
			String numeroDoDocumento) throws SQLException;

	public ArrayList<ContasPagar> listandoContas(Connection conexao)
			throws SQLException;

	public void removendoContaPagar(Connection conexao, String numeroDocumento)
			throws SQLException;

	public void atualizandoConta(Connection conexao, String numeroOriginal,
			ContasPagar conta) throws SQLException;

}