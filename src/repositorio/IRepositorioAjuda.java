package repositorio;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Ajuda;

public interface IRepositorioAjuda {

	public ArrayList<Ajuda> listandoAjudas(Connection conexao) throws SQLException;

}