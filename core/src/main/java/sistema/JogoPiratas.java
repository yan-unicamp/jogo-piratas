package sistema;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Classe principal do jogo — estende Game do LibGDX.
 *
 * Responsabilidades:
 *  - Instanciar Assets (antes do GameManager, para que o contexto GL já exista).
 *  - Instanciar e manter o GameManager.
 *  - Prover SpriteBatch e BitmapFont compartilhados entre Screens.
 */
public class JogoPiratas extends Game {

    /** SpriteBatch compartilhado (caro de criar — use apenas um). */
    public SpriteBatch batch;

    /** Fonte padrão compartilhada entre telas. */
    public BitmapFont font;

    /** Carregador/cache de texturas. */
    private Assets assets;

    /** Máquina de estados central. */
    private GameManager gameManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font  = new BitmapFont();
        font.setColor(Color.WHITE);

        // Assets precisa ser inicializado APÓS o contexto OpenGL estar pronto
        assets = new Assets();
        assets.inicializar();

        // GameManager recebe referência ao jogo e ao sistema de assets
        gameManager = new GameManager(this, assets);
        gameManager.mudarEstado(EstadoJogo.MENU);
    }

    @Override
    public void render() {
        super.render(); // delega para a Screen ativa
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        assets.dispose();
        Screen currentScreen = getScreen();
        if (currentScreen != null) currentScreen.dispose();
    }

    public Assets     getAssets()     { return assets; }
    public GameManager getGameManager() { return gameManager; }
}
