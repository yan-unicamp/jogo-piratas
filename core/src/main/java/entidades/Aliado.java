package entidades;

public class Aliado extends Personagem {
    private int nivel;
    private int experiencia;

    public Aliado(String nome, int vidaMaxima, float defesa, int iniciativa, int nivel, int experiencia) {
        super(nome, vidaMaxima, defesa, iniciativa);
        this.nivel = nivel;
        this.experiencia = experiencia;
    }

    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }

    public void ganharExperiencia(int xp) {
        this.experiencia += xp;
        System.out.println(getNome() + " ganhou " + xp + " de experiência! Total: " + this.experiencia);
    }
}
