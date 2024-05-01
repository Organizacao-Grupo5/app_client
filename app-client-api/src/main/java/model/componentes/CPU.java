package model.componentes;

import app.system.SystemMonitor;
import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CPU extends Componente {
	SystemMonitor systemMonitor = new SystemMonitor();

	private Integer numeroDeCpusLogicas;
	private Integer numeroDeCpusFisicas;
	private String microarquitetura;
	private String identificador;
	private String idCpuLooca;

	private Double frequencia;
	private Integer numeroPacotesFisicos;
	private Double uso;
	private Double temperatura;

	public CPU() {
		super();
		this.dataCaptura = LocalDateTime.now();
	}

	public CPU(Integer numeroDeCpusLogicas, Integer numeroDeCpusFisicas, String microarquitetura, String identificador,
			String idCpuLooca, Double frequencia, Integer numeroPacotesFisicos, Double uso, Double temperatura) {
		super();
		this.numeroDeCpusLogicas = numeroDeCpusLogicas;
		this.numeroDeCpusFisicas = numeroDeCpusFisicas;
		this.microarquitetura = microarquitetura;
		this.identificador = identificador;
		this.idCpuLooca = idCpuLooca;
		this.frequencia = frequencia;
		this.numeroPacotesFisicos = numeroPacotesFisicos;
		this.uso = uso;
		this.temperatura = temperatura;
		this.dataCaptura = LocalDateTime.now();
	}

	public LocalDateTime getDataHoraCaptura() {
		return dataCaptura;
	}

	public void setDataHoraCaptura(LocalDateTime dataCaptura) {
		this.dataCaptura = dataCaptura;
	}

	public Integer getNumeroDeCpusLogicas() {
		return numeroDeCpusLogicas;
	}

	public void setNumeroDeCpusLogicas(Integer numeroDeCpusLogicas) {
		this.numeroDeCpusLogicas = numeroDeCpusLogicas;
	}

	public Integer getNumeroDeCpusFisicas() {
		return numeroDeCpusFisicas;
	}

	public void setNumeroDeCpusFisicas(Integer numeroDeCpusFisicas) {
		this.numeroDeCpusFisicas = numeroDeCpusFisicas;
	}

	public String getMicroarquitetura() {
		return microarquitetura;
	}

	public void setMicroarquitetura(String microarquitetura) {
		this.microarquitetura = microarquitetura;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getIdCpuLooca() {
		return idCpuLooca;
	}

	public void setIdCpuLooca(String idCpuLooca) {
		this.idCpuLooca = idCpuLooca;
	}

	public Double getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(Double frequencia) {
		this.frequencia = frequencia;
	}

	public Integer getNumeroPacotesFisicos() {
		return numeroPacotesFisicos;
	}

	public void setNumeroPacotesFisicos(Integer numeroPacotesFisicos) {
		this.numeroPacotesFisicos = numeroPacotesFisicos;
	}

	public Double getUso() {
		return uso;
	}

	public void setUso(Double uso) {
		this.uso = uso;
	}

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}

	@Override
	public List<List<String>> tabela() {
		return Arrays.asList(Arrays.asList("", "CPU"), Arrays.asList("Data Hora captura", String.valueOf(dataCaptura)),
				Arrays.asList("Nome", Optional.ofNullable(modelo).orElse("N/A")),
				Arrays.asList("Fabricante", Optional.ofNullable(fabricante).orElse("N/A")),
				Arrays.asList("Microarquitetura", Optional.ofNullable(microarquitetura).orElse("N/A")),
				Arrays.asList("Identificador", Optional.ofNullable(identificador).orElse("N/A")),
				Arrays.asList("ID CPU", Optional.ofNullable(idCpuLooca).orElse("N/A")),
				Arrays.asList("Número de CPUs lógicas", Optional.ofNullable(numeroDeCpusLogicas).map(Object::toString).orElse("N/A")),
				Arrays.asList("Número de CPUs Físicas", Optional.ofNullable(numeroDeCpusFisicas).map(Object::toString).orElse("N/A")),
				Arrays.asList("Número de pacotes físicos", Optional.ofNullable(numeroPacotesFisicos).map(Object::toString).orElse("N/A")),
				Arrays.asList("Frequência", Optional.ofNullable(frequencia).map(Object::toString).orElse("N/A")),
				Arrays.asList("Uso", Optional.ofNullable(uso).map(Object::toString).orElse("N/A")), Arrays.asList("Temperatura", Optional.ofNullable(temperatura).map(Object::toString).orElse("N/A") + "°C"));
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
