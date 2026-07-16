package progressao;

import sistema.GameManager;
import entidades.Inimigo;
import factories.PersonagemFactory;
import java.util.ArrayList;

public class NoBatalhaCanonica implements NoMapa {
    
    private ArrayList<Inimigo> inimigos;

    public NoBatalhaCanonica(int capitulo, int nivel) {
        this.inimigos = PersonagemFactory.sortearInimigosCanonicos(capitulo, nivel);
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
