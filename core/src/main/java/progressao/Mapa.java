package progressao;

import java.util.ArrayList;
import java.util.List;

public class Mapa {
    private int etapaAtual;
    private NoMapa noAtual;
    private List<NoMapa> proximosNos;

    public Mapa() {
        this.etapaAtual = 1;
        this.proximosNos = new ArrayList<>();
    }

    /**
     * Gera os nós disponíveis para a próxima etapa.
     * Implementação: Membro 3 (Fase 2) — preencher com lógica de geração aleatória.
     */
    public void gerarProximosNos() {
        // TODO Membro 3: popular proximosNos com NoBatalha, NoEvento, NoDescanso gerados aleatoriamente
    }

    /**
     * Avança para o nó escolhido pelo jogador.
     * Atualiza noAtual e dispara entrarNo(), que por sua vez faz
     * gameManager.mudarEstado(...) e troca a Screen ativa.
     */
    public void avancarParaNo(NoMapa proximo, sistema.GameManager gameManager) {
        this.noAtual = proximo;
        this.etapaAtual++;
        proximo.entrarNo(gameManager);
    }

    /** @deprecated Use avancarParaNo(NoMapa, GameManager) — mantido para compatibilidade. */
    @Deprecated
    public void avancarParaNo(NoMapa proximo) {
        this.noAtual = proximo;
        this.etapaAtual++;
    }

    public int getEtapaAtual() { return etapaAtual; }
    public NoMapa getNoAtual() { return noAtual; }

    /**
     * @return Lista de nós disponíveis para a próxima etapa.
     * Usada pela TelaMapa para renderizar as opções de navegação.
     */
    public List<NoMapa> getProximosNos() { return proximosNos; }
}
