package dao.componente;

import model.Componentes.*;
import model.Maquina;
import util.database.MySQLConnection;
import util.logs.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComponenteDAO {
    public Boolean getComponente(Maquina maquina) throws SQLException {
        Boolean temComponente = false;
        try (Connection connection = MySQLConnection.ConBD()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM componente JOIN maquina ON componente.fkMaquina = maquina.idMaquina WHERE idMaquina = ?");
            preparedStatement.setInt(1, maquina.getIdMaquina());
            try(ResultSet resultSet = preparedStatement.executeQuery()){ // Corrigido para executeQuery()
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
            hdd.setIdHDD(idComponente);
            hdd.setModelo(modelo);
            maquina.getComponentes().add(hdd);
        }else if (componente.equals("ram")){
            MemoriaRam ram = new MemoriaRam();
            ram.setIdMemoriaRAM(idComponente);
            maquina.getComponentes().add(ram);
        }else if (componente.equals("gpu")){
            GPU gpu = new GPU();
            gpu.setIdGpu(idComponente);
            gpu.setNome(modelo);
            gpu.setFabricante(fabricante);
            maquina.getComponentes().add(gpu);
        }else if (componente.equals("cpu")){
            CPU cpu = new CPU();
            cpu.setIdCpu(idComponente);
            cpu.setNome(modelo);
            cpu.setFabricante(fabricante);
            maquina.getComponentes().add(cpu);
        }else if (componente.equals("volume")){
            Volume volume = new Volume();
            volume.setIdVolume(idComponente);
            volume.setNome(modelo);
            maquina.getComponentes().add(volume);
        }else if (componente.equals("bateria")){
            Bateria bateria = new Bateria();
            bateria.setIdBateria(idComponente);
            bateria.setNome(modelo);
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
                """.formatted(componente, idComponente, modelo, fabricante, preferenciaAlerta));
    }
}
