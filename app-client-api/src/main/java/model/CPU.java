package model;

public class CPU {
    private Integer idCpu;
    private Integer numeroDeCpusLogicas;
    private Integer numeroDeCpusFisicas;
    private String microarquitetura;
    private String identificador;
    private String idCpuLooca;
    private String fabricante;
    private Double frequencia;
    private Integer numeroPacotesFisicos;
    private Double uso;
    private String nome;
    private Double temperatura;

    public CPU() {
    }

    public CPU(Integer numeroDeCpusLogicas, Integer numeroDeCpusFisicas, String microarquitetura, String identificador, String idCpuLooca, String fabricante, Double frequencia, Integer numeroPacotesFisicos, Double uso, String nome, Double temperatura) {
        this.numeroDeCpusLogicas = numeroDeCpusLogicas;
        this.numeroDeCpusFisicas = numeroDeCpusFisicas;
        this.microarquitetura = microarquitetura;
        this.identificador = identificador;
        this.idCpuLooca = idCpuLooca;
        this.fabricante = fabricante;
        this.frequencia = frequencia;
        this.numeroPacotesFisicos = numeroPacotesFisicos;
        this.uso = uso;
        this.nome = nome;
        this.temperatura = temperatura;
    }

    public Integer getIdCpu() {
        return idCpu;
    }

    public void setIdCpu(Integer idCpu) {
        this.idCpu = idCpu;
    }

    public Integer getNumeroDeCpusLogicas() {
        return numeroDeCpusLogicas;
    }

    public void setNumeroDeCpusLogicas(Integer numeroDeCpusLogicas) {
        this.numeroDeCpusLogicas = numeroDeCpusLogicas;
    }

    public Integer getNumeroDeCpusFisicas() {
        return numeroDeCpusFisicas;
    }

    public void setNumeroDeCpusFisicas(Integer numeroDeCpusFisicas) {
        this.numeroDeCpusFisicas = numeroDeCpusFisicas;
    }

    public String getMicroarquitetura() {
        return microarquitetura;
    }

    public void setMicroarquitetura(String microarquitetura) {
        this.microarquitetura = microarquitetura;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getIdCpuLooca() {
        return idCpuLooca;
    }

    public void setIdCpuLooca(String idCpuLooca) {
        this.idCpuLooca = idCpuLooca;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Double frequencia) {
        this.frequencia = frequencia;
    }

    public Integer getNumeroPacotesFisicos() {
        return numeroPacotesFisicos;
    }

    public void setNumeroPacotesFisicos(Integer numeroPacotesFisicos) {
        this.numeroPacotesFisicos = numeroPacotesFisicos;
    }

    public Double getUso() {
        return uso;
    }

    public void setUso(Double uso) {
        this.uso = uso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

}
