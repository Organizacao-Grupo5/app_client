package dao.componente;

import app.integration.SlackIntegration;
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
        int idRegistroAlertas = 0;
        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO registroAlerta (horario, fkAlerta, fkCaptura) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(2, idAlerta);
            preparedStatement.setInt(3, idCaptura);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idRegistroAlertas = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao salvar seus registros SQLServer:: ", e.getMessage(), e);
        }

        try (Connection connection = MySQLConnection.ConnectionMySql()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO registroAlerta (idRegistroAlertas, horario, fkAlerta, fkCaptura) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, idRegistroAlertas);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setInt(3, idAlerta);
            preparedStatement.setInt(4, idCaptura);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao salvar seus registros no MySQL:: ", e.getMessage(), e);
        }

        return idRegistroAlertas;

    }

    public static void verificarUsoComponentes(int idCaptura) throws SQLException, IOException {
        Logger.logInfo("Verificando uso dos componentes...");

        try (Connection connection = MySQLConnection.ConnectionSqlServer()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT numeroIp IpMaquina, componente c, minimoParaSerMedio minMedio, minimoParaSerRuim minRuim, dadoCaptura dado FROM maquina maq JOIN ipv4 on idMaquina = ipv4.fkMaquina JOIN componente comp ON idMaquina = comp.fkMaquina JOIN configuracao conf ON fkComponente = idComponente JOIN captura cap ON idComponente = cap.fkComponente WHERE idCaptura = ? ;");

            preparedStatement.setInt(1, idCaptura);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    String componente = resultSet.getString("c");
                    double uso = resultSet.getDouble("dado");
                    float medioMinimo = resultSet.getFloat("minMedio");
                    float ruimMinimo = resultSet.getFloat("minRuim");
                    String ip = resultSet.getString("IpMaquina");

                    // ALERTAS PARA BATERIA!
                    if(componente.equalsIgnoreCase("bateria")){
                        if(uso <= 15){
                            String mensagem = """
                                URGÊNCIA: A máquina de IP: %s está com %.1f% de bateria""".
                                    formatted(ip, uso);
                             SlackIntegration.enviarMensagemParaSlack(mensagem);
                        } else if(uso <= 30){
                            String mensagem = """
                                Aviso: A máquina de IP: %s está com %.1f% de bateria""".
                                    formatted(ip, uso);
                             SlackIntegration.enviarMensagemParaSlack(mensagem);
                        }
                    }

                    // ALERTAS PARA RAM!
                    else if(componente.equalsIgnoreCase("memoriaram")){
                        if(uso >= 7){
                            String mensagem = """
                            URGÊNCIA: O componente %s da máquina de IP: %s está em condição ruim, com uso de %.1f GB""".
                                    formatted(componente, ip, uso);
                            SlackIntegration.enviarMensagemParaSlack(mensagem);

                        } else if(uso >= 6 ){
                            String mensagem = """
                                Aviso: O componente %s da máquina de IP: %s está em condição média, com uso de %.1f GB""".
                                    formatted(componente, ip, uso);
                            SlackIntegration.enviarMensagemParaSlack(mensagem);
                        }
                    }

                    // ALERTAS PARA CPU!
                    if (componente.equalsIgnoreCase("cpu")) {
                        if (uso >= 90) {
                            String mensagem = """
            URGÊNCIA: A CPU da máquina de IP: %s está com temperatura crítica de %.1f°C""".
                                    formatted(ip, uso);
                            SlackIntegration.enviarMensagemParaSlack(mensagem);
                        } else if (uso >= 75) {
                            String mensagem = """
            Aviso: A CPU da máquina de IP: %s está com temperatura alta de %.1f°C""".
                                    formatted(ip, uso);
                            SlackIntegration.enviarMensagemParaSlack(mensagem);
                        }
                    }

                    // ALERTAS PARA GPU!
                    else if (componente.equalsIgnoreCase("gpu")) {
                        if (uso >= 85) {
                            String mensagem = """
            URGÊNCIA: A GPU da máquina de IP: %s está com temperatura crítica de %.1f°C""".
                                    formatted(ip, uso);
                            SlackIntegration.enviarMensagemParaSlack(mensagem);
                        } else if (uso >= 75) {
                            String mensagem = """
            Aviso: A GPU da máquina de IP: %s está com temperatura alta de %.1f°C""".
                                    formatted(ip, uso);
                            SlackIntegration.enviarMensagemParaSlack(mensagem);
                        }
                    }


                    // ALERTA - RUIM!!!
                    else if (uso >= ruimMinimo) {
                        String mensagem = "URGÊNCIA: O componente %s da máquina de IP: %s está em condição ruim, com uso de %.1f".formatted(componente, ip, uso);
                         SlackIntegration.enviarMensagemParaSlack(mensagem);

                    }
                    // ALERTA - MÉDIO!!!
                    else if (uso >= medioMinimo) {
                        String mensagem = "Aviso: O componente %s da máquina de IP: %s está em condição média, com uso de %.1f".formatted(componente, ip, uso);
                        SlackIntegration.enviarMensagemParaSlack(mensagem);
                    }


                }
            }
        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao verificar o uso dos componentes", e.getMessage(), e);
        }
    }
}
