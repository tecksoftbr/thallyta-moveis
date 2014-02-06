package repositorio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import views.principais.tela_de_erro.ErroEncontrado;
import com.mysql.jdbc.PreparedStatement;
import modelo.Fornecedor;

public class RepositorioFornecedor implements IRepositorioFornecedor {

	// Método de listamento de fornecedores ...

	@Override
	public ArrayList<Fornecedor> listandoFornecedores(
			com.mysql.jdbc.Connection conexao) throws SQLException {

		String sql = "SELECT codigo, empresa, cep, rua, bairro, cidade, estado, telefone, fax, email, site, observacoes, data_de_cadastro, urlFoto FROM fornecedores";
		ArrayList<Fornecedor> fornecedoresBd = new ArrayList<>();

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				int codigo = rs.getInt(1);

				String empresa = rs.getString(2);
				String cep = rs.getString(3);
				String rua = rs.getString(4);
				String bairro = rs.getString(5);
				String cidade = rs.getString(6);
				String estado = rs.getString(7);
				String telefone = rs.getString(8);
				String fax = rs.getString(9);
				String email = rs.getString(10);
				String site = rs.getString(11);
				String observacoes = rs.getString(12);
				String dataDeCadastro = rs.getString(13);
				String urlFoto = rs.getString(14);

				Fornecedor fornecedor = new Fornecedor();

				fornecedor.setCodigo(codigo);
				fornecedor.setEmpresa(empresa);
				fornecedor.setCep(cep);
				fornecedor.setRua(rua);
				fornecedor.setBairro(bairro);
				fornecedor.setCidade(cidade);
				fornecedor.setEstado(estado);
				fornecedor.setTelefone(telefone);
				fornecedor.setFax(fax);
				fornecedor.setEmail(email);
				fornecedor.setPaginaWeb(site);
				fornecedor.setObservacoes(observacoes);
				fornecedor.setDataDeCadastro(dataDeCadastro);
				fornecedor.setUrlFoto(urlFoto);

				fornecedoresBd.add(fornecedor);

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		return fornecedoresBd;

	}

	// Verificando código no banco de dados ...

	@Override
	public boolean verificandoCodigo_BD(java.sql.Connection conexao,
			String codigo) throws SQLException {

		String sql = "SELECT codigo FROM fornecedores";

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

	// Método de cadastro de um novo fornecedor ...

	@Override
	public void cadastrandoFornecedor(Connection conexao, Fornecedor fornecedor) {

		String sql = "INSERT INTO fornecedores VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setInt(1, fornecedor.getCodigo());
			stm.setString(2, fornecedor.getEmpresa());
			stm.setString(3, fornecedor.getCep());
			stm.setString(4, fornecedor.getRua());
			stm.setString(5, fornecedor.getBairro());
			stm.setString(6, fornecedor.getCidade());
			stm.setString(7, fornecedor.getEstado());

			stm.setString(8, fornecedor.getTelefone());
			stm.setString(9, fornecedor.getFax());
			stm.setString(10, fornecedor.getEmail());
			stm.setString(11, fornecedor.getPaginaWeb());
			stm.setString(12, fornecedor.getObservacoes());
			stm.setString(13, fornecedor.getDataDeCadastro());
			stm.setString(14, fornecedor.getUrlFoto());

			stm.executeUpdate();

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public void removendoFornecedor(com.mysql.jdbc.Connection conexao,
			String codigoFornecedor) throws SQLException {

		String sql = "DELETE FROM fornecedores WHERE codigo=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setString(1, codigoFornecedor);
			stm.executeUpdate();

		}

	}

	@Override
	public void atualizandoFornecedor(com.mysql.jdbc.Connection conexao,
			String codigoOriginal, Fornecedor fornecedor) throws SQLException {

		String sql = "UPDATE fornecedores SET codigo=?, empresa=?, cep=?, rua=?, bairro=?, cidade=?, estado=?, t"
				+ "elefone=?, fax=?, email=?, site=?, observacoes=?, data_de_cadastro=? WHERE codigo=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setInt(1, fornecedor.getCodigo());
			stm.setString(2, fornecedor.getEmpresa());
			stm.setString(3, fornecedor.getCep());
			stm.setString(4, fornecedor.getRua());
			stm.setString(5, fornecedor.getBairro());
			stm.setString(6, fornecedor.getCidade());
			stm.setString(7, fornecedor.getEstado());
			stm.setString(8, fornecedor.getTelefone());

			stm.setString(9, fornecedor.getFax());
			stm.setString(10, fornecedor.getEmail());
			stm.setString(11, fornecedor.getPaginaWeb());
			stm.setString(12, fornecedor.getObservacoes());
			stm.setString(13, fornecedor.getDataDeCadastro());

			stm.setInt(14, Integer.parseInt(codigoOriginal));

			stm.executeUpdate();

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

}