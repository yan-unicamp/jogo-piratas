package progressao;

import sistema.GameManager;
import entidades.Inimigo;
import factories.PersonagemFactory;
import java.util.ArrayList;

public class NoBatalhaFinal implements NoMapa {
    
    private ArrayList<Inimigo> inimigos;

    public NoBatalhaFinal(int capitulo, int nivel) {
        this.inimigos = PersonagemFactory.getBossCapitulo(capitulo, nivel);
    }

    public ArrayList<Inimigo> getInimigos() {
        return inimigos;
    }

    @Override
    public void entrarNo(GameManager controle) { 
        // A lógica de combate será acionada pelo GameManager
    }
}
