package dao.componente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import model.componentes.Componente;
import util.database.MySQLConnection;
import util.logs.Logger;

public class RegistroAlertasDAO {

    public Integer gerarRegistro(Integer idCaptura, Componente componente, Integer idAlerta) {
        try (Connection connection = MySQLConnection.ConBD()) {
            
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO registroalertas (horario, fkAlerta, fkCaptura, fkComponente) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setNull(1, Types.TIMESTAMP);
			preparedStatement.setInt(2, idAlerta);
			preparedStatement.setInt(3, idCaptura);
			preparedStatement.setInt(4, componente.getIdComponente());

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows != 0) {
				try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					int idRegistroAlertas = resultSet.getInt(1);
					return idRegistroAlertas;
				}
			}
		} catch (SQLException e) {
			Logger.logError("Ocorreu um erro ao salvar suas capturas", e.getMessage(), e);
        }
		return 0;
	}
}
