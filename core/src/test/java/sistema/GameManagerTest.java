package sistema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private GameManager gm;

    @BeforeEach
    void setUp() {
        // Inicializa o GameManager antes de cada teste
        gm = new GameManager();
    }

    @Test
    void deveInicializarGameManagerCorretamente() {
        assertNotNull(gm.getMapa(), "Mapa deve ser inicializado");
        assertNotNull(gm.getTripulacao(), "Tripulação deve ser inicializada");
        
        // Verifica se a tripulação foi inicializada com o Luffy
        assertFalse(gm.getTripulacao().getAliados().isEmpty(), "Tripulação deve começar com pelo menos um aliado");
        assertEquals("Luffy", gm.getTripulacao().getAliados().get(0).getNome());
        
        // Dinheiro inicial deve ser 0
        assertEquals(0, gm.getOuro());
    }

    @Test
    void deveMudarEstadoCorretamente() {
        // Apenas verifica se a transição de estado não quebra o sistema
        assertDoesNotThrow(() -> gm.mudarEstado(EstadoJogo.MAPA));
        assertDoesNotThrow(() -> gm.mudarEstado(EstadoJogo.BATALHA));
    }
    
    @Test
    void deveMudarEstado() {
        GameManager gm = new GameManager();
        gm.mudarEstado(EstadoJogo.MAPA);
        // Só verifica se nao lanca excecao
        assertTrue(true);
    }
    
    @Test
    void deveGerenciarOuro() {
        GameManager gm = new GameManager();
        gm.getTripulacao().receberDinheiro(100);
        assertTrue(gm.gastarOuro(50));
        assertEquals(50, gm.getOuro());
        assertFalse(gm.gastarOuro(100)); // Nao tem o suficiente
    }
    
    @Test
    void deveEntrarIlha() {
        GameManager gm = new GameManager();
        gm.entrarIlha(factories.IlhaFactory.criarGenerica(progressao.IlhasGenericasEnum.ORANGE_TOWN));
        assertTrue(true);
    }
    
    @Test
    void deveEncerrarJogo() {
        GameManager gm = new GameManager();
        gm.encerrarJogo();
        assertTrue(true);
    }

    @Test
    void deveSotearBossCanonicoEObterBossFinal() throws Exception {
        GameManager gm = new GameManager();
        
        java.lang.reflect.Method sortearBoss = GameManager.class.getDeclaredMethod("sortearBossCanonico", int.class);
        sortearBoss.setAccessible(true);
        
        // Deve retornar chefes e esvaziar a lista de chefes disponiveis
        for (int i = 0; i < 4; i++) {
            entidades.Inimigo boss = (entidades.Inimigo) sortearBoss.invoke(gm, 1);
            assertNotNull(boss);
        }
        for (int i = 0; i < 5; i++) {
            entidades.Inimigo boss = (entidades.Inimigo) sortearBoss.invoke(gm, 2);
            assertNotNull(boss);
        }
        for (int i = 0; i < 4; i++) {
            entidades.Inimigo boss = (entidades.Inimigo) sortearBoss.invoke(gm, 3);
            assertNotNull(boss);
        }
        
        java.lang.reflect.Method obterFinal = GameManager.class.getDeclaredMethod("obterBossFinal", int.class);
        obterFinal.setAccessible(true);
        
        assertNotNull(obterFinal.invoke(gm, 1));
        assertNotNull(obterFinal.invoke(gm, 2));
        assertNotNull(obterFinal.invoke(gm, 3));
    }
    
    @Test
    void deveAcharOnePiece() throws Exception {
        GameManager gm = new GameManager();
        java.lang.reflect.Method acharOP = GameManager.class.getDeclaredMethod("acharOnePiece");
        acharOP.setAccessible(true);
        acharOP.invoke(gm);
        // Verify jogoRodando is false
        java.lang.reflect.Field jr = GameManager.class.getDeclaredField("jogoRodando");
        jr.setAccessible(true);
        assertFalse((Boolean) jr.get(gm));
    }
}
