package entidades;

public class Habilidade {
    private String nome;
    private TipoHabilidade tipo;
    private float valorPoder;

    public Habilidade(String nome, TipoHabilidade tipo, float valorPoder) {
        this.nome = nome;
        this.tipo = tipo;
        this.valorPoder = valorPoder;
    }

    public void executarAcao(Personagem atacante, Personagem alvo) {
        switch (tipo) {
            case DANO -> {
                float mult = atacante != null ? atacante.getMultiplicadorAtaque() : 1.0f;
                float extra = atacante != null ? atacante.getDanoFixoExtra() : 0f;
                alvo.receberDano(valorPoder * mult + extra);
            }
            case CURA -> alvo.curar(valorPoder);
            case DEFESA -> alvo.aumentarDefesaTemporaria(valorPoder);
        }
    }

    public String getNome() { 
        return nome; 
    }
    public TipoHabilidade getTipo() { 
        return tipo; 
    }
    public float getValorPoder() { 
        return valorPoder; 
    }
}
