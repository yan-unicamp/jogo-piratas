package frontend;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import entidades.Aliado;
import progressao.Loja;
import progressao.Loja.ItemLoja;
import sistema.EstadoJogo;
import sistema.GameManager;
import sistema.JogoPiratas;
import sistema.Tripulacao;

/**
 * Interface gráfica da Loja.
 *
 * Layout:
 *  ┌─────────────────────────────────────────────┐
 *  │  LOJA   [Ouro: 999] [Alvo: Capitão ▼]  [Sair]│
 *  ├─────────────────────────────────────────────┤
 *  │  [Item A]  Descrição...               Preço  │
 *  │  [Item B]  Descrição...               Preço  │
 *  │  ...  (ScrollPane)                           │
 *  └─────────────────────────────────────────────┘
 *
 * Ao clicar num item o botão chama:
 *   loja.comprarMelhoria(tripulacao, item, alvoSelecionado)
 */
public class TelaLoja implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    private Stage stage;
    private Skin  skin;

    // Aliado selecionado como alvo para itens individuais
    private Aliado alvoSelecionado;

    // Label de ouro atualizado dinamicamente
    private Label labelOuro;

    public TelaLoja(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin  = SkinPadrao.criar();
        adicionarEstiloSelectBox(skin);
        adicionarEstiloScrollPane(skin);

        Tripulacao tripulacao = gameManager.getTripulacao();
        Loja loja = gameManager.getLoja();

        // Pré-seleciona o primeiro aliado vivo como alvo padrão
        List<Aliado> aliadosVivos = tripulacao.getAliadosVivos();
        alvoSelecionado = aliadosVivos.isEmpty() ? null : aliadosVivos.get(0);

        // ── Raiz ──
        Table raiz = new Table();
        raiz.setFillParent(true);
        raiz.top();
        raiz.pad(20);

        // ── Cabeçalho ──
        Table cabecalho = montarCabecalho(tripulacao, aliadosVivos);
        raiz.add(cabecalho).fillX().expandX().padBottom(20).row();

        // ── Catálogo em ScrollPane ──
        Table listaCatalogo = montarCatalogo(loja, tripulacao);
        ScrollPane scroll = new ScrollPane(listaCatalogo, skin, "loja");
        scroll.setFadeScrollBars(false);
        scroll.setScrollingDisabled(true, false);
        raiz.add(scroll).expand().fill().row();

        stage.addActor(raiz);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Construção do cabeçalho
    // ──────────────────────────────────────────────────────────────────────────

    private Table montarCabecalho(Tripulacao tripulacao, List<Aliado> aliadosVivos) {
        Table cab = new Table();

        // Título
        Label titulo = new Label("Loja de Itens", skin);
        titulo.setFontScale(1.8f);
        titulo.setColor(new Color(1f, 0.82f, 0.18f, 1f)); // dourado

        // Ouro
        labelOuro = new Label("Ouro: " + tripulacao.getDinheiro(), skin);
        labelOuro.setColor(new Color(1f, 0.9f, 0.3f, 1f));

        // SelectBox de alvo
        Label labelAlvo = new Label("Alvo: ", skin);
        labelAlvo.setColor(Color.LIGHT_GRAY);

        SelectBox<String> selectAlvo = new SelectBox<>(skin, "default");
        Array<String> nomes = new Array<>();
        for (Aliado a : aliadosVivos) {
            nomes.add(a.getNome());
        }
        if (nomes.isEmpty()) nomes.add("(nenhum)");
        selectAlvo.setItems(nomes);

        selectAlvo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int idx = selectAlvo.getSelectedIndex();
                alvoSelecionado = (idx >= 0 && idx < aliadosVivos.size())
                        ? aliadosVivos.get(idx)
                        : null;
            }
        });

        // Botão sair
        TextButton btnSair = new TextButton("  Sair da Loja  ", skin);
        btnSair.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.mudarEstado(EstadoJogo.MAPA);
            }
        });

        cab.add(titulo).left().expandX();
        cab.add(labelOuro).padRight(20);
        cab.add(labelAlvo);
        cab.add(selectAlvo).width(150).padRight(20);
        cab.add(btnSair).width(160).height(45);
        return cab;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Construção do catálogo
    // ──────────────────────────────────────────────────────────────────────────

    private Table montarCatalogo(Loja loja, Tripulacao tripulacao) {
        Table lista = new Table();
        lista.top().left();
        lista.pad(0, 0, 20, 0);

        for (ItemLoja item : loja.getCatalogo()) {
            Table linha = criarLinhaItem(item, loja, tripulacao);
            lista.add(linha).fillX().expandX().padBottom(8).row();
        }
        return lista;
    }

    private Table criarLinhaItem(ItemLoja item, Loja loja, Tripulacao tripulacao) {
        Table linha = new Table();
        linha.setBackground(new TextureRegionDrawable(texturaCor(0.07f, 0.04f, 0.01f, 0.90f)));
        linha.pad(10, 16, 10, 16);

        // Nome
        Label nomeLabel = new Label(formatarNome(item.name()), skin);
        nomeLabel.setColor(Color.WHITE);

        // Descrição
        Label descLabel = new Label(item.getDescricao(), skin);
        descLabel.setColor(Color.LIGHT_GRAY);
        descLabel.setWrap(true);

        // Preço
        Label precoLabel = new Label(item.getPreco() + " ouro", skin);
        precoLabel.setColor(new Color(1f, 0.85f, 0.2f, 1f));
        precoLabel.setAlignment(Align.right);

        // Botão comprar
        TextButton btnComprar = new TextButton(" Comprar ", skin);
        btnComprar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean ok = loja.comprarMelhoria(tripulacao, item, alvoSelecionado);
                if (ok) {
                    labelOuro.setText("Ouro: " + tripulacao.getDinheiro());
                    mostrarFeedback(btnComprar, "✓ Comprado!", new Color(0.3f, 1f, 0.3f, 1f));
                } else {
                    mostrarFeedback(btnComprar, "✗ Sem ouro", Color.RED);
                }
            }
        });

        linha.add(nomeLabel).left().width(180).padRight(12);
        linha.add(descLabel).left().expandX().padRight(12);
        linha.add(precoLabel).right().width(90).padRight(12);
        linha.add(btnComprar).width(120).height(40);
        return linha;
    }

    /** Formata "RUMBLE_BALL" → "Rumble Ball" para exibição. */
    private String formatarNome(String enumName) {
        String[] partes = enumName.split("_");
        StringBuilder sb = new StringBuilder();
        for (String p : partes) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(p.charAt(0)).append(p.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    /** Altera o texto e cor do botão brevemente como feedback visual. */
    private void mostrarFeedback(TextButton btn, String texto, Color cor) {
        btn.setText(texto);
        btn.getLabel().setColor(cor);
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Estilos adicionais ao skin
    // ──────────────────────────────────────────────────────────────────────────

    private void adicionarEstiloSelectBox(Skin s) {
        BitmapFont font = s.get("default-font", BitmapFont.class);

        SelectBox.SelectBoxStyle style = new SelectBox.SelectBoxStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        style.background = new TextureRegionDrawable(texturaCor(0.15f, 0.15f, 0.40f, 1f));
        style.backgroundOpen = new TextureRegionDrawable(texturaCor(0.25f, 0.25f, 0.60f, 1f));

        com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle listStyle =
                new com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle();
        listStyle.font = font;
        listStyle.fontColorSelected = Color.WHITE;
        listStyle.fontColorUnselected = Color.LIGHT_GRAY;
        listStyle.selection = new TextureRegionDrawable(texturaCor(0.20f, 0.20f, 0.55f, 1f));
        listStyle.background = new TextureRegionDrawable(texturaCor(0.10f, 0.10f, 0.30f, 1f));
        style.listStyle = listStyle;

        ScrollPane.ScrollPaneStyle scrollStyle = new ScrollPane.ScrollPaneStyle();
        style.scrollStyle = scrollStyle;

        s.add("default", style);
    }

    private void adicionarEstiloScrollPane(Skin s) {
        ScrollPane.ScrollPaneStyle sp = new ScrollPane.ScrollPaneStyle();
        sp.vScrollKnob = new TextureRegionDrawable(texturaCor(0.4f, 0.4f, 0.7f, 1f));
        s.add("loja", sp);
    }

    private static Texture texturaCor(float r, float g, float b, float a) {
        Pixmap px = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        px.setColor(r, g, b, a);
        px.fill();
        Texture tex = new Texture(px);
        px.dispose();
        return tex;
    }

    // ──────────────────────────────────────────────────────────────────────────

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.03f, 0f, 1f); // marrom-escuro náutico
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

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
