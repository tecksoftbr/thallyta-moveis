package repositorio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import modelo.Cliente;
import modelo.ContasPagar;
import modelo.Lembrete;

import com.mysql.jdbc.Connection;

public class RepositorioAvisos implements IRepositorioAvisos {

	@Override
	public ArrayList<ContasPagar> retornandoContas(Connection conexao)
			throws SQLException {

		ArrayList<ContasPagar> contas = new ArrayList<>();

		String sql = "SELECT tipo, numero_documento, data_entrada, data_vencimento, valor, origem FROM pagar_contas";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			String dataDoSistema = pegandoDataDoSistema();

			while (rs.next()) {

				String tipo = rs.getString(1);
				String numeroDocumento = rs.getString(2);
				String dataEntrada = rs.getString(3);
				String dataVencimento = rs.getString(4);
				Double valor = rs.getDouble(5);
				String origem = rs.getString(6);

				ContasPagar contaModelo;

				if (dataVencimento.equals(dataDoSistema)) {

					contaModelo = new ContasPagar();

					contaModelo.setDataEntrada(dataEntrada);
					contaModelo.setDataVencimento(dataVencimento);
					contaModelo.setNumeroDocumento(numeroDocumento);
					contaModelo.setOrigem(origem);
					contaModelo.setTipoConta(tipo);
					contaModelo.setValor(valor);

					contas.add(contaModelo);

				}

			}

		}

		return contas;

	}

	@Override
	public ArrayList<Lembrete> retornandoLembretes(Connection conexao)
			throws SQLException {

		ArrayList<Lembrete> lembretes = new ArrayList<>();
		String dataDoSistema = pegandoDataDoSistema();

		String sql = "SELECT codigo, titulo, descricao, data_de_cadastro, data_de_aviso FROM lembretes";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				int codigo = (rs.getInt(1));

				String titulo = (rs.getString(2));
				String decricao = (rs.getString(3));
				String dataDeCadastro = (rs.getString(4));
				String dataDeAviso = (rs.getString(5));

				Lembrete lembrete;

				if (dataDeAviso.equals(dataDoSistema)) {

					lembrete = new Lembrete();

					lembrete.setCodigo(codigo);
					lembrete.setData_de_aviso(dataDeAviso);
					lembrete.setData_de_cadastro(dataDeCadastro);
					lembrete.setDescricao(decricao);
					lembrete.setTitulo(titulo);

					lembretes.add(lembrete);

				}

			}

		}

		return lembretes;

	}

	private String pegandoDataDoSistema() {

		Date now = new Date();
		DateFormat df = DateFormat.getDateInstance();
		String s = df.format(now);

		return s;

	}

	@Override
	public ArrayList<Cliente> retornandoClientes(Connection conexao)
			throws SQLException {

		ArrayList<Cliente> clientes = new ArrayList<>();
		String dataDoSistema = pegandoDataDoSistema();

		String sql = "SELECT codigo, nome_completo, telefone_01, telefone_02, email, data_de_nascimento FROM clientes";
		Cliente cliente;

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				int codigo = rs.getInt(1);
				String nomeCompleto = rs.getString(2);
				String telefone01 = rs.getString(3);
				String telefone02 = rs.getString(4);
				String email = rs.getString(5);
				String dataDeNascimento = rs.getString(6);

				char[] separandoDataDeNascimento = dataDeNascimento
						.toCharArray();
			
				String NovadataDeNascimento = separandoDataDeNascimento[0] + ""
						+ separandoDataDeNascimento[1] + ""
						+ separandoDataDeNascimento[2] + ""
						+ separandoDataDeNascimento[3] + ""
						+ separandoDataDeNascimento[4] + "";

				
				
				char[] separandoDataSistema = dataDoSistema.toCharArray();

				String NovadataDeSistema = separandoDataSistema[0] + ""
						+ separandoDataSistema[1] + ""
						+ separandoDataSistema[2] + ""
						+ separandoDataSistema[3] + ""
						+ separandoDataSistema[4] + "";

				if (NovadataDeNascimento.equals(NovadataDeSistema)) {

					cliente = new Cliente();

					cliente.setCodigo(codigo);
					cliente.setNomeCompleto(nomeCompleto);
					cliente.setTelefone01(telefone01);
					cliente.setTelefone02(telefone02);
					cliente.setEmail(email);

					clientes.add(cliente);

				}

			}

		}
		
		return clientes;

	}
	
}