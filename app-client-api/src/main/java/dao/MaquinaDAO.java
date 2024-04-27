package dao;

import model.Maquina;
import model.Usuario;
import util.database.MySQLConnection;
import util.logs.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MaquinaDAO {
    public Optional<Maquina> monitorarMaquina(Usuario usuario) throws SQLException {
        try (Connection conexao = MySQLConnection.ConBD()){
            PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM maquina JOIN usuario on maquina.fkUsuario = usuario.idUsuario WHERE idUsuario = ?");

            preparedStatement.setInt(1, usuario.getIdUsuario());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(criaMaquina(resultSet, usuario));
                }
            }
        }catch (SQLException e) {
            Logger.logError("Não foi possível encontrar a máquina do usuário:", e.getMessage(), e);
            throw new RuntimeException("Erro ao buscar máquina do usuário", e);
        }
        return Optional.empty();
    }

    private Maquina criaMaquina(ResultSet resultSet, Usuario usuario) throws SQLException {
        return new Maquina(
                resultSet.getInt("idMaquina"),
                resultSet.getString("ipv4"),
                usuario
        );
    }
}
