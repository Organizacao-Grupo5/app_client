package model.componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HDD extends Componente {
	private String nome;
	private String serial;
	private Double escritas;
	private Double leituras;
	private Double bytesDeEscrita;
	private Double bytesDeLeitura;
	private Double tamanho;
	private Double tempoDeTransferencia;

	public HDD() {
		this.dataCaptura = LocalDateTime.now();
	}

	public HDD(String nome, String serial, Double escritas, Double leituras, Double bytesDeEscrita,
			Double bytesDeLeitura, Double tamanho, Double tempoDeTransferencia) {
		super();
		this.nome = nome;
		this.serial = serial;
		this.escritas = escritas;
		this.leituras = leituras;
		this.bytesDeEscrita = bytesDeEscrita;
		this.bytesDeLeitura = bytesDeLeitura;
		this.tamanho = tamanho;
		this.tempoDeTransferencia = tempoDeTransferencia;
		this.dataCaptura = LocalDateTime.now();
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
		return dataCaptura;
	}

	public void setDataHoraCaptura(LocalDateTime dataCaptura) {
		this.dataCaptura = dataCaptura;
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
		return Arrays.asList(Arrays.asList("", "HDD"), Arrays.asList("Data Hora captura", String.valueOf(dataCaptura)),
				Arrays.asList("Nome", Optional.ofNullable(nome).orElse("N/A")),
				Arrays.asList("Serial", Optional.ofNullable(serial).orElse("N/A")),
				Arrays.asList("Modelo", Optional.ofNullable(modelo).orElse("N/A")),
				Arrays.asList("Escritas", Optional.ofNullable(arredondaValor(escritas)).map(Object::toString).orElse("N/A") + " MB/s"),
				Arrays.asList("Leituras", Optional.ofNullable(arredondaValor(leituras)).map(Object::toString).orElse("N/A") + " MB/s"),
				Arrays.asList("Bytes de escrita",
						Optional.ofNullable(bytesDeEscrita).map(Object::toString).orElse("N/A")),
				Arrays.asList("Bytes de leitura",
						Optional.ofNullable(bytesDeLeitura).map(Object::toString).orElse("N/A")),
				Arrays.asList("Tamanho", Optional.ofNullable(tamanho).map(Object::toString).orElse("N/A") + " GB"),
				Arrays.asList("Tempo de transferência",
						Optional.ofNullable(tempoDeTransferencia).map(Object::toString).orElse("N/A")));
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

	@Override
	public String getUnidadeMedida() {
		return "GB";
	}

	@Override
	public Double getDadoCaptura() {
		return tamanho;
	}

	public Double arredondaValor(Double valor){
		if(!valor.equals(null)){
			return (double) Math.ceil(valor * 100) / 100;
		}
		return null;
	}
}
