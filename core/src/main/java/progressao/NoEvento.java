package progressao;

import sistema.EstadoJogo;
import sistema.GameManager;

/**
 * Nó de Evento.
 * Quando o jogador navega até este nó, o GameManager troca para a TelaLoja
 * (evento de comércio/encontro). Pode ser expandido futuramente para eventos
 * aleatórios distintos (encontro amigável, item gratuito, etc.).
 */
public class NoEvento implements NoMapa {

    @Override
    public void entrarNo(GameManager controle) {
        controle.mudarEstado(EstadoJogo.LOJA);
    }
}
