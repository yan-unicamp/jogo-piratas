package factories;

import entidades.Aliado;
import entidades.Inimigo;


public class PersonagemFactory {
    private static Aliado adicionarHabilidadesDeNivel(Aliado a) {
        if (!a.temHabilidadeDesbloqueavelNoNivel(3)) a.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarGolpeConcentrado(a));
        if (!a.temHabilidadeDesbloqueavelNoNivel(5)) a.adicionarHabilidadeDesbloqueavel(5, () -> HabilidadeFactory.criarPosturaDefensiva(a));
        if (!a.temHabilidadeDesbloqueavelNoNivel(7)) a.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarFolegoExtra(a));
        if (!a.temHabilidadeDesbloqueavelNoNivel(10)) a.adicionarHabilidadeDesbloqueavel(10, () -> HabilidadeFactory.criarAtaqueSupremo(a));
        return a;
    }


    public static Aliado criarLuffy() {
        Aliado luffy = new Aliado("Luffy", 120, 0.9f, 10, 1, 0, "luffy.png"); // nome, vida, defesa, iniciativa, nivel, xp
        luffy.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(luffy));
        luffy.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        luffy.adicionarHabilidade(HabilidadeFactory.criarEspecialLuffy(luffy));
        
        luffy.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarEspecialGatling(luffy));
        luffy.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarEspecialGearSecond(luffy));
        
        return adicionarHabilidadesDeNivel(luffy);
    }

    public static Aliado criarZoro() {
        Aliado zoro = new Aliado("Zoro", 110, 0.95f, 9, 1, 0, "zoro.png");
        zoro.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(zoro));
        zoro.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        zoro.adicionarHabilidade(HabilidadeFactory.criarEspecialZoro(zoro));
        
        zoro.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarSanzenSekai(zoro));
        zoro.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarAshura(zoro));
        return adicionarHabilidadesDeNivel(zoro);
    }

    public static Aliado criarNami() {
        Aliado nami = new Aliado("Nami", 90, 1, 6, 1, 0, "nami.png");
        nami.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(nami));
        nami.adicionarHabilidade(HabilidadeFactory.criarEspecialNami(nami));
        nami.adicionarHabilidade(HabilidadeFactory.criarCurarAliadosBandagem());
        
        nami.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarThunderboltTempo(nami));
        nami.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarMirageTempo(nami));
        return adicionarHabilidadesDeNivel(nami);
    }

    public static Aliado criarSanji() {
        Aliado sanji = new Aliado("Sanji", 105, 0.95f, 8, 1, 0, "sanji.png");
        sanji.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(sanji));
        sanji.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        sanji.adicionarHabilidade(HabilidadeFactory.criarEspecialSanji(sanji));
        
        sanji.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarHellMemories(sanji));
        sanji.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarSkyWalk(sanji));
        return adicionarHabilidadesDeNivel(sanji);
    }


    public static Aliado criarRobin() {
        Aliado robin = new Aliado("Nico Robin", 100, 1, 7, 1, 0, "robin.png");
        robin.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(robin));
        robin.adicionarHabilidade(HabilidadeFactory.criarEspecialRobin(robin));
        
        robin.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarCienFleurWing(robin));
        robin.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarGigantescoMano(robin));
        return adicionarHabilidadesDeNivel(robin);
    }

    public static Aliado criarUsopp() {
        Aliado usopp = new Aliado("Usopp", 90, 1, 5, 1, 0, "usopp.png");
        usopp.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(usopp));
        usopp.adicionarHabilidade(HabilidadeFactory.criarEspecialUsopp(usopp));
        
        usopp.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarFirebirdStar(usopp));
        usopp.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarImpactWolf(usopp));
        return adicionarHabilidadesDeNivel(usopp);
    }

    public static Aliado criarChopper() {
        Aliado chopper = new Aliado("Chopper", 80, 1, 6, 1, 0, "chopper.png");
        chopper.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(chopper));
        chopper.adicionarHabilidade(HabilidadeFactory.criarCuraChopper(chopper));
        chopper.adicionarHabilidade(HabilidadeFactory.criarEspecialChopper(chopper));
        
        chopper.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarGuardPoint(chopper));
        chopper.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarBrainPoint(chopper));
        return adicionarHabilidadesDeNivel(chopper);
    }

    public static Aliado criarBrook() {
        Aliado brook = new Aliado("Brook", 100, 1, 7, 1, 0, "brook.png");
        brook.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(brook));
        brook.adicionarHabilidade(HabilidadeFactory.criarEspecialBrook(brook));
        
        brook.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarYahazuGiri(brook));
        brook.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarSoulKingLullaby(brook));
        return adicionarHabilidadesDeNivel(brook);
    }

    public static Aliado criarFranky() {
        Aliado franky = new Aliado("Franky", 110, 1, 7, 1, 0, "franky.png");
        franky.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(franky));
        franky.adicionarHabilidade(HabilidadeFactory.criarEspecialFranky(franky));
        
        franky.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarFrankyRadicalBeam(franky));
        franky.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarGeneralFranky(franky));
        return adicionarHabilidadesDeNivel(franky);
    }

    public static Aliado criarJinbe() {
        Aliado jinbe = new Aliado("Jinbe", 120, 0.9f, 9, 1, 0, "jinbe.png");
        jinbe.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(jinbe));
        jinbe.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        jinbe.adicionarHabilidade(HabilidadeFactory.criarEspecialJinbe(jinbe));
        
        jinbe.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarBuraikan(jinbe));
        jinbe.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarMizuGokoro(jinbe));
        return adicionarHabilidadesDeNivel(jinbe);
    }

    public static Aliado criarVivi(){
        Aliado vivi = new Aliado("Vivi", 90, 1, 6, 1, 0, "vivi.png");
        vivi.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(vivi));
        vivi.adicionarHabilidade(HabilidadeFactory.criarEspecialVivi(vivi));
        
        vivi.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarKujakuSlasher(vivi));
        vivi.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarVozDeAlabasta(vivi));
        return adicionarHabilidadesDeNivel(vivi);
    }

    public static Aliado criarCarrot(){
        Aliado carrot = new Aliado("Carrot", 90, 1, 6, 1, 0, "carrot.png");
        carrot.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(carrot));
        carrot.adicionarHabilidade(HabilidadeFactory.criarEspecialCarrot(carrot));
        
        carrot.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarElectro(carrot));
        carrot.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarSaltoLunar(carrot));
        return adicionarHabilidadesDeNivel(carrot);
    }

    public static Aliado criarYamato() {
        Aliado yamato = new Aliado("Yamato", 110, 0.9f, 8, 1, 0, "yamato.png");
        yamato.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(yamato));
        yamato.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        yamato.adicionarHabilidade(HabilidadeFactory.criarEspecialYamato(yamato));
        
        yamato.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarNarikaburaArrow(yamato));
        yamato.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarDefesaMakami(yamato));
        return adicionarHabilidadesDeNivel(yamato);
    }

    public static Aliado criarLaw(){
        Aliado law = new Aliado("Law", 105, 0.95f, 9, 1, 0, "law.png");
        law.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(law));
        law.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        law.adicionarHabilidade(HabilidadeFactory.criarEspecialLaw(law));
        
        law.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarGammaKnife(law));
        law.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarShambles(law));
        return adicionarHabilidadesDeNivel(law);
    }

    public static Aliado criarRebecca(){
        Aliado rebecca = new Aliado("Rebecca", 90, 1, 6, 1, 0, "rebecca.png");
        rebecca.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(rebecca));
        rebecca.adicionarHabilidade(HabilidadeFactory.criarEspecialRebecca(rebecca));
        
        rebecca.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarDancaDasEspadas(rebecca));
        rebecca.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarGolpeDeDesarme(rebecca));
        return adicionarHabilidadesDeNivel(rebecca);
    }

    public static Aliado criarVegapunk(){
        Aliado vegapunk = new Aliado("Vegapunk", 90, 1, 6, 1, 0, "vegapunk.png");
        vegapunk.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(vegapunk));
        vegapunk.adicionarHabilidade(HabilidadeFactory.criarEspecialVegapunk(vegapunk));
        
        vegapunk.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarLaserPacifista(vegapunk));
        vegapunk.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarEscudoEletromagnetico(vegapunk));
        return adicionarHabilidadesDeNivel(vegapunk);
    }

    public static Aliado criarMomonosuke(){
        Aliado momonosuke = new Aliado("Momonosuke", 90, 1, 6, 1, 0, "momonosuke.png");
        momonosuke.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(momonosuke));
        momonosuke.adicionarHabilidade(HabilidadeFactory.criarEspecialMomonosuke(momonosuke));
        
        momonosuke.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarBoloBreath(momonosuke));
        momonosuke.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarMordidaDeDragao(momonosuke));
        return adicionarHabilidadesDeNivel(momonosuke);
    }

    public static Aliado criarBonClay(){
        Aliado bonClay = new Aliado("Bon Clay", 90, 1, 6, 1, 0, "bonclay.png");
        bonClay.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(bonClay));
        bonClay.adicionarHabilidade(HabilidadeFactory.criarEspecialBonClay(bonClay));
        
        bonClay.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarSwanArabesque(bonClay));
        bonClay.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarManeManeDefense(bonClay));
        return adicionarHabilidadesDeNivel(bonClay);
    }

    public static Aliado criarKoby(){
        Aliado koby = new Aliado("Koby", 90, 1, 6, 1, 0, "koby.png");
        koby.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(koby));
        koby.adicionarHabilidade(HabilidadeFactory.criarEspecialKoby(koby));
        
        koby.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarHonestyImpact(koby));
        koby.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarSoru(koby));
        return adicionarHabilidadesDeNivel(koby);
    }

    public static Aliado criarCarue(){
        Aliado carue = new Aliado("Carue", 90, 1, 6, 1, 0, "carue.png");
        carue.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(carue));
        carue.adicionarHabilidade(HabilidadeFactory.criarEspecialCarue(carue));
        
        carue.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarInvestidaSupersonica(carue));
        carue.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarAsasDeProtecao(carue));
        return adicionarHabilidadesDeNivel(carue);
    }

    public static Aliado criarAce(){
        Aliado ace = new Aliado("Ace", 90, 1, 6, 1, 0, "ace.png");
        ace.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(ace));
        ace.adicionarHabilidade(HabilidadeFactory.criarEspecialAce(ace));
        
        ace.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarEntei(ace));
        ace.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarKagero(ace));
        return adicionarHabilidadesDeNivel(ace);
    }

    public static Aliado criarLoki(){
        Aliado loki = new Aliado("Loki", 140, 0.90f, 10, 1, 0, "loki.png");
        loki.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(loki));
        loki.adicionarHabilidade(HabilidadeFactory.criarEspecialLoki(loki));
        loki.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        loki.adicionarHabilidadeDesbloqueavel(3, () -> HabilidadeFactory.criarLancaDeElbaf(loki));
        loki.adicionarHabilidadeDesbloqueavel(7, () -> HabilidadeFactory.criarFuriaDoGigante(loki));
        return adicionarHabilidadesDeNivel(loki);
    }
    
    public static Inimigo criarMarinheiro(int nivel) { // (ta escalonando com o nivel atual, pode trocar dps)
        Inimigo marinheiro = new Inimigo("Marinheiro Who", 30 * nivel, 1, 5, 10 * nivel, 5 * nivel, "marinheiro.png");// nome, vida, defesa, iniciativa, recompensa
        marinheiro.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        
        return marinheiro;
    }

    public static Inimigo criarPirataInimigo(int nivel) { // (ta escalonando com o nivel atual, pode trocar dps)
        Inimigo pirata = new Inimigo("Pirata Who", 30 * nivel, 1, 5, 10 * nivel, 5 * nivel, "pirata.png");// nome, vida, defesa, iniciativa, recompensa
        pirata.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        
        return pirata;
    }
    
    public static Inimigo criarChefe(int nivel) {
        Inimigo boss = new Inimigo("Boss who", 300, 0.6f, 20, 500, 200, "chefe.png");
        boss.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        boss.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return boss;
    }

    public static Inimigo criarMorgan(int nivel){
        Inimigo morgan = new Inimigo("Morgan Mao de Machado", 300, 0.6f, 20, 500, 200, "morgan.png");
        morgan.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        morgan.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return morgan;
    }

    public static Inimigo criarMoria(int nivel){
        Inimigo moria = new Inimigo("Moria", 300, 0.6f, 20, 500, 200, "moria.png");
        moria.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        moria.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return moria;
    }

    public static Inimigo criarKuro(int nivel){
        Inimigo kuro = new Inimigo("Kuro", 300, 0.6f, 20, 500, 200, "kuro.png");
        kuro.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        kuro.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return kuro;
    }

    public static Inimigo criarRodyJones(int nivel){
        Inimigo rodyJones = new Inimigo("Rody Jones", 300, 0.6f, 20, 500, 200, "rodyjones.png");
        rodyJones.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        rodyJones.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return rodyJones;
    }

    public static Inimigo criarWapol(int nivel){
        Inimigo wapol = new Inimigo("Wapol", 300, 0.6f, 20, 500, 200, "wapol.png");
        wapol.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        wapol.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return wapol;
    }

    public static Inimigo criarDoflamingo(int nivel){
        Inimigo doflamingo = new Inimigo("Doflamingo", 300, 0.6f, 20, 500, 200, "doflamingo.png");
        doflamingo.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        doflamingo.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return doflamingo;
    }

    public static Inimigo criarEneru(int nivel){
        Inimigo eneru = new Inimigo("Eneru", 300, 0.6f, 20, 500, 200, "eneru.png");
        eneru.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        eneru.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return eneru;
    }

    public static Inimigo criarCrocodile(int nivel){
        Inimigo crocodile = new Inimigo("Crocodile", 300, 0.6f, 20, 500, 200, "crocodile.png");
        crocodile.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        crocodile.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return crocodile;
    }

    public static Inimigo criarDonKrieg(int nivel){
        Inimigo donKrieg = new Inimigo("Don Krieg", 300, 0.6f, 20, 500, 200, "donkrieg.png");
        donKrieg.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        donKrieg.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return donKrieg;
    }

    public static Inimigo criarBuggy(int nivel){
        Inimigo buggy = new Inimigo("Buggy", 300, 0.6f, 20, 500, 200, "buggy.png");
        buggy.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        buggy.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return buggy;
    }

    public static Inimigo criarArlong(int nivel){
        Inimigo arlong = new Inimigo("Arlong", 300, 0.6f, 20, 500, 200, "arlong.png");
        arlong.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        arlong.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return arlong;
    }

    public static Inimigo criarRobLucci(int nivel){
        Inimigo robLucci = new Inimigo("Rob Lucci", 300, 0.6f, 20, 500, 200, "roblucci.png");
        robLucci.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        robLucci.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return robLucci;
    }

    public static Inimigo criarBigMom(int nivel){
        Inimigo bigMom = new Inimigo("Big Mom", 300, 0.6f, 20, 500, 200, "bigmom.png");
        bigMom.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        bigMom.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return bigMom;
    }

    public static Inimigo criarAkainu(int nivel){
        Inimigo akainu = new Inimigo("Akainu", 300, 0.6f, 20, 500, 200, "akainu.png");
        akainu.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        akainu.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return akainu;
    }

    public static Inimigo criarKizaru(int nivel){
        Inimigo kizaru = new Inimigo("Kizaru", 300, 0.6f, 20, 500, 200, "kizaru.png");
        kizaru.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        kizaru.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return kizaru;
    }

    public static Inimigo criarKaido(int nivel){
        Inimigo kaido = new Inimigo("Kaido", 300, 0.6f, 20, 500, 200, "kaido.png");
        kaido.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        kaido.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return kaido;
    }

    public static Inimigo criarMihawk(int nivel){
        Inimigo mihawk = new Inimigo("Mihawk", 300, 0.6f, 20, 500, 200, "mihawk.png");
        mihawk.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        mihawk.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return mihawk;
    }

    public static Inimigo criarSmoker(int nivel){
        Inimigo smoker = new Inimigo("Smoker", 300, 0.6f, 20, 500, 200, "smoker.png");
        smoker.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        smoker.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return smoker;
    }

    public static Inimigo criarCesar(int nivel){
        Inimigo cesar = new Inimigo("Cesar", 300, 0.6f, 20, 500, 200, "cesar.png");
        cesar.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        cesar.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return cesar;
    }

    public static Inimigo criarMagellan(int nivel){
        Inimigo magellan = new Inimigo("Magellan", 300, 0.6f, 20, 500, 200, "magellan.png");
        magellan.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        magellan.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return magellan;
    }

    public static Inimigo criarImu(int nivel){
        Inimigo imu = new Inimigo("Imu", 1000, 0.8f, 30, 2000, 1000, "imu.png");
        imu.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel * 2));
        imu.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return imu;
    }
    
    public static java.util.ArrayList<Inimigo> sortearInimigosCanonicos(int capitulo, int nivel) {
        java.util.ArrayList<Inimigo> inimigos = new java.util.ArrayList<>();
        java.util.Random rand = new java.util.Random();
        int sorteio = rand.nextInt(3);
        
        if (capitulo == 1) {
            if (sorteio == 0) inimigos.add(criarBuggy(nivel));
            else if (sorteio == 1) inimigos.add(criarDonKrieg(nivel));
            else inimigos.add(criarArlong(nivel));
        } else if (capitulo == 2) {
            if (sorteio == 0) inimigos.add(criarWapol(nivel));
            else if (sorteio == 1) inimigos.add(criarEneru(nivel));
            else inimigos.add(criarMoria(nivel));
        } else {
            if (sorteio == 0) inimigos.add(criarMagellan(nivel));
            else if (sorteio == 1) inimigos.add(criarDoflamingo(nivel));
            else inimigos.add(criarBigMom(nivel));
        }
        return inimigos;
    }

    public static java.util.ArrayList<Inimigo> getBossCapitulo(int capitulo, int nivel) {
        java.util.ArrayList<Inimigo> inimigos = new java.util.ArrayList<>();
        if (capitulo == 1) {
            inimigos.add(criarCrocodile(nivel));
        } else if (capitulo == 2) {
            inimigos.add(criarRobLucci(nivel));
        } else {
            inimigos.add(criarKaido(nivel));
        }
        return inimigos;
    }

    public static Inimigo criarCapanga(String nome, int idx, String spriteKey) {
        int hp = 25 + idx * 10;
        float def = 1.0f - (idx * 0.02f);
        if (def < 0.5f) def = 0.5f;
        int inic = 8 + idx;
        int poder = 4 + idx * 2;
        Inimigo e = new Inimigo(nome, hp, def, inic,
                40 + idx * 20, 20 + idx * 10, spriteKey);
        e.adicionarHabilidade(new entidades.Habilidade("Ataque", entidades.TipoHabilidade.DANO, poder));
        return e;
    }

    public static Inimigo criarMiniBoss(String nome, int idx, String spriteKey, String habNome, int habPoder) {
        int hp = 50 + idx * 20;
        float def = 0.9f - (idx * 0.03f);
        if (def < 0.3f) def = 0.3f;
        int inic = 10 + idx;
        Inimigo e = new Inimigo(nome, hp, def, inic,
                100 + idx * 40, 50 + idx * 20, spriteKey);
        e.adicionarHabilidade(new entidades.Habilidade(habNome, entidades.TipoHabilidade.DANO, (habPoder / 3) + (idx * 2)));
        return e;
    }

    public static Inimigo criarBoss(String nome, int idx, String spriteKey, String hab1, int pod1, String hab2, int pod2) {
        int hp = 100 + idx * 40;
        float def = 0.8f - (idx * 0.04f);
        if (def < 0.2f) def = 0.2f;
        int inic = 12 + idx * 2;
        Inimigo b = new Inimigo(nome, hp, def, inic,
                200 + idx * 100, 100 + idx * 50, spriteKey);
        b.adicionarHabilidade(new entidades.Habilidade(hab1, entidades.TipoHabilidade.DANO, (pod1 / 3) + (idx * 3)));
        b.adicionarHabilidade(new entidades.Habilidade(hab2, entidades.TipoHabilidade.DANO, (pod2 / 3) + (idx * 4)));
        return b;
    }
}


