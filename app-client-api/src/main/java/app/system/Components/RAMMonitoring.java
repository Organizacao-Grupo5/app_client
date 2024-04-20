package app.system.Components;

import app.system.Conversor;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.memoria.Memoria;
import model.MemoriaRam;
import util.logs.Logger;

public class RAMMonitoring {
    Looca looca = new Looca();
    Conversor conversor = new Conversor();
    public MemoriaRam monitorarRAM() {
        Logger.logInfo("Capturando dados da sua memória Ram.");
        Memoria memoria = looca.getMemoria();

        if (memoria == null) {
            Logger.logWarning("Dados da memória RAM não puderam ser capturados: memória é nula.");
        }

        MemoriaRam memoriaRam = new MemoriaRam();

        memoriaRam.setMemoriaDisponivel(conversor.formatarBytes(memoria.getDisponivel()));
        memoriaRam.setMemoriaTotal(conversor.formatarBytes(memoria.getTotal()));
        memoriaRam.setMemoriaEmUso(conversor.formatarBytes(memoria.getEmUso()));

        return memoriaRam;
    }
}
