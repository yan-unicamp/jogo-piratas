package frontend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;
import entidades.TipoHabilidade;
import sistema.GameManager;
import sistema.GerenciadorDeBatalha;
import sistema.GerenciadorDeBatalha.AcaoPlanejada;
import sistema.JogoPiratas;
import sistema.Tripulacao;

import java.util.ArrayList;

public class TelaBatalha implements Screen {

    private final JogoPiratas jogo;
    private Stage stage;
    private Skin skin;
    private Table uiTable;
    private Label logLabel;
    
    private GerenciadorDeBatalha gerenciador;
    private float delayAcumulado = 0;
    
    private ArrayList<Personagem> aliados;
    private ArrayList<Personagem> inimigos;
    private Tripulacao tripulacao;

    private boolean aguardandoAcaoJogador = false;
    private Habilidade habilidadeSelecionada;
    private boolean escolhendoAlvo = false;
    private java.util.HashMap<String, Texture> textureCache = new java.util.HashMap<>();
    private java.util.HashMap<Personagem, Group> groupCache = new java.util.HashMap<>();

    private GameManager gameManager;
    private progressao.Ilha ilhaAtual;
    private progressao.Rodada rodadaAtual;

    public TelaBatalha(JogoPiratas jogo, GameManager gameManager, progressao.Ilha ilha, progressao.Rodada rodada) {
        this.jogo = jogo;
        this.gameManager = gameManager;
        this.ilhaAtual = ilha;
        this.rodadaAtual = rodada;
    }

    private Texture bgTexture;
    private Texture overlayTexture;

    @Override
    public void show() {
        stage = new Stage(new com.badlogic.gdx.utils.viewport.FitViewport(1920, 1080), jogo.batch);
        Gdx.input.setInputProcessor(stage);
        
        criarSkin();
        
        com.badlogic.gdx.scenes.scene2d.ui.Stack rootStack = new com.badlogic.gdx.scenes.scene2d.ui.Stack();
        rootStack.setFillParent(true);
        stage.addActor(rootStack);
        
        bgTexture = new Texture(Gdx.files.internal(ilhaAtual.getBgKey()));
        Image bgImage = new Image(bgTexture);
        bgImage.setScaling(com.badlogic.gdx.utils.Scaling.fill);
        rootStack.add(bgImage);
        
        Pixmap pix = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pix.setColor(0f, 0f, 0f, 0.8f);
        pix.fill();
        overlayTexture = new Texture(pix);
        pix.dispose();
        
        Image overlayImage = new Image(overlayTexture);
        overlayImage.setScaling(com.badlogic.gdx.utils.Scaling.stretch);
        rootStack.add(overlayImage);
        
        uiTable = new Table();
        rootStack.add(uiTable);
        
        logLabel = new Label("Batalha Iniciada!", skin);
        
        inicializarBatalha();
    }

    private void criarSkin() {
        skin = new Skin();
        
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        skin.add("dark", new Texture(pixmap));
        
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        skin.add("gray", new Texture(pixmap));

        skin.add("default", jogo.font);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.newDrawable("dark");
        buttonStyle.down = skin.newDrawable("gray");
        buttonStyle.font = skin.getFont("default");
        skin.add("default", buttonStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);
        
        Pixmap pixmapGreen = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmapGreen.setColor(Color.GREEN);
        pixmapGreen.fill();
        skin.add("green", new Texture(pixmapGreen));
        
        com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle barStyle = new com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle();
        barStyle.background = skin.newDrawable("dark");
        barStyle.knob = skin.newDrawable("green");
        barStyle.knobBefore = skin.newDrawable("green");
        skin.add("default-horizontal", barStyle);
    }

    private void inicializarBatalha() {
        this.tripulacao = gameManager.getTripulacao();
        this.aliados = new ArrayList<>(tripulacao.getAliadosAtivos());
        
        this.inimigos = new ArrayList<>();
        for(Personagem e : rodadaAtual.getInimigos()) {
            Inimigo clone = (Inimigo) e;
            this.inimigos.add(clone);
        }

        gerenciador = new GerenciadorDeBatalha();
        gerenciador.iniciarCombate(aliados, inimigos, tripulacao);
        
        aguardandoAcaoJogador = true;
        atualizarUI(true);
    }
    
    private Texture getTexturePersonagem(Personagem p) {
        String filename = p.getCaminhoImagem();
        if (textureCache.containsKey(filename)) {
            return textureCache.get(filename);
        }
        
        String[] possiblePaths = {
            "personagens/chapeu_de_palha/" + filename,
            "personagens/" + filename,
            "inimigos/" + filename,
            "inimigos/capangas/" + filename,
            filename
        };
        
        for (String path : possiblePaths) {
            if (Gdx.files.internal(path).exists()) {
                Texture tex = new Texture(Gdx.files.internal(path));
                textureCache.put(filename, tex);
                return tex;
            }
        }
        
        String placeholderPath = "personagens/aliados_especiais/placeholder.png";
        if (Gdx.files.internal(placeholderPath).exists()) {
            if (!textureCache.containsKey("placeholder")) {
                textureCache.put("placeholder", new Texture(Gdx.files.internal(placeholderPath)));
            }
            Texture tex = textureCache.get("placeholder");
            textureCache.put(filename, tex);
            return tex;
        }
        
        textureCache.put(filename, null);
        return null;
    }

    private void atualizarUI(boolean desenharBotoes) {
        uiTable.clear();
        
        // --- TIMELINE DE INICIATIVA ---
        Table timelineTable = new Table();
        timelineTable.add(new Label("Iniciativa:", skin)).padRight(10);
        
        java.util.List<Personagem> ordem = gerenciador.getFilaDeTurnos().getTodosOrdenados();
        Personagem personagemDaVez = null;
        if (!gerenciador.getFilaDeTurnos().getFila().isEmpty()) {
            personagemDaVez = gerenciador.getFilaDeTurnos().getFila().get(0);
        } else if (desenharBotoes) {
            personagemDaVez = gerenciador.getAliadoAguardandoAcao();
        }

        for (Personagem p : ordem) {
            if (!p.estaVivo()) continue;
            
            Table portraitBox = new Table();
            boolean isActing = (p == personagemDaVez);
            
            com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable bg = 
                new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(
                    frontend.SkinPadrao.textura1x1(0f, isActing ? 0.6f : 0f, 0f, 1f));
            
            if (isActing) {
                portraitBox.setBackground(bg);
            }
            
            Image placeholder = new Image(skin.newDrawable(isActing ? "dark" : "gray"));
            portraitBox.add(placeholder).size(30, 30).pad(2).row();
            
            String shortName = p.getNome().split(" ")[0];
            Label nameLbl = new Label(shortName, skin);
            nameLbl.setFontScale(0.8f);
            if (isActing) nameLbl.setColor(Color.GREEN);
            portraitBox.add(nameLbl);

            timelineTable.add(portraitBox).padRight(5);
        }
        
        uiTable.add(timelineTable).padTop(10).padBottom(5).row();

        Label titleLabel = new Label(ilhaAtual.getNome() + " - " + rodadaAtual.getDescricao(), skin);
        titleLabel.setColor(Color.ORANGE);
        titleLabel.setFontScale(2.0f);
        uiTable.add(titleLabel).padTop(5).padBottom(15).row();
        
        Table battleArea = new Table();
        Table leftColumn = new Table();
        Table rightColumn = new Table();
        
        // Status dos Aliados (esquerda)
        for (Personagem aliado : aliados) {
            if (aliado.estaVivo()) {
                Table charTable = new Table();
                int nivel = (aliado instanceof Aliado a) ? a.getNivel() : 1;
                String info = String.format("%s (Lvl: %d)\nDEF: %.1f", aliado.getNome(), nivel, aliado.getDefesa());
                Label lbl = new Label(info, skin);
                lbl.setAlignment(Align.center);
                charTable.add(lbl).padBottom(2).row();
                
                Table hpTable = new Table();
                hpTable.add(new Label("HP: ", skin));
                com.badlogic.gdx.scenes.scene2d.ui.ProgressBar hpBar = new com.badlogic.gdx.scenes.scene2d.ui.ProgressBar(0f, (float)aliado.getVidaMaxima(), 1f, false, skin);
                hpBar.setValue((float)aliado.getVidaAtual());
                hpBar.setAnimateDuration(0.2f);
                hpTable.add(hpBar).width(80).height(15).padRight(5);
                hpTable.add(new Label((int)aliado.getVidaAtual() + "/" + (int)aliado.getVidaMaxima(), skin));
                charTable.add(hpTable).padBottom(5).row();
                
                Group group = groupCache.get(aliado);
                if (group == null) {
                    Texture tex = getTexturePersonagem(aliado);
                    Image img;
                    if (tex != null) {
                        img = new Image(tex);
                    } else {
                        img = new Image(skin.newDrawable("dark"));
                    }
                    img.setScaling(com.badlogic.gdx.utils.Scaling.fit);
                    img.setSize(180, 180);
                    img.setOrigin(Align.center);
                    group = new Group();
                    group.setSize(180, 180);
                    group.addActor(img);
                    groupCache.put(aliado, group);
                }
                charTable.add(group).size(180, 180).row();
                
                int index = aliados.indexOf(aliado);
                float padL = (index % 2 == 1) ? 0 : 80;
                float padR = (index % 2 == 1) ? 80 : 0;
                leftColumn.add(charTable).pad(5).padLeft(padL).padRight(padR).row();
            }
        }
        
        // Status dos Inimigos (direita)
        for (Personagem ini : inimigos) {
            if (ini.estaVivo()) {
                Table charTable = new Table();
                String info = String.format("%s\nDEF: %.1f", ini.getNome(), ini.getDefesa());
                Label lbl = new Label(info, skin);
                lbl.setAlignment(Align.center);
                charTable.add(lbl).padBottom(2).row();
                
                Table hpTable = new Table();
                hpTable.add(new Label("HP: ", skin));
                com.badlogic.gdx.scenes.scene2d.ui.ProgressBar hpBar = new com.badlogic.gdx.scenes.scene2d.ui.ProgressBar(0f, (float)ini.getVidaMaxima(), 1f, false, skin);
                hpBar.setValue((float)ini.getVidaAtual());
                hpBar.setAnimateDuration(0.2f);
                hpTable.add(hpBar).width(80).height(15).padRight(5);
                hpTable.add(new Label((int)ini.getVidaAtual() + "/" + (int)ini.getVidaMaxima(), skin));
                charTable.add(hpTable).padBottom(5).row();
                
                Group group = groupCache.get(ini);
                if (group == null) {
                    Texture tex = getTexturePersonagem(ini);
                    Image img;
                    if (tex != null) {
                        img = new Image(tex);
                    } else {
                        img = new Image(skin.newDrawable("dark"));
                    }
                    img.setScaling(com.badlogic.gdx.utils.Scaling.fit);
                    img.setSize(180, 180);
                    img.setOrigin(Align.center);
                    group = new Group();
                    group.setSize(180, 180);
                    group.addActor(img);
                    groupCache.put(ini, group);
                }
                charTable.add(group).size(180, 180).row();
                
                int index = inimigos.indexOf(ini);
                float padL = (index % 2 == 1) ? 80 : 0;
                float padR = (index % 2 == 1) ? 0 : 80;
                rightColumn.add(charTable).pad(5).padLeft(padL).padRight(padR).row();
            }
        }
        
        battleArea.add(leftColumn).expand().center().pad(20);
        battleArea.add(rightColumn).expand().center().pad(20);
        
        uiTable.add(battleArea).expand().fill().row();
        
        uiTable.add(logLabel).pad(10).row();
        
        // Painel inferior (botões)
        Table controlPanel = new Table();
        if (desenharBotoes) {
            Personagem aliadoVez = gerenciador.getAliadoAguardandoAcao();
            if (aliadoVez != null) {
                int buttonCount = 0;
                if (!escolhendoAlvo) {
                    for (final Habilidade hab : aliadoVez.getHabilidades()) {
                        String poderFormatado = (hab.getTipo() == TipoHabilidade.DEFESA) 
                            ? String.format("%.1f", hab.getValorPoder()) 
                            : String.valueOf((int) hab.getValorPoder());
                        TextButton btn = new TextButton(hab.getNome() + " (" + poderFormatado + ")", skin);
                        btn.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                habilidadeSelecionada = hab;
                                escolhendoAlvo = true;
                                atualizarUI(true);
                            }
                        });
                        controlPanel.add(btn).size(300, 50).pad(10);
                        buttonCount++;
                        if (buttonCount % 2 == 0) controlPanel.row();
                    }
                } else {
                    boolean isCuraOuDefesa = (habilidadeSelecionada.getTipo() == TipoHabilidade.CURA || habilidadeSelecionada.getTipo() == TipoHabilidade.DEFESA);
                    ArrayList<Personagem> possiveisAlvos = isCuraOuDefesa ? aliados : inimigos;
                    
                    logLabel.setText("Selecione alvo para " + habilidadeSelecionada.getNome());
                    
                    for (final Personagem alvo : possiveisAlvos) {
                        if (alvo.estaVivo()) {
                            TextButton btnAlvo = new TextButton("Alvo: " + alvo.getNome(), skin);
                            btnAlvo.addListener(new ClickListener() {
                                @Override
                                public void clicked(InputEvent event, float x, float y) {
                                    escolhendoAlvo = false;
                                    aguardandoAcaoJogador = false;
                                    gerenciador.registrarAcaoJogador(habilidadeSelecionada, alvo);
                                    logLabel.setText(aliadoVez.getNome() + " usou " + habilidadeSelecionada.getNome());
                                    atualizarUI(false);
                                }
                            });
                            controlPanel.add(btnAlvo).size(300, 50).pad(10);
                            buttonCount++;
                            if (buttonCount % 2 == 0) controlPanel.row();
                        }
                    }
                    
                    TextButton btnVoltar = new TextButton("Voltar", skin);
                    btnVoltar.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            escolhendoAlvo = false;
                            atualizarUI(true);
                        }
                    });
                    controlPanel.add(btnVoltar).size(300, 50).pad(10);
                }
            }
        }
        uiTable.add(controlPanel).fillX().padBottom(20).row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        
        switch (gerenciador.getEstadoAtual()) {
            case PLANEJAMENTO_JOGADOR:
                if (!aguardandoAcaoJogador) {
                    aguardandoAcaoJogador = true;
                    atualizarUI(true);
                }
                break;
                
            case PLANEJAMENTO_INIMIGOS:
                // automático
                break;
                
            case EXECUCAO_TURNOS:
                delayAcumulado += delta;
                if (delayAcumulado > 1.5f) { // 1.5 segundo por animação/ação
                    delayAcumulado = 0;
                    Personagem quemAgiu = gerenciador.executarProximaAcao();
                    if (quemAgiu != null) {
                        logLabel.setText(gerenciador.getUltimoLog());
                        atualizarUI(false);
                        
                        AcaoPlanejada acao = gerenciador.getUltimaAcaoExecutada();
                        if (acao != null) {
                            Group gAtacante = groupCache.get(quemAgiu);
                            if (gAtacante != null && gAtacante.getChildren().size > 0) {
                                Image imgAtacante = (Image) gAtacante.getChildren().get(0);
                                boolean isAliado = aliados.contains(quemAgiu);
                                float moveX = isAliado ? 60f : -60f;
                                
                                imgAtacante.addAction(Actions.sequence(
                                    Actions.parallel(
                                        Actions.moveBy(moveX, 0, 0.1f),
                                        Actions.scaleTo(1.3f, 1.3f, 0.1f)
                                    ),
                                    Actions.parallel(
                                        Actions.moveBy(-moveX, 0, 0.1f),
                                        Actions.scaleTo(1.0f, 1.0f, 0.1f)
                                    )
                                ));
                            }
                            if (acao.alvo != null && acao.habilidade.getTipo() == TipoHabilidade.DANO) {
                                Group gAlvo = groupCache.get(acao.alvo);
                                if (gAlvo != null && gAlvo.getChildren().size > 0) {
                                    Image imgAlvo = (Image) gAlvo.getChildren().get(0);
                                    imgAlvo.addAction(Actions.sequence(
                                        Actions.delay(0.2f),
                                        Actions.color(Color.RED, 0.1f),
                                        Actions.color(Color.WHITE, 0.1f),
                                        Actions.color(Color.RED, 0.1f),
                                        Actions.color(Color.WHITE, 0.1f)
                                    ));
                                }
                            }
                        }
                    }
                }
                break;
                
            case VITORIA:
                if (delayAcumulado == 0) {
                    logLabel.setText("Vitoria! XP e Dinheiro ganhos.");
                    
                    boolean completa = ilhaAtual.avancarRodada();
                    if (completa) {
                        gameManager.getMapa().entrarIlha(ilhaAtual);
                    }
                    
                    uiTable.clear();
                    
                    Table winTable = new Table();
                    Label title = new Label(completa ? "ILHA CONCLUIDA!" : "VITORIA NA RODADA!", skin);
                    title.setColor(Color.YELLOW);
                    title.setFontScale(2.5f);
                    
                    Label xpLabel = new Label("Experiencia Ganha: " + gerenciador.getXpGanho(), skin);
                    Label coinLabel = new Label("Moedas Ganhas: " + gerenciador.getDinheiroGanho(), skin);
                    
                    TextButton btnSair = new TextButton("Continuar", skin);
                    btnSair.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            java.util.List<sistema.HabilidadePendente> pendentes = gerenciador.getHabilidadesPendentes();
                            if (!pendentes.isEmpty()) {
                                jogo.setScreen(new frontend.TelaAprenderHabilidade(jogo, gameManager, pendentes));
                            } else {
                                jogo.setScreen(new frontend.TelaMapa(jogo, gameManager));
                            }
                        }
                    });
                    
                    winTable.add(title).padBottom(60).row();
                    winTable.add(xpLabel).padBottom(20).row();
                    winTable.add(coinLabel).padBottom(60).row();
                    winTable.add(btnSair).size(400, 80);
                    
                    uiTable.add(winTable).expand().center();
                    
                    delayAcumulado = 1;
                }
                break;
                
            case DERROTA:
                if (delayAcumulado == 0) {
                    logLabel.setText("Sua tripulação foi derrotada... Game Over.");
                    uiTable.clear();
                    
                    Table lossTable = new Table();
                    Label title = new Label("GAME OVER", skin);
                    title.setColor(Color.RED);
                    title.setFontScale(2.5f);
                    
                    TextButton btnMenu = new TextButton("Voltar ao Menu", skin);
                    btnMenu.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            jogo.setScreen(new frontend.TelaInicio(jogo));
                        }
                    });
                    
                    lossTable.add(title).padBottom(60).row();
                    lossTable.add(btnMenu).size(400, 80);
                    
                    uiTable.add(lossTable).expand().center();
                    
                    delayAcumulado = 1;
                }
                break;
                
            default:
                break;
        }

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
        if (skin != null) skin.dispose();
        if (bgTexture != null) bgTexture.dispose();
        if (overlayTexture != null) overlayTexture.dispose();
        for (Texture tex : textureCache.values()) {
            if (tex != null) tex.dispose();
        }
    }
}
