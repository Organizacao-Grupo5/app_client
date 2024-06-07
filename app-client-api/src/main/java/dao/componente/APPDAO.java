package dao.componente;

import model.Maquina;
import model.componentes.APP;
import util.database.MySQLConnection;
import util.logs.LogGenerator;
import util.logs.Logger;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class APPDAO {
    public void gravaApps(List<APP>apps, Maquina maquina) throws Exception{
        try(Connection connection = MySQLConnection.ConBD()) {
            LocalDateTime localDateTime = LocalDateTime.now();
            apps.forEach(app -> {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO apps (nomeApp, pid, ramConsumida, localidade) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1,app.getNome());
                    preparedStatement.setInt(2, Integer.valueOf(app.getModelo()));
                    preparedStatement.setDouble(3,app.getDadoCaptura() == null ? 0.0 : app.getDadoCaptura());
                    preparedStatement.setString(4,app.getComando());

                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Falha ao salvar o app, nenhuma linha afetada.");
                    }

                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            app.setIdApp(generatedKeys.getInt(1));
                        } else {
                            throw new SQLException("Falha ao obter o ID do app criado.");
                        }
                    }

                    PreparedStatement preparedStatementAppAcessado = connection.prepareStatement("INSERT INTO appAcessado (fkApp, fkMaquina, hora) VALUES (?,?,?)");

                    preparedStatementAppAcessado.setInt(1, app.getIdApp());
                    preparedStatementAppAcessado.setInt(2, maquina.getIdMaquina());
                    preparedStatementAppAcessado.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

                    int affectedRowsAppsAcessados = preparedStatementAppAcessado.executeUpdate();

                    if (affectedRows == 0) {
                        throw new SQLException("Falha ao salvar o app, nenhuma linha afetada.");
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
