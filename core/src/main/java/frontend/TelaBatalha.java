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
    private boolean animando = false;
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
        jogo.audio.tocar(sistema.AudioManager.MUSICA_BATALHA, true);
        
        stage = new Stage(new com.badlogic.gdx.utils.viewport.FitViewport(1920, 1080), jogo.batch);
        Gdx.input.setInputProcessor(stage);
        
        criarSkin();
        
        com.badlogic.gdx.scenes.scene2d.ui.Stack rootStack = new com.badlogic.gdx.scenes.scene2d.ui.Stack();
        rootStack.setFillParent(true);
        stage.addActor(rootStack);
        
        String bgPath = ilhaAtual.getBgKey();
        if (!Gdx.files.internal(bgPath).exists()) {
            bgPath = "backgrounds/ilha_generica.png"; 
        }
        bgTexture = new Texture(Gdx.files.internal(bgPath));
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
    

    private void atualizarUI(boolean desenharBotoes) {
        uiTable.clear();
        
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
            
            Texture texP = p.getTextura();
            Image portrait = texP != null ? new Image(texP) : new Image(skin.newDrawable(isActing ? "dark" : "gray"));
            portrait.setScaling(com.badlogic.gdx.utils.Scaling.fit);
            portraitBox.add(portrait).size(40, 40).pad(2).row();
            
            String shortName = p.getNome().split(" ")[0];
            Label nameLbl = new Label(shortName, skin);
            nameLbl.setFontScale(0.8f);
            if (isActing) nameLbl.setColor(Color.GREEN);
            portraitBox.add(nameLbl);

            timelineTable.add(portraitBox).padRight(5);
        }
        
        uiTable.add(timelineTable).padTop(5).padBottom(5).row();

        Label titleLabel = new Label(ilhaAtual.getNome() + " - " + rodadaAtual.getDescricao(), skin);
        titleLabel.setColor(Color.ORANGE);
        titleLabel.setFontScale(1.5f);
        uiTable.add(titleLabel).padTop(2).padBottom(5).row();
        
        Table battleArea = new Table();
        Table leftColumn = new Table();
        Table rightColumn = new Table();
        
        int vivosAliados = 0;
        for (Personagem p : aliados) if (p.estaVivo()) vivosAliados++;
        int vivosInimigos = 0;
        for (Personagem p : inimigos) if (p.estaVivo()) vivosInimigos++;
        int maxChars = Math.max(vivosAliados, vivosInimigos);
        if (maxChars < 1) maxChars = 1;
        int tamanhoImg = (700 / maxChars) - 70;
        if (tamanhoImg > 350) tamanhoImg = 350;
        if (tamanhoImg < 100) tamanhoImg = 100;
        
        for (Personagem aliado : aliados) {
            if (aliado.estaVivo()) {
                Table charTable = new Table();
                Table statsTable = new Table();
                
                int nivel = (aliado instanceof Aliado a) ? a.getNivel() : 1;
                String info = String.format("%s (Lvl: %d)\nDEF: %.2f", aliado.getNome(), nivel, (1.0f - aliado.getDefesa()));
                Label lbl = new Label(info, skin);
                lbl.setAlignment(Align.center);
                statsTable.add(lbl).padBottom(2).row();
                
                Table hpTable = new Table();
                hpTable.add(new Label("HP: ", skin));
                com.badlogic.gdx.scenes.scene2d.ui.ProgressBar hpBar = new com.badlogic.gdx.scenes.scene2d.ui.ProgressBar(0f, (float)aliado.getVidaMaxima(), 1f, false, skin);
                hpBar.setValue((float)aliado.getVidaAtual());
                hpBar.setAnimateDuration(0.2f);
                hpTable.add(hpBar).width(80).height(10).padRight(5);
                hpTable.add(new Label((int)aliado.getVidaAtual() + "/" + (int)aliado.getVidaMaxima(), skin));
                statsTable.add(hpTable).padBottom(5).row();
                
                Group group = groupCache.get(aliado);
                if (group == null) {
                    Texture tex = aliado.getTextura();
                    Image img = tex != null ? new Image(tex) : new Image(skin.newDrawable("dark"));
                    img.setScaling(com.badlogic.gdx.utils.Scaling.fit);
                    group = new Group();
                    group.addActor(img);
                    groupCache.put(aliado, group);
                }
                group.setSize(tamanhoImg, tamanhoImg);
                if (group.getChildren().size > 0) {
                    group.getChildren().get(0).setSize(tamanhoImg, tamanhoImg);
                    group.getChildren().get(0).setOrigin(Align.center);
                }
                charTable.add(group).size(tamanhoImg, tamanhoImg).padRight(10);
                charTable.add(statsTable).row();
                
                int index = aliados.indexOf(aliado);
                float padL = (index % 2 == 1) ? 0 : 40;
                float padR = (index % 2 == 1) ? 40 : 0;
                leftColumn.add(charTable).pad(2).padLeft(padL).padRight(padR).row();
            }
        }
        
        for (Personagem ini : inimigos) {
            if (ini.estaVivo()) {
                Table charTable = new Table();
                Table statsTable = new Table();
                
                String info = String.format("%s\nDEF: %.2f", ini.getNome(), (1.0f - ini.getDefesa()));
                Label lbl = new Label(info, skin);
                lbl.setAlignment(Align.center);
                statsTable.add(lbl).padBottom(2).row();
                
                Table hpTable = new Table();
                hpTable.add(new Label("HP: ", skin));
                com.badlogic.gdx.scenes.scene2d.ui.ProgressBar hpBar = new com.badlogic.gdx.scenes.scene2d.ui.ProgressBar(0f, (float)ini.getVidaMaxima(), 1f, false, skin);
                hpBar.setValue((float)ini.getVidaAtual());
                hpBar.setAnimateDuration(0.2f);
                hpTable.add(hpBar).width(80).height(10).padRight(5);
                hpTable.add(new Label((int)ini.getVidaAtual() + "/" + (int)ini.getVidaMaxima(), skin));
                statsTable.add(hpTable).padBottom(5).row();
                
                Group group = groupCache.get(ini);
                if (group == null) {
                    Texture tex = ini.getTextura();
                    Image img = tex != null ? new Image(tex) : new Image(skin.newDrawable("dark"));
                    img.setScaling(com.badlogic.gdx.utils.Scaling.fit);
                    group = new Group();
                    group.addActor(img);
                    groupCache.put(ini, group);
                }
                group.setSize(tamanhoImg, tamanhoImg);
                if (group.getChildren().size > 0) {
                    group.getChildren().get(0).setSize(tamanhoImg, tamanhoImg);
                    group.getChildren().get(0).setOrigin(Align.center);
                }
                charTable.add(statsTable).padRight(10);
                charTable.add(group).size(tamanhoImg, tamanhoImg).row();
                
                int index = inimigos.indexOf(ini);
                float padL = (index % 2 == 1) ? 40 : 0;
                float padR = (index % 2 == 1) ? 0 : 40;
                rightColumn.add(charTable).pad(2).padLeft(padL).padRight(padR).row();
            }
        }
        
        battleArea.add(leftColumn).expand().center().pad(20);
        battleArea.add(rightColumn).expand().center().pad(20);
        
        uiTable.add(battleArea).expand().fill().row();
        
        Table logBox = new Table();
        logBox.setBackground(new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(SkinPadrao.textura1x1(0.1f, 0.1f, 0.15f, 0.8f)));
        logBox.add(logLabel).pad(15);
        uiTable.add(logBox).expandX().fillX().pad(10).padLeft(20).padRight(20).row();
        
        Table controlPanel = new Table();
        controlPanel.setBackground(new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(SkinPadrao.textura1x1(0.05f, 0.05f, 0.08f, 0.9f)));
        if (desenharBotoes) {
            Personagem aliadoVez = gerenciador.getAliadoAguardandoAcao();
            if (aliadoVez != null) {
                int buttonCount = 0;
                if (!escolhendoAlvo) {
                    Table optionsTable = new Table();
                    Label lblTurno = new Label("Vez de: " + aliadoVez.getNome(), skin);
                    lblTurno.setFontScale(1.3f);
                    lblTurno.setColor(Color.GOLD);
                    optionsTable.add(lblTurno).colspan(2).padBottom(15).center().row();
                    for (final Habilidade hab : aliadoVez.getHabilidades()) {
                        String poderFormatado = (hab.getTipo() == TipoHabilidade.DEFESA) 
                            ? String.format("%.1f", hab.getValorPoder()) 
                            : String.valueOf((int) hab.getValorPoder());
                        TextButton btn = new TextButton(hab.getNome() + " (" + poderFormatado + ")", skin);
                        btn.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Runnable acaoConfirmada = () -> {
                                    if (hab.isSelfcast()) {
                                        aguardandoAcaoJogador = false;
                                        gerenciador.registrarAcaoJogador(hab, aliadoVez);
                                        logLabel.setText(aliadoVez.getNome() + " preparou " + hab.getNome());
                                        atualizarUI(false);
                                    } else {
                                        habilidadeSelecionada = hab;
                                        escolhendoAlvo = true;
                                        atualizarUI(true);
                                    }
                                };
                                
                                if (hab.isEspecial()) {
                                    exibirConfirmacaoEspecial(hab, acaoConfirmada);
                                } else {
                                    acaoConfirmada.run();
                                }
                            }
                        });
                        optionsTable.add(btn).size(400, 60).pad(10);
                        buttonCount++;
                        if (buttonCount % 2 == 0) optionsTable.row();
                    }
                    controlPanel.add().width(440);
                    controlPanel.add(optionsTable).expandX().center();
                    
                    TextButton btnDebug = new TextButton("Vencer (Debug)", skin);
                    btnDebug.getLabel().setColor(Color.RED);
                    btnDebug.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            for (Personagem ini : inimigos) {
                                if (ini.estaVivo()) ini.receberDano(999999);
                            }
                            gerenciador.verificarVitoriaOuDerrota();
                            atualizarUI(false);
                        }
                    });
                    controlPanel.add(btnDebug).size(400, 60).pad(10).padLeft(30);
                } else {
                    boolean isCuraOuDefesa = (habilidadeSelecionada.getTipo() == TipoHabilidade.CURA || habilidadeSelecionada.getTipo() == TipoHabilidade.DEFESA);
                    ArrayList<Personagem> possiveisAlvos = isCuraOuDefesa ? aliados : inimigos;
                    
                    logLabel.setText("Selecione alvo para " + habilidadeSelecionada.getNome());
                    
                    Table optionsTable = new Table();
                    Label lblTurno = new Label("Vez de: " + aliadoVez.getNome(), skin);
                    lblTurno.setFontScale(1.3f);
                    lblTurno.setColor(Color.GOLD);
                    optionsTable.add(lblTurno).padBottom(15).center().row();

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
                            optionsTable.add(btnAlvo).size(400, 60).pad(10);
                            buttonCount++;
                            if (buttonCount % 2 == 0) optionsTable.row();
                        }
                    }
                    controlPanel.add().width(440);
                    controlPanel.add(optionsTable).expandX().center();
                    
                    TextButton btnVoltar = new TextButton("Voltar", skin);
                    btnVoltar.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            escolhendoAlvo = false;
                            atualizarUI(true);
                        }
                    });
                    controlPanel.add(btnVoltar).size(400, 60).pad(10).padLeft(30);
                }
            }
        }
        uiTable.add(controlPanel).fillX().minHeight(200).padBottom(20).row();
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
                break;

            case EXECUCAO_TURNOS:
                delayAcumulado += delta;
                if (!animando && delayAcumulado > 1.0f) {
                    delayAcumulado = 0;
                    Personagem quemAgiu = gerenciador.prepararProximaAcao();
                    if (quemAgiu != null) {
                        animando = true;
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
                            if (acao.alvo != null && acao.habilidade.getTipo() == entidades.TipoHabilidade.DANO) {
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
                } else if (animando && delayAcumulado > 0.5f) {
                    delayAcumulado = 0;
                    animando = false;
                    gerenciador.aplicarAcaoPreparada();
                    logLabel.setText(gerenciador.getUltimoLog());
                    atualizarUI(false);
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
                    
                    java.util.List<entidades.Aliado> desbloqueados = gerenciador.getAliadosDesbloqueados();
                    Table unlockTable = new Table();
                    for (entidades.Aliado unlocked : desbloqueados) {
                        Label unlockTitle = new Label("NOVO TRIPULANTE DESBLOQUEADO!", skin);
                        unlockTitle.setColor(Color.CYAN);
                        unlockTitle.setFontScale(1.5f);
                        unlockTable.add(unlockTitle).padBottom(10).row();
                        
                        Texture tex = unlocked.getTextura();
                        Image img = tex != null ? new Image(tex) : new Image(skin.newDrawable("dark"));
                        img.setScaling(com.badlogic.gdx.utils.Scaling.fit);
                        unlockTable.add(img).size(250, 250).padBottom(10).row();
                        
                        Label nameLabel = new Label(unlocked.getNome(), skin);
                        nameLabel.setFontScale(2.0f);
                        nameLabel.setColor(Color.GOLD);
                        unlockTable.add(nameLabel).padBottom(10).row();
                        
                        Label warningLabel = new Label("Equipe este personagem no menu de Selecao de Tripulacao!", skin);
                        warningLabel.setColor(Color.LIGHT_GRAY);
                        warningLabel.setFontScale(1.3f);
                        unlockTable.add(warningLabel).padBottom(30).row();
                    }

                    winTable.add(title).padBottom(30).row();
                    if (!desbloqueados.isEmpty()) {
                        winTable.add(unlockTable).padBottom(20).row();
                    }
                    winTable.add(xpLabel).padBottom(10).row();
                    winTable.add(coinLabel).padBottom(20).row();
                    
                    for (String rec : gerenciador.getRecompensasExtras()) {
                        Label recLabel = new Label(rec, skin);
                        recLabel.setColor(Color.GREEN);
                        winTable.add(recLabel).padBottom(10).row();
                    }
                    
                    TextButton btnSair = new TextButton("Continuar", skin);
                    btnSair.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            java.util.List<sistema.HabilidadePendente> pendentes = gerenciador.getHabilidadesPendentes();
                            Runnable onComplete = () -> {
                                if (completa) {
                                    jogo.setScreen(new frontend.TelaMapa(jogo, gameManager));
                                } else {
                                    jogo.setScreen(new frontend.TelaBatalha(jogo, gameManager, ilhaAtual, ilhaAtual.getRodadaAtual()));
                                }
                            };
                            
                            if (!pendentes.isEmpty()) {
                                jogo.setScreen(new frontend.TelaAprenderHabilidade(jogo, gameManager, pendentes, onComplete));
                            } else {
                                onComplete.run();
                            }
                        }
                    });
                    
                    winTable.add(btnSair).size(400, 80).padTop(20);
                    
                    uiTable.add(winTable).expand().center();
                    
                    delayAcumulado = 1;
                }
                break;
                
            case DERROTA:
                if (delayAcumulado == 0) {
                    logLabel.setText("Sua tripulacao foi derrotada... Game Over.");
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
    
    private void exibirConfirmacaoEspecial(Habilidade hab, Runnable acaoConfirmada) {
        uiTable.clear();
        
        Table confTable = new Table();
        Label titulo = new Label("Atencao!", skin);
        titulo.setColor(Color.YELLOW);
        titulo.setFontScale(2.0f);
        
        Label aviso = new Label("O ataque especial '" + hab.getNome() + "' deixara o personagem exausto.\nEle perdera o proximo turno. Deseja continuar?", skin);
        aviso.setAlignment(Align.center);
        
        Table botoes = new Table();
        TextButton btnSim = new TextButton("Sim", skin);
        btnSim.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                acaoConfirmada.run();
            }
        });
        
        TextButton btnNao = new TextButton("Nao", skin);
        btnNao.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                atualizarUI(true);
            }
        });
        
        botoes.add(btnSim).size(200, 60).pad(10);
        botoes.add(btnNao).size(200, 60).pad(10);
        
        confTable.add(titulo).padBottom(20).row();
        confTable.add(aviso).padBottom(40).row();
        confTable.add(botoes);
        
        uiTable.add(confTable).expand().center();
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
