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

    public void receberRecompensa(Recompensa loot) {
        this.dinheiro += loot.getDinheiroGanhado();
        int xpPorAliado = loot.getExperienciaGanhada() / aliados.size();
        for (Aliado aliado : aliados) {
            aliado.ganharExperiencia(xpPorAliado);
        }
    }

    public boolean gastarDinheiro(int valor) {
        if (this.dinheiro >= valor) {
            this.dinheiro -= valor;
            return true;
        } else {
            return false;
        }
    }

    public int getDinheiro() {
        return dinheiro;
    }

    public List<Aliado> getAliados() {
        return aliados;
    }
}
