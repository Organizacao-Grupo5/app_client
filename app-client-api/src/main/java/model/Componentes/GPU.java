package model.Componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GPU extends Componente{
    private String versao;
    private String idDevice;
    private Double vRam;
    private LocalDateTime dataHoraCaptura;
    private Double temperatura;

    public GPU() {
        dataHoraCaptura = LocalDateTime.now();
    }

    public GPU(String versao, String idDevice, Double vRam) {
        super();
        this.versao = versao;
        this.idDevice = idDevice;
        this.vRam = vRam;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public Double getvRam() {
        return vRam;
    }

    public void setvRam(Double vRam) {
        this.vRam = vRam;
    }

    @Override
    public List<List<String>> tabela() {
        return Arrays.asList(
                Arrays.asList("", "GPU"),
                Arrays.asList("Data Hora captura", String.valueOf(dataHoraCaptura)),
                Arrays.asList("ID", Optional.ofNullable(super.idComponente).map(Object::toString).orElse("N/A")),
                Arrays.asList("Nome", Optional.ofNullable(modelo).orElse("N/A")),
                Arrays.asList("Fabricante", Optional.ofNullable(fabricante).orElse("N/A")),
                Arrays.asList("Versão", Optional.ofNullable(versao).orElse("N/A")),
                Arrays.asList("ID Device", Optional.ofNullable(idDevice).orElse("N/A")),
                Arrays.asList("VRAM", Optional.ofNullable(vRam).map(Object::toString).orElse("N/A")),
                Arrays.asList("Temperatura", Optional.ofNullable(temperatura).map(Object::toString).orElse("N/A") + "°C")
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
    @Override
    public String getComponente() {
        return this.getClass().getSimpleName();
    }
}
