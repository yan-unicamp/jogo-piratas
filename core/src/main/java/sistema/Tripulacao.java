package sistema;

import java.util.ArrayList;
import java.util.List;

import entidades.Aliado;
import entidades.Item;
import entidades.Personagem;
import progressao.Recompensa;

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

    public void receberDinheiro(int valor) { 
        this.dinheiro += valor;
    }

    public Boolean gastarDinheiro(int valor) {
        if (this.dinheiro >= valor) {
            this.dinheiro -= valor;
            return true;
        }
        return false;
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
}
