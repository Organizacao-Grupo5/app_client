package app.system.Components;

import app.system.Conversor;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.sistema.Sistema;
import model.SistemaOp;
import util.logs.Logger;

import java.util.Optional;

public class SistemaOPMonitoring {
    Looca looca = new Looca();
    Conversor conversor = new Conversor();
    public SistemaOp monitorarSistemaOperacional() {
        Logger.logInfo("Capturando dados do sistema operacional.");
        Sistema sistema = looca.getSistema();

        if (sistema == null) {
            Logger.logWarning("Dados do sistema operacional não puderam ser capturados: sistema é nulo.");
        }

        return new SistemaOp(Optional.ofNullable(sistema.getSistemaOperacional()).orElse("VALOR NÃO ENCONTRADO"),
                Optional.ofNullable(sistema.getFabricante())
                        .orElse("VALOR NÃO ENCONTRADO"),
                Optional.ofNullable(sistema.getArquitetura()).map(arquitetura -> arquitetura.toString())
                        .orElse("VALOR NÃO ENCONTRADO"),
                Optional.ofNullable(sistema.getInicializado())
                        .map(Object::toString).orElse("VALOR NÃO ENCONTRADO"),
                Optional.ofNullable(sistema.getPermissao())
                        .map(p -> p ? "SIM" : "NÃO").orElse("VALOR NÃO ENCONTRADO"),
                Optional.ofNullable(sistema.getTempoDeAtividade()).map(conversor::converterSegundosParaHoras)
                        .map(Object::toString).orElse("VALOR NÃO ENCONTRADO"));
    }
}
