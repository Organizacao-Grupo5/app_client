package util.Enum;

public enum MensagemSuporte {
	CPU_CRITICO("CPU", 1,
			"Alerta! A CPU do seu sistema está em um estado crítico. Medidas imediatas devem ser tomadas para evitar danos permanentes ao sistema.",
			"Verifique a temperatura da CPU, limpe os dissipadores de calor e feche processos que estejam consumindo muitos recursos.\n"),
	CPU_ALERTA("CPU", 2,
			"Atenção! A CPU do seu sistema está operando próximo ao limite. Recomenda-se monitorar de perto e considerar otimizações para evitar problemas de desempenho.",
			"Você pode verificar o Gerenciador de Tarefas para identificar processos que estejam consumindo muitos recursos e encerrá-los, se possível.\n"),
	CPU_IDEAL("CPU", 3,
			"A CPU do seu sistema está operando dentro de limites ideais. Não são necessárias ações adicionais no momento.",
			"Continue monitorando o desempenho para garantir que tudo permaneça dentro dos limites.\n"),

	HDD_CRITICO("VOLUME", 1,
			"Alerta! O disco rígido do seu sistema está em um estado crítico. Faça backup imediatamente.",
			"Faça backup dos dados importantes o mais rápido possível e substitua o disco rígido defeituoso.\n Apague arquivos que não são mais necessários ou mova eles para outro armazenamento.\n"),
	HDD_ALERTA("VOLUME", 2,
			"Atenção! O espaço do disco rígido do seu sistema está próximo do limite. Libere espaço ou considere expandir a capacidade de armazenamento.",
			"Remova arquivos desnecessários ou transfira dados para um dispositivo de armazenamento externo para liberar espaço.\n"),
	HDD_IDEAL("VOLUME", 3,
			"O disco rígido do seu sistema está com espaço disponível suficiente. Não são necessárias ações adicionais no momento.",
			"Mantenha o monitoramento do espaço disponível para evitar futuros problemas de armazenamento.\n"),

	RAM_CRITICO("RAM", 1,
			"Alerta! A memória RAM do seu sistema está em um estado crítico. Isso pode resultar em falhas de sistema. Considere fechar aplicativos não utilizados.",
			"Encerre aplicativos desnecessários e reinicie o sistema para liberar memória.\n"),
	RAM_ALERTA("RAM", 2,
			"Atenção! A memória RAM do seu sistema está quase totalmente utilizada. Considere fechar aplicativos ou adicionar mais memória RAM.",
			"Verifique o Gerenciador de Tarefas para identificar aplicativos que estejam consumindo muita memória e encerrá-los.\n"),
	RAM_IDEAL("RAM", 3,
			"A memória RAM do seu sistema está com uso dentro dos limites ideais. Não são necessárias ações adicionais no momento.",
			"Continue monitorando o uso da memória para garantir um desempenho eficiente.\n"),

	GPU_CRITICO("GPU", 1,
			"Alerta! A GPU do seu sistema está em um estado crítico. Isso pode afetar a renderização gráfica e causar instabilidade.",
			"Verifique se há atualizações de driver disponíveis para sua placa gráfica e monitore a temperatura durante o uso.\n"),
	GPU_ALERTA("GPU", 2,
			"Atenção! A GPU do seu sistema está operando próximo ao limite. Monitorar de perto e considerar otimizações para evitar problemas de desempenho.",
			"Reduza as configurações gráficas em jogos ou aplicativos que estejam causando alta carga na GPU.\n"),
	GPU_IDEAL("GPU", 3,
			"A GPU do seu sistema está operando dentro de limites ideais. Não são necessárias ações adicionais no momento.",
			"Mantenha-se atento aos sinais de superaquecimento ou desgaste da GPU para evitar problemas futuros.\n");

	private final String componente;
	private final int numero;
	private final String mensagem;
	private final String sugestao;

	MensagemSuporte(String componente, int numero, String mensagem, String sugestao) {
		this.componente = componente;
		this.numero = numero;
		this.mensagem = mensagem;
		this.sugestao = sugestao;
	}

	public String getComponente() {
		return componente;
	}

	public int getNumero() {
		return numero;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getSugestao() {
		return sugestao;
	}

	public MensagemSuporte getByNumero(int numero) {
		for (MensagemSuporte mensagem : values()) {
			if (mensagem.numero == numero) {
				return mensagem;
			}
		}
		return null;
	}

	public static String getByNumeroComponente(int numero, String componente) {
		for (MensagemSuporte mensagem : values()) {
			if (mensagem.numero == numero && mensagem.componente.contains(componente.toUpperCase())) {
				return mensagem.mensagem + "\n\nSUGESTÕES:\n" + mensagem.sugestao;
			}
		}
		return null;
	}
}
