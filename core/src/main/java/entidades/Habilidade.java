package entidades;

public class Habilidade {
    private String nome;
    private TipoHabilidade tipo;
    private float valorPoder;
    private boolean selfcast;
    private boolean especial;

    public Habilidade(String nome, TipoHabilidade tipo, float valorPoder) {
        this(nome, tipo, valorPoder, false);
    }

    public Habilidade(String nome, TipoHabilidade tipo, float valorPoder, boolean selfcast) {
        this.nome = nome;
        this.tipo = tipo;
        this.valorPoder = valorPoder;
        this.selfcast = selfcast;
    }

    public boolean isSelfcast() { return selfcast; }
    
    public boolean isEspecial() { return especial; }
    public Habilidade setEspecial(boolean especial) {
        this.especial = especial;
        return this;
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
