package model.componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class APP extends Componente {
	private int idApp;
	private String nome;

	private String comando;
	private Double janelaID;
	private Double pid;
	private Rectangle localizacaoEtamanho;
	private LocalDateTime dataHoraCaptura;

	public APP() {
		super();
		this.dataHoraCaptura = LocalDateTime.now();
	}

	public APP(String nome, String comando, Double janelaID, Double pid, Rectangle localizacaoEtamanho) {
		this.nome = nome;
		this.comando = comando;
		this.janelaID = janelaID;
		this.pid = pid;
		this.localizacaoEtamanho = localizacaoEtamanho;
		this.dataHoraCaptura = LocalDateTime.now();
	}

	public LocalDateTime getDataHoraCaptura() {
		return dataHoraCaptura;
	}

	public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
		this.dataHoraCaptura = dataHoraCaptura;
	}

	public int getIdApp() {
		return idApp;
	}

	public void setIdApp(int idApp) {
		this.idApp = idApp;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public Double getJanelaID() {
		return janelaID;
	}

	public void setJanelaID(Double janelaID) {
		this.janelaID = janelaID;
	}

	public Double getPid() {
		return pid;
	}

	public void setPid(Double pid) {
		this.pid = pid;
	}

	public Rectangle getLocalizacaoEtamanho() {
		return localizacaoEtamanho;
	}

	public void setLocalizacaoEtamanho(Rectangle localizacaoEtamanho) {
		this.localizacaoEtamanho = localizacaoEtamanho;
	}

	@Override
	public List<List<String>> tabela() {
		return Arrays.asList(Arrays.asList("", "APP"),
				Arrays.asList("Data Hora captura", String.valueOf(dataHoraCaptura)),
				Arrays.asList("Nome", Optional.ofNullable(nome).orElse("N/A")),
				Arrays.asList("Comando", Optional.ofNullable(comando).orElse("N/A")),
				Arrays.asList("Data hora captura",
						Optional.ofNullable(dataHoraCaptura).map(Object::toString).orElse("N/A")),
				Arrays.asList("PID", Optional.ofNullable(pid).map(Object::toString).orElse("N/A")),
				Arrays.asList("Id Janela", Optional.ofNullable(janelaID).map(Object::toString).orElse("N/A")),
				Arrays.asList("Localização e tamanho",
						Optional.ofNullable(localizacaoEtamanho).map(Object::toString).orElse("N/A")));
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