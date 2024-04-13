package dao;

import util.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificaMonitoramentoDAO {
    public boolean isPrimeiroMonitoramentoGPU(int idMaquina) {
        try (Connection connection = Connector.ConBD();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM GPU JOIN Maquina ON fkMaquina = idMaquina WHERE idMaquina = ?")) {

            preparedStatement.setInt(1, idMaquina);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isPrimeiroMonitoramentoCPU(int idMaquina) {
        try (Connection connection = Connector.ConBD();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM CPU JOIN Maquina ON fkMaquina = idMaquina WHERE idMaquina = ?")) {

            preparedStatement.setInt(1, idMaquina);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isPrimeiroMonitoramentoRAM(int idMaquina) {
        try (Connection connection = Connector.ConBD();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Memoria_Ram JOIN Maquina ON fkMaquina = idMaquina WHERE idMaquina = ?")) {

            preparedStatement.setInt(1, idMaquina);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPrimeiroMonitoramentoCONUSB(int idMaquina) {
        try (Connection connection = Connector.ConBD();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM Conexao_USB JOIN Maquina ON fkMaquina = idMaquina WHERE idMaquina = ?")) {

            preparedStatement.setInt(1, idMaquina);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPrimeiroMonitoramentoAPP(int idMaquina) {
        try (Connection connection = Connector.ConBD();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM APP JOIN Maquina ON fkMaquina = idMaquina WHERE idMaquina = ?")) {

            preparedStatement.setInt(1, idMaquina);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPrimeiroMonitoramentoHDD(int idMaquina) {
        try (Connection connection = Connector.ConBD();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM HDD JOIN Maquina ON fkMaquina = idMaquina WHERE idMaquina = ?")) {

            preparedStatement.setInt(1, idMaquina);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
