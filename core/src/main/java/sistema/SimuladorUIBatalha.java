package sistema;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;
import entidades.TipoHabilidade;
import java.util.ArrayList;

public class SimuladorUIBatalha {

    public static void main(String[] args) {
        // 1. Criar dados mockados
        Aliado heroi = new Aliado("Jack Sparrow", 100, 1.0f, 10, 1, 0);
        heroi.adicionarHabilidade(new Habilidade("Corte de Espada", TipoHabilidade.DANO, 25));
        heroi.adicionarHabilidade(new Habilidade("Poção de Cura", TipoHabilidade.CURA, 30));

        Inimigo esqueleto = new Inimigo("Esqueleto Amaldiçoado", 80, 1.0f, 8, null);
        esqueleto.adicionarHabilidade(new Habilidade("Garrada", TipoHabilidade.DANO, 15));

        ArrayList<Personagem> aliados = new ArrayList<>();
        aliados.add(heroi);

        ArrayList<Personagem> inimigos = new ArrayList<>();
        inimigos.add(esqueleto);

        // 2. Instanciar o Gerenciador
        GerenciadorDeBatalha gerenciador = new GerenciadorDeBatalha();
        gerenciador.iniciarCombate(aliados, inimigos);

        // 3. Simular o loop de Renderização do LibGDX (exemplo: roda 60 vezes por segundo)
        System.out.println("--- SIMULADOR DA TELA DE BATALHA DO LIBGDX ---");
        boolean jogoRodando = true;

        while (jogoRodando) {
            switch (gerenciador.getEstadoAtual()) {
                case PLANEJAMENTO_JOGADOR:
                    // UI descobre quem é a vez
                    Personagem aliadoVez = gerenciador.getAliadoAguardandoAcao();
                    
                    System.out.println("\n[LibGDX: Aguardando UI (Botões) para o turno de: " + aliadoVez.getNome() + "]");
                    
                    // Aqui a tela exibiria botões e pararia. No simulador, vamos "forçar" um clique via código:
                    Habilidade habEscolhida = aliadoVez.getHabilidades().get(0); // Força sempre a primeira habilidade
                    Personagem alvoEscolhido = inimigos.get(0); // Força atacar o inimigo
                    
                    System.out.println("(Simulando clique de mouse na habilidade '" + habEscolhida.getNome() + "' contra '" + alvoEscolhido.getNome() + "')");
                    
                    // Avisa o gerenciador do "clique" e a máquina de estados andará pra frente
                    gerenciador.registrarAcaoJogador(habEscolhida, alvoEscolhido);
                    break;

                case PLANEJAMENTO_INIMIGOS:
                    // Inimigos são automáticos, a tela não faz nada aqui.
                    break;

                case EXECUCAO_TURNOS:
                    // Aqui a tela executa a próxima animação
                    Personagem quemAgiu = gerenciador.executarProximaAcao();
                    
                    if (quemAgiu != null) {
                        System.out.println("(LibGDX: Simulando tempo de tela para a animação da espada...)");
                        try { Thread.sleep(1500); } catch (InterruptedException e) {}
                    }
                    break;

                case VITORIA:
                    System.out.println("\n[LibGDX: MOSTRAR MENSAGEM 'VITÓRIA!', TOCAR MUSICA, DISTRIBUIR EXPERIÊNCIA!]");
                    jogoRodando = false;
                    break;

                case DERROTA:
                    System.out.println("\n[LibGDX: MOSTRAR MENSAGEM 'GAME OVER!']");
                    jogoRodando = false;
                    break;
                    
                default:
                    break;
            }
        }
        
        System.out.println("Simulação Encerrada.");
    }
}
