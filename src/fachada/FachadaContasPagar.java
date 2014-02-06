package fachada;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.ContasPagar;
import repositorio.RepositorioContasPagar;
import views.principais.tela_de_erro.ErroEncontrado;

public class FachadaContasPagar {

	private static final String url = "jdbc:mysql://localhost/thallyta_moveis";
	private static final String usuario = "root";
	private static final String senha = "";

	public void adicionarContasPagar(ContasPagar contas) throws SQLException {

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioContasPagar cadastrandoBD = new RepositorioContasPagar();
			cadastrandoBD.adicionarContasPagar(conexao, contas);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	public boolean verificandoNumeroDocumento(String numeroDoDocumento) {

		boolean verificando = true;

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioContasPagar verificar = new RepositorioContasPagar();
			verificando = verificar.verificandoNumeroDocumento(conexao,
					numeroDoDocumento);

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		return verificando;

	}

	public ArrayList<ContasPagar> listandoContas() throws SQLException {

		ArrayList<ContasPagar> contasBd = new ArrayList<>();

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioContasPagar listando = new RepositorioContasPagar();
			contasBd = listando.listandoContas(conexao);

		}

		return contasBd;

	}

	public void removerContaPagar(String numeroDoDocumento) throws SQLException {

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioContasPagar passandoValores = new RepositorioContasPagar();
			passandoValores.removendoContaPagar(conexao, numeroDoDocumento);

		}

	}

	public void alterandoContaPagar(String numeroOriginal, ContasPagar conta)
			throws SQLException {

		try (Connection conexao = DriverManager.getConnection(url, usuario,
				senha)) {

			RepositorioContasPagar alterando = new RepositorioContasPagar();
			alterando.atualizandoConta(conexao, numeroOriginal, conta);

		}

	}

}