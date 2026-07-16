package progressao;

import java.util.List;
import entidades.Inimigo;

/**
 * Representa um encontro de batalha dentro de uma Ilha.
 *
 * Cada ilha tem N rodadas: geralmente 2 rodadas de capangas
 * seguidas de 1 rodada de boss. A ultima rodada (isBoss=true)
 * e o combate final que desbloqueia a proxima ilha.
 */
public class Rodada {

    private final List<Inimigo> inimigos;
    private final boolean boss;
    private final String descricao;

    public Rodada(List<Inimigo> inimigos, boolean boss, String descricao) {
        this.inimigos  = inimigos;
        this.boss      = boss;
        this.descricao = descricao;
    }

    public List<Inimigo> getInimigos() { return inimigos; }
    public boolean isBoss()            { return boss; }
    public String getDescricao()       { return descricao; }
}
