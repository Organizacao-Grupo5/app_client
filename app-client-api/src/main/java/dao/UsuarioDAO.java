package dao;

import model.Usuario;
import util.database.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioDAO {

	public Optional<Usuario> findByEmailAndSenha(String email, String senha) {
		try (Connection conexao = MySQLConnection.ConBD();
				PreparedStatement preparedStatement = conexao.prepareStatement(
						"SELECT * FROM Usuario JOIN empresa ON usuario.fkEmpresa = empresa.idEmpresa JOIN plano ON empresa.fkPlano = plano.idPlano WHERE email = ? and senha = ?")) {

			preparedStatement.setString(1, email);
			preparedStatement.setString(2, senha);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(createUser(resultSet));
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao buscar usuário por email e senha", e);
		}

		return Optional.empty();
	}

	private Usuario createUser(ResultSet resultSet) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(resultSet.getInt("idUsuario"));
		usuario.setNome(resultSet.getString("nome"));
		usuario.setEmail(resultSet.getString("email"));
		usuario.setSenha(resultSet.getString("senha"));
		usuario.setCargo(resultSet.getString("cargo"));
		usuario.setFkPlano(resultSet.getInt("fkPlano"));
		usuario.setFkEmpresa(resultSet.getInt("fkEmpresa"));
		return usuario;
	}
}
