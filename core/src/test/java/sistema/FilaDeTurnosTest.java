package sistema;

import entidades.Aliado;
import entidades.Personagem;
import factories.PersonagemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FilaDeTurnosTest {

    private FilaDeTurnos fila;

    @BeforeEach
    void setUp() {
        fila = new FilaDeTurnos();
    }

    @Test
    void deveOrdenarPorIniciativaDecrescente() {
        // Arrange
        Aliado p1 = PersonagemFactory.criarLuffy(); // Iniciativa = 20
        Aliado p2 = PersonagemFactory.criarSanji(); // Iniciativa = 30
        
        fila.adicionar(p1);
        fila.adicionar(p2);
        
        // Act
        fila.ordenarPorIniciativa();
        
        // Assert - Sanji deve agir primeiro por ter maior iniciativa (30 > 20)
        Personagem primeiro = fila.obterProximoPersonagem();
        assertEquals("Luffy", primeiro.getNome());
        
        Personagem segundo = fila.obterProximoPersonagem();
        assertEquals("Sanji", segundo.getNome());
    }

    @Test
    void deveResetarFilaAposTodosAgirem() {
        Aliado p1 = PersonagemFactory.criarLuffy();
        fila.adicionar(p1);
        fila.ordenarPorIniciativa();

        // 1º Turno (Luffy age)
        assertNotNull(fila.obterProximoPersonagem());
        
        // Fim da rodada (fila vazia) -> deve retornar null
        assertNull(fila.obterProximoPersonagem());

        // Nova rodada -> fila resetada
        Personagem personagemNovaRodada = fila.obterProximoPersonagem();
        assertNotNull(personagemNovaRodada);
        assertEquals("Luffy", personagemNovaRodada.getNome());
    }

    @Test
    void deveRemoverPersonagemMorto() {
        Aliado p1 = PersonagemFactory.criarLuffy();
        fila.adicionar(p1);
        
        fila.remover(p1);
        
        assertTrue(fila.getFila().isEmpty());
    }
}
