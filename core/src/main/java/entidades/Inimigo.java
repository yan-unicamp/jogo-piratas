package entidades;

public class Inimigo extends Personagem {
    private int recompensaDinheiro;
    private int recompensaExperiencia;
    private Item recompensaItem;

    public Inimigo(String nome, int vidaMaxima, float defesa, int iniciativa, int recompensaDinheiro, int recompensaExperiencia, String caminhoImagem) {
        super(nome, vidaMaxima, defesa, iniciativa, caminhoImagem);
        this.recompensaDinheiro = recompensaDinheiro;
        this.recompensaExperiencia = recompensaExperiencia;
    }

    public int getRecompensaDinheiro() { return recompensaDinheiro; }
    public int getRecompensaExperiencia() { return recompensaExperiencia; }
    public Item getRecompensaItem() { return recompensaItem; }
}
