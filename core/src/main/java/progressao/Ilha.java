package progressao;

import java.util.List;

/**
 * Representa uma Ilha do Grand Line.
 *
 * Cada ilha contem uma lista de {@link Rodada} em sequencia:
 * tipicamente 1-2 rodadas de capangas + 1 rodada de Boss.
 * Ao vencer todas as rodadas, a ilha fica marcada como concluida
 * e a proxima ilha e desbloqueada.
 *
 * bgKey: caminho relativo do background, ex: "backgrounds/arlong_park.png"
 */
public class Ilha {

    private final String nome;
    private final String bgKey;
    private final List<Rodada> rodadas;
    private String descricao;

    private int rodadaAtualIdx = 0;
    private boolean completa   = false;

    public Ilha(String nome, String bgKey, List<Rodada> rodadas) {
        this.nome    = nome;
        this.bgKey   = bgKey;
        this.rodadas = rodadas;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /** @return A rodada atual, ou null se a ilha ja foi concluida. */
    public Rodada getRodadaAtual() {
        if (rodadaAtualIdx >= rodadas.size()) return null;
        return rodadas.get(rodadaAtualIdx);
    }

    /**
     * Avanca para a proxima rodada.
     * @return true se a ilha foi completamente vencida (nao ha mais rodadas).
     */
    public boolean avancarRodada() {
        rodadaAtualIdx++;
        if (rodadaAtualIdx >= rodadas.size()) {
            completa = true;
            return true;
        }
        return false;
    }

    /** Reseta o progresso da ilha para comecar do inicio (rodada 0). */
    public void resetar() {
        rodadaAtualIdx = 0;
        completa       = false;
    }

    // -- Getters ---------------------------------------------------------------
    public String      getNome()           { return nome; }
    public String      getBgKey()          { return bgKey; }
    public List<Rodada> getRodadas()       { return rodadas; }
    public int         getRodadaAtualIdx() { return rodadaAtualIdx; }
    public int         getTotalRodadas()   { return rodadas.size(); }
    public boolean     isCompleta()        { return completa; }

    public entidades.Aliado getRecompensaAliadoDaIlha() {
        if (rodadas != null && !rodadas.isEmpty()) {
            Rodada ultima = rodadas.get(rodadas.size() - 1);
            if (ultima.isBoss() && ultima.getInimigos() != null) {
                for (entidades.Inimigo inimigo : ultima.getInimigos()) {
                    if (inimigo.getRecompensaAliado() != null) {
                        return inimigo.getRecompensaAliado();
                    }
                }
            }
        }
        return null;
    }
}
