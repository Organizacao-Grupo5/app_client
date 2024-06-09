package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import model.Configuracao;
import model.componentes.Componente;
import util.database.MySQLConnection;
import util.logs.Logger;

public class ConfiguracaoDAO {
    
    public Integer inserirConfiguracao(Integer idComponente) throws SQLException {
        try (Connection connection = MySQLConnection.ConnectionMySql()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO configuracao (dataModificacao, fkComponente) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, idComponente);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao salvar suas capturas", e.getMessage(), e);
        }

        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO configuracao (dataModificacao, fkComponente) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, idComponente);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar o configuração, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idConfiguracao = generatedKeys.getInt(1);
                    this.selecionarById(idConfiguracao);
                    return idConfiguracao;
                } else {
                    throw new SQLException("Falha ao obter o ID da Configuração criado.");
                }
            }

        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao salvar suas capturas", e.getMessage(), e);
        } 
        return null;
    } 

    public Optional<Configuracao> selecionarByComponente(Componente componente) {
        try (Connection connection = MySQLConnection.ConnectionMySql()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM configuracao WHERE fkComponente = ?");
            preparedStatement.setInt(1, componente.getIdComponente());

            preparedStatement.executeQuery();
        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao salvar suas capturas", e.getMessage(), e);
        }

        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM configuracao WHERE fkComponente = ?");
			preparedStatement.setInt(1, componente.getIdComponente());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
                    Configuracao configuracao = criarConfiguracao(resultSet);
                    return Optional.of(configuracao);
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return Optional.empty();
    }
    
    public Optional<Configuracao> selecionarById(Integer idConfiguracao) {
        try (Connection connection = MySQLConnection.ConnectionMySql()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM configuracao WHERE idConfig = ?");
            preparedStatement.setInt(1, idConfiguracao);

            preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM configuracao WHERE idConfig = ?");
            preparedStatement.setInt(1, idConfiguracao);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
                    Configuracao configuracao = this.criarConfiguracao(resultSet);
                    return Optional.of(configuracao);
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Boolean existeConfiguracao(Integer idComponente) {
        Boolean existe = false;

        try (Connection connection = MySQLConnection.ConnectionMySql()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM configuracao WHERE fkComponente = ?");
            preparedStatement.setInt(1, idComponente);

            preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM configuracao WHERE fkComponente = ?");
            preparedStatement.setInt(1, idComponente);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                existe = resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }
    
    private Configuracao criarConfiguracao(ResultSet resultSet) {
        Configuracao configuracao = new Configuracao();
        try {
            configuracao.setIdConfig(resultSet.getInt(1));
            configuracao.setMinimoParaSerMedio(resultSet.getFloat(2));
            configuracao.setMinimoParaSerRuim(resultSet.getFloat(3));
            configuracao.setDataModificacao(resultSet.getTimestamp(4));
            configuracao.setFkComponente(resultSet.getInt(5));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return configuracao;
    }

}