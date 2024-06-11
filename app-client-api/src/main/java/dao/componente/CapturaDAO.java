package dao.componente;

import model.componentes.Componente;
import model.Captura;
import model.Maquina;
import util.database.MySQLConnection;
import util.logs.LogBanco;
import util.logs.Logger;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class CapturaDAO {
	public Integer inserirCaptura(Maquina maquina, Componente componente) throws IOException {
		int idCaptura = 0;
		try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
			Logger.logInfo("""

					Inserindo captura de dados no banco de dados:
					    |Componente: %s
					    |Dado capturado: %.2f
					    |Unidade de medida: %s
					    |Data hora captura: %s
					    |Id componente: %d
					""".formatted(componente.getComponente(), componente.getDadoCaptura(),
					componente.getUnidadeMedida(), LocalDateTime.now().toString(), componente.getIdComponente()));

			LogBanco.logInfo("""

					Inserindo captura de dados no banco de dados:
					    |Componente: %s
					    |Dado capturado: %.2f
					    |Unidade de medida: %s
					    |Data hora captura: %s
					    |Id componente: %d
					""".formatted(componente.getComponente(), componente.getDadoCaptura(),
					componente.getUnidadeMedida(), LocalDateTime.now().toString(), componente.getIdComponente()), LogBanco.LogType.INFO);

			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO captura (dadoCaptura, unidadeMedida, dataCaptura, fkComponente, dadoCapturaPercent) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setDouble(1, Optional.ofNullable(componente.getDadoCaptura()).orElse(0.0));
			preparedStatement.setString(2, Optional.ofNullable(componente.getUnidadeMedida()).orElse(""));
			preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
			preparedStatement.setInt(4, componente.getIdComponente());
			preparedStatement.setDouble(5, Optional.ofNullable(componente.getPercentDadoCaptura()).orElse(0.0));

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows != 0) {
				try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						idCaptura = generatedKeys.getInt(1);
						this.selecionarById(idCaptura);
						RegistroAlertasDAO.verificarUsoComponentes(idCaptura);
					} else {
						throw new SQLException("Falha ao obter o ID da Configuração criado.");
					}
				}
			}
		} catch (SQLException e) {
			Logger.logError("Ocorreu um erro ao salvar suas capturas SQLServer", e.getMessage(), e);
			LogBanco.logError("Ocorreu um erro ao salvar suas capturas SQLServer", e.getMessage(), e);
		} catch (IOException e) {
			Logger.logError("Ocorreu um erro ao salvar suas capturas SQLServer", e.getMessage(), e);
        }

		try (Connection connection = MySQLConnection.ConnectionMySql()) {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"INSERT INTO captura (idCaptura, dadoCaptura, unidadeMedida, dataCaptura, fkComponente, dadoCapturaPercent) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, idCaptura);
			preparedStatement.setDouble(2, Optional.ofNullable(componente.getDadoCaptura()).orElse(0.0));
			preparedStatement.setString(3, Optional.ofNullable(componente.getUnidadeMedida()).orElse(""));
			preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			preparedStatement.setInt(5, componente.getIdComponente());
			preparedStatement.setDouble(6, Optional.ofNullable(componente.getPercentDadoCaptura()).orElse(0.0));

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Logger.logError("Ocorreu um erro ao salvar suas capturas no MySQL", e.getMessage(), e);
		}

		return idCaptura;
    }

	public Optional<Captura>  selecionarById(Integer idCaptura) {
		try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM captura WHERE idCaptura = ?");
			preparedStatement.setInt(1, idCaptura);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
                    Captura captura = this.criarCaptura(resultSet);
					return Optional.of(captura);
				};
            }
        } catch (SQLException e) {
			Logger.logError("Ocorreu um erro ao salvar suas capturas", e.getMessage(), e);
		}
		return Optional.empty();
	}

	private Captura criarCaptura(ResultSet resultSet) {
		Captura captura = new Captura();
		try {
			captura.setIdCaptura(resultSet.getInt("idCaptura"));
			captura.setDadoCaptura(resultSet.getDouble("dadoCaptura"));
			captura.setUnidadeMedida(resultSet.getString("unidadeMedida"));
			captura.setDataCaptura(resultSet.getTimestamp("dataCaptura"));
			captura.setDadoCapturaPercent(Optional.ofNullable(resultSet.getDouble("dadoCapturaPercent")).orElse(0.0));
			captura.setFkComponente(resultSet.getInt("fkComponente"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return captura;
	}
}
