package progressao;

import sistema.Tripulacao;
import entidades.Item;
import frontend.TelaLoja;
import java.util.List;

public class Loja {

    public Loja() {}

    public void exibirCatalogo(List<Item> curas, Tripulacao cliente, TelaLoja tela) {
        Item itemEscolhido = tela.exibirCatalogo(curas);
        if (itemEscolhido != null) {
            String mensagem = comprarMelhoria(cliente, itemEscolhido);
            tela.mostrarMensagem(mensagem);
        } else {
            tela.mostrarMensagem("Nenhum item comprado.");
        }
    }

    public String comprarMelhoria(Tripulacao cliente, Item item) {
        if (cliente.gastarDinheiro(item.getPreco())) {
            cliente.receberItem(item);
            return "Compra realizada com sucesso: " + item.getNome() + "!";
        } else {
            return "Compra recusada: Dinheiro insuficiente.";
        }
    } 
}
