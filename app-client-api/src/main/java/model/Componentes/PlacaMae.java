package model.Componentes;

import java.time.LocalDateTime;
import java.util.List;

public class PlacaMae extends Componente{

    private LocalDateTime dataHoraCaptura;

    public PlacaMae() {
        super();
        this.dataHoraCaptura = LocalDateTime.now();
    }

    public LocalDateTime getDataHoraCaptura() {
        return dataHoraCaptura;
    }

    public void setDataHoraCaptura(LocalDateTime dataHoraCaptura) {
        this.dataHoraCaptura = dataHoraCaptura;
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
