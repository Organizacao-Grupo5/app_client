package model.Componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MemoriaRam extends Componente{
    private Double memoriaDisponivel;
    private Double memoriaEmUso;
    private Double memoriaTotal;
    private LocalDateTime dataHoraCaptura;

    public MemoriaRam() {
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public MemoriaRam(int idMemoriaRAM, Double memoriaDisponivel, Double memoriaEmUso, Double memoriaTotal) {
        super();
        this.memoriaDisponivel = memoriaDisponivel;
        this.memoriaEmUso = memoriaEmUso;
        this.memoriaTotal = memoriaTotal;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public Double getMemoriaDisponivel() {
        return memoriaDisponivel;
    }

    public void setMemoriaDisponivel(Double memoriaDisponivel) {
        this.memoriaDisponivel = memoriaDisponivel;
    }

    public Double getMemoriaEmUso() {
        return memoriaEmUso;
    }

    public void setMemoriaEmUso(Double memoriaEmUso) {
        this.memoriaEmUso = memoriaEmUso;
    }

    public Double getMemoriaTotal() {
        return memoriaTotal;
    }

    public void setMemoriaTotal(Double memoriaTotal) {
        this.memoriaTotal = memoriaTotal;
    }

    @Override
    public List<List<String>> tabela() {
        return Arrays.asList(
                Arrays.asList("", "RAM"),
                Arrays.asList("Data Hora captura", String.valueOf(dataHoraCaptura)),
                Arrays.asList("ID", Optional.ofNullable(super.idComponente).map(Object::toString).orElse("N/A")),
                Arrays.asList("Memória Disponível", Optional.ofNullable(memoriaDisponivel).map(Object::toString).orElse("N/A")),
                Arrays.asList("Memória em Uso", Optional.ofNullable(memoriaEmUso).map(Object::toString).orElse("N/A")),
                Arrays.asList("Memória Total", Optional.ofNullable(memoriaTotal).map(Object::toString).orElse("N/A"))
        );
    }

    @Override
    public String tabelaConvert(){
        return TablePrinter.printTable(tabela());
    }
    @Override
    public String pdfLayout(){
        return CreatePDFInfos.gerarLayoutPDF(tabela());
    }
}
