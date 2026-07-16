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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sistema.EstadoJogo;
import sistema.GameManager;
import sistema.JogoPiratas;

/**
 * Tela de Vitoria - exibida quando o jogador completa todas as 22 ilhas
 * do Grand Line e derrota Imu-sama em Elbaph.
 */
public class TelaVitoria implements Screen {

    private final JogoPiratas  jogo;
    private final GameManager  gameManager;

    private Stage stage;
    private Skin  skin;

    public TelaVitoria(JogoPiratas jogo, GameManager gameManager) {
        this.jogo        = jogo;
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin  = SkinPadrao.criar();

        Table ui = new Table();
        ui.setFillParent(true);
        ui.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.02f, 0.02f, 0.08f, 1f)));
        ui.center();
        stage.addActor(ui);

        // Titulo epico
        Label titulo = new Label("*  VOCE CONQUISTOU O GRAND LINE!  *", skin);
        titulo.setFontScale(1.8f);
        titulo.setColor(Color.GOLD);
        ui.add(titulo).padBottom(24).row();

        // Citacao do Luffy
        Label cita = new Label("\"Vou ser o Rei dos Piratas!\"  - Monkey D. Luffy", skin);
        cita.setFontScale(1.2f);
        cita.setColor(new Color(1f, 0.85f, 0.3f, 1f));
        ui.add(cita).padBottom(40).row();

        // Mensagem
        Label msg = new Label(
            "Voce navegou todos os mares, enfrentou os Almirantes,\n" +
            "desbancou os Imperadores e destruiu o trono vazio.\n\n" +
            "A Era dos Piratas chegou ao seu apice.\n" +
            "O Chapeu de Palha reina sobre o mundo!",
            skin);
        msg.setFontScale(1.05f);
        msg.setColor(Color.WHITE);
        msg.setAlignment(com.badlogic.gdx.utils.Align.center);
        ui.add(msg).padBottom(48).row();

        // Estatisticas
        int ouro = gameManager.getTripulacao().getDinheiro();
        Label stats = new Label("Ouro acumulado: " + ouro + " Berries", skin);
        stats.setColor(new Color(1f, 0.75f, 0.2f, 1f));
        ui.add(stats).padBottom(40).row();

        // Botao - jogar novamente
        TextButton btnNovaJornada = new TextButton("*  Nova Jornada", skin);
        btnNovaJornada.getLabel().setColor(Color.GOLD);
        btnNovaJornada.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.mudarEstado(EstadoJogo.MENU);
            }
        });
        ui.add(btnNovaJornada).width(260).height(60).padBottom(16).row();

        // Botao - sair
        TextButton btnSair = new TextButton("Encerrar Jogo", skin);
        btnSair.getLabel().setColor(new Color(0.8f, 0.8f, 0.8f, 1f));
        btnSair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.encerrarJogo();
            }
        });
        ui.add(btnSair).width(260).height(50);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.02f, 0.02f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause()   {}
    @Override public void resume()  {}
    @Override public void hide()    {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
