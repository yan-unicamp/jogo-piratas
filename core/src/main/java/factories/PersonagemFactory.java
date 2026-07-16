package factories;

import entidades.Aliado;
import entidades.Inimigo;
import progressao.Recompensa;


public class PersonagemFactory {

    public static Aliado criarLuffy() {
        Aliado luffy = new Aliado("Luffy", 100, 0.9f, 10, 1, 0, "luffy.png"); // nome, vida, defesa, iniciativa, nivel, xp
        luffy.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(luffy));
        luffy.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        luffy.adicionarHabilidade(HabilidadeFactory.criarEspecialLuffy(luffy));
        
        return luffy;
    }

    public static Aliado criarZoro() {
        Aliado zoro = new Aliado("Zoro", 90, 0.95f, 9, 1, 0, "zoro.png");
        zoro.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(zoro));
        zoro.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        zoro.adicionarHabilidade(HabilidadeFactory.criarEspecialZoro(zoro));
        
        return zoro;
    }

    public static Aliado criarNami() {
        Aliado nami = new Aliado("Nami", 70, 1, 6, 1, 0, "nami.png");
        nami.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(nami));
        nami.adicionarHabilidade(HabilidadeFactory.criarEspecialNami(nami));
        nami.adicionarHabilidade(HabilidadeFactory.criarCurarAliadosBandagem());
        
        return nami;
    }

    public static Aliado criarSanji() {
        Aliado sanji = new Aliado("Sanji", 85, 0.95f, 8, 1, 0, "sanji.png");
        sanji.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(sanji));
        sanji.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        sanji.adicionarHabilidade(HabilidadeFactory.criarEspecialSanji(sanji));
        
        return sanji;
    }


    public static Aliado criarRobin() {
        Aliado robin = new Aliado("Nico Robin", 80, 1, 7, 1, 0, "robin.png");
        robin.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(robin));
        robin.adicionarHabilidade(HabilidadeFactory.criarEspecialRobin(robin));
        
        return robin;
    }

    public static Aliado criarUsopp() {
        Aliado usopp = new Aliado("Usopp", 70, 1, 5, 1, 0, "usopp.png");
        usopp.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(usopp));
        usopp.adicionarHabilidade(HabilidadeFactory.criarEspecialUsopp(usopp));
        
        return usopp;
    }

    public static Aliado criarChopper() {
        Aliado chopper = new Aliado("Chopper", 60, 1, 6, 1, 0, "chopper.png");
        chopper.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(chopper));
        chopper.adicionarHabilidade(HabilidadeFactory.criarCuraChopper(chopper));
        chopper.adicionarHabilidade(HabilidadeFactory.criarEspecialChopper(chopper));
        
        return chopper;
    }

    public static Aliado criarBrook() {
        Aliado brook = new Aliado("Brook", 80, 1, 7, 1, 0, "brook.png");
        brook.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(brook));
        brook.adicionarHabilidade(HabilidadeFactory.criarEspecialBrook(brook));
        
        return brook;
    }

    public static Aliado criarFranky() {
        Aliado franky = new Aliado("Franky", 90, 1, 7, 1, 0, "franky.png");
        franky.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(franky));
        franky.adicionarHabilidade(HabilidadeFactory.criarEspecialFranky(franky));
        
        return franky;
    }

    public static Aliado criarJinbe() {
        Aliado jinbe = new Aliado("Jinbe", 100, 0.9f, 9, 1, 0, "jinbe.png");
        jinbe.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(jinbe));
        jinbe.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        jinbe.adicionarHabilidade(HabilidadeFactory.criarEspecialJinbe(jinbe));
        
        return jinbe;
    }

    public static Aliado criarVivi(){
        Aliado vivi = new Aliado("Vivi", 70, 1, 6, 1, 0, "vivi.png");
        vivi.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(vivi));
        vivi.adicionarHabilidade(HabilidadeFactory.criarEspecialVivi(vivi));
        
        return vivi;
    }

    public static Aliado criarCarrot(){
        Aliado carrot = new Aliado("Carrot", 70, 1, 6, 1, 0, "carrot.png");
        carrot.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(carrot));
        carrot.adicionarHabilidade(HabilidadeFactory.criarEspecialCarrot(carrot));
        
        return carrot;
    }

    public static Aliado criarYamato() {
        Aliado yamato = new Aliado("Yamato", 90, 0.9f, 8, 1, 0, "yamato.png");
        yamato.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(yamato));
        yamato.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        yamato.adicionarHabilidade(HabilidadeFactory.criarEspecialYamato(yamato));
        
        return yamato;
    }

    public static Aliado criarLaw(){
        Aliado law = new Aliado("Law", 85, 0.95f, 9, 1, 0, "law.png");
        law.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(law));
        law.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        law.adicionarHabilidade(HabilidadeFactory.criarEspecialLaw(law));
        
        return law;
    }

    public static Aliado criarRebecca(){
        Aliado rebecca = new Aliado("Rebecca", 70, 1, 6, 1, 0, "rebecca.png");
        rebecca.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(rebecca));
        rebecca.adicionarHabilidade(HabilidadeFactory.criarEspecialRebecca(rebecca));
        
        return rebecca;
    }

    public static Aliado criarVegapunk(){
        Aliado vegapunk = new Aliado("Vegapunk", 70, 1, 6, 1, 0, "vegapunk.png");
        vegapunk.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(vegapunk));
        vegapunk.adicionarHabilidade(HabilidadeFactory.criarEspecialVegapunk(vegapunk));
        
        return vegapunk;
    }

    public static Aliado criarMomonosuke(){
        Aliado momonosuke = new Aliado("Momonosuke", 70, 1, 6, 1, 0, "momonosuke.png");
        momonosuke.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(momonosuke));
        momonosuke.adicionarHabilidade(HabilidadeFactory.criarEspecialMomonosuke(momonosuke));
        
        return momonosuke;
    }

    public static Aliado criarBonClay(){
        Aliado bonClay = new Aliado("Bon Clay", 70, 1, 6, 1, 0, "bonclay.png");
        bonClay.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(bonClay));
        bonClay.adicionarHabilidade(HabilidadeFactory.criarEspecialBonClay(bonClay));
        
        return bonClay;
    }

    public static Aliado criarKoby(){
        Aliado koby = new Aliado("Koby", 70, 1, 6, 1, 0, "koby.png");
        koby.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(koby));
        koby.adicionarHabilidade(HabilidadeFactory.criarEspecialKoby(koby));
        
        return koby;
    }

    public static Aliado criarCarue(){
        Aliado carue = new Aliado("Carue", 70, 1, 6, 1, 0, "carue.png");
        carue.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(carue));
        carue.adicionarHabilidade(HabilidadeFactory.criarEspecialCarue(carue));
        
        return carue;
    }

    public static Aliado criarAce(){
        Aliado ace = new Aliado("Ace", 70, 1, 6, 1, 0, "ace.png");
        ace.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(ace));
        ace.adicionarHabilidade(HabilidadeFactory.criarEspecialAce(ace));
        
        return ace;
    }

    public static Aliado criarLoki(){
        Aliado loki = new Aliado("Loki", 120, 0.90f, 10, 1, 0, "loki.png");
        loki.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(loki));
        loki.adicionarHabilidade(HabilidadeFactory.criarEspecialLoki(loki));
        loki.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return loki;
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

}
