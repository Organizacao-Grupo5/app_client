package service;

import core.sistema.Monitoramento;
import dao.MonitoramentoDAO;
import exception.ExceptionMonitoring;
import model.*;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ServiceMonitoring {
    private final MonitoramentoDAO monitoramentoDao;
    private final Monitoramento monitoramento;
    public ServiceMonitoring() {
        this.monitoramentoDao = new MonitoramentoDAO();
        this.monitoramento = new Monitoramento();
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

    public Map<String, Object> monitorar(Maquina maquina, Usuario usuario) throws ExceptionMonitoring {
        Map<String, Object> mapaDeHardwaresDisponiveis = monitoramento.iniarMonitoramento(maquina, usuario);

        for (Map.Entry<String, Object> entry : mapaDeHardwaresDisponiveis.entrySet()) {
            System.out.println("Componente: " + entry.getKey());
            Object hardware = entry.getValue();
            if (hardware instanceof CPU cpu) {
                System.out.println("Modelo da CPU: " + cpu.getModelo());
                System.out.println("Número de Série da CPU: " + cpu.getNumeroSerie());
                // Adicione mais informações da CPU aqui, se necessário
            } else if (hardware instanceof GPU gpu) {
                System.out.println("Modelo da GPU: " + gpu.getModelo());
                System.out.println("Memória da GPU: " + gpu.getMemoria());
                System.out.println("Utilização da GPU: " + gpu.getUtilizacao());
                System.out.println("Versão do Driver da GPU: " + gpu.getVersaoDriver());
                // Adicione mais informações da GPU aqui, se necessário
            } else if (hardware instanceof HDD hdd) {
                System.out.println("Capacidade Total do HDD: " + hdd.getCapacidadeTotal());
                System.out.println("Número de Partições do HDD: " + hdd.getNumeroParticoes());
                System.out.println("Status de Saúde do HDD: " + hdd.getStatusSaude());
                // Adicione mais informações do HDD aqui, se necessário
            } else if (hardware instanceof MemoriaRam memoriaRam) {
                System.out.println("Capacidade Total da RAM: " + memoriaRam.getCapacidadeTotal());
                System.out.println("Capacidade Utilizada da RAM: " + memoriaRam.getPorcentagemUtilizada());
                System.out.println("Porcentagem Utilizada da RAM: " + memoriaRam.getPorcentagemUtilizada());
                System.out.println("Número de Módulos de RAM: " + memoriaRam.getNumeroModulo());
                // Adicione mais informações da RAM aqui, se necessário
            } else if (hardware instanceof APP app) {
                System.out.println("Nome do Aplicativo: " + app.getNomeApp());
                System.out.println("Data de Instalação: " + app.getDtInstalacao());
                System.out.println("Última Data de Instalação: " + app.getUltimaDtInstalacao());
                System.out.println("Tamanho do Aplicativo: " + app.getTamanhoAplicativo());
                // Adicione mais informações do APP aqui, se necessário
            } else if (hardware instanceof ConexaoUSB conexaoUSB) {
                System.out.println("Total de Portas USB: " + conexaoUSB.getTotalPortas());
                System.out.println("Tipo de Conector USB: " + conexaoUSB.getTipoConector());
                System.out.println("Detecção de Dispositivo USB: " + conexaoUSB.getDeteccaoDispositivo());
                System.out.println("Energia das Portas USB: " + conexaoUSB.getEnergiaPorta());
                System.out.println("Hubs Conectados: " + conexaoUSB.getHubsConectados());
                System.out.println("Dispositivo Conectado: " + conexaoUSB.getDispositivoConectado());
                // Adicione mais informações da Conexão USB aqui, se necessário
            } else {
                System.out.println("Tipo de componente não reconhecido: " + hardware.getClass().getSimpleName());
            }
            System.out.println("-----------------------------------");
        }
        
        return mapaDeHardwaresDisponiveis = monitoramento.iniarMonitoramento(maquina, usuario);
    }

}
