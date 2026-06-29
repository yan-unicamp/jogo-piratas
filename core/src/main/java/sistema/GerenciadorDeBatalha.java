package sistema;

import java.util.List;

import entidades.Aliado;
import entidades.Inimigo;

/**
 * Orquestra o combate por turnos entre aliados e inimigos.
 *
 * Contrato de integração (Membro 4 → Membro 2):
 *   1. GameManager chama iniciarCombate(aliados, inimigos) ao entrar em TelaBatalha.
 *   2. TelaBatalha chama executarTurno() a cada ação do jogador.
 *   3. TelaBatalha chama isVitoria() / isDerrota() para decidir o próximo estado.
 *
 * Implementação dos métodos: Membro 2 (Fase 2/3).
 */
public class GerenciadorDeBatalha {

    private FilaDeTurnos filaDeTurnos;
    private List<Aliado> aliados;
    private List<Inimigo> inimigos;
    private boolean batalhaEncerrada;
    private boolean vitoria;

    public GerenciadorDeBatalha() {
        this.filaDeTurnos = new FilaDeTurnos();
        this.batalhaEncerrada = false;
        this.vitoria = false;
    }

    /**
     * Configura e inicia um novo combate.
     * Deve ser chamado pelo GameManager imediatamente antes de abrir a TelaBatalha.
     *
     * Implementação: Membro 2.
     *   - Popular filaDeTurnos com aliados + inimigos
     *   - Ordenar por iniciativa
     *   - Resetar flags batalhaEncerrada / vitoria
     */
    public void iniciarCombate(List<Aliado> aliados, List<Inimigo> inimigos) {
        this.aliados = aliados;
        this.inimigos = inimigos;
        this.batalhaEncerrada = false;
        this.vitoria = false;
        // TODO Membro 2: popular filaDeTurnos e ordenar por iniciativa
    }

    /**
     * Executa a ação do personagem ativo no turno atual.
     * Chamado pela TelaBatalha quando o jogador clica num botão de habilidade.
     *
     * Implementação: Membro 2.
     */
    public void executarTurno() {
        // TODO Membro 2: aplicar habilidade do ator atual no alvo, avançar turno
    }

    /**
     * Verifica e atualiza os flags de fim de batalha.
     * Chamado pela TelaBatalha a cada frame após executarTurno().
     */
    public void verificarVitoriaOuDerrota() {
        // TODO Membro 2: checar se todos os inimigos morreram (vitoria=true)
        // ou se todos os aliados morreram (batalhaEncerrada=true, vitoria=false)
    }

    /** @return true se todos os inimigos foram derrotados. */
    public boolean isVitoria() { return vitoria; }

    /** @return true se a batalha terminou (por vitória ou derrota). */
    public boolean isBatalhaEncerrada() { return batalhaEncerrada; }

    // Getters dos participantes — usados pela TelaBatalha para exibir HP
    public List<Aliado> getAliados() { return aliados; }
    public List<Inimigo> getInimigos() { return inimigos; }
    public FilaDeTurnos getFilaDeTurnos() { return filaDeTurnos; }
}
