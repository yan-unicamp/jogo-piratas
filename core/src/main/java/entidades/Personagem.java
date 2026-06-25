package entidades;

import java.util.ArrayList;
import java.util.List;

public abstract class Personagem {
    private String nome;
    private int vidaAtual;
    private int vidaMaxima;
    private int defesa;
    private int defesaAtual; 
    private int iniciativa;
    private List<Habilidade> habilidades;

    public Personagem(String nome, int vidaMaxima, int defesa, int iniciativa) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.defesa = defesa;
        this.defesaAtual = defesa;
        this.iniciativa = iniciativa;
        this.habilidades = new ArrayList<>();
    }

    public void receberDano(int valor) {
        int dano = valor * defesaAtual; 
        this.vidaAtual -= dano;
        if (this.vidaAtual < 0) this.vidaAtual = 0; 
     }

    public void curar(int valor) {
        this.vidaAtual += valor;
        if (this.vidaAtual > this.vidaMaxima) {
            this.vidaAtual = this.vidaMaxima;
        }
     }

    public void aumentarDefesaTemporaria(int valor) { 
        int novadefesa = defesa + valor;
        this.defesaAtual = novadefesa;
    }

    public String getNome() { 

        return nome; 
    }
    public int getVidaAtual() { 

        return vidaAtual; 
    }
    public int getVidaMaxima() { 
        
        return vidaMaxima; 
    }
    public int getDefesa() { 
        
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
