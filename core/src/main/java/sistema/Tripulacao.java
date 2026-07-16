package sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entidades.Aliado;
import entidades.Item;
import entidades.Personagem;
import entidades.Inimigo;

public class Tripulacao {
    private int dinheiro;
    private List<Aliado> aliados;
    private List<Aliado> aliadosAtivos;
    private List<Item> itens;

    public Tripulacao() {
        this.dinheiro = 0;
        this.aliados = new ArrayList<>();
        this.aliadosAtivos = new ArrayList<>();
        this.itens = new ArrayList<>();
    }

    /**
     * Recebe a recompensa de uma batalha vencida.
     * O ouro é somado ao total da tripulação.
     * A XP é dividida igualmente entre os aliados VIVOS.
     */
    public void receberRecompensa(List<Inimigo> inimigosDerrotados) {
        int totalDinheiro = 0;
        int totalXp = 0;
        
        for (Inimigo inimigo : inimigosDerrotados) {
            totalDinheiro += inimigo.getRecompensaDinheiro();
            totalXp += inimigo.getRecompensaExperiencia();
            if (inimigo.getRecompensaItem() != null) {
                receberItem(inimigo.getRecompensaItem());
            }
        }

        this.dinheiro += totalDinheiro;

        List<Aliado> aliadosVivos = getAliadosVivos();
        if (!aliadosVivos.isEmpty() && totalXp > 0) {
            int xpPorAliado = totalXp / aliadosVivos.size();
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

    public void receberDinheiro(int valor) {
        this.dinheiro += valor;
    }

    public void adicionarAliado(Aliado aliado) {
        if (!this.aliados.contains(aliado)) {
            this.aliados.add(aliado);
        }
    }

    public void removerAliado(Aliado aliado) {
        this.aliados.remove(aliado);
        this.aliadosAtivos.remove(aliado);
    }

    public void adicionarAliadoAtivo(Aliado aliado) {
        if (this.aliados.contains(aliado) && !this.aliadosAtivos.contains(aliado)) {
            this.aliadosAtivos.add(aliado);
        }
    }

    public void removerAliadoAtivo(Aliado aliado) {
        this.aliadosAtivos.remove(aliado);
    }

    public void receberItem(Item item) {
        this.itens.add(item);
    }

    public void removerItem(Item item) {
        this.itens.remove(item);
    }

    public void usarItem(Item item, Personagem alvo) {
        if (this.itens.contains(item)) {
            item.usar(alvo);
            removerItem(item);
        }
    }

    public int getDinheiro() { return dinheiro; }
    public List<Aliado> getAliados() { return aliados; }
    public List<Aliado> getAliadosAtivos() { return aliadosAtivos; }
    public List<Item> getItens() { return itens; }

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
}
