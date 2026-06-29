package sistema;

import java.util.List;

import com.badlogic.gdx.Gdx;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.TipoHabilidade;
import frontend.TelaBatalha;
import frontend.TelaDescanso;
import frontend.TelaGameOver;
import frontend.TelaLoja;
import frontend.TelaMapa;
import frontend.TelaMenu;
import progressao.Loja;
import progressao.Mapa;

/**
 * Gerenciador central do jogo — máquina de estados.
 *
 * É o elo entre o backend (lógica pura, sem LibGDX) e o frontend (LibGDX Screens).
 * Todas as Screens recebem referência a este GameManager para:
 *   - Ler o estado do jogo (tripulação, mapa, batalha, loja).
 *   - Disparar transições de tela via mudarEstado().
 *
 * Fluxo principal:
 *   JogoPiratas.create()
 *     → new GameManager(jogo) → mudarEstado(MENU)
 *     → TelaMenu: "Novo Jogo" → iniciarJogo() → mudarEstado(MAPA)
 *     → TelaMapa: clique em nó → mapa.avancarParaNo(no, this) → no.entrarNo(this)
 *         NoBatalha  → prepararBatalha(inimigos) → mudarEstado(BATALHA)
 *         NoDescanso → mudarEstado(DESCANSO)
 *         NoEvento   → mudarEstado(LOJA)
 *     → TelaBatalha: vitória → mudarEstado(MAPA) | derrota → mudarEstado(GAME_OVER)
 *     → TelaGameOver: retry → mudarEstado(MENU)
 */
public class GameManager {

    private final JogoPiratas jogo;
    private EstadoJogo estadoAtual;

    // --- Subsistemas de backend (sem dependência de LibGDX) ---
    private final Mapa mapa;
    private final Tripulacao tripulacao;
    private final GerenciadorDeBatalha gerenciadorDeBatalha;
    private final Loja loja;

    // --- Contexto de batalha: injetado por NoBatalha antes de mudarEstado(BATALHA) ---
    private List<Inimigo> inimigosAtivos;

    public GameManager(JogoPiratas jogo) {
        this.jogo = jogo;
        this.mapa = new Mapa();
        this.tripulacao = new Tripulacao();
        this.gerenciadorDeBatalha = new GerenciadorDeBatalha();
        this.loja = new Loja();
        this.estadoAtual = EstadoJogo.MENU;
    }

    /**
     * Configura a partida e avança para o mapa.
     * Chamado pelo botão "Novo Jogo" da TelaMenu.
     */
    public void iniciarJogo() {
        // Aliado 1 — Capitão (tanque)
        Aliado capitao = new Aliado("Capitão", 120, 15, 12, 1, 0);
        capitao.adicionarHabilidade(new Habilidade("Espadada Pesada", TipoHabilidade.DANO, 30));
        capitao.adicionarHabilidade(new Habilidade("Postura Defensiva", TipoHabilidade.DEFESA, 20));
        tripulacao.adicionarAliado(capitao);

        // Aliado 2 — Navegador (ágil)
        Aliado navegador = new Aliado("Navegador", 85, 8, 20, 1, 0);
        navegador.adicionarHabilidade(new Habilidade("Flechada", TipoHabilidade.DANO, 22));
        navegador.adicionarHabilidade(new Habilidade("Esquiva", TipoHabilidade.DEFESA, 15));
        tripulacao.adicionarAliado(navegador);

        // Aliado 3 — Curandeiro (suporte)
        Aliado curandeiro = new Aliado("Curandeiro", 70, 6, 16, 1, 0);
        curandeiro.adicionarHabilidade(new Habilidade("Cura Leve", TipoHabilidade.CURA, 25));
        curandeiro.adicionarHabilidade(new Habilidade("Golpe de Cajado", TipoHabilidade.DANO, 15));
        tripulacao.adicionarAliado(curandeiro);

        mapa.gerarProximosNos();
        mudarEstado(EstadoJogo.MAPA);
    }

    /**
     * Injeta o contexto de inimigos antes de iniciar uma batalha.
     * Chamado por NoBatalha.entrarNo() antes de mudarEstado(BATALHA).
     */
    public void prepararBatalha(List<Inimigo> inimigos) {
        this.inimigosAtivos = inimigos;
    }

    /**
     * Muda o estado do jogo e troca a Screen ativa do LibGDX.
     * Único ponto de transição entre telas — nunca chame jogo.setScreen() diretamente.
     */
    public void mudarEstado(EstadoJogo novoEstado) {
        this.estadoAtual = novoEstado;
        switch (novoEstado) {
            case MENU:
                jogo.setScreen(new TelaMenu(jogo, this));
                break;
            case MAPA:
                mapa.gerarProximosNos();
                jogo.setScreen(new TelaMapa(jogo, this));
                break;
            case BATALHA:
                // Configura o gerenciador com os participantes antes de abrir a tela
                gerenciadorDeBatalha.iniciarCombate(
                        tripulacao.getAliadosVivos(),
                        inimigosAtivos
                );
                jogo.setScreen(new TelaBatalha(jogo, this));
                break;
            case LOJA:
                jogo.setScreen(new TelaLoja(jogo, this));
                break;
            case DESCANSO:
                jogo.setScreen(new TelaDescanso(jogo, this));
                break;
            case GAME_OVER:
                jogo.setScreen(new TelaGameOver(jogo, this));
                break;
        }
    }

    /** Fecha a aplicação. Chamado pelo botão "Sair" da TelaMenu. */
    public void encerrarJogo() {
        Gdx.app.exit();
    }

    // --- Getters dos subsistemas (usados pelas Screens) ---
    public Mapa getMapa() { return mapa; }
    public Tripulacao getTripulacao() { return tripulacao; }
    public GerenciadorDeBatalha getGerenciadorDeBatalha() { return gerenciadorDeBatalha; }
    public Loja getLoja() { return loja; }
    public List<Inimigo> getInimigosAtivos() { return inimigosAtivos; }
    public EstadoJogo getEstadoAtual() { return estadoAtual; }
}
