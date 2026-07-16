package sistema;

import java.util.ArrayList;
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
import frontend.TelaVitoria;
import progressao.Ilha;
import progressao.Loja;
import progressao.Mapa;
import progressao.Rodada;

/**
 * Gerenciador central do jogo — máquina de estados.
 *
 * É o elo entre o backend (lógica pura) e o frontend (LibGDX Screens).
 *
 * Fluxo principal:
 *  JogoPiratas.create() → new GameManager(jogo, assets) → mudarEstado(MENU)
 *  TelaMenu: "Novo Jogo" → iniciarJogo()        → mudarEstado(MAPA)
 *  TelaMapa: clique ilha → entrarIlha(ilha)     → mudarEstado(BATALHA)
 *  TelaBatalha: vitória  → avancarRodada():
 *    false → mais rodadas → mudarEstado(BATALHA)  [mesma ilha, nova rodada]
 *    true  → ilha concluída:
 *      mais ilhas → mudarEstado(MAPA)
 *      sem ilhas  → mudarEstado(VITORIA)
 *  TelaBatalha: derrota  → mudarEstado(GAME_OVER)
 */
public class GameManager {

    private final JogoPiratas jogo;
    private final Assets      assets;
    private EstadoJogo        estadoAtual;

    // Subsistemas de backend
    private final Mapa                mapa;
    private final Tripulacao          tripulacao;
    private final GerenciadorDeBatalha gerenciadorDeBatalha;
    private final Loja                loja;

    // Contexto de batalha atual
    private List<Inimigo> inimigosAtivos;

    public GameManager(JogoPiratas jogo, Assets assets) {
        this.jogo                = jogo;
        this.assets              = assets;
        this.mapa                = new Mapa();
        this.tripulacao          = new Tripulacao();
        this.gerenciadorDeBatalha = new GerenciadorDeBatalha();
        this.loja                = new Loja();
        this.estadoAtual         = EstadoJogo.MENU;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Ações do jogador
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Configura a tripulação com os 10 Chapéus de Palha e inicia o mapa.
     * Chamado pelo botão "Novo Jogo" da TelaMenu.
     */
    public void iniciarJogo() {
        tripulacao.getAliados().clear();

        // ── Tripulação dos Chapéus de Palha ──────────────────────────────────

        // Luffy — tanque / atacante pesado
        Aliado luffy = new Aliado("Luffy", 150, 8, 14, 1, 0);
        luffy.adicionarHabilidade(new Habilidade("Gomu Gomu no Pistol",  TipoHabilidade.DANO, 38));
        luffy.adicionarHabilidade(new Habilidade("Gear 2 — Red Hawk",    TipoHabilidade.DANO, 58));
        tripulacao.adicionarAliado(luffy);

        // Zoro — atacante equilibrado, alta iniciativa
        Aliado zoro = new Aliado("Zoro", 130, 10, 16, 1, 0);
        zoro.adicionarHabilidade(new Habilidade("Oni Giri",              TipoHabilidade.DANO, 42));
        zoro.adicionarHabilidade(new Habilidade("Santoryu — Tora Gari",  TipoHabilidade.DANO, 62));
        tripulacao.adicionarAliado(zoro);

        // Nami — suporte / curandeira
        Aliado nami = new Aliado("Nami", 90, 5, 18, 1, 0);
        nami.adicionarHabilidade(new Habilidade("Thunderbolt Tempo",     TipoHabilidade.DANO, 28));
        nami.adicionarHabilidade(new Habilidade("Curar Aliados",         TipoHabilidade.CURA, 35));
        tripulacao.adicionarAliado(nami);

        // Usopp — longo alcance, dano moderado
        Aliado usopp = new Aliado("Usopp", 100, 6, 15, 1, 0);
        usopp.adicionarHabilidade(new Habilidade("Flecha de Fogo",       TipoHabilidade.DANO, 30));
        usopp.adicionarHabilidade(new Habilidade("Kabuto — Tiro Livre",  TipoHabilidade.DANO, 46));
        tripulacao.adicionarAliado(usopp);

        // Sanji — alta iniciativa, chutes letais
        Aliado sanji = new Aliado("Sanji", 120, 7, 17, 1, 0);
        sanji.adicionarHabilidade(new Habilidade("Diable Jambe",         TipoHabilidade.DANO, 52));
        sanji.adicionarHabilidade(new Habilidade("Hell Memories",        TipoHabilidade.DANO, 68));
        tripulacao.adicionarAliado(sanji);

        // Chopper — curandeiro / combatente
        Aliado chopper = new Aliado("Chopper", 110, 9, 12, 1, 0);
        chopper.adicionarHabilidade(new Habilidade("Rumble Ball — Horn", TipoHabilidade.DANO, 36));
        chopper.adicionarHabilidade(new Habilidade("Curar Feridos",      TipoHabilidade.CURA, 50));
        tripulacao.adicionarAliado(chopper);

        // Robin — controle, dano múltiplo
        Aliado robin = new Aliado("Robin", 105, 6, 13, 1, 0);
        robin.adicionarHabilidade(new Habilidade("Mil Fleurs — Clutch",  TipoHabilidade.DANO, 44));
        robin.adicionarHabilidade(new Habilidade("Gigantesco — Hold",    TipoHabilidade.DANO, 60));
        tripulacao.adicionarAliado(robin);

        // Franky — tanque / disparo frontal
        Aliado franky = new Aliado("Franky", 140, 12, 10, 1, 0);
        franky.adicionarHabilidade(new Habilidade("Fresh Fire",          TipoHabilidade.DANO, 40));
        franky.adicionarHabilidade(new Habilidade("General Cannon",      TipoHabilidade.DANO, 70));
        tripulacao.adicionarAliado(franky);

        // Brook — ataque fantasma, velocidade
        Aliado brook = new Aliado("Brook", 95, 5, 19, 1, 0);
        brook.adicionarHabilidade(new Habilidade("Gavotte Bond en Avant", TipoHabilidade.DANO, 48));
        brook.adicionarHabilidade(new Habilidade("Soul King — Prelude",   TipoHabilidade.DANO, 64));
        tripulacao.adicionarAliado(brook);

        // Jinbe — tanque / defensor
        Aliado jinbe = new Aliado("Jinbe", 145, 14, 11, 1, 0);
        jinbe.adicionarHabilidade(new Habilidade("Arabesque — Ōdan",     TipoHabilidade.DANO, 50));
        jinbe.adicionarHabilidade(new Habilidade("Escudo de Água",        TipoHabilidade.DEFESA, 30));
        tripulacao.adicionarAliado(jinbe);

        mudarEstado(EstadoJogo.MAPA);
    }

    /**
     * Entra em uma ilha: reseta progresso e inicia a primeira rodada.
     * Chamado por TelaMapa ao clicar em uma ilha disponível.
     */
    public void entrarIlha(Ilha ilha) {
        mapa.entrarIlha(ilha);
        prepararBatalha(new ArrayList<>(mapa.getRodadaAtual().getInimigos()));
        mudarEstado(EstadoJogo.BATALHA);
    }

    /**
     * Avança para a próxima rodada da ilha atual.
     * Atualiza inimigosAtivos se ainda há rodadas.
     *
     * @return true se a ilha foi completamente vencida.
     */
    public boolean avancarRodada() {
        boolean ilhaCompleta = mapa.avancarRodada();
        if (!ilhaCompleta) {
            Rodada proxima = mapa.getRodadaAtual();
            if (proxima != null) {
                prepararBatalha(new ArrayList<>(proxima.getInimigos()));
            }
        }
        return ilhaCompleta;
    }

    /**
     * Injeta inimigos para a próxima batalha.
     * Chamado antes de mudarEstado(BATALHA).
     */
    public void prepararBatalha(List<Inimigo> inimigos) {
        this.inimigosAtivos = inimigos;
    }

    /**
     * Verifica se o jogo foi completado (todas as ilhas vencidas).
     * Deve ser chamado após uma ilha ser concluída.
     */
    public boolean verificarVitoria() {
        return mapa.jogoCompleto();
    }

    /**
     * Muda o estado do jogo e troca a Screen ativa.
     * Único ponto de transição entre telas.
     */
    public void mudarEstado(EstadoJogo novoEstado) {
        this.estadoAtual = novoEstado;
        switch (novoEstado) {
            case MENU:
                jogo.setScreen(new TelaMenu(jogo, this));
                break;
            case MAPA:
                jogo.setScreen(new TelaMapa(jogo, this));
                break;
            case BATALHA:
                gerenciadorDeBatalha.iniciarCombate(
                        tripulacao.getAliadosVivos(),
                        inimigosAtivos);
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
            case VITORIA:
                jogo.setScreen(new TelaVitoria(jogo, this));
                break;
        }
    }

    /** Fecha a aplicação. */
    public void encerrarJogo() {
        Gdx.app.exit();
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Getters
    // ──────────────────────────────────────────────────────────────────────────

    public Assets              getAssets()               { return assets; }
    public Mapa                getMapa()                 { return mapa; }
    public Tripulacao          getTripulacao()           { return tripulacao; }
    public GerenciadorDeBatalha getGerenciadorDeBatalha() { return gerenciadorDeBatalha; }
    public Loja                getLoja()                 { return loja; }
    public List<Inimigo>       getInimigosAtivos()       { return inimigosAtivos; }
    public EstadoJogo          getEstadoAtual()          { return estadoAtual; }
}
