package sistema;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import entidades.Habilidade;
import entidades.Personagem;

public class GerenciadorDeBatalha {
    private final FilaDeTurnos filaDeTurnos;
    private ArrayList<Personagem> aliados;
    private ArrayList<Personagem> inimigos;
    private Scanner scanner;
    private Random random;
    private java.util.HashMap<Personagem, AcaoPlanejada> acoesPlanejadas;

    private static class AcaoPlanejada {
        Habilidade habilidade;
        Personagem alvo;
        AcaoPlanejada(Habilidade habilidade, Personagem alvo) {
            this.habilidade = habilidade;
            this.alvo = alvo;
        }
    }

    public GerenciadorDeBatalha() {
        this.filaDeTurnos = new FilaDeTurnos();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.acoesPlanejadas = new java.util.HashMap<>();
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
            acoesPlanejadas.clear();
            System.out.println("\n=== FASE DE PLANEJAMENTO ===");
            for (Personagem aliado : aliados) {
                if (aliado.getVidaAtual() > 0) {
                    System.out.println("\n--- Planejamento de: " + aliado.getNome() + " ---");
                    acoesTurno(aliado);
                }
            }
            for (Personagem inimigo : inimigos) {
                if (inimigo.getVidaAtual() > 0) {
                    acoesTurno(inimigo);
                }
            }

            System.out.println("\n=== FASE DE EXECUÇÃO ===");
            Personagem personagemDaVez;
            while ((personagemDaVez = filaDeTurnos.obterProximoPersonagem()) != null) {
                if (personagemDaVez.getVidaAtual() > 0) {
                    executarTurno(personagemDaVez);
                }
                if (verificarVitoriaOuDerrota()) {
                    combateIniciado = false;
                    break;
                }
            }
        }
    }

    public void acoesTurno(Personagem personagem) {
        if (personagem instanceof entidades.Aliado) {
            escolherAcaoJogador(personagem);
        } else {
            escolherAcaoInimigo(personagem);
        }
    }

    public void executarTurno(Personagem personagem) {
        AcaoPlanejada acao = acoesPlanejadas.get(personagem);
        if (acao != null && acao.habilidade != null && acao.alvo != null) {
            if (acao.alvo.getVidaAtual() > 0 || acao.habilidade.getTipo() == entidades.TipoHabilidade.CURA) {
                System.out.println("[" + personagem.getNome() + " atua]");
                acao.habilidade.executarAcao(acao.alvo);
            } else {
                System.out.println("[" + personagem.getNome() + " atua] Mas o alvo " + acao.alvo.getNome() + " já foi derrotado!");
            }
        }
    }

    private void escolherAcaoJogador(Personagem personagem) {
        System.out.println("Escolha uma habilidade para " + personagem.getNome() + ":");
        ArrayList<Habilidade> habs = new ArrayList<>(personagem.getHabilidades());
        if (habs.isEmpty()) {
            System.out.println(personagem.getNome() + " não possui habilidades!");
            return;
        }

        for (int i = 0; i < habs.size(); i++) {
            System.out.println((i + 1) + " - " + habs.get(i).getNome() + " (Poder: " + habs.get(i).getValorPoder() + ")");
        }

        int habIndex = -1;
        while (habIndex < 0 || habIndex >= habs.size()) {
            System.out.print("Opção: ");
            if (scanner.hasNextInt()) {
                habIndex = scanner.nextInt() - 1;
            } else {
                scanner.next();
            }
        }
        Habilidade habEscolhida = habs.get(habIndex);

        System.out.println("Escolha um alvo:");
        ArrayList<Personagem> alvos = new ArrayList<>();
        if (habEscolhida.getTipo() == entidades.TipoHabilidade.CURA || habEscolhida.getTipo() == entidades.TipoHabilidade.DEFESA) {
            for (Personagem p : aliados) {
                if (p.getVidaAtual() > 0) alvos.add(p);
            }
        } else {
            for (Personagem p : inimigos) {
                if (p.getVidaAtual() > 0) alvos.add(p);
            }
        }

        if (alvos.isEmpty()) {
            System.out.println("Nenhum alvo válido para esta habilidade! Turno ignorado.");
            return;
        }

        for (int i = 0; i < alvos.size(); i++) {
            System.out.println((i + 1) + " - " + alvos.get(i).getNome() + " (HP: " + alvos.get(i).getVidaAtual() + "/" + alvos.get(i).getVidaMaxima() + ")");
        }

        int alvoIndex = -1;
        while (alvoIndex < 0 || alvoIndex >= alvos.size()) {
            System.out.print("Opção: ");
            if (scanner.hasNextInt()) {
                alvoIndex = scanner.nextInt() - 1;
            } else {
                scanner.next();
            }
        }
        Personagem alvEscolhido = alvos.get(alvoIndex);
        acoesPlanejadas.put(personagem, new AcaoPlanejada(habEscolhida, alvEscolhido));
    }

    private void escolherAcaoInimigo(Personagem personagem) {
        ArrayList<Habilidade> habs = new ArrayList<>(personagem.getHabilidades());
        if (habs.isEmpty()) {
            return;
        }
        Habilidade habEscolhida = habs.get(random.nextInt(habs.size()));

        ArrayList<Personagem> alvos = new ArrayList<>();
        if (habEscolhida.getTipo() == entidades.TipoHabilidade.CURA || habEscolhida.getTipo() == entidades.TipoHabilidade.DEFESA) {
            for (Personagem p : inimigos) {
                if (p.getVidaAtual() > 0) alvos.add(p);
            }
        } else {
            for (Personagem p : aliados) {
                if (p.getVidaAtual() > 0) alvos.add(p);
            }
        }

        Personagem alvEscolhido = null;
        if (!alvos.isEmpty()) {
            alvEscolhido = alvos.get(random.nextInt(alvos.size()));
        }
        
        acoesPlanejadas.put(personagem, new AcaoPlanejada(habEscolhida, alvEscolhido));
        System.out.println(personagem.getNome() + " preparou uma ação.");
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
