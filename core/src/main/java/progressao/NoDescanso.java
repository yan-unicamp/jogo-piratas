package progressao;

import sistema.EstadoJogo;
import sistema.GameManager;

/**
 * Nó de Descanso.
 * Quando o jogador navega até este nó, o GameManager troca para a TelaDescanso,
 * onde a tripulação pode recuperar HP antes de prosseguir.
 */
public class NoDescanso implements NoMapa {

    @Override
    public void entrarNo(GameManager controle) {
        controle.mudarEstado(EstadoJogo.DESCANSO);
    }
}
