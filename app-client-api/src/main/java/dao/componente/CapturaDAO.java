package dao.componente;

import model.componentes.Componente;
import model.Captura;
import model.Maquina;
import util.database.MySQLConnection;
import util.logs.LogGenerator;
import util.logs.Logger;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class CapturaDAO {
	public Integer inserirCaptura(Maquina maquina, Componente componente) throws IOException {
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

			LogGenerator.logInfo("""

					Inserindo captura de dados no banco de dados:
					    |Componente: %s
					    |Dado capturado: %.2f
					    |Unidade de medida: %s
					    |Data hora captura: %s
					    |Id componente: %d
					""".formatted(componente.getComponente(), componente.getDadoCaptura(),
					componente.getUnidadeMedida(), LocalDateTime.now().toString(), componente.getIdComponente()), LogGenerator.LogType.INFO);

			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO captura (dadoCaptura, unidadeMedida, dataCaptura, fkComponente) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setDouble(1, Optional.ofNullable(componente.getDadoCaptura()).orElse(0.0));
			preparedStatement.setString(2, Optional.ofNullable(componente.getUnidadeMedida()).orElse(""));
			preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			preparedStatement.setInt(4, componente.getIdComponente());

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows != 0) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int idCaptura = generatedKeys.getInt(1);
						this.selecionarById(idCaptura);
						return idCaptura;
					} else {
						throw new SQLException("Falha ao obter o ID da Configuração criado.");
					}
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

	public Optional<Captura> selecionarById(Integer idCaptura) {
		try (Connection connection = MySQLConnection.ConBD()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM captura WHERE idCaptura = ?");
			preparedStatement.setInt(1, idCaptura);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
                    Captura captura = this.criarCaptura(resultSet);
					return Optional.of(captura);
				};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return Optional.empty();
	}

	private Captura criarCaptura(ResultSet resultSet) {
		Captura captura = new Captura();
		try {
			captura.setIdCaptura(resultSet.getInt(1));
			captura.setDadoCaptura(resultSet.getDouble(2));
			captura.setUnidadeMedida(resultSet.getString(3));
			captura.setDataCaptura(resultSet.getTimestamp(4));
			captura.setFkComponente(resultSet.getInt(5));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return captura;
	}
}
