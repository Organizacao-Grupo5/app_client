package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Alerta;
import util.database.MySQLConnection;

public class AlertaDAO {

    public List<Alerta> selecionarTodos() {
		List<Alerta> listaAlertas = new ArrayList<>();
		try (Connection conexao = MySQLConnection.ConBD()) {
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

	private Alerta createAlerta(ResultSet resultSet) throws SQLException {
		Alerta alerta = new Alerta();
		alerta.setIdAlerta(resultSet.getInt("idAlertas"));
		alerta.setDefinicaoPercentual(resultSet.getString("definicaoPercentual"));
		alerta.setMensagem(resultSet.getString("mensagem"));
		alerta.setPercentualMaximo(resultSet.getDouble("percentualMaximo"));
		alerta.setTipoAlerta(resultSet.getString("tipoAlerta"));
		return alerta;
	}

}
