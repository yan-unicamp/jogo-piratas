package sistema;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;


/**
 * Tela inicial de teste para validar que o libGDX está funcionando.
 * Será substituída por TelaMenu no futuro.
 */
public class TelaInicial implements Screen {

    private final JogoPiratas jogo;
    private final Texture imgLuffy;
        private float luffyX = 100;
        private float luffyY = 100;
        private final float largura = 150;
        private final float altura = 150;

    public TelaInicial(JogoPiratas jogo) {
        this.jogo = jogo;
        imgLuffy = new Texture("luffy.png");
    }

    @Override
    public void show() {
        // Chamado quando a tela se torna ativa
    }

    @Override
    public void render(float delta) {
        // Limpa a tela com a cor preta
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        // DAQUI PRA FRENTE É COM VOCÊ!

        float velocidade = 300f;

        if (Gdx.input.isKeyPressed(Keys.D)) {
            luffyX += velocidade * delta;
        }
        if (Gdx.input.isKeyPressed(Keys.A)) {
            luffyX -= velocidade * delta;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            luffyY += velocidade * delta;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            luffyY -= velocidade * delta;
        }

        jogo.batch.begin();
        jogo.batch.draw(imgLuffy, luffyX, luffyY, largura, altura);
        jogo.font.draw(jogo.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 700);
        jogo.batch.end();
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
    public void dispose() {
        imgLuffy.dispose();
    }
}
