package model.Componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConexaoUSB implements Componente{
    private int idConexaoUSB;
    private String nomeUsb;
    private String fornecedor;
    private String idDispositivoUSBExclusivo;
    private String idFornecedor;
    private String numeroSerie;
    private String idProduto;
    private LocalDateTime dataHoraCaptura;

    public ConexaoUSB() {
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public ConexaoUSB(String nomeUsb, String fornecedor, String idDispositivoUSBExclusivo, String idFornecedor, String numeroSerie, String idProduto) {
        this.nomeUsb = nomeUsb;
        this.fornecedor = fornecedor;
        this.idDispositivoUSBExclusivo = idDispositivoUSBExclusivo;
        this.idFornecedor = idFornecedor;
        this.numeroSerie = numeroSerie;
        this.idProduto = idProduto;
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
    }

    public int getIdConexaoUSB() {
        return idConexaoUSB;
    }

    public void setIdConexaoUSB(int idConexaoUSB) {
        this.idConexaoUSB = idConexaoUSB;
    }

    public String getNomeUsb() {
        return nomeUsb;
    }

    public void setNomeUsb(String nomeUsb) {
        this.nomeUsb = nomeUsb;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getIdDispositivoUSBExclusivo() {
        return idDispositivoUSBExclusivo;
    }

    public void setIdDispositivoUSBExclusivo(String idDispositivoUSBExclusivo) {
        this.idDispositivoUSBExclusivo = idDispositivoUSBExclusivo;
    }

    public String getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(String idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    @Override
    public List<List<String>> tabela() {
        return Arrays.asList(
                Arrays.asList("", "Conexão USB"),
                Arrays.asList("Data Hora captura", String.valueOf(dataHoraCaptura)),
                Arrays.asList("Nome USB", Optional.ofNullable(nomeUsb).orElse("N/A")),
                Arrays.asList("Fornecedor", Optional.ofNullable(fornecedor).orElse("N/A")),
                Arrays.asList("Data hora captura", Optional.ofNullable(dataHoraCaptura).map(Object::toString).orElse("N/A")),
                Arrays.asList("Id Fornecedor", Optional.ofNullable(idFornecedor).map(Object::toString).orElse("N/A")),
                Arrays.asList("Número Série", Optional.ofNullable(numeroSerie).map(Object::toString).orElse("N/A")),
                Arrays.asList("Id Dispositivo USB exclusivo", Optional.ofNullable(idDispositivoUSBExclusivo).map(Object::toString).orElse("N/A")),
                Arrays.asList("Id Produto", Optional.ofNullable(idProduto).map(Object::toString).orElse("N/A"))
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
