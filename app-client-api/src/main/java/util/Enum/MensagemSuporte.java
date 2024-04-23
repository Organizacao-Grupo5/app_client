package util.Enum;

public enum MensagemSuporte {
    CPU_CRITICO("CPU", 1, "Alerta! A CPU do seu sistema está em um estado crítico. Medidas imediatas devem ser tomadas para evitar danos permanentes ao sistema.", "Recomenda-se verificar a temperatura da CPU, limpar os dissipadores de calor e verificar se há processos em execução que estejam consumindo muitos recursos."),
    CPU_ALERTA("CPU", 2, "Atenção! A CPU do seu sistema está operando próximo ao limite. Recomenda-se monitorar de perto e considerar otimizações para evitar problemas de desempenho.", "Você pode verificar o Gerenciador de Tarefas para identificar processos que estejam consumindo muitos recursos e encerrá-los, se possível."),
    CPU_IDEAL("CPU", 3, "A CPU do seu sistema está operando dentro de limites ideais. Não são necessárias ações adicionais no momento.", ""),

    HDD_CRITICO("HDD", 1, "Alerta! O disco rígido do seu sistema está em um estado crítico. Faça backup imediatamente e substitua o disco rígido.", "Recomenda-se fazer backup dos dados importantes o mais rápido possível e substituir o disco rígido defeituoso."),
    HDD_ALERTA("HDD", 2, "Atenção! O espaço do disco rígido do seu sistema está próximo do limite. Libere espaço ou considere expandir a capacidade de armazenamento.", "Você pode remover arquivos desnecessários ou transferir dados para um dispositivo de armazenamento externo para liberar espaço."),
    HDD_IDEAL("HDD", 3, "O disco rígido do seu sistema está com espaço disponível suficiente. Não são necessárias ações adicionais no momento.", ""),

    RAM_CRITICO("RAM", 1, "Alerta! A memória RAM do seu sistema está em um estado crítico. Isso pode resultar em falhas de sistema. Considere fechar aplicativos não utilizados.", "Encerre aplicativos desnecessários e reinicie o sistema para liberar memória."),
    RAM_ALERTA("RAM", 2, "Atenção! A memória RAM do seu sistema está quase totalmente utilizada. Considere fechar aplicativos ou adicionar mais memória RAM.", "Você pode verificar o Gerenciador de Tarefas para identificar aplicativos que estejam consumindo muita memória e encerrá-los."),
    RAM_IDEAL("RAM", 3, "A memória RAM do seu sistema está com uso dentro dos limites ideais. Não são necessárias ações adicionais no momento.", ""),

    GPU_CRITICO("GPU", 1, "Alerta! A GPU do seu sistema está em um estado crítico. Isso pode afetar a renderização gráfica e causar instabilidade.", "Verifique se há atualizações de driver disponíveis para sua placa gráfica e monitore a temperatura durante o uso."),
    GPU_ALERTA("GPU", 2, "Atenção! A GPU do seu sistema está operando próximo ao limite. Monitorar de perto e considerar otimizações para evitar problemas de desempenho.", "Reduza as configurações gráficas em jogos ou aplicativos que estejam causando alta carga na GPU."),
    GPU_IDEAL("GPU", 3, "A GPU do seu sistema está operando dentro de limites ideais. Não são necessárias ações adicionais no momento.", "");

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

    public static MensagemSuporte getByNumeroComponente(int numero, String componente){
        for (MensagemSuporte mensagem : values()){
            if (mensagem.numero == numero && mensagem.componente.contains(componente.toUpperCase())){
                return mensagem;
            }
        }
        return null;
    }
}
