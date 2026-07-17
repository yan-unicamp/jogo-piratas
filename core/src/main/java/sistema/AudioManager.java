package sistema;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Gerenciador de audio do jogo.
 * Carrega musicas lazy e toca sem sobrepor.
 * Se o arquivo de audio nao existir, ele ignora sem travar o jogo.
 */
public class AudioManager {
    private final Map<String, Music> musicas = new HashMap<>();
    private Music musicaAtual;
    private String caminhoAtual;

    // Nomes padroes sugeridos (O usuario deve colocar os mp3 na pasta assets/audio)
    public static final String MUSICA_MENU = "audio/SakeBinks.mp3";
    public static final String MUSICA_MAPA = "audio/SakeBinks.mp3";
    public static final String MUSICA_BATALHA = "audio/Overtaken.mp3";
    public static final String MUSICA_LOJA = "audio/loja.mp3";
    public static final String MUSICA_DESCANSO = "audio/descanso.mp3";

    public AudioManager() {
    }

    private Music getMusica(String caminho) {
        if (caminho == null || caminho.isEmpty())
            return null;
        if (musicas.containsKey(caminho))
            return musicas.get(caminho);

        try {
            if (Gdx.files.internal(caminho).exists()) {
                Music m = Gdx.audio.newMusic(Gdx.files.internal(caminho));
                musicas.put(caminho, m);
                return m;
            }
        } catch (Exception e) {
            Gdx.app.log("AudioManager", "Aviso: Nao foi possivel carregar o arquivo de audio: " + caminho
                    + " - Coloque um arquivo de musica valido nesta pasta para ouvir a trilha!");
        }

        musicas.put(caminho, null); // Marca como null para nao tentar carregar de novo
        return null;
    }

    public void tocar(String caminho, boolean loop) {
        // Se ja esta tocando essa mesma musica, nao recomeca
        if (caminhoAtual != null && caminhoAtual.equals(caminho) && musicaAtual != null && musicaAtual.isPlaying()) {
            return;
        }

        pararAtual();

        Music m = getMusica(caminho);
        if (m != null) {
            m.setLooping(loop);
            m.setVolume(0.1f);
            m.play();
            musicaAtual = m;
            caminhoAtual = caminho;
        }
    }

    public void pararAtual() {
        if (musicaAtual != null && musicaAtual.isPlaying()) {
            musicaAtual.stop();
        }
        musicaAtual = null;
        caminhoAtual = null;
    }

    public void dispose() {
        pararAtual();
        for (Music m : musicas.values()) {
            if (m != null)
                m.dispose();
        }
        musicas.clear();
    }
}
