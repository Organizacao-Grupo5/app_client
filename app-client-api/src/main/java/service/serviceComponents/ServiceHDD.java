package service.serviceComponents;

import com.mysql.cj.util.StringUtils;
import model.HDD;
import service.ServiceMonitoring;
import util.logs.Logger;

import java.util.Arrays;
import java.util.List;

public class ServiceHDD extends ServiceMonitoring {

    public void exibirTabelaHDD(List<HDD> listaHDD) {
        if (StringUtils.isNullOrEmpty(listaHDD.toString())) {
            Logger.logWarning("Nenhum HDD encontrado durante o monitoramento.");
            return;
        }
        listaHDD.forEach(hdd -> {
            Logger.logInfo("Enviando informações para exibir a tabela do HDD %s no sistema de monitoramento no HDD ".formatted(hdd.getSerial()));
            tablePrinter.printTable(
                    Arrays.asList(
                            Arrays.asList("", "HDD"),
                            Arrays.asList("Nome", hdd.getNome()),
                            Arrays.asList("Serial", hdd.getSerial()),
                            Arrays.asList("Modelo", hdd.getModelo()),
                            Arrays.asList("Escritas", hdd.getEscritas().toString()),
                            Arrays.asList("Leituras", hdd.getLeituras().toString()),
                            Arrays.asList("Bytes de escrita", hdd.getBytesDeEscrita().toString()),
                            Arrays.asList("Bytes de leitura", hdd.getBytesDeLeitura().toString()),
                            Arrays.asList("Tamanho", hdd.getTamanho().toString()),
                            Arrays.asList("Tamanho atual", hdd.getTamanhoAtualDaFita().toString()),
                            Arrays.asList("Tempo de transferência", hdd.getTempoDeTransferencia().toString())
                    )
            );
        });
    }
}
