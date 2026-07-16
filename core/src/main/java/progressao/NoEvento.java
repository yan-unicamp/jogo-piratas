package progressao;

import sistema.EstadoJogo;
import sistema.GameManager;

/**
 * No de Evento.
 * Quando o jogador navega ate este no, o GameManager troca para a TelaLoja
 * (evento de comercio/encontro). Pode ser expandido futuramente para eventos
 * aleatorios distintos (encontro amigavel, item gratuito, etc.).
 */
public class NoEvento implements NoMapa {

    @Override
    public void entrarNo(GameManager controle) {
        controle.mudarEstado(EstadoJogo.LOJA);
    }
}
