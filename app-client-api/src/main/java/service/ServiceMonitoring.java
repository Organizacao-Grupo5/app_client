package service;

import core.sistema.Monitoramento;
import dao.MonitoramentoDAO;
import dao.VerificaMonitoramentoDAO;
import exception.ExceptionMonitoring;
import model.Maquina;

import java.net.UnknownHostException;
import java.util.Optional;

public class ServiceMonitoring {
    private final MonitoramentoDAO monitoramentoDao;
    private final VerificaMonitoramentoDAO verificaMonitoramentoDAO;
    private final Monitoramento monitoramento;
    public ServiceMonitoring() {
        this.monitoramentoDao = new MonitoramentoDAO();
        this.monitoramento = new Monitoramento();
        this.verificaMonitoramentoDAO = new VerificaMonitoramentoDAO();
    }

    public Maquina verificaMaquinaUsuario(int idUser) throws ExceptionMonitoring {
        try {
            Optional<Maquina> maquina = monitoramentoDao.findMaquinaByIdUsuario(idUser);
            if (maquina.isEmpty()) {
                throw new ExceptionMonitoring("Falha ao autenticar a máquina. Nenhuma máquina está vinculada a você. Converse com seu administrador.");
            }

            String ipv4 = monitoramento.getIp();

            if (!verificarIp(maquina.get(), ipv4)) {
                throw new ExceptionMonitoring("A máquina à qual sua conta está conectada não é esta. Faça login com a conta correspondente ou troque de máquina.");
            }
            return maquina.get();
        } catch (ExceptionMonitoring e) {
            throw e;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verificarIp(Maquina maquina, String ipv4) {
        return maquina != null && maquina.getIpv4().equals(ipv4);
    }

    public boolean isPrimeiroMonitoramentoCPU(int idMaquina) {
        return verificaMonitoramentoDAO.isPrimeiroMonitoramentoCPU(idMaquina);
    }

    public boolean isPrimeiroMonitoramentoGPU(int idMaquina) {
        return verificaMonitoramentoDAO.isPrimeiroMonitoramentoGPU(idMaquina);
    }

    public boolean isPrimeiroMonitoramentoHDD(int idMaquina) {
        return verificaMonitoramentoDAO.isPrimeiroMonitoramentoHDD(idMaquina);
    }

    public boolean isPrimeiroMonitoramentoCONUSB(int idMaquina) {
        return verificaMonitoramentoDAO.isPrimeiroMonitoramentoCONUSB(idMaquina);
    }

    public boolean isPrimeiroMonitoramentoAPP(int idMaquina) {
        return verificaMonitoramentoDAO.isPrimeiroMonitoramentoAPP(idMaquina);
    }

}
