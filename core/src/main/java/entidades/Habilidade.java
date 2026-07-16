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

    public void executarAcao(Personagem alvo) {
        switch (tipo) {
            case DANO -> alvo.receberDano((int)valorPoder);
            case CURA -> alvo.curar((int)valorPoder);
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
