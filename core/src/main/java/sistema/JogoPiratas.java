package sistema;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import frontend.TelaMenu;
import frontend.TelaBatalha;
import entidades.Personagem;
import entidades.ConfiguracaoBatalha;
import factories.PersonagemFactory;
import java.util.ArrayList;

/**
 * Classe principal do jogo que estende Game do libGDX.
 * Gerencia as telas e recursos globais.
 */
public class JogoPiratas extends Game {

    /** SpriteBatch compartilhado entre todas as telas (evita criar varios) */
    public SpriteBatch batch;

    /** Fonte padrao compartilhada */
    public BitmapFont font;
    
    /** Gerenciador de Assets (texturas) */
    public Assets assets;

    /** Gerenciador de Audio */
    public AudioManager audio;

    /** Gerenciador do estado global do jogo (backend) */
    public GameManager gameManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // fonte padrao do libGDX
        font.setColor(Color.WHITE);
        
        // Filtro linear para a fonte nao ficar pixelada ao esticar
        font.getRegion().getTexture().setFilter(com.badlogic.gdx.graphics.Texture.TextureFilter.Linear, com.badlogic.gdx.graphics.Texture.TextureFilter.Linear);
        font.getData().setScale(1.6f);
        
        // Espacar mais as letras (aumenta a distancia horizontal entre elas)
        for (com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph[] page : font.getData().glyphs) {
            if (page != null) {
                for (com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph glyph : page) {
                    if (glyph != null) {
                        glyph.xadvance += 2;
                    }
                }
            }
        }

        gameManager = new GameManager();

        assets = new Assets();
        assets.inicializar();

        audio = new AudioManager();

        // Inicia com a tela inicial
        setScreen(new frontend.TelaInicio(this));
    }

    @Override
    public void render() {
        // Game.render() delega para a Screen ativa automaticamente
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();

        // Dispor a tela atual se existir
        Screen currentScreen = getScreen();
        if (currentScreen != null) {
            currentScreen.dispose();
        }
        
        if (audio != null) {
            audio.dispose();
        }
    }
}
