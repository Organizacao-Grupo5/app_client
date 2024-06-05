package dao.componente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import util.database.MySQLConnection;
import util.logs.Logger;

public class RegistroAlertasDAO {

    public Integer gerarRegistro(Integer idCaptura, Integer idAlerta) {
        try (Connection connection = MySQLConnection.ConBD()) {
            
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO registroAlerta (horario, fkAlerta, fkCaptura) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			preparedStatement.setInt(2, idAlerta);
			preparedStatement.setInt(3, idCaptura);

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows > 0) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int idRegistroAlertas = generatedKeys.getInt(1);
						return idRegistroAlertas;
					}
				}
			}
		} catch (SQLException e) {
			Logger.logError("Ocorreu um erro ao salvar seus registros", e.getMessage(), e);
        }
		return 0;
	}
}
