package progressao;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia a progressão por ilhas do Grand Line.
 *
 * Substitui o sistema de nós aleatórios. As ilhas são pré-definidas em ordem
 * por {@link ProgressaoIlhas} e o jogador as percorre sequencialmente.
 *
 * Fluxo:
 *  TelaMapa → entrarIlha(ilha) → mudarEstado(BATALHA) [GameManager]
 *  TelaBatalha vitória → avancarRodada():
 *    false → próxima rodada da mesma ilha → mudarEstado(BATALHA)
 *    true  → ilha concluída → mudarEstado(MAPA)
 */
public class Mapa {

    private final List<Ilha> ilhas;
    private Ilha ilhaAtual;
    private int  etapaAtual; // número de ilhas concluídas

    // ── Mantido para retrocompatibilidade com código dos outros membros ────────
    private final List<NoMapa> proximosNos = new ArrayList<>();

    public Mapa() {
        this.ilhas     = ProgressaoIlhas.criarTodasIlhas();
        this.etapaAtual = 0;
        this.ilhaAtual  = null;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Sistema de Ilhas (novo)
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Define a ilha atual e reinicia o progresso dela para a rodada 0.
     * Chamado por GameManager.entrarIlha().
     */
    public void entrarIlha(Ilha ilha) {
        ilha.resetar();
        this.ilhaAtual = ilha;
    }

    /**
     * Avança para a próxima rodada dentro da ilha atual.
     * @return true se a ilha foi completamente vencida (todas as rodadas).
     */
    public boolean avancarRodada() {
        if (ilhaAtual == null) return true;
        boolean concluida = ilhaAtual.avancarRodada();
        if (concluida) {
            etapaAtual++;
        }
        return concluida;
    }

    /** @return A rodada atual da ilha em curso, ou null. */
    public Rodada getRodadaAtual() {
        return ilhaAtual != null ? ilhaAtual.getRodadaAtual() : null;
    }

    /** @return true se não há mais ilhas para jogar. */
    public boolean jogoCompleto() {
        return etapaAtual >= ilhas.size();
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Sistema de Nós (legado — mantido para compatibilidade)
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * No-op no novo sistema. Mantido para que código dos outros membros
     * que ainda chame gerarProximosNos() não quebre.
     */
    public void gerarProximosNos() {
        // No-op: progressão agora é por ilhas (ProgressaoIlhas)
    }

    /** @deprecated Use entrarIlha(Ilha, GameManager) */
    @Deprecated
    public void avancarParaNo(NoMapa proximo, sistema.GameManager gameManager) {
        proximo.entrarNo(gameManager);
    }

    /** @deprecated Mantido para retrocompatibilidade. */
    @Deprecated
    public void avancarParaNo(NoMapa proximo) { /* no-op */ }

    // ──────────────────────────────────────────────────────────────────────────
    // Getters
    // ──────────────────────────────────────────────────────────────────────────

    public List<Ilha>  getIlhas()         { return ilhas; }
    public Ilha        getIlhaAtual()     { return ilhaAtual; }
    public int         getEtapaAtual()    { return etapaAtual; }

    /** @deprecated Retorna lista vazia no novo sistema. Use getIlhas(). */
    @Deprecated
    public List<NoMapa> getProximosNos()  { return proximosNos; }

    /** @deprecated Use getIlhaAtual(). */
    @Deprecated
    public NoMapa getNoAtual()            { return null; }
}
