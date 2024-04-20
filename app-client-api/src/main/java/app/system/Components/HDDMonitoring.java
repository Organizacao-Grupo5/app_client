package app.system.Components;

import app.system.Conversor;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import exception.InvalidDataException;
import model.HDD;
import util.logs.Logger;

import java.util.ArrayList;
import java.util.List;

public class HDDMonitoring {
    private final Looca looca = new Looca();
    private final Conversor conversor = new Conversor();

    public List<HDD> monitorarHDD() {
        Logger.logInfo("Capturando dados do seu HDD");
        List<HDD> hdds = new ArrayList<>();

        try {
            looca.getGrupoDeDiscos().getDiscos().forEach(disco -> {
                try {
                    validarDadosDisco(disco);
                    HDD hdd = mapearDiscoParaHDD(disco);
                    hdds.add(hdd);
                    Logger.logInfo("Dados do disco gravados.");
                } catch (InvalidDataException e) {
                    Logger.logWarning("Erro ao processar dados do HDD: " + e.getMessage());
                }
            });
            Logger.logInfo("Todos os discos verificados.");
        } catch (Exception e) {
            Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
        }
        return hdds;
    }

    private void validarDadosDisco(Disco disco) {
        if (disco.getNome() == null || disco.getNome().isEmpty()) {
            Logger.logWarning("Não foi possível encontrar o nome do disco.");
        } else {
            Logger.logInfo("Dado capturado do disco | Nome do disco: " + disco.getNome());
        }

        if (disco.getSerial() == null || disco.getSerial().isEmpty()) {
            Logger.logWarning("Não foi possível encontrar o serial do disco.");
        } else {
            Logger.logInfo("Dado capturado do disco | Serial do disco: " + disco.getSerial());
        }

        if (disco.getModelo() == null || disco.getModelo().isEmpty()) {
            Logger.logWarning("Não foi possível encontrar o modelo do disco.");
        } else {
            Logger.logInfo("Dado capturado do disco | Modelo do disco: " + disco.getModelo());
        }

        if (disco.getTamanho() <= 0) {
            Logger.logWarning("Não foi possível encontrar o tamanho do disco.");
        } else {
            Logger.logInfo("Dado capturado do disco | Tamanho do disco: " + disco.getTamanho());
        }
    }

    private HDD mapearDiscoParaHDD(Disco disco) throws InvalidDataException {
        return new HDD(disco.getNome(), disco.getSerial(), disco.getModelo(),
                conversor.formatarBytes(disco.getEscritas()), conversor.formatarBytes(disco.getLeituras()),
                conversor.formatarBytes(disco.getBytesDeEscritas()),
                conversor.formatarBytes(disco.getBytesDeLeitura()),
                conversor.converterCasasDecimais(conversor.formatarBytes(disco.getTamanho()), 2),
                conversor.converterCasasDecimais(conversor.formatarBytes(disco.getTamanhoAtualDaFila()), 2),
                conversor.converterSegundosParaHoras(disco.getTempoDeTransferencia()));
    }
}
