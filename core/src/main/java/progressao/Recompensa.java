package progressao;

import sistema.Tripulacao;
import entidades.Aliado;
import entidades.Item;

public class Recompensa {

    private Recompensa() {
        // Classe apenas com métodos estáticos
    }

    public static void darDinheiro(Tripulacao tripulacao, int valor) {
        tripulacao.receberDinheiro(valor);
    }
    
    public static void darItem(Tripulacao tripulacao, Item item) {
        if (item != null) {
            tripulacao.receberItem(item);
        }
    }

    public static void darXp(Tripulacao tripulacao, int valor) {
        for (Aliado aliado : tripulacao.getAliadosAtivos()) {
            aliado.addXp(valor);
        }
    }
}
