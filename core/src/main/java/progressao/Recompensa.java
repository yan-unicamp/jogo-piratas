package progressao;

public class Recompensa {
    private int dinheiroGanhado;
    private int experienciaGanhada;

    public Recompensa(int dinheiroGanhado, int experienciaGanhada) {
        this.dinheiroGanhado = dinheiroGanhado;
        this.experienciaGanhada = experienciaGanhada;
    }

    public int getDinheiroGanhado() { return dinheiroGanhado; }
    public int getExperienciaGanhada() { return experienciaGanhada; }
}
