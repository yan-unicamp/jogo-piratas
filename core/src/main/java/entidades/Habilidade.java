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

    public void executarAcao(Personagem alvo) { }

    public String getNome() { return nome; }
    public TipoHabilidade getTipo() { return tipo; }
    public int getValorPoder() { return valorPoder; }
}
