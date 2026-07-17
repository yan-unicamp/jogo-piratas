package frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import sistema.EstadoJogo;
import sistema.GameManager;
import sistema.JogoPiratas;

/**
 * Interface grafica de Game Over.
 *
 * Exibe a etapa alcancada e o ouro coletado.
 * Botao "Menu Principal" reinicia o jogo do zero (volta ao MENU,
 * que recria o GameManager via JogoPiratas).
 */
public class TelaGameOver implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    private Stage stage;
    private Skin  skin;

    public TelaGameOver(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();

        int etapa = gameManager.getMapa().getEtapaAtual();
        int ouro  = gameManager.getTripulacao().getDinheiro();

        Table raiz = new Table();
        raiz.setFillParent(true);
        raiz.center();

        // -- Titulo "Game Over" --
        Label titulo = new Label("GAME OVER", skin);
        titulo.setFontScale(3f);
        titulo.setColor(new Color(0.85f, 0.1f, 0.1f, 1f)); // vermelho dramatico

        // -- Subtitulo --
        Label subtitulo = new Label("A tripulacao foi derrotada...", skin);
        subtitulo.setColor(new Color(0.7f, 0.7f, 0.7f, 1f));

        // -- Estatisticas da partida --
        Label statEtapa = new Label("Etapas percorridas: " + etapa, skin);
        statEtapa.setColor(Color.LIGHT_GRAY);

        Label statOuro = new Label("Ouro acumulado: " + ouro, skin);
        statOuro.setColor(new Color(1f, 0.85f, 0.2f, 1f));

        // -- Botao --
        TextButton btnMenu = new TextButton("  Menu Principal  ", skin);
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.mudarEstado(EstadoJogo.MENU);
            }
        });

        raiz.add(titulo).padBottom(12).row();
        raiz.add(subtitulo).padBottom(50).row();
        raiz.add(statEtapa).padBottom(8).row();
        raiz.add(statOuro).padBottom(60).row();
        raiz.add(btnMenu).width(260).height(60).row();

        stage.addActor(raiz);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.12f, 0f, 0f, 1f); // vermelho-escuro dramatico
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
