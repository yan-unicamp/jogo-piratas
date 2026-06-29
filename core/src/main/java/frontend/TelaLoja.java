package frontend;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import progressao.Loja.ItemLoja;
import sistema.EstadoJogo;
import sistema.GameManager;
import sistema.JogoPiratas;

/**
 * Interface gráfica da Loja.
 *
 * Usa o GameManager para:
 *   - gameManager.getLoja().getCatalogo()         → listar itens disponíveis
 *   - gameManager.getLoja().comprarMelhoria(...)   → confirmar compra
 *   - gameManager.getTripulacao()                  → exibir saldo de ouro
 *   - gameManager.mudarEstado(EstadoJogo.MAPA)    → botão "Sair da Loja"
 *
 * TODO Fase 5: Montar grid de itens com ScrollPane, cada item como TextButton
 *              com nome, descrição e preço. Exibir saldo no topo.
 *              Ao clicar: selecionar aliado alvo (se necessário) e chamar comprarMelhoria.
 */
public class TelaLoja implements Screen {

    private final JogoPiratas jogo;
    private final GameManager gameManager;

    public TelaLoja(JogoPiratas jogo, GameManager gameManager) {
        this.jogo = jogo;
        this.gameManager = gameManager;
    }

    @Override
    public void show() {
        // Dados já disponíveis para construir a UI:
        ItemLoja[] catalogo = gameManager.getLoja().getCatalogo();
        int ouroAtual = gameManager.getTripulacao().getDinheiro();

        // TODO F5: Criar Stage e montar UI da loja com catalogo e ouroAtual
        // Botão de compra: gameManager.getLoja().comprarMelhoria(tripulacao, item, alvoSelecionado)
        // Botão "Sair":    gameManager.mudarEstado(EstadoJogo.MAPA)
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.05f, 0.03f, 0f, 1); // marrom-escuro náutico
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
