package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import views.principais.tela_de_erro.ErroEncontrado;
import views.principais.tela_de_inicio.TelaPrincipal;

import modelo.ContasPagar;

public class RepositorioContasPagar implements IRepositorioContasPagar {

	private static String sqlContasPagar = "insert into pagar_contas values (?,?,?,?,?,?)";

	public void adicionarContasPagar(Connection con, ContasPagar contas)
			throws SQLException {

		try (PreparedStatement stm = con.prepareStatement(sqlContasPagar)) {

			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

			stm.setString(1, contas.getTipoConta());
			stm.setString(2, contas.getNumeroDocumento());
			stm.setString(3, contas.getDataEntrada());
			stm.setString(4, contas.getDataVencimento());
			stm.setDouble(5, contas.getValor());
			stm.setString(6, contas.getOrigem());

			stm.executeUpdate();

		}

		catch (Exception e) {

			JOptionPane
					.showMessageDialog(
							null,
							"Erro Na Gravação Dos Dados No Banco De Dados, Entre Em Contato Com o Seu Administrador ...",
							"(Aviso) - Erro No Sistema",
							JOptionPane.ERROR_MESSAGE);

			new TelaPrincipal();

		}

	}

	@Override
	public boolean verificandoNumeroDocumento(Connection con,
			String numeroDoDocumento) throws SQLException {

		String sql = "select numero_documento from pagar_contas";

		try (PreparedStatement stm = (PreparedStatement) con
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				if (numeroDoDocumento.equals(rs.getString(1))) {

					return false;

				}

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		return true;

	}

	@Override
	public ArrayList<ContasPagar> listandoContas(Connection conexao)
			throws SQLException {

		String sql = "SELECT tipo, numero_documento, data_entrada, data_vencimento, valor, origem FROM pagar_contas";
		ArrayList<ContasPagar> contasBD = new ArrayList<>();

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				String tipo = rs.getString(1);
				String numeroDocumento = rs.getString(2);
				String dataEntrada = rs.getString(3);
				String dataVencimento = rs.getString(4);
				Double valor = rs.getDouble(5);
				String origem = rs.getString(6);

				ContasPagar conta = new ContasPagar();

				conta.setDataEntrada(dataEntrada);
				conta.setDataVencimento(dataVencimento);
				conta.setNumeroDocumento(numeroDocumento);
				conta.setOrigem(origem);
				conta.setTipoConta(tipo);
				conta.setValor(valor);

				contasBD.add(conta);

			}

		}

		return contasBD;

	}

	@Override
	public void removendoContaPagar(Connection conexao, String numeroDocumento)
			throws SQLException {

		String sql = "DELETE FROM pagar_contas WHERE numero_documento=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setString(1, numeroDocumento);
			stm.executeUpdate();

		}

	}

	@Override
	public void atualizandoConta(Connection conexao, String numeroOriginal,
			ContasPagar conta) throws SQLException {

		String sql = "UPDATE pagar_contas SET tipo=?, numero_documento=?, data_entrada=?, data_vencimento=?, valor=?, origem=? WHERE numero_documento=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setString(1, conta.getTipoConta());
			stm.setString(2, conta.getNumeroDocumento());
			stm.setString(3, conta.getDataEntrada());
			stm.setString(4, conta.getDataVencimento());
			stm.setDouble(5, conta.getValor());
			stm.setString(6, conta.getOrigem());

			stm.setString(7, numeroOriginal);
			stm.executeUpdate();

		}

	}

}