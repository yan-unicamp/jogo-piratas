package frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import entidades.Aliado;
import entidades.Habilidade;
import sistema.GameManager;
import sistema.JogoPiratas;

public class TelaTripulacao implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;
    private final Screen telaAnterior;
    private Stage stage;
    private Skin skin;
    private Table painelDetalhes;

    public TelaTripulacao(JogoPiratas jogo, GameManager gameManager, Screen telaAnterior) {
        this.jogo = jogo;
        this.gameManager = gameManager;
        this.telaAnterior = telaAnterior;
    }

    private Table painelEsquerdo;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), jogo.batch);
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();

        Table root = new Table();
        root.setFillParent(true);
        root.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.1f, 0.1f, 0.15f, 1f)));
        stage.addActor(root);

        // Header
        Label titulo = new Label("Tripulacao", skin);
        titulo.setFontScale(1.5f);
        titulo.setColor(Color.GOLD);
        root.add(titulo).pad(20).colspan(2).row();

        // Layout: Lista na esquerda, Detalhes na direita
        painelEsquerdo = new Table();
        painelDetalhes = new Table();
        
        painelDetalhes.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.2f, 0.2f, 0.25f, 1f)));
        painelDetalhes.pad(20);

        atualizarPainelEsquerdo();

        ScrollPane scrollLista = new ScrollPane(painelEsquerdo, skin);
        scrollLista.setScrollingDisabled(true, false);
        
        root.add(scrollLista).width(250).expandY().fillY().pad(20);
        root.add(painelDetalhes).expand().fill().pad(20).row();

        // Botao voltar
        TextButton btnVoltar = new TextButton("Voltar", skin);
        btnVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(telaAnterior);
            }
        });
        root.add(btnVoltar).width(200).height(50).padBottom(20).colspan(2).row();

        // Mostrar o primeiro por padrao, se existir
        if (!gameManager.getTripulacao().getAliados().isEmpty()) {
            mostrarDetalhes(gameManager.getTripulacao().getAliados().get(0));
        }
    }

    private void atualizarPainelEsquerdo() {
        painelEsquerdo.clear();
        for (final Aliado aliado : gameManager.getTripulacao().getAliados()) {
            boolean isAtivo = gameManager.getTripulacao().getAliadosAtivos().contains(aliado);
            TextButton btnAliado = new TextButton(removerAcentos(aliado.getNome()), skin);
            if (isAtivo) {
                btnAliado.getLabel().setColor(Color.GOLD);
            }
            btnAliado.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mostrarDetalhes(aliado);
                }
            });
            painelEsquerdo.add(btnAliado).width(200).height(50).padBottom(10).row();
        }
    }

    private void mostrarDetalhes(final Aliado aliado) {
        painelDetalhes.clear();
        
        Label lblNome = new Label(removerAcentos(aliado.getNome()), skin);
        lblNome.setFontScale(1.3f);
        lblNome.setColor(Color.CYAN);
        
        int nextXp = 100 * aliado.getNivel();
        Label lblStatus = new Label(
            "Nivel: " + aliado.getNivel() + "\n" +
            "XP: " + aliado.getExperiencia() + " / " + nextXp + "\n" +
            "HP: " + aliado.getVidaAtual() + " / " + aliado.getVidaMaxima() + "\n" +
            "DEF: " + String.format("%.1f", aliado.getDefesa()), 
            skin
        );
        lblStatus.setAlignment(Align.left);

        painelDetalhes.add(lblNome).padBottom(20).colspan(2).row();
        
        Texture tex = aliado.getTextura();
        if (tex != null) {
            Image img = new Image(tex);
            painelDetalhes.add(img).size(120, 120).padRight(20);
        } else {
            Image img = new Image(new TextureRegionDrawable(SkinPadrao.textura1x1(0.3f, 0.3f, 0.3f, 1f)));
            painelDetalhes.add(img).size(120, 120).padRight(20);
        }
        
        painelDetalhes.add(lblStatus).expandX().fillX().row();
        
        Label lblHabilidadesTitulo = new Label("Habilidades:", skin);
        lblHabilidadesTitulo.setColor(Color.GOLD);
        painelDetalhes.add(lblHabilidadesTitulo).padTop(20).colspan(2).row();
        
        Table habTable = new Table();
        for (Habilidade hab : aliado.getHabilidades()) {
            String tipo = hab.getTipo().toString();
            String desc = hab.getNome() + " (" + tipo + " - Poder: " + String.format("%.1f", hab.getValorPoder()) + ")";
            Label lblHab = new Label(removerAcentos(desc), skin);
            habTable.add(lblHab).left().padBottom(5).row();
        }
        painelDetalhes.add(habTable).colspan(2).padTop(10).left().row();

        // Botao Convocar / Desconvocar
        final boolean isAtivo = gameManager.getTripulacao().getAliadosAtivos().contains(aliado);
        String txtBotao = isAtivo ? "Desconvocar" : "Convocar";
        TextButton btnConvocar = new TextButton(txtBotao, skin);
        if (isAtivo) {
            btnConvocar.getLabel().setColor(new Color(1f, 0.4f, 0.4f, 1f));
        } else {
            btnConvocar.getLabel().setColor(new Color(0.4f, 1f, 0.4f, 1f));
        }
        final Label lblAviso = new Label("", skin);
        lblAviso.setColor(Color.RED);

        btnConvocar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int ativos = gameManager.getTripulacao().getAliadosAtivos().size();
                if (isAtivo) {
                    if (ativos > 1) {
                        gameManager.getTripulacao().removerAliadoAtivo(aliado);
                        atualizarPainelEsquerdo();
                        mostrarDetalhes(aliado);
                    }
                } else {
                    if (ativos < 3) {
                        gameManager.getTripulacao().adicionarAliadoAtivo(aliado);
                        atualizarPainelEsquerdo();
                        mostrarDetalhes(aliado);
                    } else {
                        lblAviso.setText("A equipe de ataque esta cheia (Max: 3)!");
                    }
                }
            }
        });
        painelDetalhes.add(btnConvocar).colspan(2).padTop(20).width(200).height(45).row();
        painelDetalhes.add(lblAviso).colspan(2).padTop(10).row();
    }

    private String removerAcentos(String str) {
        if (str == null) return null;
        return java.text.Normalizer.normalize(str, java.text.Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    @Override
    public void render(float delta) {
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
    @Override public void dispose() { stage.dispose(); }
}
