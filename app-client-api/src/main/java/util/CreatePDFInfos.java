package util;

import app.system.Conversor;
import util.Enum.MensagemSuporte;
import util.logs.Logger;

import java.util.List;
import java.util.Map;

public class CreatePDFInfos {
    private static final String TITULO = "TITULO";
    private static final String DESCRICAO = "DESCRICAO";
    private static final String PDF_PREFIX = "PDF";

    public void gerarLayoutPDF(Map<String, Object> mapaComponentes) {
        Conversor conversor = new Conversor();
        StringBuilder displayAll = new StringBuilder();
        for (String componente : mapaComponentes.keySet()) {
            StringBuilder layout = new StringBuilder();
            layout.append(String.format("\n\n     ----- %s -----     \n", componente));

            Object componenteData = mapaComponentes.get(componente);

            if (componenteData instanceof Map<?, ?>) {
                Map<?, ?> componenteDataMap = (Map<?, ?>) componenteData;
                for (Object key : componenteDataMap.keySet()) {
                    layout.append(String.format("%s: %s\n", key, componenteDataMap.get(key)));
                }

                if (componenteDataMap.containsKey(DESCRICAO)) {
                    List<Double> valores = (List<Double>) componenteDataMap.get(DESCRICAO);
                    Double atual = valores.get(0);
                    Double total = valores.get(1);
                    Double porcentagem = conversor.convertePorcentagem(total, atual);
                    int status = porcentagem >= 85.0 ? 1 : porcentagem >= 65.0 ? 2 : 3;
                    String descricaoFormatada = String.format("Descrição: \n%s\n", MensagemSuporte.getByNumeroComponente(status, componente).getMensagem());
                    layout.append(descricaoFormatada);
                    layout.append(String.format("%% Atual: %.2f%%\n", atual));
                    layout.append(String.format("%% Restante: %.2f%%\n", 100.0 - atual));
                }
            } else {
                Logger.logWarning("Dados do componente inválidos: "+ componente);
            }

            mapaComponentes.put(PDF_PREFIX + componente, layout.toString());
        }
    }

}
