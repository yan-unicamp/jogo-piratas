package progressao;

import factories.IlhaFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaria para inicializar a lista de ilhas do jogo,
 * agora utilizando a IlhaFactory e o IlhaEnum.
 */
public class ProgressaoIlhas {

    public static List<Ilha> criarTodasIlhas() {
        List<Ilha> ilhas = new ArrayList<>();
        for (IlhaEnum ilhaEnum : IlhaEnum.values()) {
            ilhas.add(IlhaFactory.criar(ilhaEnum));
        }
        return ilhas;
    }
}
