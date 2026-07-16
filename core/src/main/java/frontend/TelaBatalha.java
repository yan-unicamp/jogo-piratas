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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;
import entidades.TipoHabilidade;
import factories.PersonagemFactory;
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

    public TelaBatalha(JogoPiratas jogo) {
        this.jogo = jogo;
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
        aliados = new ArrayList<>();
        aliados.add(PersonagemFactory.criarLuffy());
        aliados.add(PersonagemFactory.criarZoro());
        aliados.add(PersonagemFactory.criarChopper());
        
        inimigos = new ArrayList<>();
        inimigos.add(PersonagemFactory.criarMarinheiro(1));
        inimigos.add(PersonagemFactory.criarPirataInimigo(1));
        
        tripulacao = new Tripulacao();
        for (Personagem aliado : aliados) {
            tripulacao.getAliados().add((Aliado) aliado);
        }

        gerenciador = new GerenciadorDeBatalha();
        gerenciador.iniciarCombate(aliados, inimigos, tripulacao);
        
        aguardandoAcaoJogador = true;
        atualizarUI(true);
    }

    private void atualizarUI(boolean desenharBotoes) {
        uiTable.clear();
        
        Table charactersTable = new Table();
        Table leftTable = new Table();
        Table rightTable = new Table();
        
        // Status dos Aliados (esquerda)
        for (Personagem aliado : aliados) {
            if (aliado.estaVivo()) {
                Label lbl = new Label(aliado.getNome() + " HP: " + aliado.getVidaAtual() + "/" + aliado.getVidaMaxima() + " DEF: " + aliado.getDefesa(), skin);
                leftTable.add(lbl).pad(5).left().row();
            }
        }
        
        // Status dos Inimigos (direita)
        for (Personagem ini : inimigos) {
            if (ini.estaVivo()) {
                Label lbl = new Label(ini.getNome() + " HP: " + ini.getVidaAtual() + "/" + ini.getVidaMaxima() + " DEF: " + ini.getDefesa(), skin);
                rightTable.add(lbl).pad(5).right().row();
            }
        }
        
        charactersTable.add(leftTable).expandX().left().pad(20);
        charactersTable.add(rightTable).expandX().right().pad(20);
        
        uiTable.add(charactersTable).expandX().fillX().row();
        
        uiTable.add(logLabel).pad(20).row();
        
        if (desenharBotoes) {
            Personagem aliadoVez = gerenciador.getAliadoAguardandoAcao();
            if (aliadoVez != null) {
                Table botoesTable = new Table();
                if (!escolhendoAlvo) {
                    for (final Habilidade hab : aliadoVez.getHabilidades()) {
                        TextButton btn = new TextButton(hab.getNome() + " (" + hab.getValorPoder() + ")", skin);
                        btn.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                habilidadeSelecionada = hab;
                                escolhendoAlvo = true;
                                atualizarUI(true); // Redesenha com botões de alvo
                            }
                        });
                        botoesTable.add(btn).pad(5).fillX();
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
                            botoesTable.add(btnAlvo).pad(5).fillX();
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
                    botoesTable.add(btnVoltar).pad(5).fillX();
                }
                uiTable.add(botoesTable).colspan(2).pad(10).row();
            }
        }
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
                atualizarUI(false);
                break;
                
            case DERROTA:
                logLabel.setText("Derrota... Game Over.");
                atualizarUI(false);
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
