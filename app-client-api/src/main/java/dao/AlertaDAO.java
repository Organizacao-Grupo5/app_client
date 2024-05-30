package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.system.SystemMonitor;
import model.Alerta;
import model.componentes.APP;
import model.componentes.Bateria;
import model.componentes.CPU;
import model.componentes.Componente;
import model.componentes.GPU;
import model.componentes.HDD;
import model.componentes.MemoriaRam;
import model.componentes.SistemaOp;
import util.database.MySQLConnection;

public class AlertaDAO {

	public Alerta criarAlerta(Componente componente) {

		this.verificarDados(componente);
		try (Connection connection = MySQLConnection.ConBD()) {
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO alertas (fkComponente, percentualMaximo, mensagem, tipoAlerta, definicaoPercentual) VALUES (?, ?, ?, ?, ?)");

			preparedStatement.setInt(1, componente.getIdComponente());
			// preparedStatement.setDouble(2, );
			// preparedStatement.setString(3, );
			// preparedStatement.setString(4, );
			// preparedStatement.setString(5, );

			preparedStatement.executeUpdate();

			

		} catch (SQLException e) {
			throw new RuntimeException("Erro ao selecionar todos dados da tabela Alertas!", e);
		}
	}

	public void verificarDados(Componente componente) {
		SystemMonitor systemMonitor = new SystemMonitor();


		System.out.println("VALOR COMPONENTE");
		System.out.println(componente.getDadoCaptura());
	}

    public List<Alerta> select() {
		List<Alerta> listaAlertas = new ArrayList<>();
		try (Connection conexao = MySQLConnection.ConBD();
			PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM alertas")) {
			
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
