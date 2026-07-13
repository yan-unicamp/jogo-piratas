package sistema;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import frontend.TelaMenu;

/**
 * Classe principal do jogo que estende Game do libGDX.
 * Gerencia as telas e recursos globais.
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

        // Inicia com a tela de menu do pacote frontend
        setScreen(new TelaMenu(this));
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
