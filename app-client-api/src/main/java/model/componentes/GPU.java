package model.componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class GPU extends Componente {
	private String versao;
	private String idDevice;
	private Double vRam;
	private Double temperatura;

	public GPU() {
		dataCaptura = LocalDateTime.now();
	}

	public GPU(String versao, String idDevice, Double vRam) {
		super();
		this.versao = versao;
		this.idDevice = idDevice;
		this.vRam = vRam;
		this.dataCaptura = LocalDateTime.now();
	}

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}

	public LocalDateTime getDataHoraCaptura() {
		return dataCaptura;
	}

	public void setDataHoraCaptura(LocalDateTime dataCaptura) {
		this.dataCaptura = dataCaptura;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(String idDevice) {
		this.idDevice = idDevice;
	}

	public Double getvRam() {
		return vRam;
	}

	public void setvRam(Double vRam) {
		this.vRam = vRam;
	}

	@Override
	public List<List<String>> tabela() {
		return Arrays.asList(Arrays.asList("", "GPU"), Arrays.asList("Data Hora captura", String.valueOf(dataCaptura)),
				Arrays.asList("ID", Optional.ofNullable(super.idComponente).map(Object::toString).orElse("N/A")),
				Arrays.asList("Nome", Optional.ofNullable(modelo).orElse("N/A")),
				Arrays.asList("Fabricante", Optional.ofNullable(fabricante).orElse("N/A")),
				Arrays.asList("Versão", Optional.ofNullable(versao).orElse("N/A")),
				Arrays.asList("ID Device", Optional.ofNullable(idDevice).orElse("N/A")),
				Arrays.asList("VRAM", Optional.ofNullable(vRam).map(Object::toString).orElse("N/A") + " GB"),
				Arrays.asList("Temperatura", Optional.ofNullable(temperatura).map(Object::toString).orElse("N/A") + "°C"));
	}

	@Override
	public String tabelaConvert() {
		return TablePrinter.printTable(tabela());
	}

	@Override
	public String pdfLayout() {
		List<List<String>> listaPDF = new ArrayList<>(tabela());
		listaPDF.add(Arrays.asList("VALOR", String.valueOf(85.0), temperatura.toString(), "°C"));
		return CreatePDFInfos.gerarLayoutPDF(listaPDF);
	}

	@Override
	public String getComponente() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String getUnidadeMedida() {
		return "°C";
	}

	@Override
	public Double getDadoCaptura() {
		return temperatura;
	}
}
