package dao;

import model.Maquina;
import model.Usuario;
import oshi.SystemInfo;
import util.database.MySQLConnection;
import util.logs.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import app.integration.Userinfo;

public class MaquinaDAO {
	public Optional<Maquina> monitorarMaquina(Usuario usuario) throws SQLException {
		try (Connection conexao = MySQLConnection.ConBD()) {
			PreparedStatement preparedStatement = conexao.prepareStatement(
					"SELECT * FROM maquina JOIN usuario on maquina.fkUsuario = usuario.idUsuario JOIN ipv4 ON ipv4.fkMaquina = maquina.idMaquina WHERE idUsuario = ?");

			preparedStatement.setInt(1, usuario.getIdUsuario());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Maquina maquina = null;
				while (resultSet.next()) {
					if (maquina == null) {
						maquina = criaMaquina(resultSet, usuario);
					}

					Userinfo userinfo = new Userinfo();

					// VERIFICAR SE EXISTE ESSA INFORMAÇÔES NO BANCO ANTES DE INSERIR;
					
					SystemInfo systemInfo = new SystemInfo();
					String manufacturer = systemInfo.getHardware().getComputerSystem().getManufacturer();
        			String model = systemInfo.getHardware().getComputerSystem().getModel();
			        String serialNumber = systemInfo.getHardware().getComputerSystem().getSerialNumber();

					String hostname = userinfo.hostname();
					String username = userinfo.username();
					
					maquina.setHostname(hostname);
					maquina.setUsername(username);

					maquina.getIpv4().add(resultSet.getString("numeroIP"));
				}
				return Optional.of(maquina);
			}
		} catch (SQLException e) {
			Logger.logError("Não foi possível encontrar a máquina do usuário:", e.getMessage(), e);
			throw new RuntimeException("Erro ao buscar máquina do usuário", e);
		}
	}

	private Maquina criaMaquina(ResultSet resultSet, Usuario usuario) throws SQLException {
		return new Maquina(resultSet.getInt("idMaquina"), usuario);
	}
}
