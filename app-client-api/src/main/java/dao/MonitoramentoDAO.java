package dao;

import model.*;
import util.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MonitoramentoDAO {
    public Optional<Maquina> findMaquinaByIdUsuario(int idUser) {
        try (Connection conexao = Connector.ConBD();
             PreparedStatement preparedStatement = conexao.prepareStatement("SELECT * FROM Maquina JOIN Usuario ON idUsuario = fkUsuario WHERE idUsuario = ?")) {

            preparedStatement.setInt(1, idUser);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(createMaquina(resultSet));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar a máquina por usuário", e);
        }

        return Optional.empty();
    }

    public Maquina createMaquina(ResultSet resultSet) throws SQLException {
        Maquina maquina = new Maquina();
        maquina.setIdMaquina(resultSet.getInt("idMaquina"));
        maquina.setIpv4(resultSet.getString("IPV4"));
        return maquina;
    }

    public void salvarComponenteHDD(Integer idMaquina, HDD hdd) {
        String sqlSelect = "SELECT idHDD FROM HDD WHERE idHDD = ?";
        String sqlUpdate = "UPDATE HDD SET CapacidadeTotal = ?, NumeroParticoes = ?, StatusSaude = ? WHERE idHDD = ?";
        String sqlInsert = "INSERT INTO HDD (idHDD, CapacidadeTotal, NumeroParticoes, StatusSaude, fkMaquina) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, hdd.getIdHDD());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setDouble(1, hdd.getCapacidadeTotal());
                updateStatement.setInt(2, hdd.getNumeroParticoes());
                updateStatement.setString(3, hdd.getStatusSaude());
                updateStatement.setInt(4, hdd.getIdHDD());

                updateStatement.executeUpdate();
            } else {
                insertStatement.setInt(1, hdd.getIdHDD());
                insertStatement.setDouble(2, hdd.getCapacidadeTotal());
                insertStatement.setInt(3, hdd.getNumeroParticoes());
                insertStatement.setString(4, hdd.getStatusSaude());
                insertStatement.setInt(5, idMaquina);

                insertStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void salvarComponenteGPU(Integer idMaquina, GPU gpu) {
        String sqlSelect = "SELECT idGPU FROM GPU WHERE idGPU = ?";
        String sqlUpdate = "UPDATE GPU SET Modelo = ?, Memoria = ?, Utilizacao = ?, VersaoDriver = ? WHERE idGPU = ?";
        String sqlInsert = "INSERT INTO GPU (idGPU, Modelo, Memoria, Utilizacao, VersaoDriver, fkMaquina) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, gpu.getIdGPU());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setString(1, gpu.getModelo());
                updateStatement.setDouble(2, gpu.getMemoria());
                updateStatement.setDouble(3, gpu.getUtilizacao());
                updateStatement.setString(4, gpu.getVersaoDriver());
                updateStatement.setInt(5, gpu.getIdGPU());

                updateStatement.executeUpdate();
            } else {
                insertStatement.setInt(1, gpu.getIdGPU());
                insertStatement.setString(2, gpu.getModelo());
                insertStatement.setDouble(3, gpu.getMemoria());
                insertStatement.setDouble(4, gpu.getUtilizacao());
                insertStatement.setString(5, gpu.getVersaoDriver());
                insertStatement.setInt(6, idMaquina);

                insertStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void salvarComponenteMemoriaRAM(Integer idMaquina, MemoriaRam memoriaRAM) {
        String sqlSelect = "SELECT idMemória_RAM FROM Memoria_RAM WHERE idMemória_RAM = ?";
        String sqlUpdate = "UPDATE Memoria_RAM SET CapacidadeTotal = ?, NumeroModulo = ?, PorcentagemUtilizada = ? WHERE idMemoria_RAM = ?";
        String sqlInsert = "INSERT INTO Memoria_RAM (idMemoria_RAM, CapacidadeTotal, NumeroModulo, PorcentagemUtilizada, fkMaquina) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, memoriaRAM.getIdMemoriaRAM());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setDouble(1, memoriaRAM.getCapacidadeTotal());
                updateStatement.setInt(2, memoriaRAM.getNumeroModulo());
                updateStatement.setDouble(3, memoriaRAM.getPorcentagemUtilizada());
                updateStatement.setInt(4, memoriaRAM.getIdMemoriaRAM());

                updateStatement.executeUpdate();
            } else {
                insertStatement.setInt(1, memoriaRAM.getIdMemoriaRAM());
                insertStatement.setDouble(2, memoriaRAM.getCapacidadeTotal());
                insertStatement.setInt(3, memoriaRAM.getNumeroModulo());
                insertStatement.setDouble(4, memoriaRAM.getPorcentagemUtilizada());
                insertStatement.setInt(5, idMaquina);

                insertStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void salvarComponenteApp(Integer idMaquina, APP app) {
        String sqlSelect = "SELECT idApp FROM App WHERE idApp = ?";
        String sqlUpdate = "UPDATE App SET nomeApp = ?, DtInstalacao = ?, UltimaDtInstalacao = ?, TamanhoAplicativo = ? WHERE idApp = ?";
        String sqlInsert = "INSERT INTO App (idApp, nomeApp, DtInstalacao, UltimaDtInstalacao, TamanhoAplicativo, fkMaquina) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, app.getIdApp());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setString(1, app.getNomeApp());
                updateStatement.setDate(2, app.getDtInstalacao());
                updateStatement.setDate(3, app.getUltimaDtInstalacao());
                updateStatement.setString(4, app.getTamanhoAplicativo());
                updateStatement.setInt(5, app.getIdApp());

                updateStatement.executeUpdate();
            } else {
                insertStatement.setInt(1, app.getIdApp());
                insertStatement.setString(2, app.getNomeApp());
                insertStatement.setDate(3, app.getDtInstalacao());
                insertStatement.setDate(4, app.getUltimaDtInstalacao());
                insertStatement.setString(5, app.getTamanhoAplicativo());
                insertStatement.setInt(6, idMaquina);

                insertStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void salvarComponenteConexaoUSB(Integer idMaquina, ConexaoUSB conexaoUSB) {
        String sqlSelect = "SELECT idConexao_USB FROM Conexao_USB WHERE idConexao_USB = ?";
        String sqlUpdate = "UPDATE Conexao_USB SET TotalPortas = ?, TipoConector = ?, DeteccaoDispositivo = ?, EnergiaPorta = ?, HubsConectados = ?, DispositivoConectado = ? WHERE idConexao_USB = ?";
        String sqlInsert = "INSERT INTO Conexao_USB (idConexao_USB, TotalPortas, TipoConector, DetecçãoDispositivo, EnergiaPorta, HubsConectados, DispositivoConectado, fkMaquina) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, conexaoUSB.getIdConexaoUSB());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setInt(1, conexaoUSB.getTotalPortas());
                updateStatement.setString(2, conexaoUSB.getTipoConector());
                updateStatement.setString(3, conexaoUSB.getDeteccaoDispositivo());
                updateStatement.setString(4, conexaoUSB.getEnergiaPorta());
                updateStatement.setString(5, conexaoUSB.getHubsConectados());
                updateStatement.setString(6, conexaoUSB.getDispositivoConectado());
                updateStatement.setInt(7, conexaoUSB.getIdConexaoUSB());

                updateStatement.executeUpdate();
            } else {
                insertStatement.setInt(1, conexaoUSB.getIdConexaoUSB());
                insertStatement.setInt(2, conexaoUSB.getTotalPortas());
                insertStatement.setString(3, conexaoUSB.getTipoConector());
                insertStatement.setString(4, conexaoUSB.getDeteccaoDispositivo());
                insertStatement.setString(5, conexaoUSB.getEnergiaPorta());
                insertStatement.setString(6, conexaoUSB.getHubsConectados());
                insertStatement.setString(7, conexaoUSB.getDispositivoConectado());
                insertStatement.setInt(8, idMaquina);

                insertStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
