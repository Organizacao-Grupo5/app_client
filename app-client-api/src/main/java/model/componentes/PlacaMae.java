package model.componentes;

import java.time.LocalDateTime;
import java.util.List;

public class PlacaMae extends Componente {

	private LocalDateTime dataCaptura;

	public PlacaMae() {
		super();
		this.dataCaptura = LocalDateTime.now();
	}

	public LocalDateTime getDataHoraCaptura() {
		return dataCaptura;
	}

	public void setDataHoraCaptura(LocalDateTime dataCaptura) {
		this.dataCaptura = dataCaptura;
	}

	@Override
	public List<List<String>> tabela() {
		return List.of();
	}

	@Override
	public String tabelaConvert() {
		return "";
	}

	@Override
	public String pdfLayout() {
		return "";
	}

	@Override
	public String getComponente() {
		return this.getClass().getSimpleName();
	}
}
