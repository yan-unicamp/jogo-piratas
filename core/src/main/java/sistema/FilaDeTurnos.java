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
        this.fila   = new ArrayList<>();
        this.espera = new ArrayList<>();
    }

    /** Adiciona um personagem à fila de turnos. */
    public void adicionar(Personagem p) {
        fila.add(p);
    }

    /** Ordena a fila por iniciativa (decrescente — maior age primeiro). */
    public void ordenarPorIniciativa() {
        fila.sort(Comparator.comparing(Personagem::getIniciativa).reversed());
    }

    /**
     * Recoloca os personagens de espera de volta na fila e reordena.
     * Chamado quando a fila esgota (fim de uma rodada completa).
     */
    private void resetarFila() {
        fila.addAll(espera);
        espera.clear();
        ordenarPorIniciativa();
    }

    /**
     * Retorna o próximo personagem da fila.
     * Quando a fila se esgota, reseta-a e retorna null (sinal de nova rodada).
     */
    public Personagem obterProximoPersonagem() {
        if (fila.isEmpty()) {
            resetarFila();
            return null;
        }
        Personagem prox = fila.get(0);
        fila.remove(0);
        espera.add(prox);
        return prox;
    }

    /** Remove um personagem de todas as listas (ex: após morte definitiva). */
    public void remover(Personagem p) {
        fila.remove(p);
        espera.remove(p);
    }

    public ArrayList<Personagem> getFila() { return fila; }
}
