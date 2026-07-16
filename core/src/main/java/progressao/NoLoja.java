package progressao;

import sistema.GameManager;
import entidades.Item;
import factories.ItemFactory;
import frontend.TelaLoja;
import java.util.ArrayList;
import java.util.List;

/**
 * Nó de Loja.
 * Permite que a tripulação compre itens com o ouro acumulado.
 */
public class NoLoja implements NoMapa {

    @Override
    public void entrarNo(GameManager controle) {
        System.out.println("\n[!] NÓ DE LOJA [!]");
        System.out.println("Você encontrou um navio mercante amigável!");
        
        Loja loja = new Loja();
        TelaLoja telaLoja = new TelaLoja();
        
        // Gerar alguns itens para vender (ex: poções criadas pelo ItemFactory)
        List<Item> itensAVenda = new ArrayList<>();
        itensAVenda.add(ItemFactory.criarBandagemSimples());
        itensAVenda.add(ItemFactory.criarCarneComOsso());
        itensAVenda.add(ItemFactory.criarKitMedico());
        
        loja.exibirCatalogo(itensAVenda, controle.getTripulacao(), telaLoja);
    }
}
