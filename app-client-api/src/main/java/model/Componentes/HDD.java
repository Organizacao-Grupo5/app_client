package model.Componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HDD implements Componente{
    private Integer idHDD;
    private String nome;
    private String serial;
    private String modelo;
    private Double escritas;
    private Double leituras;
    private Double bytesDeEscrita;
    private Double bytesDeLeitura;
    private Double tamanho;
    private Double tempoDeTransferencia;
    private LocalDateTime dataHoraCaptura;

    public HDD() {
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public Integer getIdHDD() {
        return idHDD;
    }

    public HDD(String nome, String serial, String modelo, Double escritas, Double leituras, Double bytesDeEscrita, Double bytesDeLeitura, Double tamanho, Double tempoDeTransferencia) {
        this.nome = nome;
        this.serial = serial;
        this.modelo = modelo;
        this.escritas = escritas;
        this.leituras = leituras;
        this.bytesDeEscrita = bytesDeEscrita;
        this.bytesDeLeitura = bytesDeLeitura;
        this.tamanho = tamanho;
        this.tempoDeTransferencia = tempoDeTransferencia;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public void setIdHDD(Integer idHDD) {
        this.idHDD = idHDD;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getEscritas() {
        return escritas;
    }

    public void setEscritas(Double escritas) {
        this.escritas = escritas;
    }

    public Double getLeituras() {
        return leituras;
    }

    public void setLeituras(Double leituras) {
        this.leituras = leituras;
    }

    public Double getBytesDeEscrita() {
        return bytesDeEscrita;
    }

    public void setBytesDeEscrita(Double bytesDeEscrita) {
        this.bytesDeEscrita = bytesDeEscrita;
    }

    public Double getBytesDeLeitura() {
        return bytesDeLeitura;
    }

    public void setBytesDeLeitura(Double bytesDeLeitura) {
        this.bytesDeLeitura = bytesDeLeitura;
    }

    public Double getTamanho() {
        return tamanho;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public void setTamanho(Double tamanho) {
        this.tamanho = tamanho;
    }

    public Double getTempoDeTransferencia() {
        return tempoDeTransferencia;
    }

    public void setTempoDeTransferencia(Double tempoDeTransferencia) {
        this.tempoDeTransferencia = tempoDeTransferencia;
    }

    @Override
    public List<List<String>> tabela() {
        return Arrays.asList(
                Arrays.asList("", "HDD"),
                Arrays.asList("Data Hora captura", String.valueOf(dataHoraCaptura)),
                Arrays.asList("Nome", Optional.ofNullable(nome).orElse("N/A")),
                Arrays.asList("Serial", Optional.ofNullable(serial).orElse("N/A")),
                Arrays.asList("Modelo", Optional.ofNullable(modelo).orElse("N/A")),
                Arrays.asList("Escritas", Optional.ofNullable(escritas).map(Object::toString).orElse("N/A")),
                Arrays.asList("Leituras", Optional.ofNullable(leituras).map(Object::toString).orElse("N/A")),
                Arrays.asList("Bytes de escrita", Optional.ofNullable(bytesDeEscrita).map(Object::toString).orElse("N/A")),
                Arrays.asList("Bytes de leitura", Optional.ofNullable(bytesDeLeitura).map(Object::toString).orElse("N/A")),
                Arrays.asList("Tamanho", Optional.ofNullable(tamanho).map(Object::toString).orElse("N/A")),
                Arrays.asList("Tempo de transferência", Optional.ofNullable(tempoDeTransferencia).map(Object::toString).orElse("N/A"))
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
