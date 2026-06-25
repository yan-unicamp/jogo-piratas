package entidades;

import progressao.Recompensa;

public class Inimigo extends Personagem {
    private Recompensa recompensa;

    public Inimigo(String nome, int vidaMaxima, int defesa, int iniciativa, Recompensa recompensa) {
        super(nome, vidaMaxima, defesa, iniciativa);
        this.recompensa = recompensa;
    }

    public Recompensa getRecompensa() { return recompensa; }
}
