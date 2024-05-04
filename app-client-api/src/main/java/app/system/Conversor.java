package app.system;

import java.text.DecimalFormat;

public class Conversor {

	public double converterParaKB(long bytes) {
		return bytes / 1024.0;
	}

	public double converterParaMB(long bytes) {
		return bytes / (1024.0 * 1024.0);
	}

	public double converterParaGB(long bytes) {
		return bytes / (1024.0 * 1024.0 * 1024.0);
	}

	public double converterCasasDecimais(double valor, int casasDecimais) {
		StringBuilder patternBuilder = new StringBuilder("0.");
		for (int i = 0; i < casasDecimais; i++) {
			patternBuilder.append("#");
		}
		DecimalFormat df = new DecimalFormat(patternBuilder.toString());
		String valorFormatado = df.format(valor);
		return Double.parseDouble(valorFormatado.replace(",", "."));
	}

	public Double formatarBytes(long bytes) {
		if (bytes < 1024) {
			return Double.parseDouble(String.valueOf(bytes));
		} else if (bytes < 1048576) {
			return bytes / 1024.0;
		} else if (bytes < 1073741824) {
			return bytes / 1048576.0;
		} else {
			return bytes / 1073741824.0;
		}
	}

	public Double convertePorcentagem(double valorTotal, Double valorAtual) {
		double porcentagem = (valorAtual / valorTotal) * 100;
		return converterCasasDecimais(porcentagem, 1);
	}

	public double converterBytesParaDouble(byte[] bytes) {
		if (bytes.length != 8) {
			throw new IllegalArgumentException("O array de bytes deve ter 8 elementos (tamanho de um double)");
		}

		long valorLong = 0;
		for (int i = 0; i < 8; i++) {
			valorLong |= ((long) bytes[i] & 0xff) << (i * 8);
		}

		return Double.longBitsToDouble(valorLong);
	}

	public double converterKBparaMB(double valorKB) {
		return valorKB / 1024;
	}

	public Double converterSegundosParaHoras(long segundos) {
		return (double) segundos / 3600;
	}
}
