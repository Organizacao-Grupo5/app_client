package dao.componente;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.naming.spi.DirStateFactory.Result;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import model.componentes.Componente;
import util.database.MySQLConnection;
import util.logs.LogGenerator;
import util.logs.Logger;

public class RegistroAlertasDAO {

    public Integer insert(Integer idCaptura, Componente componente) {
        try (Connection connection = MySQLConnection.ConBD()) {
            
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO registroalertas (horario, fkAlerta, fkCaptura, fkComponente) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setNull(1, Types.TIMESTAMP);
			preparedStatement.setInt(2, alerta.getIdAlerta());
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
			LogGenerator.logError("Ocorreu um erro ao salvar suas capturas", e.getMessage(), e);
		} catch (IOException e) {
            throw new RuntimeException(e);
        }
		return 0;
	}
}
