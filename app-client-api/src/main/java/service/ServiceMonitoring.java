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
        exibirMonitoramento(mapaDeHardwaresDisponiveis);
        for (Map.Entry<String, Object> entry : mapaDeHardwaresDisponiveis.entrySet()) {
            System.out.println("Componente: " + entry.getKey());

            Object hardware = entry.getValue();

            switch (hardware) {
                case CPU cpu -> monitoramentoDao.salvarComponenteCPU(maquina.getIdMaquina(), cpu);
                case GPU gpu -> monitoramentoDao.salvarComponenteGPU(maquina.getIdMaquina(), gpu);
                case HDD hdd -> monitoramentoDao.salvarComponenteHDD(maquina.getIdMaquina(), hdd);
                case MemoriaRam memoriaRam -> monitoramentoDao.salvarComponenteMemoriaRAM(maquina.getIdMaquina(), memoriaRam);
                case APP app -> monitoramentoDao.salvarComponenteApp(maquina.getIdMaquina(), app);
                case ConexaoUSB conexaoUSB ->  monitoramentoDao.salvarComponenteConexaoUSB(maquina.getIdMaquina(), conexaoUSB);
                case null, default -> System.out.println("Tipo de componente não reconhecido: " + hardware.getClass().getSimpleName());
            }
        }
        return mapaDeHardwaresDisponiveis = monitoramento.iniarMonitoramento(maquina, usuario);
    }

    public void exibirMonitoramento(Map<String, Object> mapaDeHardwaresDisponiveis){
        for (Map.Entry<String, Object> entry : mapaDeHardwaresDisponiveis.entrySet()) {
            System.out.println("Componente: " + entry.getKey());

            Object hardware = entry.getValue();

            switch (hardware) {
                case CPU cpu:
                    System.out.println("Modelo: " + cpu.getModelo());
                    System.out.println("Número de Série: " + cpu.getNumeroSerie());
                    System.out.println("Fabricante: " + cpu.getFabricante());
                    System.out.println("Arquitetura: " + cpu.getArquitetura());
                    System.out.println("Cache: " + cpu.getCache());
                    System.out.println("Velocidade do CPU: " + cpu.getVelocidadeComponente());
                    System.out.println("Temperatura do CPU: " + cpu.getTemperaturaComponente());
                    break;
                case GPU gpu:
                    System.out.println("Modelo: " + gpu.getModelo());
                    System.out.println("Memória: " + gpu.getMemoria());
                    System.out.println("Utilização: " + gpu.getUtilizacao());
                    System.out.println("Versão do Driver: " + gpu.getVersaoDriver());
                    break;
                case HDD hdd:
                    System.out.println("Capacidade Total: " + hdd.getCapacidadeTotal());
                    System.out.println("Número de Partições: " + hdd.getNumeroParticoes());
                    System.out.println("Status de Saúde: " + hdd.getStatusSaude());
                    break;
                case MemoriaRam memoriaRam:
                    System.out.println("Capacidade Total: " + memoriaRam.getCapacidadeTotal());
                    System.out.println("Número de Módulos: " + memoriaRam.getNumeroModulo());
                    System.out.println("Porcentagem Utilizada: " + memoriaRam.getPorcentagemUtilizada());
                    break;
                case APP app:
                    System.out.println("Nome do App: " + app.getNomeApp());
                    System.out.println("Data de Instalação: " + app.getDtInstalacao());
                    System.out.println("Última Data de Instalação: " + app.getUltimaDtInstalacao());
                    System.out.println("Tamanho do Aplicativo: " + app.getTamanhoAplicativo());
                    break;
                case ConexaoUSB conexaoUSB:
                    System.out.println("Total de Portas: " + conexaoUSB.getTotalPortas());
                    System.out.println("Tipo de Conector: " + conexaoUSB.getTipoConector());
                    System.out.println("Detecção de Dispositivo: " + conexaoUSB.getDeteccaoDispositivo());
                    System.out.println("Energia da Porta: " + conexaoUSB.getEnergiaPorta());
                    System.out.println("Hubs Conectados: " + conexaoUSB.getHubsConectados());
                    System.out.println("Dispositivo Conectado: " + conexaoUSB.getDispositivoConectado());
                    break;
                case null, default:
                    System.out.println("Tipo de componente não reconhecido: " + hardware.getClass().getSimpleName());
            }
        }
    }

}
