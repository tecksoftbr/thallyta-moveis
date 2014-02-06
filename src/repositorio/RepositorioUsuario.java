package repositorio;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Usuario;
import views.principais.tela_de_erro.ErroEncontrado;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class RepositorioUsuario implements IRepositorioUsuario {

	private static String sqlUsuarios = "insert into usuarios values (?,?,?,?,?,?,?,?)";

	@Override
	public void cadastrandoUsuario(Connection conexao, Usuario usu)
			throws SQLException {

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sqlUsuarios)) {

			stm.setInt(1, 0);
			stm.setString(2, usu.getNomeCompleto());
			stm.setString(3, usu.getDataDeCadastro());
			stm.setString(4, usu.getApelido());
			stm.setString(5, usu.getSenha());
			stm.setString(6, usu.getPerguntaSecreta());
			stm.setString(7, usu.getRespostaSecreta());
			stm.setString(8, usu.getSobreMim());

			stm.executeUpdate();

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public boolean buscandoUsuario(Connection conexao, Usuario usuario)
			throws SQLException {

		String sql = "select apelido, senha from usuarios";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				if (usuario.getApelido().equals(rs.getString(1))
						&& usuario.getSenha().equals(rs.getString(2))) {

					return true;

				}

			}

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

		return false;

	}

	public Usuario verificandoUsuario(Connection conexao, String nomeOuApelido)
			throws SQLException {

		Usuario usu = new Usuario();

		String sql = "select nome_completo, data_de_cadastro, apelido, senha, pergunta_secreta, resposta_secreta, sobre_mim from usuarios";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				usu.setNomeCompleto(rs.getString(1));
				usu.setDataDeCadastro(rs.getString(2));
				usu.setApelido(rs.getString(3));
				usu.setSenha(rs.getString(4));
				usu.setPerguntaSecreta(rs.getString(5));
				usu.setRespostaSecreta(rs.getString(6));
				usu.setSobreMim(rs.getString(7));

				if (usu.getNomeCompleto().equals(nomeOuApelido)
						|| usu.getApelido().equals(nomeOuApelido)) {

					return usu;

				}

			}

		}

		return usu;

	}

	@Override
	public void alterandoSenha(Connection conexao, Usuario usuario)
			throws SQLException {

		String sql = "UPDATE usuarios SET senha=? WHERE apelido=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setString(1, usuario.getSenha());
			stm.setString(2, usuario.getApelido());

			stm.executeUpdate();

		}

		catch (Exception e) {

			new ErroEncontrado();

		}

	}

	@Override
	public ArrayList<Usuario> listandoUsuarios(Connection conexao)
			throws SQLException {

		String sql = "SELECT nome_completo, data_de_cadastro, apelido, senha, pergunta_secreta, resposta_secreta, sobre_mim from USUARIOS";

		ArrayList<Usuario> usuarios = new ArrayList<>();

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

			while (rs.next()) {

				String nomeCompleto = (rs.getString(1));
				String dataDeCadastro = (rs.getString(2));
				String apelido = (rs.getString(3));
				String senha = (rs.getString(4));
				String perguntaSecreta = (rs.getString(5));
				String respostaSecreta = (rs.getString(6));
				String sobreMim = (rs.getString(7));

				Usuario usuario = new Usuario();

				usuario.setApelido(apelido);
				usuario.setDataDeCadastro(dataDeCadastro);
				usuario.setNomeCompleto(nomeCompleto);
				usuario.setPerguntaSecreta(perguntaSecreta);
				usuario.setRespostaSecreta(respostaSecreta);
				usuario.setSenha(senha);
				usuario.setSobreMim(sobreMim);

				usuarios.add(usuario);

			}

		}

		return usuarios;

	}

	@Override
	public void removerUsuario(Usuario usuario, Connection conexao)
			throws SQLException {

		String sql = "DELETE FROM usuarios WHERE nome_completo=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setString(1, usuario.getNomeCompleto());

			stm.executeUpdate();

		}

	}

	@Override
	public void alterandoUsuario(Connection conexao, String apelidoOriginal,
			Usuario usu) throws SQLException {

		String sql = "UPDATE usuarios SET nome_completo=?, data_de_cadastro=?, apelido=?, senha=?, "
				+ "pergunta_secreta=?, resposta_secreta=?, sobre_mim=?WHERE apelido=?";

		try (PreparedStatement stm = (PreparedStatement) conexao
				.prepareStatement(sql)) {

			stm.setString(1, usu.getNomeCompleto());
			stm.setString(2, usu.getDataDeCadastro());
			stm.setString(3, usu.getApelido());
			stm.setString(4, usu.getSenha());
			stm.setString(5, usu.getPerguntaSecreta());
			stm.setString(6, usu.getRespostaSecreta());
			stm.setString(7, usu.getSobreMim());

			stm.setString(8, apelidoOriginal);

			stm.executeUpdate();

		}

	}

}