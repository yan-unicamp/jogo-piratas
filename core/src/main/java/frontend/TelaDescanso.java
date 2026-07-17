package frontend;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import entidades.Aliado;
import sistema.EstadoJogo;
import sistema.GameManager;
import sistema.JogoPiratas;

/**
 * Interface grafica do No de Descanso.
 *
 * Exibe cards com HP atual/maximo de cada aliado vivo.
 * Botao "Descansar" cura 30% do HP maximo de cada aliado e avanca para o mapa.
 * Botao "Continuar" avanca para o mapa sem curar.
 */
public class TelaDescanso implements Screen {

    private static final float CURA_PERCENTUAL = 0.30f;

    private final JogoPiratas jogo;
    private final GameManager gameManager;
    private final progressao.Ilha ilhaAtual;

    private Stage stage;
    private Skin skin;

    public TelaDescanso(JogoPiratas jogo, GameManager gameManager, progressao.Ilha ilha) {
        this.jogo = jogo;
        this.gameManager = gameManager;
        this.ilhaAtual = ilha;
    }

    @Override
    public void show() {
        jogo.audio.tocar(sistema.AudioManager.MUSICA_DESCANSO, true);
        
        stage = new Stage(new ScreenViewport(), jogo.batch);
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();

        // Skin extra: barra de HP (verde sobre fundo escuro)
        adicionarEstilosBarraHp(skin);

        // Adiciona fundo do Sunny
        Texture bgTex = jogo.assets.getTextura("backgrounds/sunny.png");
        com.badlogic.gdx.scenes.scene2d.ui.Image bgImg = new com.badlogic.gdx.scenes.scene2d.ui.Image(
                new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(
                        new com.badlogic.gdx.graphics.g2d.TextureRegion(bgTex)));
        bgImg.setFillParent(true);
        bgImg.setScaling(com.badlogic.gdx.utils.Scaling.fill);
        stage.addActor(bgImg);

        // Overlay escuro
        Table overlayEscuro = new Table();
        overlayEscuro.setFillParent(true);
        overlayEscuro.setBackground(new TextureRegionDrawable(texturaCor(0, 0, 0, 0.65f)));
        stage.addActor(overlayEscuro);

        Table raiz = new Table();
        raiz.setFillParent(true);
        raiz.center();

        // --- Titulo ---
        Label titulo = new Label("Descansar no Sunny", skin);
        titulo.setFontScale(2f);
        titulo.setColor(new Color(0.95f, 0.80f, 0.40f, 1f)); // dourado quente

        Label subtitulo = new Label("Sua tripulacao pode recuperar forcas antes de continuar.", skin);
        subtitulo.setColor(Color.LIGHT_GRAY);

        raiz.add(titulo).padBottom(8).row();
        raiz.add(subtitulo).padBottom(40).row();

        // --- Cards dos aliados ---
        List<Aliado> aliados = gameManager.getTripulacao().getAliados();
        Table cardsTable = new Table();

        for (Aliado aliado : aliados) {
            Table card = criarCardAliado(aliado);
            cardsTable.add(card).width(220).padRight(20);
        }
        raiz.add(cardsTable).padBottom(50).row();

        // --- Calculo da cura para o label informativo ---
        int curaPorAliado = calcularCura(aliados);
        Label infoLabel = new Label("Descansar recupera ~" + curaPorAliado + " HP por aliado (30% do maximo).", skin);
        infoLabel.setColor(new Color(0.6f, 0.9f, 0.6f, 1f));
        raiz.add(infoLabel).padBottom(30).row();

        // --- Botoes ---
        Table botoesTable = new Table();

        TextButton btnDescansar  = new TextButton("  Descansar  ", skin);
        TextButton btnContinuar  = new TextButton("  Continuar sem descansar  ", skin);

        btnDescansar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (Aliado a : gameManager.getTripulacao().getAliadosVivos()) {
                    int cura = (int)(a.getVidaMaxima() * CURA_PERCENTUAL);
                    a.curar(cura);
                }
                gameManager.getMapa().entrarIlha(ilhaAtual);
                jogo.setScreen(new frontend.TelaMapa(jogo, gameManager));
            }
        });

        btnContinuar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.getMapa().entrarIlha(ilhaAtual);
                jogo.setScreen(new frontend.TelaMapa(jogo, gameManager));
            }
        });

        botoesTable.add(btnDescansar).width(200).height(55).padRight(20);
        botoesTable.add(btnContinuar).width(300).height(55);
        raiz.add(botoesTable).row();

        stage.addActor(raiz);
    }

    /** Monta um card visual para um aliado com nome, nivel e barra de HP. */
    private Table criarCardAliado(Aliado aliado) {
        Table card = new Table();
        card.setBackground(new TextureRegionDrawable(texturaCor(0.10f, 0.07f, 0.02f, 0.85f)));

        // Nome e nivel
        Label nomeLabel = new Label(aliado.getNome(), skin);
        nomeLabel.setColor(Color.WHITE);

        Label nivelLabel = new Label("Nv. " + aliado.getNivel(), skin);
        nivelLabel.setColor(new Color(0.8f, 0.8f, 0.5f, 1f));

        // HP texto
        String hpTexto = aliado.getVidaAtual() + " / " + aliado.getVidaMaxima() + " HP";
        Color corHp = aliado.estaVivo() ? new Color(0.4f, 1f, 0.4f, 1f) : Color.RED;
        Label hpLabel = new Label(hpTexto, skin);
        hpLabel.setColor(corHp);

        // Barra de HP
        float hpRatio = aliado.getVidaMaxima() > 0
                ? (float) aliado.getVidaAtual() / aliado.getVidaMaxima()
                : 0f;
        ProgressBar barraHp = new ProgressBar(0f, 1f, 0.01f, false, skin, "hp");
        barraHp.setValue(hpRatio);

        card.pad(14);
        card.add(nomeLabel).left().row();
        card.add(nivelLabel).left().padBottom(10).row();
        card.add(barraHp).width(180).height(14).padBottom(4).row();
        card.add(hpLabel).left().row();

        return card;
    }

    /** Calcula um valor representativo de cura (usa o primeiro aliado como referencia). */
    private int calcularCura(List<Aliado> aliados) {
        if (aliados.isEmpty()) return 0;
        return (int)(aliados.get(0).getVidaMaxima() * CURA_PERCENTUAL);
    }

    /** Adiciona estilo de ProgressBar ao skin para a barra de HP. */
    private void adicionarEstilosBarraHp(Skin s) {
        BitmapFont font = s.get("default-font", BitmapFont.class);

        Texture fundoBarra = texturaCor(0.15f, 0.15f, 0.15f, 1f);
        Texture preenchimento = texturaCor(0.20f, 0.80f, 0.20f, 1f);

        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle();
        barStyle.background  = new TextureRegionDrawable(fundoBarra);
        barStyle.knob        = new TextureRegionDrawable(texturaCor(0f, 0f, 0f, 0f));  // invisivel
        barStyle.knobBefore  = new TextureRegionDrawable(preenchimento);
        s.add("hp", barStyle);
    }

    /** Cria uma Texture 1A—1 de cor solida. */
    private static Texture texturaCor(float r, float g, float b, float a) {
        Pixmap px = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        px.setColor(r, g, b, a);
        px.fill();
        Texture tex = new Texture(px);
        px.dispose();
        return tex;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.10f, 0.06f, 0.01f, 1f); // sepia quente de acampamento
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
