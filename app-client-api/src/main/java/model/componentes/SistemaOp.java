package model.componentes;

import util.reports.CreatePDFInfos;
import util.reports.TablePrinter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SistemaOp extends Componente {
	private String arquitetura;
	private String inicializado;
	private String permissao;
	private String tempoDeAtividade;
	private LocalDateTime dataCaptura;

	public SistemaOp() {
		dataCaptura = LocalDateTime.now();
	}

	public SistemaOp(String arquitetura, String inicializado, String permissao, String tempoDeAtividade) {
		super();
		this.arquitetura = arquitetura;
		this.inicializado = inicializado;
		this.permissao = permissao;
		this.tempoDeAtividade = tempoDeAtividade;
		this.dataCaptura = LocalDateTime.now();
	}

	public String getPermissao() {
		return permissao;
	}

	public LocalDateTime getDataHoraCaptura() {
		return dataCaptura;
	}

	public void setDataHoraCaptura(LocalDateTime dataCaptura) {
		this.dataCaptura = dataCaptura;
	}

	public String getArquitetura() {
		return arquitetura;
	}

	public void setArquitetura(String arquitetura) {
		this.arquitetura = arquitetura;
	}

	public String getInicializado() {
		return inicializado;
	}

	public void setInicializado(String inicializado) {
		this.inicializado = inicializado;
	}

	public String isPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}

	public String getTempoDeAtividade() {
		return tempoDeAtividade;
	}

	public void setTempoDeAtividade(String tempoDeAtividade) {
		this.tempoDeAtividade = tempoDeAtividade;
	}

	public List<List<String>> tabela() {
		return Arrays.asList(Arrays.asList("", "Sistema Operacional"),
				Arrays.asList("Data Hora captura", String.valueOf(dataCaptura)),
				Arrays.asList("Nome Sistema", Optional.ofNullable(modelo).orElse("N/A")),
				Arrays.asList("Fabricante", Optional.ofNullable(fabricante).orElse("N/A")),
				Arrays.asList("Arquitetura", Optional.ofNullable(arquitetura).orElse("N/A")),
				Arrays.asList("Inicializado",
						Optional.ofNullable(inicializado).map(Object::toString).orElse("N/A")),
				Arrays.asList("Permissão",
						Optional.ofNullable(permissao).map(Object::toString).orElse("N/A")),
				Arrays.asList("Tempo de atividade",
						Optional.ofNullable(tempoDeAtividade).map(Object::toString).orElse("N/A")));
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
