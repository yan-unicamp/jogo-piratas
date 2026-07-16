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
    private boolean jogoRodando;

    public GameManager() {
        this.mapa = new Mapa();
        this.tripulacao = new Tripulacao();
        this.gerenciadorDeBatalha = new GerenciadorDeBatalha();
        this.jogoRodando = false;
        System.out.println("GameManager inicializado.");
    }

    public void iniciarJogo() { 
        System.out.println("Iniciando o jogo...");
        this.jogoRodando = true;
        loopPrincipal();
    }

    public void loopPrincipal() { 
        while (jogoRodando) {
            // TODO: Lógica principal (ex: ler input do usuário, navegar no mapa, gerenciar batalhas, etc)
            // Aqui você conectará a progressão no mapa com a entrada em combates ou eventos.
            
            // Evitando loop infinito no esqueleto base
            this.jogoRodando = false;
        }
        encerrarJogo();
    }

    public void encerrarJogo() { 
        System.out.println("Jogo encerrado. Obrigado por jogar!");
        this.jogoRodando = false;
    }
}
