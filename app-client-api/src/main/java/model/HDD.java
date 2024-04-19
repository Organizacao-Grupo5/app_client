package model;

public class HDD {
    private Integer idHDD;
    private String nome;
    private String serial;
    private String modelo;
    private long escritas;
    private long leituras;
    private Double bytesDeEscrita;
    private Double bytesDeLeitura;
    private Double tamanho;
    private Double tamanhoAtualDaFita;
    private Double tempoDeTransferencia;

    public HDD() {
    }

    public Integer getIdHDD() {
        return idHDD;
    }

    public HDD(String nome, String serial, String modelo, long escritas, long leituras, Double bytesDeEscrita, Double bytesDeLeitura, Double tamanho, Double tamanhoAtualDaFita, Double tempoDeTransferencia) {
        this.nome = nome;
        this.serial = serial;
        this.modelo = modelo;
        this.escritas = escritas;
        this.leituras = leituras;
        this.bytesDeEscrita = bytesDeEscrita;
        this.bytesDeLeitura = bytesDeLeitura;
        this.tamanho = tamanho;
        this.tamanhoAtualDaFita = tamanhoAtualDaFita;
        this.tempoDeTransferencia = tempoDeTransferencia;
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

    public long getEscritas() {
        return escritas;
    }

    public void setEscritas(long escritas) {
        this.escritas = escritas;
    }

    public long getLeituras() {
        return leituras;
    }

    public void setLeituras(long leituras) {
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

    public void setTamanho(Double tamanho) {
        this.tamanho = tamanho;
    }

    public Double getTamanhoAtualDaFita() {
        return tamanhoAtualDaFita;
    }

    public void setTamanhoAtualDaFita(Double tamanhoAtualDaFita) {
        this.tamanhoAtualDaFita = tamanhoAtualDaFita;
    }

    public Double getTempoDeTransferencia() {
        return tempoDeTransferencia;
    }

    public void setTempoDeTransferencia(Double tempoDeTransferencia) {
        this.tempoDeTransferencia = tempoDeTransferencia;
    }
}
