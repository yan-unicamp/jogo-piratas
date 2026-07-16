package progressao;

import java.util.List;

import entidades.Inimigo;
import sistema.EstadoJogo;
import sistema.GameManager;

/**
 * No de Batalha.
 *
 * Carrega a lista de inimigos que o jogador enfrentara.
 * Quando o jogador navega ate este no:
 *  1. Injeta os inimigos no GameManager via prepararBatalha()
 *  2. Transita para o estado BATALHA -> abre TelaBatalha
 *
 * Criacao e populacao dos inimigos: Membro 3, dentro de Mapa.gerarProximosNos().
 * Exemplo:
 *   List<Inimigo> grupo = List.of(
 *       new Inimigo("Corsario", 60, 5, 10, new Recompensa(80, 30))
 *   );
 *   proximosNos.add(new NoBatalha(grupo));
 */
public class NoBatalha implements NoMapa {

    private final List<Inimigo> inimigos;

    /** Cria um no de batalha com o grupo de inimigos que o aguarda. */
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
