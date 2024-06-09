package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import model.Ipv4;
import model.Rede;
import service.ServiceRede;
import util.database.MySQLConnection;
import util.logs.Logger;

public class RedeIpv4DAO {
    public void insert(Rede rede, Ipv4 ipv4) throws SQLException {
        try (Connection conexao = MySQLConnection.ConnectionMySql()) {
            PreparedStatement preparedStatement = conexao.prepareStatement("INSERT INTO relredeipv4 (fkRede, fkIpv4) VALUES (?, ?)");

            preparedStatement.setInt(1, rede.getIdRede());
            preparedStatement.setInt(2, ipv4.getIdIpv4());
        
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            Logger.logError("Não foi possível inserir valores à entidade 'rede': ", e.getMessage(), e);
			throw new RuntimeException("Erro ao inserir dados à tabela rede", e);
        }
    } 

    public List<String> selectIps(Rede rede) {
        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM relredeIpv4 JOIN ipv4 ON fkIpv4 = idIpv4 JOIN rede ON fkRede = idRede WHERE fkRede = ?");

            preparedStatement.setInt(1, rede.getIdRede());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<String> listaIPs = new ArrayList<>();
                while (resultSet.next()) {
                    listaIPs.add(resultSet.getString("numeroIP"));
                }

                ServiceRede.compararDispositivos(listaIPs);

                return listaIPs;
            }

        } catch (SQLException e) {
            Logger.logError("Não foi possível listar os IPs:", e.getMessage(), e);
			throw new RuntimeException("Erro ao listar os IPs da rede com o id = "+ rede.getIdRede() +": ", e);
        }
    }

    public Boolean existe(Ipv4 ipv4, Rede rede) {
        Boolean existe = false;
        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM relRedeIpv4 WHERE fkRede = ? AND fkIpv4 = ?");

            preparedStatement.setInt(1, rede.getIdRede());
            preparedStatement.setInt(2, ipv4.getIdIpv4());

            ResultSet resultSet = preparedStatement.executeQuery();
            
            existe = resultSet.next();
        } catch (SQLException e) {
            Logger.logError("Não foi possível listar as informações do ipv4: ", e.getMessage(), e);
			throw new RuntimeException("Erro ao listar as informações do ipv4 com o numero ip = "+ ipv4.getNumeroIp() +": ", e);
        }
        return existe;
    }
}
