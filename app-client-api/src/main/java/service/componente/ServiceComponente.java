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

    public void obterComponentes(Maquina maquina){
        try{
            if(componenteDAO.getComponente(maquina)){
                Logger.logInfo("Sua máquina contém componentes registrados");
            } else{
                Logger.logInfo("Sua máquina não contém componentes registrados iremos registrar seus componentes");

                List<Componente> listaComponente = new ArrayList<>();

                listaComponente.add(systemMonitor.capturarInformacoesPlacaMae());

                listaComponente.add(systemMonitor.monitorarSistemaOperacional());

                listaComponente.add(systemMonitor.monitorarCPU());

                listaComponente.add(systemMonitor.monitorarRAM());

                systemMonitor.monitorarGPU().forEach(gpu -> {
                    listaComponente.add(gpu);
                });
                systemMonitor.monitorarBateria().forEach(bateria -> {
                    listaComponente.add(bateria);
                });
                systemMonitor.monitorarUSB().forEach(usb -> {
                    listaComponente.add(usb);
                });
                systemMonitor.monitorarDisplay().forEach(app -> {
                    listaComponente.add(app);
                });
                systemMonitor.monitorarHDD().forEach(hdd -> {
                    listaComponente.add(hdd);
                });
                systemMonitor.monitorarVolumeLogico().forEach(volume -> {
                    listaComponente.add(volume);
                });

                listaComponente.forEach(componente -> {
                    try {
                        componenteDAO.salvarComponente(maquina, componente);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (Exception e){
            Logger.logError("Ocorreu um erro ao obter seus componentes:", e.getMessage(), e);
        }
    }
}
