package progressao;

import sistema.GameManager;
import entidades.Inimigo;
import java.util.ArrayList;

public class NoBatalhaFinal implements NoMapa {
    
    private ArrayList<Inimigo> inimigos;

    public NoBatalhaFinal(Inimigo chefeFinal) {
        this.inimigos = new ArrayList<>();
        this.inimigos.add(chefeFinal);
    }

    public ArrayList<Inimigo> getInimigos() {
        return inimigos;
    }

    @Override
    public void entrarNo(GameManager controle) { 
    }
}
