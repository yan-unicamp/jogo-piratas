package entidades;

import java.util.ArrayList;
import java.util.List;

public abstract class Personagem {
    private String nome;
    private float vidaAtual;
    private int vidaMaxima;
    private float defesa;
    private float defesaAtual; 
    private int iniciativa;
    private List<Habilidade> habilidades;

    public Personagem(String nome, int vidaMaxima, float defesa, int iniciativa) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.defesa = defesa;
        this.defesaAtual = defesa;
        this.iniciativa = iniciativa;
        this.habilidades = new ArrayList<>();
    }

    public void receberDano(float valor) {
        float dano = valor * defesaAtual; 
        this.vidaAtual -= dano;
        if (this.vidaAtual < 1) this.vidaAtual = 0; 
    }

    public void curar(float valor) {
        this.vidaAtual += valor;
        if (this.vidaAtual > this.vidaMaxima) {
            this.vidaAtual = this.vidaMaxima;
        }
    }

    public void aumentarDefesaTemporaria(float valor) { 
        float novadefesa = defesa - valor;
        this.defesaAtual = novadefesa;
    }

    public String getNome() { 

        return nome; 
    }
    public float getVidaAtual() { 

        return vidaAtual; 
    }
    public float getVidaMaxima() { 
        
        return vidaMaxima; 
    }
    public float getDefesa() { 
        
        return defesa; 
    }
    public int getIniciativa() { 
        
        return iniciativa; 
    }

    public List<Habilidade> getHabilidades() { 
        
        return habilidades; 
    }

    public void adicionarHabilidade(Habilidade habilidade) { 
        this.habilidades.add(habilidade);
    }
}
