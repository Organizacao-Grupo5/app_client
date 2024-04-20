package app.system.Components;

import app.system.Conversor;
import com.github.britooo.looca.api.core.Looca;
import model.ConexaoUSB;
import util.logs.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class USBMonitoring {
    Looca looca = new Looca();
    Conversor conversor = new Conversor();
    public List<ConexaoUSB> monitorarUSB() {
        Logger.logInfo("Capturando dados das suas conexões USB");
        return looca.getDispositivosUsbGrupo().getDispositivosUsbConectados().stream()
                .map(usb -> new ConexaoUSB(usb.getNome(), usb.getForncecedor(), usb.getIdDispositivoUsbExclusivo(),
                        usb.getIdFornecedor(), usb.getNumeroDeSerie(), usb.getIdProduto()))
                .collect(Collectors.toList());
    }

}
