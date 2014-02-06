package repositorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Cliente;
import views.principais.tela_de_erro.ErroEncontrado;

import com.mysql.jdbc.Connection;

public class RepositorioCliente implements IRepositorioCliente {

	@Override
	public boolean verificandoCodigo(Connection conexao, String codigo)
			throws SQLException {

		String sql = "SELECT codigo FROM clientes";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				if (rs.getString(1).equals(codigo)) {

					return false;

				}

			}

		}

		return true;

	}

	@Override
	public void adicionandoCliente(Connection conexao, Cliente cliente)
			throws SQLException {

		String sql = "INSERT INTO clientes values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement stm = conexao.prepareStatement(sql)) {

			stm.setString(1, String.valueOf((cliente.getCodigo())));
			stm.setString(2, cliente.getNomeCompleto());
			stm.setString(3, cliente.getSexo());
			stm.setString(4, cliente.getCpf());
			stm.setString(5, String.valueOf((cliente.getRg())));
			stm.setString(6, cliente.getOrgaoEmissor());
			stm.setString(7, cliente.getDataDeEmissao());
			stm.setString(8, cliente.getNacionalidade());

			stm.setString(9, cliente.getRua());
			stm.setString(10, cliente.getNumero());
			stm.setString(11, cliente.getComplemento());
			stm.setString(12, cliente.getBairro());
			stm.setString(13, cliente.getCidade());
			stm.setString(14, cliente.getEstado());
			stm.setString(15, cliente.getCep());
			stm.setString(16, cliente.getTelefone01());
			stm.setString(17, cliente.getTelefone02());
			stm.setString(18, cliente.getEmail());

			stm.setString(19, cliente.getDataDeNascimento());
			stm.setString(20, cliente.getEstadoCivil());
			stm.setString(21, cliente.getConjugue());
			stm.setString(22, cliente.getPai());
			stm.setString(23, cliente.getMae());
			stm.setString(24, cliente.getTrabalho());
			stm.setString(25, cliente.getCargo());
			stm.setString(26, cliente.getTempoServico());
			stm.setString(27, cliente.getSobreTrabalho());
			stm.setString(28, cliente.getObservacoesAdicionais());
			stm.setString(29, cliente.getDataDeCadastro());
			stm.setString(30, cliente.getEnderecoOndeTrabalha());
			stm.setString(31, cliente.getNumeroTrabalho());
			stm.setString(32, cliente.getComplementoTrabalho());
			stm.setString(33, cliente.getBairroTrabalho());
			stm.setString(34, cliente.getCidadeTrabalho());
			stm.setString(35, cliente.getEstadoTrabalho());
			stm.setString(36, cliente.getCepTrabalho());
			stm.setString(37, cliente.getTelefoneTrabalho());
			stm.setString(38, cliente.getFaxTrabalho());
			stm.setString(39, cliente.getEmailTrabalho());

			stm.executeUpdate();

		}

	}

	@Override
	public ArrayList<Cliente> retornandoNomeClientes(Connection conexao)
			throws SQLException {

		ArrayList<Cliente> clientes = new ArrayList<>();
		Cliente cli;

		String sql = "SELECT codigo, nome_completo, rua, numero, bairro, comple"
				+ "mento, cidade, estado, cep, telefone_01, telefone_02, email FROM clientes";

		try {

			try (PreparedStatement stm = (PreparedStatement) conexao
					.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

				while (rs.next()) {

					cli = new Cliente();

					cli.setCodigo(rs.getInt(1));
					cli.setNomeCompleto(rs.getString(2));
					cli.setRua(rs.getString(3));
					cli.setNumero(String.valueOf(rs.getInt(4)));

					cli.setBairro(rs.getString(5));
					cli.setComplemento(rs.getString(6));
					cli.setCidade(rs.getString(7));
					cli.setEstado(rs.getString(8));

					cli.setCep(rs.getString(9));
					cli.setTelefone01(rs.getString(10));
					cli.setTelefone02(rs.getString(11));
					cli.setEmail(rs.getString(12));

					clientes.add(cli);

				}

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		return clientes;

	}

	@Override
	public ArrayList<Cliente> listandoClientes(Connection conexao)
			throws SQLException {

		ArrayList<Cliente> clientes = new ArrayList<>();

		String sql = "SELECT codigo, nome_completo, sexo, cpf, rg, orgao_emissor, data_de_emissao, nacionalidade, rua, numero, complemento, bair"
				+ "ro, cidade, estado, cep, telefone_01, telefone_02, email, data_de_nascimento, estado_civil, conjugue, pai, mae, trabalho, c"
				+ "argo, tempo_servico, sobre_trabalho, observacoes_adicionais, data_cadastro, endereco_trabalho, numero_trabalho, complemento_t"
				+ "rabalho, bairro_trabalho, cidade_trabalho, estado_trabalho, cep_trabalho, telefone_trabalho, fax_trabalho, email_trabalho FROM clientes";

		Cliente cliente;

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				cliente = new Cliente();

				cliente.setCodigo(rs.getInt(1));
				cliente.setNomeCompleto(rs.getString(2));
				cliente.setSexo(rs.getString(3));
				cliente.setCpf(rs.getString(4));
				cliente.setRg(Integer.parseInt(rs.getString(5)));
				cliente.setOrgaoEmissor(rs.getString(6));
				cliente.setDataDeEmissao(rs.getString(7));
				cliente.setNacionalidade(rs.getString(8));
				cliente.setRua(rs.getString(9));
				cliente.setNumero(rs.getString(10));
				cliente.setComplemento(rs.getString(11));
				cliente.setBairro(rs.getString(12));
				cliente.setCidade(rs.getString(13));
				cliente.setEstado(rs.getString(14));
				cliente.setCep(rs.getString(15));
				cliente.setTelefone01(rs.getString(16));
				cliente.setTelefone02(rs.getString(17));
				cliente.setEmail(rs.getString(18));
				cliente.setDataDeNascimento(rs.getString(19));
				cliente.setEstadoCivil(rs.getString(20));
				cliente.setConjugue(rs.getString(21));
				cliente.setPai(rs.getString(22));
				cliente.setMae(rs.getString(23));
				cliente.setTrabalho(rs.getString(24));
				cliente.setCargo(rs.getString(25));
				cliente.setTempoServico(rs.getString(26));
				cliente.setSobreTrabalho(rs.getString(27));
				cliente.setObservacoesAdicionais(rs.getString(28));
				cliente.setDataDeCadastro(rs.getString(29));
				cliente.setEnderecoOndeTrabalha(rs.getString(30));
				cliente.setNumeroTrabalho(rs.getString(31));
				cliente.setComplementoTrabalho(rs.getString(32));
				cliente.setBairroTrabalho(rs.getString(33));
				cliente.setCidadeTrabalho(rs.getString(34));
				cliente.setEstadoTrabalho(rs.getString(35));
				cliente.setCepTrabalho(rs.getString(36));
				cliente.setTelefoneTrabalho(rs.getString(37));
				cliente.setFaxTrabalho(rs.getString(38));
				cliente.setEmailTrabalho(rs.getString(39));

				clientes.add(cliente);

			}

		}

		return clientes;

	}

	@Override
	public void deletandoCliente(Connection conexao, String codigo)
			throws SQLException {

		String sql = "DELETE FROM clientes WHERE codigo=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setString(1, codigo);
			stm.executeUpdate();

		}

	}

}