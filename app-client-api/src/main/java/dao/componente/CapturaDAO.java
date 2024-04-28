package dao.componente;

import model.componentes.Componente;
import model.Maquina;
import util.database.MySQLConnection;
import util.logs.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class CapturaDAO {
	public void inserirCaptura(Maquina maquina, Componente componente) {
		try (Connection connection = MySQLConnection.ConBD()) {
			Logger.logInfo("""

					Inserindo captura de dados no banco de dados:
					    |Componente: %s
					    |Dado capturado: %.2f
					    |Unidade de medida: %s
					    |Data hora captura: %s
					    |Id componente: %d
					""".formatted(componente.getComponente(), componente.getDadoCaptura(),
					componente.getUnidadeMedida(), LocalDateTime.now().toString(), componente.getIdComponente()));

			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO captura (dadoCaptura, unidadeMedida, dataCaptura, fkComponente) VALUES (?,?,?,?)");

			preparedStatement.setDouble(1, Optional.ofNullable(componente.getDadoCaptura()).orElse(0.0));
			preparedStatement.setString(2, Optional.ofNullable(componente.getUnidadeMedida()).orElse(""));
			preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			preparedStatement.setInt(4, componente.getIdComponente());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			Logger.logError("Ocorreu um erro ao salvar suas capturas", e.getMessage(), e);
		}
	}
}
