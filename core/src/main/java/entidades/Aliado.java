package entidades;

public class Aliado extends Personagem {
    private int nivel;
    private int experiencia;

    public Aliado(String nome, int vidaMaxima, float defesa, int iniciativa, int nivel, int experiencia, String caminhoImagem) {
        super(nome, vidaMaxima, defesa, iniciativa, caminhoImagem);
        this.nivel = nivel;
        this.experiencia = experiencia;
    }

    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }
}
