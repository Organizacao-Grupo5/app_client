package app.system.Components;

import app.system.Conversor;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.temperatura.Temperatura;
import exception.InvalidDataException;
import model.CPU;
import util.logs.Logger;

public class CPUMonitoring {
    private final Looca looca = new Looca();
    private final Conversor conversor = new Conversor();

    public CPU monitorarCPU() {
        Logger.logInfo("Capturando dados da sua CPU.");

        Processador processador = null;
        Temperatura temperatura = null;

        try {
            processador = looca.getProcessador();
            temperatura = looca.getTemperatura();
        } catch (Exception e) {
            Logger.logError("Erro ao acessar informações da CPU: ", e.getMessage(), e);
        }

        validarDadosCPU(processador, temperatura);

        if (processador != null && temperatura != null) {
            return mapearCPU(processador, temperatura);
        } else {
            return null;
        }
    }

    private void validarDadosCPU(Processador processador, Temperatura temperatura) {
        if (processador == null) {
            Logger.logWarning("Dados da CPU não puderam ser capturados: processador é nulo.");
        } else {
            Logger.logInfo("Dados da CPU capturados com sucesso.");
            verificarDadosProcessador(processador, temperatura);
        }

        if (temperatura == null) {
            Logger.logWarning("Dados da CPU não puderam ser capturados: temperatura é nula.");
        } else {
            Logger.logInfo("Dados de temperatura capturados com sucesso.");
        }
    }

    private void verificarDadosProcessador(Processador processador, Temperatura temperatura) {
        if (processador.getNumeroCpusFisicas() <= 0) {
            Logger.logWarning("Número inválido de CPUs físicas.");
        }

        if (processador.getNumeroCpusLogicas() <= 0) {
            Logger.logWarning("Número inválido de CPUs lógicas.");
        }

        if (processador.getFrequencia() <= 0) {
            Logger.logWarning("Frequência inválida da CPU.");
        }

        if (processador.getNumeroPacotesFisicos() <= 0) {
            Logger.logWarning("Número inválido de pacotes físicos da CPU.");
        }

        if (processador.getUso() < 0 || processador.getUso() > 100) {
            Logger.logWarning("Uso inválido da CPU.");
        }

        if (temperatura.getTemperatura() == null) {
            Logger.logWarning("Temperatura inválida da CPU.");
        }
    }

    private CPU mapearCPU(Processador processador, Temperatura temperatura) {
        String frequenciaFormatada = String.valueOf(conversor.formatarBytes(processador.getFrequencia()));
        double usoFormatado = conversor.converterCasasDecimais(processador.getUso(), 2);
        double temperaturaFormatada = temperatura.getTemperatura();

        return new CPU(processador.getNumeroCpusFisicas(), processador.getNumeroCpusLogicas(),
                processador.getMicroarquitetura(), processador.getIdentificador(), processador.getId(),
                processador.getFabricante(), Double.parseDouble(frequenciaFormatada),
                processador.getNumeroPacotesFisicos(), usoFormatado, processador.getNome(),
                temperaturaFormatada);
    }
}
