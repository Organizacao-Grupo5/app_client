package dao;

import model.*;
import util.Connector;

import java.sql.*;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.*;

public class MonitoramentoDAO {
    DecimalFormat df = new DecimalFormat("###.##");

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

    public void salvarComponenteCPU(Integer idMaquina, CPU cpu) {
        String sqlSelect = "SELECT CPU.idCPU FROM CPU INNER JOIN Maquina ON CPU.fkMaquina = Maquina.idMaquina WHERE CPU.idCPU = ? AND Maquina.idMaquina = ?";
        String sqlUpdate = "UPDATE CPU SET Modelo = ?, Numero_de_Serie = ?, Fabricante = ?, Arquitetura = ?, Cache = ?, Velocidade_CPU = ?, Temperatura_CPU = ?  WHERE idCPU = ?";
        String sqlInsert = "INSERT INTO CPU (Modelo, Numero_de_Serie, Fabricante, Arquitetura, Cache, fkMaquina, Velocidade_CPU, Temperatura_CPU) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, cpu.getIdCPU());
            selectStatement.setInt(2, idMaquina);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setString(1, cpu.getModelo());
                updateStatement.setString(2, cpu.getNumeroSerie());
                updateStatement.setString(3, cpu.getFabricante());
                updateStatement.setString(4, cpu.getArquitetura());
                updateStatement.setString(5, cpu.getCache());
                updateStatement.setDouble(6, Double.parseDouble(df.format(cpu.getVelocidadeComponente())));
                updateStatement.setDouble(7, Double.parseDouble(df.format(cpu.getTemperaturaComponente())));
                updateStatement.setInt(8, cpu.getIdCPU());
                updateStatement.executeUpdate();
            } else {
                insertStatement.setString(1, cpu.getModelo());
                insertStatement.setString(2, cpu.getNumeroSerie());
                insertStatement.setString(3, cpu.getFabricante());
                insertStatement.setString(4, cpu.getArquitetura());
                insertStatement.setString(5, cpu.getCache());
                insertStatement.setInt(6, idMaquina);
                insertStatement.setDouble(7, Double.parseDouble(df.format(cpu.getVelocidadeComponente())));
                insertStatement.setDouble(8, Double.parseDouble(df.format(cpu.getTemperaturaComponente())));
                insertStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void salvarComponenteHDD(Integer idMaquina, HDD hdd) {
        String sqlSelect = "SELECT HDD.idHDD FROM HDD INNER JOIN Maquina ON HDD.fkMaquina = Maquina.idMaquina WHERE HDD.idHDD = ? AND Maquina.idMaquina = ?";
        String sqlUpdate = "UPDATE HDD SET CapacidadeTotal = ?, NumeroParticoes = ?, StatusSaude = ? WHERE idHDD = ?";
        String sqlInsert = "INSERT INTO HDD (idHDD, CapacidadeTotal, NumeroParticoes, StatusSaude, fkMaquina) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, hdd.getIdHDD());
            selectStatement.setInt(2, idMaquina);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setDouble(1, hdd.getCapacidadeTotal());
                updateStatement.setInt(2, hdd.getNumeroParticoes());
                updateStatement.setString(3, hdd.getStatusSaude());
                updateStatement.setInt(4, hdd.getIdHDD());

                System.out.println("UPDATE realizado para HDD com id: " + hdd.getIdHDD());
                updateStatement.executeUpdate();
            } else {
                insertStatement.setInt(1, hdd.getIdHDD());
                insertStatement.setDouble(2, hdd.getCapacidadeTotal());
                insertStatement.setInt(3, hdd.getNumeroParticoes());
                insertStatement.setString(4, hdd.getStatusSaude());
                insertStatement.setInt(5, idMaquina);

                System.out.println("INSERT realizado para HDD com id: " + hdd.getIdHDD());
                insertStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void salvarComponenteGPU(Integer idMaquina, GPU gpu) {
        String sqlSelect = "SELECT GPU.idGPU FROM GPU INNER JOIN Maquina ON GPU.fkMaquina = Maquina.idMaquina WHERE GPU.idGPU = ? AND Maquina.idMaquina = ?";
        String sqlUpdate = "UPDATE GPU SET Modelo = ?, Memoria = ?, Utilizacao = ?, VersaoDriver = ? WHERE idGPU = ?";
        String sqlInsert = "INSERT INTO GPU (idGPU, Modelo, Memoria, Utilizacao, VersaoDriver, fkMaquina) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, gpu.getIdGPU());
            selectStatement.setInt(2, idMaquina);
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
        String sqlSelect = "SELECT Memoria_RAM.idMemoria_RAM FROM Memoria_RAM INNER JOIN Maquina ON Memoria_RAM.fkMaquina = Maquina.idMaquina WHERE Memoria_RAM.idMemoria_RAM = ? AND Maquina.idMaquina = ?";
        String sqlUpdate = "UPDATE Memoria_RAM SET CapacidadeTotal = ?, NumeroModulo = ?, PorcentagemUtilizada = ? WHERE idMemoria_RAM = ?";
        String sqlInsert = "INSERT INTO Memoria_RAM (idMemoria_RAM, CapacidadeTotal, NumeroModulo, PorcentagemUtilizada, fkMaquina) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, memoriaRAM.getIdMemoriaRAM());
            selectStatement.setInt(2, idMaquina);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setDouble(1, Double.parseDouble(df.format(memoriaRAM.getCapacidadeTotal()).replace(",", ".")));
                updateStatement.setInt(2, memoriaRAM.getNumeroModulo());
                updateStatement.setDouble(3, Double.parseDouble(df.format(memoriaRAM.getPorcentagemUtilizada()).replace(",", "."))); // Substituir ',' por '.'
                updateStatement.setInt(4, memoriaRAM.getIdMemoriaRAM());
                updateStatement.executeUpdate();
            } else {
                insertStatement.setInt(1, memoriaRAM.getIdMemoriaRAM());
                insertStatement.setDouble(2, Double.parseDouble(df.format(memoriaRAM.getCapacidadeTotal()).replace(",", ".")));
                insertStatement.setInt(3, memoriaRAM.getNumeroModulo());
                insertStatement.setDouble(4, Double.parseDouble(df.format(memoriaRAM.getPorcentagemUtilizada()).replace(",", "."))); // Substituir ',' por '.'
                insertStatement.setInt(5, idMaquina);

                insertStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void salvarComponenteApp(Integer idMaquina, APP app) {
        String sqlSelect = "SELECT App.idApp FROM App INNER JOIN Maquina ON App.fkMaquina = Maquina.idMaquina WHERE App.idApp = ? AND Maquina.idMaquina = ?";
        String sqlUpdate = "UPDATE App SET nomeApp = ?, DtInstalacao = ?, UltimaDtInstalacao = ?, TamanhoAplicativo = ? WHERE idApp = ?";
        String sqlInsert = "INSERT INTO App (idApp, nomeApp, DtInstalacao, UltimaDtInstalacao, TamanhoAplicativo, fkMaquina) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, app.getIdApp());
            selectStatement.setInt(2, idMaquina);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                updateStatement.setString(1, app.getNomeApp());
                updateStatement.setDate(2, (Date) app.getDtInstalacao());
                updateStatement.setDate(3, (Date) app.getUltimaDtInstalacao());
                updateStatement.setString(4, app.getTamanhoAplicativo());
                updateStatement.setInt(5, app.getIdApp());
                System.out.println("UPDATE realizado para App com id: " + app.getIdApp());

                updateStatement.executeUpdate();
            } else {
                insertStatement.setInt(1, app.getIdApp());
                insertStatement.setString(2, app.getNomeApp());
                insertStatement.setDate(3, (Date) app.getDtInstalacao());
                insertStatement.setDate(4, (Date) app.getUltimaDtInstalacao());
                insertStatement.setString(5, app.getTamanhoAplicativo());
                insertStatement.setInt(6, idMaquina);
                System.out.println("INSERT realizado para App com id: " + app.getIdApp());

                insertStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void salvarComponenteConexaoUSB(Integer idMaquina, ConexaoUSB conexaoUSB) {
        String sqlSelect = "SELECT Conexao_USB.idConexao_USB FROM Conexao_USB INNER JOIN Maquina ON Conexao_USB.fkMaquina = Maquina.idMaquina WHERE Conexao_USB.idConexao_USB = ? AND Maquina.idMaquina = ?";
        String sqlUpdate = "UPDATE Conexao_USB SET TotalPortas = ?, TipoConector = ?, DeteccaoDispositivo = ?, EnergiaPorta = ?, HubsConectados = ?, DispositivoConectado = ? WHERE idConexao_USB = ?";
        String sqlInsert = "INSERT INTO Conexao_USB (idConexao_USB, TotalPortas, TipoConector, DeteccaoDispositivo, EnergiaPorta, HubsConectados, DispositivoConectado, fkMaquina) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        System.out.println("Estou no dao: " + conexaoUSB.getIdConexaoUSB());
        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatement = conexao.prepareStatement(sqlSelect);
             PreparedStatement updateStatement = conexao.prepareStatement(sqlUpdate);
             PreparedStatement insertStatement = conexao.prepareStatement(sqlInsert)) {

            selectStatement.setInt(1, conexaoUSB.getIdConexaoUSB());
            selectStatement.setInt(2, idMaquina);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next() ) {
                updateStatement.setInt(1, conexaoUSB.getTotalPortas());
                updateStatement.setString(2, conexaoUSB.getTipoConector());
                updateStatement.setString(3, conexaoUSB.getDeteccaoDispositivo());
                updateStatement.setString(4, conexaoUSB.getEnergiaPorta());
                updateStatement.setString(5, conexaoUSB.getHubsConectados());
                updateStatement.setString(6, conexaoUSB.getDispositivoConectado());
                updateStatement.setInt(7, conexaoUSB.getIdConexaoUSB());
                System.out.println("UPDATE realizado para Conexão USB com id: " + conexaoUSB.getIdConexaoUSB());
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
                System.out.println("INSERT realizado para Conexão USB com id: " + conexaoUSB.getIdConexaoUSB());

                insertStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Map<String, Set<Integer>> coletaIDComponente(Map<String, Object> mapaComponente, Integer idMaquina) {
        Map<String, Set<Integer>> idsComponentes = new HashMap<>();

        idsComponentes.put("CPU", new HashSet<>());
        idsComponentes.put("GPU", new HashSet<>());
        idsComponentes.put("HDD", new HashSet<>());
        idsComponentes.put("RAM", new HashSet<>());
        idsComponentes.put("APP", new HashSet<>());
        idsComponentes.put("CONEXAO USB", new HashSet<>());

        String sqlSelectCPU = "SELECT CPU.idCPU FROM CPU INNER JOIN Maquina ON CPU.fkMaquina = Maquina.idMaquina WHERE Maquina.idMaquina = ?";
        String sqlSelectGPU = "SELECT GPU.idGPU FROM GPU INNER JOIN Maquina ON GPU.fkMaquina = Maquina.idMaquina WHERE Maquina.idMaquina = ?";
        String sqlSelectHDD = "SELECT HDD.idHDD FROM HDD INNER JOIN Maquina ON HDD.fkMaquina = Maquina.idMaquina WHERE Maquina.idMaquina = ?";
        String sqlSelectMemoriaRam = "SELECT Memoria_RAM.idMemoria_RAM FROM Memoria_RAM INNER JOIN Maquina ON Memoria_RAM.fkMaquina = Maquina.idMaquina WHERE Maquina.idMaquina = ?";
        String sqlSelectAPP = "SELECT App.idApp FROM App INNER JOIN Maquina ON App.fkMaquina = Maquina.idMaquina WHERE Maquina.idMaquina = ?";
        String sqlSelectConexaoUSB = "SELECT Conexao_USB.idConexao_USB FROM Conexao_USB INNER JOIN Maquina ON Conexao_USB.fkMaquina = Maquina.idMaquina WHERE Maquina.idMaquina = ?";

        try (Connection conexao = Connector.ConBD();
             PreparedStatement selectStatementCPU = conexao.prepareStatement(sqlSelectCPU);
             PreparedStatement selectStatementGPU = conexao.prepareStatement(sqlSelectGPU);
             PreparedStatement selectStatementHDD = conexao.prepareStatement(sqlSelectHDD);
             PreparedStatement selectStatementMemoriaRam = conexao.prepareStatement(sqlSelectMemoriaRam);
             PreparedStatement selectStatementAPP = conexao.prepareStatement(sqlSelectAPP);
             PreparedStatement selectStatementConexaoUSB = conexao.prepareStatement(sqlSelectConexaoUSB)) {

            selectStatementCPU.setInt(1, idMaquina);
            try (ResultSet resultSetCPU = selectStatementCPU.executeQuery()) {
                while (resultSetCPU.next()) {
                    idsComponentes.get("CPU").add(resultSetCPU.getInt("idCPU"));
                }
            }

            selectStatementGPU.setInt(1, idMaquina);
            try (ResultSet resultSetGPU = selectStatementGPU.executeQuery()) {
                while (resultSetGPU.next()) {
                    idsComponentes.get("GPU").add(resultSetGPU.getInt("idGPU"));
                }
            }

            selectStatementHDD.setInt(1, idMaquina);
            try (ResultSet resultSetHDD = selectStatementHDD.executeQuery()) {
                while (resultSetHDD.next()) {
                    idsComponentes.get("HDD").add(resultSetHDD.getInt("idHDD"));
                }
            }

            selectStatementMemoriaRam.setInt(1, idMaquina);
            try (ResultSet resultSetMemoriaRam = selectStatementMemoriaRam.executeQuery()) {
                while (resultSetMemoriaRam.next()) {
                    idsComponentes.get("RAM").add(resultSetMemoriaRam.getInt("idMemoria_RAM"));
                }
            }

            selectStatementAPP.setInt(1, idMaquina);
            try (ResultSet resultSetAPP = selectStatementAPP.executeQuery()) {
                while (resultSetAPP.next()) {
                    idsComponentes.get("APP").add(resultSetAPP.getInt("idApp"));
                }
            }

            selectStatementConexaoUSB.setInt(1, idMaquina);
            try (ResultSet resultSetConexaoUSB = selectStatementConexaoUSB.executeQuery()) {
                while (resultSetConexaoUSB.next()) {
                    idsComponentes.get("CONEXAO USB").add(resultSetConexaoUSB.getInt("idConexao_USB"));
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return idsComponentes;
    }

}
