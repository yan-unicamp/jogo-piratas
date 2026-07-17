package progressao;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import entidades.Inimigo;
import entidades.Aliado;
import factories.PersonagemFactory;

import java.util.ArrayList;
import java.util.List;

class IlhaTest {
    
    @Test
    void deveGerenciarAtributosEBuscarsRecompensaAliado() {
        List<Rodada> rodadas = new ArrayList<>();
        
        // Rodada 1 normal
        List<Inimigo> capangas = new ArrayList<>();
        capangas.add(PersonagemFactory.criarMarinheiro(1));
        rodadas.add(new Rodada(capangas, false, "bg_capangas.png"));
        
        // Rodada 2 boss
        List<Inimigo> bosses = new ArrayList<>();
        Inimigo buggy = PersonagemFactory.criarBuggy(1);
        Aliado recompensa = PersonagemFactory.criarZoro();
        buggy.setRecompensaAliado(recompensa);
        bosses.add(buggy);
        rodadas.add(new Rodada(bosses, true, "bg_boss.png"));
        
        Ilha ilha = new Ilha("Orange Town", "bg.png", rodadas);
        ilha.setDescricao("Cidade do Buggy");
        ilha.setIdSave("ENUM:ORANGE_TOWN");
        
        assertEquals("Orange Town", ilha.getNome());
        assertEquals("bg.png", ilha.getBgKey());
        assertEquals("Cidade do Buggy", ilha.getDescricao());
        assertEquals("ENUM:ORANGE_TOWN", ilha.getIdSave());
        assertEquals(2, ilha.getTotalRodadas());
        
        Aliado ali = ilha.getRecompensaAliadoDaIlha();
        assertNotNull(ali);
        assertEquals(recompensa.getNome(), ali.getNome());
        
        assertFalse(ilha.isCompleta());
        assertEquals(0, ilha.getRodadaAtualIdx());
        assertNotNull(ilha.getRodadaAtual());
        
        assertFalse(ilha.avancarRodada());
        assertEquals(1, ilha.getRodadaAtualIdx());
        
        assertTrue(ilha.avancarRodada());
        assertTrue(ilha.isCompleta());
        assertNull(ilha.getRodadaAtual()); // Nao tem rodada 2
        
        ilha.resetar();
        assertEquals(0, ilha.getRodadaAtualIdx());
        assertFalse(ilha.isCompleta());
    }
}
