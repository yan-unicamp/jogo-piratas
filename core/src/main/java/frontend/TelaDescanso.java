package frontend;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entidades.Aliado;
import sistema.GameManager;
import sistema.JogoPiratas;

public class TelaDescanso implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;
    private final progressao.Ilha ilhaAtual;

    private Stage stage;
    private Skin skin;
    private ShapeRenderer shapeRenderer;

    private float tempoFogo = 0f;
    private boolean curando = false;
    private float tempoCura = 0f;
    private final float DURACAO_CURA = 2.5f;

    private Vector2 centroFogueira = new Vector2(960, 250);
    private java.util.ArrayList<CardAliado> cardsAliados = new java.util.ArrayList<>();

    public TelaDescanso(JogoPiratas jogo, GameManager gameManager, progressao.Ilha ilha) {
        this.jogo = jogo;
        this.gameManager = gameManager;
        this.ilhaAtual = ilha;
        this.shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        jogo.audio.tocar(sistema.AudioManager.MUSICA_DESCANSO, true);

        stage = new Stage(new FitViewport(1920, 1080), jogo.batch);
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();
        adicionarEstilosBarraHp(skin);

        // Fundo Sunny
        Texture texPerg = jogo.assets.getTextura("backgrounds/sunny.png");
        if (texPerg != null) {
            Image bgImg = new Image(new TextureRegionDrawable(new TextureRegion(texPerg)));
            bgImg.setFillParent(true);
            bgImg.setScaling(Scaling.fill);
            bgImg.setColor(0.5f, 0.4f, 0.6f, 1f); // Tom escuro azulado para noite
            stage.addActor(bgImg);
        }

        // Layout Principal (HUD)
        Table root = new Table();
        root.setFillParent(true);
        root.pad(30);

        // --- HEADER removido a pedido ---
        root.add(new Table()).expand().row(); // Spacer pro centro

        // --- FOOTER (Controles) ---
        Table footer = new Table();
        footer.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.8f, 0.7f, 0.5f, 0.6f)));
        footer.pad(15);

        Label lblDicas = new Label("A: Descansar   B: Voltar ao Mapa", skin);
        lblDicas.setColor(Color.BROWN);

        TextButton btnDescansar = new TextButton("Descansar", skin);
        btnDescansar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                iniciarCura(0.30f); // 30% placeholder for the mode
            }
        });

        TextButton btnVoltar = new TextButton("Sair", skin);
        btnVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(!curando) {
                    gameManager.getMapa().entrarIlha(ilhaAtual);
                    jogo.setScreen(new frontend.TelaMapa(jogo, gameManager));
                }
            }
        });

        Table containerBotoes = new Table();
        containerBotoes.add(btnDescansar).padRight(20).width(250).height(60);
        containerBotoes.add(btnVoltar).width(180).height(60);

        footer.add(lblDicas).padRight(100);
        footer.add(containerBotoes).right();

        root.add(footer).fillX().bottom();
        stage.addActor(root);

        // --- PERSONAGENS (Radial Layout) ---
        Table groupPersonagens = new Table();
        groupPersonagens.setFillParent(true);

        List<Aliado> aliados = gameManager.getTripulacao().getAliados();
        // Separando em duas fileiras
        List<Aliado> filaFrente = new java.util.ArrayList<>();
        List<Aliado> filaTras = new java.util.ArrayList<>();

        for (int i = 0; i < aliados.size(); i++) {
            if (i < 6) filaFrente.add(aliados.get(i));
            else filaTras.add(aliados.get(i));
        }

        posicionarArco(filaFrente, 750, 320, 165, 15, groupPersonagens);
        posicionarArco(filaTras, 880, 550, 150, 30, groupPersonagens);

        stage.addActor(groupPersonagens);
    }

    private void posicionarArco(List<Aliado> lista, float raioX, float raioY, float anguloInicio, float anguloFim, Table group) {
        if (lista.isEmpty()) return;
        float anguloPasso = lista.size() > 1 ? (anguloFim - anguloInicio) / (lista.size() - 1) : 0;
        
        for (int i = 0; i < lista.size(); i++) {
            Aliado a = lista.get(i);
            float angulo = anguloInicio + (i * anguloPasso);
            float rad = (float) Math.toRadians(angulo);

            float cx = centroFogueira.x + raioX * (float) Math.cos(rad);
            float cy = centroFogueira.y + raioY * (float) Math.sin(rad);

            CardAliado card = new CardAliado(a);
            // Center the card on cx, cy
            card.setPosition(cx - card.getWidth() / 2, cy - card.getHeight() / 2);
            cardsAliados.add(card);
            group.addActor(card);
        }
    }



    private void iniciarCura(float percentualCura) {
        if (curando) return;
        curando = true;
        tempoCura = 0f;

        for (CardAliado card : cardsAliados) {
            float hpAtual = card.aliado.getVidaAtual();
            float maxHp = card.aliado.getVidaMaxima();
            float hpCura = maxHp * percentualCura;
            float hpFinal = Math.min(maxHp, hpAtual + hpCura);
            
            card.hpInicialAnim = hpAtual;
            card.hpFinalAnim = hpFinal;
            
            // Aplica no modelo
            card.aliado.curar((int) hpCura);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.03f, 0.01f, 1f); // Black/Dark
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tempoFogo += delta;

        // Update Animations
        if (curando) {
            tempoCura += delta;
            float progress = Math.min(1f, tempoCura / DURACAO_CURA);
            float easeProgress = Interpolation.pow2Out.apply(progress);

            for (CardAliado card : cardsAliados) {
                float currentVisualHp = card.hpInicialAnim + (card.hpFinalAnim - card.hpInicialAnim) * easeProgress;
                card.atualizarVisual(currentVisualHp);
            }

            if (tempoCura >= DURACAO_CURA + 1f) {
                curando = false;
                // Volta ao mapa após a animação e um tempinho
                gameManager.getMapa().entrarIlha(ilhaAtual);
                jogo.setScreen(new frontend.TelaMapa(jogo, gameManager));
            }
        }

        stage.act(delta);
        stage.draw();

        // Draw Bonfire + Healing Lines
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Bonfire glowing center and flames
        shapeRenderer.setColor(1f, 0.4f, 0f, 0.3f);
        shapeRenderer.circle(centroFogueira.x, centroFogueira.y, 80 + (float)Math.sin(tempoFogo*8)*15);
        shapeRenderer.setColor(1f, 0.6f, 0f, 0.5f);
        shapeRenderer.circle(centroFogueira.x, centroFogueira.y, 50 + (float)Math.sin(tempoFogo*12)*10);

        // Chama Vermelha/Laranja escuro
        shapeRenderer.setColor(0.9f, 0.2f, 0.05f, 0.9f);
        shapeRenderer.triangle(
            centroFogueira.x - 45, centroFogueira.y - 15,
            centroFogueira.x + 45, centroFogueira.y - 15,
            centroFogueira.x + (float)Math.sin(tempoFogo * 15)*25, centroFogueira.y + 110 + (float)Math.sin(tempoFogo * 20)*20
        );

        // Chama Laranja viva
        shapeRenderer.setColor(1f, 0.5f, 0f, 0.95f);
        shapeRenderer.triangle(
            centroFogueira.x - 30, centroFogueira.y - 15,
            centroFogueira.x + 30, centroFogueira.y - 15,
            centroFogueira.x - 20 + (float)Math.sin(tempoFogo * 12)*20, centroFogueira.y + 85 + (float)Math.sin(tempoFogo * 17)*25
        );
        shapeRenderer.triangle(
            centroFogueira.x, centroFogueira.y - 15,
            centroFogueira.x + 40, centroFogueira.y - 15,
            centroFogueira.x + 30 + (float)Math.cos(tempoFogo * 14)*15, centroFogueira.y + 75 + (float)Math.sin(tempoFogo * 11)*20
        );

        // Chama Amarela (nucleo)
        shapeRenderer.setColor(1f, 0.9f, 0.1f, 1f);
        shapeRenderer.triangle(
            centroFogueira.x - 18, centroFogueira.y - 15,
            centroFogueira.x + 18, centroFogueira.y - 15,
            centroFogueira.x + (float)Math.sin(tempoFogo * 18)*10, centroFogueira.y + 55 + (float)Math.sin(tempoFogo * 10)*15
        );

        // Healing Lines
        if (curando) {
            float progress = Math.min(1f, tempoCura / DURACAO_CURA);
            float alpha = 1f - progress;
            if (alpha < 0) alpha = 0;

            for (CardAliado card : cardsAliados) {
                Vector2 target = new Vector2(card.getX() + 45, card.getY() + card.getHeight() / 2); // Center of portrait
                
                // Draw a thick line using rectLine
                shapeRenderer.setColor(0.3f, 1f, 0.3f, alpha * 0.8f); // Bright green
                shapeRenderer.rectLine(centroFogueira, target, 4f);
                
                // Aura around character portrait
                shapeRenderer.setColor(0.3f, 1f, 0.3f, alpha * 0.4f);
                shapeRenderer.circle(target.x, target.y, 50);
            }
        }
        
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void adicionarEstilosBarraHp(Skin s) {
        Texture fundoBarra = SkinPadrao.textura1x1(0.15f, 0.15f, 0.15f, 1f);
        Texture preenchimento = SkinPadrao.textura1x1(0.20f, 0.80f, 0.20f, 1f);

        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle();
        barStyle.background = new TextureRegionDrawable(fundoBarra);
        barStyle.knob = new TextureRegionDrawable(SkinPadrao.textura1x1(0f, 0f, 0f, 0f));
        barStyle.knobBefore = new TextureRegionDrawable(preenchimento);
        s.add("hp", barStyle);
    }

    class CardAliado extends Table {
        public Aliado aliado;
        public ProgressBar barraHp;
        public Label hpLabel;
        
        public float hpInicialAnim;
        public float hpFinalAnim;

        public CardAliado(Aliado a) {
            this.aliado = a;
            this.setSize(260, 90);
            this.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.85f, 0.75f, 0.55f, 0.9f))); // Pergaminho

            // Portrait Box
            Table portraitBox = new Table();
            portraitBox.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.1f, 0.1f, 0.1f, 1f)));
            
            Texture tex = a.getTextura();
            if (tex != null) {
                Image img = new Image(new TextureRegionDrawable(new TextureRegion(tex)));
                img.setScaling(Scaling.fit);
                portraitBox.add(img).expand().fill().pad(2);
            }
            
            // Round masking or background (we just use a square portrait box to keep it simple, ShapeRenderer draws green aura over it)
            this.add(portraitBox).size(80, 80).padLeft(5).padRight(10);

            // Info Box
            Table info = new Table();
            Label nome = new Label(a.getNome(), skin);
            nome.setColor(Color.BLACK);
            info.add(nome).left().row();

            barraHp = new ProgressBar(0, a.getVidaMaxima(), 0.1f, false, skin, "hp");
            barraHp.setValue(a.getVidaAtual());
            info.add(barraHp).width(140).height(12).padTop(5).padBottom(5).row();

            int pct = (int)((a.getVidaAtual() * 100f) / a.getVidaMaxima());
            hpLabel = new Label("HP: " + pct + "% / 100%", skin);
            hpLabel.setColor(Color.DARK_GRAY);
            hpLabel.setFontScale(0.9f);
            info.add(hpLabel).left();

            this.add(info).expand().fill().padTop(10);
        }

        public void atualizarVisual(float hpVisual) {
            barraHp.setValue(hpVisual);
            int pct = (int)((hpVisual * 100f) / aliado.getVidaMaxima());
            hpLabel.setText("HP: " + pct + "% / 100%");
        }
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); shapeRenderer.dispose(); }
}
