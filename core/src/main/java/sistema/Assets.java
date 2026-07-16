package sistema;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Carregador lazy de texturas com fallback automatico.
 *
 * Uso:
 *   Texture bg  = assets.getTextura("backgrounds/arlong_park.png");
 *   Texture img = assets.getTextura("inimigos/bosses/buggy.png");
 *
 * Se o arquivo nao existir na pasta assets/, retorna uma textura
 * colorida de placeholder (cinza escuro) para o jogo nao travar.
 *
 * Deve ser instanciado APOS LibGDX.create() (contexto OpenGL ativo).
 * Chamar dispose() ao encerrar o jogo para liberar as texturas.
 */
public class Assets {

    private final Map<String, Texture> cache      = new HashMap<>();
    private       Texture              placeholder = null;

    /**
     * Inicializa o sistema de assets (cria placeholder).
     * Chamado em JogoPiratas.create().
     */
    public void inicializar() {
        Pixmap px = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        px.setColor(0.25f, 0.25f, 0.30f, 0.85f);
        px.fill();
        // Borda escura para indicar visualmente que falta o asset
        px.setColor(0.5f, 0.5f, 0.6f, 1f);
        px.drawRectangle(0, 0, 63, 63);
        placeholder = new Texture(px);
        px.dispose();
    }

    /**
     * Retorna a textura para o caminho dado (relativo A  pasta assets/).
     * Carrega na primeira chamada e armazena em cache.
     * Se o arquivo nao existir, retorna o placeholder.
     */
    public Texture getTextura(String caminho) {
        if (caminho == null || caminho.isEmpty()) return placeholder;
        if (cache.containsKey(caminho)) return cache.get(caminho);

        try {
            if (Gdx.files.internal(caminho).exists()) {
                Texture t = new Texture(Gdx.files.internal(caminho));
                cache.put(caminho, t);
                return t;
            }
        } catch (Exception e) {
            Gdx.app.log("Assets", "Falha ao carregar: " + caminho + " - " + e.getMessage());
        }

        // Salva o placeholder no cache para evitar tentativas repetidas
        cache.put(caminho, placeholder);
        return placeholder;
    }

    /**
     * Atalho para textura de Luffy - tenta o caminho novo e cai de volta
     * para o arquivo legado na raiz de assets/.
     */
    public Texture getLuffy() {
        Texture t = getTextura("personagens/chapeu_de_palha/luffy.png");
        if (t == placeholder) {
            t = getTextura("luffy.png"); // arquivo ja existente antes da refatoracao
        }
        return t;
    }

    /** Libera todas as texturas carregadas. Chamar em JogoPiratas.dispose(). */
    public void dispose() {
        for (Map.Entry<String, Texture> entry : cache.entrySet()) {
            if (entry.getValue() != placeholder) {
                entry.getValue().dispose();
            }
        }
        cache.clear();
        if (placeholder != null) {
            placeholder.dispose();
            placeholder = null;
        }
    }

    public Texture getPlaceholder() { return placeholder; }
}
