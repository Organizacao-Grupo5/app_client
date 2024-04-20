package service;

import app.system.Components.HDDMonitoring;
import app.system.SystemMonitor;
import com.mysql.cj.util.StringUtils;
import exception.ExceptionMonitoring;
import model.*;
import service.serviceComponents.*;
import util.TablePrinter;
import util.logs.Logger;

import java.util.*;

public class ServiceMonitoring {

    protected TablePrinter tablePrinter = new TablePrinter();
    protected SystemMonitor systemMonitor = new SystemMonitor();

    ServiceHDD serviceHDD = new ServiceHDD();
    ServiceCPU serviceCPU = new ServiceCPU();
    ServiceSO serviceSO = new ServiceSO();
    ServiceGPU serviceGPU = new ServiceGPU();
    ServiceUSB serviceUSB = new ServiceUSB();
    ServiceRam serviceRam = new ServiceRam();
    ServiceBateria serviceBateria = new ServiceBateria();
    ServiceVolume serviceVolume = new ServiceVolume();
    ServiceAPP serviceAPP = new ServiceAPP();

    public void exibirTabelas(CPU cpu, List<GPU> gpus, List<HDD> hdd, SistemaOp so, MemoriaRam ram, List<APP> apps, List<ConexaoUSB> usb, List<Bateria> bateria, List<Volume> volumes) throws Exception {
        try {
            Logger.logInfo("Iniciando o monitoramento dos componentes");

            if (systemMonitor == null || tablePrinter == null) {
                throw new NullPointerException("O sistema de monitoramento ou o printer de tabela falharam.");
            }
            serviceHDD.exibirTabelaHDD(hdd);
            serviceSO.exibirTabelaSO(so);
            serviceCPU.exibirTabelaCPU(cpu);
        } catch (NullPointerException e) {
            Logger.logError("Erro ao iniciar monitoramento: ", e.getMessage(), e);
        }
    }


}
