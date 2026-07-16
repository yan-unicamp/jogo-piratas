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

    public void ganharExperiencia(int xp) {
        this.experiencia += xp;
        while (this.experiencia >= 100 * this.nivel) {
            ganharNivel();
        }
    }

    private boolean ganharNivel() {
        if (this.experiencia >= 100 * this.nivel) {
            this.experiencia -= 100 * this.nivel;
            this.nivel++;
            System.out.println(this.getNome() + " subiu para o nível " + this.nivel + "!");
            // Aqui você pode adicionar aumento de atributos (vidaMaxima, etc) no futuro
            return true;
        }
        return false;
    }

    public void subirStatus() {
        vidaMaxima += 10;
        defesa -= 0.05f;
        iniciativa += 2;
        vidaAtual = vidaMaxima;
        defesaAtual = defesa;
    }


}
