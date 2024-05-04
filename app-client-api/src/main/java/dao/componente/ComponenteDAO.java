package dao.componente;

import model.componentes.*;
import model.Maquina;
import util.database.MySQLConnection;
import util.logs.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponenteDAO {
	public List<Componente> getComponentes(Maquina maquina) throws SQLException {
		Logger.logInfo("Buscando componentes...");
		List<Componente> componentes = new ArrayList<>();
		try (Connection connection = MySQLConnection.ConBD()) {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM componente JOIN maquina ON componente.fkMaquina = maquina.idMaquina WHERE idMaquina = ?");
			preparedStatement.setInt(1, maquina.getIdMaquina());
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Componente componente = verificaTipoComponente(resultSet);
					componentes.add(componente);
				}
			}
		} catch (SQLException e) {
			Logger.logError("Ocorreu um erro ao buscar seus componentes", e.getMessage(), e);
		}
		return componentes;
	}

	private Componente verificaTipoComponente(ResultSet resultSet) throws SQLException {
		String componente = resultSet.getString("componente");
		String modelo = resultSet.getString("modelo");
		String fabricante = resultSet.getString("fabricante");

		Logger.logInfo("""
				Componente encontrado :
				    |Componente: %s
				    |Modelo: %s
				    |Fabricante: %s
				    |ID Componente: %d
				""".formatted(componente, modelo, fabricante, resultSet.getInt("idComponente")));

		Componente componenteInstanciado = null;
		switch (componente.toLowerCase()) {
		case "hdd":
			componenteInstanciado = new HDD();
			break;
		case "memoriaram":
			componenteInstanciado = new MemoriaRam();
			break;
		case "gpu":
			componenteInstanciado = new GPU();
			break;
		case "cpu":
			componenteInstanciado = new CPU();
			break;
		case "volume":
			componenteInstanciado = new Volume();
			break;
		case "bateria":
			componenteInstanciado = new Bateria();
			break;
		case "sistemaop":
			componenteInstanciado = new SistemaOp();
			break;
		case "placamae":
			componenteInstanciado = new PlacaMae();
			break;
		case "app":
			componenteInstanciado = new APP();
			break;
		}

		if (componenteInstanciado != null) {
			componenteInstanciado.setIdComponente(resultSet.getInt("idComponente"));
			componenteInstanciado.setComponente(componente);
			componenteInstanciado.setModelo(modelo);
			componenteInstanciado.setFabricante(fabricante);
		} else {
			Logger.logWarning("Não foi possível instânciar o componente.");
		}

		return componenteInstanciado;
	}

	public void salvarComponente(Maquina maquina, Componente componente) throws SQLException {
		try (Connection connection = MySQLConnection.ConBD()) {
			Logger.logInfo("Iniciando verificação se o componente já existe no banco de dados.");
			if (!componenteExistenteNoBanco(componente, maquina)) {
				try {
					Logger.logInfo("Inserindo dados do componente no banco!");
					PreparedStatement preparedStatement = connection.prepareStatement(
							"INSERT INTO componente (componente, modelo, fabricante, fkMaquina, fkUsuario) VALUES (?,?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setString(1, Optional.ofNullable(componente.getComponente()).orElse("N/A"));
					preparedStatement.setString(2, Optional.ofNullable(componente.getModelo()).orElse("N/A"));
					preparedStatement.setString(3, Optional.ofNullable(componente.getFabricante()).orElse("N/A"));
					preparedStatement.setInt(4, maquina.getIdMaquina());
					preparedStatement.setInt(5, maquina.getUsuario().getIdUsuario());

					int affectedRows = preparedStatement.executeUpdate();

					if (affectedRows == 0) {
						throw new SQLException("Falha ao salvar o componente, nenhuma linha afetada.");
					}

					try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							int idComponente = generatedKeys.getInt(1);
							componente.setIdComponente(idComponente);
							Logger.logInfo("ID do componente criado: " + idComponente);
						} else {
							throw new SQLException("Falha ao obter o ID do componente criado.");
						}
					}
				} catch (SQLException e) {
					Logger.logError("Ocorreu um erro ao salvar seus componentes", e.getMessage(), e);
				}
			}
		}
	}

	public boolean componenteExistenteNoBanco(Componente componente, Maquina maquina) throws SQLException {
		boolean existe = false;
		try (Connection connection = MySQLConnection.ConBD()) {
			Logger.logInfo("""

					        Verificando se o componente %s existe no banco de dados.
					        Modelo: %s
					        Fabricante: %s
					        Id Máquina: %d
					""".formatted(componente.getComponente(), componente.getModelo(), componente.getFabricante(),
					maquina.getIdMaquina()));
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM componente WHERE componente = ? AND modelo = ? AND fabricante = ? AND fkMaquina = ?");
			preparedStatement.setString(1, componente.getComponente());
			preparedStatement.setString(2, componente.getModelo());
			preparedStatement.setString(3, componente.getFabricante());
			preparedStatement.setInt(4, maquina.getIdMaquina());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				existe = resultSet.next();
			}
		} catch (SQLException e) {
			Logger.logError("Ocorreu um erro ao verificar se o componente existe no banco de dados", e.getMessage(), e);
		}
		Logger.logInfo("O componente %s no banco de dados.".formatted(existe ? "existe" : "não existe"));
		return existe;
	}
}
