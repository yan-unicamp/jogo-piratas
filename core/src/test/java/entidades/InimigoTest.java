package entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import factories.PersonagemFactory;

import static org.junit.jupiter.api.Assertions.*;

class InimigoTest {

    private Inimigo inimigo;

    @BeforeEach
    void setUp() {
        inimigo = PersonagemFactory.criarMarinheiro(1);
    }

    @Test
    void deveTerRecompensasCorretas() {
        // As recompensas do marinheiro nível 1 na PersonagemFactory
        assertEquals(10, inimigo.getRecompensaDinheiro());
        assertEquals(5, inimigo.getRecompensaExperiencia());
        assertNull(inimigo.getRecompensaAliado());
        assertNull(inimigo.getRecompensaItem());
    }

    @Test
    void deveAtribuirAliadoComoRecompensaCorretamente() {
        Aliado aliadoRecompensa = PersonagemFactory.criarZoro();
        
        // Testa o método builder fluente
        inimigo.comAliado(aliadoRecompensa);
        
        assertNotNull(inimigo.getRecompensaAliado());
        assertEquals("Zoro", inimigo.getRecompensaAliado().getNome());
    }
}
