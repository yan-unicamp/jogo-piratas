package sistema;

import progressao.Mapa;
import progressao.NoMapa;
import progressao.NoBatalha;
import progressao.NoDescanso;
import progressao.NoEvento;
import entidades.Personagem;
import factories.PersonagemFactory;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * Gerenciador principal do jogo.
 * Contém a lógica de fluxo do jogo (estados, transições).
 */
public class GameManager {
    private Mapa mapa;
    private Tripulacao tripulacao;
    private GerenciadorDeBatalha gerenciadorDeBatalha;
    private boolean jogoRodando;
    private Scanner scanner;

    public GameManager() {
        this.mapa = new Mapa();
        this.tripulacao = new Tripulacao();
        this.gerenciadorDeBatalha = new GerenciadorDeBatalha();
        this.jogoRodando = false;
        this.scanner = new Scanner(System.in);
        System.out.println("GameManager inicializado.");
    }

    public void iniciarJogo() { 
        System.out.println("Iniciando o jogo...");
        this.jogoRodando = true;
        
        // Exemplo: adicionando um aliado base
        tripulacao.adicionarAliado(PersonagemFactory.criarLuffy());
        tripulacao.adicionarAliadoAtivo(tripulacao.getAliados().get(0));

        loopPrincipal();
    }

    public void loopPrincipal() { 
        while (jogoRodando) {
            if (mapa.getCapitulo() > 3) {
                acharOnePiece();
                break;
            }

            System.out.println("\n--- MAPA: Capítulo " + mapa.getCapitulo() + " - Etapa " + mapa.getEtapaAtual() + " ---");
            System.out.println("Para qual Nó você quer ir?");
            System.out.println("1. Nó de Batalha");
            System.out.println("2. Nó de Evento");
            System.out.println("3. Nó de Descanso");
            System.out.println("0. Sair do Jogo");
            System.out.print("Escolha: ");
            
            int escolha = -1;
            if (scanner.hasNextInt()) {
                escolha = scanner.nextInt();
            } else {
                scanner.next(); // Limpar buffer inválido
            }
            
            if (escolha == 0) {
                this.jogoRodando = false;
                break;
            }
            
            NoMapa proximoNo = null;
            if (escolha == 1) {
                proximoNo = new NoBatalha();
            } else if (escolha == 2) {
                proximoNo = new NoEvento();
            } else if (escolha == 3) {
                proximoNo = new NoDescanso();
            } else {
                System.out.println("Opção inválida!");
                continue;
            }
            
            mapa.avancarParaNo(proximoNo);
            
            if (proximoNo instanceof NoBatalha) {
                System.out.println("\n[!] ENTRANDO EM COMBATE [!]");
                ArrayList<Personagem> aliados = new ArrayList<>(tripulacao.getAliadosAtivos());
                ArrayList<Personagem> inimigos = new ArrayList<>();
                inimigos.add(PersonagemFactory.criarMarinheiro(mapa.getEtapaAtual()));
                
                gerenciadorDeBatalha.iniciarCombate(aliados, inimigos, tripulacao);
            } else {
                proximoNo.entrarNo(this);
            }
        }
        encerrarJogo();
    }

    public void encerrarJogo() { 
        System.out.println("Jogo encerrado. Obrigado por jogar!");
        this.jogoRodando = false;
        if (scanner != null) scanner.close();
    }

    private void acharOnePiece() {
        System.out.println("\n=============================================");
        System.out.println("PARABÉNS! VOCÊ ACHOU O ONE PIECE!");
        System.out.println("Você concluiu sua jornada e se tornou o Rei dos Piratas!");
        System.out.println("=============================================\n");
        this.jogoRodando = false;
    }

    public Tripulacao getTripulacao() { return tripulacao; }
    public Mapa getMapa() { return mapa; }
}
