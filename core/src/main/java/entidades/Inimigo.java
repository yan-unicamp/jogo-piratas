package entidades;

public class Inimigo extends Personagem {
    private int recompensaDinheiro;
    private int recompensaExperiencia;
    private Item recompensaItem;
    private Aliado recompensaAliado;

    public Inimigo(String nome, int vidaMaxima, float defesa, int iniciativa, int recompensaDinheiro, int recompensaExperiencia, String caminhoImagem) {
        super(nome, vidaMaxima, defesa, iniciativa, caminhoImagem);
        this.recompensaDinheiro = recompensaDinheiro;
        this.recompensaExperiencia = recompensaExperiencia;
        this.recompensaAliado = null;
    }

    public int getRecompensaDinheiro() { return recompensaDinheiro; }
    public int getRecompensaExperiencia() { return recompensaExperiencia; }
    public Item getRecompensaItem() { return recompensaItem; }
    public Aliado getRecompensaAliado() { return recompensaAliado; }

    public void setRecompensaAliado(Aliado aliado) {
        this.recompensaAliado = aliado;
    }

    public void setRecompensaItem(Item item) {
        this.recompensaItem = item;
    }

    public Inimigo comAliado(Aliado aliado) {
        this.recompensaAliado = aliado;
        return this;
    }
}
