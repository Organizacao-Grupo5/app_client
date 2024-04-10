package model;

import java.time.LocalDateTime;

public class Disco {
    private long total;
    private long usado;
    private long livre;
    private LocalDateTime dataHoraCaptura;

    public Disco(long total, long usado, long livre, LocalDateTime dataHoraCaptura) {
        this.total = total;
        this.usado = usado;
        this.livre = livre;
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public Disco() {

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
