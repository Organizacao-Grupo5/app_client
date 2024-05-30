package dao.componente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Optional;

import model.componentes.Componente;
import util.database.MySQLConnection;
import util.logs.LogGenerator;
import util.logs.Logger;

public class RegistroAlertasDAO {

    public void insert(Integer idCaptura, Componente componente) {
        try (Connection connection = MySQLConnection.ConBD()) {
            
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO registroalertas (horario, fkAlerta, fkCaptura, fkComponente) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			// preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
			// preparedStatement.setDouble(2, Optional.ofNullable(componente.getDadoCaptura()).orElse(0.0));
			// preparedStatement.setString(3, Optional.ofNullable(componente.getUnidadeMedida()).orElse(""));
			// preparedStatement.setInt(4, componente.getIdComponente());

			// preparedStatement.executeUpdate();

			// return preparedStatement.getGeneratedKeys().getInt(1);

		} catch (SQLException e) {
			Logger.logError("Ocorreu um erro ao salvar suas capturas", e.getMessage(), e);
			LogGenerator.logError("Ocorreu um erro ao salvar suas capturas", e.getMessage(), e);
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
