package sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;
import entidades.TipoHabilidade;

/**
 * Orquestra o combate por turnos entre aliados e inimigos.
 *
 * Fluxo de uso pela TelaBatalha:
 *   1. GameManager chama iniciarCombate(aliados, inimigos) → fila montada e ordenada
 *   2. TelaBatalha chama getPersonagemAtivo() para saber quem age agora
 *   3. Se for aliado: player escolhe habilidade → executarTurnoAliado(habilidade, alvo)
 *      Se for inimigo: executarTurnoInimigo() (IA automática)
 *   4. Após cada turno: verificarVitoriaOuDerrota()
 *   5. isVitoria() / isBatalhaEncerrada() guiam a transição de tela
 */
public class GerenciadorDeBatalha {

    private final FilaDeTurnos filaDeTurnos;
    private List<Aliado>  aliados;
    private List<Inimigo> inimigos;
    private boolean batalhaEncerrada;
    private boolean vitoria;

    /** Log de combate — cada entrada descreve o que aconteceu no último turno. */
    private final List<String> logCombate;

    private final Random rng = new Random();

    public GerenciadorDeBatalha() {
        this.filaDeTurnos    = new FilaDeTurnos();
        this.batalhaEncerrada = false;
        this.vitoria          = false;
        this.logCombate       = new ArrayList<>();
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Inicialização
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Configura e inicia um novo combate.
     * Chamado pelo GameManager antes de abrir a TelaBatalha.
     */
    public void iniciarCombate(List<Aliado> aliados, List<Inimigo> inimigos) {
        this.aliados  = aliados;
        this.inimigos = inimigos;
        this.batalhaEncerrada = false;
        this.vitoria          = false;
        logCombate.clear();

        // Monta a fila unificada e ordena por iniciativa
        List<Personagem> todos = new ArrayList<>();
        todos.addAll(aliados);
        todos.addAll(inimigos);
        filaDeTurnos.popular(todos);

        logCombate.add("=== Batalha iniciada! ===");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Controle de turno
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Retorna o personagem ativo no turno atual SEM avançar a fila.
     * A TelaBatalha usa isso para saber quem age e montar os botões corretos.
     */
    public Personagem getPersonagemAtivo() {
        if (batalhaEncerrada || filaDeTurnos.getFila().isEmpty()) return null;
        // Peek no índice atual sem consumir
        List<Personagem> fila = filaDeTurnos.getFila();
        int idx = filaDeTurnos.getIndiceTurnoAtual();
        if (idx >= fila.size()) idx = 0;
        return fila.get(idx);
    }

    /**
     * Executa o turno de um ALIADO com uma habilidade escolhida pelo jogador.
     *
     * @param habilidade  A habilidade selecionada.
     * @param alvo        O alvo da habilidade (inimigo para DANO, aliado para CURA/DEFESA).
     */
    public void executarTurnoAliado(Habilidade habilidade, Personagem alvo) {
        if (batalhaEncerrada) return;

        Personagem ator = filaDeTurnos.obterProximoPersonagem();
        if (ator == null || !ator.estaVivo()) return;

        habilidade.executarAcao(alvo);

        String log = ator.getNome() + " usou " + habilidade.getNome();
        if (alvo != null) log += " em " + alvo.getNome();
        log += "! (poder: " + habilidade.getValorPoder() + ")";
        logCombate.add(log);

        verificarVitoriaOuDerrota();

        // Se a batalha não acabou, executa automaticamente os turnos de inimigos
        // até chegar em um aliado novamente
        if (!batalhaEncerrada) {
            executarTurnosInimigosConsecutivos();
        }
    }

    /**
     * Versão simplificada de executarTurno() mantida por compatibilidade.
     * Executa o turno do personagem ativo com IA (usado por telas que não
     * distinguem turno de aliado / inimigo).
     */
    public void executarTurno() {
        if (batalhaEncerrada) return;
        Personagem ator = filaDeTurnos.obterProximoPersonagem();
        if (ator == null) return;

        if (ator instanceof Inimigo) {
            executarIAInimigo((Inimigo) ator);
        } else if (ator instanceof Aliado) {
            // Aliado sem input: usa a primeira habilidade de DANO disponível contra inimigo aleatório
            Personagem alvoPadrao = inimigosVivos().stream().findFirst().orElse(null);
            Habilidade habPadrao = ator.getHabilidades().stream()
                    .filter(h -> h.getTipo() == TipoHabilidade.DANO)
                    .findFirst()
                    .orElse(!ator.getHabilidades().isEmpty() ? ator.getHabilidades().get(0) : null);
            if (habPadrao != null && alvoPadrao != null) {
                habPadrao.executarAcao(alvoPadrao);
                logCombate.add(ator.getNome() + " usou " + habPadrao.getNome() + " em " + alvoPadrao.getNome());
            }
        }
        verificarVitoriaOuDerrota();
    }

    // ──────────────────────────────────────────────────────────────────────────
    // IA dos inimigos
    // ──────────────────────────────────────────────────────────────────────────

    /** Executa turnos de inimigos consecutivos até chegar num aliado vivo (ou batalha encerrar). */
    private void executarTurnosInimigosConsecutivos() {
        int limite = inimigos.size() + 1; // proteção contra loop infinito
        for (int i = 0; i < limite && !batalhaEncerrada; i++) {
            Personagem proximo = getPersonagemAtivo();
            if (proximo instanceof Aliado) break; // vez do player
            if (proximo instanceof Inimigo) {
                filaDeTurnos.obterProximoPersonagem(); // consume da fila
                executarIAInimigo((Inimigo) proximo);
                verificarVitoriaOuDerrota();
            }
        }
    }

    /** IA simples: ataca um aliado vivo aleatório. */
    private void executarIAInimigo(Inimigo inimigo) {
        List<Aliado> alvosValidos = aliadosVivos();
        if (alvosValidos.isEmpty()) return;

        Aliado alvo = alvosValidos.get(rng.nextInt(alvosValidos.size()));

        // Usa habilidade de DANO se tiver, senão ataque base
        Habilidade hab = inimigo.getHabilidades().stream()
                .filter(h -> h.getTipo() == TipoHabilidade.DANO)
                .findFirst()
                .orElse(null);

        if (hab != null) {
            hab.executarAcao(alvo);
            logCombate.add(inimigo.getNome() + " usou " + hab.getNome() + " em " + alvo.getNome()
                    + "! (poder: " + hab.getValorPoder() + ")");
        } else {
            // Ataque básico sem habilidade (poder = iniciativa * 2 como proxy)
            int danoBase = inimigo.getIniciativa() * 2;
            alvo.receberDano(danoBase);
            logCombate.add(inimigo.getNome() + " atacou " + alvo.getNome() + "! (dano base: " + danoBase + ")");
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Condições de vitória/derrota
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Verifica e atualiza os flags de fim de batalha.
     * Chamado após cada turno.
     */
    public void verificarVitoriaOuDerrota() {
        boolean todosInimigosMortos = inimigos.stream().noneMatch(Personagem::estaVivo);
        boolean todosAliadosMortos  = aliados.stream().noneMatch(Personagem::estaVivo);

        if (todosInimigosMortos) {
            vitoria = true;
            batalhaEncerrada = true;
            logCombate.add("=== Vitória! ===");
            // Recolhe recompensas de todos os inimigos derrotados
            coletarRecompensas();
        } else if (todosAliadosMortos) {
            vitoria = false;
            batalhaEncerrada = true;
            logCombate.add("=== Derrota... ===");
        }
    }

    /** Soma as recompensas de todos os inimigos e registra no log. */
    private void coletarRecompensas() {
        int ouroTotal = inimigos.stream()
                .mapToInt(i -> i.getRecompensa().getDinheiroGanhado())
                .sum();
        int xpTotal = inimigos.stream()
                .mapToInt(i -> i.getRecompensa().getExperienciaGanhada())
                .sum();
        logCombate.add("Recompensas: " + ouroTotal + " ouro, " + xpTotal + " XP");
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Helpers internos
    // ──────────────────────────────────────────────────────────────────────────

    private List<Aliado> aliadosVivos() {
        List<Aliado> vivos = new ArrayList<>();
        for (Aliado a : aliados) {
            if (a.estaVivo()) vivos.add(a);
        }
        return vivos;
    }

    private List<Inimigo> inimigosVivos() {
        List<Inimigo> vivos = new ArrayList<>();
        for (Inimigo i : inimigos) {
            if (i.estaVivo()) vivos.add(i);
        }
        return vivos;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Getters
    // ──────────────────────────────────────────────────────────────────────────

    public boolean isVitoria()          { return vitoria; }
    public boolean isBatalhaEncerrada() { return batalhaEncerrada; }

    public List<Aliado>  getAliados()  { return aliados; }
    public List<Inimigo> getInimigos() { return inimigos; }
    public FilaDeTurnos  getFilaDeTurnos() { return filaDeTurnos; }
    public List<String>  getLogCombate()   { return logCombate; }

    /** Inimigos vivos disponíveis como alvos para habilidades de DANO. */
    public List<Inimigo> getInimigosVivos() { return inimigosVivos(); }
    /** Aliados vivos disponíveis como alvos para habilidades de CURA/DEFESA. */
    public List<Aliado>  getAliadosVivos()  { return aliadosVivos(); }
}
