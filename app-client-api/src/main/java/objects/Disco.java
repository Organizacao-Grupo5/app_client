package objects;

import objects.Historicos.HistoricoDisco;
import objects.Historicos.HistoricoRam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Disco {
    private long total;
    private long usado;
    private long livre;
    private LocalDateTime dataHoraCaptura;

    private List<HistoricoDisco> historicosDisco;

    public Disco(long total, long usado, long livre, LocalDateTime dataHoraCaptura) {
        this.total = total;
        this.usado = usado;
        this.livre = livre;
        this.dataHoraCaptura = dataHoraCaptura;
        this.historicosDisco = new ArrayList<>();
    }

    public Disco() {
        this.historicosDisco = new ArrayList<>();
    }

    public List<HistoricoDisco> getHistoricosDisco() {
        return historicosDisco;
    }

    public void gravarHistorico(HistoricoDisco historicoDisco){
        historicosDisco.add(historicoDisco);
    }

    public void setHistoricosDisco(List<HistoricoDisco> historicosDisco) {
        this.historicosDisco = historicosDisco;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getUsado() {
        return usado;
    }

    public void setUsado(long usado) {
        this.usado = usado;
    }

    public long getLivre() {
        return livre;
    }

    public void setLivre(long livre) {
        this.livre = livre;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public String toString() {
        return "Disco: " +
                "\nTotal: " + formatarBytes(total) +
                "\nUsado: " + formatarBytes(usado) +
                "\nLivre: " + formatarBytes(livre) +
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
