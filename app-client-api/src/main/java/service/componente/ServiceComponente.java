package service.componente;

import app.system.SystemMonitor;
import dao.componente.ComponenteDAO;
import model.Componentes.Componente;
import model.Maquina;
import util.logs.Logger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceComponente {
    private ComponenteDAO componenteDAO = new ComponenteDAO();
    private SystemMonitor systemMonitor = new SystemMonitor();

    public void obterComponentes(Maquina maquina) {
        try {
            List<Componente> componentesRegistrados = componenteDAO.getComponentes(maquina);
            List<Componente> listaComponente = new ArrayList<>();

            listaComponente.add(systemMonitor.capturarInformacoesPlacaMae());
            listaComponente.add(systemMonitor.monitorarSistemaOperacional());
            listaComponente.add(systemMonitor.monitorarCPU());
            listaComponente.add(systemMonitor.monitorarRAM());
            systemMonitor.monitorarGPU().forEach(gpu -> listaComponente.add(gpu));
            systemMonitor.monitorarBateria().forEach(bateria -> listaComponente.add(bateria));
            systemMonitor.monitorarHDD().forEach(hdd -> listaComponente.add(hdd));
            systemMonitor.monitorarVolumeLogico().forEach(volume -> listaComponente.add(volume));

            listaComponente.forEach(novoComponente -> {
                try {
                    boolean existe = false;
                    for (Componente registrado : componentesRegistrados) {
                        if (registrado.getModelo().equals(novoComponente.getModelo()) &&
                                registrado.getFabricante().equals(novoComponente.getFabricante()) &&
                                registrado.getComponente().equals(novoComponente.getComponente())) {
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        componenteDAO.salvarComponente(maquina, novoComponente);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            if (!componentesRegistrados.isEmpty()) {
                Logger.logInfo("Sua máquina contém componentes registrados");
            } else {
                Logger.logInfo("Todos os componentes da sua máquina foram registrados");
            }

        } catch (Exception e) {
            Logger.logError("Ocorreu um erro ao obter seus componentes:", e.getMessage(), e);
        }
    }

}
