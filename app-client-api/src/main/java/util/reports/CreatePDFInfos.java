package util.reports;

import app.system.Conversor;
import com.mysql.cj.util.StringUtils;
import util.Enum.MensagemSuporte;

import java.util.List;

public class CreatePDFInfos {

	public static String gerarLayoutPDF(List<List<String>> data) {
		Conversor conversor = new Conversor();
		StringBuilder displayAll = new StringBuilder();
		displayAll.append("\n -------- %s -------- \n".formatted(String.valueOf(data.get(0).get(1))));
		for (int i = 1; i < data.size(); i++) {
			if (!StringUtils.isNullOrEmpty(data.get(i).get(1))) {
				displayAll.append(" - %s: %s \n".formatted(data.get(i).get(0), (data.get(i).get(1))));
			} else {
				displayAll.append(" - %s: NÃO FOI POSSÍVEL VERIFICAR \n".formatted(data.get(i).get(0)));
			}
			if (data.get(i).get(0).equalsIgnoreCase("VALOR")) {
				Double max = Double.valueOf(data.get(i).get(1).replace(",", "."));
				Double atual = Double.valueOf(data.get(i).get(2).replace(",", "."));
				String unidadeMedida = data.get(i).get(3);
				Double porcentagemAtual = conversor.converterCasasDecimais(conversor.convertePorcentagem(max, atual),
						1);

				displayAll.append("""
						 - Porcentagem utilizada: %.2f%%
						 - Porcentagem não utilizada: %.2f%%
						 - Total: %f %s
						 - Atual: %f %s
						 - Livre: %f %s
						""".formatted(porcentagemAtual, 100 - porcentagemAtual, max, unidadeMedida, atual,
						unidadeMedida, max - atual, unidadeMedida));

				displayAll.append("\nDESCRIÇÃO: " + MensagemSuporte.getByNumeroComponente(
						porcentagemAtual >= 75.0 ? 1 : porcentagemAtual >= 50.0 ? 2 : 3,
						data.get(0).get(1).toUpperCase()) + "\n");

			}
		}
		return displayAll.toString();
	}

}
