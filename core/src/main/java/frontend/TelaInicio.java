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

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import sistema.GameManager;
import sistema.JogoPiratas;

public class TelaInicio implements Screen {

    private final JogoPiratas jogo;
    private Stage stage;
    private Skin skin;
    private Texture placeholderImage;
    private Texture backgroundTexture;

    public TelaInicio(JogoPiratas jogo) {
        this.jogo = jogo;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();

        Table root = new Table();
        root.setFillParent(true);
        
        backgroundTexture = new Texture(Gdx.files.internal("background_inicio.png"));
        root.setBackground(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        
        stage.addActor(root);

        placeholderImage = new Texture(Gdx.files.internal("logo-onepiece.png"));
        Image img = new Image(placeholderImage);
        img.setScaling(Scaling.fit);
        
        root.add(img).width(1200).height(800).padBottom(40).row();
        
        TextButton btnNovoJogo = new TextButton("  Novo Jogo  ", skin);
        TextButton btnContinuar = new TextButton("  Continuar  ", skin);
        TextButton btnSair = new TextButton("     Sair    ", skin);

        final Table overlayConfirmacao = new Table();
        overlayConfirmacao.setFillParent(true);
        overlayConfirmacao.setVisible(false);
        stage.addActor(overlayConfirmacao);

        final Runnable iniciarNovoJogo = () -> {
            if (sistema.SaveManager.temSave()) {
                sistema.SaveManager.deletarSave();
            }
            jogo.gameManager = new GameManager();
            GameManager gm = jogo.gameManager;
            gm.getTripulacao().getAliados().clear();
            gm.getTripulacao().getAliadosAtivos().clear();
            gm.getTripulacao().getItens().clear();
            gm.getMapa().resetar();
            gm.getTripulacao().adicionarAliado(factories.PersonagemFactory.criarLuffy());
            gm.getTripulacao().adicionarAliadoAtivo(gm.getTripulacao().getAliados().get(0));
            sistema.SaveManager.salvar(gm);
            
            jogo.setScreen(new frontend.TelaMapa(jogo, gm));
        };

        stage.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER && overlayConfirmacao.isVisible()) {
                    iniciarNovoJogo.run();
                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });

        btnNovoJogo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (sistema.SaveManager.temSaveAvancado()) {
                    overlayConfirmacao.clearChildren();
                    Table tint = new Table();
                    tint.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0, 0, 0, 0.85f)));
                    tint.setFillParent(true);
                    
                    Table janela = new Table();
                    janela.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.15f, 0.1f, 0.1f, 1f)));
                    janela.pad(40);
                    
                    Label titulo = new Label("Atencao!", skin);
                    titulo.setFontScale(1.5f);
                    titulo.setColor(Color.RED);
                    
                    Label desc = new Label("Voce possui um save com progresso.\nIniciar um novo jogo ira APAGAR o save anterior.\nDeseja continuar?", skin);
                    desc.setAlignment(Align.center);
                    
                    TextButton btnConfirmar = new TextButton("Sim, apagar save", skin);
                    btnConfirmar.getLabel().setColor(Color.RED);
                    btnConfirmar.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            iniciarNovoJogo.run();
                        }
                    });
                    
                    TextButton btnVoltar = new TextButton("Cancelar", skin);
                    btnVoltar.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            overlayConfirmacao.setVisible(false);
                        }
                    });
                    
                    janela.add(titulo).padBottom(20).row();
                    janela.add(desc).padBottom(40).row();
                    janela.add(btnConfirmar).width(300).height(50).padBottom(15).row();
                    janela.add(btnVoltar).width(300).height(50).row();
                    
                    tint.add(janela);
                    overlayConfirmacao.add(tint).expand().fill();
                    overlayConfirmacao.setVisible(true);
                } else {
                    iniciarNovoJogo.run();
                }
            }
        });

        btnContinuar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!sistema.SaveManager.temSave()) return;
                jogo.gameManager = new GameManager();
                sistema.SaveManager.carregar(jogo.gameManager);
                jogo.setScreen(new frontend.TelaMapa(jogo, jogo.gameManager));
            }
        });
        
        if (!sistema.SaveManager.temSave()) {
            btnContinuar.setDisabled(true);
            btnContinuar.getLabel().setColor(new Color(0.4f, 0.4f, 0.4f, 1f));
        }

        btnSair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table botoesTable = new Table();
        botoesTable.add(btnContinuar).width(200).height(60).padRight(20);
        botoesTable.add(btnNovoJogo).width(200).height(60).padRight(20);
        botoesTable.add(btnSair).width(200).height(60);
        
        root.add(botoesTable).row();
        
        jogo.audio.tocar(sistema.AudioManager.MUSICA_MENU, true);
    }

    @Override
    public void render(float delta) {
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
        if (backgroundTexture != null) backgroundTexture.dispose();
    }
}
