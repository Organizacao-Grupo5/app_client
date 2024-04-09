package objects.Historicos;

import java.time.LocalDateTime;

public class HistoricoCpu {
    private LocalDateTime dataHoraCaptura;
    private double frequencia;
    private double uso;

    public HistoricoCpu(LocalDateTime dataHoraCaptura, double frequencia, double uso) {
        this.dataHoraCaptura = dataHoraCaptura;
        this.frequencia = frequencia;
        this.uso = uso;
    }

    public HistoricoCpu() {

    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(double frequencia) {
        this.frequencia = frequencia;
    }

    public double getUso() {
        return uso;
    }

    public void setUso(double uso) {
        this.uso = uso;
    }

    @Override
    public String toString() {
        return """
                
                Histórico da CPU:
                CPU Frequência : %.2f
                CPU Uso : %.2f
                Data hora captura : %s
                
                """.formatted(frequencia, uso, dataHoraCaptura);
    }
}
