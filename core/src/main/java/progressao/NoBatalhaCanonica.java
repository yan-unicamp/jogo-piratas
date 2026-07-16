package progressao;

import sistema.GameManager;
import entidades.Inimigo;
import java.util.ArrayList;
import java.util.List;

public class NoBatalhaCanonica implements NoMapa {
    
    private ArrayList<Inimigo> inimigos;

    public NoBatalhaCanonica(Inimigo chefeSorteado) {
        this.inimigos = new ArrayList<>();
        this.inimigos.add(chefeSorteado);
        // Opcional: adicionar lacaios genéricos caso queira que o boss venha acompanhado.
    }

    public ArrayList<Inimigo> getInimigos() {
        return inimigos;
    }

    @Override
    public void entrarNo(GameManager controle) { 
        // A lógica de combate será acionada pelo GameManager, que checará o tipo do nó
        // e usará getInimigos().
    }
}
