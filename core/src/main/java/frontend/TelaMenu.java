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
import sistema.GameManager;
import sistema.JogoPiratas;

/**
 * Tela de Menu Principal - funcional com botoes Scene2D.
 *
 * Botoes:
 * "Novo Jogo" -> gameManager.iniciarJogo() -> muda para MAPA
 * "Sair" -> gameManager.encerrarJogo() -> fecha o app
 *
 * TODO Fase 5: Substituir SkinPadrao por skin tematico (piratas),
 * adicionar logo animado, musica de fundo, efeitos de hover.
 */
public class TelaMenu implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    private Stage stage;
    private Skin skin;

    public TelaMenu(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();

        // Layout centralizado com Table
        Table tabela = new Table();
        tabela.setFillParent(true);
        tabela.center();

        // Titulo
        Label titulo = new Label("JOGO PIRATAS", skin);
        titulo.setFontScale(2.5f, 2.5f);
        titulo.setColor(Color.GOLD);

        Label subtitulo = new Label("Uma aventura no Grand Line", skin);
        subtitulo.setColor(Color.LIGHT_GRAY);

        TextButton btnNovoJogo = new TextButton("  Novo Jogo  ", skin);
        TextButton btnContinuar = new TextButton("  Continuar  ", skin);
        TextButton btnSair = new TextButton("     Sair    ", skin);

        btnNovoJogo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (sistema.SaveManager.temSave()) {
                    sistema.SaveManager.deletarSave();
                }
                gameManager.getTripulacao().getAliados().clear();
                gameManager.getTripulacao().getAliadosAtivos().clear();
                gameManager.getTripulacao().getItens().clear();
                gameManager.getMapa().resetar();
                gameManager.getTripulacao().adicionarAliado(factories.PersonagemFactory.criarLuffy());
                gameManager.getTripulacao().adicionarAliadoAtivo(gameManager.getTripulacao().getAliados().get(0));
                sistema.SaveManager.salvar(gameManager);
                
                jogo.setScreen(new frontend.TelaMapa(jogo, gameManager));
            }
        });

        btnContinuar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sistema.SaveManager.carregar(gameManager);
                jogo.setScreen(new frontend.TelaMapa(jogo, gameManager));
            }
        });

        btnSair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.encerrarJogo();
            }
        });

        // Montagem da tabela
        tabela.add(titulo).padBottom(10).row();
        tabela.add(subtitulo).padBottom(60).row();
        
        if (sistema.SaveManager.temSave()) {
            tabela.add(btnContinuar).width(240).height(60).padBottom(20).row();
        }
        
        tabela.add(btnNovoJogo).width(240).height(60).padBottom(20).row();
        tabela.add(btnSair).width(240).height(60).row();

        stage.addActor(tabela);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0.12f, 1f); // azul-noite maritimo
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
