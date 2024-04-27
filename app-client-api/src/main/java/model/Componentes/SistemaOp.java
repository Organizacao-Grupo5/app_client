package model.Componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SistemaOp implements Componente{
    private int idSistemaOperacional;

    private String sistemaOperacional;
    private String fabricante;
    private String arquitetura;
    private String inicializado;
    private String permissao;
    private String tempoDeAtividade;
    private LocalDateTime dataHoraCaptura;

    public SistemaOp() {
        dataHoraCaptura = LocalDateTime.now();
    }

    public SistemaOp(String sistemaOperacional, String fabricante, String arquitetura, String inicializado, String permissao, String tempoDeAtividade) {
        this.sistemaOperacional = sistemaOperacional;
        this.fabricante = fabricante;
        this.arquitetura = arquitetura;
        this.inicializado = inicializado;
        this.permissao = permissao ;
        this.tempoDeAtividade = tempoDeAtividade;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public String getPermissao() {
        return permissao;
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public int getIdSistemaOperacional() {
        return idSistemaOperacional;
    }

    public void setIdSistemaOperacional(int idSistemaOperacional) {
        this.idSistemaOperacional = idSistemaOperacional;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getArquitetura() {
        return arquitetura;
    }

    public void setArquitetura(String arquitetura) {
        this.arquitetura = arquitetura;
    }

    public String getInicializado() {
        return inicializado;
    }

    public void setInicializado(String inicializado) {
        this.inicializado = inicializado;
    }

    public String isPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public String getTempoDeAtividade() {
        return tempoDeAtividade;
    }

    public void setTempoDeAtividade(String tempoDeAtividade) {
        this.tempoDeAtividade = tempoDeAtividade;
    }

    public List<List<String>> tabela() {
        return Arrays.asList(
                Arrays.asList("", "Sistema Operacional"),
                Arrays.asList("Data Hora captura", String.valueOf(dataHoraCaptura)),
                Arrays.asList("Nome Sistema", Optional.ofNullable(sistemaOperacional).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Fabricante", Optional.ofNullable(fabricante).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Arquitetura", Optional.ofNullable(arquitetura).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Inicializado", Optional.ofNullable(inicializado).map(Object::toString).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Permissão", Optional.ofNullable(permissao).map(Object::toString).orElse("VALOR NÃO ENCONTRADO")),
                Arrays.asList("Tempo de atividade", Optional.ofNullable(tempoDeAtividade).map(Object::toString).orElse("VALOR NÃO ENCONTRADO"))
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
