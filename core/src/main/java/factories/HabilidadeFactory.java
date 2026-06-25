package entidades; 

public class HabilidadeFactory {
    
    public static Habilidade criarAtaqueBasico(Personagem p) {
        return new Habilidade("Ataque Base", TipoHabilidade.DANO, 10 + (2*p.getNivel())); //botei pra variar com o nivel tambem
    }

    public static Habilidade criarDefesaHaki() {
        return new Habilidade("Haki", TipoHabilidade.DEFESA, 0.1f);
    }

    public static Habilidade criarCurarAliadosBandagem() {
        return new Habilidade("Bandagem", TipoHabilidade.CURA, 10);
    }
}
