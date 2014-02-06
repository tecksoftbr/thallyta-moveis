package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import fachada.FachadaLembretes;

import views.principais.tela_de_erro.ErroEncontrado;

import modelo.Lembrete;

public class RepositorioLembrete implements IRepositorioLembrete {

	private static String sqlLembretes = "insert into lembretes values (?,?,?,?,?)";

	private Lembrete lembrete;

	@Override
	public void adicionarLembrete(Connection con, Lembrete lem)
			throws SQLException {

		try (PreparedStatement stm = con.prepareStatement(sqlLembretes)) {

			stm.setInt(1, lem.getCodigo());
			stm.setString(2, lem.getTitulo());
			stm.setString(3, lem.getDescricao());
			stm.setString(4, lem.getData_de_cadastro());
			stm.setString(5, lem.getData_de_aviso());

			stm.executeUpdate();

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public ArrayList<Lembrete> listandoLembretes(Connection conexao)
			throws SQLException {

		String sql = "SELECT codigo, titulo, descricao, data_de_cadastro, data_de_aviso FROM lembretes";

		ArrayList<Lembrete> lembretes = new ArrayList<>();

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				int codigo = (rs.getInt(1));

				String titulo = (rs.getString(2));
				String decricao = (rs.getString(3));
				String dataDeCadastro = (rs.getString(4));
				String dataDeAviso = (rs.getString(5));

				Lembrete lembrete = new Lembrete();

				lembrete.setCodigo(codigo);
				lembrete.setData_de_aviso(dataDeAviso);
				lembrete.setData_de_cadastro(dataDeCadastro);
				lembrete.setDescricao(decricao);
				lembrete.setTitulo(titulo);

				lembretes.add(lembrete);

			}

		}

		return lembretes;

	}

	@Override
	public boolean verificandoCodigo_BD(Connection conexao, String codigo)
			throws SQLException {

		String sql = "SELECT codigo FROM lembretes";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				if (codigo.equals(String.valueOf(rs.getInt(1)))) {

					return false;

				}

			}

		}

		return true;

	}

	// Removendo lembrete do banco de dados ...

	@Override
	public void removendoLembrete(Connection conexao, String codigo)
			throws SQLException {

		String sql = "DELETE FROM lembretes WHERE codigo=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setString(1, codigo);
			stm.executeUpdate();

		}

	}

	@Override
	public void alterandoLembrete(Connection conexao, String codigoOriginal,
			Lembrete lembrete) {

		String sql = "UPDATE lembretes SET codigo=?, titulo=?, descricao=?, data_de_cadastro=?, data_de_aviso=? WHERE codigo=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setInt(1, lembrete.getCodigo());
			stm.setString(2, lembrete.getTitulo());
			stm.setString(3, lembrete.getDescricao());
			stm.setString(4, lembrete.getData_de_cadastro());
			stm.setString(5, lembrete.getData_de_aviso());

			stm.setInt(6, Integer.parseInt(codigoOriginal));
			stm.executeUpdate();

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public void gravandoLembreteEntrega(Lembrete lembrete, Connection conexao)
			throws SQLException {

		this.lembrete = new Lembrete();
		this.lembrete = lembrete;

		this.lembrete.setCodigo(valorAleatorio());

		try (PreparedStatement stm = conexao.prepareStatement(sqlLembretes)) {

			stm.setInt(1, this.lembrete.getCodigo());
			stm.setString(2, this.lembrete.getTitulo());
			stm.setString(3, this.lembrete.getDescricao());
			stm.setString(4, this.lembrete.getData_de_cadastro());
			stm.setString(5, this.lembrete.getData_de_aviso());

			stm.executeUpdate();

		}

		catch (Exception e) {

			FachadaLembretes tentandoNovamente = new FachadaLembretes();
			tentandoNovamente.adicionandoLembreteVenda(lembrete);

		}

	}

	public int valorAleatorio() {

		Random ale = new Random();
		int aleat = ale.nextInt(99999);

		return aleat;

	}

}
