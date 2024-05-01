package model;

import model.componentes.Componente;
import util.reports.CreatePDFInfos;
import util.reports.PDFGenerator;
import util.reports.TablePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Maquina {
	private Integer idMaquina;
	private List<String> ipv4;
	private Usuario usuario;
	private List<Componente> componentes;

	public Maquina() {
		componentes = new ArrayList<>();
		this.ipv4 = new ArrayList<>();
	}

	public Maquina(Integer idMaquina, Usuario usuario) {
		this.idMaquina = idMaquina;
		this.ipv4 = new ArrayList<>();
		this.usuario = usuario;
		this.componentes = new ArrayList<>();
	}

	public int getIdMaquina() {
		return idMaquina;
	}

	public void setIdMaquina(int idMaquina) {
		this.idMaquina = idMaquina;
	}

	public List<String> getIpv4() {
		return ipv4;
	}

	public void setIpv4(List<String> ipv4) {
		this.ipv4 = ipv4;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Componente> getComponentes() {
		return componentes;
	}

	public void setComponentes(List<Componente> componentes) {
		this.componentes = componentes;
	}

	public String exibirTabelaComponentes() {
		StringBuilder stringBuilder = new StringBuilder();
		componentes.forEach(componente -> {
			String tablePrinter = componente.tabelaConvert();
			stringBuilder.append(tablePrinter);
		});

		return stringBuilder.toString();
	}

	public String layoutPdfComponentes() {
		StringBuilder stringBuilder = new StringBuilder();
		componentes.forEach(componente -> {
			String tablePrinter = componente.pdfLayout();
			stringBuilder.append(tablePrinter);
		});

		return stringBuilder.toString();
	}
}
