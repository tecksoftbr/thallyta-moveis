package repositorio;

import java.sql.SQLException;
import java.util.ArrayList;

import modelo.Usuario;

import com.mysql.jdbc.Connection;

public interface IRepositorioUsuario {

	public boolean buscandoUsuario(Connection conexao, Usuario usuario)
			throws SQLException;

	public void cadastrandoUsuario(Connection conexao, Usuario usuario)
			throws SQLException;

	public Usuario verificandoUsuario(Connection conexao, String nomeOuApelido)
			throws SQLException;

	public void alterandoSenha(Connection conexao, Usuario usuario)
			throws SQLException;

	public ArrayList<Usuario> listandoUsuarios(Connection conexao)
			throws SQLException;

	public void removerUsuario(Usuario usuario, Connection conexao)
			throws SQLException;

	public void alterandoUsuario(Connection conexao, String apelidoOriginal,
			Usuario usu) throws SQLException;

}