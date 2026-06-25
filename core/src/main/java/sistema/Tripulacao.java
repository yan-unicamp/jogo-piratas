package sistema;

import java.util.ArrayList;
import java.util.List;

import entidades.Aliado;
import progressao.Recompensa;

public class Tripulacao {
    private int dinheiro;
    private List<Aliado> aliados;

    public Tripulacao() {
        this.dinheiro = 0;
        this.aliados = new ArrayList<>();
    }

    public void receberRecompensa(Recompensa loot) { }

    public void gastarDinheiro(int valor) { }

    public int getDinheiro() { return dinheiro; }
    public List<Aliado> getAliados() { return aliados; }
}
