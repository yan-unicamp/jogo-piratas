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
        if (this.tipo == TipoHabilidade.DANO){
            alvo.receberDano(this.valorPoder);
        }
        else if (this.tipo == TipoHabilidade.CURA){
            alvo.curar(this.valorPoder);
        }
        else if (this.tipo == TipoHabilidade.DEFESA){
            alvo.aumentarDefesaTemporaria(this.valorPoder);
        }
     }

    public String getNome() { 
        return nome; 
    }
    public TipoHabilidade getTipo() { 
        return tipo; 
    }
    public int getValorPoder() { 
        return valorPoder; 
    }
}
