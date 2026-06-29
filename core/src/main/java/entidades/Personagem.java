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

    /**
     * Aplica dano ao personagem, respeitando a defesa.
     * Implementação: Membro 1 (Fase 2).
     */
    public void receberDano(int valor) { }

    /**
     * Cura o personagem, limitado à vidaMaxima.
     * Implementação: Membro 1 (Fase 2).
     */
    public void curar(int valor) { }

    public void aumentarDefesaTemporaria(int valor) { }

    /** @return true se o personagem ainda está com vida. */
    public boolean estaVivo() {
        return this.vidaAtual > 0;
    }

    /** Adiciona uma habilidade ao repertório do personagem. */
    public void adicionarHabilidade(Habilidade habilidade) {
        this.habilidades.add(habilidade);
    }

    /**
     * Ponto centralizado de mutação de vida. Usado por receberDano() e curar().
     * Garante que vidaAtual nunca ultrapasse vidaMaxima nem fique negativa.
     */
    protected void setVidaAtual(int novaVida) {
        this.vidaAtual = Math.max(0, Math.min(novaVida, this.vidaMaxima));
    }

    public String getNome() { return nome; }
    public int getVidaAtual() { return vidaAtual; }
    public int getVidaMaxima() { return vidaMaxima; }
    public int getDefesa() { return defesa; }
    public int getIniciativa() { return iniciativa; }
    public List<Habilidade> getHabilidades() { return habilidades; }
}
