package sistema;

import java.util.ArrayList;

import entidades.Personagem;

public class GerenciadorDeBatalha {
    private FilaDeTurnos filaDeTurnos;

    public GerenciadorDeBatalha() {
        this.filaDeTurnos = new FilaDeTurnos();
    }

    public void iniciarCombate(ArrayList<Personagem> aliados, ArrayList<Personagem> inimigos) {
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
            acoesTurno();
            executarTurno();

            if (verificarVitoriaOuDerrota()) {
                combateIniciado = false;
            }
        }
    }

    public void acoesTurno() {
        // Implementar logica de pegar ações no turno
    }

    public void executarTurno() {
        // Implementar logica de depois das ações serem feitas pelo jogador
    }

    public boolean verificarVitoriaOuDerrota() {
        // Implementar logica de verificar se o combate acabou
        return false;
    }
}
