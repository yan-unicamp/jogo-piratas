package sistema;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;


/**
 * Tela inicial de teste para validar que o libGDX está funcionando.
 * Será substituída por TelaMenu no futuro.
 */
public class TelaMenu implements Screen {

    private final JogoPiratas jogo;

    private Stage palco;
    private Skin skin;

    public TelaMenu(JogoPiratas jogo) {
        this.jogo = jogo;
    }

    @Override
    public void show() {
        // Chamado quando a tela se torna ativa
        palco = new Stage();
        Gdx.input.setInputProcessor(palco);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton botaoJogar = new TextButton("Novo Jogo", skin);
        TextButton botaoSair = new TextButton("Sair", skin);

        Table tabela = new Table();
        tabela.setFillParent(true);

        tabela.add(botaoJogar).pad(10);
        botaoJogar.pad(5, 10, 5, 10);
        botaoJogar.getLabel().setFontScale(1.5f);
        tabela.row();
        tabela.add(botaoSair).pad(10);

        botaoSair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        botaoJogar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(new TelaInicial(jogo));
            }
        });
        
        palco.addActor(tabela);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        palco.act(delta);
        palco.draw();
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
