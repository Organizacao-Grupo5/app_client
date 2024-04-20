package app.system.Components;

import app.system.Conversor;
import com.github.britooo.looca.api.core.Looca;
import model.Volume;

import java.util.List;
import java.util.stream.Collectors;

public class VolumeMonitoring {
    Looca looca = new Looca();
    Conversor conversor = new Conversor();
    public List<Volume> monitorarVolumeLogico() {
        return looca.getGrupoDeDiscos().getVolumes().stream().map(volume -> new Volume(
                volume.getNome(),
                volume.getVolume(),
                conversor.formatarBytes(volume.getDisponivel()),
                conversor.formatarBytes(volume.getTotal()),
                volume.getTipo(),
                volume.getUUID(),
                volume.getPontoDeMontagem()
        )).collect(Collectors.toList());

    }}
