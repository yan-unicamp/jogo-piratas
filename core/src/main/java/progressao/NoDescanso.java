package progressao;

import sistema.GameManager;
import entidades.Aliado;
import java.util.Scanner;

/**
 * Nó de Descanso.
 * Recupera 30% da vida de toda a tripulação.
 */
public class NoDescanso implements NoMapa {

    @Override
    public void entrarNo(GameManager controle) {
        System.out.println("\n[!] NÓ DE DESCANSO [!]");
        System.out.println("Você encontrou um local calmo e seguro. A tripulação monta acampamento para descansar e curar suas feridas.");
        System.out.println("Pressione ENTER para continuar...");
        
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        for (Aliado aliado : controle.getTripulacao().getAliadosVivos()) {
            float cura = aliado.getVidaMaxima() * 0.3f;
            aliado.curar((int)cura);
        }

        System.out.println("Todos os aliados recuperaram 30% da vida máxima!\n");
    }
}
