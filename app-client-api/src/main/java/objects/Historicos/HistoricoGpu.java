package objects.Historicos;

import java.time.LocalDateTime;

public class HistoricoGpu {
    private long memoria;

    private LocalDateTime dataHoraCaptura;

    public HistoricoGpu(long memoria, LocalDateTime dataHoraCaptura) {
        this.memoria = memoria;
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public HistoricoGpu() {

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

    @Override
    public String toString() {
        return """
                
                Histórico da GPU:
                GPU Memória : %s
                GPU Data hora da captura : %s
                
                """.formatted(formatarBytes(memoria), dataHoraCaptura);
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
