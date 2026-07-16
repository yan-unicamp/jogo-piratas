package sistema;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;
import entidades.TipoHabilidade;

/**
 * Gerenciador de batalha para o modo GUI (LibGDX).
 *
 * Funciona orientado a eventos — ao invés de um loop bloqueante,
 * expõe o estado atual da batalha para que a TelaBatalha possa
 * consultar e reagir:
 *
 *  1. {@link #iniciarCombate} — configura aliados, inimigos e ordem de turnos.
 *  2. {@link #getPersonagemAtivo} — retorna o aliado que está aguardando
 *     input do jogador (null se for turno de inimigo ou batalha encerrada).
 *  3. {@link #executarTurnoAliado} — chamado pela TelaBatalha ao clicar
 *     numa habilidade; executa a ação do aliado e depois processa
 *     automaticamente todos os turnos de inimigos até o próximo aliado.
 *  4. {@link #isBatalhaEncerrada} / {@link #isVitoria} — estado final.
 */
public class GerenciadorDeBatalha {

    // ── Estado da batalha ─────────────────────────────────────────────────────
    private List<Aliado>    aliados;
    private List<Inimigo>   inimigos;
    private List<Personagem> ordemTurnos;   // todos, ordenados por iniciativa
    private int              indiceTurno;
    private Personagem       personagemAtivo; // aliado esperando input do jogador
    private boolean          batalhaEncerrada;
    private boolean          vitoria;
    private List<String>     logCombate;

    private final Random random = new Random();

    // ──────────────────────────────────────────────────────────────────────────
    // Inicialização
    // ──────────────────────────────────────────────────────────────────────────

    public GerenciadorDeBatalha() {
        this.logCombate = new ArrayList<>();
    }

    /**
     * Configura e inicia um novo combate.
     *
     * @param aliadosPersonagens Lista de aliados vivos da tripulação.
     * @param inimigosLista      Lista de inimigos da rodada.
     */
    public void iniciarCombate(List<Aliado> aliadosPersonagens, List<Inimigo> inimigosLista) {
        this.aliados          = new ArrayList<>(aliadosPersonagens);
        this.inimigos         = new ArrayList<>(inimigosLista);
        this.logCombate       = new ArrayList<>();
        this.batalhaEncerrada = false;
        this.vitoria          = false;
        this.indiceTurno      = 0;
        this.personagemAtivo  = null;

        // Monta a ordem de turnos por iniciativa decrescente
        this.ordemTurnos = new ArrayList<>();
        ordemTurnos.addAll(aliados);
        ordemTurnos.addAll(inimigos);
        ordemTurnos.sort(Comparator.comparingInt(Personagem::getIniciativa).reversed());

        logCombate.add("⚔ Batalha iniciada! " + inimigos.size() + " inimigo(s).");

        // Avança até o primeiro aliado que precisa de input
        avancarAteProximoAliado();
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Controle de turnos
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Executa o turno do aliado ativo com a habilidade e alvo escolhidos.
     * Depois processa automaticamente todos os turnos de inimigos até
     * chegar no próximo aliado vivo (ou encerrar a batalha).
     *
     * Chamado pela TelaBatalha ao clicar num botão de habilidade.
     */
    public void executarTurnoAliado(Habilidade habilidade, Personagem alvo) {
        if (batalhaEncerrada || personagemAtivo == null) return;

        executarAcao(personagemAtivo, habilidade, alvo);
        indiceTurno++;
        personagemAtivo = null;

        if (verificarFimBatalha()) return;

        avancarAteProximoAliado();
    }

    /**
     * Avança a fila de turnos processando inimigos automaticamente
     * até encontrar um aliado vivo ou a batalha encerrar.
     */
    private void avancarAteProximoAliado() {
        int total     = ordemTurnos.size();
        int tentativas = 0;

        while (tentativas < total * 3) {
            if (verificarFimBatalha()) return;

            Personagem atual = ordemTurnos.get(indiceTurno % total);

            if (!atual.estaVivo()) {
                // Personagem morto — pula o turno
                indiceTurno++;
                tentativas++;
                continue;
            }

            if (atual instanceof Aliado) {
                // Para aqui e aguarda input do jogador
                personagemAtivo = atual;
                return;
            } else {
                // Inimigo — age automaticamente
                executarTurnoInimigo(atual);
                indiceTurno++;
                tentativas++;

                if (verificarFimBatalha()) return;
            }
        }

        // Segurança: todos os personagens desta rodada agiram — reinicia o índice
        indiceTurno = 0;
        avancarAteProximoAliado();
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Execução de ações
    // ──────────────────────────────────────────────────────────────────────────

    private void executarAcao(Personagem agente, Habilidade habilidade, Personagem alvo) {
        if (agente == null || habilidade == null || alvo == null) return;
        if (!agente.estaVivo()) return;

        boolean alvoPodeSerAlvo = alvo.estaVivo()
                || habilidade.getTipo() == TipoHabilidade.CURA;

        if (alvoPodeSerAlvo) {
            habilidade.executarAcao(alvo);
            String msg = agente.getNome() + " usou [" + habilidade.getNome() + "]";
            if (habilidade.getTipo() == TipoHabilidade.CURA) {
                msg += " → curou " + alvo.getNome();
            } else {
                int danoAprox = (int) habilidade.getValorPoder();
                msg += " em " + alvo.getNome() + " (" + danoAprox + " dmg)";
                if (!alvo.estaVivo()) msg += " 💀";
            }
            logCombate.add(msg);
        } else {
            logCombate.add(agente.getNome() + " mirou em " + alvo.getNome()
                    + ", mas ele já foi derrotado!");
        }
    }

    private void executarTurnoInimigo(Personagem inimigo) {
        List<Habilidade> habs = inimigo.getHabilidades();
        if (habs.isEmpty()) {
            logCombate.add(inimigo.getNome() + " passa o turno.");
            return;
        }

        Habilidade hab = habs.get(random.nextInt(habs.size()));

        if (hab.getTipo() == TipoHabilidade.CURA || hab.getTipo() == TipoHabilidade.DEFESA) {
            // Inimigo se cura ou cura outro inimigo
            List<Inimigo> inimigosVivos = getInimigosVivos();
            if (!inimigosVivos.isEmpty()) {
                executarAcao(inimigo, hab, inimigosVivos.get(random.nextInt(inimigosVivos.size())));
            }
        } else {
            List<Aliado> aliadosVivos = getAliadosVivos();
            if (!aliadosVivos.isEmpty()) {
                executarAcao(inimigo, hab, aliadosVivos.get(random.nextInt(aliadosVivos.size())));
            }
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Verificação de fim de batalha
    // ──────────────────────────────────────────────────────────────────────────

    private boolean verificarFimBatalha() {
        if (getAliadosVivos().isEmpty()) {
            batalhaEncerrada = true;
            vitoria          = false;
            logCombate.add("💀 Derrota! Todos os aliados caíram em batalha.");
            return true;
        }
        if (getInimigosVivos().isEmpty()) {
            batalhaEncerrada = true;
            vitoria          = true;
            logCombate.add("🏆 Vitória! Todos os inimigos foram derrotados!");
            return true;
        }
        return false;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Getters para TelaBatalha
    // ──────────────────────────────────────────────────────────────────────────

    /** Aliado cuja vez é no momento (aguarda input). Null se for turno de inimigo ou batalha encerrada. */
    public Personagem getPersonagemAtivo() { return personagemAtivo; }

    public boolean isBatalhaEncerrada()    { return batalhaEncerrada; }
    public boolean isVitoria()             { return vitoria; }

    /** Log textual das últimas ações — exibido no painel lateral da TelaBatalha. */
    public List<String> getLogCombate()    { return logCombate; }

    /** Todos os aliados (vivos e mortos). */
    public List<Aliado> getAliados()       { return aliados; }

    /** Todos os inimigos (vivos e mortos). */
    public List<Inimigo> getInimigos()     { return inimigos; }

    /** Apenas aliados ainda vivos. */
    public List<Aliado> getAliadosVivos() {
        List<Aliado> vivos = new ArrayList<>();
        if (aliados == null) return vivos;
        for (Aliado a : aliados) {
            if (a.estaVivo()) vivos.add(a);
        }
        return vivos;
    }

    /** Apenas inimigos ainda vivos. */
    public List<Inimigo> getInimigosVivos() {
        List<Inimigo> vivos = new ArrayList<>();
        if (inimigos == null) return vivos;
        for (Inimigo i : inimigos) {
            if (i.estaVivo()) vivos.add(i);
        }
        return vivos;
    }
}
