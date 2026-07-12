package sistema;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import entidades.Personagem;

/**
 * Mantém a ordem de turnos dos participantes do combate.
 *
 * Os personagens são ordenados de forma decrescente por iniciativa:
 * quem tem maior iniciativa age primeiro.
 * Ao término de cada "rodada" (todos agiram), a fila é reiniciada.
 */
public class FilaDeTurnos {

    private List<Personagem> fila;
    private int indiceTurnoAtual;

    public FilaDeTurnos() {
        this.fila = new ArrayList<>();
        this.indiceTurnoAtual = 0;
    }

    /**
     * Popula a fila com os personagens fornecidos e ordena por iniciativa (maior primeiro).
     * Deve ser chamado por GerenciadorDeBatalha.iniciarCombate().
     */
    public void popular(List<? extends Personagem> participantes) {
        fila.clear();
        fila.addAll(participantes);
        ordenarPorIniciativa();
        indiceTurnoAtual = 0;
    }

    /**
     * Ordena a fila de forma decrescente por iniciativa.
     * Chamado automaticamente por popular(), mas pode ser chamado novamente
     * se iniciativas mudarem durante o combate.
     */
    public void ordenarPorIniciativa() {
        fila.sort(Comparator.comparingInt(Personagem::getIniciativa).reversed());
    }

    /**
     * Retorna o personagem cujo turno é o atual e avança o índice interno.
     * Quando a rodada termina (todos agiram), reinicia do primeiro personagem.
     * Personagens mortos são pulados automaticamente.
     *
     * @return O próximo personagem vivo a agir, ou null se a fila estiver vazia.
     */
    public Personagem obterProximoPersonagem() {
        if (fila.isEmpty()) return null;

        // Tenta até dar uma volta completa na fila buscando alguém vivo
        int tentativas = 0;
        while (tentativas < fila.size()) {
            if (indiceTurnoAtual >= fila.size()) {
                indiceTurnoAtual = 0; // reinicia a rodada
            }
            Personagem candidato = fila.get(indiceTurnoAtual);
            indiceTurnoAtual++;
            if (candidato.estaVivo()) {
                return candidato;
            }
            tentativas++;
        }
        return null; // todos mortos
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
