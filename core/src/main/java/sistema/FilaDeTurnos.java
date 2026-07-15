package sistema;

import java.util.ArrayList;
import java.util.Comparator;

import entidades.Personagem;

/**
 * Mantém a ordem de turnos dos participantes do combate.
 *
 * Os personagens são ordenados de forma decrescente por iniciativa:
 * quem tem maior iniciativa age primeiro.
 * Ao término de cada "rodada" (todos agiram), a fila é reiniciada.
 */
public class FilaDeTurnos {
    private final ArrayList<Personagem> fila;
    private final ArrayList<Personagem> espera;

    public FilaDeTurnos() {
        this.fila = new ArrayList<>();
        this.espera = new ArrayList<>();
    }

    public void adicionar(Personagem p) {
        fila.add(p);
    }

    public void ordenarPorIniciativa() {
        fila.sort(Comparator.comparing(Personagem::getIniciativa).reversed());
    }

    /**
     * Adiciona novamente membros da espera para lista e ordena a lista por iniciativa.
     */
    private void resetarFila() {
        fila.addAll(espera);
        espera.clear();
        ordenarPorIniciativa();
    }


    /**
     * Retorna o proximo personagem da fila.
     * Caso a fila acabe, reseta ela e retorna nulo.
     */
    public Personagem obterProximoPersonagem() {
        if (fila.isEmpty()) {  // Se a fila acabou, coloca os personagens de volta na fila e ordena, retornando null.
            resetarFila();
            return null;
        }

        Personagem prox = fila.getFirst();
        fila.removeFirst();
        espera.add(prox);

        return prox;
    }

    /** Adiciona um personagem à fila (sem reordenar). */
    public void adicionar(Personagem p) {
        fila.add(p);
    }

    /** Remove um personagem da fila (ex: após morte definitiva). */
    public void remover(Personagem p) {
        fila.remove(p);
        if (indiceTurnoAtual >= fila.size()) {
            indiceTurnoAtual = 0;
        }
    }

    public List<Personagem> getFila() { return fila; }
    public int getIndiceTurnoAtual() { return indiceTurnoAtual; }
}
