package sistema;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import entidades.Aliado;
import entidades.Inimigo;
import entidades.Item;
import factories.PersonagemFactory;
import factories.ItemFactory;

import java.util.ArrayList;

class TripulacaoTest {
    private Tripulacao t;

    @BeforeEach
    void setUp() {
        t = new Tripulacao();
    }

    @Test
    void deveGerenciarAliados() {
        Aliado luffy = PersonagemFactory.criarLuffy();
        t.adicionarAliado(luffy);
        t.adicionarAliadoAtivo(luffy);
        
        assertTrue(t.getAliados().contains(luffy));
        assertTrue(t.getAliadosAtivos().contains(luffy));
        
        t.removerAliadoAtivo(luffy);
        assertFalse(t.getAliadosAtivos().contains(luffy));
        assertTrue(t.getAliados().contains(luffy));
        
        t.removerAliado(luffy);
        assertFalse(t.getAliados().contains(luffy));
    }

    @Test
    void deveGerenciarDinheiro() {
        t.receberDinheiro(100);
        assertEquals(100, t.getDinheiro());
        
        assertTrue(t.gastarDinheiro(40));
        assertEquals(60, t.getDinheiro());
        
        assertFalse(t.gastarDinheiro(100)); // Nao tem 100
        assertEquals(60, t.getDinheiro());
    }

    @Test
    void deveGerenciarItens() {
        Item item = ItemFactory.criarBandagemSimples();
        t.receberItem(item);
        assertTrue(t.getItens().contains(item));
        
        Aliado luffy = PersonagemFactory.criarLuffy();
        luffy.receberDano(30);
        int vida = luffy.getVidaAtual();
        
        t.usarItem(item, luffy); // Usa o item
        assertFalse(t.getItens().contains(item)); // Foi removido
        assertTrue(luffy.getVidaAtual() > vida); // Foi curado
    }

    @Test
    void deveReceberRecompensa() {
        Aliado luffy = PersonagemFactory.criarLuffy();
        t.adicionarAliado(luffy);
        
        Inimigo marinha = PersonagemFactory.criarMarinheiro(1);
        marinha.setRecompensaItem(ItemFactory.criarBandagemSimples());
        
        ArrayList<Inimigo> inimigos = new ArrayList<>();
        inimigos.add(marinha);
        
        int xp = luffy.getExperiencia();
        t.receberRecompensa(inimigos);
        
        assertTrue(t.getDinheiro() > 0);
        assertTrue(luffy.getExperiencia() > xp);
        assertEquals(1, t.getItens().size());
    }

    @Test
    void deveVerificarTodosMortos() {
        Aliado luffy = PersonagemFactory.criarLuffy();
        t.adicionarAliado(luffy);
        assertFalse(t.todosMortos());
        assertEquals(1, t.getAliadosVivos().size());
        
        luffy.receberDano(9999);
        assertTrue(t.todosMortos());
        assertEquals(0, t.getAliadosVivos().size());
    }
}
