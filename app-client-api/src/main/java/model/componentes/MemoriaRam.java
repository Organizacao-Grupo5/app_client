package model.componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MemoriaRam extends Componente {
	private Double memoriaDisponivel;
	private Double memoriaEmUso;
	private Double memoriaTotal;

	public MemoriaRam() {
		this.dataCaptura = LocalDateTime.now();
	}

	public MemoriaRam(int idMemoriaRAM, Double memoriaDisponivel, Double memoriaEmUso, Double memoriaTotal) {
		super();
		this.memoriaDisponivel = memoriaDisponivel;
		this.memoriaEmUso = memoriaEmUso;
		this.memoriaTotal = memoriaTotal;
		this.dataCaptura = LocalDateTime.now();
	}

	public LocalDateTime getDataHoraCaptura() {
		return dataCaptura;
	}

	public void setDataHoraCaptura(LocalDateTime dataCaptura) {
		this.dataCaptura = dataCaptura;
	}

	public Double getMemoriaDisponivel() {
		return memoriaDisponivel;
	}

	public void setMemoriaDisponivel(Double memoriaDisponivel) {
		this.memoriaDisponivel = memoriaDisponivel;
	}

	public Double getMemoriaEmUso() {
		return memoriaEmUso;
	}

	public void setMemoriaEmUso(Double memoriaEmUso) {
		this.memoriaEmUso = memoriaEmUso;
	}

	public Double getMemoriaTotal() {
		return memoriaTotal;
	}

	public void setMemoriaTotal(Double memoriaTotal) {
		this.memoriaTotal = memoriaTotal;
	}

	@Override
	public List<List<String>> tabela() {
		return Arrays.asList(Arrays.asList("", "RAM"), Arrays.asList("Data Hora captura", String.valueOf(dataCaptura)),
				Arrays.asList("ID", Optional.ofNullable(super.idComponente).map(Object::toString).orElse("N/A")),
				Arrays.asList("Memória Disponível",
						Optional.ofNullable(memoriaDisponivel).map(Object::toString).orElse("N/A")),
				Arrays.asList("Memória em Uso", Optional.ofNullable(memoriaEmUso).map(Object::toString).orElse("N/A")),
				Arrays.asList("Memória Total", Optional.ofNullable(memoriaTotal).map(Object::toString).orElse("N/A")));
	}

	@Override
	public String tabelaConvert() {
		return TablePrinter.printTable(tabela());
	}

	@Override
	public String pdfLayout() {
		List<List<String>> listaPDF = new ArrayList<>(tabela());
		listaPDF.add(Arrays.asList("VALOR", memoriaTotal.toString(), memoriaEmUso.toString(), "GB"));
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
		return memoriaEmUso;
	}
}
