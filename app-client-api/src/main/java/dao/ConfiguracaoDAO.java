package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import model.Configuracao;
import model.Maquina;
import model.Usuario;
import model.componentes.Componente;
import util.database.MySQLConnection;
import util.logs.LogGenerator;
import util.logs.Logger;

public class ConfiguracaoDAO {
    
    public void inserirConfiguracao(Componente componente, Maquina maquina, Usuario usuario) throws SQLException {
        try (Connection connection = MySQLConnection.ConBD()) {

            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO configuracao (minimoParaSerMedio, minimoParaSerRuim, dataModificacao, fkComponente, fkMaquina, fkUsuario) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setDouble(1, Types.FLOAT);
            preparedStatement.setDouble(2, Types.FLOAT);
            preparedStatement.setNull(3, Types.TIMESTAMP);
            preparedStatement.setInt(4, componente.getIdComponente());
            preparedStatement.setInt(4, maquina.getIdMaquina());
            preparedStatement.setInt(4, usuario.getIdUsuario());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar o componente, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idConfiguracao = generatedKeys.getInt(1);
                    criarConfiguracao(idConfiguracao);
                } else {
                    throw new SQLException("Falha ao obter o ID da Configuração criado.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } 
    } 

    public Boolean selecionarConfiguracao(Componente componente, Maquina maquina, Usuario usuario) {
        try (Connection connection = MySQLConnection.ConBD()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM configuracao WHERE fkComponente = ? AND fkMaquina = ? AND fkUsuario = ?");
			preparedStatement.setInt(1, componente.getIdComponente());
			preparedStatement.setInt(2, maquina.getIdMaquina());
			preparedStatement.setInt(3, usuario.getIdUsuario());

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
                    criarConfiguracao(resultSet);
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }

    public Configuracao criarConfiguracao(ResultSet resultSet) {
        Configuracao configuracao = new Configuracao();
        configuracao.setIdConfig(resultSet.getInt(1));
        configuracao.setMinimoParaSerMedio(resultSet.getFloat(2));
        configuracao.setMinimoParaSerRuim(idConfiguracao);
        configuracao.setFkComponente(idConfiguracao);
        configuracao.setDataModificacao(idConfiguracao);
    }
}