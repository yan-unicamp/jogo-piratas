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
        
        // --- CABECALHO (Imagem e Info) ---
        Table headerTable = new Table();
        
        Texture tex = aliado.getTextura();
        Image img;
        if (tex != null) {
            img = new Image(tex);
        } else {
            img = new Image(new TextureRegionDrawable(SkinPadrao.textura1x1(0.3f, 0.3f, 0.3f, 1f)));
        }
        img.setScaling(com.badlogic.gdx.utils.Scaling.fit);
        // Imagem bem maior na esquerda do header
        headerTable.add(img).size(250, 250).padRight(30).align(Align.center);
        
        Table infoTable = new Table();
        Label lblNome = new Label(removerAcentos(aliado.getNome()), skin);
        lblNome.setFontScale(1.8f);
        lblNome.setColor(Color.CYAN);
        infoTable.add(lblNome).padBottom(20).left().row();
        
        int nextXp = 100 * aliado.getNivel();
        Label lblStatus = new Label(
            "Nivel: " + aliado.getNivel() + "\n" +
            "XP: " + aliado.getExperiencia() + " / " + nextXp + "\n" +
            "HP: " + aliado.getVidaAtual() + " / " + aliado.getVidaMaxima() + "\n" +
            "DEF: " + String.format("%.2f", (1.0f - aliado.getDefesa())), 
            skin
        );
        lblStatus.setFontScale(1.2f);
        lblStatus.setAlignment(Align.left);
        infoTable.add(lblStatus).left().row();
        
        headerTable.add(infoTable).expandX().left();
        painelDetalhes.add(headerTable).expandX().fillX().padBottom(30).row();
        
        // --- HABILIDADES ---
        Table skillsTable = new Table();
        
        Table currentSkillsTable = new Table();
        Label lblCurrent = new Label("Habilidades Adquiridas", skin);
        lblCurrent.setColor(Color.GOLD);
        lblCurrent.setFontScale(1.4f);
        currentSkillsTable.add(lblCurrent).padBottom(15).left().row();
        
        for (Habilidade hab : aliado.getHabilidades()) {
            String desc = "- " + hab.getNome() + " (" + hab.getTipo().toString() + ": " + String.format("%.0f", hab.getValorPoder()) + ")";
            Label lblHab = new Label(removerAcentos(desc), skin);
            lblHab.setFontScale(1.2f);
            currentSkillsTable.add(lblHab).left().padBottom(8).row();
        }
        
        Table lockedSkillsTable = new Table();
        Label lblLocked = new Label("Proximos Desbloqueios", skin);
        lblLocked.setColor(Color.LIGHT_GRAY);
        lblLocked.setFontScale(1.4f);
        lockedSkillsTable.add(lblLocked).padBottom(15).left().row();
        
        java.util.Map<Integer, java.util.function.Supplier<Habilidade>> habsDesbl = aliado.getHabilidadesDesbloqueaveis();
        java.util.List<Integer> niveis = new java.util.ArrayList<>(habsDesbl.keySet());
        java.util.Collections.sort(niveis);
        
        boolean hasLocked = false;
        for (Integer lvl : niveis) {
            if (lvl > aliado.getNivel()) {
                hasLocked = true;
                Habilidade dummy = habsDesbl.get(lvl).get();
                String desc = "- Nivel " + lvl + ": " + dummy.getNome() + " [LOCKED]";
                Label lblHabLocked = new Label(removerAcentos(desc), skin);
                lblHabLocked.setColor(Color.GRAY);
                lblHabLocked.setFontScale(1.2f);
                lockedSkillsTable.add(lblHabLocked).left().padBottom(8).row();
            }
        }
        if (!hasLocked) {
            Label lblHabLocked = new Label("Todas desbloqueadas!", skin);
            lblHabLocked.setColor(Color.DARK_GRAY);
            lblHabLocked.setFontScale(1.2f);
            lockedSkillsTable.add(lblHabLocked).left().row();
        }
        
        skillsTable.add(currentSkillsTable).expandX().top().left().padRight(40);
        skillsTable.add(lockedSkillsTable).expandX().top().left();
        painelDetalhes.add(skillsTable).expand().fill().row();

        // --- BOTAO CONVOCAR / DESCONVOCAR ---
        final boolean isAtivo = gameManager.getTripulacao().getAliadosAtivos().contains(aliado);
        String txtBotao = isAtivo ? "Desconvocar (Na equipe)" : "Convocar (Reserva)";
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
                    } else {
                        lblAviso.setText("A equipe de ataque nao pode ficar vazia!");
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
        painelDetalhes.add(btnConvocar).colspan(2).padTop(30).width(250).height(50).align(Align.center).row();
        painelDetalhes.add(lblAviso).colspan(2).padTop(10).align(Align.center).row();
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
