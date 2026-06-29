package sistema;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classe principal do jogo que estende Game do libGDX.
 *
 * Responsabilidades:
 *  - Instanciar e manter o GameManager (máquina de estados).
 *  - Prover recursos globais compartilhados entre Screens (batch, font).
 *  - Delegar a renderização para a Screen ativa via super.render().
 *
 * NÃO chame jogo.setScreen() diretamente fora desta classe.
 * Use gameManager.mudarEstado(EstadoJogo) para transitar entre telas.
 */
public class JogoPiratas extends Game {

    /** SpriteBatch compartilhado entre todas as telas (caro de criar, use um só). */
    public SpriteBatch batch;

    /** Fonte padrão compartilhada entre telas. */
    public BitmapFont font;

    /** Máquina de estados central — único ponto de controle do fluxo do jogo. */
    private GameManager gameManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        // GameManager recebe referência ao jogo para poder chamar setScreen()
        gameManager = new GameManager(this);

        // Ponto de entrada: sempre começa no Menu
        gameManager.mudarEstado(EstadoJogo.MENU);
    }

    @Override
    public void render() {
        // Game.render() delega automaticamente para a Screen ativa
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        Screen currentScreen = getScreen();
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }

    /** @return O GameManager — acessado pelo DesktopLauncher se necessário. */
    public GameManager getGameManager() { return gameManager; }
}
