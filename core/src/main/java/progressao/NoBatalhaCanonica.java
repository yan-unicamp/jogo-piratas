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
        // Opcional: adicionar lacaios genericos caso queira que o boss venha acompanhado.
    }

    public ArrayList<Inimigo> getInimigos() {
        return inimigos;
    }

    @Override
    public void entrarNo(GameManager controle) { 
        // A logica de combate sera acionada pelo GameManager, que checara o tipo do no
        // e usara getInimigos().
    }
}
