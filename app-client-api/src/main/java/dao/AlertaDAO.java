package dao;

import model.Alerta;
import util.database.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlertaDAO {

    public List<Alerta> selecionarTodos() {
		List<Alerta> listaAlertas = new ArrayList<>();
		try (Connection conexao = MySQLConnection.ConnectionMySql()) {
			PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM alerta");
			
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Alerta alerta = createAlerta(resultSet);
					listaAlertas.add(alerta);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao selecionar todos dados da tabela Alertas!", e);
		}

		return listaAlertas;
	}

	private Alerta createAlerta(ResultSet resultSet) {
		Alerta alerta = new Alerta();
		try {
			alerta.setIdAlerta(resultSet.getInt(1));
			alerta.setMensagem(resultSet.getString(2));
			alerta.setTipoAlerta(resultSet.getString(3));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alerta;
	}

}
