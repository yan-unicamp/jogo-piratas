package frontend;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import sistema.EstadoJogo;
import sistema.GameManager;
import sistema.JogoPiratas;

/**
 * Interface gráfica da Batalha por Turnos.
 *
 * Usa o GameManager para:
 *   - gameManager.getTripulacao()            → aliados, HP, habilidades
 *   - gameManager.getGerenciadorDeBatalha()  → estado do combate, turno atual
 *   - gameManager.mudarEstado(MAPA)          → ao vencer
 *   - gameManager.mudarEstado(GAME_OVER)     → ao perder (tripulacao.todosMortos())
 *
 * TODO Fase 5: Implementar sprites de personagens, barras de vida (ProgressBar),
 *              botões de habilidades (TextButton), log de combate (ScrollPane)
 *              e indicador de turno.
 */
public class TelaBatalha implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    public TelaBatalha(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        // TODO F5: Criar Stage e montar UI de batalha:
        //   - Sprites de aliados e inimigos
        //   - ProgressBar para cada personagem conectada em gameManager.getTripulacao()
        //   - Botões de habilidade (um por Habilidade do aliado ativo)
        //   - Label do turno atual
        //
        // Ao clicar num botão de habilidade:
        //   gerenciador.executarTurno() → atualizar barras → verificarVitoriaOuDerrota()
        //   Se venceu: gameManager.mudarEstado(EstadoJogo.MAPA)
        //   Se perdeu: gameManager.mudarEstado(EstadoJogo.GAME_OVER)
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0.05f, 0.1f, 1); // azul-escuro oceânico
        // TODO F5: stage.act(delta); stage.draw();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        // TODO F5: stage.dispose(); skin.dispose(); texturas.dispose();
    }
}
