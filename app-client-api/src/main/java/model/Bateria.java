package model;

import util.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Bateria {
    private Integer idBateria;
    private Double amperagem;
    private String nomeDispositivo;
    private String numeroSerial;
    private String quimica;
    private String nome;
    private Double voltagem;
    private String unidadesCapacidade;
    private Double capacidadeAtual;
    private int ciclos;
    private Double capacidadeDesign;
    private Double tempoRestanteInstantaneo;
    private Double tempoRestanteEstimado;
    private Double taxaUsoEnergia;
    private Double temperatura;
    private Double capacidadeMaxima;
    private Double percentualCapacidadeRestante;
    private String dataFabricacao;
    private String fabricante;
    private LocalDateTime dataHoraCaptura;
    private Double bateriaAtual;

    public Bateria() {
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public Integer getIdBateria() {
        return idBateria;
    }

    public Bateria(Double amperagem, String nomeDispositivo, String numeroSerial, String quimica, String nome, Double voltagem, String unidadesCapacidade, Double capacidadeAtual, int ciclos, Double capacidadeDesign, Double tempoRestanteInstantaneo, Double tempoRestanteEstimado, Double taxaUsoEnergia, Double temperatura, Double capacidadeMaxima, Double percentualCapacidadeRestante, String dataFabricacao, String fabricante, Double bateriaAtual) {
        this.amperagem = amperagem;
        this.nomeDispositivo = nomeDispositivo;
        this.numeroSerial = numeroSerial;
        this.quimica = quimica;
        this.nome = nome;
        this.voltagem = voltagem;
        this.unidadesCapacidade = unidadesCapacidade;
        this.capacidadeAtual = capacidadeAtual;
        this.ciclos = ciclos;
        this.capacidadeDesign = capacidadeDesign;
        this.tempoRestanteInstantaneo = tempoRestanteInstantaneo;
        this.tempoRestanteEstimado = tempoRestanteEstimado;
        this.taxaUsoEnergia = taxaUsoEnergia;
        this.temperatura = temperatura;
        this.capacidadeMaxima = capacidadeMaxima;
        this.percentualCapacidadeRestante = percentualCapacidadeRestante;
        this.dataFabricacao = dataFabricacao;
        this.fabricante = fabricante;
        this.dataHoraCaptura = LocalDateTime.now();
        this.bateriaAtual = bateriaAtual;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public Double getBateriaAtual() {
        return bateriaAtual;
    }

    public void setBateriaAtual(Double bateriaAtual) {
        this.bateriaAtual = bateriaAtual;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public void setIdBateria(Integer idBateria) {
        this.idBateria = idBateria;
    }

    public Double getAmperagem() {
        return amperagem;
    }

    public void setAmperagem(Double amperagem) {
        this.amperagem = amperagem;
    }

    public String getNomeDispositivo() {
        return nomeDispositivo;
    }

    public void setNomeDispositivo(String nomeDispositivo) {
        this.nomeDispositivo = nomeDispositivo;
    }

    public String getNumeroSerial() {
        return numeroSerial;
    }

    public void setNumeroSerial(String numeroSerial) {
        this.numeroSerial = numeroSerial;
    }

    public String getQuimica() {
        return quimica;
    }

    public void setQuimica(String quimica) {
        this.quimica = quimica;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getVoltagem() {
        return voltagem;
    }

    public void setVoltagem(Double voltagem) {
        this.voltagem = voltagem;
    }

    public String getUnidadesCapacidade() {
        return unidadesCapacidade;
    }

    public void setUnidadesCapacidade(String unidadesCapacidade) {
        this.unidadesCapacidade = unidadesCapacidade;
    }

    public Double getCapacidadeAtual() {
        return capacidadeAtual;
    }

    public void setCapacidadeAtual(Double capacidadeAtual) {
        this.capacidadeAtual = capacidadeAtual;
    }

    public Integer getCiclos() {
        return ciclos;
    }

    public void setCiclos(int ciclos) {
        this.ciclos = ciclos;
    }

    public Double getCapacidadeDesign() {
        return capacidadeDesign;
    }

    public void setCapacidadeDesign(Double capacidadeDesign) {
        this.capacidadeDesign = capacidadeDesign;
    }

    public Double getTempoRestanteInstantaneo() {
        return tempoRestanteInstantaneo;
    }

    public void setTempoRestanteInstantaneo(Double tempoRestanteInstantaneo) {
        this.tempoRestanteInstantaneo = tempoRestanteInstantaneo;
    }

    public Double getTempoRestanteEstimado() {
        return tempoRestanteEstimado;
    }

    public void setTempoRestanteEstimado(Double tempoRestanteEstimado) {
        this.tempoRestanteEstimado = tempoRestanteEstimado;
    }

    public Double getTaxaUsoEnergia() {
        return taxaUsoEnergia;
    }

    public void setTaxaUsoEnergia(Double taxaUsoEnergia) {
        this.taxaUsoEnergia = taxaUsoEnergia;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(Double capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public Double getPercentualCapacidadeRestante() {
        return percentualCapacidadeRestante;
    }

    public void setPercentualCapacidadeRestante(Double percentualCapacidadeRestante) {
        this.percentualCapacidadeRestante = percentualCapacidadeRestante;
    }

    public String getDataFabricacao() {
        return dataFabricacao;
    }

    public void setDataFabricacao(String dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String tabela() {
        List<List<String>> bateriaData = Arrays.asList(
                Arrays.asList("", "Bateria"),
                Arrays.asList("Data Hora captura", String.valueOf(dataHoraCaptura)),
                Arrays.asList("ID", String.valueOf(idBateria)),
                Arrays.asList("Amperagem", String.valueOf(amperagem)),
                Arrays.asList("Nome do Dispositivo", nomeDispositivo),
                Arrays.asList("Número Serial", numeroSerial),
                Arrays.asList("Química", quimica),
                Arrays.asList("Nome", nome),
                Arrays.asList("Voltagem", String.valueOf(voltagem)),
                Arrays.asList("Unidades de Capacidade", unidadesCapacidade),
                Arrays.asList("Capacidade Atual", String.valueOf(capacidadeAtual)),
                Arrays.asList("Ciclos", String.valueOf(ciclos)),
                Arrays.asList("Capacidade Design", String.valueOf(capacidadeDesign)),
                Arrays.asList("Tempo Restante Instantâneo", String.valueOf(tempoRestanteInstantaneo)),
                Arrays.asList("Tempo Restante Estimado", String.valueOf(tempoRestanteEstimado)),
                Arrays.asList("Taxa de Uso de Energia", String.valueOf(taxaUsoEnergia)),
                Arrays.asList("Temperatura", String.valueOf(temperatura)),
                Arrays.asList("Capacidade Máxima", String.valueOf(capacidadeMaxima)),
                Arrays.asList("Percentual Capacidade Restante", String.valueOf(percentualCapacidadeRestante)),
                Arrays.asList("Data de Fabricação", dataFabricacao),
                Arrays.asList("Fabricante", fabricante),
                Arrays.asList("Bateria Atual (%)", bateriaAtual.toString() + "%")
        );
        return TablePrinter.printTable(bateriaData);
    }

}
