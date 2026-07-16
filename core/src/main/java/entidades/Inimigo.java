package entidades;

public class Inimigo extends Personagem {
    private int recompensaDinheiro;
    private int recompensaExperiencia;
    private Item recompensaItem;

    public Inimigo(String nome, int vidaMaxima, float defesa, int iniciativa, int recompensaDinheiro, int recompensaExperiencia) {
        super(nome, vidaMaxima, defesa, iniciativa);
        this.recompensaDinheiro = recompensaDinheiro;
        this.recompensaExperiencia = recompensaExperiencia;
    }

    public Inimigo(String nome, int vidaMaxima, float defesa, int iniciativa, int recompensaDinheiro, int recompensaExperiencia, Item recompensaItem) {
        super(nome, vidaMaxima, defesa, iniciativa);
        this.recompensaDinheiro = recompensaDinheiro;
        this.recompensaExperiencia = recompensaExperiencia;
        this.recompensaItem = recompensaItem;
    }

    public int getRecompensaDinheiro() { return recompensaDinheiro; }
    public int getRecompensaExperiencia() { return recompensaExperiencia; }
    public Item getRecompensaItem() { return recompensaItem; }
}
