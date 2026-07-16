package sistema;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;
import entidades.TipoHabilidade;
import progressao.Recompensa;
import factories.PersonagemFactory;
import java.util.ArrayList;
import java.util.Scanner;

public class TesteBatalhaTerminal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Criar dados usando a Factory
        Aliado heroi = PersonagemFactory.criarLuffy();
        Inimigo esqueleto = PersonagemFactory.criarMarinheiro(1);

        ArrayList<Personagem> aliados = new ArrayList<>();
        aliados.add(heroi);

        ArrayList<Personagem> inimigos = new ArrayList<>();
        inimigos.add(esqueleto);

        Tripulacao tripulacao = new Tripulacao();
        tripulacao.getAliados().add(heroi);

        // 2. Instanciar o Gerenciador
        GerenciadorDeBatalha gerenciador = new GerenciadorDeBatalha();
        gerenciador.iniciarCombate(aliados, inimigos, tripulacao);

        System.out.println("--- BATALHA NO TERMINAL INICIADA ---");
        boolean jogoRodando = true;

        while (jogoRodando) {
            switch (gerenciador.getEstadoAtual()) {
                case PLANEJAMENTO_JOGADOR:
                    Personagem aliadoVez = gerenciador.getAliadoAguardandoAcao();
                    
                    System.out.println("\n--- Turno de " + aliadoVez.getNome() + " ---");
                    System.out.println("HP: " + aliadoVez.getVidaAtual() + "/" + aliadoVez.getVidaMaxima());
                    System.out.println("Escolha uma habilidade:");
                    
                    for (int i = 0; i < aliadoVez.getHabilidades().size(); i++) {
                        Habilidade hab = aliadoVez.getHabilidades().get(i);
                        System.out.println((i + 1) + " - " + hab.getNome() + " (" + hab.getTipo() + " - Efeito: " + hab.getValorPoder() + ")");
                    }
                    
                    System.out.print("Opção: ");
                    int opcao = scanner.nextInt();
                    Habilidade habEscolhida = aliadoVez.getHabilidades().get(opcao - 1);
                    
                    Personagem alvoEscolhido;
                    if (habEscolhida.getTipo() == TipoHabilidade.CURA || habEscolhida.getTipo() == TipoHabilidade.DEFESA) {
                        alvoEscolhido = aliadoVez; // Cura/Defende a si mesmo
                    } else {
                        alvoEscolhido = inimigos.get(0); // Ataca o inimigo
                    }
                    
                    gerenciador.registrarAcaoJogador(habEscolhida, alvoEscolhido);
                    break;

                case PLANEJAMENTO_INIMIGOS:
                    // Inimigos são automáticos, a máquina de estados andará pra frente
                    break;

                case EXECUCAO_TURNOS:
                    Personagem quemAgiu = gerenciador.executarProximaAcao();
                    if (quemAgiu != null) {
                        try { Thread.sleep(1000); } catch (InterruptedException e) {}
                    }
                    break;

                case VITORIA:
                    System.out.println("\n--- VITÓRIA! ---");
                    System.out.println("XP do " + heroi.getNome() + ": " + heroi.getExperiencia());
                    System.out.println("Dinheiro da Tripulação: " + tripulacao.getDinheiro());
                    jogoRodando = false;
                    break;

                case DERROTA:
                    System.out.println("\n--- GAME OVER! ---");
                    jogoRodando = false;
                    break;
                    
                default:
                    break;
            }
        }
        
        System.out.println("Simulação Encerrada.");
        scanner.close();
    }
}
