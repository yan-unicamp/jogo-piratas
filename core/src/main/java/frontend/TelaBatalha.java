package frontend;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;
import entidades.TipoHabilidade;
import progressao.Ilha;
import sistema.Assets;
import sistema.EstadoJogo;
import sistema.GameManager;
import sistema.GerenciadorDeBatalha;
import sistema.JogoPiratas;
import sistema.Tripulacao;

/**
 * Tela de Batalha — exibe fundo da ilha, sprites dos personagens e UI de
 * combate.
 *
 * Layout em camadas (Stack):
 * [0] Background da ilha (fill screen)
 * [1] UI Table:
 * Topo → HUD (island label, rodada, HP inimigos)
 * Centro → Sprite Luffy (esq.) | Sprite Inimigo (dir.)
 * Baixo → HP aliados | Botões de habilidade | Log
 */
public class TelaBatalha implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    private Stage stage;
    private Skin skin;

    // Referências atualizáveis
    private Table painelAliadosHP;
    private Table painelInimigosHP;
    private Table painelHabilidades;
    private Table logTable;
    private ScrollPane logScroll;
    private Label labelTurno;
    private Image spriteInimigo;

    public TelaBatalha(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Ciclo de vida
    // ──────────────────────────────────────────────────────────────────────────

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();
        adicionarEstilosExtra(skin);

        Assets assets = gameManager.getAssets();
        GerenciadorDeBatalha gb = gameManager.getGerenciadorDeBatalha();
        Ilha ilhaAtual = gameManager.getMapa().getIlhaAtual();

        // ── Stack: background + UI sobrepostos ──────────────────────────────
        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        // Camada 0: background da ilha
        Texture bgTex = (ilhaAtual != null)
                ? assets.getTextura(ilhaAtual.getBgKey())
                : assets.getPlaceholder();
        Image bgImage = new Image(new TextureRegionDrawable(new TextureRegion(bgTex)));
        bgImage.setScaling(Scaling.fill);
        stack.add(bgImage);

        // Overlay escuro para legibilidade da UI
        Table overlay = new Table();
        overlay.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0f, 0f, 0f, 0.45f)));
        stack.add(overlay);

        // Camada 1: UI
        Table ui = montarUI(assets, gb, ilhaAtual);
        stack.add(ui);

        atualizarTudo(gb);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Montagem da UI
    // ──────────────────────────────────────────────────────────────────────────

    private Table montarUI(Assets assets, GerenciadorDeBatalha gb, Ilha ilhaAtual) {
        Table ui = new Table();
        ui.setFillParent(true);
        ui.pad(12);

        // ── Topo: nome da ilha + rodada + inimigos HP ─────────────────────
        Table topo = new Table();

        String nomeIlha = (ilhaAtual != null) ? ilhaAtual.getNome() : "???";
        String infoRodada = "";
        boolean isBossRodada = false;
        if (ilhaAtual != null && ilhaAtual.getRodadaAtual() != null) {
            infoRodada = "  —  " + ilhaAtual.getRodadaAtual().getDescricao();
            isBossRodada = ilhaAtual.getRodadaAtual().isBoss();
        }

        Label labelIlha = new Label(nomeIlha + infoRodada, skin);
        labelIlha.setFontScale(1.3f);
        labelIlha.setColor(isBossRodada ? new Color(1f, 0.3f, 0.2f, 1f) : Color.GOLD);

        painelInimigosHP = new Table();

        topo.add(labelIlha).left().expandX();
        topo.add(painelInimigosHP).right();
        ui.add(topo).fillX().expandX().padBottom(6).row();

        // ── Indicador de turno ─────────────────────────────────────────────
        labelTurno = new Label("", skin);
        labelTurno.setColor(new Color(1f, 0.9f, 0.3f, 1f));
        labelTurno.setFontScale(1.1f);
        ui.add(labelTurno).padBottom(4).row();

        // ── Centro: sprites dos personagens ─────────────────────────────────
        Table arenaSprites = new Table();

        // Sprite de Luffy (esquerda)
        Texture luffyTex = assets.getLuffy();
        Image spriteLuffy = new Image(new TextureRegionDrawable(new TextureRegion(luffyTex)));
        spriteLuffy.setScaling(Scaling.fit);
        arenaSprites.add(spriteLuffy).width(200).height(200).expandX().left().padRight(20);

        // Sprite do inimigo principal (direita) — atualizado conforme a batalha
        Inimigo primoInimigo = (gb.getInimigos() != null && !gb.getInimigos().isEmpty())
                ? gb.getInimigos().get(0)
                : null;
        Texture iniTex = (primoInimigo != null)
                ? assets.getTextura(primoInimigo.getSpriteKey())
                : assets.getPlaceholder();
        spriteInimigo = new Image(new TextureRegionDrawable(new TextureRegion(iniTex)));
        spriteInimigo.setScaling(Scaling.fit);
        // Boss fica maior
        float spriteW = (primoInimigo != null && primoInimigo.isBoss()) ? 240 : 200;
        arenaSprites.add(spriteInimigo).width(spriteW).height(spriteW).expandX().right();

        ui.add(arenaSprites).expandX().fillX().expandY().padBottom(8).row();

        // ── Baixo: aliados HP | habilidades | log ────────────────────────
        Table rodape = new Table();

        // Coluna esquerda: HP dos aliados
        painelAliadosHP = new Table();
        rodape.add(painelAliadosHP).width(220).top().left().padRight(12);

        // Coluna centro: botões de habilidade
        painelHabilidades = new Table();
        painelHabilidades.top();
        ScrollPane scrollHab = new ScrollPane(painelHabilidades, skin);
        rodape.add(scrollHab).width(260).expandY().fillY().padRight(12);

        // Coluna direita: log de combate
        logTable = new Table();
        logTable.top().left();
        logScroll = new ScrollPane(logTable, skin);
        logScroll.setFadeScrollBars(false);
        rodape.add(logScroll).expand().fill();

        ui.add(rodape).fillX().height(220).row();

        return ui;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Atualização de estado
    // ──────────────────────────────────────────────────────────────────────────

    private void atualizarTudo(GerenciadorDeBatalha gb) {
        atualizarPainelInimigos(gb);
        atualizarPainelAliados(gb);
        atualizarLog(gb);
        atualizarSpritePrincipalInimigo(gb);

        if (gb.isBatalhaEncerrada()) {
            mostrarBotaoFinal(gb.isVitoria());
            return;
        }

        Personagem ativo = gb.getPersonagemAtivo();
        if (ativo instanceof Aliado) {
            labelTurno.setText("⚔ Seu turno: " + ativo.getNome());
            montarBotoesHabilidade((Aliado) ativo, gb);
        } else if (ativo instanceof Inimigo) {
            labelTurno.setText("💀 Turno do inimigo...");
            painelHabilidades.clear();
        }
    }

    private void atualizarSpritePrincipalInimigo(GerenciadorDeBatalha gb) {
        if (spriteInimigo == null)
            return;
        // Mostra sprite do primeiro inimigo vivo
        Inimigo vivo = (gb.getInimigosVivos() != null && !gb.getInimigosVivos().isEmpty())
                ? gb.getInimigosVivos().get(0)
                : null;
        if (vivo != null) {
            Texture t = gameManager.getAssets().getTextura(vivo.getSpriteKey());
            spriteInimigo.setDrawable(new TextureRegionDrawable(new TextureRegion(t)));
        }
    }

    private void atualizarPainelInimigos(GerenciadorDeBatalha gb) {
        painelInimigosHP.clear();
        if (gb.getInimigos() == null)
            return;
        for (Inimigo ini : gb.getInimigos()) {
            Label lbl = new Label(
                    (ini.isBoss() ? "👑 " : "") + ini.getNome()
                            + "  " + ini.getVidaAtual() + "/" + ini.getVidaMaxima() + " HP",
                    skin);
            lbl.setColor(ini.estaVivo() ? new Color(1f, 0.5f, 0.5f, 1f) : Color.DARK_GRAY);
            painelInimigosHP.add(lbl).padLeft(10);
        }
    }

    private void atualizarPainelAliados(GerenciadorDeBatalha gb) {
        painelAliadosHP.clear();
        if (gb.getAliados() == null)
            return;
        for (Aliado ali : gb.getAliados()) {
            float ratio = ali.getVidaMaxima() > 0
                    ? (float) ali.getVidaAtual() / ali.getVidaMaxima()
                    : 0f;
            Color corHP = ratio > 0.5f ? new Color(0.3f, 1f, 0.3f, 1f)
                    : ratio > 0.25f ? Color.ORANGE : Color.RED;

            Label lbl = new Label(
                    ali.getNome() + "  " + ali.getVidaAtual() + "/" + ali.getVidaMaxima(),
                    skin);
            lbl.setColor(ali.estaVivo() ? corHP : Color.DARK_GRAY);
            painelAliadosHP.add(lbl).left().row();
        }
    }

    private void atualizarLog(GerenciadorDeBatalha gb) {
        logTable.clear();
        List<String> linhas = gb.getLogCombate();
        int inicio = Math.max(0, linhas.size() - 18);
        for (int i = inicio; i < linhas.size(); i++) {
            Label l = new Label(linhas.get(i), skin);
            l.setColor(new Color(0.85f, 0.85f, 0.85f, 1f));
            l.setWrap(true);
            logTable.add(l).left().width(240).padBottom(2).row();
        }
    }

    private void montarBotoesHabilidade(Aliado aliado, GerenciadorDeBatalha gb) {
        painelHabilidades.clear();
        Label titulo = new Label(aliado.getNome() + ":", skin);
        titulo.setColor(Color.WHITE);
        painelHabilidades.add(titulo).left().padBottom(6).row();

        for (Habilidade hab : aliado.getHabilidades()) {
            TextButton btn = new TextButton(hab.getNome(), skin);
            btn.getLabel().setColor(corPorTipo(hab.getTipo()));

            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Personagem alvo = escolherAlvo(hab, gb);
                    gb.executarTurnoAliado(hab, alvo);
                    atualizarTudo(gb);

                    if (gb.isBatalhaEncerrada()) {
                        processarFimBatalha(gb.isVitoria());
                    }
                }
            });

            painelHabilidades.add(btn).width(250).height(44).padBottom(6).row();
        }
    }

    private void mostrarBotaoFinal(boolean venceu) {
        painelHabilidades.clear();
        String txt = venceu ? "Próximo ▶" : "Fim de jogo...";
        TextButton btn = new TextButton(txt, skin);
        btn.getLabel().setColor(venceu ? new Color(0.3f, 1f, 0.3f, 1f) : Color.RED);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                processarFimBatalha(venceu);
            }
        });
        painelHabilidades.add(btn).width(250).height(55);
    }

    /** Distribui recompensas e decide próxima tela. */
    private void processarFimBatalha(boolean venceu) {
        if (!venceu) {
            gameManager.mudarEstado(EstadoJogo.GAME_OVER);
            return;
        }
        // Distribui recompensas
        GerenciadorDeBatalha gb = gameManager.getGerenciadorDeBatalha();
        for (Inimigo ini : gb.getInimigos()) {
            gameManager.getTripulacao().receberRecompensa(ini.getRecompensa());
        }
        // Verifica se há mais rodadas na ilha
        boolean ilhaCompleta = gameManager.avancarRodada();
        if (ilhaCompleta) {
            // Verifica se é a última ilha (vitória total)
            if (gameManager.verificarVitoria()) {
                gameManager.mudarEstado(EstadoJogo.VITORIA);
            } else {
                gameManager.mudarEstado(EstadoJogo.MAPA);
            }
        } else {
            gameManager.mudarEstado(EstadoJogo.BATALHA); // nova TelaBatalha com próxima rodada
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Helpers
    // ──────────────────────────────────────────────────────────────────────────

    private Personagem escolherAlvo(Habilidade hab, GerenciadorDeBatalha gb) {
        if (hab.getTipo() == TipoHabilidade.DANO) {
            List<Inimigo> vivos = gb.getInimigosVivos();
            return vivos.isEmpty() ? null : vivos.get(0);
        }
        // CURA ou DEFESA — alvo com menor HP
        List<Aliado> vivos = gb.getAliadosVivos();
        return vivos.stream()
                .min((a, b) -> a.getVidaAtual() - b.getVidaAtual())
                .orElse(null);
    }

    private Color corPorTipo(TipoHabilidade tipo) {
        switch (tipo) {
            case DANO:
                return new Color(1f, 0.45f, 0.45f, 1f);
            case CURA:
                return new Color(0.4f, 1f, 0.45f, 1f);
            case DEFESA:
                return new Color(0.4f, 0.75f, 1f, 1f);
            default:
                return Color.WHITE;
        }
    }

    private void adicionarEstilosExtra(Skin s) {
        com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle hpStyle = new com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle();
        hpStyle.background = new TextureRegionDrawable(SkinPadrao.textura1x1(0.15f, 0.15f, 0.15f, 1f));
        hpStyle.knob = new TextureRegionDrawable(SkinPadrao.textura1x1(0f, 0f, 0f, 0f));
        hpStyle.knobBefore = new TextureRegionDrawable(SkinPadrao.textura1x1(0.2f, 0.8f, 0.2f, 1f));
        s.add("hp", hpStyle);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Lifecycle
    // ──────────────────────────────────────────────────────────────────────────

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.05f, 0.12f, 1f);
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
