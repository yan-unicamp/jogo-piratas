package sistema;

import java.util.ArrayList;

import entidades.Personagem;

public class GerenciadorDeBatalha {
    private final FilaDeTurnos filaDeTurnos;
    private ArrayList<Personagem> aliados;
    private ArrayList<Personagem> inimigos;

    public GerenciadorDeBatalha() {
        this.filaDeTurnos = new FilaDeTurnos();
    }

    public void iniciarCombate(ArrayList<Personagem> aliados, ArrayList<Personagem> inimigos) {
        this.aliados = aliados;
        this.inimigos = inimigos;
        System.out.println("Debug: Combate iniciado.");
        for (Personagem aliado : aliados) {
            filaDeTurnos.adicionar(aliado);
        }

        for (Personagem inimigo : inimigos) {
            filaDeTurnos.adicionar(inimigo);
        }

        filaDeTurnos.ordenarPorIniciativa();

        boolean combateIniciado = true;

        while (combateIniciado) {
            Personagem personagemDaVez = filaDeTurnos.obterProximoPersonagem();

            if (personagemDaVez != null && personagemDaVez.getVidaAtual() > 0) {
                System.out.println("\n--- Turno de: " + personagemDaVez.getNome() + " ---");
                acoesTurno(personagemDaVez);
                executarTurno(personagemDaVez);
            }

            if (verificarVitoriaOuDerrota()) {
                combateIniciado = false;
            }
        }
    }

    public void acoesTurno(Personagem personagem) {
        // TODO: Implementar lógica de escolha de ação (ex: jogador escolhe habilidade/alvo no console, inimigo usa IA simples)
        System.out.println("Aguardando ação de " + personagem.getNome() + "...");
    }

    public void executarTurno(Personagem personagem) {
        // TODO: Implementar execução da ação escolhida
        System.out.println("Ação de " + personagem.getNome() + " executada.");
    }

    public boolean verificarVitoriaOuDerrota() {
        boolean aliadosVivos = false;
        for (Personagem aliado : aliados) {
            if (aliado.getVidaAtual() > 0) {
                aliadosVivos = true;
                break;
            }
        }

        boolean inimigosVivos = false;
        for (Personagem inimigo : inimigos) {
            if (inimigo.getVidaAtual() > 0) {
                inimigosVivos = true;
                break;
            }
        }

        if (!aliadosVivos) {
            System.out.println("\nDerrota! Todos os aliados caíram em batalha.");
            return true;
        } else if (!inimigosVivos) {
            System.out.println("\nVitória! Todos os inimigos foram derrotados.");
            return true;
        }

        return false;
    }
}
