package model.componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConexaoUSB extends Componente {
	private String idDispositivoUSBExclusivo;
	private String idFornecedor;
	private String numeroSerie;
	private String idProduto;

	public ConexaoUSB() {
		this.dataCaptura = LocalDateTime.now();
	}

	public ConexaoUSB(String idDispositivoUSBExclusivo, String idFornecedor, String numeroSerie, String idProduto) {
		super();
		this.idDispositivoUSBExclusivo = idDispositivoUSBExclusivo;
		this.idFornecedor = idFornecedor;
		this.numeroSerie = numeroSerie;
		this.idProduto = idProduto;
		this.dataCaptura = LocalDateTime.now();
	}

	public LocalDateTime getDataHoraCaptura() {
		return dataCaptura;
	}

	public void setDataHoraCaptura(LocalDateTime dataCaptura) {
		this.dataCaptura = dataCaptura;
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
		return Arrays.asList(Arrays.asList("", "Conexão USB"),
				Arrays.asList("Data Hora captura", String.valueOf(dataCaptura)),
				Arrays.asList("Nome USB", Optional.ofNullable(modelo).orElse("N/A")),
				Arrays.asList("Fornecedor", Optional.ofNullable(fabricante).orElse("N/A")),
				Arrays.asList("Data hora captura", Optional.ofNullable(dataCaptura).map(Object::toString).orElse("N/A")),
				Arrays.asList("Id Fornecedor", Optional.ofNullable(idFornecedor).map(Object::toString).orElse("N/A")),
				Arrays.asList("Número Série", Optional.ofNullable(numeroSerie).map(Object::toString).orElse("N/A")),
				Arrays.asList("Id Dispositivo USB exclusivo", Optional.ofNullable(idDispositivoUSBExclusivo).map(Object::toString).orElse("N/A")),
				Arrays.asList("Id Produto", Optional.ofNullable(idProduto).map(Object::toString).orElse("N/A")));
	}

	@Override
	public String tabelaConvert() {
		return TablePrinter.printTable(tabela());
	}

	@Override
	public String pdfLayout() {
		return CreatePDFInfos.gerarLayoutPDF(tabela());
	}

	@Override
	public String getComponente() {
		return this.getClass().getSimpleName();
	}
}
