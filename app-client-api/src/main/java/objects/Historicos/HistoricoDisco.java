package objects.Historicos;

import java.time.LocalDateTime;

public class HistoricoDisco {
    private long total;
    private long usado;
    private long livre;
    private LocalDateTime dataHoraCaptura;

    public HistoricoDisco(long total, long usado, long livre, LocalDateTime dataHoraCaptura) {
        this.total = total;
        this.usado = usado;
        this.livre = livre;
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public HistoricoDisco() {

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

    @Override
    public String toString() {
        return  """
                
                Histórico do Disco:
                Disco Total : %s
                Disco Usado : %s
                Disco Livre : %s
                Data hora captura : %s
                
                """.formatted(formatarBytes(total), formatarBytes(usado), formatarBytes(livre), dataHoraCaptura);
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
