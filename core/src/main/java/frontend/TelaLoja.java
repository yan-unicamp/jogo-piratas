package frontend;

import entidades.Item;
import java.util.List;
import java.util.Scanner;

public class TelaLoja {
    private Scanner scanner;

    public TelaLoja() {
        this.scanner = new Scanner(System.in);
    }

    public Item exibirCatalogo(List<Item> curas) {
        System.out.println("=== Catálogo da Loja ===");
        for (int i = 0; i < curas.size(); i++) {
            Item c = curas.get(i);
            System.out.printf("%d. %s - %s (Cura: %d, Custo: %d)\n", 
                              i + 1, c.getNome(), c.getDescricao(), c.getValor(), c.getPreco());
        }
        System.out.println("0. Sair");
        System.out.print("Escolha um item pelo número: ");
        
        int escolha = -1;
        if (scanner.hasNextInt()) {
            escolha = scanner.nextInt();
        } else {
            scanner.next(); // limpa entrada inválida
        }

        if (escolha > 0 && escolha <= curas.size()) {
            return curas.get(escolha - 1);
        }
        return null;
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}
