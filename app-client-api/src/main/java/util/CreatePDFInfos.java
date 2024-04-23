package util;

import app.system.Conversor;
import com.mysql.cj.util.StringUtils;
import util.Enum.MensagemSuporte;
import util.logs.Logger;

import java.util.List;
import java.util.Map;

public class CreatePDFInfos {
    private static final String TITULO = "TITULO";
    private static final String DESCRICAO = "DESCRICAO";
    private static final String PDF_PREFIX = "PDF";

    public String gerarLayoutPDF(List<List<String>> data) {
        Conversor conversor = new Conversor();
        StringBuilder displayAll = new StringBuilder();
        displayAll.append("\n -------- %s -------- \n".formatted(data.getFirst().get(1)));
        for (int i = 1; i < data.size(); i++) {
            if (!StringUtils.isNullOrEmpty(data.get(i).get(1))){
                displayAll.append(" ✅ %s: %s \n".formatted(data.get(i).get(0), (data.get(i).get(1))));
            } else{
                displayAll.append(" ❌ %s: %s \n".formatted(data.get(i).get(0), (data.get(i).get(1))));
            }
        }
        return displayAll.toString();
    }

}
