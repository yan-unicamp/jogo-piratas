package factories;

import entidades.Item;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemFactoryTest {

    @Test
    void deveCriarBandagemSimplesCorretamente() {
        Item item = ItemFactory.criarBandagemSimples();
        assertNotNull(item);
        assertEquals("Bandagem Simples", item.getNome());
        assertEquals(5, item.getPreco());
        assertEquals(15, item.getValor());
    }

    @Test
    void deveCriarCarneComOssoCorretamente() {
        Item item = ItemFactory.criarCarneComOsso();
        assertNotNull(item);
        assertEquals("Carne com Osso", item.getNome());
        assertEquals(45, item.getPreco());
        assertEquals(100, item.getValor());
    }

    @Test
    void deveCriarKitMedicoCorretamente() {
        Item item = ItemFactory.criarKitMedico();
        assertNotNull(item);
        assertEquals("Kit Medico do Chopper", item.getNome());
        assertEquals(80, item.getPreco());
        assertEquals(150, item.getValor());
    }
}
