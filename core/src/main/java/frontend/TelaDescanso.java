package frontend;

import java.util.List;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import entidades.Aliado;
import sistema.EstadoJogo;
import sistema.GameManager;
import sistema.JogoPiratas;

/**
 * Interface gráfica do Nó de Descanso.
 *
 * Neste nó o jogador pode recuperar HP dos aliados antes de prosseguir.
 *
 * Usa o GameManager para:
 *   - gameManager.getTripulacao().getAliadosVivos()    → aliados que podem curar
 *   - aliado.curar(valor)                               → aplicar cura (Membro 1)
 *   - gameManager.mudarEstado(EstadoJogo.MAPA)         → continuar viagem
 *
 * TODO Fase 5: Exibir lista de aliados com HP atual/máximo.
 *              Botão "Descansar" cura um valor fixo (ex: 30% do HP máximo).
 *              Botão "Continuar" avança para o mapa sem curar.
 */
public class TelaDescanso implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    public TelaDescanso(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        List<Aliado> aliadosVivos = gameManager.getTripulacao().getAliadosVivos();

        // TODO F5: Exibir cards de cada aliado com barra de HP
        // Botão "Descansar":
        //   for aliado in aliadosVivos: aliado.curar((int)(aliado.getVidaMaxima() * 0.3f))
        //   gameManager.mudarEstado(EstadoJogo.MAPA)
        // Botão "Continuar sem descansar":
        //   gameManager.mudarEstado(EstadoJogo.MAPA)
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.05f, 0f, 1); // sépia quente de acampamento
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
