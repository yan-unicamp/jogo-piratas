package frontend;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

import progressao.Ilha;
import progressao.IlhaDescanso;
import progressao.IlhaLoja;
import sistema.Assets;
import sistema.GameManager;
import sistema.JogoPiratas;

public class TelaMapa implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    private Stage stage;
    private Skin skin;
    private Table overlayConfirmacao;
    private Ilha ilhaSelecionada;
    private Runnable acaoConfirmarIlha = null;

    private final Color COR_PERGAMINHO = new Color(0.85f, 0.76f, 0.60f, 1f);

    public TelaMapa(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920, 1080));
        Gdx.input.setInputProcessor(stage);
        
        stage.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.ENTER) {
                    if (overlayConfirmacao != null && overlayConfirmacao.isVisible() && acaoConfirmarIlha != null) {
                        acaoConfirmarIlha.run();
                        return true;
                    }
                } else if (overlayConfirmacao != null && !overlayConfirmacao.isVisible()) {
                    List<Ilha> opcoes = gameManager.getMapa().getOpcoesAtuais();
                    if (keycode == com.badlogic.gdx.Input.Keys.LEFT && opcoes.size() > 0) {
                        mostrarConfirmacao(opcoes.get(0), jogo.assets);
                        return true;
                    } else if (keycode == com.badlogic.gdx.Input.Keys.UP && opcoes.size() == 3) {
                        mostrarConfirmacao(opcoes.get(1), jogo.assets);
                        return true;
                    } else if (keycode == com.badlogic.gdx.Input.Keys.RIGHT && opcoes.size() >= 2) {
                        if (opcoes.size() == 3) mostrarConfirmacao(opcoes.get(2), jogo.assets);
                        else mostrarConfirmacao(opcoes.get(1), jogo.assets);
                        return true;
                    }
                }
                return super.keyDown(event, keycode);
            }
        });
        
        skin = SkinPadrao.criar();
        
        sistema.SaveManager.salvar(gameManager);

        Assets assets = jogo.assets;
        List<Ilha> concluidas = gameManager.getMapa().getIlhasConcluidas();
        List<Ilha> opcoes = gameManager.getMapa().getOpcoesAtuais();
        int etapa = gameManager.getMapa().getEtapaAtual(); 

        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        Texture texPerg = assets.getTextura("backgrounds/pergaminho.png");
        Image bgImg = new Image(new TextureRegionDrawable(new TextureRegion(texPerg)));
        bgImg.setScaling(Scaling.fill);
        bgImg.setColor(0.5f, 0.5f, 0.5f, 1f); // Escurece o pergaminho pela metade para dar contraste
        stack.add(bgImg);

        Group mapaGroup = montarMapaGroup(assets, concluidas, opcoes);
        ScrollPane scroll = new ScrollPane(mapaGroup, skin);
        scroll.setFadeScrollBars(false);
        scroll.setScrollingDisabled(true, false);
        scroll.layout();
        scroll.setScrollPercentY(100f);
        
        Table ui = new Table();
        ui.setFillParent(true);
        ui.top();

        ui.add(montarHud(etapa, 6)).fillX().expandX().row();
        ui.add(scroll).expand().fill().row();

        stack.add(ui);



        overlayConfirmacao = new Table();
        overlayConfirmacao.setFillParent(true);
        overlayConfirmacao.setVisible(false);
        stack.add(overlayConfirmacao);

        if (opcoes.size() == 1) {
            mostrarConfirmacao(opcoes.get(0), assets);
        }
        
        jogo.audio.tocar(sistema.AudioManager.MUSICA_MAPA, true);
        
        // Assegura que o scrollpane pule para o topo do conteudo apois o primeiro frame
        Gdx.app.postRunnable(() -> {
            scroll.setScrollPercentY(1.0f);
        });
    }

    private Group montarMapaGroup(Assets assets, List<Ilha> concluidas, List<Ilha> opcoes) {
        Group map = new Group();
        
        float NODE_W = 120;
        float Y_STEP = 300;
        float X_CENTER = 1920 / 2f;
        float X_OFFSET = 350;
        float currentY = 150;
        
        Label lblStart = new Label("[ INICIO ]", skin);
        lblStart.setColor(new Color(0.1f, 0.1f, 0.1f, 1f));
        lblStart.setPosition(X_CENTER - lblStart.getPrefWidth()/2, currentY - 50);
        map.addActor(lblStart);

        float prevX = X_CENTER;
        float prevY = currentY - 30; // base of the first line

        for(int i = 0; i < concluidas.size(); i++) {
            Ilha ilha = concluidas.get(i);
            int realDepth = (gameManager.getMapa().getCapitulo() == 1) ? i : i + 1;
            boolean isCenter = (realDepth == 0 || realDepth == 3 || realDepth == 6 || realDepth == 9 || realDepth == 10);
            int col = isCenter ? 1 : Math.abs(ilha.getNome().hashCode()) % 3;
            if (!isCenter && col == 1) col = 0; // Evita que ilhas genericas fiquem no centro exato se isCenter for falso

            float x = X_CENTER + (col - 1) * X_OFFSET;
            float y = currentY + (i * Y_STEP);
            
            if (!isCenter) {
                for(int c = 0; c < 3; c++) {
                    float fakeX = X_CENTER + (c - 1) * X_OFFSET;
                    if (c != col) {
                        Image linhaFake = criarLinha(prevX, prevY, fakeX, y, new Color(0, 0, 0, 0.2f));
                        map.addActor(linhaFake);
                        
                        Table fakeNode = criarNoFake();
                        fakeNode.setPosition(fakeX - NODE_W/2, y - NODE_W/2);
                        map.addActor(fakeNode);
                    }
                }
            }
            
            Image linha = criarLinha(prevX, prevY, x, y, new Color(0.1f, 0.1f, 0.1f, 0.85f));
            map.addActor(linha);
            
            Table no = criarNoIlha(ilha, realDepth, true, false, assets);
            no.setPosition(x - NODE_W/2, y - NODE_W/2);
            map.addActor(no);
            
            prevX = x;
            prevY = y;
        }

        int etapaAtual = concluidas.size();
        int realDepthAt = (gameManager.getMapa().getCapitulo() == 1) ? etapaAtual : etapaAtual + 1;
        boolean isCenterAt = (realDepthAt == 0 || realDepthAt == 3 || realDepthAt == 6 || realDepthAt == 9 || realDepthAt == 10);
        float y = currentY + (etapaAtual * Y_STEP);
        
        List<Ilha> opcoesAtuais = gameManager.getMapa().getOpcoesAtuais();
        
        for(int i = 0; i < opcoesAtuais.size(); i++) {
            Ilha ilha = opcoesAtuais.get(i);
            int col = (opcoesAtuais.size() == 1) ? (isCenterAt ? 1 : Math.abs(ilha.getNome().hashCode()) % 3) : i;
            if (opcoesAtuais.size() == 1 && !isCenterAt && col == 1) col = 0;
            
            float x = X_CENTER + (col - 1) * X_OFFSET;
            
            Image linha = criarLinha(prevX, prevY, x, y, new Color(0.1f, 0.1f, 0.1f, 0.85f));
            map.addActor(linha);
            
            Table no = criarNoIlha(ilha, realDepthAt, false, true, assets);
            no.setPosition(x - NODE_W/2, y - NODE_W/2);
            map.addActor(no);
        }
        
        map.setSize(1920, y + 400); // Altura total do scroll
        return map;
    }

    private Image criarLinha(float x1, float y1, float x2, float y2, Color color) {
        Image linha = new Image(new TextureRegionDrawable(SkinPadrao.textura1x1(color.r, color.g, color.b, color.a)));
        float length = (float) Math.hypot(x2 - x1, y2 - y1);
        float angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
        linha.setSize(length, 8f);
        linha.setOrigin(0, 4f);
        linha.setPosition(x1, y1 - 4f);
        linha.setRotation(angle);
        return linha;
    }

    private Table criarNoFake() {
        Table card = new Table();
        card.setSize(120, 120);
        card.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.35f, 0.2f, 0.1f, 0.7f)));
        
        Label xLbl = new Label("X", skin);
        xLbl.setFontScale(3f);
        xLbl.setColor(new Color(0.6f, 0.1f, 0.1f, 0.8f));
        card.add(xLbl).center();
        
        return card;
    }

    private Table criarNoIlha(Ilha ilha, int etapa, boolean concluida, boolean atual, Assets assets) {
        Table card = new Table();
        card.setSize(120, 120);
        card.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.35f, 0.2f, 0.1f, 1f)));
        
        Table inner = new Table();
        if (concluida) {
            inner.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.4f, 0.4f, 0.4f, 1f)));
        } else if (atual) {
            inner.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.9f, 0.8f, 0.2f, 1f)));
        } else {
            inner.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.85f, 0.76f, 0.60f, 1f)));
        }
        
        Texture thumb = assets.getTextura(ilha.getBgKey());
        Image thumbImg = new Image(new TextureRegionDrawable(new TextureRegion(thumb)));
        thumbImg.setScaling(Scaling.fit);
        
        inner.add(thumbImg).size(100, 100);
        card.add(inner).size(110, 110);
        
        Table labelContainer = new Table();
        labelContainer.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0f, 0f, 0f, 0.7f)));
        labelContainer.pad(4, 8, 4, 8);

        Label lbl = new Label(removerAcentos(ilha.getNome()), skin);
        if (atual) {
            lbl.setColor(Color.GOLD); // Dourado brilhante para destacar escolhas
            lbl.setFontScale(1.1f);
            card.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.enabled);
            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    mostrarConfirmacao(ilha, assets);
                }
            });
        } else if (concluida) {
            lbl.setColor(Color.WHITE); // Branco para contraste
        }
        
        labelContainer.add(lbl);
        labelContainer.pack();
        labelContainer.setPosition(60 - labelContainer.getWidth()/2, -35);
        card.addActor(labelContainer);
        
        return card;
    }

    private Table montarHud(int etapa, int total) {
        Table hud = new Table();
        hud.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.15f, 0.1f, 0.05f, 0.9f)));
        hud.pad(15, 30, 15, 30);

        int ouro = gameManager.getTripulacao().getDinheiro();
        int hpTotal = gameManager.getTripulacao().getAliadosVivos()
                .stream().mapToInt(a -> a.getVidaAtual()).sum();

        Label lblIlhas = new Label("Ilha: " + etapa, skin);
        lblIlhas.setColor(COR_PERGAMINHO);
        lblIlhas.setFontScale(1.2f);

        Label lblHP = new Label("HP Total: " + hpTotal, skin);
        lblHP.setColor(new Color(0.4f, 1f, 0.4f, 1f));

        Label lblOuro = new Label("Ouro: " + ouro, skin);
        lblOuro.setColor(Color.GOLD);

        TextButton btnTripulacao = new TextButton("Ver Tripulacao", skin);
        btnTripulacao.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(new TelaTripulacao(jogo, gameManager, TelaMapa.this));
            }
        });

        TextButton btnSair = new TextButton(" Sair do Jogo ", skin);
        btnSair.getLabel().setColor(new Color(1f, 0.4f, 0.4f, 1f));
        btnSair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sistema.SaveManager.salvar(gameManager);
                Gdx.app.exit();
            }
        });

        hud.add(lblIlhas).left().expandX();
        hud.add(lblHP).center().expandX();
        hud.add(lblOuro).center().expandX();
        hud.add(btnTripulacao).right().padRight(10);
        hud.add(btnSair).right();
        return hud;
    }

    private void mostrarConfirmacao(final Ilha ilha, Assets assets) {
        this.ilhaSelecionada = ilha;
        
        overlayConfirmacao.clearChildren();
        
        Stack stack = new Stack();
        stack.setFillParent(true);
        
        Texture bgTex = assets.getTextura(ilha.getBgKey());
        Image bgImg = new Image(new TextureRegionDrawable(new TextureRegion(bgTex)));
        bgImg.setScaling(Scaling.fill);
        stack.add(bgImg);
        
        Table tint = new Table();
        tint.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0, 0, 0, 0.85f)));
        stack.add(tint);
        
        Table janela = new Table();
        janela.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.15f, 0.1f, 0.05f, 1f)));
        janela.pad(40);
        
        Label titulo = new Label("Desbravar: " + removerAcentos(ilha.getNome()) + "?", skin);
        titulo.setFontScale(1.6f);
        titulo.setColor(COR_PERGAMINHO);
        
        Label desc = new Label("Voce enfrentara " + ilha.getTotalRodadas() + " rodadas de batalha.", skin);
        
        acaoConfirmarIlha = () -> {
            Screen proximaTela = null;
            if (ilha instanceof IlhaDescanso) {
                proximaTela = new TelaDescanso(jogo, gameManager, ilha);
            } else if (ilha instanceof IlhaLoja) {
                proximaTela = new TelaLoja(jogo, gameManager, ilha);
            } else {
                progressao.Rodada rodada = ilha.getRodadaAtual();
                if (rodada != null) {
                    proximaTela = new TelaBatalha(jogo, gameManager, ilha, rodada);
                }
            }

            if (proximaTela != null) {
                if (ilha.getDescricao() != null && !ilha.getDescricao().isEmpty()) {
                    jogo.setScreen(new TelaTransicaoIlha(jogo, ilha, proximaTela));
                } else {
                    jogo.setScreen(proximaTela);
                }
            }
        };

        TextButton btnConfirmar = new TextButton("Confirmar e Entrar", skin);
        btnConfirmar.getLabel().setColor(Color.GREEN);
        btnConfirmar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (acaoConfirmarIlha != null) acaoConfirmarIlha.run();
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
        
        tint.add(janela);
        overlayConfirmacao.add(stack).expand().fill();
        overlayConfirmacao.setVisible(true);
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

    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
    
    private String removerAcentos(String str) {
        if (str == null) return null;
        return java.text.Normalizer.normalize(str, java.text.Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }
}
