package dao;

import model.Usuario;
import util.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UsuarioDAO {

    public Optional<Usuario> findByEmailAndSenha(String email, String senha) {
        try (Connection conexao = Connector.ConBD();
             PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM Usuario WHERE email = ? and senha = ?")) {

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
        usuario.setNome(resultSet.getString("Nome"));
        usuario.setEmail(resultSet.getString("Email"));
        usuario.setSenha(resultSet.getString("Senha"));
        usuario.setCpfCnpj(resultSet.getString("CPF_CNPJ"));
        usuario.setStatus(resultSet.getString("Status"));
        usuario.setIdPlano(resultSet.getInt("fkPlano"));
        return usuario;
    }
}
