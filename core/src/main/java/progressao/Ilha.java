package progressao;

import java.util.List;

/**
 * Representa uma Ilha do Grand Line.
 *
 * Cada ilha contém uma lista de {@link Rodada} em sequência:
 * tipicamente 1-2 rodadas de capangas + 1 rodada de Boss.
 * Ao vencer todas as rodadas, a ilha fica marcada como concluída
 * e a próxima ilha é desbloqueada.
 *
 * bgKey: caminho relativo do background, ex: "backgrounds/arlong_park.png"
 */
public class Ilha {

    private final String nome;
    private final String bgKey;
    private final List<Rodada> rodadas;

    private int rodadaAtualIdx = 0;
    private boolean completa   = false;

    public Ilha(String nome, String bgKey, List<Rodada> rodadas) {
        this.nome    = nome;
        this.bgKey   = bgKey;
        this.rodadas = rodadas;
    }

    /** @return A rodada atual, ou null se a ilha já foi concluída. */
    public Rodada getRodadaAtual() {
        if (rodadaAtualIdx >= rodadas.size()) return null;
        return rodadas.get(rodadaAtualIdx);
    }

    /**
     * Avança para a próxima rodada.
     * @return true se a ilha foi completamente vencida (não há mais rodadas).
     */
    public boolean avancarRodada() {
        rodadaAtualIdx++;
        if (rodadaAtualIdx >= rodadas.size()) {
            completa = true;
            return true;
        }
        return false;
    }

    /** Reseta o progresso da ilha para começar do início (rodada 0). */
    public void resetar() {
        rodadaAtualIdx = 0;
        completa       = false;
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String      getNome()           { return nome; }
    public String      getBgKey()          { return bgKey; }
    public List<Rodada> getRodadas()       { return rodadas; }
    public int         getRodadaAtualIdx() { return rodadaAtualIdx; }
    public int         getTotalRodadas()   { return rodadas.size(); }
    public boolean     isCompleta()        { return completa; }
}
