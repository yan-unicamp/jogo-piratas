package progressao;

import java.util.List;

import entidades.Inimigo;
import sistema.EstadoJogo;
import sistema.GameManager;

/**
 * Nó de Batalha.
 *
 * Carrega a lista de inimigos que o jogador enfrentará.
 * Quando o jogador navega até este nó:
 *  1. Injeta os inimigos no GameManager via prepararBatalha()
 *  2. Transita para o estado BATALHA → abre TelaBatalha
 *
 * Criação e população dos inimigos: Membro 3, dentro de Mapa.gerarProximosNos().
 * Exemplo:
 *   List<Inimigo> grupo = List.of(
 *       new Inimigo("Corsário", 60, 5, 10, new Recompensa(80, 30))
 *   );
 *   proximosNos.add(new NoBatalha(grupo));
 */
public class NoBatalha implements NoMapa {

    private final List<Inimigo> inimigos;

    /** Cria um nó de batalha com o grupo de inimigos que o aguarda. */
    public NoBatalha(List<Inimigo> inimigos) {
        this.inimigos = inimigos;
    }

    @Override
    public void entrarNo(GameManager controle) {
        controle.prepararBatalha(inimigos); // injeta contexto antes de trocar de tela
        controle.mudarEstado(EstadoJogo.BATALHA);
    }

    public List<Inimigo> getInimigos() { return inimigos; }
}
