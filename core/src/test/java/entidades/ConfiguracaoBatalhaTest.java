package entidades;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ConfiguracaoBatalhaTest {

    @Test
    void deveCriarERetornarValoresCorretos() {
        ArrayList<Personagem> aliados = new ArrayList<>();
        ArrayList<Personagem> inimigos = new ArrayList<>();
        
        ConfiguracaoBatalha config = new ConfiguracaoBatalha("Arlong Park", "fundo.png", aliados, inimigos);
        
        assertEquals("Arlong Park", config.getTituloArco());
        assertEquals("fundo.png", config.getCaminhoFundo());
        assertEquals(aliados, config.getAliados());
        assertEquals(inimigos, config.getInimigos());
    }
}
