package factories;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.TipoHabilidade;

public class HabilidadeFactory {

    public static Habilidade criarAtaqueBasico(Aliado p) {
        return new Habilidade("Ataque Base", TipoHabilidade.DANO, 10 + (2 * p.getNivel())); // botei pra variar com o nivel tambem
    }

    public static Habilidade criarAtaqueBasicoInimigo(int nivel) {
        return new Habilidade("Ataque Base", TipoHabilidade.DANO, 5 + (2 * nivel)); // botei pra variar com o nivel tambem
    }

    public static Habilidade criarDefesaHaki() {
        return new Habilidade("Haki", TipoHabilidade.DEFESA, 0.1f);
    }

    public static Habilidade criarCurarAliadosBandagem() {
        return new Habilidade("Bandagem", TipoHabilidade.CURA, 10);
    }

    public static Habilidade criarEspecialLuffy(Aliado p) {
        return new Habilidade("Gomu Gomu no Jet", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialZoro(Aliado p) {
        return new Habilidade("Onigiri", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialNami(Aliado p) {
        return new Habilidade("Zeus", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialSanji(Aliado p) {
        return new Habilidade("Diable Jambe", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialRobin(Aliado p) {
        return new Habilidade("Tres Fleur", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialUsopp(Aliado p) {
        return new Habilidade("Hissatsu: Midori Boshi", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialChopper(Aliado p) {
        return new Habilidade("Monster Point", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarCuraChopper(Aliado p) {
        return new Habilidade("Cura do Chopper", TipoHabilidade.CURA, p.getVidaMaxima() * 0.3f); //ele cura equivalente a 30% da vida maxima dele
    }

    public static Habilidade criarEspecialBrook(Aliado p) {
        return new Habilidade("Yubi Barry", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialJinbe(Aliado p) {
        return new Habilidade("Gyojin Karate", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialFranky(Aliado p) {
        return new Habilidade("Coupe de Vent", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f); 
    }

    public static Habilidade criarEspecialVivi(Aliado p) {
        return new Habilidade("Danse Macabre", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialCarue(Aliado p) {
        return new Habilidade("Patada do Carue", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialKoby(Aliado p) {
        return new Habilidade("Shigan", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialYamato(Aliado p) {
        return new Habilidade("Thunder Bagua", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialLaw(Aliado p) {
        return new Habilidade("Room", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialRebecca(Aliado p) {
        return new Habilidade("Coliseu", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialVegapunk(Aliado p) {
        return new Habilidade("Fabrica", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialMomonosuke(Aliado p) {
        return new Habilidade("Rugido do Dragão", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialBonClay(Aliado p) {
        return new Habilidade("Manji ", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialAce(Aliado p) {
        return new Habilidade("Hiken", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }
    
    public static Habilidade criarEspecialLoki(Aliado p) {
        return new Habilidade("Porrada", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

    public static Habilidade criarEspecialCarrot(Aliado p) {
        return new Habilidade("Sulong", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f);
    }

}
