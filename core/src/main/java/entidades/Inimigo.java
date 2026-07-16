package entidades;

import progressao.Recompensa;

public class Inimigo extends Personagem {
    private Recompensa recompensa;

    public Inimigo(String nome, int vidaMaxima, float defesa, int iniciativa, Recompensa recompensa, String caminhoImagem) {
        super(nome, vidaMaxima, defesa, iniciativa, caminhoImagem);
        this.recompensa = recompensa;
    }

    public Recompensa getRecompensa() { return recompensa; }
}
