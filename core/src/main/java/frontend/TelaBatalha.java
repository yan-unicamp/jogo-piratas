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
import sistema.GameManager;
import sistema.GerenciadorDeBatalha;
import sistema.JogoPiratas;
import sistema.Tripulacao;

import java.util.ArrayList;

public class TelaBatalha implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;
    private Stage stage;
    private Skin skin;
    private Table uiTable;
    private Label logLabel;
    
    private GerenciadorDeBatalha gerenciador;
    private float delayAcumulado = 0;
    
    private ArrayList<Personagem> aliados;
    private ArrayList<Personagem> inimigos;

    private boolean aguardandoAcaoJogador = false;
    private Habilidade habilidadeSelecionada;
    private boolean escolhendoAlvo = false;

    public TelaBatalha(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
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
        // Usa o gerenciador já preparado pelo GameManager
        gerenciador = gameManager.getGerenciadorDeBatalha();
        aliados = gerenciador.getAliados();
        inimigos = gerenciador.getInimigos();
        
        aguardandoAcaoJogador = true;
        atualizarUI(true);
    }

    private void atualizarUI(boolean desenharBotoes) {
        uiTable.clear();
        
        Table battleArea = new Table();
        Table leftColumn = new Table();
        Table rightColumn = new Table();
        
        // Status dos Aliados (esquerda)
        for (Personagem aliado : aliados) {
            if (aliado.estaVivo()) {
                Table charTable = new Table();
                String info = String.format("%s\nHP: %d/%d Lvl: 1\nDEF: %.1f", aliado.getNome(), aliado.getVidaAtual(), aliado.getVidaMaxima(), aliado.getDefesa());
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
                String info = String.format("%s\nHP: %d/%d Lvl: 1\nDEF: %.1f", ini.getNome(), ini.getVidaAtual(), ini.getVidaMaxima(), ini.getDefesa());
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
        if (gerenciador.getEstadoAtual() == sistema.GerenciadorDeBatalha.EstadoBatalha.VITORIA || 
            gerenciador.getEstadoAtual() == sistema.GerenciadorDeBatalha.EstadoBatalha.DERROTA) {
            
            TextButton btnContinuar = new TextButton("Continuar", skin);
            btnContinuar.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    boolean vitoria = (gerenciador.getEstadoAtual() == sistema.GerenciadorDeBatalha.EstadoBatalha.VITORIA);
                    gameManager.batalhaConcluida(vitoria, jogo);
                }
            });
            controlPanel.add(btnContinuar).size(250, 60).pad(10);
            
        } else if (desenharBotoes) {
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
                        logLabel.setText(quemAgiu.getNome() + " agiu!");
                        atualizarUI(false);
                    }
                }
                break;
                
            case VITORIA:
                logLabel.setText("Vitoria! XP e Dinheiro ganhos.");
                if (uiTable.getChildren().size <= 2) { // Evita recriar a UI toda hora
                    atualizarUI(true); // Redesenha a UI pra mostrar o botão de Continuar
                }
                break;
                
            case DERROTA:
                logLabel.setText("Derrota! O bando foi aniquilado.");
                if (uiTable.getChildren().size <= 2) {
                    atualizarUI(true); // Redesenha a UI pra mostrar o botão de Continuar
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
