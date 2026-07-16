package entidades;

public class Aliado extends Personagem {
    private int nivel;
    private int experiencia;
    private java.util.Map<Integer, java.util.function.Supplier<Habilidade>> habilidadesDesbloqueaveis = new java.util.HashMap<>();

    public Aliado(String nome, int vidaMaxima, float defesa, int iniciativa, int nivel, int experiencia, String caminhoImagem) {
        super(nome, vidaMaxima, defesa, iniciativa, caminhoImagem);
        this.nivel = nivel;
        this.experiencia = experiencia;
    }

    public void adicionarHabilidadeDesbloqueavel(int nivelDesbloqueio, java.util.function.Supplier<Habilidade> supplier) {
        habilidadesDesbloqueaveis.put(nivelDesbloqueio, supplier);
    }

    public int getNivel() { return nivel; }
    public int getExperiencia() { return experiencia; }

    public java.util.List<Habilidade> ganharExperiencia(int xp) {
        java.util.List<Habilidade> destravadas = new java.util.ArrayList<>();
        this.experiencia += xp;
        while (this.experiencia >= 100 * this.nivel) {
            if (ganharNivel()) {
                if (habilidadesDesbloqueaveis.containsKey(this.nivel)) {
                    destravadas.add(habilidadesDesbloqueaveis.get(this.nivel).get());
                }
            }
        }
        return destravadas;
    }

    private boolean ganharNivel() {
        if (this.experiencia >= 100 * this.nivel) {
            this.experiencia -= 100 * this.nivel;
            this.nivel++;
            System.out.println(this.getNome() + " subiu para o nível " + this.nivel + "!");
            subirStatus();
            return true;
        }
        return false;
    }

    public void subirStatus() {
        vidaMaxima += 10;
        defesa -= 0.05f;
        iniciativa += 2;
        vidaAtual += 10;
        defesaAtual = defesa;
    }


}
