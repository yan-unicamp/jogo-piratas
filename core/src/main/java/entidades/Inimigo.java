package entidades;

import progressao.Recompensa;

/**
 * Representa um inimigo do jogo.
 *
 * Além dos atributos de Personagem, carrega:
 *  - {@code recompensa}: ouro e XP concedidos ao ser derrotado.
 *  - {@code spriteKey}: caminho relativo da textura, ex: "inimigos/bosses/buggy.png".
 *    Usado por Assets.getTextura() para carregar a imagem correta.
 *  - {@code boss}: true se este inimigo é o chefe da ilha (exibe coroa na UI).
 */
public class Inimigo extends Personagem {

    public Inimigo(String nome, int vidaMaxima, float defesa, int iniciativa, Recompensa recompensa) {
        super(nome, vidaMaxima, defesa, iniciativa);
        this.recompensa = recompensa;
        this.spriteKey  = spriteKey;
        this.boss       = boss;
    }

    /**
     * Construtor de compatibilidade (mantido para código existente).
     * Usa sprite de capanga genérico e não é boss.
     */
    public Inimigo(String nome, int vidaMaxima, int defesa, int iniciativa,
                   Recompensa recompensa) {
        this(nome, vidaMaxima, defesa, iniciativa, recompensa,
             "inimigos/capangas/capanga_pirata.png", false);
    }

    public Recompensa getRecompensa() { return recompensa; }
    public String     getSpriteKey()  { return spriteKey; }
    public boolean    isBoss()        { return boss; }
}
