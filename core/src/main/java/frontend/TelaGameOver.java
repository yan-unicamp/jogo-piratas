package frontend;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import sistema.EstadoJogo;
import sistema.GameManager;
import sistema.JogoPiratas;

/**
 * Interface gráfica de Game Over.
 *
 * Exibida quando gameManager.getTripulacao().todosMortos() retorna true.
 *
 * Usa o GameManager para:
 *   - gameManager.getMapa().getEtapaAtual()     → exibir até onde o jogador chegou
 *   - gameManager.mudarEstado(EstadoJogo.MENU)  → botão "Menu Principal"
 *
 * TODO Fase 5: Exibir animação de derrota, etapa alcançada, ouro coletado.
 *              TextButton "Tentar Novamente" → gameManager.mudarEstado(MENU)
 *              (O MENU recria o GameManager e reinicia tudo do zero.)
 */
public class TelaGameOver implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    public TelaGameOver(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        int etapaAlcancada = gameManager.getMapa().getEtapaAtual();

        // TODO F5: Exibir "Game Over", etapaAlcancada e botão de retry
        // Botão "Menu Principal": gameManager.mudarEstado(EstadoJogo.MENU)
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0f, 0f, 1); // vermelho-escuro dramático
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
