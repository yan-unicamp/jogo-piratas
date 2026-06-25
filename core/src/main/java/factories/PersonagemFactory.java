package entidades; // ou pacote factories

import progressao.Recompensa;

public class PersonagemFactory {

    public static Aliado criarLuffy() {
        Aliado luffy = new Aliado("Luffy", 100, 0.9f, 10, 1, 0); // nome, vida, defesa, iniciativa, nivel, xp
        luffy.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(luffy));
        luffy.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        return luffy;
    }

    public static Aliado criarZoro() {
        Aliado zoro = new Aliado("Zoro", 90, 0.95f, 9, 1, 0);
        zoro.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(zoro));
        zoro.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return zoro;
    }

    public static Aliado criarNami() {
        Aliado nami = new Aliado("Nami", 70, 0, 6, 1, 0);
        nami.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(nami));
        
        return nami;
    }

    
    public static Inimigo criarMarinheiro(int nivel) { // (ta escalonando com o nivel atual, pode trocar dps)
        Recompensa loot = new Recompensa(10 * nivel, 5 * nivel); // dinheiro, xp 
        Inimigo marinheiro = new Inimigo("Marinheiro Who", 30 * nivel, 0, 5, loot);// nome, vida, defesa, iniciativa, recompensa
        marinheiro.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(marinheiro));
        
        return marinheiro;
    }

    public static Inimigo criarPirataInimigo(int nivel) { // (ta escalonando com o nivel atual, pode trocar dps)
        Recompensa loot = new Recompensa(10 * nivel, 5 * nivel); // dinheiro, xp 
        Inimigo pirata = new Inimigo("Pirata Who", 30 * nivel, 0, 5, loot);// nome, vida, defesa, iniciativa, recompensa
        pirata.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(pirata));
        
        return pirata;
    }
    
    public static Inimigo criarChefe() {
        Recompensa lootGordo = new Recompensa(500, 200);
        Inimigo boss = new Inimigo("Boss who", 300, 0.6f, 20, lootGordo);
        boss.adicionarHabilidade(HabilidadeFactory.criarAtaqueBasico(boss));
        boss.adicionarHabilidade(HabilidadeFactory.criarDefesaHaki());
        
        return boss;
    }
}
