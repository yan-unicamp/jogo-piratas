package frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

import progressao.Ilha;
import sistema.JogoPiratas;

public class TelaTransicaoIlha implements Screen {

    private final JogoPiratas jogo;
    private final Ilha ilha;
    private final Screen proximaTela;
    
    private Stage stage;
    private Skin skin;
    private float tempoPiscando = 0f;
    private Label lblEnter;

    public TelaTransicaoIlha(JogoPiratas jogo, Ilha ilha, Screen proximaTela) {
        this.jogo = jogo;
        this.ilha = ilha;
        this.proximaTela = proximaTela;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();

        Table root = new Table();
        root.setFillParent(true);
        
        // Fundo da ilha esmaecido
        Texture bgTex = jogo.assets.getTextura(ilha.getBgKey());
        Image bgImg = new Image(new TextureRegionDrawable(new TextureRegion(bgTex)));
        bgImg.setScaling(Scaling.fill);
        bgImg.setColor(0.2f, 0.2f, 0.2f, 1f); // Escurecer bem a imagem de fundo
        bgImg.setFillParent(true);
        
        stage.addActor(bgImg);
        stage.addActor(root);

        // Titulo da Ilha
        Label title = new Label(ilha.getNome().toUpperCase(), skin);
        title.setFontScale(2.5f);
        title.setColor(Color.GOLD);
        title.setAlignment(Align.center);
        
        // Texto de Lore
        String descricao = ilha.getDescricao() != null ? ilha.getDescricao() : "Uma ilha misteriosa aguarda...";
        Label descLabel = new Label(descricao, skin);
        descLabel.setFontScale(1.3f);
        descLabel.setColor(Color.WHITE);
        descLabel.setAlignment(Align.center);
        descLabel.setWrap(true);

        lblEnter = new Label("Pressione ENTER ou CLIQUE para continuar", skin);
        lblEnter.setFontScale(1.0f);
        lblEnter.setColor(Color.LIGHT_GRAY);
        lblEnter.setAlignment(Align.center);

        root.add(title).padBottom(40).row();
        root.add(descLabel).width(600).padBottom(60).row();
        root.add(lblEnter).padTop(20).row();
    }

    private float tempoExibicao = 0f;

    @Override
    public void render(float delta) {
        tempoExibicao += delta;
        
        if (tempoExibicao > 0.5f) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) {
                jogo.setScreen(proximaTela);
                return;
            }
        }

        tempoPiscando += delta;
        if (lblEnter != null && tempoExibicao > 0.5f) {
            float alpha = (float) (Math.sin(tempoPiscando * 4) + 1) / 2f;
            lblEnter.setColor(0.8f, 0.8f, 0.8f, 0.2f + (alpha * 0.8f));
        } else if (lblEnter != null) {
            lblEnter.setColor(0.8f, 0.8f, 0.8f, 0f);
        }

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
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
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}
