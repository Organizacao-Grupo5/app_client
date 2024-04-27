package dao.componente;

import model.Componentes.*;
import model.Maquina;
import util.database.MySQLConnection;
import util.logs.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ComponenteDAO {
    public Boolean getComponente(Maquina maquina) throws SQLException {
        Boolean temComponente = false;
        try (Connection connection = MySQLConnection.ConBD()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM componente JOIN maquina ON componente.fkMaquina = maquina.idMaquina WHERE idMaquina = ?");
            preparedStatement.setInt(1, maquina.getIdMaquina());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    verificaTipoComponente(maquina, resultSet);
                    temComponente = true;
                }
            }
        } catch (SQLException e){
            Logger.logError("Ocorreu um erro ao buscar seus componentes", e.getMessage(), e);
        }
        return temComponente;
    }

    private void verificaTipoComponente(Maquina maquina, ResultSet resultSet) throws SQLException {
            Logger.logInfo("\nIdentificamos seu COMPONENTE:");
            Integer idComponente = resultSet.getInt("idComponente");
            String componente = resultSet.getString("componente");
            String modelo = resultSet.getString("modelo");
            String fabricante = resultSet.getString("fabricante");
            String preferenciaAlerta = resultSet.getString("preferenciaAlerta");

        if (componente.equals("hdd")){
            HDD hdd = new HDD();
            hdd.setIdComponente(idComponente);
            hdd.setComponente(componente);
            hdd.setModelo(modelo);
            maquina.getComponentes().add(hdd);
        }else if (componente.equals("ram")){
            MemoriaRam ram = new MemoriaRam();
            ram.setIdComponente(idComponente);
            ram.setComponente(componente);
            maquina.getComponentes().add(ram);
        }else if (componente.equals("gpu")){
            GPU gpu = new GPU();
            gpu.setIdComponente(idComponente);
            gpu.setModelo(modelo);
            gpu.setComponente(componente);
            gpu.setFabricante(fabricante);
            maquina.getComponentes().add(gpu);
        }else if (componente.equals("cpu")){
            CPU cpu = new CPU();
            cpu.setIdComponente(idComponente);
            cpu.setModelo(modelo);
            cpu.setComponente(componente);
            cpu.setFabricante(fabricante);
            maquina.getComponentes().add(cpu);
        }else if (componente.equals("volume")){
            Volume volume = new Volume();
            volume.setIdComponente(idComponente);
            volume.setComponente(componente);
            volume.setModelo(modelo);
            maquina.getComponentes().add(volume);
        }else if (componente.equals("bateria")){
            Bateria bateria = new Bateria();
            bateria.setIdComponente(idComponente);
            bateria.setModelo(modelo);
            bateria.setComponente(componente);
            bateria.setFabricante(fabricante);
            maquina.getComponentes().add(bateria);
        }
        Logger.logInfo("""
                    
                    +===========================================
                    |COMPONENTE: %s
                    |ID: %s
                    |MODELO: %s
                    |FABRICANTE: %s
                    |PREFERÊNCIA ALERTA: %s
                    +============================================
                """.formatted(componente, idComponente, modelo, fabricante, (Optional.ofNullable(preferenciaAlerta).orElse("N/A")).toString()));
    }

    public void salvarComponente(Maquina maquina, Componente componente) throws SQLException {
        Logger.logInfo("""
                Salvando componente:
                    +=============================
                    | %s
                    | %s
                    | %s
                    | %s
                    +=============================
                """.formatted(
                Optional.ofNullable(componente.getComponente()).orElse("N/A"),
                Optional.ofNullable(componente.getModelo()).orElse("N/A"),
                Optional.ofNullable(componente.getFabricante()).orElse("N/A"),
                Optional.ofNullable(componente.getPreferenciaAlerta()).map(Object::toString).orElse("N/A")
        ));
        try (Connection connection = MySQLConnection.ConBD()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO componente (componente, modelo, fabricante, preferenciaAlerta, fkMaquina, fkUsuario) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, Optional.ofNullable(componente.getModelo()).orElse("N/A"));
            preparedStatement.setString(2, Optional.ofNullable(componente.getModelo()).orElse("N/A"));
            preparedStatement.setString(3, Optional.ofNullable(componente.getFabricante()).orElse("N/A"));
            preparedStatement.setDouble(4, Optional.ofNullable(componente.getPreferenciaAlerta()).orElse(0.0));
            preparedStatement.setInt(5, maquina.getIdMaquina());
            preparedStatement.setInt(6, maquina.getUsuario().getIdUsuario());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao salvar o componente, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idComponente = generatedKeys.getInt(1);
                    componente.setIdComponente(idComponente);
                    Logger.logInfo("ID do componente criado: " + idComponente);
                } else {
                    throw new SQLException("Falha ao obter o ID do componente criado.");
                }
            }
        } catch (SQLException e){
            Logger.logError("Ocorreu um erro ao salvar seus componentes", e.getMessage(), e);
        }
    }
}
