package core.sistema;

import com.github.britooo.looca.api.core.Looca;
import exception.ExceptionMonitoring;
import model.Maquina;
import oshi.SystemInfo;
import service.ServiceMonitoring;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Logger;

public class Monitoramento {
    private static Looca looca = new Looca();
    SystemInfo systemInfo = new SystemInfo();
    private static final Logger LOGGER = Logger.getLogger(Monitoramento.class.getName());

    private static final ServiceMonitoring serviceMonitoring;

    public Monitoramento() {
        this.serviceMonitoring = new ServiceMonitoring();
    }

    public String getIp() throws UnknownHostException {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Erro ao obter o endereço IP: " + e.getMessage());
            throw e;
        }
    }

    public static void iniarMonitoramento(Maquina maquina) throws ExceptionMonitoring {
        try {
            if (serviceMonitoring.isPrimeiroMonitoramentoAPP(maquina.getIdMaquina())){

            } else{

            }
            if (serviceMonitoring.isPrimeiroMonitoramentoCPU(maquina.getIdMaquina())){

            } else{

            }
            if (serviceMonitoring.isPrimeiroMonitoramentoCONUSB(maquina.getIdMaquina())){

            } else{

            }
            if (serviceMonitoring.isPrimeiroMonitoramentoGPU(maquina.getIdMaquina())){

            } else{

            }
            if (serviceMonitoring.isPrimeiroMonitoramentoHDD(maquina.getIdMaquina())){

            } else{

            }

        }catch (ExceptionMonitoring e){
            throw new ExceptionMonitoring("Houve um problema ao monitorar um dos componentes: " + e);
        }
    }
}
