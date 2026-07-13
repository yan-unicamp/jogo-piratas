package progressao;

public class Mapa {
    private int etapaAtual;
    private NoMapa noAtual;

    public Mapa() {
        this.etapaAtual = 1;
    }

    public void gerarProximosNos() { }

    public void avancarParaNo(NoMapa proximo) { }

    public int getEtapaAtual() { return etapaAtual; }
    public NoMapa getNoAtual() { return noAtual; }
}
