package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Rede;
import util.database.MySQLConnection;
import util.logs.Logger;

public class RedeDAO {
     public Rede insert(Rede rede) throws SQLException {
        try (Connection conexao = MySQLConnection.ConnectionMySql()) {
            PreparedStatement preparedStatement = conexao.prepareStatement("INSERT INTO rede (nomeRede, interfaceRede, sinalRede, transmissaoRede, bssidRede) VALUES (?, ?, ?, ?, ?)");

            preparedStatement.setString(1, rede.getNomeRede());
            preparedStatement.setString(2, rede.getInterfaceRede());
            preparedStatement.setInt(3, rede.getSinalRede());
            preparedStatement.setDouble(4, rede.getTransmissaoRede());
            preparedStatement.setString(5, rede.getBssidRede());
        
            preparedStatement.executeUpdate();
            
            return rede;

        } catch (SQLException e) {
            Logger.logError("Não foi possível inserir valores à entidade 'rede': ", e.getMessage(), e);
			throw new RuntimeException("Erro ao inserir dados à tabela rede", e);
        }
    } 

    public List<Object> select(Rede rede) throws SQLException {
        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rede WHERE bssidRede = ?");

            preparedStatement.setString(1, rede.getBssidRede());
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Object> atributosRede = new ArrayList<>();
                while (resultSet.next()) {
                    atributosRede.add(resultSet.getInt("idRede"));
                    atributosRede.add(resultSet.getString("nomeRede"));
                    atributosRede.add(resultSet.getString("interfaceRede"));
                    atributosRede.add(resultSet.getInt("sinalRede"));
                    atributosRede.add(resultSet.getDouble("transmissaoRede"));
                    atributosRede.add(resultSet.getString("bssidRede"));
                }
                return atributosRede;
            }

        } catch (SQLException e) {
            Logger.logError("Não foi possível listar a rede:", e.getMessage(), e);
			throw new RuntimeException("Erro ao listar rede do com o id = "+ rede.getIdRede() +": ", e);
        }
    }

    public Boolean existe(Rede rede) {
        Boolean existe = false;
        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM rede WHERE bssidRede = ?");

            preparedStatement.setString(1, rede.getBssidRede());

            ResultSet resultSet = preparedStatement.executeQuery();
            
            existe = resultSet.next();
            
        } catch (SQLException e) {
            Logger.logError("Não foi possível listar as informações da rede: ", e.getMessage(), e);
			throw new RuntimeException("Erro ao listar as informações da rede com o BSSID = "+ rede.getBssidRede() +": ", e);
        }
        return existe;
    }

    public void atribuirId(Rede rede) throws SQLException {
        List<Object> redeObj = this.select(rede);

        String idRede = redeObj.get(0).toString();

        rede.setIdRede(Integer.parseInt(idRede));
    }
}
