package frontend;

import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import progressao.Mapa;
import progressao.NoMapa;
import sistema.EstadoJogo;
import sistema.GameManager;
import sistema.JogoPiratas;

/**
 * Interface gráfica do Mapa de Navegação.
 *
 * Usa o GameManager para:
 *   - gameManager.getMapa().getProximosNos()           → nós clicáveis
 *   - gameManager.getMapa().avancarParaNo(no, gm)      → navegar para um nó
 *   - gameManager.getMapa().getEtapaAtual()            → exibir progresso
 *   - gameManager.getTripulacao()                       → exibir status do grupo
 *
 * Ao clicar em um nó, avancarParaNo() chama no.entrarNo(gameManager),
 * que dispara automaticamente a transição de estado correta:
 *   NoBatalha  → mudarEstado(BATALHA)
 *   NoDescanso → mudarEstado(DESCANSO)
 *   NoEvento   → mudarEstado(LOJA)
 *
 * TODO Fase 5: Desenhar nós como ícones/botões conectados por linhas.
 *              Indicar nó atual vs nós disponíveis visualmente.
 *              Mostrar mini-status da tripulação (HP total).
 */
public class TelaMapa implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    public TelaMapa(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        Mapa mapa = gameManager.getMapa();
        List<NoMapa> proximosNos = mapa.getProximosNos();
        int etapa = mapa.getEtapaAtual();

        // TODO F5: Criar Stage e renderizar os nós como botões clicáveis
        // Para cada no em proximosNos:
        //   TextButton btnNo → mapa.avancarParaNo(no, gameManager)
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0.08f, 0.05f, 1); // verde-escuro oceânico
        // TODO F5: stage.act(delta); stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        // TODO F5: stage.dispose(); skin.dispose();
    }
}
