package sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entidades.Aliado;
import progressao.Recompensa;

public class Tripulacao {
    private int dinheiro;
    private List<Aliado> aliados;

    public Tripulacao() {
        this.dinheiro = 0;
        this.aliados = new ArrayList<>();
    }

    /**
     * Recebe a recompensa de uma batalha vencida.
     * O ouro é somado ao total da tripulação.
     * A XP é dividida igualmente entre os aliados VIVOS.
     */
    public void receberRecompensa(Recompensa loot) {
        this.dinheiro += loot.getDinheiroGanhado();

        List<Aliado> aliadosVivos = getAliadosVivos();
        if (!aliadosVivos.isEmpty()) {
            int xpPorAliado = loot.getExperienciaGanhada() / aliadosVivos.size();
            for (Aliado aliado : aliadosVivos) {
                aliado.ganharExperiencia(xpPorAliado);
            }
        }
    }

    /**
     * Tenta gastar uma quantia de dinheiro.
     * @return true se o saldo era suficiente e o valor foi debitado; false caso contrário.
     */
    public boolean gastarDinheiro(int valor) {
        if (this.dinheiro >= valor) {
            this.dinheiro -= valor;
            return true;
        }
        return false;
    }

    /** Adiciona um aliado à tripulação. */
    public void adicionarAliado(Aliado aliado) {
        this.aliados.add(aliado);
    }

    /** Remove um aliado da tripulação. @return true se encontrado e removido. */
    public boolean removerAliado(Aliado aliado) {
        return this.aliados.remove(aliado);
    }

    /** @return lista apenas com aliados que ainda têm vida (estaVivo() == true). */
    public List<Aliado> getAliadosVivos() {
        return aliados.stream()
                .filter(Aliado::estaVivo)
                .collect(Collectors.toList());
    }

    /** @return true se todos os aliados foram derrotados — condição de Game Over. */
    public boolean todosMortos() {
        return aliados.stream().noneMatch(Aliado::estaVivo);
    }

    public int getDinheiro() { return dinheiro; }
    public List<Aliado> getAliados() { return aliados; }
}
