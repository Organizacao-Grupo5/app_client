package model.Componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Volume implements Componente{
    private Integer idVolume;
    private String nome;
    private String volume;
    private Double disponivel;
    private Double total;
    private String tipo;
    private String uuid;
    private String pontoDeMontagem;
    private LocalDateTime dataHoraCaptura;

    public Volume() {
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public Volume(String nome, String volume, Double disponivel, Double total, String tipo, String uuid, String pontoDeMontagem) {
        this.nome = nome;
        this.volume = volume;
        this.disponivel = disponivel;
        this.total = total;
        this.tipo = tipo;
        this.uuid = uuid;
        this.pontoDeMontagem = pontoDeMontagem;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public Integer getIdVolume() {
        return idVolume;
    }

    public void setIdVolume(Integer idVolume) {
        this.idVolume = idVolume;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Double getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Double disponivel) {
        this.disponivel = disponivel;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPontoDeMontagem() {
        return pontoDeMontagem;
    }

    public void setPontoDeMontagem(String pontoDeMontagem) {
        this.pontoDeMontagem = pontoDeMontagem;
    }

    @Override
    public List<List<String>> tabela() {
        return Arrays.asList(
                Arrays.asList("", "VOLUME"),
                Arrays.asList("Data Hora captura", String.valueOf(dataHoraCaptura)),
                Arrays.asList("Nome", Optional.ofNullable(nome).orElse("N/A")),
                Arrays.asList("Volume", Optional.ofNullable(volume).orElse("N/A")),
                Arrays.asList("Disponível", Optional.ofNullable(disponivel).map(Object::toString).orElse("N/A")),
                Arrays.asList("Total", Optional.ofNullable(total).map(Object::toString).orElse("N/A")),
                Arrays.asList("Tipo", Optional.ofNullable(tipo).orElse("N/A")),
                Arrays.asList("UUID", Optional.ofNullable(uuid).orElse("N/A")),
                Arrays.asList("Ponto de Montagem", Optional.ofNullable(pontoDeMontagem).orElse("N/A"))
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
