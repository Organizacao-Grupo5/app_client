package dao;

import model.Usuario;
import util.database.MySQLConnection;
import util.logs.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioDAO {

	public Optional<Usuario> findByEmailAndSenha(String email, String senha) throws SQLException {
		try (Connection connection = MySQLConnection.ConBD()) {

			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Usuario JOIN empresa ON usuario.fkEmpresa = empresa.idEmpresa JOIN plano ON empresa.fkPlano = plano.idPlano WHERE email = ? and senha = ?");

			preparedStatement.setString(1, email);
			preparedStatement.setString(2, senha);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return Optional.of(createUser(resultSet));
				}
			} catch (SQLException e) {
				throw new SQLException("Erro ao buscar usuário por email e senha", e);
			}
		} catch (SQLException e) {
			Logger.logError("\"Não foi possível abrir a conexão com o banco!:", e.getMessage(), e);
			throw new RuntimeException("Erro ao abrir a conexão com o banco!", e);
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
