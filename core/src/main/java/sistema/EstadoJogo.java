package sistema;

/**
 * Enum que representa os estados possíveis do jogo.
 *
 * O GameManager usa este enum como máquina de estados para controlar
 * qual Screen do LibGDX está ativa em cada momento.
 *
 * Fluxo típico:
 *   MENU → MAPA → BATALHA → MAPA → LOJA → MAPA → ... → GAME_OVER
 *                         ↘ DESCANSO ↗
 */
public enum EstadoJogo {
    MENU,
    MAPA,
    BATALHA,
    LOJA,
    DESCANSO,
    GAME_OVER
}
