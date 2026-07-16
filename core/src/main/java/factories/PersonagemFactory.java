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
        
        return vivi;
    }

    public static Aliado criarCarrot(){
        Aliado carrot = new Aliado("Carrot", 70, 1, 6, 1, 0, "carrot.png");
        carrot.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(carrot));
        
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
        
        return momonosuke;
    }

    public static Aliado criarBonClay(){
        Aliado bonClay = new Aliado("Bon Clay", 70, 1, 6, 1, 0, "bonclay.png");
        bonClay.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(bonClay));
        
        return bonClay;
    }

    public static Aliado criarKoby(){
        Aliado koby = new Aliado("Koby", 70, 1, 6, 1, 0, "koby.png");
        koby.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(koby));
        
        return koby;
    }

    public static Aliado criarKaru(){
        Aliado karu = new Aliado("Karu", 70, 1, 6, 1, 0, "karu.png");
        karu.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(karu));
        
        return karu;
    }

    public static Aliado criarAce(){
        Aliado ace = new Aliado("Ace", 70, 1, 6, 1, 0, "ace.png");
        ace.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(ace));
        
        return ace;
    }

    public static Aliado criarLoki(){
        Aliado loki = new Aliado("Loki", 120, 0.90f, 10, 1, 0, "loki.png");
        loki.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(loki));
        
        return loki;
    }
    
    public static Inimigo criarMarinheiro(int nivel) { // (ta escalonando com o nivel atual, pode trocar dps)
        Recompensa loot = new Recompensa(10 * nivel, 5 * nivel); // dinheiro, xp 
        Inimigo marinheiro = new Inimigo("Marinheiro Who", 30 * nivel, 0, 5, loot, "marinheiro.png");// nome, vida, defesa, iniciativa, recompensa
        marinheiro.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        
        return marinheiro;
    }

    public static Inimigo criarPirataInimigo(int nivel) { // (ta escalonando com o nivel atual, pode trocar dps)
        Recompensa loot = new Recompensa(10 * nivel, 5 * nivel); // dinheiro, xp 
        Inimigo pirata = new Inimigo("Pirata Who", 30 * nivel, 0, 5, loot, "pirata.png");// nome, vida, defesa, iniciativa, recompensa
        pirata.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        
        return pirata;
    }
    
    public static Inimigo criarChefe(int nivel) {
        Recompensa lootGordo = new Recompensa(500, 200);
        Inimigo boss = new Inimigo("Boss who", 300, 0.6f, 20, lootGordo, "chefe.png");
        boss.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        boss.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return boss;
    }
}
