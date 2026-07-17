package frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entidades.Aliado;
import entidades.Habilidade;
import sistema.GameManager;
import sistema.JogoPiratas;
import sistema.HabilidadePendente;

import java.util.List;

public class TelaAprenderHabilidade implements Screen {
    private JogoPiratas jogo;
    private GameManager gameManager;
    private List<HabilidadePendente> pendentes;
    
    private Stage stage;
    private Skin skin;
    private Table uiTable;

    private Runnable onComplete;

    public TelaAprenderHabilidade(JogoPiratas jogo, GameManager gameManager, List<HabilidadePendente> pendentes, Runnable onComplete) {
        this.jogo = jogo;
        this.gameManager = gameManager;
        this.pendentes = pendentes;
        this.onComplete = onComplete;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920, 1080), jogo.batch);
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();

        uiTable = new Table();
        uiTable.setFillParent(true);
        stage.addActor(uiTable);

        mostrarProximaPendente();
    }

    private void mostrarProximaPendente() {
        uiTable.clear();

        if (pendentes.isEmpty()) {
            if (onComplete != null) {
                onComplete.run();
            } else {
                jogo.setScreen(new TelaMapa(jogo, gameManager));
            }
            return;
        }

        HabilidadePendente atual = pendentes.get(0);
        Aliado aliado = atual.getAliado();
        Habilidade novaHab = atual.getHabilidade();

        Label lblTitulo = new Label(aliado.getNome() + " quer aprender " + novaHab.getNome() + "!", skin);
        lblTitulo.setColor(Color.GOLD);
        lblTitulo.setFontScale(1.5f);
        
        Label lblDesc = new Label("Porem, " + aliado.getNome() + " ja conhece 4 habilidades.\nQual habilidade deseja esquecer?", skin);

        uiTable.add(lblTitulo).padBottom(10).row();
        uiTable.add(lblDesc).padBottom(30).row();

        // Botoes das habilidades atuais
        for (Habilidade habAtual : aliado.getHabilidades()) {
            TextButton btnHab = new TextButton("Esquecer: " + habAtual.getNome(), skin);
            btnHab.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    aliado.getHabilidades().remove(habAtual);
                    aliado.adicionarHabilidade(novaHab);
                    avancar();
                }
            });
            uiTable.add(btnHab).size(350, 50).padBottom(10).row();
        }

        // Botao para cancelar
        TextButton btnCancelar = new TextButton("Desistir de aprender " + novaHab.getNome(), skin);
        btnCancelar.getLabel().setColor(Color.SALMON);
        btnCancelar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                avancar();
            }
        });
        uiTable.add(btnCancelar).size(350, 60).padTop(20).row();
    }

    private void avancar() {
        pendentes.remove(0);
        mostrarProximaPendente();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
