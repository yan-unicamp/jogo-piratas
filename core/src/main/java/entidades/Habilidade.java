package entidades;

public class Habilidade {
    private String nome;
    private TipoHabilidade tipo;
    private int valorPoder;

    public Habilidade(String nome, TipoHabilidade tipo, int valorPoder) {
        this.nome = nome;
        this.tipo = tipo;
        this.valorPoder = valorPoder;
    }

    /**
     * Aplica o efeito da habilidade no alvo.
     *   DANO   → chama alvo.receberDano(valorPoder)
     *   CURA   → chama alvo.curar(valorPoder)
     *   DEFESA → chama alvo.aumentarDefesaTemporaria(valorPoder)
     */
    public void executarAcao(Personagem alvo) {
        if (alvo == null) return;
        switch (tipo) {
            case DANO:
                alvo.receberDano(valorPoder);
                break;
            case CURA:
                alvo.curar(valorPoder);
                break;
            case DEFESA:
                alvo.aumentarDefesaTemporaria(valorPoder);
                break;
        }
    }

    public String getNome() { return nome; }
    public TipoHabilidade getTipo() { return tipo; }
    public int getValorPoder() { return valorPoder; }
}
