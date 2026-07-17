package entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import factories.PersonagemFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AliadoTest {

    private Aliado aliado;

    @BeforeEach
    void setUp() {
        // Personagem criado no nivel 1 com 0 de xp
        aliado = PersonagemFactory.criarLuffy();
    }

    @Test
    void deveGanharExperienciaESubirDeNivel() {
        int nivelInicial = aliado.getNivel();
        int vidaMaxInicial = aliado.getVidaMaxima();
        float defesaInicial = aliado.getDefesa();
        
        // 100 xp deve ser suficiente para subir do nivel 1 para o nivel 2
        aliado.ganharExperiencia(100);
        
        assertEquals(nivelInicial + 1, aliado.getNivel());
        assertEquals(vidaMaxInicial + 20, aliado.getVidaMaxima());
        assertTrue(aliado.getDefesa() < defesaInicial, "A defesa deve diminuir (ficar melhor) ao subir de nivel");
        assertEquals(0, aliado.getExperiencia());
    }

    @Test
    void deveAcumularExperienciaSeNaoForSuficienteParaSubirDeNivel() {
        aliado.ganharExperiencia(50);
        
        assertEquals(1, aliado.getNivel());
        assertEquals(50, aliado.getExperiencia());
    }

    @Test
    void deveDesbloquearHabilidadeAoAtingirNivelEspecifico() {
        // O Luffy já tem habilidades destraváveis configuradas na Factory
        // Vamos forçar ele a subir vários níveis e verificar se ele ganha habilidades
        List<Habilidade> habsDesbloqueadas = aliado.ganharExperiencia(100 + 200 + 300); // Sobe para nível 4
        
        assertFalse(habsDesbloqueadas.isEmpty(), "Deve desbloquear novas habilidades ao subir de nível");
        assertEquals(4, aliado.getNivel());
    }

    @Test
    void deveCurarCorretamente() {
        aliado.receberDano(50);
        int vida = aliado.getVidaAtual();
        aliado.curar(20);
        assertEquals(vida + 20, aliado.getVidaAtual());
        
        aliado.curar(999);
        assertEquals(aliado.getVidaMaxima(), aliado.getVidaAtual());
    }
    
    @Test
    void deveAumentarStatusTemporarios() {
        int initAntiga = aliado.getIniciativa();
        aliado.aumentarIniciativa(10);
        assertEquals(initAntiga + 10, aliado.getIniciativa());
        
        int vidaMax = aliado.getVidaMaxima();
        aliado.aumentarVidaMaxima(10);
        assertEquals(vidaMax + 10, aliado.getVidaMaxima());
        
        float defAntiga = aliado.getDefesa();
        aliado.aumentarDefesaTemporaria(0.2f);
        assertTrue(aliado.getDefesa() < defAntiga);
        
        aliado.resetarDefesa();
        assertEquals(defAntiga, aliado.getDefesa(), 0.01f);
    }
    
    @Test
    void deveGerenciarTurnosDePausa() {
        aliado.setTurnosDePausa(2);
        assertEquals(2, aliado.getTurnosDePausa());
        
        aliado.decrementarTurnoDePausa();
        assertEquals(1, aliado.getTurnosDePausa());
    }
}
