package service;

import app.system.SystemMonitor;
import com.mysql.cj.util.StringUtils;
import exception.ExceptionMonitoring;
import model.*;
import util.TablePrinter;
import util.logs.Logger;

import java.util.*;

public class ServiceMonitoring {

    protected TablePrinter tablePrinter = new TablePrinter();
    protected SystemMonitor systemMonitor = new SystemMonitor();

    public void iniciarMonitoramento(CPU cpu, List<GPU> gpus, List<HDD> hdd, SistemaOp so, MemoriaRam ram, List<APP> apps, List<ConexaoUSB> usb, List<Bateria> bateria, List<Volume> volumes) throws Exception {
        try {
            Logger.logInfo("Iniciando o monitoramento dos componentes");

            if (systemMonitor == null || tablePrinter == null) {
                throw new NullPointerException("O sistema de monitoramento ou o printer de tabela falharam.");
            }

        } catch (NullPointerException e) {
            Logger.logError("Erro ao iniciar monitoramento: ", e.getMessage(), e);
        }
    }


}
