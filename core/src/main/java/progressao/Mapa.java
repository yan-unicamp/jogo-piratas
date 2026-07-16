package progressao;

public class Mapa {
    private int etapaAtual;
    private int capitulo;
    private final int MAX_ETAPAS_POR_CAPITULO = 10; // Exemplo de tamanho do capitulo
    private NoMapa noAtual;
    private java.util.List<Ilha> ilhas;

    public Mapa() {
        this.etapaAtual = 0; // Alterado para 0 para que TelaMapa.java consiga exibir a primeira ilha (índice 0) corretamente
        this.capitulo = 1;
        this.ilhas = new java.util.ArrayList<>();
        for (IlhaEnum ie : IlhaEnum.values()) {
            this.ilhas.add(factories.IlhaFactory.criar(ie));
        }
    }

    public java.util.List<Ilha> getIlhas() { return ilhas; }

    public void gerarProximosNos() { }

    public void avancarParaNo(NoMapa proximo) { 
        this.noAtual = proximo;
        this.etapaAtual++;
        
        if (this.etapaAtual > MAX_ETAPAS_POR_CAPITULO) {
            this.etapaAtual = 1;
            this.capitulo++;
        }
    }

    public int getEtapaAtual() { return etapaAtual; }
    public int getCapitulo() { return capitulo; }
    public NoMapa getNoAtual() { return noAtual; }
}
