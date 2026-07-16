package entidades;

import java.util.ArrayList;
import java.util.List;

public abstract class Personagem {
    protected String nome;
    protected int vidaAtual;
    protected int vidaMaxima;
    protected float defesa;
    protected float defesaAtual; 
    protected int iniciativa;
    protected List<Habilidade> habilidades;
    protected String caminhoImagem;

    public Personagem(String nome, int vidaMaxima, float defesa, int iniciativa, String caminhoImagem) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.defesa = defesa;
        this.defesaAtual = defesa;
        this.iniciativa = iniciativa;
        this.habilidades = new ArrayList<>();
        this.caminhoImagem = caminhoImagem;
    }


    public void receberDano(int valor) {
        int dano = (int)(valor * defesaAtual);
        this.vidaAtual -= dano;
        if (this.vidaAtual < 1)
            this.vidaAtual = 0;
    }

    public void curar(int valor) {
        this.vidaAtual += valor;
        if (this.vidaAtual > this.vidaMaxima) {
            this.vidaAtual = this.vidaMaxima;
        }
    }

    public void aumentarDefesaTemporaria(float valor) {
        float novadefesa = defesa - valor;
        if (novadefesa < 0)
            novadefesa = 0; // Impede que a defesa fique negativa (o que faria o dano curar o personagem)
        this.defesaAtual = novadefesa;
    }

    public void resetarDefesa() {
        this.defesaAtual = this.defesa;
    }

    public boolean estaVivo() {
        return this.vidaAtual > 0;
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

    public float getDefesa() {
        return defesaAtual;
    }

    public int getIniciativa() {

        return iniciativa;
    }

    public List<Habilidade> getHabilidades() {

        return habilidades;
    }

    public String getCaminhoImagem() { 
        return caminhoImagem; 
    }

    public void adicionarHabilidade(Habilidade habilidade) { 
        this.habilidades.add(habilidade);
    }
}