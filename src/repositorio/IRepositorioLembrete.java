package repositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Lembrete;

public interface IRepositorioLembrete {

	public void adicionarLembrete(Connection con, Lembrete lem)
			throws SQLException;

	public ArrayList<Lembrete> listandoLembretes(Connection conexao)
			throws SQLException;

	public boolean verificandoCodigo_BD(java.sql.Connection conexao,
			String codigo) throws SQLException;

	public void removendoLembrete(java.sql.Connection conexao, String codigo)
			throws SQLException;

	public void alterandoLembrete(java.sql.Connection conexao,
			String codigoOriginal, Lembrete lembrete);

	public void gravandoLembreteEntrega(Lembrete lembrete, Connection conexao)
			throws SQLException;

}