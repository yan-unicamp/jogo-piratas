package sistema;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Classe principal do jogo que estende Game do libGDX.
 * Gerencia as telas (Screen) e recursos compartilhados.
 */
public class JogoPiratas extends Game {

    /** SpriteBatch compartilhado entre todas as telas (evita criar vários) */
    public SpriteBatch batch;

    /** Fonte padrão compartilhada */
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // fonte padrão do libGDX
        font.setColor(Color.WHITE);

        // Inicia com a tela de teste
        setScreen(new TelaInicial(this));
    }

    @Override
    public void render() {
        // Game.render() delega para a Screen ativa automaticamente
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();

        // Dispor a tela atual se existir
        Screen currentScreen = getScreen();
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
