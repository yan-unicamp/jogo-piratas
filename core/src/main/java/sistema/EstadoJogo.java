package sistema;

/**
 * Enum que representa os estados possiveis do jogo.
 *
 * O GameManager usa este enum como maquina de estados para controlar
 * qual Screen do LibGDX esta ativa em cada momento.
 *
 * Fluxo tipico:
 * MENU -> MAPA -> BATALHA -> MAPA -> LOJA -> MAPA -> ... -> GAME_OVER
 * a†˜ DESCANSO a†—
 */
public enum EstadoJogo {
    MENU,
    MAPA,
    BATALHA,
    LOJA,
    DESCANSO,
    GAME_OVER
}
