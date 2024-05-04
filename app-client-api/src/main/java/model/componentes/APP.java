package model.componentes;

import app.integration.ShellIntegration;
import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class APP extends Componente {
	ShellIntegration shellIntegration = new ShellIntegration();

	private int idApp;
	private String comando;
	private String nome;
	private Rectangle localizacaoEtamanho;
	private LocalDateTime dataHoraCaptura;

	public APP() {
		super();
		this.dataHoraCaptura = LocalDateTime.now();
	}

	public APP(String nome, String comando, Double janelaID, Long pid, Rectangle localizacaoEtamanho) {
		modelo = pid.toString();
		fabricante = janelaID.toString();
		this.comando = comando;
		this.nome = nome;
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

	public String getComando() {
		return comando;
	}

	public void setComando(String comando){
		this.comando = comando;
	}

	public void setJanelaID(String comando) {
		this.comando = comando;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
				Arrays.asList("PID", Optional.ofNullable(modelo).map(Object::toString).orElse("N/A")),
				Arrays.asList("Uso de ram", Optional.ofNullable(dadoCaptura).map(Object::toString).orElse("N/A") + "MB"),
				Arrays.asList("Id Janela", Optional.ofNullable(fabricante).map(Object::toString).orElse("N/A")),
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

	@Override
	public String getUnidadeMedida() {
		return "MB";
	}

	public void setDadoCaptura() {
		dadoCaptura = shellIntegration.monitorarUsoDeRamDaJanela(modelo);
	}
}