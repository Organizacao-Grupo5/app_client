package model.componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Componente {
	protected Integer idComponente;
	protected String componente;
	protected String modelo;
	protected String fabricante;
	protected Integer fkMaquina;
	protected String unidadeMedida;
	protected Double dadoCaptura;
	protected LocalDateTime dataCaptura;

	public Componente() {

	}

	public List<List<String>> tabela() {
		return Arrays.asList(Arrays.asList("", componente),
		Arrays.asList("Modelo", modelo),
		Arrays.asList("Fabricante", fabricante)
		);
	}

	public String tabelaConvert() {
		return TablePrinter.printTable(tabela());
	}

	public String pdfLayout() {
		return CreatePDFInfos.gerarLayoutPDF(tabela());
	}

	public Componente(Integer idComponente, String componente, String modelo, String fabricante, Integer fkMaquina,
			Double dadoCaptura) {
		this.idComponente = idComponente;
		this.componente = componente;
		this.modelo = modelo;
		this.fabricante = fabricante;
		this.fkMaquina = fkMaquina;
		this.dadoCaptura = dadoCaptura;
	}

	public Componente(String fabricante, String modelo, String componente) {
		this.fabricante = fabricante;
		this.modelo = modelo;
		this.componente = componente;
	}

	public Integer getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Integer idComponente) {
		this.idComponente = idComponente;
	}

	public String getComponente() {
		return componente;
	}

	public void setComponente(String componente) {
		this.componente = componente;
	}

	public String getModelo() {
		return modelo != null ? modelo : "N/A";
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getFabricante() {
		return fabricante != null ? fabricante : "N/A";
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public Integer getFkMaquina() {
		return fkMaquina;
	}

	public void setFkMaquina(Integer fkMaquina) {
		this.fkMaquina = fkMaquina;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Double getDadoCaptura() {
		return dadoCaptura;
	}

	public void setDadoCaptura(Double dadoCaptura) {
		this.dadoCaptura = dadoCaptura;
	}

	public LocalDateTime getDataCaptura() {
		return dataCaptura;
	}

	public void setDataCaptura(LocalDateTime dataCaptura) {
		this.dataCaptura = dataCaptura;
	}
}
