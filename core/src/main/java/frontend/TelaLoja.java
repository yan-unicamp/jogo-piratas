package frontend;

import java.util.List;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entidades.Aliado;
import sistema.GameManager;
import sistema.JogoPiratas;

public class TelaLoja implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;
    private final progressao.Ilha ilhaAtual;
    private Stage stage;
    private Skin skin;
    private Label ouroLbl;

    public TelaLoja(JogoPiratas jogo, GameManager gameManager, progressao.Ilha ilha) {
        this.jogo = jogo;
        this.gameManager = gameManager;
        this.ilhaAtual = ilha;
    }

    @Override
    public void show() {
        jogo.audio.tocar(sistema.AudioManager.MUSICA_LOJA, true);
        
        stage = new Stage(new FitViewport(1920, 1080), jogo.batch);
        Gdx.input.setInputProcessor(stage);
        
        final Runnable acaoVoltar = () -> {
            gameManager.getMapa().entrarIlha(ilhaAtual);
            jogo.setScreen(new frontend.TelaMapa(jogo, gameManager));
        };
        
        stage.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {
            @Override
            public boolean keyDown(com.badlogic.gdx.scenes.scene2d.InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.B) {
                    acaoVoltar.run();
                    return true;
                }
                return super.keyDown(event, keycode);
            }
        });
        
        skin = SkinPadrao.criar();

        Stack stack = new Stack();
        stack.setFillParent(true);
        stage.addActor(stack);

        Texture texPerg = jogo.assets.getTextura("backgrounds/pergaminho.png");
        if (texPerg != null) {
            Image bgImg = new Image(new TextureRegionDrawable(new TextureRegion(texPerg)));
            bgImg.setScaling(Scaling.fill);
            bgImg.setColor(0.5f, 0.5f, 0.5f, 1f);
            stack.add(bgImg);
        } else {
            Table bg = new Table();
            bg.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.05f, 0.1f, 0.1f, 1f)));
            stack.add(bg);
        }

        Table root = new Table();
        root.setFillParent(true);
        root.pad(30);
        stack.add(root);

        Table header = new Table();
        
        Table tituloContainer = new Table();
        tituloContainer.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0f, 0f, 0f, 0.7f)));
        tituloContainer.pad(10, 30, 10, 30);
        
        Label subTitulo = new Label("Straw Hat Pirates", skin);
        subTitulo.setColor(Color.LIGHT_GRAY);
        subTitulo.setFontScale(1.0f);
        
        Label titulo = new Label("MERCADO DO BANDO", skin);
        titulo.setFontScale(2.2f);
        titulo.setColor(Color.GOLD);
        
        tituloContainer.add(subTitulo).row();
        tituloContainer.add(titulo);
        
        Table infoPanel = new Table();
        infoPanel.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.35f, 0.2f, 0.1f, 0.9f)));
        infoPanel.pad(10, 20, 10, 20);
        
        ouroLbl = new Label("Carteira: " + formatarBerries(gameManager.getOuro()), skin);
        ouroLbl.setColor(Color.GOLD);
        ouroLbl.setFontScale(1.2f);
        
        Label descLbl = new Label("Desconto da Tripulacao: 15%", skin);
        descLbl.setColor(new Color(0.6f, 1f, 0.6f, 1f));
        descLbl.setFontScale(0.9f);
        
        infoPanel.add(ouroLbl).right().row();
        infoPanel.add(descLbl).right();
        
        header.add(new Table()).expandX();
        header.add(tituloContainer).expandX().center();
        header.add(infoPanel).expandX().right();
        
        root.add(header).fillX().padBottom(40).row();

        Table grid = new Table();
        grid.defaults().pad(15).width(450).height(120);
        
        grid.add(criarCardLoja("Carne de Rei dos Mares", "Cura 100% da vida de todos os aliados vivos.", 300, "itens/icon_carne.png", (card) -> {
            if (gameManager.gastarOuro(300)) {
                for (Aliado aliado : gameManager.getTripulacao().getAliados()) {
                    if (aliado.getVidaAtual() > 0) aliado.curar(aliado.getVidaMaxima());
                }
                atualizarOuro();
                mostrarAnimacaoGasto(card, 300);
                desativarCard(card);
            }
        }, true));
        
        grid.add(criarCardLoja("Garrafa de Cola de Alta Pressao", "Revive aliados caídos e cura 100% da vida.", 800, "itens/icon_cola.png", (card) -> {
            if (gameManager.gastarOuro(800)) {
                for (Aliado aliado : gameManager.getTripulacao().getAliados()) {
                    aliado.curar(aliado.getVidaMaxima());
                }
                atualizarOuro();
                mostrarAnimacaoGasto(card, 800);
                desativarCard(card);
            }
        }, true));
        
        grid.add(criarCardLoja("Sake de Qualidade do Shaky's Bar", "Uma bebida revigorante. Concede 100 XP para todos.", 150, "itens/icon_sake.png", (card) -> {
            if (gameManager.gastarOuro(150)) {
                darXpParaTodos(100, 150, card);
                desativarCard(card);
            }
        }, true));
        
        grid.add(criarCardLoja("SMILE (Fruta do Diabo)", "Poder misterioso! Concede 250 XP para todos.", 500, "itens/icon_akumanomi.png", (card) -> {
            if (gameManager.gastarOuro(500)) {
                darXpParaTodos(250, 500, card);
                desativarCard(card);
            }
        }, true));
        grid.row();
        
        grid.add(criarCardLoja("Bussola de Grand Line (Log Pose)", "Melhora a navegação. +20 Vida Maxima para todos.", 350, "itens/icon_logpose.png", (card) -> {
            if (gameManager.gastarOuro(350)) {
                for (Aliado aliado : gameManager.getTripulacao().getAliados()) {
                    aliado.aumentarVidaMaxima(20);
                }
                atualizarOuro();
                mostrarAnimacaoGasto(card, 350);
                desativarCard(card);
            }
        }, true));
        
        grid.add(criarCardLoja("Espada de Qualidade (Wazamono)", "Lâmina afiada. +4 Iniciativa para todos.", 400, "itens/icon_espada.png", (card) -> {
            if (gameManager.gastarOuro(400)) {
                for (Aliado aliado : gameManager.getTripulacao().getAliados()) {
                    aliado.aumentarIniciativa(4);
                }
                atualizarOuro();
                mostrarAnimacaoGasto(card, 400);
                desativarCard(card);
            }
        }, true));
        
        grid.add(criarCardLoja("Pecas de Reparo Avancado", "Melhorias estruturais. +10 Vida Maxima para todos.", 150, "itens/icon_ferramentas.png", (card) -> {
            if (gameManager.gastarOuro(150)) {
                for (Aliado aliado : gameManager.getTripulacao().getAliados()) {
                    aliado.aumentarVidaMaxima(10);
                }
                atualizarOuro();
                mostrarAnimacaoGasto(card, 150);
                desativarCard(card);
            }
        }, true));
        
        grid.add(criarCardLoja("Manual de Navegacao Avancada", "Conhecimento é poder. Concede 50 XP para todos.", 80, "itens/icon_manual.png", (card) -> {
            if (gameManager.gastarOuro(80)) {
                darXpParaTodos(50, 80, card);
                desativarCard(card);
            }
        }, true));
        grid.row();
        
        grid.add(criarCardLoja("Material de Reparo da Franky Family", "Reparo rápido. Cura 50% da vida de todos.", 150, "itens/icon_ferramentas.png", (card) -> {
            if (gameManager.gastarOuro(150)) {
                for (Aliado aliado : gameManager.getTripulacao().getAliados()) {
                    if (aliado.getVidaAtual() > 0) aliado.curar(aliado.getVidaMaxima() / 2);
                }
                atualizarOuro();
                mostrarAnimacaoGasto(card, 150);
                desativarCard(card);
            }
        }, true));
        grid.add(); grid.add(); grid.add();
        
        root.add(grid).expand().center().row();

        Table footer = new Table();
        footer.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0f, 0f, 0f, 0.7f)));
        footer.pad(10);
        
        Label lblDicas = new Label("[B: Voltar]", skin);
        lblDicas.setColor(Color.CYAN);
        
        TextButton btnVoltar = new TextButton("Voltar para o Mapa", skin);
        btnVoltar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                acaoVoltar.run();
            }
        });
        
        footer.add().expandX();
        footer.add(lblDicas).padRight(30);
        footer.add(btnVoltar).width(200).height(50);
        
        root.add(footer).fillX().bottom();
    }

    private void desativarCard(Table card) {
        card.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.disabled);
        card.getColor().a = 0.4f; // Dim the whole card to indicate it was bought
    }

    private void darXpParaTodos(int xp, int preco, Table card) {
        java.util.List<sistema.HabilidadePendente> pendentes = new java.util.ArrayList<>();
        for (Aliado aliado : gameManager.getTripulacao().getAliadosAtivos()) {
            java.util.List<entidades.Habilidade> destravadas = aliado.ganharExperiencia(xp);
            for (entidades.Habilidade h : destravadas) {
                if (aliado.getHabilidades().size() < 4) {
                    aliado.adicionarHabilidade(h);
                } else {
                    pendentes.add(new sistema.HabilidadePendente(aliado, h));
                }
            }
        }
        atualizarOuro();
        mostrarAnimacaoGasto(card, preco); 
        
        if (!pendentes.isEmpty()) {
            Runnable onComplete = () -> jogo.setScreen(new frontend.TelaLoja(jogo, gameManager, ilhaAtual));
            jogo.setScreen(new frontend.TelaAprenderHabilidade(jogo, gameManager, pendentes, onComplete));
        }
    }

    private void mostrarAnimacaoGasto(Table card, int valorBase) {
        String texto = "- " + formatarBerries(valorBase);
        Label lblAnim = new Label(texto, skin);
        lblAnim.setColor(Color.RED);
        lblAnim.setFontScale(1.5f);
        
        Vector2 pos = card.localToStageCoordinates(new Vector2(card.getWidth()/2, card.getHeight()));
        lblAnim.setPosition(pos.x - lblAnim.getPrefWidth()/2, pos.y);
        
        stage.addActor(lblAnim);
        
        lblAnim.addAction(Actions.sequence(
            Actions.parallel(Actions.moveBy(0, 80, 1.2f), Actions.fadeOut(1.2f)),
            Actions.removeActor()
        ));
    }

    private String formatarBerries(int ouroBase) {
        int berries = ouroBase * 1000;
        return String.format("%,d", berries).replace(',', '.') + " B";
    }

    private Table criarCardLoja(String nome, String descricao, int precoBase, String iconPath, Consumer<Table> acao, boolean disponivel) {
        Table card = new Table();
        card.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.35f, 0.2f, 0.1f, disponivel ? 0.95f : 0.5f)));
        
        Table iconBox = new Table();
        iconBox.setBackground(new TextureRegionDrawable(SkinPadrao.textura1x1(0.85f, 0.76f, 0.60f, disponivel ? 1f : 0.5f)));
        
        Texture tex = jogo.assets.getTextura(iconPath);
        if (tex != null) {
            Image iconImg = new Image(new TextureRegionDrawable(new TextureRegion(tex)));
            iconImg.setScaling(Scaling.fit);
            if (!disponivel) iconImg.setColor(1f, 1f, 1f, 0.5f);
            iconBox.add(iconImg).expand().fill().pad(5);
        } else {
            Label iconLbl = new Label("?", skin);
            iconLbl.setColor(Color.BLACK);
            iconLbl.setFontScale(2f);
            iconBox.add(iconLbl).center();
        }
        
        card.add(iconBox).size(90, 90).pad(10);
        
        Table infoBox = new Table();
        infoBox.left();
        
        Label nomeLbl = new Label(nome, skin);
        nomeLbl.setWrap(true);
        nomeLbl.setAlignment(Align.left);
        nomeLbl.setColor(disponivel ? Color.WHITE : Color.DARK_GRAY);
        
        String precoStr = disponivel ? formatarBerries(precoBase) : "--- B";
        Label precoLbl = new Label(precoStr, skin);
        precoLbl.setColor(disponivel ? Color.GOLD : Color.DARK_GRAY);
        precoLbl.setFontScale(1.1f);
        
        infoBox.add(nomeLbl).width(280).left().row();
        
        Label descLbl = new Label(descricao, skin);
        descLbl.setWrap(true);
        descLbl.setAlignment(Align.left);
        descLbl.setColor(Color.LIGHT_GRAY);
        descLbl.setFontScale(0.8f);
        infoBox.add(descLbl).width(280).left().padTop(5).row();
        
        infoBox.add(precoLbl).padTop(10).left();
        
        card.add(infoBox).expand().fill().pad(10);
        
        if (disponivel) {
            card.setTouchable(com.badlogic.gdx.scenes.scene2d.Touchable.enabled);
            card.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (acao != null) {
                        acao.accept(card);
                    }
                }
            });
        }
        
        return card;
    }

    private void atualizarOuro() {
        ouroLbl.setText("Carteira: " + formatarBerries(gameManager.getOuro()));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
