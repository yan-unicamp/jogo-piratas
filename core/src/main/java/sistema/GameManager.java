package sistema;

import progressao.Mapa;

/**
 * Gerenciador principal do jogo.
 * Contém a lógica de fluxo do jogo (estados, transições).
 */
public class GameManager {
    private Mapa mapa;
    private Tripulacao tripulacao;
    private GerenciadorDeBatalha gerenciadorDeBatalha;

    public GameManager() {
        this.mapa = new Mapa();
        this.tripulacao = new Tripulacao();
        this.gerenciadorDeBatalha = new GerenciadorDeBatalha();
        System.out.println("GameManager inicializado.");
    }

    public void iniciarJogo() { }

    public void loopPrincipal() { }

    public void encerrarJogo() { }
}

