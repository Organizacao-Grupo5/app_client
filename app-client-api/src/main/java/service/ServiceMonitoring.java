package service;

import core.sistema.SaveMonitoramento;
import dao.MonitoramentoDAO;
import exception.ExceptionMonitoring;
import model.*;

import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceMonitoring {
    private final MonitoramentoDAO monitoramentoDao;
    private final SaveMonitoramento monitoramento;

    public ServiceMonitoring() {
        this.monitoramentoDao = new MonitoramentoDAO();
        this.monitoramento = new SaveMonitoramento();
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
        Map<String, Set<Integer>> mapaDeIds = monitoramentoDao.coletaIDComponente(mapaDeHardwaresDisponiveis, maquina.getIdMaquina());
        adicionarIdsAosComponentesDoMap(mapaDeHardwaresDisponiveis, mapaDeIds);

        List<HDD> listaDeHDD = monitoramento.inicializarHDD();
        List<ConexaoUSB> listaDeUSB = monitoramento.inicializarConexaoUSB();
        List<APP> listaDeAPP = monitoramento.inicializarAPP();
        for (Map.Entry<String, Object> entry : mapaDeHardwaresDisponiveis.entrySet()) {
            String chave = entry.getKey();
            Object hardware = entry.getValue();

            if (chave.contains("CPU")) {
                monitoramentoDao.salvarComponenteCPU(maquina.getIdMaquina(), (CPU) hardware);
            } else if (chave.contains("GPU")) {
                monitoramentoDao.salvarComponenteGPU(maquina.getIdMaquina(), (GPU) hardware);
            } else if (chave.contains("RAM")) {
                monitoramentoDao.salvarComponenteMemoriaRAM(maquina.getIdMaquina(), (MemoriaRam) hardware);
            }
        }

        adicionaIdsAosComponentesDaLista(listaDeHDD, listaDeAPP, listaDeUSB, mapaDeIds);
        for (HDD hdd : listaDeHDD) {
            monitoramentoDao.salvarComponenteHDD(maquina.getIdMaquina(), hdd);
        }
/*
        for (APP app : listaDeAPP) {
            System.out.println(app.getIdApp());
            monitoramentoDao.salvarComponenteApp(maquina.getIdMaquina(), app);
        }
 */
        for (ConexaoUSB conexaoUSB : listaDeUSB) {
            monitoramentoDao.salvarComponenteConexaoUSB(maquina.getIdMaquina(), conexaoUSB);
        }
        monitoramento.exibirMonitoramento();
        return mapaDeHardwaresDisponiveis = monitoramento.iniarMonitoramento(maquina, usuario);
    }


    public void adicionarIdsAosComponentesDoMap(Map<String, Object> mapaDeHardwaresDisponiveis, Map<String, Set<Integer>> mapaDeIds) {
        for (Map.Entry<String, Object> entry : mapaDeHardwaresDisponiveis.entrySet()) {
            String tipoComponente = entry.getKey();
            Object hardware = entry.getValue();

            Set<Integer> ids = mapaDeIds.get(tipoComponente);

            if (ids != null && !ids.isEmpty()) {
                int id = ids.iterator().next();

                if (tipoComponente.contains("CPU")) {
                    CPU cpu = (CPU) hardware;
                    cpu.setIdCPU(id);
                } else if (tipoComponente.contains("GPU")) {
                    GPU gpu = (GPU) hardware;
                    gpu.setIdGPU(id);
                } else if (tipoComponente.contains("RAM")) {
                    MemoriaRam memoriaRam = (MemoriaRam) hardware;
                    memoriaRam.setIdMemoriaRAM(id);
                }

                ids.remove(id);
            }
        }
    }

    public void adicionaIdsAosComponentesDaLista(List<HDD> listaHDD, List<APP> listaAPP, List<ConexaoUSB> listaUSB, Map<String, Set<Integer>> mapaDeIds) {
        List<Integer> idsHDD = mapaDeIds.getOrDefault("HDD", Collections.emptySet()).stream().collect(Collectors.toList());
        for (int i = 0; i < listaHDD.size() && i < idsHDD.size(); i++) {
            listaHDD.get(i).setIdHDD(idsHDD.get(i));
        }

        List<Integer> idsAPP = mapaDeIds.getOrDefault("APP", Collections.emptySet()).stream().collect(Collectors.toList());
        for (int i = 0; i < listaAPP.size() && i < idsAPP.size(); i++) {
            listaAPP.get(i).setIdApp(idsAPP.get(i));
        }

        List<Integer> idsUSB = mapaDeIds.getOrDefault("CONEXAO USB", Collections.emptySet()).stream().collect(Collectors.toList());
        for (int i = 0; i < listaUSB.size() && i < idsUSB.size(); i++) {
            listaUSB.get(i).setIdConexaoUSB(idsUSB.get(i));
        }
    }

}
