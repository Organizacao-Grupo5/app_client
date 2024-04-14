package core.sistema;

import java.text.DecimalFormat;

public class Conversor {

    public static double converterParaKB(long bytes) {
        return bytes / 1024.0;
    }

    public static double converterParaMB(long bytes) {
        return bytes / (1024.0 * 1024.0);
    }

    public static double converterParaGB(long bytes) {
        return bytes / (1024.0 * 1024.0 * 1024.0);
    }

    public static double converterCasasDecimais(double valor, int casasDecimais) {
        StringBuilder patternBuilder = new StringBuilder("0.");
        for (int i = 0; i < casasDecimais; i++) {
            patternBuilder.append("#");
        }
        DecimalFormat df = new DecimalFormat(patternBuilder.toString());
        String valorFormatado = df.format(valor);
        return Double.parseDouble(valorFormatado.replace(",", "."));
    }
}
