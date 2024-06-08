package dao.componente;

import util.database.MySQLConnection;
import util.logs.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.time.LocalDateTime;

public class RegistroAlertasDAO {

    public Integer gerarRegistro(Integer idCaptura, Integer idAlerta) {
        try (Connection connection = MySQLConnection.ConBD()) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO registroAlerta (horario, fkAlerta, fkCaptura) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, idAlerta);
            preparedStatement.setInt(3, idCaptura);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idRegistroAlertas = generatedKeys.getInt(1);
                        return idRegistroAlertas;
                    }
                }
            }
        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao salvar seus registros", e.getMessage(), e);
        }
        return 0;
    }

    public static void verificarUsoComponentes(int idCaptura) throws SQLException, IOException {
        Logger.logInfo("Verificando uso dos componentes...");

        try (Connection connection = MySQLConnection.ConBD()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT numeroIp IpMaquina, componente c, minimoParaSerMedio minMedio, " +
                            "minimoParaSerRuim minRuim," +
                            "                            dadoCaptura dado FROM maquina maq" +
                            "                            JOIN ipv4 on idMaquina = ipv4.fkMaquina" +
                            "                            JOIN componente comp ON idMaquina = " +
                            "comp.fkMaquina" +
                            "                            JOIN configuracao conf ON fkComponente = " +
                            "idComponente" +
                            "                            JOIN captura cap ON idComponente = " +
                            "cap.fkComponente" +
                            "                                             WHERE idCaptura = ? ;");

            preparedStatement.setInt(1, idCaptura);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    String componente = resultSet.getString("c");
                    double uso = resultSet.getDouble("dado");
                    float medioMinimo = resultSet.getFloat("minMedio");
                    float ruimMinimo = resultSet.getFloat("minRuim");
                    String ip = resultSet.getString("IpMaquina");

                    // ALERTA - RUIM!!!
                    if (uso >= ruimMinimo) {
                        String mensagem = """
                                URGÊNCIA: O componente %s da máquina de IP: %s está em condição ruim, com uso de %.2f""".
                                formatted(componente, ip, uso);
//                        SlackIntegration.enviarMensagemParaSlack(mensagem);

                    }
                    // ALERTA - MÉDIO!!!
                    else if (uso >= medioMinimo) {
                        String mensagem = "Aviso: O componente " + componente +
                                " da máquina de IP" + ip + " está em condição média, com uso de "
                                + uso;
//                        SlackIntegration.enviarMensagemParaSlack(mensagem);
                    }


                }
            }
        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao verificar o uso dos componentes",
                    e.getMessage(), e);
        }
    }
}
