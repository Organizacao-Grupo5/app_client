package service;

import app.system.SystemMonitor;
import dao.componente.APPDAO;
import model.Maquina;
import model.componentes.APP;
import util.logs.Logger;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceAPP {
    private APPDAO appdao = new APPDAO();
    private SystemMonitor systemMonitor = new SystemMonitor();


    public void listarApps(Maquina maquina) throws IOException {
        List<APP> apps = systemMonitor.monitorarDisplay();
        try{
            appdao.gravaApps(apps, maquina);
        }catch(Exception e){
            Logger.logError("Erro ao inserir janela:\n", e.getMessage(), e);
        }
    }
}
