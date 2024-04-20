import app.system.Conversor;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.janelas.Janela;
import exception.InvalidDataException;
import model.APP;
import util.logs.Logger;

import java.util.ArrayList;
import java.util.List;

public class APPMonitoring {
    private final Looca looca = new Looca();
    private final Conversor conversor = new Conversor();

    public List<APP> monitorarDisplay() {
        Logger.logInfo("Capturando dados dos seus Apps.");
        List<APP> apps = new ArrayList<>();

        try {
            looca.getGrupoDeJanelas().getJanelasVisiveis().forEach(janela -> {
                try {
                    validarDados(janela);
                    Logger.logInfo("Dados da janela capturados.");
                    APP app = new APP(janela.getTitulo(), janela.getComando(),
                            conversor.formatarBytes(janela.getJanelaId()), conversor.formatarBytes(janela.getPid()),
                            janela.getLocalizacaoETamanho());
                    apps.add(app);
                    Logger.logInfo("Dados da janela gravados.");
                } catch (Exception e) {
                    Logger.logError("Erro ao processar janela: ", e.getMessage(), e);
                }
            });
            Logger.logInfo("Todas as janelas verificadas.");
        } catch (Exception e) {
            Logger.logError("Erro ao acessar informações do sistema: ", e.getMessage(), e);
        }

        return apps;
    }

    private void validarDados(Janela janela) {
        if (janela.getTitulo() == null || janela.getTitulo().isEmpty()) {
            Logger.logWarning("Não foi possível encontrar o título da janela.");
        } else {
            Logger.logInfo("Dado capturado da janela | título da janela: " + janela.getTitulo());
        }

        if (janela.getComando() == null || janela.getComando().isEmpty()) {
            Logger.logWarning("Não foi possível encontrar o comando da janela.");
        } else {
            Logger.logInfo("Dado capturado da janela | comando da janela: " + janela.getComando());
        }

        if (janela.getJanelaId() <= 0) {
            Logger.logWarning("Não foi possível encontrar o id da janela.");
        } else {
            Logger.logInfo("Dado capturado da janela | id da janela: " + janela.getJanelaId());
        }

        if (janela.getPid() <= 0) {
            Logger.logWarning("Não foi possível encontrar o PID da janela.");
        } else {
            Logger.logInfo("Dado capturado da janela | PID da janela: " + janela.getPid());
        }

        if (janela.getLocalizacaoETamanho() == null) {
            Logger.logWarning("Não foi possível encontrar a localização da janela.");
        } else {
            Logger.logInfo("Dado capturado da janela | localização da janela: " + janela.getLocalizacaoETamanho());
        }
    }
}
