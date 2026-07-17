package factories;

import entidades.Aliado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonagemFactoryTest {

    @Test
    void deveCriarAliadosCorretamente() {
        assertNotNull(PersonagemFactory.criarLuffy());
        assertNotNull(PersonagemFactory.criarZoro());
        assertNotNull(PersonagemFactory.criarNami());
        assertNotNull(PersonagemFactory.criarSanji());
        assertNotNull(PersonagemFactory.criarRobin());
        assertNotNull(PersonagemFactory.criarUsopp());
        assertNotNull(PersonagemFactory.criarChopper());
        assertNotNull(PersonagemFactory.criarBrook());
        assertNotNull(PersonagemFactory.criarFranky());
        assertNotNull(PersonagemFactory.criarJinbe());
        assertNotNull(PersonagemFactory.criarVivi());
        assertNotNull(PersonagemFactory.criarCarrot());
        assertNotNull(PersonagemFactory.criarYamato());
        assertNotNull(PersonagemFactory.criarLaw());
        assertNotNull(PersonagemFactory.criarRebecca());
        assertNotNull(PersonagemFactory.criarVegapunk());
        assertNotNull(PersonagemFactory.criarMomonosuke());
        assertNotNull(PersonagemFactory.criarBonClay());
        assertNotNull(PersonagemFactory.criarKoby());
        assertNotNull(PersonagemFactory.criarCarue());
        assertNotNull(PersonagemFactory.criarAce());
        assertNotNull(PersonagemFactory.criarLoki());
    }

    @Test
    void deveCriarInimigosCorretamente() {
        assertNotNull(PersonagemFactory.criarMarinheiro(1));
        assertNotNull(PersonagemFactory.criarPirataInimigo(1));
        assertNotNull(PersonagemFactory.criarChefe(1));
        assertNotNull(PersonagemFactory.criarMorgan(1));
        assertNotNull(PersonagemFactory.criarMoria(1));
        assertNotNull(PersonagemFactory.criarKuro(1));
        assertNotNull(PersonagemFactory.criarHodyJones(1));
        assertNotNull(PersonagemFactory.criarWapol(1));
        assertNotNull(PersonagemFactory.criarDoflamingo(1));
        assertNotNull(PersonagemFactory.criarEneru(1));
        assertNotNull(PersonagemFactory.criarCrocodile(1));
        assertNotNull(PersonagemFactory.criarDonKrieg(1));
        assertNotNull(PersonagemFactory.criarBuggy(1));
        assertNotNull(PersonagemFactory.criarArlong(1));
        assertNotNull(PersonagemFactory.criarRobLucci(1));
        assertNotNull(PersonagemFactory.criarBigMom(1));
        assertNotNull(PersonagemFactory.criarAkainu(1));
        assertNotNull(PersonagemFactory.criarKizaru(1));
        assertNotNull(PersonagemFactory.criarKaido(1));
        assertNotNull(PersonagemFactory.criarMihawk(1));
        assertNotNull(PersonagemFactory.criarSmoker(1));
        assertNotNull(PersonagemFactory.criarCesar(1));
        assertNotNull(PersonagemFactory.criarMagellan(1));
        assertNotNull(PersonagemFactory.criarImu(1));
    }
    
    @Test
    void deveSortearInimigosCanonicos() {
        assertFalse(PersonagemFactory.sortearInimigosCanonicos(1, 1).isEmpty());
        assertFalse(PersonagemFactory.sortearInimigosCanonicos(2, 1).isEmpty());
        assertFalse(PersonagemFactory.sortearInimigosCanonicos(3, 1).isEmpty());
    }
    
    @Test
    void devePegarBossCapitulo() {
        assertFalse(PersonagemFactory.getBossCapitulo(1, 1).isEmpty());
        assertFalse(PersonagemFactory.getBossCapitulo(2, 1).isEmpty());
        assertFalse(PersonagemFactory.getBossCapitulo(3, 1).isEmpty());
    }
}
