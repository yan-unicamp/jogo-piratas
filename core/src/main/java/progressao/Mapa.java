package progressao;

public class Mapa {
    private int etapaAtual;
    private int capitulo;
    private final int MAX_ETAPAS_POR_CAPITULO = 10; // Exemplo de tamanho do capitulo
    private NoMapa noAtual;

    public Mapa() {
        this.etapaAtual = 1;
        this.capitulo = 1;
    }

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
