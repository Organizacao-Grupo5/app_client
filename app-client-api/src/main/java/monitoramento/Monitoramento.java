package monitoramento;

import com.github.britooo.looca.api.core.Looca;
import jcuda.driver.CUdevice;
import jcuda.driver.JCudaDriver;
import model.*;
import oshi.SystemInfo;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import static jcuda.driver.JCudaDriver.*;

public class Monitoramento {
    private static Looca looca = new Looca();
    SystemInfo systemInfo = new SystemInfo();
    private static final Logger LOGGER = Logger.getLogger(Monitoramento.class.getName());

    public static Cpu monitorarCpu() throws Exception{
        Cpu cpu = new Cpu();
        try{
            System.out.println("Monitorando CPU ...");
            cpu.setModelo(looca.getProcessador().getNome());
            cpu.setFrequencia(looca.getProcessador().getFrequencia());
            cpu.setUso(looca.getProcessador().getUso());
            cpu.setNumeroNucleos(looca.getProcessador().getNumeroCpusFisicas() + looca.getProcessador().getNumeroCpusLogicas());
            cpu.setDataHoraCaptura(LocalDateTime.now());
        } catch (Exception e){
            LOGGER.severe("Erro de monitoramento da CPU\nErro: " + e);
            throw new Exception(e);
        } finally {
            System.out.println("Finalizado monitoramento da CPU ...");
        }
        return cpu;
    }

    public static Disco monitorarDisco() throws Exception{
        Disco disco = new Disco();
        try{
            System.out.println("Monitorando Disco ...");
            disco.setLivre(looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel());
            disco.setTotal(looca.getGrupoDeDiscos().getVolumes().get(0).getTotal());
            disco.setUsado(looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() - looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel());
            disco.setDataHoraCaptura(LocalDateTime.now());
        } catch (Exception e){
            LOGGER.severe("Erro de monitoramento do Disco\nErro: " + e);
            throw new Exception(e);
        }finally {
            System.out.println("Finalizado monitoramento do Disco ...");
        }
        return disco;
    }

    public static Ram monitorarRam() throws Exception{
        Ram ram = new Ram();
        try{
            System.out.println("Monitorando a RAM ...");
            ram.setLivre(looca.getMemoria().getDisponivel());
            ram.setTotal(looca.getMemoria().getTotal());
            ram.setUsado(looca.getMemoria().getEmUso());
            ram.setDataHoraCaptura(LocalDateTime.now());
        } catch (Exception e){
            LOGGER.severe("Erro de monitoramento da Ram\nErro: " + e);
            throw new Exception(e);
        }finally {
            System.out.println("Finalizado monitoramento da RAM ...");
        }
        return ram;
    }

    public static Gpu monitorarGPU() throws Exception {
        Gpu gpuInfo = new Gpu();
        try {
            System.out.println("Monitorando GPU ...");
            JCudaDriver.setExceptionsEnabled(true);
            cuInit(0);

            int[] deviceCount = new int[1];
            cuDeviceGetCount(deviceCount);
            int numDevices = deviceCount[0];
            if (numDevices < 1) {
                throw new Exception("Nenhuma GPU encontrada.");
            }

            CUdevice device = new CUdevice();
            cuDeviceGet(device, 0);

            long[] totalMemory = new long[1];
            long[] freeMemory = new long[1];
            cuMemGetInfo(freeMemory, totalMemory);

            gpuInfo.setLivre(freeMemory[0]);
            gpuInfo.setTotal(totalMemory[0]);
            gpuInfo.setDataHoraCaptura(LocalDateTime.now());
        } catch (Exception e) {
            LOGGER.severe("Erro de monitoramento da GPU\nErro: " + e);
            throw new Exception(e);
        }finally {
            System.out.println("Finalizado monitoramento da GPU ...");
        }
        return gpuInfo;
    }

    public static Maquina monitorarMaquina(){
        Maquina maquina = new Maquina();

        try {
            System.out.println("Monitorando a máquina ...");
            InetAddress myip = InetAddress.getLocalHost();
            // Verificar se a máquina já foi monitorada alguma vez

            maquina.setNomeMaquina(System.getProperty("user.name"));
            maquina.setSistOperacional(System.getProperty("os.name"));
            maquina.setFabricante("Não disponível");
            maquina.setPlacaMae("Não disponível");
            maquina.setVersaoBios("Não disponível");

        } catch (Exception e){
            LOGGER.severe("Erro ao capturar dados da máquina\nErro: " + e);
        }finally {
            System.out.println("Finalizado monitoramento da Máquina ...");
        }

        return maquina;
    }
}
