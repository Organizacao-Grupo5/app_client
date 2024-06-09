package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Ipv4;
import model.Maquina;
import util.database.MySQLConnection;
import util.logs.Logger;

public class Ipv4DAO {

    public Ipv4 insert(Ipv4 ipv4) {
        try (Connection conexao = MySQLConnection.ConnectionMySql()) {
            PreparedStatement preparedStatement = conexao.prepareStatement("INSERT INTO ipv4 (numeroIP, nomeLocal, fkMaquina) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, ipv4.getNumeroIp());
            preparedStatement.setString(2, ipv4.getNomeLocal());
            preparedStatement.setInt(3, ipv4.getFkMaquina());
        
            int affectedRows = preparedStatement.executeUpdate();
            
            if (affectedRows != 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idIpv4 = generatedKeys.getInt(1);
                        ipv4.setIdIpv4(idIpv4);
                    } else {
						throw new SQLException("Falha ao obter o ID do IPV4 criado.");
                    }
                }
            }
            
        } catch (SQLException e) {
            Logger.logError("Não foi possível inserir valores à entidade 'ipv4': ", e.getMessage(), e);
        }
        return ipv4;
    } 

    public List<Object> select(Ipv4 ipv4) {
        List<Object> atributosIpv4 = new ArrayList<>();
        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ipv4 WHERE numeroIP = ? AND nomeLocal = ?");

            preparedStatement.setString(1, ipv4.getNumeroIp());
            preparedStatement.setString(2, ipv4.getNomeLocal());
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    atributosIpv4.add(resultSet.getInt("idIpv4"));
                    atributosIpv4.add(resultSet.getString("numeroIP"));
                    atributosIpv4.add(resultSet.getString("nomeLocal"));
                    atributosIpv4.add(resultSet.getInt("fkMaquina"));
                }
            }
            
        } catch (SQLException e) {
            Logger.logError("Não foi possível listar a ipv4:", e.getMessage(), e);
        }
        return atributosIpv4;
    }

    public Boolean existe(Ipv4 ipv4) {
        Boolean existe = false;
        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ipv4 WHERE numeroIP = ? AND nomeLocal = ?");

            preparedStatement.setString(1, ipv4.getNumeroIp());
            preparedStatement.setString(2, ipv4.getNomeLocal());

            ResultSet resultSet = preparedStatement.executeQuery();
            
            existe = resultSet.next();
        } catch (SQLException e) {
            Logger.logError("Não foi possível listar as informações do ipv4: ", e.getMessage(), e);
        }
        return existe;
    }

    public void atribuirId(Ipv4 ipv4) throws SQLException {
        List<Object> ipv4Obj = this.select(ipv4);

        String idIpv4 = ipv4Obj.get(0).toString();

        ipv4.setIdIpv4(Integer.parseInt(idIpv4));
    }

    public Boolean existeIpv4(Maquina maquina, String ip) {
        Boolean existe = false;        
        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ipv4 WHERE numeroIP = ? AND fkMaquina = ?");

            preparedStatement.setString(1, ip);
            preparedStatement.setInt(2, maquina.getIdMaquina());

            ResultSet resultSet = preparedStatement.executeQuery();
            
            existe = resultSet.next();
            
        } catch (SQLException e) {
            Logger.logError("Não foi possível listar as informações do ipv4: ", e.getMessage(), e);
        }
        return existe;
    }

}
