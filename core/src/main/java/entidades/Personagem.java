package entidades;

import java.util.ArrayList;
import java.util.List;

public abstract class Personagem {
    private String nome;
    private int vidaAtual;
    private int vidaMaxima;
    private int defesa;
    private int iniciativa;
    private List<Habilidade> habilidades;

    public Personagem(String nome, int vidaMaxima, int defesa, int iniciativa) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.defesa = defesa;
        this.iniciativa = iniciativa;
        this.habilidades = new ArrayList<>();
    }

    public void receberDano(int valor) { }

    public void curar(int valor) { }

    public void aumentarDefesaTemporaria(int valor) { }

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
}
