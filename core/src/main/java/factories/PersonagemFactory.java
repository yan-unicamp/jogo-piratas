package factories;

import entidades.Aliado;
import entidades.Inimigo;
import progressao.Recompensa;

public class PersonagemFactory {

    public static Aliado criarLuffy() {
        Aliado luffy = new Aliado("Luffy", 100, 0.9f, 10, 1, 0); // nome, vida, defesa, iniciativa, nivel, xp
        luffy.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(luffy));
        luffy.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        luffy.adicionarHabilidade(HabilidadeFactory.criarEspecialLuffy(luffy));
        
        return luffy;
    }

    public static Aliado criarZoro() {
        Aliado zoro = new Aliado("Zoro", 90, 0.95f, 9, 1, 0);
        zoro.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(zoro));
        zoro.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        zoro.adicionarHabilidade(HabilidadeFactory.criarEspecialZoro(zoro));
        
        return zoro;
    }

    public static Aliado criarNami() {
        Aliado nami = new Aliado("Nami", 70, 1, 6, 1, 0);
        nami.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(nami));
        nami.adicionarHabilidade(HabilidadeFactory.criarEspecialNami(nami));
        nami.adicionarHabilidade(HabilidadeFactory.criarCurarAliadosBandagem());
        
        return nami;
    }

    public static Aliado criarSanji() {
        Aliado sanji = new Aliado("Sanji", 85, 0.95f, 8, 1, 0);
        sanji.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(sanji));
        sanji.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        sanji.adicionarHabilidade(HabilidadeFactory.criarEspecialSanji(sanji));
        
        return sanji;
    }


    public static Aliado criarRobin() {
        Aliado robin = new Aliado("Nico Robin", 80, 1, 7, 1, 0);
        robin.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(robin));
        robin.adicionarHabilidade(HabilidadeFactory.criarEspecialRobin(robin));
        
        return robin;
    }

    public static Aliado criarUsopp() {
        Aliado usopp = new Aliado("Usopp", 70, 1, 5, 1, 0);
        usopp.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(usopp));
        usopp.adicionarHabilidade(HabilidadeFactory.criarEspecialUsopp(usopp));
        
        return usopp;
    }

    public static Aliado criarChopper() {
        Aliado chopper = new Aliado("Chopper", 60, 1, 6, 1, 0);
        chopper.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(chopper));
        chopper.adicionarHabilidade(HabilidadeFactory.criarCuraChopper(chopper));
        chopper.adicionarHabilidade(HabilidadeFactory.criarEspecialChopper(chopper));
        
        return chopper;
    }

    public static Aliado criarBrook() {
        Aliado brook = new Aliado("Brook", 80, 1, 7, 1, 0);
        brook.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(brook));
        brook.adicionarHabilidade(HabilidadeFactory.criarEspecialBrook(brook));
        
        return brook;
    }

    public static Aliado criarFranky() {
        Aliado franky = new Aliado("Franky", 90, 1, 7, 1, 0);
        franky.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(franky));
        franky.adicionarHabilidade(HabilidadeFactory.criarEspecialFranky(franky));
        
        return franky;
    }

    public static Aliado criarJinbe() {
        Aliado jinbe = new Aliado("Jinbe", 100, 0.9f, 9, 1, 0);
        jinbe.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(jinbe));
        jinbe.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        jinbe.adicionarHabilidade(HabilidadeFactory.criarEspecialJinbe(jinbe));
        
        return jinbe;
    }

    
    public static Inimigo criarMarinheiro(int nivel) { // (ta escalonando com o nivel atual, pode trocar dps)
        Recompensa loot = new Recompensa(10 * nivel, 5 * nivel); // dinheiro, xp 
        Inimigo marinheiro = new Inimigo("Marinheiro Who", 30 * nivel, 0, 5, loot);// nome, vida, defesa, iniciativa, recompensa
        marinheiro.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        
        return marinheiro;
    }

    public static Inimigo criarPirataInimigo(int nivel) { // (ta escalonando com o nivel atual, pode trocar dps)
        Recompensa loot = new Recompensa(10 * nivel, 5 * nivel); // dinheiro, xp 
        Inimigo pirata = new Inimigo("Pirata Who", 30 * nivel, 0, 5, loot);// nome, vida, defesa, iniciativa, recompensa
        pirata.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        
        return pirata;
    }
    
    public static Inimigo criarChefe(int nivel) {
        Recompensa lootGordo = new Recompensa(500, 200);
        Inimigo boss = new Inimigo("Boss who", 300, 0.6f, 20, lootGordo);
        boss.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasicoInimigo(nivel));
        boss.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return boss;
    }
}
