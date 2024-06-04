package dao.componente;

import model.Maquina;
import util.database.MySQLConnection;
import util.logs.LogGenerator;
import util.logs.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistroAlertasDAO {

    public void verificarUsoComponentes(Maquina maquina) throws SQLException, IOException {
        Logger.logInfo("Verificando uso dos componentes...");
        LogGenerator.logInfo("Verificando uso dos componentes...", LogGenerator.LogType.INFO);

        try (Connection connection = MySQLConnection.ConBD()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT idComponente, componente FROM componente WHERE fkMaquina = ?");
            preparedStatement.setInt(1, maquina.getIdMaquina());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int idComponente = resultSet.getInt("idComponente");
                    String componente = resultSet.getString("componente");

                    PreparedStatement capturaStatement = connection.prepareStatement(
                            "SELECT dadoCaptura FROM captura WHERE fkComponente = ?" +
                                    " ORDER BY dataCaptura DESC LIMIT 1");
                    capturaStatement.setInt(1, idComponente);

                    try (ResultSet capturaResultSet = capturaStatement.executeQuery()) {
                        if (capturaResultSet.next()) {
                            double uso = Double.parseDouble(capturaResultSet.getString(
                                    "dadoCaptura"));

                            if ("memoriaram".equalsIgnoreCase(componente) && uso > 80) {
                                String mensagem = "Urgência: Uso de RAM acima de 80%! Uso atual: "
                                        + uso;
//                                SlackIntegration.enviarMensagemParaSlack(mensagem);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao verificar o uso dos componentes",
                    e.getMessage(), e);
            LogGenerator.logError("Ocorreu um erro ao verificar o uso dos componentes",
                    e.getMessage(), e);
        }
    }
}