package frontend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Fabrica de Skin minimo para prototipo visual (sem arquivos externos).
 *
 * Gera botoes e labels funcionais usando cores solidas programaticamente.
 * Na Fase 5, substituir por um Skin real com atlas e JSON estilizado.
 *
 * USO: Skin skin = SkinPadrao.criar();  // lembrar de skin.dispose() no dispose()
 */
public class SkinPadrao {

    private SkinPadrao() { /* utilitario estatico */ }

    /** Cria e retorna um Skin pronto para uso. O chamador e responsavel por dispose(). */
    public static Skin criar() {
        Skin skin = new Skin();

        BitmapFont font = new BitmapFont();
        skin.add("default-font", font);

        // Paleta de cores do prototipo
        skin.add("tex-btn-normal",  textura(0.15f, 0.15f, 0.40f, 1f));
        skin.add("tex-btn-hover",   textura(0.25f, 0.25f, 0.65f, 1f));
        skin.add("tex-btn-pressed", textura(0.08f, 0.08f, 0.28f, 1f));

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font      = font;
        btnStyle.fontColor = Color.WHITE;
        btnStyle.up   = new TextureRegionDrawable(skin.get("tex-btn-normal",  Texture.class));
        btnStyle.over = new TextureRegionDrawable(skin.get("tex-btn-hover",   Texture.class));
        btnStyle.down = new TextureRegionDrawable(skin.get("tex-btn-pressed", Texture.class));
        skin.add("default", btnStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font      = font;
        labelStyle.fontColor = Color.WHITE;
        skin.add("default", labelStyle);

        com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle scrollStyle = new com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle();
        skin.add("default", scrollStyle);

        return skin;
    }

    /**
     * Cria uma Texture 1A—1 de cor solida.
     * Alias publico para uso por outras classes (ex: TelaBatalha).
     */
    public static Texture textura1x1(float r, float g, float b, float a) {
        return textura(r, g, b, a);
    }

    /** Cria uma Texture 1A—1 de cor solida (liberada pelo Skin no dispose()). */
    private static Texture textura(float r, float g, float b, float a) {
        Pixmap px = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        px.setColor(r, g, b, a);
        px.fill();
        Texture tex = new Texture(px);
        px.dispose();
        return tex;
    }
}
