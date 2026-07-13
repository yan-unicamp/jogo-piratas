package frontend;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import sistema.JogoPiratas;

/**
 * Interface gráfica do Menu Principal.
 */
public class TelaMenu implements Screen {

    private final JogoPiratas jogo;

    public TelaMenu(JogoPiratas jogo) {
        this.jogo = jogo;
    }

    @Override
    public void show() {
        // Inicializar botões e tabelas aqui
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        // palco.act() e palco.draw()
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
