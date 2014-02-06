package repositorio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import modelo.Ajuda;

public class RepositorioAjuda implements IRepositorioAjuda {

	@Override
	public ArrayList<Ajuda> listandoAjudas(Connection conexao)
			throws SQLException {

		String sql = "select titulo, descricao from ajuda";

		ArrayList<Ajuda> ajudas = new ArrayList<>();
		Ajuda ajuda;

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				ajuda = new Ajuda();

				ajuda.setTitulo(rs.getString(1));
				ajuda.setDescricao(rs.getString(2));

				ajudas.add(ajuda);

			}

		}

		return ajudas;

	}

}