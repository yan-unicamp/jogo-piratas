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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;
import entidades.TipoHabilidade;
import factories.PersonagemFactory;
import sistema.GameManager;
import sistema.GerenciadorDeBatalha;
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

    private GameManager gameManager;
    private progressao.Ilha ilhaAtual;
    private progressao.Rodada rodadaAtual;

    public TelaBatalha(JogoPiratas jogo, GameManager gameManager, progressao.Ilha ilha, progressao.Rodada rodada) {
        this.jogo = jogo;
        this.gameManager = gameManager;
        this.ilhaAtual = ilha;
        this.rodadaAtual = rodada;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport(), jogo.batch);
        Gdx.input.setInputProcessor(stage);
        
        criarSkin();
        
        uiTable = new Table();
        uiTable.setFillParent(true);
        stage.addActor(uiTable);
        
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
    }

    private void inicializarBatalha() {
        this.tripulacao = gameManager.getTripulacao();
        this.aliados = new ArrayList<>(tripulacao.getAliadosAtivos());
        
        // Clona os inimigos para a batalha não afetar o blueprint
        this.inimigos = new ArrayList<>();
        for(Personagem e : rodadaAtual.getInimigos()) {
            Inimigo clone = (Inimigo) e; // Em um jogo completo deve-se clonar a instância de inimigo
            this.inimigos.add(clone);
        }

        gerenciador = new GerenciadorDeBatalha();
        gerenciador.iniciarCombate(aliados, inimigos, tripulacao);
        
        aguardandoAcaoJogador = true;
        atualizarUI(true);
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
            
            // Fundo verde se for a vez do personagem
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
        
        uiTable.add(timelineTable).padTop(10).padBottom(20).row();
        
        Table battleArea = new Table();
        Table leftColumn = new Table();
        Table rightColumn = new Table();
        
        // Status dos Aliados (esquerda)
        for (Personagem aliado : aliados) {
            if (aliado.estaVivo()) {
                Table charTable = new Table();
                int nivel = (aliado instanceof Aliado a) ? a.getNivel() : 1;
                String info = String.format("%s\nHP: %d/%d Lvl: %d\nDEF: %.1f", aliado.getNome(), aliado.getVidaAtual(), aliado.getVidaMaxima(), nivel, aliado.getDefesa());
                Label lbl = new Label(info, skin);
                lbl.setAlignment(Align.center);
                charTable.add(lbl).padBottom(5).row();
                
                Image img = new Image(skin.newDrawable("dark"));
                charTable.add(img).size(80, 80).row();
                
                leftColumn.add(charTable).pad(20).row();
            }
        }
        
        // Status dos Inimigos (direita)
        for (Personagem ini : inimigos) {
            if (ini.estaVivo()) {
                Table charTable = new Table();
                String info = String.format("%s\nHP: %d/%d\nDEF: %.1f", ini.getNome(), ini.getVidaAtual(), ini.getVidaMaxima(), ini.getDefesa());
                Label lbl = new Label(info, skin);
                lbl.setAlignment(Align.center);
                charTable.add(lbl).padBottom(5).row();
                
                Image img = new Image(skin.newDrawable("dark"));
                charTable.add(img).size(80, 80).row();
                
                rightColumn.add(charTable).pad(20).row();
            }
        }
        
        battleArea.add(leftColumn).expand().left().pad(20);
        battleArea.add(rightColumn).expand().right().pad(20);
        
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
                        controlPanel.add(btn).size(250, 60).pad(10);
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
                            controlPanel.add(btnAlvo).size(250, 60).pad(10);
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
                    controlPanel.add(btnVoltar).size(250, 60).pad(10);
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
                    }
                }
                break;
                
            case VITORIA:
                if (delayAcumulado == 0) { // Flag para rodar apenas uma vez
                    logLabel.setText("Vitoria! A rodada foi concluida.");
                    boolean completa = ilhaAtual.avancarRodada();
                    if (completa) {
                        gameManager.getMapa().entrarIlha(ilhaAtual);
                        logLabel.setText("Vitoria! A ilha foi completamente dominada.");
                    }
                    
                    uiTable.clear();
                    TextButton btnContinuar = new TextButton("Continuar", skin);
                    btnContinuar.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            java.util.List<sistema.HabilidadePendente> pendentes = gerenciador.getHabilidadesPendentes();
                            if (!pendentes.isEmpty()) {
                                jogo.setScreen(new TelaAprenderHabilidade(jogo, gameManager, pendentes));
                            } else {
                                jogo.setScreen(new TelaMapa(jogo, gameManager));
                            }
                        }
                    });
                    uiTable.add(logLabel).padBottom(20).row();
                    uiTable.add(btnContinuar).size(250, 60);
                    
                    delayAcumulado = 1;
                }
                break;
                
            case DERROTA:
                if (delayAcumulado == 0) {
                    logLabel.setText("Sua tripulação foi derrotada... Game Over.");
                    uiTable.clear();
                    TextButton btnMenu = new TextButton("Voltar ao Menu", skin);
                    btnMenu.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            jogo.setScreen(new TelaInicio(jogo));
                        }
                    });
                    uiTable.add(logLabel).padBottom(20).row();
                    uiTable.add(btnMenu).size(250, 60);
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
    }
}
