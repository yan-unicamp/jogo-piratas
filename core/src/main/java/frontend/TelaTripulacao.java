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
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

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

    private final Color COR_PERGAMINHO = new Color(0.85f, 0.76f, 0.60f, 1f);
    private final Color COR_MADEIRA_ESCURA = new Color(0.35f, 0.20f, 0.10f, 1f);
    private final Color COR_MADEIRA_CLARA = new Color(0.55f, 0.35f, 0.23f, 1f);
    private final Color COR_TEXTO_ESCURO = new Color(0.15f, 0.08f, 0.04f, 1f);
    private final Color COR_BARRA_HP = new Color(0.1f, 0.4f, 0.6f, 1f);
    private final Color COR_BARRA_DEF = new Color(0.6f, 0.2f, 0.2f, 1f);

    private Table painelInferior; // Seletor de tripulacao
    private Table painelEsquerdo;
    private Table painelCentral;
    private Table painelDireito;

    public TelaTripulacao(JogoPiratas jogo, GameManager gameManager, Screen telaAnterior) {
        this.jogo = jogo;
        this.gameManager = gameManager;
        this.telaAnterior = telaAnterior;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(1920, 1080), jogo.batch);
        Gdx.input.setInputProcessor(stage);
        skin = SkinPadrao.criar();

        Table root = new Table();
        root.setFillParent(true);
        root.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(COR_PERGAMINHO.r, COR_PERGAMINHO.g, COR_PERGAMINHO.b, 1f)));
        stage.addActor(root);

        Table header = new Table();
        Label titulo = new Label("VER TRIPULACAO", skin);
        titulo.setFontScale(1.8f);
        titulo.setColor(COR_TEXTO_ESCURO);
        
        int totalAliados = gameManager.getTripulacao().getAliados().size();
        Label lblInfo = new Label("Tripulacao: " + totalAliados + "/10\nBounty: 1,500,000,000", skin);
        lblInfo.setAlignment(Align.right);
        lblInfo.setFontScale(1.1f);
        lblInfo.setColor(COR_TEXTO_ESCURO);

        header.add(titulo).expandX().center().padLeft(150);
        header.add(lblInfo).right().padRight(40);
        
        root.add(header).fillX().padTop(20).padBottom(20).row();

        // CONTEUDO CENTRAL (3 colunas)
        Table conteudo = new Table();
        
        painelEsquerdo = new Table();
        painelCentral = new Table();
        painelDireito = new Table();

        conteudo.add(painelEsquerdo).width(500).expandY().fillY().pad(10);
        conteudo.add(painelCentral).expand().fill().pad(10);
        conteudo.add(painelDireito).width(600).expandY().fillY().pad(10);

        root.add(conteudo).expand().fill().row();

        Table bottomBar = new Table();
        bottomBar.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(COR_MADEIRA_ESCURA.r, COR_MADEIRA_ESCURA.g, COR_MADEIRA_ESCURA.b, 1f)));
        bottomBar.pad(10);

        painelInferior = new Table();
        ScrollPane scrollInferior = new ScrollPane(painelInferior, skin);
        scrollInferior.setScrollingDisabled(false, true);

        TextButton btnVoltar = new TextButton("B: Voltar", skin);
        btnVoltar.setColor(Color.GOLD);
        btnVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                jogo.setScreen(telaAnterior);
            }
        });

        bottomBar.add(scrollInferior).expandX().fillX().padRight(20);
        bottomBar.add(btnVoltar).width(150).height(50);
        
        root.add(bottomBar).fillX().height(150).row();

        if (!gameManager.getTripulacao().getAliados().isEmpty()) {
            Aliado primeiro = gameManager.getTripulacao().getAliados().get(0);
            atualizarPainelInferior(primeiro);
            mostrarDetalhes(primeiro);
        }
    }

    private void atualizarPainelInferior(final Aliado aliadoSelecionado) {
        painelInferior.clear();
        for (final Aliado aliado : gameManager.getTripulacao().getAliados()) {
            boolean isSelecionado = (aliado == aliadoSelecionado);
            boolean isAtivo = gameManager.getTripulacao().getAliadosAtivos().contains(aliado);
            
            Table btnContainer = new Table();
            btnContainer.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.enabled);
            
            if (isSelecionado) {
                btnContainer.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.9f, 0.8f, 0.2f, 1f)));
                btnContainer.pad(5);
            } else if (isAtivo) {
                btnContainer.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.2f, 0.8f, 0.2f, 1f)));
                btnContainer.pad(3);
            }

            Image img;
            if (aliado.getTextura() != null) {
                img = new Image(aliado.getTextura());
                img.setScaling(Scaling.fit);
            } else {
                img = new Image(new TextureRegionDrawable(SkinPadrao.textura1x1(0.3f, 0.3f, 0.3f, 1f)));
            }
            btnContainer.add(img).size(100, 100);

            btnContainer.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    atualizarPainelInferior(aliado);
                    mostrarDetalhes(aliado);
                }
            });
            
            painelInferior.add(btnContainer).padRight(15);
        }
    }

    private void mostrarDetalhes(final Aliado aliado) {
        painelEsquerdo.clear();
        painelEsquerdo.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(COR_MADEIRA_ESCURA.r, COR_MADEIRA_ESCURA.g, COR_MADEIRA_ESCURA.b, 1f)));
        painelEsquerdo.pad(5);

        Table statusInner = new Table();
        statusInner.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(COR_PERGAMINHO.r, COR_PERGAMINHO.g, COR_PERGAMINHO.b, 1f)));
        statusInner.pad(20);
        painelEsquerdo.add(statusInner).expand().fill();

        Label lblNome = new Label(removerAcentos(aliado.getNome()), skin);
        lblNome.setFontScale(1.5f);
        lblNome.setColor(COR_TEXTO_ESCURO);
        statusInner.add(lblNome).colspan(2).padBottom(20).center().row();

        Label lblFuncao = new Label("Funcao: " + (aliado.getNome().contains("Luffy") ? "Capitao / Lutador" : "Tripulante"), skin);
        lblFuncao.setColor(COR_TEXTO_ESCURO);
        statusInner.add(lblFuncao).colspan(2).padBottom(20).left().row();

        adicionarBarraAtributo(statusInner, "Vitalidade (HP)", aliado.getVidaAtual(), aliado.getVidaMaxima(), COR_BARRA_HP);
        
        int xpBase = 100 * aliado.getNivel();
        adicionarBarraAtributo(statusInner, "Experiencia (XP)", aliado.getExperiencia(), xpBase, COR_BARRA_DEF);
        
        Label lblNivel = new Label("Nivel: " + aliado.getNivel(), skin);
        lblNivel.setColor(COR_TEXTO_ESCURO);
        statusInner.add(lblNivel).colspan(2).padTop(10).left().row();
        
        Label lblDefesa = new Label("Defesa: " + String.format("%.1f", aliado.getDefesa()), skin);
        lblDefesa.setColor(COR_TEXTO_ESCURO);
        statusInner.add(lblDefesa).colspan(2).padTop(10).left().row();

        statusInner.add().expandY().fillY().row();

        painelCentral.clear();
        if (aliado.getTextura() != null) {
            Image imgCentral = new Image(aliado.getTextura());
            imgCentral.setScaling(Scaling.fit);
            painelCentral.add(imgCentral).expand().fill().pad(20);
        }

        painelDireito.clear();
        painelDireito.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(COR_MADEIRA_ESCURA.r, COR_MADEIRA_ESCURA.g, COR_MADEIRA_ESCURA.b, 1f)));
        painelDireito.pad(15);

        Label lblHabilidades = new Label("TECNICAS DE COMBATE", skin);
        lblHabilidades.setFontScale(1.2f);
        lblHabilidades.setColor(COR_PERGAMINHO);
        painelDireito.add(lblHabilidades).padBottom(20).center().row();

        for (Habilidade hab : aliado.getHabilidades()) {
            Table boxHab = new Table();
            boxHab.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(COR_MADEIRA_CLARA.r, COR_MADEIRA_CLARA.g, COR_MADEIRA_CLARA.b, 1f)));
            boxHab.pad(10);
            
            Label lblHabNome = new Label(removerAcentos(hab.getNome()), skin);
            lblHabNome.setColor(Color.WHITE);
            boxHab.add(lblHabNome).left().expandX().row();
            
            Label lblHabDetalhe = new Label(hab.getTipo().toString() + " | Poder: " + String.format("%.1f", hab.getValorPoder()), skin);
            lblHabDetalhe.setFontScale(0.8f);
            lblHabDetalhe.setColor(Color.LIGHT_GRAY);
            boxHab.add(lblHabDetalhe).left().row();

            painelDireito.add(boxHab).expandX().fillX().padBottom(10).row();
        }
        
        painelDireito.add().expandY().fillY().row();

        final boolean isAtivo = gameManager.getTripulacao().getAliadosAtivos().contains(aliado);
        String txtBotao = isAtivo ? "Desconvocar Tripulante" : "Convocar Tripulante";
        TextButton btnConvocar = new TextButton(txtBotao, skin);
        
        final Label lblAviso = new Label("", skin);
        lblAviso.setColor(Color.RED);

        btnConvocar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int ativos = gameManager.getTripulacao().getAliadosAtivos().size();
                if (isAtivo) {
                    if (ativos > 1) {
                        gameManager.getTripulacao().removerAliadoAtivo(aliado);
                        atualizarPainelInferior(aliado);
                        mostrarDetalhes(aliado);
                    } else {
                        lblAviso.setText("A equipe de ataque nao pode ficar vazia!");
                    }
                } else {
                    if (ativos < 3) {
                        gameManager.getTripulacao().adicionarAliadoAtivo(aliado);
                        atualizarPainelInferior(aliado);
                        mostrarDetalhes(aliado);
                    } else {
                        lblAviso.setText("A equipe de ataque esta cheia (Max: 3)!");
                    }
                }
            }
        });
        painelDireito.add(btnConvocar).width(300).height(50).padTop(20).row();
        painelDireito.add(lblAviso).padTop(10).row();
    }

    private void adicionarBarraAtributo(Table tabela, String nome, float atual, float maximo, Color corBarra) {
        Label lblNome = new Label(nome, skin);
        lblNome.setColor(COR_TEXTO_ESCURO);
        tabela.add(lblNome).left().padTop(10).row();

        Table bgBarra = new Table();
        bgBarra.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.2f, 0.2f, 0.2f, 1f)));
        
        Table fgBarra = new Table();
        fgBarra.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(corBarra.r, corBarra.g, corBarra.b, 1f)));
        
        float pct = maximo > 0 ? atual / maximo : 0;
        if (pct > 1) pct = 1;
        
        bgBarra.add(fgBarra).width(300 * pct).height(20).left().expandX();
        
        Label lblValor = new Label((int)atual + "/" + (int)maximo, skin);
        lblValor.setColor(COR_TEXTO_ESCURO);
        lblValor.setFontScale(0.9f);

        Table barraContainer = new Table();
        barraContainer.add(bgBarra).width(300).height(20).left();
        barraContainer.add(lblValor).padLeft(10).left();

        tabela.add(barraContainer).left().row();
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
