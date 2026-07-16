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

    /** SpriteBatch compartilhado entre todas as telas (evita criar vários) */
    public SpriteBatch batch;

    /** Fonte padrão compartilhada */
    public BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // fonte padrão do libGDX
        font.setColor(Color.WHITE);
        
        // Filtro linear para a fonte não ficar pixelada ao esticar
        font.getRegion().getTexture().setFilter(com.badlogic.gdx.graphics.Texture.TextureFilter.Linear, com.badlogic.gdx.graphics.Texture.TextureFilter.Linear);
        font.getData().setScale(1.4f);
        
        // Espaçar mais as letras (aumenta a distância horizontal entre elas)
        for (com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph[] page : font.getData().glyphs) {
            if (page != null) {
                for (com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph glyph : page) {
                    if (glyph != null) {
                        glyph.xadvance += 2;
                    }
                }
            }
        }

        // Configuração mock de batalha
        ArrayList<Personagem> aliados = new ArrayList<>();
        aliados.add(PersonagemFactory.criarLuffy());
        aliados.add(PersonagemFactory.criarZoro());
        aliados.add(PersonagemFactory.criarChopper());

        ArrayList<Personagem> inimigos = new ArrayList<>();
        inimigos.add(PersonagemFactory.criarMarinheiro(1));
        inimigos.add(PersonagemFactory.criarPirataInimigo(1));

        ConfiguracaoBatalha config = new ConfiguracaoBatalha(
            "ARCO DE ALABASTA", 
            "backgrounds/alabasta.png", 
            aliados, 
            inimigos
        );

        // Inicia com a tela de batalha para testes
        setScreen(new TelaBatalha(this, config));
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
    }
}
