package model.componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Volume extends Componente {
	private String volume;
	private Double disponivel;
	private Double total;
	private String tipo;
	private String uuid;
	private String pontoDeMontagem;

	public Volume() {
		this.dataCaptura = LocalDateTime.now();
	}

	public Volume(String volume, Double disponivel, Double total, String tipo, String uuid, String pontoDeMontagem) {
		super();
		this.volume = volume;
		this.disponivel = disponivel;
		this.total = total;
		this.tipo = tipo;
		this.uuid = uuid;
		this.pontoDeMontagem = pontoDeMontagem;
		this.dataCaptura = LocalDateTime.now();
	}

	public LocalDateTime getDataHoraCaptura() {
		return dataCaptura;
	}

	public void setDataHoraCaptura(LocalDateTime dataCaptura) {
		this.dataCaptura = dataCaptura;
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
		return Arrays.asList(Arrays.asList("", "VOLUME"),
				Arrays.asList("Data Hora captura", String.valueOf(dataCaptura)),
				Arrays.asList("Nome", Optional.ofNullable(modelo).orElse("N/A")),
				Arrays.asList("Volume", Optional.ofNullable(volume).orElse("N/A")),
				Arrays.asList("Disponível", disponivel != null ? arredondaValor(disponivel).toString() : "N/A" + " GB"),
				Arrays.asList("Total", total != null ? arredondaValor(total).toString() : "N/A" + " GB"),
				Arrays.asList("Tipo", Optional.ofNullable(tipo).orElse("N/A")),
				Arrays.asList("UUID", Optional.ofNullable(fabricante).orElse("N/A")),
				Arrays.asList("Ponto de Montagem", Optional.ofNullable(pontoDeMontagem).orElse("N/A")));
	}

	@Override
	public String tabelaConvert() {
		return TablePrinter.printTable(tabela());
	}

	@Override
	public String pdfLayout() {
		List<List<String>> listaPDF = new ArrayList<>(tabela());
		listaPDF.add(Arrays.asList("VALOR", total.toString(), disponivel.toString(), "GB"));
		return CreatePDFInfos.gerarLayoutPDF(listaPDF);
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
		return total - disponivel;
	}

	public Double arredondaValor(Double valor){
		if(!valor.equals(null)){
			return (double) Math.round(valor * 100) / 100;
		}
		return null;
	}
}
