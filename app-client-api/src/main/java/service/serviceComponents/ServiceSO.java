package service.serviceComponents;

import model.SistemaOp;
import service.ServiceMonitoring;
import util.TablePrinter;
import util.logs.Logger;

import java.util.Arrays;
import java.util.Optional;

public class ServiceSO extends ServiceMonitoring {
    public void exibirTabelaSO(SistemaOp sistemaOp) {
        if (sistemaOp == null) {
            Logger.logWarning("Sistema operacional não encontrado durante o monitoramento.");
            return;
        }
        tablePrinter.printTable(Arrays.asList(
                Arrays.asList("", "Sistema Operacional"),
                Arrays.asList("Nome Sistema", Optional.ofNullable(sistemaOp.getSistemaOperacional()).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Fabricante", Optional.ofNullable(sistemaOp.getFabricante()).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Arquitetura", Optional.ofNullable(sistemaOp.getArquitetura()).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Inicializado", Optional.ofNullable(sistemaOp.getInicializado()).map(Object::toString).orElse("VALOR NÃO IDENTIFICADO")),
                Arrays.asList("Permissão", Optional.ofNullable(sistemaOp.isPermissao()).map(Object::toString).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Tempo de atividade", Optional.ofNullable(sistemaOp.getTempoDeAtividade()).orElse("VALOR NÃO IDENTIFICADO")))
        );
    }
}
