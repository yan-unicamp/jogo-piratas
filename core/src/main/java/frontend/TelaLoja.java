package frontend;

import java.util.List;

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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import entidades.Aliado;
import sistema.GameManager;
import sistema.JogoPiratas;

public class TelaLoja implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;
    private final progressao.Ilha ilhaAtual;
    private Stage stage;
    private Skin skin;
    private Label ouroLbl;

    public TelaLoja(JogoPiratas jogo, GameManager gameManager, progressao.Ilha ilha) {
        this.jogo = jogo;
        this.gameManager = gameManager;
        this.ilhaAtual = ilha;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), jogo.batch);
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();

        Table root = new Table();
        root.setFillParent(true);
        root.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.05f, 0.1f, 0.1f, 1f)));
        stage.addActor(root);

        // Titulo e Ouro
        Label titulo = new Label("Mercador Ambulante", skin);
        titulo.setFontScale(1.8f);
        titulo.setColor(Color.GOLD);
        root.add(titulo).pad(20).row();

        ouroLbl = new Label("Ouro disponivel: " + gameManager.getOuro(), skin);
        ouroLbl.setColor(Color.YELLOW);
        root.add(ouroLbl).padBottom(20).row();

        // Botoes de compras simples
        TextButton btnCura = new TextButton("Comprar Refeicao Especial (Cura 100% HP) - 50 Ouro", skin);
        btnCura.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameManager.gastarOuro(50)) {
                    for (Aliado aliado : gameManager.getTripulacao().getAliados()) {
                        if (aliado.getVidaAtual() > 0) {
                            aliado.curar(aliado.getVidaMaxima());
                        }
                    }
                    atualizarOuro();
                    System.out.println("Tripulacao curada!");
                }
            }
        });
        root.add(btnCura).width(600).height(50).padBottom(10).row();

        TextButton btnReviver = new TextButton("Comprar Elixir (Reviver e Curar) - 100 Ouro", skin);
        btnReviver.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameManager.gastarOuro(100)) {
                    for (Aliado aliado : gameManager.getTripulacao().getAliados()) {
                        aliado.curar(aliado.getVidaMaxima());
                    }
                    atualizarOuro();
                    System.out.println("Tripulacao revivida e curada!");
                }
            }
        });
        root.add(btnReviver).width(600).height(50).padBottom(10).row();

        TextButton btnXp = new TextButton("Comprar Experiencia (100 XP para todos) - 50 Ouro", skin);
        btnXp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameManager.gastarOuro(50)) {
                    java.util.List<sistema.HabilidadePendente> pendentes = new java.util.ArrayList<>();
                    for (Aliado aliado : gameManager.getTripulacao().getAliados()) {
                        java.util.List<entidades.Habilidade> destravadas = aliado.ganharExperiencia(100);
                        for (entidades.Habilidade h : destravadas) {
                            pendentes.add(new sistema.HabilidadePendente(aliado, h));
                        }
                    }
                    atualizarOuro();
                    System.out.println("Experiencia comprada!");
                    if (!pendentes.isEmpty()) {
                        jogo.setScreen(new frontend.TelaAprenderHabilidade(jogo, gameManager, pendentes, () -> {
                            jogo.setScreen(new TelaLoja(jogo, gameManager, ilhaAtual));
                        }));
                    }
                }
            }
        });
        root.add(btnXp).width(600).height(50).padBottom(30).row();

        TextButton btnSair = new TextButton("Sair da Loja", skin);
        btnSair.getLabel().setColor(Color.LIGHT_GRAY);
        btnSair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.getMapa().entrarIlha(ilhaAtual);
                jogo.setScreen(new frontend.TelaMapa(jogo, gameManager));
            }
        });
        root.add(btnSair).width(200).height(50).row();
    }

    private void atualizarOuro() {
        ouroLbl.setText("Ouro disponivel: " + gameManager.getOuro());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
