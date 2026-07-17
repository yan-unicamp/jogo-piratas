package factories;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.TipoHabilidade;

public class HabilidadeFactory {

    public static Habilidade criarAtaqueBasico(Aliado p) {
        return new Habilidade("Ataque Base", TipoHabilidade.DANO, 15 + (4 * p.getNivel())); // botei pra variar com o
                                                                                            // nivel tambem
    }

    public static Habilidade criarAtaqueBasicoInimigo(int nivel) {
        return new Habilidade("Ataque Base", TipoHabilidade.DANO, 5 + (2 * nivel)); // botei pra variar com o nivel
                                                                                    // tambem
    }

    public static Habilidade criarDefesaHaki() {
        return new Habilidade("Haki", TipoHabilidade.DEFESA, 0.1f, true);
    }

    public static Habilidade criarCurarAliadosBandagem() {
        return new Habilidade("Bandagem", TipoHabilidade.CURA, 10);
    }

    public static Habilidade criarEspecialLuffy(Aliado p) {
        return new Habilidade("Gomu Gomu no Pistol", TipoHabilidade.DANO, 20 + (p.getVidaMaxima() * 0.4f))
                .setEspecial(true);
    }

    public static Habilidade criarEspecialGatling(Aliado p) {
        return new Habilidade("Gomu Gomu no Gatling", TipoHabilidade.DANO, 20 + (p.getVidaMaxima() * 0.5f))
                .setEspecial(true);
    }

    public static Habilidade criarEspecialGearSecond(Aliado p) {
        return new Habilidade("Gear Second", TipoHabilidade.DANO, 50 + (p.getVidaMaxima() * 0.4f)).setEspecial(true);
    }

    public static Habilidade criarEspecialZoro(Aliado p) {
        return new Habilidade("Onigiri", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialNami(Aliado p) {
        return new Habilidade("Zeus", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialSanji(Aliado p) {
        return new Habilidade("Diable Jambe", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialRobin(Aliado p) {
        return new Habilidade("Tres Fleur", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialUsopp(Aliado p) {
        return new Habilidade("Hissatsu: Midori Boshi", TipoHabilidade.DANO, p.getVidaMaxima() * 0.3f)
                .setEspecial(true);
    }

    public static Habilidade criarEspecialChopper(Aliado p) {
        return new Habilidade("Monster Point", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarCuraChopper(Aliado p) {
        return new Habilidade("Cura do Chopper", TipoHabilidade.CURA, p.getVidaMaxima() * 0.4f); // ele cura equivalente
                                                                                                 // a 30% da vida maxima
                                                                                                 // dele
    }

    public static Habilidade criarEspecialBrook(Aliado p) {
        return new Habilidade("Yubi Barry", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialJinbe(Aliado p) {
        return new Habilidade("Gyojin Karate", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialFranky(Aliado p) {
        return new Habilidade("Coupe de Vent", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialVivi(Aliado p) {
        return new Habilidade("Danse Macabre", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialCarue(Aliado p) {
        return new Habilidade("Patada do Carue", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialKoby(Aliado p) {
        return new Habilidade("Shigan", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialYamato(Aliado p) {
        return new Habilidade("Thunder Bagua", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialLaw(Aliado p) {
        return new Habilidade("Room", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialRebecca(Aliado p) {
        return new Habilidade("Coliseu", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialVegapunk(Aliado p) {
        return new Habilidade("Fabrica", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialMomonosuke(Aliado p) {
        return new Habilidade("Rugido do Dragao", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialBonClay(Aliado p) {
        return new Habilidade("Manji ", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialAce(Aliado p) {
        return new Habilidade("Hiken", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialLoki(Aliado p) {
        return new Habilidade("Porrada", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarEspecialCarrot(Aliado p) {
        return new Habilidade("Sulong", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarGolpeConcentrado(Aliado p) {
        return new Habilidade("Golpe Concentrado", TipoHabilidade.DANO, p.getVidaMaxima() * 0.4f);
    }

    public static Habilidade criarPosturaDefensiva(Aliado p) {
        return new Habilidade("Postura Defensiva", TipoHabilidade.DEFESA, 0.4f, true);
    }

    public static Habilidade criarFolegoExtra(Aliado p) {
        return new Habilidade("Folego Extra", TipoHabilidade.CURA, p.getVidaMaxima() * 0.4f, true);
    }

    public static Habilidade criarAtaqueSupremo(Aliado p) {
        return new Habilidade("Ataque Supremo", TipoHabilidade.DANO, p.getVidaMaxima() * 1.2f);
    }

    public static Habilidade criarSanzenSekai(Aliado p) {
        return new Habilidade("Santoryu Ogi: Sanzen Sekai", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarAshura(Aliado p) {
        return new Habilidade("Ashura", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarThunderboltTempo(Aliado p) {
        return new Habilidade("Thunderbolt Tempo", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarMirageTempo(Aliado p) {
        return new Habilidade("Mirage Tempo", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarHellMemories(Aliado p) {
        return new Habilidade("Hell Memories", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarSkyWalk(Aliado p) {
        return new Habilidade("Sky Walk", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarCienFleurWing(Aliado p) {
        return new Habilidade("Cien Fleur: Wing", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarGigantescoMano(Aliado p) {
        return new Habilidade("Gigantesco Mano", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarFirebirdStar(Aliado p) {
        return new Habilidade("Hissatsu: Firebird Star", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarImpactWolf(Aliado p) {
        return new Habilidade("Pop Green: Impact Wolf", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarGuardPoint(Aliado p) {
        return new Habilidade("Guard Point", TipoHabilidade.DEFESA, 0.5f, true).setEspecial(true);
    }

    public static Habilidade criarBrainPoint(Aliado p) {
        return new Habilidade("Brain Point", TipoHabilidade.CURA, p.getVidaMaxima() * 0.3f, true).setEspecial(true);
    }

    public static Habilidade criarYahazuGiri(Aliado p) {
        return new Habilidade("Yahazu Giri", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarSoulKingLullaby(Aliado p) {
        return new Habilidade("Soul King Lullaby", TipoHabilidade.CURA, p.getVidaMaxima() * 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarFrankyRadicalBeam(Aliado p) {
        return new Habilidade("Radical Beam", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarGeneralFranky(Aliado p) {
        return new Habilidade("General Franky", TipoHabilidade.DEFESA, 0.5f, true).setEspecial(true);
    }

    public static Habilidade criarBuraikan(Aliado p) {
        return new Habilidade("Buraikan", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarMizuGokoro(Aliado p) {
        return new Habilidade("Mizu Gokoro", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarKujakuSlasher(Aliado p) {
        return new Habilidade("Kujaku Slasher", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarVozDeAlabasta(Aliado p) {
        return new Habilidade("Voz de Alabasta", TipoHabilidade.CURA, p.getVidaMaxima() * 0.3f, true).setEspecial(true);
    }

    public static Habilidade criarElectro(Aliado p) {
        return new Habilidade("Electro", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarSaltoLunar(Aliado p) {
        return new Habilidade("Salto Lunar", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarNarikaburaArrow(Aliado p) {
        return new Habilidade("Narikabura Arrow", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarDefesaMakami(Aliado p) {
        return new Habilidade("Defesa Makami", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarGammaKnife(Aliado p) {
        return new Habilidade("Gamma Knife", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarShambles(Aliado p) {
        return new Habilidade("Shambles", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarDancaDasEspadas(Aliado p) {
        return new Habilidade("Danca das Espadas", TipoHabilidade.DEFESA, 0.5f, true).setEspecial(true);
    }

    public static Habilidade criarGolpeDeDesarme(Aliado p) {
        return new Habilidade("Golpe de Desarme", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarLaserPacifista(Aliado p) {
        return new Habilidade("Laser Pacifista", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarEscudoEletromagnetico(Aliado p) {
        return new Habilidade("Escudo Eletromagnetico", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarBoloBreath(Aliado p) {
        return new Habilidade("Bolo Breath", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarMordidaDeDragao(Aliado p) {
        return new Habilidade("Mordida de Dragao", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarSwanArabesque(Aliado p) {
        return new Habilidade("Swan Arabesque", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarManeManeDefense(Aliado p) {
        return new Habilidade("Mane Mane Defense", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarHonestyImpact(Aliado p) {
        return new Habilidade("Honesty Impact", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarSoru(Aliado p) {
        return new Habilidade("Soru", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarInvestidaSupersonica(Aliado p) {
        return new Habilidade("Investida Supersonica", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarAsasDeProtecao(Aliado p) {
        return new Habilidade("Asas de Protecao", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarEntei(Aliado p) {
        return new Habilidade("Dai Enkai: Entei", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

    public static Habilidade criarKagero(Aliado p) {
        return new Habilidade("Kagero", TipoHabilidade.DEFESA, 0.4f, true).setEspecial(true);
    }

    public static Habilidade criarLancaDeElbaf(Aliado p) {
        return new Habilidade("Lanca de Elbaf", TipoHabilidade.DANO, p.getVidaMaxima() * 0.5f).setEspecial(true);
    }

    public static Habilidade criarFuriaDoGigante(Aliado p) {
        return new Habilidade("Furia do Gigante", TipoHabilidade.DANO, p.getVidaMaxima() * 0.6f).setEspecial(true);
    }

}
