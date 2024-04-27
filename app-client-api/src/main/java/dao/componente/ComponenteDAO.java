package dao.componente;

import model.Componentes.*;
import model.Componentes.Componente;
import model.Maquina;
import util.database.MySQLConnection;
import util.logs.Logger;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ComponenteDAO {
    private static final Map<String, Class<? extends Componente>> tipoComponentes = new HashMap<>();

    static {
        tipoComponentes.put("hdd", HDD.class);
        tipoComponentes.put("ram", MemoriaRam.class);
        tipoComponentes.put("gpu", GPU.class);
        tipoComponentes.put("cpu", CPU.class);
        tipoComponentes.put("volume", Volume.class);
        tipoComponentes.put("bateria", Bateria.class);
    }

    public Boolean getComponente(Maquina maquina) {
        boolean componentesEncontrados = false;

        String sql = "SELECT * FROM componente JOIN maquina ON componente.fkMaquina = maquina.idMaquina WHERE idMaquina = ?";

        try (Connection connection = MySQLConnection.ConBD();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, maquina.getIdMaquina());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    verificaTipoComponente(maquina, resultSet);
                    componentesEncontrados = true;
                }
            }
        } catch (SQLException e) {
            Logger.logError("Ocorreu um erro ao buscar seus componentes", e.getMessage(), e);
        }
        return componentesEncontrados;
    }

    public void verificaTipoComponente(Maquina maquina, ResultSet resultSet) throws SQLException {

        Logger.logInfo("\nIdentificamos seu COMPONENTE:");

        Integer idComponente = resultSet.getInt("idComponente");
        String componente = resultSet.getString("componente");
        String modelo = resultSet.getString("modelo");
        String fabricante = resultSet.getString("fabricante");
        String preferenciaAlerta = resultSet.getString("preferenciaAlerta");

        Class<? extends Componente> componenteClass = tipoComponentes.get(componente);
        if (componenteClass != null) {
            try {
                Constructor<? extends Componente> constructor = componenteClass.getConstructor();
                Componente comp = constructor.newInstance();
                comp.setId(idComponente);
                comp.setNome(modelo);
                if (comp instanceof GPU || comp instanceof CPU || comp instanceof Bateria) {
                    comp.setFabricante(fabricante);
                }
                maquina.getComponentes().add(comp);
            } catch (Exception e) {
                Logger.logError("Erro ao instanciar o componente", e.getMessage(), e);
            }
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
