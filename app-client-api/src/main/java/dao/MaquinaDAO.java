package dao;

import model.Maquina;
import model.Usuario;
import oshi.SystemInfo;
import oshi.hardware.ComputerSystem;
import oshi.hardware.HardwareAbstractionLayer;
import util.database.MySQLConnection;
import util.logs.LogGenerator;
import util.logs.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


import app.integration.Userinfo;

public class MaquinaDAO {

	public Optional<Maquina> monitorarMaquina(Usuario usuario) throws SQLException, IOException {
		try (Connection connection = MySQLConnection.ConBD()) {

			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM Maquina JOIN usuario on Maquina.fkUsuario = usuario.idUsuario JOIN ipv4 ON ipv4.fkMaquina = Maquina.idMaquina WHERE idUsuario = ?");

			preparedStatement.setInt(1, usuario.getIdUsuario());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				Maquina maquina = null;
				while (resultSet.next()) {
					if (maquina == null) {
						maquina = criaMaquina(resultSet, usuario);
						especificarMaquina(maquina);
					}

					maquina.getIpv4().add(resultSet.getString("numeroIP"));
				}
				return Optional.of(maquina);
			} catch (SQLException e) {
				throw new SQLException("Erro ao executar a consulta da maquina no banco!", e);
			}
		} catch (SQLException e) {
			Logger.logError("Não foi possível abrir a conexão com o banco!:", e.getMessage(), e);
			LogGenerator.logError("Não foi possível abrir a conexão com o banco!:", e.getMessage(), e);
			throw new RuntimeException("Erro ao abrir conexão com o banco!!", e);
		}
	}

	public void especificarMaquina(Maquina maquina) throws SQLException, IOException {
		try (Connection connection = MySQLConnection.ConBD()) {

			verifyMaquina(maquina);
			
			PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Maquina SET numeroIdentificacao = ?, modelo = ?, marca = ?, username = ?, hostname = ? WHERE idMaquina = ?");

			preparedStatement.setString(1, maquina.getNumeroSerial());
			preparedStatement.setString(2, maquina.getModelo());
			preparedStatement.setString(3, maquina.getMarca());
			preparedStatement.setString(4, maquina.getUsername());
			preparedStatement.setString(5, maquina.getHostname());
			preparedStatement.setInt(6, maquina.getIdMaquina());

			preparedStatement.executeUpdate();		
		} catch (SQLException | IOException e) {
			Logger.logError("Não foi possível abrir a conexão com o banco!:", e.getMessage(), e);
			LogGenerator.logError("Não foi possível abrir a conexão com o banco!:", e.getMessage(), e);
			throw new RuntimeException("Erro ao abrir conexão com o banco!!", e);
		}
	}
	
	public static String getIpv4() throws IOException {
		Userinfo userinfo = new Userinfo();
		return userinfo.ipv4();
	}
	
	private void verifyMaquina(Maquina maquina) throws SQLException, IOException {
		HardwareAbstractionLayer hardware = new SystemInfo().getHardware();
		ComputerSystem computerSystem = hardware.getComputerSystem();
	
		String fabricante = computerSystem.getManufacturer();
		String modelo = computerSystem.getModel();
		String numeroDeSerie = computerSystem.getSerialNumber();

		Userinfo userinfo = new Userinfo();
		String hostname = userinfo.hostname();
		String username = userinfo.username();

		try (Connection connection = MySQLConnection.ConBD()) {
		
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Maquina WHERE idMaquina = ?");
			
			preparedStatement.setInt(1, maquina.getIdMaquina());
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					maquina.setNumeroSerial(resultSet.getString("numeroIdentificacao") == numeroDeSerie ? maquina.getNumeroSerial() : numeroDeSerie);
					maquina.setModelo(resultSet.getString("modelo") == modelo ? maquina.getModelo() : modelo);
					maquina.setMarca(resultSet.getString("marca") == fabricante ? maquina.getMarca() : fabricante);
					maquina.setUsername(resultSet.getString("username") == username ? maquina.getUsername() : username);
					maquina.setHostname(resultSet.getString("hostname") == hostname ? maquina.getHostname() : hostname);
				}
			} catch (SQLException e) {
				throw new SQLException("Erro ao executar a consulta da maquina no banco!", e);
			}
		} catch (SQLException e) {
			Logger.logError("Não foi possível abrir a conexão com o banco!:", e.getMessage(), e);
			LogGenerator.logError("Não foi possível abrir a conexão com o banco!:", e.getMessage(), e);
			throw new RuntimeException("Erro ao abrir conexão com o banco!!", e);
		}
	}
	
	private Maquina criaMaquina(ResultSet resultSet, Usuario usuario) throws SQLException {
		return new Maquina(resultSet.getInt("idMaquina"), usuario);
	}
}
