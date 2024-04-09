package objects;

public class Maquina {
    private String nomeMaquina;

    private String sistOperacional;

    private Cpu cpu;

    private Disco disco;

    private Ram ram;

    private Gpu gpu;

    public Maquina(String nomeMaquina, String sistOperacional) {
        this.nomeMaquina = nomeMaquina;
        this.sistOperacional = sistOperacional;
    }

    public Maquina() {
    }

    public String getNomeMaquina() {
        return nomeMaquina;
    }

    public void setNomeMaquina(String nomeMaquina) {
        this.nomeMaquina = nomeMaquina;
    }

    public String getSistOperacional() {
        return sistOperacional;
    }

    public void setSistOperacional(String sistOperacional) {
        this.sistOperacional = sistOperacional;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public Disco getDisco() {
        return disco;
    }

    public void setDisco(Disco disco) {
        this.disco = disco;
    }

    public Ram getRam() {
        return ram;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public Gpu getGpu() {
        return gpu;
    }

    public void setGpu(Gpu gpu) {
        this.gpu = gpu;
    }

    @Override
    public String toString() {
        return "Maquina{" +
                "nomeMaquina='" + nomeMaquina + '\'' +
                ", sistOperacional='" + sistOperacional + '\'' +
                ", cpu=" + cpu +
                ", disco=" + disco +
                ", ram=" + ram +
                ", gpu=" + gpu +
                '}';
    }

    private String formatarBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";
        } else if (bytes < 1048576) {
            return String.format("%.2f", bytes / 1024.0) + " KB";
        } else if (bytes < 1073741824) {
            return String.format("%.2f", bytes / 1048576.0) + " MB";
        } else {
            return String.format("%.2f", bytes / 1073741824.0) + " GB";
        }
    }
}
