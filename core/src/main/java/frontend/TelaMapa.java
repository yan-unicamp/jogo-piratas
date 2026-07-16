package frontend;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

import progressao.Ilha;
import sistema.Assets;
import sistema.GameManager;
import sistema.JogoPiratas;

/**
 * Tela do Mapa do Grand Line — exibe todas as ilhas em ordem.
 *
 * Status por ilha:
 *   ✅ Concluída — card esmaecido, não clicável
 *   ⚔  Disponível — próxima ilha não concluída, clicável (destaque dourado)
 *   🔒 Bloqueada — ilhas além da atual, não clicáveis
 *
 * HUD superior: ouro, HP total da tripulação, nº de ilhas concluídas.
 */
public class TelaMapa implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    private Stage stage;
    private Skin  skin;
    private Table overlayConfirmacao;
    private Ilha  ilhaSelecionada;

    public TelaMapa(JogoPiratas jogo, GameManager gameManager) {
        this.jogo        = jogo;
        this.gameManager = gameManager;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Lifecycle
    // ──────────────────────────────────────────────────────────────────────────

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin  = SkinPadrao.criar();

        Assets       assets = jogo.assets;
        List<Ilha>   concluidas = gameManager.getMapa().getIlhasConcluidas();
        List<Ilha>   opcoes     = gameManager.getMapa().getOpcoesAtuais();
        int          etapa  = gameManager.getMapa().getEtapaAtual(); 

        // Stack: background + UI
        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        // Fundo: thumbnail da primeira opção disponível
        if (!opcoes.isEmpty()) {
            Texture bgTex = assets.getTextura(opcoes.get(0).getBgKey());
            Image bgImg = new Image(new TextureRegionDrawable(new TextureRegion(bgTex)));
            bgImg.setScaling(Scaling.fill);
            stack.add(bgImg);
        }
        // Overlay escuro
        Table overlay = new Table();
        overlay.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0f, 0f, 0f, 0.65f)));
        stack.add(overlay);

        // UI principal
        Table ui = montarUI(assets, concluidas, opcoes, etapa);
        stack.add(ui);

        // Overlay de confirmação
        overlayConfirmacao = new Table();
        overlayConfirmacao.setFillParent(true);
        overlayConfirmacao.setVisible(false);
        stack.add(overlayConfirmacao);

        // Se houver apenas 1 opção, pular para a confirmação
        if (opcoes.size() == 1) {
            mostrarConfirmacao(opcoes.get(0), assets);
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Montagem da UI
    // ──────────────────────────────────────────────────────────────────────────

    private Table montarUI(Assets assets, List<Ilha> concluidas, List<Ilha> opcoes, int etapa) {
        Table ui = new Table();
        ui.setFillParent(true);
        ui.top().pad(16);

        // ── HUD ───────────────────────────────────────────────────────────
        ui.add(montarHud(etapa, 6)).fillX().expandX().padBottom(16).row(); // 5 genéricas + boss

        // ── Título ─────────────────────────────────────────────────────────
        Label titulo = new Label("Grand Line — Escolha sua próxima ilha", skin);
        titulo.setFontScale(1.4f);
        titulo.setColor(Color.GOLD);
        ui.add(titulo).padBottom(12).row();

        // ── Lista de ilhas em ScrollPane ───────────────────────────────────
        Table listaIlhas = montarListaIlhas(assets, concluidas, opcoes);
        ScrollPane scroll = new ScrollPane(listaIlhas, skin);
        scroll.setFadeScrollBars(false);
        scroll.setScrollingDisabled(true, false);
        ui.add(scroll).expand().fill().row();

        return ui;
    }

    private Table montarHud(int etapa, int total) {
        Table hud = new Table();
        hud.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0f, 0f, 0f, 0.5f)));
        hud.pad(8, 16, 8, 16);

        int ouro   = gameManager.getTripulacao().getDinheiro();
        int hpTotal = gameManager.getTripulacao().getAliadosVivos()
                .stream().mapToInt(a -> a.getVidaAtual()).sum();

        Label lblIlhas = new Label("Ilhas: " + etapa + "/" + total, skin);
        lblIlhas.setColor(Color.WHITE);
        lblIlhas.setFontScale(1.2f);

        Label lblHP = new Label("HP Total: " + hpTotal, skin);
        lblHP.setColor(new Color(0.4f, 1f, 0.4f, 1f));

        Label lblOuro = new Label("Ouro: " + ouro, skin);
        lblOuro.setColor(new Color(1f, 0.85f, 0.2f, 1f));

        hud.add(lblIlhas).left().expandX();
        hud.add(lblHP).center().expandX();
        hud.add(lblOuro).right().expandX();
        return hud;
    }

    private Table montarListaIlhas(Assets assets, List<Ilha> concluidas, List<Ilha> opcoes) {
        Table lista = new Table();
        lista.top();
        lista.pad(0, 20, 20, 20);

        // Histórico de ilhas concluídas
        for (int i = 0; i < concluidas.size(); i++) {
            Ilha ilha = concluidas.get(i);
            Table card = criarCardIlha(assets, ilha, i, true, false, false);
            lista.add(card).fillX().expandX().padBottom(10).row();
        }

        // Título para as opções atuais (se houver mais de uma)
        if (opcoes.size() > 1) {
            Label opcoesTitulo = new Label("--- Caminhos Disponíveis ---", skin);
            opcoesTitulo.setColor(Color.LIGHT_GRAY);
            lista.add(opcoesTitulo).padTop(10).padBottom(10).row();
        }

        // Opções disponíveis
        for (int j = 0; j < opcoes.size(); j++) {
            Ilha ilha = opcoes.get(j);
            Table card = criarCardIlha(assets, ilha, concluidas.size() + j, false, true, false);
            lista.add(card).fillX().expandX().padBottom(10).row();
        }

        return lista;
    }

    private Table criarCardIlha(Assets assets, Ilha ilha, int indice,
            boolean concluida, boolean atual, boolean bloqueada) {

        // Cor de fundo do card conforme status
        float[] bg = concluida ? new float[]{0.05f,0.18f,0.05f,0.8f}
                   : atual     ? new float[]{0.18f,0.12f,0.02f,0.9f}
                   :             new float[]{0.08f,0.08f,0.12f,0.75f};

        Table card = new Table();
        card.setBackground(new TextureRegionDrawable(
                SkinPadrao.textura1x1(bg[0], bg[1], bg[2], bg[3])));
        card.pad(10, 14, 10, 14);

        // Thumbnail do background da ilha (pequeno, à esquerda)
        Texture thumb = assets.getTextura(ilha.getBgKey());
        Image thumbImg = new Image(new TextureRegionDrawable(new TextureRegion(thumb)));
        thumbImg.setScaling(Scaling.fit);
        card.add(thumbImg).width(80).height(50).padRight(14);

        // Informações
        Table info = new Table();
        info.left();

        // Status icon + nome
        String icone = concluida ? "✅ " : atual ? "⚔  " : "🔒 ";
        Label nomeLbl = new Label(icone + (indice + 1) + ". " + ilha.getNome(), skin);
        nomeLbl.setFontScale(1.15f);
        nomeLbl.setColor(concluida ? Color.GRAY
                : atual ? Color.GOLD
                : new Color(0.5f, 0.5f, 0.55f, 1f));
        info.add(nomeLbl).left().row();

        // Rodadas / status
        String statusTxt = concluida ? "Conquistada!"
                : atual ? "Rodada " + (ilha.getRodadaAtualIdx() + 1) + " / " + ilha.getTotalRodadas()
                : "Bloqueada";
        Label statusLbl = new Label(statusTxt, skin);
        statusLbl.setColor(new Color(0.75f, 0.75f, 0.75f, 1f));
        info.add(statusLbl).left().row();

        card.add(info).expandX().fillX();

        // Botão (só para ilha disponível)
        if (atual) {
            TextButton btn = new TextButton("Entrar ▶", skin);
            btn.getLabel().setColor(Color.GOLD);
            final Ilha ilhaFinal = ilha;
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mostrarConfirmacao(ilhaFinal, assets);
                }
            });
            card.add(btn).width(140).height(46).padLeft(10);
        } else {
            card.add().width(140); // espaço vazio para alinhar
        }

        return card;
    }

    private void mostrarConfirmacao(final Ilha ilha, Assets assets) {
        this.ilhaSelecionada = ilha;
        
        overlayConfirmacao.clearChildren();
        overlayConfirmacao.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0, 0, 0, 0.85f)));
        
        Table janela = new Table();
        janela.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.15f, 0.1f, 0.1f, 1f)));
        janela.pad(40);
        
        Label titulo = new Label("Desbravar: " + ilha.getNome() + "?", skin);
        titulo.setFontScale(1.6f);
        titulo.setColor(Color.GOLD);
        
        Label desc = new Label("Você enfrentará " + ilha.getTotalRodadas() + " rodadas de batalha.", skin);
        
        TextButton btnConfirmar = new TextButton("Confirmar e Entrar", skin);
        btnConfirmar.getLabel().setColor(Color.GREEN);
        btnConfirmar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.entrarIlha(ilha);
            }
        });
        
        TextButton btnVoltar = new TextButton("Voltar para o Mapa", skin);
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
        
        overlayConfirmacao.add(janela);
        overlayConfirmacao.setVisible(true);
    }

    // ──────────────────────────────────────────────────────────────────────────

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.07f, 0.05f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
