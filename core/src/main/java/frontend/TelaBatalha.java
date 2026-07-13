package frontend;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import sistema.JogoPiratas;

/**
 * Interface gráfica da Batalha.
 * Lê informações do GerenciadorDeBatalha.
 */
public class TelaBatalha implements Screen {

    private final JogoPiratas jogo;

    public TelaBatalha(JogoPiratas jogo) {
        this.jogo = jogo;
    }

    @Override
    public void show() {
        // Inicializar imagens da batalha
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
