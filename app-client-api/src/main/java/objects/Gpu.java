package objects;

import objects.Historicos.HistoricoGpu;
import objects.Historicos.HistoricoRam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Gpu {

    private String modelo;
    private long memoria;

    private LocalDateTime dataHoraCaptura;

    private List<HistoricoGpu> historicosGpu;

    public Gpu(String modelo, long memoria, LocalDateTime dataHoraCaptura) {
        this.modelo = modelo;
        this.memoria = memoria;
        this.dataHoraCaptura = dataHoraCaptura;
        this.historicosGpu = new ArrayList<>();
    }

    public Gpu() {
        this.historicosGpu = new ArrayList<>();
    }

    public void gravarHistorico(HistoricoGpu historicoGpu){
        historicosGpu.add(historicoGpu);
    }

    public List<HistoricoGpu> getHistoricosGpu() {
        return historicosGpu;
    }

    public void setHistoricosGpu(List<HistoricoGpu> historicosGpu) {
        this.historicosGpu = historicosGpu;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public long getMemoria() {
        return memoria;
    }

    public void setMemoria(long memoria) {
        this.memoria = memoria;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public String toString() {
        return "GPU: " +
                "\nModelo: " + modelo +
                "\nMemória: " + formatarBytes(memoria) +
                "\nData da captura: " + dataHoraCaptura;
    }

    private String formatarBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1048576) {
            return String.format("%.2f", bytes / 1024.0) + " KB";
        } else if (bytes < 1073741824) {
            return String.format("%.2f", bytes / 1048576.0) + " MB";
        } else {
            return String.format("%.2f", bytes / 1073741824.0) + " GB";
        }
    }
}
