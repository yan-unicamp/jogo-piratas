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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import sistema.GameManager;
import sistema.JogoPiratas;

public class TelaInicio implements Screen {

    private final JogoPiratas jogo;
    private Stage stage;
    private Skin skin;
    private Texture placeholderImage;

    public TelaInicio(JogoPiratas jogo) {
        this.jogo = jogo;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // Placeholder for future image
        placeholderImage = SkinPadrao.textura1x1(0.2f, 0.2f, 0.2f, 1f); // Dark gray
        Image img = new Image(new TextureRegionDrawable(new TextureRegion(placeholderImage)));
        img.setScaling(Scaling.fit);
        
        Label title = new Label("ONE PIECE - JOGO PIRATAS", skin);
        title.setFontScale(2.0f);
        title.setColor(Color.GOLD);
        title.setAlignment(Align.center);

        Label lblEnter = new Label("Pressione ENTER ou CLIQUE para iniciar", skin);
        lblEnter.setFontScale(1.2f);
        lblEnter.setAlignment(Align.center);

        root.add(title).padBottom(20).row();
        root.add(img).width(400).height(300).padBottom(40).row();
        root.add(lblEnter).row();
    }

    @Override
    public void render(float delta) {
        // Handle input
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) {
            if (jogo.gameManager == null) {
                jogo.gameManager = new GameManager(); // Initialize the game manager
            }
            jogo.setScreen(new TelaMapa(jogo, jogo.gameManager));
            return;
        }

        // Draw screen
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.08f, 1f);
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
        if (placeholderImage != null) placeholderImage.dispose();
    }
}
